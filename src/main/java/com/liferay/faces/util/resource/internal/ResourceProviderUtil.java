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
package com.liferay.faces.util.resource.internal;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

/**
 * @author Kyle Stiemann
 */
public final class ResourceProviderUtil {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceProviderUtil.class);

	// Public Constants
	public static final String ALL_FACES_CONFIG_PATTERN = "*faces-config.xml";
	public static final String FACES_CONFIG_EXTENSION_PATTERN = "*.faces-config.xml";
	public static final String META_INF_PATH = "/META-INF/";
	public static final String WEB_FRAGMENT = "web-fragment.xml";
	public static final String META_INF_WEB_FRAGMENT = META_INF_PATH + WEB_FRAGMENT;

	private ResourceProviderUtil() {
		throw new AssertionError();
	}

	public static void addAllEnumerationURLsToList(Enumeration<URL> urlsToAdd, List<URL> urls) {

		if (urlsToAdd != null) {

			List<URL> facesBundleResourceURLs = Collections.list(urlsToAdd);
			urls.addAll(facesBundleResourceURLs);
		}
	}

	public static Set<URI> getResourcesAsURIs(List<URL> resourceURLs) {

		Set<URI> resourceURIs = new HashSet<URI>();

		for (URL resourceURL : resourceURLs) {

			try {
				resourceURIs.add(resourceURL.toURI());
			}
			catch (URISyntaxException e) {
				logger.error(e);
			}
		}

		return Collections.unmodifiableSet(resourceURIs);
	}
}
