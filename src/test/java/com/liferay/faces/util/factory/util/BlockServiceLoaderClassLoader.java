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
package com.liferay.faces.util.factory.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;

import com.liferay.faces.util.classloader.TestClassLoader;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Kyle Stiemann
 */
/* package-private */ class BlockServiceLoaderClassLoader extends TestClassLoader {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BlockServiceLoaderClassLoader.class);

	// Private Data Members
	private final String serviceClassName;
	private int attemptsToAccessServiceFile = 0;

	public BlockServiceLoaderClassLoader(ClassLoader parent, String serviceClassName) {

		super(parent);
		this.serviceClassName = serviceClassName;
	}

	public boolean attemptedToAccessServiceFile() {

		logger.debug("There were {0} attempts to access service file: {1}", attemptsToAccessServiceFile,
			"/META-INF/services/" + serviceClassName);

		return attemptsToAccessServiceFile > 0;
	}

	@Override
	public URL findResource(String name) {

		if (name.contains("META-INF/services")) {

			if (name.contains(serviceClassName)) {
				attemptsToAccessServiceFile++;
			}

			return null;
		}
		else {
			return super.findResource(name);
		}
	}

	@Override
	public Enumeration<URL> findResources(String name) throws IOException {

		if (name.contains("META-INF/services")) {

			if (name.contains(serviceClassName)) {
				attemptsToAccessServiceFile++;
			}

			return Collections.enumeration(Collections.<URL>emptyList());
		}
		else {
			return super.findResources(name);
		}
	}

	@Override
	public URL getResource(String name) {

		if (name.contains("META-INF/services")) {

			if (name.contains(serviceClassName)) {
				attemptsToAccessServiceFile++;
			}

			return null;
		}
		else {
			return super.getResource(name);
		}
	}

	@Override
	public InputStream getResourceAsStream(String name) {

		if (name.contains("META-INF/services")) {

			if (name.contains(serviceClassName)) {
				attemptsToAccessServiceFile++;
			}

			return null;
		}
		else {
			return super.getResourceAsStream(name);
		}
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {

		if (name.contains("META-INF/services")) {

			if (name.contains(serviceClassName)) {
				attemptsToAccessServiceFile++;
			}

			return Collections.enumeration(Collections.<URL>emptyList());
		}
		else {
			return super.getResources(name);
		}
	}
}
