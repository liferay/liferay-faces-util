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

import java.io.IOException;
import java.io.InputStream;


/**
 * @author  Kyle Stiemann
 */
/* package-private */ class BlockServiceLoaderUtilCompat {

	protected BlockServiceLoaderUtilCompat() {
		throw new AssertionError();
	}

	protected static void close(InputStream inputStream) {

		if (inputStream != null) {

			try {
				inputStream.close();
			}
			catch (IOException e) {
				// Do nothing.
			}
		}
	}

	protected static void loadFactoryClassInnerClasses(String factoryClassName, String factoryClazzSimpleName,
		Class<?> factoryClazz, BlockServiceLoaderClassLoader blockServiceLoaderClassLoader)
		throws ClassNotFoundException, IOException {

		String serviceFinderClassName = factoryClassName + "$ServiceFinder";
		String serviceFinderClassFileName = factoryClazzSimpleName + "$ServiceFinder.class";
		InputStream serviceFinderClassFileInputStream = factoryClazz.getResourceAsStream(serviceFinderClassFileName);

		try {
			blockServiceLoaderClassLoader.loadClass(serviceFinderClassName, serviceFinderClassFileInputStream);
		}
		finally {
			close(serviceFinderClassFileInputStream);
		}
	}
}
