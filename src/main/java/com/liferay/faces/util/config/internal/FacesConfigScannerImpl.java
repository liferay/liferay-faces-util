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
package com.liferay.faces.util.config.internal;

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


/**
 * @author  Neil Griffin
 */
public class FacesConfigScannerImpl extends FacesConfigScannerBase {

	public FacesConfigScannerImpl(ClassLoader classLoader, SAXParser saxParser, boolean resolveEntities,
		WebConfig webConfig) {
		super(classLoader, saxParser, resolveEntities, webConfig);
	}

	@Override
	protected List<URL> getFacesConfigURLs(ClassLoader classLoader, FacesContext initFacesContext) throws IOException {

		List<URL> facesConfigURLs = new ArrayList<URL>();

		URL mojarraFacesConfigURL = null;

		// First, parse the Mojarra configuration found in the classpath.
		Enumeration<URL> mojarraFacesConfigURLs = classLoader.getResources(MOJARRA_CONFIG_PATH);

		if (mojarraFacesConfigURLs != null) {

			while (mojarraFacesConfigURLs.hasMoreElements()) {
				mojarraFacesConfigURL = mojarraFacesConfigURLs.nextElement();

				break;
			}
		}

		if (mojarraFacesConfigURL != null) {
			facesConfigURLs.add(mojarraFacesConfigURL);
		}

		Enumeration<URL> urlEnumeration = classLoader.getResources("META-INF/faces-config.xml");

		while (urlEnumeration.hasMoreElements()) {
			facesConfigURLs.add(urlEnumeration.nextElement());
		}

		ExternalContext externalContext = initFacesContext.getExternalContext();
		URL webInfFacesConfigURL = externalContext.getResource(FACES_CONFIG_WEB_INF_PATH);

		if (webInfFacesConfigURL != null) {
			facesConfigURLs.add(webInfFacesConfigURL);
		}

		return Collections.unmodifiableList(facesConfigURLs);
	}
}
