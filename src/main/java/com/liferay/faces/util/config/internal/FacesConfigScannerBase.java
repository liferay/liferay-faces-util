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
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.webapp.FacesServlet;
import javax.xml.parsers.SAXParser;

import com.liferay.faces.util.config.ConfiguredServlet;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.config.WebConfig;
import com.liferay.faces.util.internal.CloseableUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.internal.ProductMojarraImpl;


/**
 * @author  Neil Griffin
 */
public abstract class FacesConfigScannerBase implements FacesConfigScanner {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesConfigScannerBase.class);

	// Public Constants
	public static final String MOJARRA_CONFIG_PATH = "com/sun/faces/jsf-ri-runtime.xml";

	// Protected Constants
	protected static final String FACES_CONFIG_WEB_INF_PATH = "/WEB-INF/faces-config.xml";
	protected static final String FACES_SERVLET = "Faces Servlet";
	protected static final String FACES_SERVLET_FQCN = FacesServlet.class.getName();

	// Private Data Members
	private ClassLoader classLoader;
	private boolean resolveEntities;
	private SAXParser saxParser;
	private WebConfig webConfig;

	public FacesConfigScannerBase(ClassLoader classLoader, SAXParser saxParser, boolean resolveEntities,
		WebConfig webConfig) {
		this.classLoader = classLoader;
		this.saxParser = saxParser;
		this.resolveEntities = resolveEntities;
		this.webConfig = webConfig;
	}

	public FacesConfig scan() throws IOException {

		String configuredFacesServletName = FACES_SERVLET;

		List<ConfiguredServletMapping> facesServletMappings = new ArrayList<ConfiguredServletMapping>();

		// Determine the configured servlet-name for the FacesServlet.
		List<ConfiguredServlet> configuredServlets = webConfig.getConfiguredServlets();

		if (configuredServlets != null) {

			for (ConfiguredServlet configuredServlet : configuredServlets) {

				if (FACES_SERVLET_FQCN.equals(configuredServlet.getServletClass())) {

					configuredFacesServletName = configuredServlet.getServletName();

					break;
				}
			}
		}

		// Determine the configured servlet-mapping entries that are associated with the FacesServlet.
		List<ConfiguredServletMapping> configuredServletMappings = webConfig.getConfiguredServletMappings();

		if (configuredServletMappings != null) {

			for (ConfiguredServletMapping configuredServletMapping : configuredServletMappings) {

				if (configuredFacesServletName.equals(configuredServletMapping.getServletName())) {

					facesServletMappings.add(configuredServletMapping);
				}
			}
		}

		// Discover the suffixes/extensions that the user has specified to be associated with JSF views.
		String defaultSuffixParam = webConfig.getConfiguredContextParams().get(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME);

		if (defaultSuffixParam == null) {
			defaultSuffixParam = ViewHandler.DEFAULT_SUFFIX;
		}

		List<String> configuredSuffixes = Arrays.asList(defaultSuffixParam.split(" "));

		// If they don't exist explicitly in web.xml, then setup implicit servlet-mapping entries to the default
		// suffixes.
		for (String configuredSuffix : configuredSuffixes) {

			boolean found = false;

			for (ConfiguredServletMapping explicitFacesServletMapping : facesServletMappings) {

				if (explicitFacesServletMapping.isExtensionMapped() &&
						explicitFacesServletMapping.getExtension().equals(configuredSuffix)) {
					found = true;

					break;
				}
			}

			if (!found) {
				String urlPattern = "*" + configuredSuffix;
				ConfiguredServletMapping implicitFacesServletMapping = new ConfiguredServletMappingImpl(FACES_SERVLET,
						urlPattern, true);
				facesServletMappings.add(implicitFacesServletMapping);
				logger.debug("Added implicit extension-mapped servlet-mapping for urlPattern=[{0}]", urlPattern);
			}
		}

		FacesConfig facesConfig = new FacesConfigImpl(facesServletMappings, configuredSuffixes);
		InputStream inputStream = null;

		try {

			// Parse the WEB-INF/faces-config.xml descriptor. Gathering absolute-ordering, if any.
			FacesConfigDescriptor mojarraConfigDescriptor = null;
			FacesConfigDescriptor webInfFacesConfigDescriptor = null;
			FacesConfigDescriptorParser facesConfigDescriptorParser = newFacesConfigDescriptorParser();
			FacesConfigParser facesConfigParser = newFacesConfigParser();

			// Parse all of the faces-config.xml files found in the classpath.
			List<URL> facesConfigURLs = getFacesConfigURLs(classLoader, FacesContext.getCurrentInstance());
			List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();

			for (URL facesConfigURL : facesConfigURLs) {

				logger.debug("Pre-processing faces-config: [{0}]", facesConfigURL);
				inputStream = facesConfigURL.openStream();

				String facesConfigPath = facesConfigURL.getPath();

				if (facesConfigPath.endsWith(MOJARRA_CONFIG_PATH)) {

					// Parse the com/sun/faces/jsf-ri-runtime.xml descriptor.
					mojarraConfigDescriptor = facesConfigDescriptorParser.parse(inputStream, facesConfigURL);
				}
				else if (facesConfigPath.endsWith(FACES_CONFIG_WEB_INF_PATH)) {

					// Parse the WEB-INF/faces-config.xml descriptor. Gathering absolute-ordering, if any.
					webInfFacesConfigDescriptor = facesConfigDescriptorParser.parse(inputStream, facesConfigURL);
				}
				else {

					// Parse any META-INF/faces-config.xml files found in the classpath.
					FacesConfigDescriptor facesConfigDescriptor = facesConfigDescriptorParser.parse(inputStream,
							facesConfigURL);
					facesConfigDescriptors.add(facesConfigDescriptor);
				}

				inputStream.close();
			}

			// Since the FactoryExtensionFinder has not been initialized, create a new Mojarra Product manually,
			// to determine if Mojarra is present.
			final boolean MOJARRA_DETECTED = new ProductMojarraImpl().isDetected();

			if (MOJARRA_DETECTED && (mojarraConfigDescriptor == null)) {
				logger.warn("{0} not found.", MOJARRA_CONFIG_PATH);
			}

			// Sort the faces configuration files in accord with
			// javax.faces-api-2.2-FINAL_JSF_20130320_11.4.8_Ordering_of_Artifacts
			List<FacesConfigDescriptor> orderedConfigs = OrderingUtil.getOrderedFacesConfigDescriptors(
					mojarraConfigDescriptor, facesConfigDescriptors, webInfFacesConfigDescriptor);

			for (FacesConfigDescriptor config : orderedConfigs) {

				String urlString = config.getURL();
				URL url = new URL(urlString);
				logger.debug("Post-processing faces-config: [{0}]", url);

				inputStream = url.openStream();

				try {

					if (urlString.contains(MOJARRA_CONFIG_PATH)) {

						FacesConfigParser mojarraConfigParser = new FacesConfigParserImpl(saxParser, resolveEntities);
						mojarraConfigParser.parse(inputStream, facesConfig);
					}
					else {
						facesConfig = facesConfigParser.parse(inputStream, facesConfig);
					}
				}
				catch (IOException e) {
					logger.error(e);
				}

				inputStream.close();

				try {
					saxParser.reset();
				}
				catch (Exception e) {
					logger.error(e);
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		finally {
			CloseableUtil.close(inputStream);
		}

		return facesConfig;
	}

	protected abstract List<URL> getFacesConfigURLs(ClassLoader classLoader, FacesContext initFacesContext)
		throws IOException;

	protected FacesConfigDescriptorParser newFacesConfigDescriptorParser() {
		return new FacesConfigDescriptorParserImpl(saxParser, resolveEntities);
	}

	protected FacesConfigParser newFacesConfigParser() {
		return new FacesConfigParserImpl(saxParser, resolveEntities);
	}
}
