/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.model;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

import com.liferay.faces.util.model.internal.UploadedFileFactoryImpl;


/**
 * @author  Kyle Stiemann
 */
public final class UploadedFileFactoryTest {

	private static boolean isCommonsClassNotFound(Throwable t) {

		while (!(t instanceof CommonsClassNotFoundException) && (t != null)) {
			t = t.getCause();
		}

		return (t instanceof CommonsClassNotFoundException);
	}

	@Test
	public void testFACES_3419() throws Throwable {

		try {

			Class<?> testClass = getClass();
			ClassLoader classLoader = new ClassLoaderNoCommonsImpl(testClass.getResource("/"),
					testClass.getClassLoader());
			Class<?> uploadedFileFactoryImplClass = classLoader.loadClass(UploadedFileFactoryImpl.class.getName());
			Object uploadedFileFactoryImpl = uploadedFileFactoryImplClass.newInstance();
			Method getUploadedFileMethod = uploadedFileFactoryImplClass.getMethod("getUploadedFile", Exception.class);
			Class<?> ioExceptionClass = classLoader.loadClass(IOException.class.getName());
			Object ioException = ioExceptionClass.newInstance();
			getUploadedFileMethod.invoke(uploadedFileFactoryImpl, ioException);
		}
		catch (Throwable t) {

			if (isCommonsClassNotFound(t)) {
				throw new AssertionError("", t);
			}
			else {
				throw t;
			}
		}
	}

	private static final class ClassLoaderNoCommonsImpl extends URLClassLoader {

		public ClassLoaderNoCommonsImpl(URL testClassesURL, ClassLoader classLoader) throws MalformedURLException {
			super(new URL[] { new URL(testClassesURL.toString().replace("/test-classes", "/classes")), testClassesURL },
				classLoader);
		}

		@Override
		public Class<?> loadClass(String className) throws ClassNotFoundException {

			if (className.startsWith("org.apache.commons.")) {
				throw new CommonsClassNotFoundException(className);
			}

			if (className.startsWith("java.") || className.startsWith("javax.")) {
				return super.loadClass(className);
			}
			else {
				return super.findClass(className);
			}
		}
	}

	private static final class CommonsClassNotFoundException extends ClassNotFoundException {

		// serialVersionUID
		private static final long serialVersionUID = 2154466466767297423L;

		public CommonsClassNotFoundException(String s) {
			super(s);
		}
	}
}
