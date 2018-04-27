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

import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductWrapper;


/**
 * @author  Kyle Stiemann
 */
public class ProductSpecImpl extends ProductWrapper {

	// Private Data Members
	private Product wrappedProduct;

	public ProductSpecImpl(String fallbackSpecTitle, Product... specImplProducts) {

		for (int i = 0; i < specImplProducts.length; i++) {

			Product specImplProduct = specImplProducts[i];

			if (specImplProduct.isDetected() || (i == (specImplProducts.length - 1))) {

				this.wrappedProduct = specImplProduct;

				break;
			}
		}

		if (this.wrappedProduct == null) {

			ProductBaseImpl productBaseImpl = new ProductBaseImpl(new ProductInfo(false, fallbackSpecTitle));
			this.wrappedProduct = productBaseImpl;
		}
	}

	@Override
	public Product getWrapped() {
		return wrappedProduct;
	}
}
