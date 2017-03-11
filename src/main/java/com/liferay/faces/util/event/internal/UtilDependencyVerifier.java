/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.event.internal;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductFactory;


/**
 * @author  Neil Griffin
 */
public class UtilDependencyVerifier {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UtilDependencyVerifier.class);

	public static void verify() {

		Product jsf = ProductFactory.getProduct(Product.Name.JSF);
		int jsfMajorVersion = jsf.getMajorVersion();
		int jsfMinorVersion = jsf.getMinorVersion();

		if (!((jsfMajorVersion >= 2) && (jsfMinorVersion >= 2))) {

			Package utilPackage = UtilDependencyVerifier.class.getPackage();
			String implementationTitle = utilPackage.getImplementationTitle();
			String implementationVersion = utilPackage.getImplementationVersion();

			logger.error("{0} {1} is designed to be used with JSF 2.2+ but detected {2}.{3}", implementationTitle,
				implementationVersion, jsfMajorVersion, jsfMinorVersion);
		}
	}
}
