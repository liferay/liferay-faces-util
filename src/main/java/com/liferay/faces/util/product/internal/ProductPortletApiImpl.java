/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.product.internal;

/**
 * @author  Neil Griffin
 */
public class ProductPortletApiImpl extends ProductBaseImpl {

	public ProductPortletApiImpl(ProductLiferayPortalImpl productLiferayPortalImpl, ProductPlutoImpl productPlutoImpl) {

		this.title = "Portlet API";

		// Liferay 6.2.0 through 6.2.4 and Pluto 2.0 rely on the Portlet 2.0 API jar which does not contain the
		// correct version information.
		if ((productLiferayPortalImpl.isDetected() && (productLiferayPortalImpl.getMajorVersion() == 6) &&
					(productLiferayPortalImpl.getMinorVersion() == 2) &&
					(productLiferayPortalImpl.getPatchVersion() < 5)) ||
				(productPlutoImpl.isDetected() && (productPlutoImpl.getMajorVersion() == 2) &&
					(productPlutoImpl.getMinorVersion() == 0))) {

			this.detected = true;
			initVersionInfo("2.0");
		}
		else {

			try {

				Class<?> clazz = Class.forName("javax.portlet.PortletContext");
				init(clazz, "Portlet API");
			}
			catch (Exception e) {
				// Ignore -- the Portlet API is likely not present.
			}
		}
	}
}
