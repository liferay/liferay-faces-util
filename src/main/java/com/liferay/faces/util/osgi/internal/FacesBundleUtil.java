/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.util.osgi.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleRequirement;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Kyle Stiemann
 */
public final class FacesBundleUtil {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(FacesBundleUtil.class);

	// Private Constants
	private static final boolean CURRENT_BUNDLE_DETECTED;
	private static final Long OSGI_FRAMEWORK_BUNDLE_ID = 0L;

	static {

		boolean currentBundleDetected = false;

		if (OSGiEnvironment.isApiDetected()) {

			Bundle currentBundle = FrameworkUtil.getBundle(FacesBundleUtil.class);

			if (currentBundle != null) {
				currentBundleDetected = true;
			}
		}

		CURRENT_BUNDLE_DETECTED = currentBundleDetected;
	}

	private FacesBundleUtil() {
		throw new AssertionError();
	}

	public static Collection<Bundle> getFacesBundles(Object context) {

		Map<Long, Bundle> facesBundles = null;

		if (CURRENT_BUNDLE_DETECTED) {

			facesBundles = (Map<Long, Bundle>) getServletContextAttribute(context, FacesBundleUtil.class.getName());

			if (facesBundles == null) {

				facesBundles = new HashMap<Long, Bundle>();

				Bundle wabBundle = getCurrentFacesWab(context);

				if (wabBundle != null) {

					// If the WAB's dependencies are not contained in the WAB's WEB-INF/lib, find all the WAB's
					// dependencies and return them as well.
					if (!isCurrentBundleThickWab()) {
						addRequiredBundlesRecurse(facesBundles, wabBundle);
					}

					facesBundles = Collections.unmodifiableMap(facesBundles);
					setServletContextAttribute(context, FacesBundleUtil.class.getName(), facesBundles);
				}
			}
		}

		if (facesBundles == null) {
			facesBundles = Collections.emptyMap();
		}

		return facesBundles.values();
	}

	public static ClassLoader getFacesBundleWiringClassLoader(Bundle facesBundle) {

		ClassLoader classLoader = null;

		if (facesBundle != null) {

			BundleWiring bundleWiring = facesBundle.adapt(BundleWiring.class);

			if (bundleWiring != null) {
				classLoader = bundleWiring.getClassLoader();
			}
		}

		return classLoader;
	}

	public static boolean isCurrentWarThinWab() {
		return CURRENT_BUNDLE_DETECTED && !isCurrentBundleThickWab();
	}

	public static boolean isWab(Bundle bundle) {

		String webContextPathHeader = null;

		if (bundle != null) {

			Dictionary<String, String> headers = bundle.getHeaders();
			webContextPathHeader = headers.get("Web-ContextPath");
		}

		return webContextPathHeader != null;
	}

	/* package-private */ static Bundle getCurrentFacesWab(Object context) {

		BundleContext bundleContext = (BundleContext) getServletContextAttribute(context, "osgi-bundlecontext");
		Bundle bundle = null;

		if (bundleContext != null) {

			try {
				bundle = bundleContext.getBundle();
			}
			catch (IllegalStateException e) {
				// Ignore
			}
		}

		return bundle;
	}

	private static void addRequiredBundlesRecurse(Map<Long, Bundle> facesBundles, Bundle bundle) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		if (bundleWiring != null) {

			List<BundleWire> bundleWires = bundleWiring.getRequiredWires(BundleRevision.PACKAGE_NAMESPACE);

			if (bundleWires != null) {

				for (BundleWire bundleWire : bundleWires) {

					BundleRevision provider = bundleWire.getProvider();

					if (provider == null) {
						continue;
					}

					Bundle providerBundle = provider.getBundle();
					Long key = providerBundle.getBundleId();

					if (OSGI_FRAMEWORK_BUNDLE_ID.equals(key)) {
						continue;
					}

					// If the provider bundle is Mojarra, PrimeFaces, or a Liferay Faces bundle and it was
					// obtained dynamically, skip it. If a bundle uses an overly-broad DynamicImport-Package
					// header, unnecessary and even erroneous bundles can be included in the list of Faces
					// bundles. For example, some Liferay bundles use headers like
					// "DynamicImport-Package:com.liferay.*" which will cause portlets such as the JSF Showcase
					// portlet to include Liferay Faces Portal, Liferay Faces Alloy, and Liferay Faces Clay
					// unnecessarily, which causes bugs in the JSF Showcase. Similarly, if PrimeFaces is added
					// to the list of Faces bundles for a portlet which does not expect it, it can completely
					// change the h:head renderer and add unnecessary front-end resources to every page causing
					// performance issues and other bugs.
					if (facesBundles.containsKey(key) || isIgnoredBundle(providerBundle.getSymbolicName()) ||
							(isFacesLibraryBundle(key) && isDynamicDependency(bundleWire))) {
						continue;
					}

					logger.debug("JSF module dependency: [{0}]", providerBundle.getSymbolicName());

					facesBundles.put(key, providerBundle);

					String symbolicName = bundle.getSymbolicName();

					if (isBridgeBundle(symbolicName, "impl")) {

						Map<Long, Bundle> bridgeImplBundles = getBridgeImplBundles(providerBundle);
						Set<Long> bridgeImplBundleKeys = bridgeImplBundles.keySet();

						for (Long bridgeImplBundleKey : bridgeImplBundleKeys) {

							Bundle bridgeImplBundle = bridgeImplBundles.get(bridgeImplBundleKey);
							facesBundles.put(bridgeImplBundleKey, bridgeImplBundle);
							addRequiredBundlesRecurse(facesBundles, bridgeImplBundle);
						}
					}

					addRequiredBundlesRecurse(facesBundles, providerBundle);
				}
			}
		}
	}

	private static String getBridgeBundleSymbolicName(String bundleSymbolicNameSuffix) {
		return "com.liferay.faces.bridge." + bundleSymbolicNameSuffix;
	}

	private static Map<Long, Bundle> getBridgeImplBundles(Bundle bridgeAPIBundle) {

		Map<Long, Bundle> bridgeImplBundles = new TreeMap<Long, Bundle>();
		BundleWiring bundleWiring = bridgeAPIBundle.adapt(BundleWiring.class);

		if (bundleWiring != null) {

			List<BundleWire> bundleWires = bundleWiring.getProvidedWires(BundleRevision.PACKAGE_NAMESPACE);

			if (bundleWires != null) {

				boolean bridgeImplFound = false;
				boolean bridgeExtFound = false;

				for (BundleWire bundleWire : bundleWires) {

					Bundle bundleDependingOnBridgeAPI = bundleWire.getRequirer().getBundle();
					String symbolicName = bundleDependingOnBridgeAPI.getSymbolicName();
					boolean bridgeImpl = isBridgeBundle(symbolicName, "impl");
					boolean bridgeExt = isBridgeBundle(symbolicName, "ext");

					if (bridgeImpl || bridgeExt) {
						bridgeImplBundles.putIfAbsent(bundleDependingOnBridgeAPI.getBundleId(),
							bundleDependingOnBridgeAPI);

						if (!bridgeExtFound) {
							bridgeExtFound = bridgeExt;
						}

						if (!bridgeImplFound) {
							bridgeImplFound = bridgeImpl;
						}

						if (bridgeImplFound && bridgeExtFound) {
							break;
						}
					}
				}
			}
		}

		return Collections.unmodifiableMap(bridgeImplBundles);
	}

	private static Object getServletContextAttribute(Object context, String servletContextAttributeName) {

		Object servletContextAttributeValue = null;
		boolean isFacesContext = context instanceof FacesContext;

		if (isFacesContext || (context instanceof ExternalContext)) {

			ExternalContext externalContext;

			if (isFacesContext) {

				FacesContext facesContext = (FacesContext) context;
				externalContext = facesContext.getExternalContext();
			}
			else {
				externalContext = (ExternalContext) context;
			}

			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			servletContextAttributeValue = applicationMap.get(servletContextAttributeName);
		}
		else if (context instanceof ServletContext) {

			ServletContext servletContext = (ServletContext) context;
			servletContextAttributeValue = servletContext.getAttribute(servletContextAttributeName);
		}
		else if (context instanceof Map) {

			Map<String, Object> applicationMap = (Map<String, Object>) context;
			servletContextAttributeValue = applicationMap.get(servletContextAttributeName);
		}
		else {
			throwIllegalContextClassException(context);
		}

		return servletContextAttributeValue;
	}

	private static boolean isBridgeBundle(String symbolicName, String bundleSymbolicNameSuffix) {

		String bridgeBundleSymbolicName = getBridgeBundleSymbolicName(bundleSymbolicNameSuffix);

		return symbolicName.equals(bridgeBundleSymbolicName);
	}

	private static boolean isCurrentBundleThickWab() {

		// If the current bundle is a WAB, then Liferay Faces Util must be inside the WAB's WEB-INF/lib folder and the
		// WAB is a thick WAB.
		Bundle bundle = FrameworkUtil.getBundle(FacesBundleUtil.class);

		return isWab(bundle);
	}

	private static boolean isDynamicDependency(BundleWire bundleWire) {

		boolean dynamicDependency = false;
		BundleRequirement requirement = bundleWire.getRequirement();

		if (requirement != null) {

			Map<String, String> directives = requirement.getDirectives();
			String resolution = directives.get("resolution");
			dynamicDependency = "dynamic".equalsIgnoreCase(resolution);
		}

		return dynamicDependency;
	}

	private static boolean isFacesLibraryBundle(Long key) {
		return key < 0;
	}

	private static boolean isIgnoredBundle(String symbolicName) {

		if (symbolicName.startsWith("com.liferay.portal") || symbolicName.startsWith("com.liferay.util") ||
				symbolicName.equals("com.sun.el.javax.el") || symbolicName.startsWith("jboss-classfilewriter") ||
				symbolicName.startsWith("javax.servlet") || symbolicName.startsWith("javax.validation") ||
				symbolicName.startsWith("org.apache.commons") || symbolicName.startsWith("org.apache.felix") ||
				symbolicName.startsWith("org.apache.geronimo.specs") || symbolicName.startsWith("org.jboss.logging") ||
				symbolicName.startsWith("org.jboss.weld") || symbolicName.startsWith("org.jsoup") ||
				symbolicName.startsWith("org.osgi")) {

			return true;
		}

		return false;
	}

	private static void setServletContextAttribute(Object context, String servletContextAttributeName,
		Object servletContextAttributeValue) {

		boolean isFacesContext = context instanceof FacesContext;

		if (isFacesContext || (context instanceof ExternalContext)) {

			ExternalContext externalContext;

			if (isFacesContext) {

				FacesContext facesContext = (FacesContext) context;
				externalContext = facesContext.getExternalContext();
			}
			else {
				externalContext = (ExternalContext) context;
			}

			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			applicationMap.put(servletContextAttributeName, servletContextAttributeValue);
		}
		else if (context instanceof ServletContext) {

			ServletContext servletContext = (ServletContext) context;
			servletContext.setAttribute(servletContextAttributeName, servletContextAttributeValue);
		}
		else if (context instanceof Map) {
			Map<String, Object> applicationMap = (Map<String, Object>) context;
			applicationMap.put(servletContextAttributeName, servletContextAttributeValue);
		}
		else {
			throwIllegalContextClassException(context);
		}
	}

	private static void throwIllegalContextClassException(Object context) throws IllegalArgumentException {

		String contextClassName = "null";

		if (context != null) {
			contextClassName = context.getClass().getName();
		}

		throw new IllegalArgumentException("context [" + contextClassName + "] is not an instance of " +
			FacesContext.class.getName() + " or " + ExternalContext.class.getName() + " or " +
			ServletContext.class.getName());
	}
}
