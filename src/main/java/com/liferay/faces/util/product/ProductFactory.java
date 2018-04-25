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
package com.liferay.faces.util.product;

import java.util.Iterator;
import java.util.ServiceLoader;


/**
 * @deprecated  Please use {@link com.liferay.faces.util.product.info.ProductInfoFactory} instead.
 * @author      Kyle Stiemann
 */
@Deprecated
public abstract class ProductFactory {

	// Private Constants
	private static final ProductFactory PRODUCT_FACTORY;

	static {

		ServiceLoader<ProductFactory> serviceLoader = ServiceLoader.load(ProductFactory.class);

		if (serviceLoader != null) {

			Iterator<ProductFactory> iterator = serviceLoader.iterator();

			ProductFactory productFactoryImpl = null;

			while ((productFactoryImpl == null) && iterator.hasNext()) {
				productFactoryImpl = iterator.next();
			}

			if (productFactoryImpl == null) {

				try {

					// FACES-2966 Netbeans auto completion fails for Liferay Faces components
					Class<?> clazz = Class.forName("com.liferay.faces.util.product.internal.ProductFactoryImpl");
					productFactoryImpl = (ProductFactory) clazz.getConstructor().newInstance();
				}
				catch (Exception e) {
					throw new RuntimeException("Unable locate service for " + ProductFactory.class.getName(), e);
				}
			}

			PRODUCT_FACTORY = productFactoryImpl;
		}
		else {
			throw new NullPointerException("Unable to acquire ServiceLoader for " + ProductFactory.class.getName());
		}
	}

	/**
	 * @deprecated  Call {@link ProductUtil#getProduct(javax.faces.context.FacesContext,
	 *              com.liferay.faces.util.product.Product.Name)} instead.
	 *
	 *              <p>Returns the product associated with the specified productId.</p>
	 *
	 * @param       productId  The id of the product.
	 *
	 * @return      The product associated with the specified productId.
	 */
	@Deprecated
	public static final Product getProduct(Product.Name productId) {
		return PRODUCT_FACTORY.getProductImplementation(productId);
	}

	/**
	 * @deprecated  Call {@link ProductUtil#getProduct(javax.faces.context.FacesContext,
	 *              com.liferay.faces.util.product.Product.Name)} instead.
	 */
	@Deprecated
	public abstract Product getProductImplementation(Product.Name product);
}
