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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;


/**
 * @author  Kyle Stiemann
 */
public class BlockServiceLoaderClassLoader extends ClassLoader {

	public BlockServiceLoaderClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	public URL findResource(String name) {

		if (name.contains("META-INF/services")) {
			return null;
		}
		else {
			return super.findResource(name);
		}
	}

	@Override
	public Enumeration<URL> findResources(String name) throws IOException {

		if (name.contains("META-INF/services")) {
			return Collections.<URL>emptyEnumeration();
		}
		else {
			return super.findResources(name);
		}
	}

	@Override
	public URL getResource(String name) {

		if (name.contains("META-INF/services")) {
			return null;
		}
		else {
			return super.getResource(name);
		}
	}

	@Override
	public InputStream getResourceAsStream(String name) {

		if (name.contains("META-INF/services")) {
			return null;
		}
		else {
			return super.getResourceAsStream(name);
		}
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {

		if (name.contains("META-INF/services")) {
			return Collections.<URL>emptyEnumeration();
		}
		else {
			return super.getResources(name);
		}
	}

	public Class<?> loadClass(String name, InputStream inputStream) throws ClassNotFoundException, IOException {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int length = 0;
		byte[] data = new byte[1024];

		while (length > -1) {

			length = inputStream.read(data, 0, data.length);

			if (length > -1) {
				buffer.write(data, 0, length);
			}
		}

		buffer.flush();

		byte[] bytes = buffer.toByteArray();
		Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
		resolveClass(clazz);

		return clazz;
	}
}
