/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.liferay.faces.util.internal.CloseableUtil;


/**
 * This {@link ClassLoader} assumes that it is operating in a single-threaded environment (specifically to make {@link
 * #loadClassFromJar(java.lang.Class, java.net.URL)} work correctly).
 *
 * @author  Kyle Stiemann
 */
public class TestClassLoader extends ClassLoader {

	// Private Data Members
	private String packageBeingDefinedFromJar = null;

	public TestClassLoader(ClassLoader parent) {
		super(parent);
	}

	public Class<?> loadClassFromJar(Class<?> clazz, URL jarURL) throws ClassNotFoundException, MalformedURLException {
		return loadClassFromJar(clazz, jarURL, null);
	}

	public Class<?> loadClassFromJar(Class<?> clazz, URL jarURL, String overriddenJarVersion)
		throws ClassNotFoundException, MalformedURLException {

		String className = clazz.getName();

		String classPath = className.replace(".", "/") + ".class";
		String urlString = "jar:" + jarURL.toString() + "!/" + classPath;
		String manifestURLString = urlString.replaceFirst("[!][/].*$", "!/META-INF/MANIFEST.MF");
		URL url = new URL(urlString);
		InputStream manifestInputStream = null;

		try {

			URL manifestURL = new URL(manifestURLString);
			manifestInputStream = manifestURL.openStream();

			Properties properties = new Properties();
			properties.load(manifestInputStream);

			String specTitle = properties.getProperty("Specification-Title");
			String specVersion = properties.getProperty("Specification-Version");

			if (overriddenJarVersion != null) {
				specVersion = overriddenJarVersion;
			}

			String specVendor = properties.getProperty("Specification-Vendor");
			String implTitle = properties.getProperty("Implementation-Title");
			String implVersion = properties.getProperty("Implementation-Version");

			if (overriddenJarVersion != null) {
				implVersion = overriddenJarVersion;
			}

			String implVendor = properties.getProperty("Implementation-Vendor");

			// Since the package may be defined (in this loader or the parent loader), save the package
			// name and ensure that the package appears to be undefined (by returning null from
			// getPackage(packageBeingDefinedFromJar for example).
			int indexOfLastPeriod = className.lastIndexOf(".");
			packageBeingDefinedFromJar = className.substring(0, indexOfLastPeriod);
			definePackage(packageBeingDefinedFromJar, specTitle, specVersion, specVendor, implTitle, implVersion,
				implVendor, url);
		}
		catch (IOException e) {
			throw new ClassNotFoundException("Failed to define package " + packageBeingDefinedFromJar + " from jar.",
				e);
		}
		finally {

			packageBeingDefinedFromJar = null;
			CloseableUtil.close(manifestInputStream);
		}

		return loadClassWithoutParentLoader(clazz, url);
	}

	public Class<?> loadClassWithoutParentLoader(Class<?> clazz) throws ClassNotFoundException {

		String classFileName = "/" + clazz.getName().replace(".", "/") + ".class";
		URL url = clazz.getResource(classFileName);

		return loadClassWithoutParentLoader(clazz, url);
	}

	@Override
	protected Package getPackage(String name) {

		Package package_ = null;

		if (!name.equals(packageBeingDefinedFromJar)) {
			package_ = super.getPackage(name);
		}

		return package_;
	}

	@Override
	protected Package[] getPackages() {

		List<Package> packages = Arrays.asList(super.getPackages());

		if (packageBeingDefinedFromJar != null) {

			Iterator<Package> iterator = packages.iterator();

			while (iterator.hasNext()) {

				Package package_ = iterator.next();
				String packageName = package_.getName();

				if (packageName.equals(packageBeingDefinedFromJar)) {
					iterator.remove();
				}
			}
		}

		return packages.toArray(new Package[packages.size()]);
	}

	private Class<?> loadClassWithoutParentLoader(Class<?> clazz, URL url) throws ClassNotFoundException {

		String className = clazz.getName();
		Class<?> reloadedClass;
		InputStream inputStream = null;

		try {

			inputStream = url.openStream();

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
			reloadedClass = defineClass(className, bytes, 0, bytes.length);
			resolveClass(reloadedClass);
		}
		catch (IOException e) {
			throw new ClassNotFoundException("Failed to load class " + className + " without parent classloader.", e);
		}
		finally {
			CloseableUtil.close(inputStream);
		}

		return reloadedClass;
	}
}
