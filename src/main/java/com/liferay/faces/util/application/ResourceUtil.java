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
package com.liferay.faces.util.application;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.faces.component.UIComponent;


/**
 * @author  Neil Griffin
 */
public final class ResourceUtil {

	// Prevent instantiation since this is a static utility class.
	private ResourceUtil() {
		throw new AssertionError();
	}

	public static String getResourceId(UIComponent componentResource) {

		String library = null;
		String name = null;

		if (componentResource != null) {

			Map<String, Object> componentResourceAttributes = componentResource.getAttributes();
			library = (String) componentResourceAttributes.get("library");
			name = (String) componentResourceAttributes.get("name");
		}

		return getResourceId(library, name);
	}

	public static String getResourceId(String library, String name) {

		String resourceDependencyId;

		if (library == null) {
			resourceDependencyId = name;
		}
		else if (name == null) {
			resourceDependencyId = library;
		}
		else {
			resourceDependencyId = library.concat(":").concat(name);
		}

		return resourceDependencyId;
	}

	/**
	 * Converts a String to an {@link InputStream}.
	 *
	 * @param   string  the string to be used
	 * @param   encoding  encoding for the string
	 *
	 * @return  the InputStream representation of the String.
	 *
	 * @throws  UnsupportedEncodingException  problem with the encoding support
	 */
	public static InputStream toInputStream(String string, String encoding) throws UnsupportedEncodingException {
		return new ByteArrayInputStream(string.getBytes(encoding));
	}

	/**
	 * Converts an {@link InputStream} to a String.
	 *
	 * @param   inputStream  The InputStream which contains some text.
	 * @param   encoding     The encoding of the text of the InputStream.
	 * @param   bufferSize   The size of the character buffer to be used when reading the file. If you do not want to
	 *                       specify this value, use {@link ResourceUtil#toInputStream(java.lang.String,
	 *                       java.lang.String)} which provides a default of 1024.
	 *
	 * @return  the string content of the InputStream.
	 *
	 * @throws  IOException  problem with the IO
	 */
	public static String toString(InputStream inputStream, String encoding, int bufferSize) throws IOException {

		char[] buffer = new char[bufferSize];
		StringBuilder stringBuilder = new StringBuilder();
		InputStreamReader inputStreamReader = null;

		try {

			inputStreamReader = new InputStreamReader(inputStream, encoding);

			int charsRead = 0;

			while (charsRead != -1) {

				charsRead = inputStreamReader.read(buffer, 0, buffer.length);

				if (charsRead > 0) {
					stringBuilder.append(buffer, 0, charsRead);
				}
			}
		}
		finally {

			if (inputStream != null) {
				inputStream.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
		}

		return stringBuilder.toString();
	}
}
