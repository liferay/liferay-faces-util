/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.product;

import javax.faces.FacesWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.product.internal.ProductFactoryImpl;


/**
 * @author  Kyle Stiemann
 */
public abstract class ProductFactory implements FacesWrapper<ProductFactory> {

	/**
	 * Returns the thread-safe singleton instance of {@link Product} associated with the specified {@link Product#Name}
	 * from the {@link ProductFactory} found by the {@link FactoryExtensionFinder}. The returned instance is not
	 * guaranteed to be {@link javax.io.Serializable}.
	 *
	 * @param       productName  The name of the product.
	 *
	 * @deprecated  Use {@link #getProductInstance(javax.faces.context.ExternalContext,
	 *              com.liferay.faces.util.product.Product.Name)} instead.
	 */
	@Deprecated
	public static final Product getProduct(Product.Name productName) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ProductFactory productFactory = null;

		if (facesContext != null) {

			ExternalContext externalContext = facesContext.getExternalContext();
			productFactory = (ProductFactory) FactoryExtensionFinder.getFactory(externalContext, ProductFactory.class);
		}

		// If the singleton ProductFactory cannot be obtained from the FactoryExtensionFinder, then create a new
		// ProductFactoryImpl. This allows ProductFactory to remain backwards compatible for use cases where
		// the developer has used ProductFactory before Util's factories are initialized (and even before the
		// FacesContext is initialized).
		if (productFactory == null) {
			productFactory = new ProductFactoryImpl();
		}

		return productFactory.getProductInfo(productName);
	}

	/**
	 * Returns the thread-safe singleton instance of {@link Product} associated with the specified {@link Product#Name}
	 * from the {@link ProductFactory} found by the {@link FactoryExtensionFinder}. The returned instance is not
	 * guaranteed to be {@link javax.io.Serializable}.
	 *
	 * @param  productName  The name of the product.
	 */
	public static Product getProductInstance(ExternalContext externalContext, Product.Name productName) {

		ProductFactory productFactory = (ProductFactory) FactoryExtensionFinder.getFactory(externalContext,
				ProductFactory.class);

		return productFactory.getProductInfo(productName);
	}

	/**
	 * Returns the thread-safe singleton instance of {@link Product} associated with the specified {@link Product#Name}.
	 * The returned instance is not guaranteed to be {@link javax.io.Serializable}.
	 *
	 * @param  productName  The name of the product.
	 */
	public abstract Product getProductInfo(Product.Name productName);

	@Override
	public abstract ProductFactory getWrapped();

	/**
	 * Returns the thread-safe singleton instance of {@link Product} associated with the specified {@link Product#Name}.
	 * The returned instance is not guaranteed to be {@link javax.io.Serializable}.
	 *
	 * @param       productName
	 *
	 * @deprecated  Use {@link #getProductInfo(com.liferay.faces.util.product.Product.Name)} instead.
	 */
	@Deprecated
	public Product getProductImplementation(Product.Name productName) {
		return getProduct(productName);
	}
}
