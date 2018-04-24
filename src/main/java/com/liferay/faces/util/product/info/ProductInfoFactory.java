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
package com.liferay.faces.util.product.info;

import javax.faces.FacesWrapper;
import javax.faces.context.ExternalContext;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 */
public abstract class ProductInfoFactory implements FacesWrapper<ProductInfoFactory> {

	public static ProductInfo getProductInfoInstance(ExternalContext externalContext,
		ProductInfo.Name productInfoName) {

		ProductInfoFactory browserSnifferFactory = (ProductInfoFactory) FactoryExtensionFinder.getFactory(
				externalContext, ProductInfoFactory.class);

		return browserSnifferFactory.getProductInfo(productInfoName);
	}

	/**
	 * Returns a thread-safe singleton instance of {@link ProductInfo}.
	 */
	public abstract ProductInfo getProductInfo(ProductInfo.Name productInfoName);

	@Override
	public abstract ProductInfoFactory getWrapped();
}
