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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductFactory;
import com.liferay.faces.util.product.info.ProductInfo;
import com.liferay.faces.util.product.info.ProductInfoFactory;
import com.liferay.faces.util.product.info.internal.ProductInfoFactoryImpl;


/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class ProductFactoryImpl extends ProductFactory {

	@Override
	public Product getProductImplementation(Product.Name productName) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		String productNameString = productName.toString();
		ProductInfo.Name productInfoName = ProductInfo.Name.valueOf(productNameString);
		ProductInfoFactory productInfoFactory = null;

		if (facesContext != null) {

			ExternalContext externalContext = facesContext.getExternalContext();
			productInfoFactory = (ProductInfoFactory) FactoryExtensionFinder.getFactory(externalContext,
					ProductInfoFactory.class);
		}

		// If the singleton ProductInfoFactory cannot be obtained from the FactoryExtensionFinder, then create a new
		// ProductInfoFactoryImpl. This allows ProductFactory to remain backwards compatible for use cases where
		// the developer has used ProductFactory before Util's factories are initialized (and even before the
		// FacesContext is initialized).
		if (productInfoFactory == null) {
			productInfoFactory = new ProductInfoFactoryImpl();
		}

		ProductInfo productInfo = productInfoFactory.getProductInfo(productInfoName);

		return new ProductAdapterImpl(productInfo);
	}
}
