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
package com.liferay.faces.util.config.internal;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.config.WebConfig;
import com.liferay.faces.util.internal.TCCLUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.xml.ConcurrentSAXParserFactory;


/**
 * @author  Neil Griffin
 */
public class ApplicationConfigInitializerImpl implements ApplicationConfigInitializer {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigInitializerImpl.class);

	// Private Data Members
	private String contextPath;
	private boolean resolveEntities;

	public ApplicationConfigInitializerImpl(String contextPath, boolean resolveEntities) {
		this.contextPath = contextPath;
		this.resolveEntities = resolveEntities;
	}

	public ApplicationConfig initialize() throws IOException {

		// Obtain the current ClassLoader
		ClassLoader classLoader = TCCLUtil.getThreadContextClassLoaderOrDefault(getClass());

		// Obtain a ResourceReader that is compatible with a startup ExternalContext
		ResourceReader resourceReader = newResourceReader();

		// Obtain a SAX Parser Factory.
		SAXParserFactory saxParserFactory = ConcurrentSAXParserFactory.newInstance();
		saxParserFactory.setValidating(false);
		saxParserFactory.setNamespaceAware(true);

		try {

			// Obtain a SAX Parser from the factory.
			SAXParser saxParser = saxParserFactory.newSAXParser();

			// Scan all the web.xml and web-fragment.xml descriptors in the classpath.
			WebConfigScanner webConfigScanner = newWebConfigScanner(classLoader, resourceReader, saxParser,
					resolveEntities);
			WebConfig webConfig = webConfigScanner.scan();

			// Scan all the faces-config.xml descriptors in the classpath.
			FacesConfigScanner facesConfigScanner = newFacesConfigScanner(classLoader, resourceReader, saxParser,
					resolveEntities, webConfig);
			FacesConfig facesConfig = facesConfigScanner.scan();

			return new ApplicationConfigImpl(contextPath, facesConfig, webConfig);
		}
		catch (Exception e) {

			// Log the error before throwing since the developer will need to see the stacktrace and
			// IOException(Throwable) wasn't added until Java 6.
			logger.error(e);
			throw new IOException(e.getMessage());
		}
	}

	protected FacesConfigScanner newFacesConfigScanner(ClassLoader classLoader, ResourceReader resourceReader,
		SAXParser saxParser, boolean resolveEntities, WebConfig webConfig) {
		return new FacesConfigScannerImpl(classLoader, resourceReader, saxParser, resolveEntities, webConfig);
	}

	protected ResourceReader newResourceReader() {
		FacesContext startupFacesContext = FacesContext.getCurrentInstance();
		ExternalContext startupExternalContext = startupFacesContext.getExternalContext();

		return new ResourceReaderExternalContextImpl(startupExternalContext);
	}

	protected WebConfigScanner newWebConfigScanner(ClassLoader classLoader, ResourceReader resourceReader,
		SAXParser saxParser, boolean resolveEntities) {
		return new WebConfigScannerImpl(classLoader, resourceReader, saxParser, resolveEntities);
	}

}
