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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Kyle Stiemann
 */
public abstract class ProductFactory {

	private static final ProductFactory productFactory;

	static {

		ServiceFinder<ProductFactory> serviceLoader = ServiceFinder.load(ProductFactory.class);

		if (serviceLoader != null) {

			Iterator<ProductFactory> iterator = serviceLoader.iterator();

			ProductFactory productFactoryImpl = null;

			while ((productFactoryImpl == null) && iterator.hasNext()) {
				productFactoryImpl = iterator.next();
			}

			if (productFactoryImpl == null) {
				throw new NullPointerException("Unable locate service for " + ProductFactory.class.getName());
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

	public abstract Product getProductImplementation(Product.Name product);

	private static final class ServiceFinder<S> implements Iterable<S> {

		private static final Logger logger = LoggerFactory.getLogger(ServiceFinder.class);

		private Class<S> serviceClass;

		private ServiceFinder(Class<S> serviceClass) {
			this.serviceClass = serviceClass;
		}

		private static <S> ServiceFinder<S> load(Class<S> serviceClass) {
			return new ServiceFinder(serviceClass);
		}

		public Iterator<S> iterator() {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			List<S> instances = new ArrayList<S>();
			Enumeration<URL> resources = null;

			try {
				resources = classLoader.getResources("META-INF/services/" + serviceClass.getName());
			}
			catch (IOException e) {

				logger.error("Unable to obtain resources via path=[META-INF/services/" + serviceClass.getName() + "]:");
				logger.error(e);
			}

			while ((resources != null) && resources.hasMoreElements()) {

				URL resource = resources.nextElement();
				InputStream inputStream = null;
				InputStreamReader inputStreamReader = null;
				BufferedReader bufferedReader = null;
				String className = null;

				try {

					inputStream = resource.openStream();
					inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
					bufferedReader = new BufferedReader(inputStreamReader);
					className = bufferedReader.readLine();

				}
				catch (IOException e) {
					logger.error("Unable to read contents of resource=[" + resource.getPath() + "]");
				}
				finally {

					try {

						if (bufferedReader != null) {
							bufferedReader.close();
						}

						if (inputStreamReader != null) {
							inputStreamReader.close();
						}

						if (inputStream != null) {
							inputStream.close();
						}
					}
					catch (IOException e) {
						// ignore
					}
				}

				if (className != null) {

					try {

						Class<?> clazz = Class.forName(className);
						S instance = (S) clazz.newInstance();
						instances.add(instance);
					}
					catch (Exception e) {

						logger.error("Unable to instantiate class=[" + className + "]:");
						logger.error(e);
					}
				}
			}

			return instances.iterator();
		}
	}
}
