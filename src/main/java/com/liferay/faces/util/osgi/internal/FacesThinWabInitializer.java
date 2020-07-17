/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.osgi.framework.Bundle;

import com.liferay.faces.util.config.WebConfig;
import com.liferay.faces.util.config.WebConfigParam;
import com.liferay.faces.util.config.internal.ResourceReader;
import com.liferay.faces.util.config.internal.ResourceReaderImpl;
import com.liferay.faces.util.config.internal.WebConfigScanner;
import com.liferay.faces.util.config.internal.WebConfigScannerImpl;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.internal.TCCLUtil;
import com.liferay.faces.util.xml.ConcurrentSAXParserFactory;


/**
 * @author  Kyle Stiemann
 */
public final class FacesThinWabInitializer implements ServletContainerInitializer {

	private static boolean getBooleanValue(ServletContext servletContext, String name, String alternateName,
		boolean defaultBooleanValue) {

		boolean booleanValue = defaultBooleanValue;

		String configuredValue = getConfiguredValue(servletContext, name, alternateName);

		if (configuredValue != null) {
			booleanValue = BooleanHelper.isTrueToken(configuredValue);
		}

		return booleanValue;
	}

	private static String getConfiguredValue(ServletContext servletContext, String name, String alternateName) {

		String configuredValue = servletContext.getInitParameter(name);

		if ((configuredValue == null) && (alternateName != null)) {
			configuredValue = servletContext.getInitParameter(alternateName);
		}

		return configuredValue;
	}

	@Override
	public void onStartup(Set<Class<?>> classes, ServletContext servletContext) throws ServletException {

		// Obtain the current ClassLoader
		ClassLoader classLoader = TCCLUtil.getThreadContextClassLoaderOrDefault(getClass());

		// Obtain a ResourceReader
		ResourceReader resourceReader = new ResourceReaderImpl(servletContext);

		// Obtain a SAX Parser Factory.
		SAXParserFactory saxParserFactory = ConcurrentSAXParserFactory.newInstance();
		saxParserFactory.setValidating(false);
		saxParserFactory.setNamespaceAware(true);

		try {

			// Obtain a SAX Parser from the factory.
			SAXParser saxParser = saxParserFactory.newSAXParser();
			String resolveXMLEntitiesName = WebConfigParam.ResolveXMLEntities.getName();
			String resolveXMLEntitiesAlternateName = WebConfigParam.ResolveXMLEntities.getAlternateName();
			boolean resolveEntities = getBooleanValue(servletContext, resolveXMLEntitiesName,
					resolveXMLEntitiesAlternateName, false);

			// Scan all the web-fragment.xml descriptors in bundles that this bundle depends on.
			WebConfigScanner webConfigScanner = new WebConfigScannerImpl(classLoader, resourceReader, saxParser,
					resolveEntities);
			FacesBundlesHandlerBase<List<URL>> facesBundlesHandlerGetWebFragmentImpl =
				new FacesBundlesHandlerGetWebFragmentImpl();
			List<URL> webFragmentURLs = facesBundlesHandlerGetWebFragmentImpl.handleFacesBundles(servletContext, true);
			WebConfig webFragmentConfig = webConfigScanner.scanWebFragments(Collections.enumeration(webFragmentURLs));
			Map<String, String> webFragmentContextParams = webFragmentConfig.getConfiguredContextParams();
			Set<Map.Entry<String, String>> entrySet = webFragmentContextParams.entrySet();

			if (!webFragmentContextParams.isEmpty()) {

				List<String> initParameterNames = Collections.list(servletContext.getInitParameterNames());

				for (Map.Entry<String, String> webFragmentContextParam : entrySet) {

					String name = webFragmentContextParam.getKey();

					if (!initParameterNames.contains(name)) {

						String value = webFragmentContextParam.getValue();
						servletContext.setInitParameter(name, value);
					}
				}
			}
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private static final class FacesBundlesHandlerGetWebFragmentImpl extends FacesBundlesHandlerBase<List<URL>> {

		@Override
		protected List<URL> getInitialReturnValueObject() {
			return new ArrayList<URL>();
		}

		@Override
		protected void handleCurrentFacesWab(Bundle currentFacesWab,
			ReturnValueReference<List<URL>> returnValueReference, boolean recurse) {

			super.handleCurrentFacesWab(currentFacesWab, returnValueReference, recurse);

			URL webConfigURL = currentFacesWab.getEntry("WEB-INF/web.xml");

			if (webConfigURL != null) {
				returnValueReference.get().add(webConfigURL);
			}
		}

		@Override
		protected void handleFacesBundle(Bundle bundle, ReturnValueReference<List<URL>> returnValueReference,
			boolean recurse) {
			collect(bundle, "META-INF", "*.web-fragment.xml", returnValueReference, recurse);
			collect(bundle, "META-INF", "web-fragment.xml", returnValueReference, recurse);
		}

		private void collect(Bundle bundle, String directory, String pattern,
			ReturnValueReference<List<URL>> returnValueReference, boolean recurse) {

			Enumeration<URL> webFragmentURLs = bundle.findEntries(directory, pattern, recurse);

			while ((webFragmentURLs != null) && webFragmentURLs.hasMoreElements()) {
				returnValueReference.get().add(webFragmentURLs.nextElement());
			}
		}
	}
}
