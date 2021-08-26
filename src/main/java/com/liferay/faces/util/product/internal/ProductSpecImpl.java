/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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
 * @author  Kyle Stiemann
 */
public class ProductSpecImpl extends ProductBase {

	public ProductSpecImpl(String fallbackSpecTitle, ProductBase... specImplProducts) {
		super(getProductInfo(fallbackSpecTitle, specImplProducts));
	}

	private static ProductInfo getProductInfo(String fallbackSpecTitle, ProductBase... specImplProducts) {

		ProductInfo productInfo = null;

		for (int i = 0; i < specImplProducts.length; i++) {

			ProductBase specImplProduct = specImplProducts[i];

			if (specImplProduct.isDetected() || (i == (specImplProducts.length - 1))) {

				productInfo = specImplProduct.getProductInfo();

				break;
			}
		}

		if (productInfo == null) {
			productInfo = new ProductInfo(false, fallbackSpecTitle);
		}

		return productInfo;
	}
}
