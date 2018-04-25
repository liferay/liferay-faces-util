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
package com.liferay.faces.util.factory;

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;

import com.liferay.faces.util.config.ConfiguredElement;


/**
 * This class provides a factory lookup mechanism similar to the {@link javax.faces.FactoryFinder} in the JSF API.
 * Factory instances are stored as attributes in the {@link ExternalContext#getApplicationMap()}.
 *
 * @author  Neil Griffin
 */
public abstract class FactoryExtensionFinder {

	// Private Static Data Members
	private static FactoryExtensionFinder instance;

	/**
	 * @deprecated  Call {@link #getFactory(ExternalContext, Class)} instead.
	 *
	 *              <p>Returns the factory instance associated with the specified factory class from the external
	 *              context associated with the current faces context.</p>
	 *
	 * @param       factoryClass  The factory {@link java.lang.Class}.
	 */
	@Deprecated
	@SuppressWarnings("deprecation")
	public static Object getFactory(Class<?> factoryClass) {
		return getInstance().getFactoryInstance(factoryClass);
	}

	/**
	 * Returns the factory instance associated with the specified factory class from the specified external context.
	 *
	 * @param  externalContext  The external context associated with the current faces context.
	 * @param  factoryClass     The factory {@link java.lang.Class}.
	 */
	public static Object getFactory(ExternalContext externalContext, Class<?> factoryClass) {
		return getInstance().getFactoryInstance(externalContext, factoryClass);
	}

	/**
	 * Returns the thread-safe Singleton instance of the factory extension finder.
	 *
	 * @throws  FacesException  When the factory extension finder cannot be discovered.
	 */
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

	/**
	 * @deprecated  Call {@link #getFactoryInstance(ExternalContext, Class)} instead.
	 *
	 *              <p>Returns the factory instance associated with the specified factory class from the external
	 *              context associated with the current faces context.</p>
	 *
	 * @param       factoryClass  The factory {@link java.lang.Class}.
	 */
	@Deprecated
	public abstract Object getFactoryInstance(Class<?> factoryClass);

	/**
	 * Returns the factory instance associated with the specified factory class from the specified external context.
	 *
	 * @param  externalContext  The external context associated with the current faces context.
	 * @param  factoryClass     The factory {@link java.lang.Class}.
	 */
	public abstract Object getFactoryInstance(ExternalContext externalContext, Class<?> factoryClass);

	/**
	 * @deprecated  Call {@link #registerFactory(ExternalContext, ConfiguredElement)} instead.
	 *
	 *              <p>Registers the specified configured factory extension by storing it as an attribute in the {@link
	 *              ExternalContext#getApplicationMap()} associated with the current faces context. Since this method is
	 *              designed to be called during application initialization, it is not guaranteed to be thread-safe.</p>
	 *
	 * @param       configuredFactoryExtension  The configured factory extension.
	 */
	@Deprecated
	public abstract void registerFactory(ConfiguredElement configuredFactoryExtension);

	/**
	 * Registers the specified configured factory extension by storing it as an attribute in the specified {@link
	 * ExternalContext#getApplicationMap()}. Since this method is designed to be called during application
	 * initialization, it is not guaranteed to be thread-safe.
	 *
	 * @param  externalContext             The external context associated with the current faces context.
	 * @param  configuredFactoryExtension  The configured factory extension.
	 */
	public abstract void registerFactory(ExternalContext externalContext, ConfiguredElement configuredFactoryExtension);

	/**
	 * Releases all of the factories that were registered via the {@link #registerFactory(ExternalContext,
	 * ConfiguredElement)} method. It is designed to be called when a webapp context is destroyed.
	 */
	public abstract void releaseFactories(ExternalContext externalContext);
}
