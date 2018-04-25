/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.product.info.internal;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.info.ProductInfo;


/**
 * @author  Neil Griffin
 */
public class ProductInfoPortletApiImpl extends ProductInfoBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ProductInfoPortletApiImpl.class);

	public ProductInfoPortletApiImpl(ProductInfo liferayPortal, ProductInfo pluto) {

		this.title = "Portlet API";

		int liferayPortalMajorVersion = liferayPortal.getMajorVersion();
		int liferayPortalMinorVersion = liferayPortal.getMinorVersion();
		boolean liferayPortalDetected = liferayPortal.isDetected();

		// Liferay 6.2.0 through 6.2.4 and Pluto 2.0 rely on the Portlet 2.0 API jar which does not contain the
		// correct version information.
		if ((liferayPortalDetected && (liferayPortalMajorVersion == 6) && (liferayPortalMinorVersion == 2) &&
					(liferayPortal.getPatchVersion() < 5)) ||
				(pluto.isDetected() && (pluto.getMajorVersion() == 2) && (pluto.getMinorVersion() == 0))) {

			this.detected = true;
			initVersionInfo("2.0");
		}
		else {

			try {

				Class<?> clazz = Class.forName("javax.portlet.PortletContext");
				init(clazz, "Portlet API", null, false);

				if (liferayPortalDetected) {

					boolean liferay_7_0_detected = (liferayPortalMajorVersion == 7) && (liferayPortalMinorVersion == 0);

					if (liferay_7_0_detected || (liferayPortalMajorVersion == 6)) {

						if (this.majorVersion == 0) {
							initVersionInfo("2.0");
						}
						else if (liferay_7_0_detected && (this.majorVersion == 1)) {

							// FACES-3238 Util's PORTLET_API incorrectly reports Portlet 2.1 API version as 1.0 when
							// running in Liferay 7.0 GA5 causing BridgeDependencyVerifier error message
							initVersionInfo("2.1.0");
						}
					}
					else {
						initVersionInfo("3.0");
					}
				}

				if (this.majorVersion == 0) {
					logger.warn("Unable to obtain version information for {0}.", this.title);
				}
			}
			catch (Exception e) {
				// Ignore -- the Portlet API is likely not present.
			}
		}

		initStringValue(version);
	}
}
