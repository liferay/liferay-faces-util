/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.product;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;

import com.liferay.faces.util.classloader.TestClassLoader;


/**
 * @author  Kyle Stiemann
 */
public class UtilOnlyClassLoader extends TestClassLoader {

	public UtilOnlyClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	public URL findResource(String name) {

		URL resource = null;

		if (name.startsWith("com/liferay/faces/util")) {
			resource = super.findResource(name);
		}

		return resource;
	}

	@Override
	public Enumeration<URL> findResources(String name) throws IOException {

		Enumeration<URL> resources = Collections.<URL>enumeration(Collections.<URL>emptyList());

		if (name.startsWith("com/liferay/faces/util")) {
			resources = super.findResources(name);
		}

		return resources;
	}

	@Override
	public URL getResource(String name) {

		URL resource = null;

		if (name.startsWith("com/liferay/faces/util")) {
			resource = super.getResource(name);
		}

		return resource;
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {

		Enumeration<URL> resources = Collections.<URL>enumeration(Collections.<URL>emptyList());

		if (name.startsWith("com/liferay/faces/util")) {
			resources = super.getResources(name);
		}

		return resources;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		if (!(name.startsWith("com.liferay.faces.util") || name.startsWith("java.lang"))) {
			throw new ClassNotFoundException(name);
		}

		return super.loadClass(name);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		if (!(name.startsWith("com.liferay.faces.util") || name.startsWith("java.lang"))) {
			throw new ClassNotFoundException(name);
		}

		return super.findClass(name);
	}

	@Override
	protected Package getPackage(String name) {

		Package package_ = null;

		if (!(name.startsWith("com.liferay.faces.util") || name.startsWith("java.lang"))) {
			package_ = super.getPackage(name);
		}

		return package_;
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

		if (!(name.startsWith("com.liferay.faces.util") || name.startsWith("java.lang"))) {
			throw new ClassNotFoundException(name);
		}

		return super.loadClass(name, resolve);
	}
}
