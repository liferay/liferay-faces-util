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

import java.util.Dictionary;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Kyle Stiemann
 */
public abstract class FacesBundlesHandlerBase<ReturnValueType> {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(FacesBundlesHandlerBase.class);

	// Private Constants
	private static final boolean OSGI_ENVIRONMENT_DETECTED;

	static {

		boolean frameworkUtilDetected = false;

		try {

			Class.forName("org.osgi.framework.FrameworkUtil");
			frameworkUtilDetected = true;
		}
		catch (ClassNotFoundException e) {
			// Do nothing.
		}
		catch (NoClassDefFoundError e) {
			// Do nothing.
		}
		catch (Throwable t) {

			logger.error("An unexpected error occurred when attempting to detect OSGi:");
			logger.error(t);
		}

		boolean osgiEnvironmentDetected = false;

		if (frameworkUtilDetected) {

			Bundle currentBundle = FrameworkUtil.getBundle(FacesBundlesHandlerBase.class);

			if (currentBundle != null) {
				osgiEnvironmentDetected = true;
			}
		}

		OSGI_ENVIRONMENT_DETECTED = osgiEnvironmentDetected;
	}

	public static boolean isCurrentWarThinWab() {
		return OSGI_ENVIRONMENT_DETECTED && !isWab(getCurrentBundle());
	}

	protected static boolean isWab(Bundle bundle) {

		String webContextPathHeader = null;

		if (bundle != null) {

			Dictionary<String, String> headers = bundle.getHeaders();
			webContextPathHeader = headers.get("Web-ContextPath");
		}

		return webContextPathHeader != null;
	}

	private static Bundle getCurrentBundle() {
		return FrameworkUtil.getBundle(FacesBundlesHandlerBase.class);
	}

	private static Bundle getCurrentFacesWab(Object context) {

		BundleContext bundleContext = (BundleContext) getServletContextAttribute(context, "osgi-bundlecontext");
		Bundle bundle;

		try {
			bundle = bundleContext.getBundle();
		}
		catch (IllegalStateException e) {
			bundle = null;
		}

		return bundle;
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
		else {

			String contextClassName = "null";

			if (context != null) {
				contextClassName = context.getClass().getName();
			}

			throw new IllegalArgumentException("context [" + contextClassName + "] is not an instance of " +
				FacesContext.class.getName() + " or " + ExternalContext.class.getName() + " or " +
				ServletContext.class.getName());
		}

		return servletContextAttributeValue;
	}

	public final ReturnValueType handleFacesBundles(Object context) {

		ReturnValueType returnValueObject = getInitialReturnValueObject();
		ReturnValueReference<ReturnValueType> returnValueReference = new ReturnValueReference<ReturnValueType>(
				returnValueObject);

		if (OSGI_ENVIRONMENT_DETECTED) {

			Bundle currentFacesWab = getCurrentFacesWab(context);
			handleCurrentFacesWab(currentFacesWab, returnValueReference);

			Bundle currentBundle = getCurrentBundle();

			if (currentFacesWab.getBundleId() != currentBundle.getBundleId()) {
				handleFacesBundle(currentBundle, returnValueReference);
			}
		}

		return returnValueReference.get();
	}

	protected abstract ReturnValueType getInitialReturnValueObject();

	protected abstract void handleFacesBundle(Bundle bundle,
		ReturnValueReference<ReturnValueType> returnValueReference);

	protected void handleCurrentFacesWab(Bundle currentFacesWab,
		ReturnValueReference<ReturnValueType> returnValueReference) {
		handleFacesBundle(currentFacesWab, returnValueReference);
	}

	protected static final class ReturnValueReference<ReturnValueType> {

		// Private Data Members
		private ReturnValueType returnValueObject;

		public ReturnValueReference(ReturnValueType returnValueObject) {
			this.returnValueObject = returnValueObject;
		}

		public ReturnValueType get() {
			return returnValueObject;
		}

		public boolean isEmpty() {
			return returnValueObject == null;
		}

		public void set(ReturnValueType returnValueObject) {
			this.returnValueObject = returnValueObject;
		}
	}
}
