/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class FactoryExtensionFinderImpl extends FactoryExtensionFinder {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FactoryExtensionFinderImpl.class);

	// Private Data Members
	private Map<Class<?>, Object> factoryExtensionCache = new HashMap<Class<?>, Object>();

	@Override
	@SuppressWarnings("unchecked")
	public void registerFactory(ConfiguredElement configuredFactoryExtension) {

		if (configuredFactoryExtension != null) {

			String factoryClassFQCN = configuredFactoryExtension.getValue();

			try {

				Class<?> factoryExtensionClass = Class.forName(factoryClassFQCN);
				Class<?> baseFactoryExtensionClass = getBaseFactoryExtensionClass(factoryExtensionClass);
				Object existingFactoryInstance = getFactoryInstance(baseFactoryExtensionClass);
				Object factoryInstance = newFactoryInstance(factoryExtensionClass, baseFactoryExtensionClass,
						existingFactoryInstance);

				factoryExtensionCache.put(baseFactoryExtensionClass, factoryInstance);
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	protected Object newFactoryInstance(Class<?> factoryExtensionClass, Class<?> baseFactoryExtensionClass,
		Object wrappedFactory) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
		IllegalArgumentException, InvocationTargetException {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Object classInstance = null;

		if (classLoader != null) {

			if (factoryExtensionClass != null) {
				Constructor<?> wrapperConstructor = null;
				Constructor<?>[] constructors = factoryExtensionClass.getDeclaredConstructors();

				for (Constructor<?> constructor : constructors) {
					Class<?>[] parameterTypes = constructor.getParameterTypes();

					if ((parameterTypes != null) && (parameterTypes.length == 1) &&
							(parameterTypes[0].getName().equals(baseFactoryExtensionClass.getName()))) {
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

	@SuppressWarnings("unchecked")
	protected Class<?> getBaseFactoryExtensionClass(Class<?> factoryClass) {

		Class<?> baseFactoryExtensionClass = factoryClass;
		Class<?> factorySuperclass = factoryClass.getSuperclass();

		if (!Object.class.getName().equals(factorySuperclass.getName())) {
			baseFactoryExtensionClass = getBaseFactoryExtensionClass(factorySuperclass);
		}

		return baseFactoryExtensionClass;
	}

	@Override
	public Object getFactoryInstance(Class<?> clazz) {
		Object factory = null;

		if (clazz != null) {
			factory = factoryExtensionCache.get(clazz);
		}

		return factory;
	}
}
