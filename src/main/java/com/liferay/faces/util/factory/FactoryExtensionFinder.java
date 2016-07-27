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
package com.liferay.faces.util.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;

import com.liferay.faces.util.config.ConfiguredElement;


/**
 * @author  Neil Griffin
 */
public abstract class FactoryExtensionFinder {

	// Private Static Data Members
	private static FactoryExtensionFinder instance;

	public static Object getFactory(Class<?> clazz) {
		return getInstance().getFactoryInstance(clazz);
	}

	public static FactoryExtensionFinder getInstance() throws FacesException {

		// This method of lazy-initialization is thread-safe because the FactoryExtensionFinder is first called during
		// ApplicationStartupListener.processSystemEvent(). ApplicationStartupListener.processSystemEvent() occurs
		// before multiple threads have the opportunity to concurrently access the FactoryExtensionFinder.
		if (instance == null) {

			ServiceFinder<FactoryExtensionFinder> serviceLoader = ServiceFinder.load(FactoryExtensionFinder.class);

			if (serviceLoader != null) {

				Iterator<FactoryExtensionFinder> iterator = serviceLoader.iterator();

				while ((instance == null) && iterator.hasNext()) {
					instance = iterator.next();
				}

				if (instance == null) {
					throw new FacesException("Unable locate service for " + FactoryExtensionFinder.class.getName());
				}
			}
			else {
				throw new FacesException("Unable to acquire ServiceLoader for " +
					FactoryExtensionFinder.class.getName());
			}
		}

		return instance;
	}

	public abstract Object getFactoryInstance(Class<?> clazz);

	public abstract void registerFactory(ConfiguredElement configuredFactoryExtension);

	private static final class ServiceFinder<S> implements Iterable<S> {

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

				System.err.println("Unable to obtain resources via path=[META-INF/services/" + serviceClass.getName() +
					"]:");
				System.err.println(e);
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
					System.err.println("Unable to read contents of resource=[" + resource.getPath() + "]");
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

						System.err.println("Unable to instantiate class=[" + className + "]:");
						System.err.println(e);
					}
				}
			}

			return instances.iterator();
		}
	}
}
