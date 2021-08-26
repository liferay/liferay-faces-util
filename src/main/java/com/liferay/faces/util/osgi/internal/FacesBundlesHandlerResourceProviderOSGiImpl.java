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
package com.liferay.faces.util.osgi.internal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.resource.internal.ResourceProviderUtil;


/**
 * Note that this class uses a {@link List} of {@link URL}s rather than a {@link java.util.Set} of URLs (which would
 * remove duplicates) because Set's check for equality. Unfortunately {@link URL#equals(java.lang.Object)} and {@link
 * URL#hashCode()} may actually make blocking network requests before returning, so using a Set may cause serious
 * performance issues. For more information see this StackOverflow Q&A:
 * https://stackoverflow.com/questions/18280818/what-java-library-can-i-use-to-compare-two-urls-for-equality.
 *
 * @author  Kyle Stiemann
 */
public class FacesBundlesHandlerResourceProviderOSGiImpl extends FacesBundlesHandlerBase<List<URL>> {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceProviderUtil.class);

	// Private Data Members
	private final String currentFacesWabFilePattern;
	private final String path;
	private final String facesBundleFilePattern;

	public FacesBundlesHandlerResourceProviderOSGiImpl(String path, String facesBundleFilePattern) {

		this.path = path;
		this.currentFacesWabFilePattern = facesBundleFilePattern;
		this.facesBundleFilePattern = facesBundleFilePattern;
	}

	public FacesBundlesHandlerResourceProviderOSGiImpl(String path, String currentFacesWabFilePattern,
		String facesBundleFilePattern) {

		this.path = path;
		this.currentFacesWabFilePattern = currentFacesWabFilePattern;
		this.facesBundleFilePattern = facesBundleFilePattern;
	}

	@Override
	protected List<URL> getInitialReturnValueObject() {
		return new ArrayList<URL>();
	}

	@Override
	protected void handleCurrentFacesWab(Bundle currentFacesWab, ReturnValueReference<List<URL>> returnValueReference,
		boolean recurse) {

		BundleWiring bundleWiring = currentFacesWab.adapt(BundleWiring.class);

		if (bundleWiring != null) {

			int options = BundleWiring.LISTRESOURCES_LOCAL;

			if (recurse) {
				options = BundleWiring.LISTRESOURCES_RECURSE;
			}

			Collection<String> resourceFilePaths = bundleWiring.listResources(path, currentFacesWabFilePattern,
					options);

			for (String resourceFilePath : resourceFilePaths) {

				Enumeration<URL> resources = null;

				try {
					resources = currentFacesWab.getResources(resourceFilePath);
				}
				catch (IOException e) {

					long bundleId = currentFacesWab.getBundleId();
					String symbolicName = currentFacesWab.getSymbolicName();
					logger.error(
						"Failed to obtain URLs of resources with path \"{0}\" from bundle with bundle id \"{1}\" and symbolic name \"{2}\" due to the following error:",
						resourceFilePath, bundleId, symbolicName);
					logger.error(e);
				}

				List<URL> urls = returnValueReference.get();
				ResourceProviderUtil.addAllEnumerationURLsToList(resources, urls);
			}
		}
	}

	@Override
	protected void handleFacesBundle(Bundle bundle, ReturnValueReference<List<URL>> returnValueReference,
		boolean recurse) {

		Enumeration<URL> resources = bundle.findEntries(path, facesBundleFilePattern, recurse);
		List<URL> urls = returnValueReference.get();
		ResourceProviderUtil.addAllEnumerationURLsToList(resources, urls);
	}
}
