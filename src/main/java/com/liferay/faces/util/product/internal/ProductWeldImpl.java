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
package com.liferay.faces.util.product.internal;

/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class ProductWeldImpl extends ProductBase {

	public ProductWeldImpl() {
		super(newInstance());
	}

	private static ProductInfo newInstance() {

		String title = "Weld";
		ProductInfo productInfo = null;

		try {

			Class<?> cdiImplClass = Class.forName("org.jboss.weld.util.Types");
			productInfo = ProductInfo.newInstance("Weld Servlet (Uber Jar)", cdiImplClass);

			if (!productInfo.detected) {
				productInfo = new ProductInfo(false, "Weld Implementation", productInfo.version);
			}

			if (productInfo.detected) {

				Package pkg = cdiImplClass.getPackage();

				if ((pkg != null) && (pkg.getSpecificationVersion() != null)) {

					// The precise version of Weld is found in the Specification-Version rather than the
					// Implementation-Version in META-INF/MANIFEST.MF
					productInfo = new ProductInfo(productInfo.title, pkg.getSpecificationVersion());
				}
			}
		}
		catch (Exception e) {
			// Ignore -- Weld is likely not present.
		}

		if (productInfo == null) {
			productInfo = new ProductInfo(false, title);
		}

		return productInfo;
	}
}
