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

import java.util.Iterator;
import java.util.ServiceLoader;

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

			ServiceLoader<FactoryExtensionFinder> serviceLoader = ServiceLoader.load(FactoryExtensionFinder.class);

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

	public abstract void releaseFactories();
}
