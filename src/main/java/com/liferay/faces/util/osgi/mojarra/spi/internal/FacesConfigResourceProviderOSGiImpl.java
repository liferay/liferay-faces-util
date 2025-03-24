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
package com.liferay.faces.util.osgi.mojarra.spi.internal;

import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;

import com.liferay.faces.util.osgi.internal.FacesBundleUtil;
import com.liferay.faces.util.osgi.internal.FacesBundlesHandlerBase;
import com.liferay.faces.util.osgi.internal.FacesBundlesHandlerResourceProviderOSGiImpl;
import com.liferay.faces.util.resource.internal.ResourceProviderUtil;

import com.sun.faces.spi.FacesConfigResourceProvider;


/**
 * This class implements the Mojarra {@link com.sun.faces.spi.ConfigurationResourceProvider} SPI in order to enable the
 * discovery of resources within the OSGi bundle that match the "*.faces-config.xml" wildcard.
 *
 * @author  Kyle Stiemann
 */
public class FacesConfigResourceProviderOSGiImpl implements FacesConfigResourceProvider {

	/**
	 * Returns the list of *.faces-config.xml resources (and *faces-config.xml resources if necessary) found in Faces
	 * OSGi bundles. For more information, see {@link
	 * com.sun.faces.spi.ConfigurationResourceProvider#getResources(ServletContext)}.
	 */
	@Override
	public Collection<URI> getResources(ServletContext servletContext) {

		FacesBundlesHandlerBase<List<URL>> facesBundlesHandler;

		if (FacesBundleUtil.isCurrentWarThinWab()) {

			facesBundlesHandler = new FacesBundlesHandlerResourceProviderOSGiImpl(ResourceProviderUtil.META_INF_PATH,

					// Mojarra finds all the default META-INF/faces-config.xml files that are included inside each
					// thin WAB so only search for *.faces-config.xml files in the thin WAB.
					ResourceProviderUtil.FACES_CONFIG_EXTENSION_PATTERN,

					// Mojarra cannot find any of the META-INF/faces-config.xml files (or META-INF/*.faces-config.xml
					// files) in external OSGi bundles so search for all types of faces-config.xml files in other
					// bundles.
					ResourceProviderUtil.ALL_FACES_CONFIG_PATTERN);
		}
		else {

			// Mojarra finds all the default META-INF/faces-config.xml files that are included inside each
			// thin WAB so only search for *.faces-config.xml files in the thin WAB.
			facesBundlesHandler = new FacesBundlesHandlerResourceProviderOSGiImpl(ResourceProviderUtil.META_INF_PATH,
					ResourceProviderUtil.FACES_CONFIG_EXTENSION_PATTERN);
		}

		List<URL> resourceURLs = facesBundlesHandler.handleFacesBundles(servletContext, true);

		return ResourceProviderUtil.getResourcesAsURIs(resourceURLs);
	}
}
