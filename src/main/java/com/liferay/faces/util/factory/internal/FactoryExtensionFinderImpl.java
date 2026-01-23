/**
 * Copyright (c) 2000-2025 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.factory.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.internal.TCCLUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

/**
 * @author Neil Griffin
 */
public class FactoryExtensionFinderImpl extends FactoryExtensionFinder {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FactoryExtensionFinderImpl.class);

	// Private Constants
	private static final String FACTORY_EXTENSION_CACHE = FactoryExtensionFinderImpl.class.getName();

	@Override
	@Deprecated
	public Object getFactoryInstance(Class<?> factoryClass) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		return getFactoryInstance(externalContext, factoryClass);
	}

	@Override
	public Object getFactoryInstance(ExternalContext externalContext, Class<?> factoryClass) {
		Object factory = null;

		if (factoryClass != null) {
			Map<Class<?>, Object> factoryExtensionCache = getFactoryExtensionCache(externalContext);
			factory = factoryExtensionCache.get(factoryClass);
		}

		return factory;
	}

	@Override
	public void registerFactory(ConfiguredElement configuredFactoryExtension) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		registerFactory(externalContext, configuredFactoryExtension);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void registerFactory(ExternalContext externalContext, ConfiguredElement configuredFactoryExtension) {

		if (configuredFactoryExtension != null) {

			String factoryClassFQCN = configuredFactoryExtension.getValue();

			try {

				ClassLoader classLoader = TCCLUtil.getThreadContextClassLoaderOrDefault(getClass());
				Class<?> factoryExtensionClass = classLoader.loadClass(factoryClassFQCN);
				Class<?> baseFactoryExtensionClass = getBaseFactoryExtensionClass(factoryExtensionClass);
				Object existingFactoryInstance = getFactoryInstance(externalContext, baseFactoryExtensionClass);
				Object factoryInstance = newFactoryInstance(classLoader, factoryExtensionClass,
					baseFactoryExtensionClass, existingFactoryInstance);

				Map<Class<?>, Object> factoryExtensionCache = getFactoryExtensionCache(externalContext);
				factoryExtensionCache.put(baseFactoryExtensionClass, factoryInstance);
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void releaseFactories(ExternalContext externalContext) {

		Map<String, Object> applicationMap = externalContext.getApplicationMap();
		Map<Class<?>, Object> factoryExtensionCache =
			(Map<Class<?>, Object>) applicationMap.get(FACTORY_EXTENSION_CACHE);

		if (factoryExtensionCache != null) {
			factoryExtensionCache.clear();
			applicationMap.remove(FACTORY_EXTENSION_CACHE);
		}
	}

	@SuppressWarnings("unchecked")
	private Class<?> getBaseFactoryExtensionClass(Class<?> factoryClass) {

		Class<?> baseFactoryExtensionClass = factoryClass;
		Class<?> factorySuperclass = factoryClass.getSuperclass();

		if (!Object.class.getName().equals(factorySuperclass.getName())) {
			baseFactoryExtensionClass = getBaseFactoryExtensionClass(factorySuperclass);
		}

		return baseFactoryExtensionClass;
	}

	@SuppressWarnings("unchecked")
	private Map<Class<?>, Object> getFactoryExtensionCache(ExternalContext externalContext) {

		Map<String, Object> applicationMap = externalContext.getApplicationMap();
		Map<Class<?>, Object> factoryExtensionCache =
			(Map<Class<?>, Object>) applicationMap.get(FACTORY_EXTENSION_CACHE);

		if (factoryExtensionCache == null) {
			factoryExtensionCache = new HashMap<Class<?>, Object>();
			applicationMap.put(FACTORY_EXTENSION_CACHE, factoryExtensionCache);
		}

		return factoryExtensionCache;
	}

	private Object newFactoryInstance(ClassLoader classLoader, Class<?> factoryExtensionClass,
		Class<?> baseFactoryExtensionClass, Object wrappedFactory) throws ClassNotFoundException,
		InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Object classInstance = null;

		if (classLoader != null) {

			if (factoryExtensionClass != null) {
				Constructor<?> wrapperConstructor = null;
				Constructor<?>[] constructors = factoryExtensionClass.getDeclaredConstructors();

				for (Constructor<?> constructor : constructors) {
					Class<?>[] parameterTypes = constructor.getParameterTypes();

					if ((parameterTypes != null) && (parameterTypes.length == 1)
						&& (parameterTypes[0].getName().equals(baseFactoryExtensionClass.getName()))) {
						wrapperConstructor = constructor;
					}
				}

				if (wrapperConstructor == null) {

					logger.debug("Creating instance with zero-arg constructor since wrapperConstructor=null");

					try {
						classInstance = factoryExtensionClass.newInstance();
					}
					catch (InstantiationException e) {
						logger.error("Unable to create an instance of [{0}] with zero-arg constructor.",
							factoryExtensionClass.getName());
						throw e;
					}
					catch (IllegalAccessException e) {
						logger.error("Unable to create an instance of [{0}] with zero-arg constructor.",
							factoryExtensionClass.getName());
						throw e;
					}
				}
				else {
					logger.debug("Creating instance with one-arg constructor since wrapperConstructor=[{0}]",
						wrapperConstructor);

					try {
						classInstance = wrapperConstructor.newInstance(wrappedFactory);
					}
					catch (InstantiationException e) {
						logger.error("Unable to create an instance of [{0}] with one-arg constructor.",
							factoryExtensionClass.getName());
						throw e;
					}
					catch (IllegalAccessException e) {
						logger.error("Unable to create an instance of [{0}] with one-arg constructor.",
							factoryExtensionClass.getName());
						throw e;
					}
					catch (IllegalArgumentException e) {
						logger.error("Unable to create an instance of [{0}] with one-arg constructor.",
							factoryExtensionClass.getName());
						throw e;
					}
					catch (InvocationTargetException e) {
						logger.error("Unable to create an instance of [{0}] with one-arg constructor.",
							factoryExtensionClass.getName());
						throw e;
					}
				}
			}
		}

		return classInstance;
	}
}
