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
package com.liferay.faces.util.internal;

/**
 * @author Kyle Stiemann
 */
public final class TCCLUtil {

	private TCCLUtil() {
		throw new AssertionError();
	}

	public static ClassLoader getThreadContextClassLoaderOrDefault(Class<?> callingClass) {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		if (classLoader == null) {
			classLoader = callingClass.getClassLoader();
		}

		return classLoader;
	}

	public static Class<?> loadClassFromContext(Class<?> callingClass, String className) throws ClassNotFoundException {

		ClassLoader classLoader = getThreadContextClassLoaderOrDefault(callingClass);

		return classLoader.loadClass(className);
	}
}
