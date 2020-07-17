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
package com.liferay.faces.util.osgi.internal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.parsers.SAXParser;

import com.liferay.faces.util.config.WebConfig;
import com.liferay.faces.util.config.internal.FacesConfigScannerBase;
import com.liferay.faces.util.config.internal.ResourceReader;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.internal.ProductPlutoImpl;
import com.liferay.faces.util.resource.internal.ResourceProviderUtil;


/**
 * @author  Kyle Stiemann
 */
public class FacesConfigScannerOSGiImpl extends FacesConfigScannerBase {

	// Private Constants
	private static final String FACES_CONFIG_META_INF_PATH = ResourceProviderUtil.META_INF_PATH + "faces-config.xml";

	public FacesConfigScannerOSGiImpl(ClassLoader classLoader, SAXParser saxParser, boolean resolveEntities,
		WebConfig webConfig) {
		super(classLoader, saxParser, resolveEntities, webConfig);
	}

	protected List<URL> getFacesConfigURLs(ClassLoader classLoader, FacesContext initFacesContext) throws IOException {

		List<URL> facesConfigURLs = new ArrayList<URL>();

		if (FacesBundleUtil.isCurrentWarThinWab()) {

			FacesBundlesHandlerBase<List<URL>> facesBundlesHandler = new FacesBundlesHandlerFacesConfigScannerOSGiImpl(
					ResourceProviderUtil.META_INF_PATH, ResourceProviderUtil.ALL_FACES_CONFIG_PATTERN);
			facesConfigURLs.addAll(facesBundlesHandler.handleFacesBundles(initFacesContext, true));
		}
		else {

			URL mojarraConfigURL = classLoader.getResource("/" + MOJARRA_CONFIG_PATH);

			if (mojarraConfigURL != null) {
				facesConfigURLs.add(mojarraConfigURL);
			}

			Enumeration<URL> facesConfigResources = classLoader.getResources(FACES_CONFIG_META_INF_PATH);

			ResourceProviderUtil.addAllEnumerationURLsToList(facesConfigResources, facesConfigURLs);

			// Obtain all *.faces-config.xml files as well.
			FacesBundlesHandlerBase<List<URL>> facesBundlesHandler = new FacesBundlesHandlerResourceProviderOSGiImpl(
					ResourceProviderUtil.META_INF_PATH, ResourceProviderUtil.FACES_CONFIG_EXTENSION_PATTERN);
			facesConfigURLs.addAll(facesBundlesHandler.handleFacesBundles(initFacesContext, true));

			// TODO Unfortunately, the above method of searching for *.faces-config.xml files returns an empty list in
			// non-OSGi enviroments. In the future, we should ensure that searching for *.faces-config.xml files works
			// in all environments. But since the *.faces-config.xml feature is rarely used and our supported
			// environment (Liferay Portal) is an OSGi environment and searching for class path resources is non-trivial
			// outside of OSGi, we can wait to support this.
		}

		ExternalContext externalContext = initFacesContext.getExternalContext();
		URL webInfFacesConfigURL = externalContext.getResource(FACES_CONFIG_WEB_INF_PATH);

		if (webInfFacesConfigURL != null) {
			facesConfigURLs.add(webInfFacesConfigURL);
		}

		return Collections.unmodifiableList(facesConfigURLs);
	}
}
