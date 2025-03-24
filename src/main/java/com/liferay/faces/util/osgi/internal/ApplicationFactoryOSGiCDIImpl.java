/**
 * Copyright (c) 2000-2025 Liferay, Inc. All rights reserved.
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

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;

import org.osgi.framework.Bundle;

/**
 * @author Neil Griffin
 */
public class ApplicationFactoryOSGiCDIImpl extends ApplicationFactory {

	private Application application;
	private boolean requiresCapabilityCDI;
	private ApplicationFactory wrappedApplicationFactory;

	public ApplicationFactoryOSGiCDIImpl(ApplicationFactory wrappedApplicationFactory) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (OSGiEnvironment.isApiDetected()) {
			Bundle currentFacesWab = FacesBundleUtil.getCurrentFacesWab(facesContext);

			if (currentFacesWab != null) {
				Dictionary<String, String> headers = currentFacesWab.getHeaders();

				if (headers != null) {
					String requireCapabilityHeader = headers.get("Require-Capability");

					if (requireCapabilityHeader != null) {

						if (requireCapabilityHeader.contains("osgi.cdi.extension")) {
							requiresCapabilityCDI = true;
						}
					}
				}
			}
		}

		this.wrappedApplicationFactory = wrappedApplicationFactory;
	}

	@Override
	public Application getApplication() {

		if (application == null) {

			if (requiresCapabilityCDI) {
				application = new ApplicationOSGiCDIImpl(wrappedApplicationFactory.getApplication());
			}
			else {
				application = wrappedApplicationFactory.getApplication();
			}
		}

		return application;
	}

	@Override
	public void setApplication(Application application) {
		this.application = application;
		wrappedApplicationFactory.setApplication(application);
	}
}
