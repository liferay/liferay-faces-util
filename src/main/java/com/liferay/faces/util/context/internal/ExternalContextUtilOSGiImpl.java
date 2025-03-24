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
package com.liferay.faces.util.context.internal;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextWrapper;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.osgi.internal.FacesBundlesHandlerBase;
import com.liferay.faces.util.osgi.internal.FacesBundlesHandlerResourceProviderOSGiImpl;

/**
 * This class provides the ability for {@link com.sun.faces.application.resource.WebappResourceHelper} to discover JSF
 * resources (such as primefaces/jquery/jquery.js) that are contained in other OSGi bundles. The reason why
 * {@link ExternalContext} is used as an extension point is because Mojarra does not have a resource provider API.
 *
 * @author Neil Griffin
 */
public class ExternalContextUtilOSGiImpl extends ExternalContextWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExternalContextUtilOSGiImpl.class);

	// Private Data Members
	private Map<String, Object> applicationMap;
	private Map<String, Set<String>> resourcePathsCache;
	private Map<String, URL> resourceURLCache;

	public ExternalContextUtilOSGiImpl(Object context) {
		this.applicationMap = new PortletContextMap(context);
		this.resourcePathsCache = new HashMap<String, Set<String>>();
		this.resourceURLCache = new HashMap<String, URL>();
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {

		if (path == null) {
			throw new NullPointerException();
		}

		path = fixPath(path);

		URL resourceURL = resourceURLCache.get(path);

		if (resourceURL != null) {
			return resourceURL;
		}

		String pathDir = path;
		String fileSpec = "*";

		int pos = path.lastIndexOf("/");

		if (pos > 0) {
			pathDir = path.substring(0, pos);
			fileSpec = path.substring(pos + 1);
		}

		FacesBundlesHandlerBase<List<URL>> facesBundlesHandler =
			new FacesBundlesHandlerResourceProviderOSGiImpl(pathDir, fileSpec);
		List<URL> urls = facesBundlesHandler.handleFacesBundles(applicationMap, false);

		if ((urls != null) && !urls.isEmpty()) {
			resourceURL = urls.get(0);
			resourceURLCache.put(path, resourceURL);

			return resourceURL;
		}

		return null;
	}

	@Override
	public InputStream getResourceAsStream(String path) {

		logger.debug("path=[{}]", path);

		URL resourceURL = null;

		try {
			resourceURL = getResource(path);
		}
		catch (MalformedURLException e) {
			logger.error(e);

			return null;
		}

		if (resourceURL != null) {

			try {
				return resourceURL.openStream();
			}
			catch (IOException e) {
				logger.error(e);

				return null;
			}
		}

		return null;
	}

	@Override
	public Set<String> getResourcePaths(String path) {

		if (path == null) {
			throw new NullPointerException();
		}

		path = fixPath(path);

		Set<String> resourcePaths = resourcePathsCache.get(path);

		if (resourcePaths != null) {
			return resourcePaths;
		}

		FacesBundlesHandlerBase<List<URL>> facesBundlesHandler =
			new FacesBundlesHandlerResourceProviderOSGiImpl(path, "*");
		List<URL> urls = facesBundlesHandler.handleFacesBundles(applicationMap, false);

		if ((urls != null) && !urls.isEmpty()) {
			resourcePaths = new HashSet<String>();

			for (URL url : urls) {
				String urlPath = url.getPath();

				if (urlPath.startsWith(path)) {
					resourcePaths.add(urlPath);
				}
			}

			resourcePathsCache.put(path, resourcePaths);

			return resourcePaths;
		}

		return null;
	}

	@Override
	public ExternalContext getWrapped() {
		return null;
	}

	private String fixPath(String path) {

		if (path.startsWith("/resources")) {
			path = "/META-INF" + path;
		}
		else if (path.startsWith("/WEB-INF")) {
			path = "/META-INF" + path.substring("/WEB-INF".length());
		}

		return path;
	}

	private static class PortletContextMap extends HashMap<String, Object> {

		// Private Data Members
		private Object context;
		Method getAttributeMethod;

		public PortletContextMap(Object context) {
			this.context = context;

			Class<?> contextClass = context.getClass();

			try {
				getAttributeMethod = contextClass.getMethod("getAttribute", String.class);
			}
			catch (NoSuchMethodException e) {
				logger.error(e);
			}
		}

		@Override
		public Object get(Object key) {

			try {
				return getAttributeMethod.invoke(context, key);
			}
			catch (Exception e) {
				logger.error(e);
			}

			return null;
		}

		@Override
		public Object put(String key, Object value) {
			throw new UnsupportedOperationException();
		}
	}
}
