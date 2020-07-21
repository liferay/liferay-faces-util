/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.factory.util;

import java.lang.reflect.Method;

import junit.framework.Assert;


/**
 * @author  Kyle Stiemann
 */
public final class BlockServiceLoaderUtil {

	private BlockServiceLoaderUtil() {
		throw new AssertionError();
	}

	public static <T> T callFactoryMethodWithBlockedServiceLoader(Class<?> factoryClazz, Class<?> factoryImplClazz,
		String methodName, Object factoryInput) {

		String factoryClazzSimpleName = factoryClazz.getSimpleName();
		T factoryOutput = null;
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		try {

			ClassLoader classLoader = BlockServiceLoaderUtil.class.getClassLoader();
			String factoryClassName = factoryClazz.getName();
			BlockServiceLoaderClassLoader blockServiceLoaderClassLoader = new BlockServiceLoaderClassLoader(classLoader,
					factoryClassName);
			Thread.currentThread().setContextClassLoader(blockServiceLoaderClassLoader);

			Class<?> factoryClazzWithDisabledServiceLoader = blockServiceLoaderClassLoader.loadClassWithoutParentLoader(
					factoryClazz);

			// Load the factory implementation with blockServiceLoaderClassLoader to ensure that it extends the
			// factoryClazzWithDisabledServiceLoader rather than the factoryClazz (otherwise a ClassCastException will
			// occur).
			blockServiceLoaderClassLoader.loadClassWithoutParentLoader(factoryImplClazz);

			if (factoryInput != null) {

				Method getProductMethod = factoryClazzWithDisabledServiceLoader.getMethod(methodName,
						factoryInput.getClass());
				factoryOutput = (T) getProductMethod.invoke(null, factoryInput);
			}
			else {

				Method getProductMethod = factoryClazzWithDisabledServiceLoader.getMethod(methodName);
				factoryOutput = (T) getProductMethod.invoke(null);
			}

			Assert.assertTrue("Invalid test: no class attempted to access service file \"/META-INF/services/" +
				factoryClassName +
				".class\" via the blockServiceLoaderClassLoader. The file may have been accessed by another classloader.",
				blockServiceLoaderClassLoader.attemptedToAccessServiceFile());
		}
		catch (Throwable t) {
			throw new AssertionError("Unable to load " + factoryClazzSimpleName +
				" service when services directory unavailable.", t);
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}

		return factoryOutput;
	}

	/**
	 * This method loads (or reloads if necessary) the given factory in a separate ClassLoader which disables
	 * ServiceLoader. Since the factories which use ServiceLoader are initialized in a static block, their classes may
	 * need to be reloaded in order to re-run their static initialization. See {@link
	 * LoggerTest#loadLoggerFactoryWhenServicesDirectoryInaccessibleFACES_2966()}, {@link
	 * BlockServiceLoaderClassLoader}, and <a href="https://issues.liferay.com/browse/FACES-2966">FACES-2966 Netbeans
	 * auto completion fails for Liferay Faces components</a> for more details.
	 */
	public static <T> T getFactoryOutputWithBlockedServiceLoader(Class<?> factoryClazz, Class<?> factoryImplClazz,
		Object factoryInput) {

		String factoryClazzSimpleName = factoryClazz.getSimpleName();
		String factoryMethodName = "get" + factoryClazzSimpleName.replace("Factory", "");

		return callFactoryMethodWithBlockedServiceLoader(factoryClazz, factoryImplClazz, factoryMethodName,
				factoryInput);
	}
}
