/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.product.Product;


/**
 * @author  Neil Griffin
 */
public class ProductJSFApiImpl extends ProductBase {

	public ProductJSFApiImpl(Product mojarra, Product myFaces) {
		super(newInstance(mojarra, myFaces));
	}

	private static ProductInfo newInstance(Product mojarra, Product myFaces) {

		Product jsfRuntime;

		if (myFaces.isDetected()) {
			jsfRuntime = myFaces;
		}
		else {
			jsfRuntime = mojarra;
		}

		boolean detected = jsfRuntime.isDetected();
		String version = jsfRuntime.getMajorVersion() + "." + jsfRuntime.getMinorVersion();

		return new ProductInfo(detected, "JSF API", version);
	}
}
