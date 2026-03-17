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
package com.liferay.faces.util.model;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.InvalidFileNameException;

import org.junit.Test;

import com.liferay.faces.util.model.internal.UploadedFileFactoryImpl;

import junit.framework.Assert;


/**
 * @author  Kyle Stiemann
 */
public final class UploadedFileFactoryTest {

	private static void assertCorrectUploadedFileStatusAndMessageObtainedFromException(
		UploadedFile.Status expectedStatus, Exception e) {
		assertCorrectUploadedFileStatusAndMessageObtainedFromException(expectedStatus, e.getMessage(), e);
	}

	private static void assertCorrectUploadedFileStatusAndMessageObtainedFromException(
		UploadedFile.Status expectedStatus, String expectedMessage, Exception e) {

		UploadedFileFactory uploadedFileFactory = new UploadedFileFactoryImpl();
		UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(e);
		Assert.assertEquals(expectedStatus, uploadedFile.getStatus());
		Assert.assertEquals(expectedMessage, uploadedFile.getMessage());

		if ((e instanceof FileUploadException) && !(e instanceof FileUploadBase.IOFileUploadException)) {

			FileUploadException fileUploadException = (FileUploadException) e;
			FileUploadBase.FileUploadIOException fileUploadIOException = new FileUploadBase.FileUploadIOException(
					fileUploadException);
			uploadedFile = uploadedFileFactory.getUploadedFile(fileUploadIOException);
			Assert.assertEquals(expectedStatus, uploadedFile.getStatus());
			Assert.assertEquals(expectedMessage, uploadedFile.getMessage());
		}
	}

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
				throw new AssertionError(t);
			}
			else {
				throw t;
			}
		}
	}

	@Test
	public void testGetUploadedFileCommonsExceptionStatusTest() {

		String expectedMessage = "test";
		assertCorrectUploadedFileStatusAndMessageObtainedFromException(UploadedFile.Status.FILE_SIZE_LIMIT_EXCEEDED,
			new FileUploadBase.FileSizeLimitExceededException(expectedMessage, 0, 1));
		assertCorrectUploadedFileStatusAndMessageObtainedFromException(UploadedFile.Status.REQUEST_SIZE_LIMIT_EXCEEDED,
			new FileUploadBase.SizeLimitExceededException(expectedMessage, 0, 1));
		assertCorrectUploadedFileStatusAndMessageObtainedFromException(UploadedFile.Status.FILE_INVALID_NAME_PATTERN,
			new InvalidFileNameException("\\\\", "test"));
		assertCorrectUploadedFileStatusAndMessageObtainedFromException(UploadedFile.Status.ERROR, expectedMessage,
			new FileUploadBase.IOFileUploadException("", new IOException(expectedMessage)));
		assertCorrectUploadedFileStatusAndMessageObtainedFromException(UploadedFile.Status.ERROR,
			new FileUploadBase.InvalidContentTypeException(expectedMessage));
		assertCorrectUploadedFileStatusAndMessageObtainedFromException(UploadedFile.Status.ERROR,
			new FileUploadBase.UnknownSizeException(expectedMessage));
		assertCorrectUploadedFileStatusAndMessageObtainedFromException(UploadedFile.Status.ERROR,
			new FileUploadException(expectedMessage));
		assertCorrectUploadedFileStatusAndMessageObtainedFromException(UploadedFile.Status.ERROR,
			new RuntimeException(expectedMessage));
	}

	@Test
	public void testGetUploadedFileExceptionStatusTest() {

		UploadedFileFactory uploadedFileFactory = new UploadedFileFactoryImpl();

		for (UploadedFile.Status expectedStatus : UploadedFile.Status.values()) {

			String expectedMessage = "expected message";
			Exception e = new IOException(expectedMessage);
			UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(e, expectedStatus);
			String message = uploadedFile.getMessage();
			Assert.assertEquals(expectedMessage, message);

			UploadedFile.Status status = uploadedFile.getStatus();
			Assert.assertEquals(expectedStatus, status);
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
