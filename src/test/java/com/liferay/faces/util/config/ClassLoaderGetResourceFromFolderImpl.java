/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;


/**
 * @author  Kyle Stiemann
 */
public class ClassLoaderGetResourceFromFolderImpl extends ClassLoader {

	// Private Data Members
	private final String[] resourceFolders;

	public ClassLoaderGetResourceFromFolderImpl(ClassLoader classLoader, String... resourceFolders) {

		super(classLoader);
		this.resourceFolders = resourceFolders;
	}

	private static String getResourceName(final String RESOURCE_FOLDER_PREFIX, String resourceName) {

		if (!resourceName.startsWith(RESOURCE_FOLDER_PREFIX)) {

			if (!resourceName.startsWith("/")) {
				resourceName = "/" + resourceName;
			}

			resourceName = RESOURCE_FOLDER_PREFIX + resourceName;
		}

		return resourceName;
	}

	@Override
	public URL getResource(String name) {

		URL resource = null;

		for (String resourceFolder : resourceFolders) {

			resource = super.getResource(getResourceName(resourceFolder, name));

			if (resource != null) {
				break;
			}
		}

		return resource;
	}

	@Override
	public InputStream getResourceAsStream(String name) {

		InputStream resourceAsStream = null;

		for (String resourceFolder : resourceFolders) {

			resourceAsStream = super.getResourceAsStream(getResourceName(resourceFolder, name));

			if (resourceAsStream != null) {
				break;
			}
		}

		return resourceAsStream;
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {

		List<URL> resources = new ArrayList<URL>();

		for (String resourceFolder : resourceFolders) {
			resources.addAll(Collections.list(super.getResources(getResourceName(resourceFolder, name))));
		}

		return Collections.enumeration(resources);
	}
}
