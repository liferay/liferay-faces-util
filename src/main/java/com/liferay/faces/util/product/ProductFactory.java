/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.ServiceLoader;


/**
 * @author  Kyle Stiemann
 */
public abstract class ProductFactory {

	private static final ProductFactory productFactory;

	static {

		ServiceLoader<ProductFactory> serviceLoader = ServiceLoader.load(ProductFactory.class);

		if (serviceLoader != null) {

			Iterator<ProductFactory> iterator = serviceLoader.iterator();

			ProductFactory productFactoryImpl = null;

			while ((productFactoryImpl == null) && iterator.hasNext()) {
				productFactoryImpl = iterator.next();
			}

			// productFactoryImpl should never be null in production.
			if (productFactoryImpl == null) {

				InputStream inputStream = null;
				InputStreamReader inputStreamReader = null;
				BufferedReader bufferedReader = null;

				try {

					// FACES-2966 Netbeans auto completion fails for Liferay Faces components
					URL resource = ProductFactory.class.getResource("/META-INF/resources/services/" +
							ProductFactory.class.getName());
					inputStream = resource.openStream();
					inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
					bufferedReader = new BufferedReader(inputStreamReader);

					String className = bufferedReader.readLine();
					Class<?> clazz = Class.forName(className);
					productFactoryImpl = (ProductFactory) clazz.newInstance();
				}
				catch (Exception e) {
					// do nothing
				}
				finally {

					close(inputStream);
					close(inputStreamReader);
					close(bufferedReader);

					if (productFactoryImpl == null) {
						throw new NullPointerException("Unable locate service for " + ProductFactory.class.getName());
					}
				}
			}

			productFactory = productFactoryImpl;
		}
		else {
			throw new NullPointerException("Unable to acquire ServiceLoader for " + ProductFactory.class.getName());
		}
	}

	/**
	 * Returns the product associated with the specified productId.
	 *
	 * @param   productId  The id of the product.
	 *
	 * @return  The product associated with the specified productId.
	 */
	public static final Product getProduct(Product.Name productId) {
		return productFactory.getProductImplementation(productId);
	}

	private static void close(Closeable closable) {

		if (closable != null) {

			try {
				closable.close();
			}
			catch (IOException e) {
				// do nothing
			}
		}
	}

	public abstract Product getProductImplementation(Product.Name product);
}
