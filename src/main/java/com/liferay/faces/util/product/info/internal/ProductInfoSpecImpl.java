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

import com.liferay.faces.util.product.info.ProductInfo;
import com.liferay.faces.util.product.info.ProductInfoWrapper;


/**
 * @author  Kyle Stiemann
 */
public class ProductInfoSpecImpl extends ProductInfoWrapper {

	// Private Data Members
	private ProductInfo wrappedProductInfo;

	public ProductInfoSpecImpl(String fallbackSpecTitle, ProductInfo... specImplProductInfos) {

		for (int i = 0; i < specImplProductInfos.length; i++) {

			ProductInfo specImplProductInfo = specImplProductInfos[i];

			if (specImplProductInfo.isDetected() || (i == (specImplProductInfos.length - 1))) {

				this.wrappedProductInfo = specImplProductInfo;

				break;
			}
		}

		if (this.wrappedProductInfo == null) {

			ProductInfoBaseImpl productInfoBaseImpl = new ProductInfoBaseImpl();
			productInfoBaseImpl.title = fallbackSpecTitle;
			this.wrappedProductInfo = productInfoBaseImpl;
		}
	}

	@Override
	public ProductInfo getWrapped() {
		return wrappedProductInfo;
	}
}
