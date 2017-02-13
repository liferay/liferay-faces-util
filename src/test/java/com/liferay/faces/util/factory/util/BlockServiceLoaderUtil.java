/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import com.liferay.faces.util.product.ProductTest;


/**
 * @author  Kyle Stiemann
 */
public final class BlockServiceLoaderUtil {

	private BlockServiceLoaderUtil() {
		throw new AssertionError();
	}

	/**
	 * This method loads (or reloads if necessary) the given factory in a separate ClassLoader which disables
	 * ServiceLoader. Since the factories which use ServiceLoader are initialized in a static block, their classes may
	 * need to be reloaded in order to re-run their static initialization. See {@link
	 * ProductTest#loadProductFactoryWhenServicesDirectoryInaccessibleFACES_2966()}, {@link
	 * LoggerTest#loadLoggerFactoryWhenServicesDirectoryInaccessibleFACES_2966()}, {@link
	 * BlockServiceLoaderClassLoader}, and <a href="https://issues.liferay.com/browse/FACES-2966">FACES-2966 Netbeans
	 * auto completion fails for Liferay Faces components</a> for more details.
	 */
	public static <T> T getFactoryOutputWithBlockedServiceLoader(Object factoryInput, Class<?> factoryClazz,
		Class<?> factoryImplClazz) {

		String factoryClazzSimpleName = factoryClazz.getSimpleName();
		InputStream factoryClassFileInputStream = null;
		InputStream factoryImplClassFileInputStream = null;
		T factoryOutput = null;
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		try {

			ClassLoader classLoader = BlockServiceLoaderUtil.class.getClassLoader();
			BlockServiceLoaderClassLoader blockServiceLoaderClassLoader = new BlockServiceLoaderClassLoader(
					classLoader);
			Thread.currentThread().setContextClassLoader(blockServiceLoaderClassLoader);

			String factoryClassFileName = factoryClazzSimpleName + ".class";
			factoryClassFileInputStream = factoryClazz.getResourceAsStream(factoryClassFileName);

			// Load or reload the factoryClazz with blockServiceLoaderClassLoader to ensure that ServiceLoader cannot
			// be used to initialize the ProductFactory.
			Class<?> factoryClazzWithDisabledServiceLoader = blockServiceLoaderClassLoader.loadClass(
					factoryClazz.getName(), factoryClassFileInputStream);

			// Load the factory implementation with blockServiceLoaderClassLoader to ensure that it extends the
			// factoryClazzWithDisabledServiceLoader rather than the factoryClazz (otherwise a ClassCastException will
			// occur).
			String factoryImplClassFileName = factoryImplClazz.getSimpleName() + ".class";
			factoryImplClassFileInputStream = factoryImplClazz.getResourceAsStream(factoryImplClassFileName);
			blockServiceLoaderClassLoader.loadClass(factoryImplClazz.getName(), factoryImplClassFileInputStream);

			String factoryMethodName = "get" + factoryClazzSimpleName.replace("Factory", "");
			Method getProductMethod = factoryClazzWithDisabledServiceLoader.getMethod(factoryMethodName,
					factoryInput.getClass());

			@SuppressWarnings("unchecked")
			T t = (T) getProductMethod.invoke(null, factoryInput);
			factoryOutput = t;
		}
		catch (Throwable t) {
			throw new AssertionError("Unable to load " + factoryClazzSimpleName +
				" service when services directory unavailable.", t);
		}
		finally {

			Thread.currentThread().setContextClassLoader(contextClassLoader);
			close(factoryClassFileInputStream);
			close(factoryImplClassFileInputStream);
		}

		return factoryOutput;
	}

	private static void close(Closeable closeable) {

		if (closeable != null) {

			try {
				closeable.close();
			}
			catch (IOException e) {
				// Do nothing.
			}
		}
	}
}
