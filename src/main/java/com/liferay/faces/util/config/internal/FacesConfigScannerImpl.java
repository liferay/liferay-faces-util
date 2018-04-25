/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
import java.util.Enumeration;
import java.util.List;

import javax.faces.application.ViewHandler;
import javax.faces.webapp.FacesServlet;
import javax.xml.parsers.SAXParser;

import com.liferay.faces.util.config.ConfiguredServlet;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.config.WebConfig;
import com.liferay.faces.util.internal.CloseableUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.internal.ProductMojarraImpl;


/**
 * @author  Neil Griffin
 */
public class FacesConfigScannerImpl implements FacesConfigScanner {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesConfigScannerImpl.class);

	// Private Constants
	private static final String FACES_CONFIG_META_INF_PATH = "META-INF/faces-config.xml";
	private static final String FACES_CONFIG_WEB_INF_PATH = "/WEB-INF/faces-config.xml";
	private static final String FACES_SERVLET = "Faces Servlet";
	private static final String FACES_SERVLET_FQCN = FacesServlet.class.getName();
	private static final String MOJARRA_CONFIG_PATH = "com/sun/faces/jsf-ri-runtime.xml";

	// Private Data Members
	private ClassLoader classLoader;
	private boolean resolveEntities;
	private ResourceReader resourceReader;
	private SAXParser saxParser;
	private WebConfig webConfig;

	public FacesConfigScannerImpl(ClassLoader classLoader, ResourceReader resourceReader, SAXParser saxParser,
		boolean resolveEntities, WebConfig webConfig) {
		this.classLoader = classLoader;
		this.saxParser = saxParser;
		this.resourceReader = resourceReader;
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

			FacesConfigDescriptorParser facesConfigDescriptorParser = newFacesConfigDescriptorParser();

			// Parse the WEB-INF/faces-config.xml descriptor. Gathering absolute-ordering, if any.
			FacesConfigDescriptor webInfFacesConfigDescriptor;
			inputStream = resourceReader.getResourceAsStream(FACES_CONFIG_WEB_INF_PATH);
			webInfFacesConfigDescriptor = facesConfigDescriptorParser.parse(inputStream, FACES_CONFIG_WEB_INF_PATH);
			inputStream.close();

			// First, parse the Mojarra configuration found in the classpath.
			Enumeration<URL> mojarraConfigURLs = classLoader.getResources(MOJARRA_CONFIG_PATH);

			// Since the FactoryExtensionFinder has not been initialized, create a new Mojarra Product manually,
			// to determine if Mojarra is present.
			final Product MOJARRA = new ProductMojarraImpl();

			if (MOJARRA.isDetected() && ((mojarraConfigURLs == null) || !mojarraConfigURLs.hasMoreElements())) {
				logger.warn("{0} not found.", MOJARRA_CONFIG_PATH);
			}

			if (mojarraConfigURLs != null) {

				boolean processedMojarraConfig = false;

				while (mojarraConfigURLs.hasMoreElements()) {
					URL mojarraConfigURL = mojarraConfigURLs.nextElement();

					if (processedMojarraConfig) {
						logger.debug("Skipping Mojarra config: [{0}]", mojarraConfigURL);
					}
					else {
						logger.debug("Processing Mojarra config: [{0}]", mojarraConfigURL);

						FacesConfigParser mojarraConfigParser = new FacesConfigParserImpl(saxParser, resolveEntities);
						inputStream = mojarraConfigURL.openStream();

						try {
							facesConfig = mojarraConfigParser.parse(inputStream, facesConfig);
						}
						catch (IOException e) {
							logger.error(e);
						}

						inputStream.close();
						processedMojarraConfig = true;
					}
				}
			}

			FacesConfigParser facesConfigParser = newFacesConfigParser();

			// Next, parse all of the META-INF/faces-config.xml files found in the classpath.
			Enumeration<URL> facesConfigURLs = classLoader.getResources(FACES_CONFIG_META_INF_PATH);
			List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();

			if (facesConfigURLs != null) {

				// Build up a semi-sorted list of faces-config.xml descriptor files, ensuring that
				// liferay-faces-bridge-impl.jar!META-INF/faces-config.xml is ordered first and that
				// liferay-faces-util.jar!META-INF/faces-config.xml is ordered second.
				// (Note that the JSF 2.0 <ordering> element is not yet supported.)
				while (facesConfigURLs.hasMoreElements()) {

					URL facesConfigURL = facesConfigURLs.nextElement();
					logger.debug("Pre-processing faces-config: [{0}]", facesConfigURL);
					inputStream = facesConfigURL.openStream();

					FacesConfigDescriptor facesConfigDescriptor = facesConfigDescriptorParser.parse(inputStream,
							facesConfigURL);

					facesConfigDescriptors.add(facesConfigDescriptor);

					inputStream.close();
				}

				// Sort the faces configuration files in accord with
				// javax.faces-api-2.2-FINAL_JSF_20130320_11.4.8_Ordering_of_Artifacts
				List<FacesConfigDescriptor> orderedConfigs = getOrderedConfigs(facesConfigDescriptors,
						webInfFacesConfigDescriptor);

				for (FacesConfigDescriptor config : orderedConfigs) {

					String urlString = config.getURL();
					URL url = new URL(urlString);
					logger.debug("Post-processing faces-config: [{0}]", url);

					inputStream = url.openStream();

					try {
						facesConfig = facesConfigParser.parse(inputStream, facesConfig);
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

			// Second, parse the WEB-INF/faces-config.xml descriptor. Any entries made here will take
			// precedence over those found previously.
			inputStream = resourceReader.getResourceAsStream(FACES_CONFIG_WEB_INF_PATH);

			if (inputStream != null) {
				logger.debug("Processing faces-config: [{0}]", FACES_CONFIG_WEB_INF_PATH);
				facesConfig = facesConfigParser.parse(inputStream, facesConfig);
				inputStream.close();
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

	protected List<FacesConfigDescriptor> getOrderedConfigs(List<FacesConfigDescriptor> facesConfigDescriptors,
		FacesConfigDescriptor webInfFacesConfig) throws Exception {

		if (facesConfigDescriptors.size() > 1) {

			List<String> absoluteOrdering = webInfFacesConfig.getAbsoluteOrdering();

			if (absoluteOrdering == null) {
				logger.debug("Ordering faces-config descriptors");
				facesConfigDescriptors = OrderingUtil.getOrder(facesConfigDescriptors);
			}
			else {
				logger.debug("Ordering faces-config descriptors: absoluteOrdering=[{0}]", absoluteOrdering);
				facesConfigDescriptors = OrderingUtil.getOrder(facesConfigDescriptors, absoluteOrdering);
			}
		}

		return facesConfigDescriptors;
	}

	protected SAXParser getSAXParser() {
		return saxParser;
	}

	protected FacesConfigDescriptorParser newFacesConfigDescriptorParser() {
		return new FacesConfigDescriptorParserImpl(saxParser, resolveEntities);
	}

	protected FacesConfigParser newFacesConfigParser() {
		return new FacesConfigParserImpl(saxParser, resolveEntities);
	}
}
