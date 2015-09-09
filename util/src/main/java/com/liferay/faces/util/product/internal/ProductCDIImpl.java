/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.product.ProductConstants;


/**
 * @author  Neil Griffin
 */
public class ProductCDIImpl extends ProductBaseImpl {

	public ProductCDIImpl() {

		try {
			Class<?> cdiImplClass;

			try {
				cdiImplClass = Class.forName("org.jboss.weld.util.Types");
				this.title = ProductConstants.WELD;
				init(cdiImplClass, "Weld Servlet (Uber Jar)");

				if (!isDetected()) {
					init(cdiImplClass, "Weld Implementation");
				}

				if (isDetected()) {

					Package pkg = cdiImplClass.getPackage();

					if ((pkg != null) && (pkg.getSpecificationVersion() != null)) {

						// The precise version of Weld is found in the Specification-Version rather than the
						// Implementation-Version in META-INF/MANIFEST.MF
						initVersionInfo(pkg.getSpecificationVersion());
					}
				}
			}
			catch (ClassNotFoundException e) {
				cdiImplClass = Class.forName("org.apache.webbeans.util.WebBeansConstants");
				this.title = ProductConstants.OPEN_WEB_BEANS;
				init(cdiImplClass, "OpenWebBeans Core");
			}
		}
		catch (Exception e) {
			// Ignore -- CDI implementation is likely not present.
		}
	}
}
