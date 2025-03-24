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
package com.liferay.faces.util.event.internal;

import javax.faces.context.ExternalContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductFactory;

/**
 * @author Neil Griffin
 */
public class UtilDependencyVerifier {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UtilDependencyVerifier.class);

	public static void verify(ExternalContext initExternalContext) {

		final Product JSF = ProductFactory.getProductInstance(initExternalContext, Product.Name.JSF);
		final int JSF_MAJOR_VERSION = JSF.getMajorVersion();
		final int JSF_MINOR_VERSION = JSF.getMinorVersion();
		Package utilPackage = UtilDependencyVerifier.class.getPackage();
		String implementationTitle = utilPackage.getImplementationTitle();
		String implementationVersion = utilPackage.getImplementationVersion();

		if (!((JSF_MAJOR_VERSION > 2) || ((JSF_MAJOR_VERSION == 2) && (JSF_MINOR_VERSION >= 2)))) {
			logger.error("{0} {1} is designed to be used with JSF 2.2+ but detected {2}.{3}", implementationTitle,
				implementationVersion, JSF_MAJOR_VERSION, JSF_MINOR_VERSION);
		}
	}
}
