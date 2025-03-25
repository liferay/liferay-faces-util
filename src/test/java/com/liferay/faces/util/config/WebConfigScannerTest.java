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
package com.liferay.faces.util.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;

import org.xml.sax.SAXException;

import com.liferay.faces.util.config.internal.WebConfigScanner;
import com.liferay.faces.util.config.internal.WebConfigScannerImpl;
import com.liferay.faces.util.xml.ConcurrentSAXParserFactory;

import junit.framework.Assert;


/**
 * @author  Kyle Stiemann
 */
public class WebConfigScannerTest {

	@Test
	public void testWebConfigScanner() throws ParserConfigurationException, SAXException, IOException {

		SAXParserFactory saxParserFactory = ConcurrentSAXParserFactory.newInstance();
		saxParserFactory.setValidating(false);
		saxParserFactory.setNamespaceAware(true);

		// Obtain a SAX Parser from the factory.
		SAXParser saxParser = saxParserFactory.newSAXParser();

		// Scan all the web.xml and web-fragment.xml descriptors in the classpath.
		final ClassLoader CLASS_LOADER = new ClassLoaderGetResourceFromFolderImpl(Thread.currentThread()
				.getContextClassLoader(), "web-config-scanner-test", "web-config-scanner-test/0",
				"web-config-scanner-test/1");
		WebConfigScanner webConfigScanner = new WebConfigScannerImpl(CLASS_LOADER,
				new ResourceReaderTestImpl(CLASS_LOADER), saxParser, false);
		WebConfig webConfig = webConfigScanner.scan();

		List<ConfiguredServlet> configuredServlets = webConfig.getConfiguredServlets();
		Assert.assertEquals(1, configuredServlets.size());

		ConfiguredServlet configuredServlet = configuredServlets.get(0);
		Assert.assertEquals("Faces Servlet", configuredServlet.getServletName());
		Assert.assertEquals("jakarta.faces.webapp.FacesServlet", configuredServlet.getServletClass());
		Assert.assertNull(configuredServlet.getMultiPartConfig());

		List<ConfiguredServletMapping> configuredServletMappings = webConfig.getConfiguredServletMappings();
		Assert.assertEquals(1, configuredServletMappings.size());

		ConfiguredServletMapping configuredServletMapping = configuredServletMappings.get(0);
		Assert.assertEquals("Faces Servlet", configuredServletMapping.getServletName());
		Assert.assertEquals("*.xhtml", configuredServletMapping.getUrlPattern());
		Assert.assertEquals(".xhtml", configuredServletMapping.getExtension());
		Assert.assertTrue(configuredServletMapping.isExtensionMapped());
		Assert.assertFalse(configuredServletMapping.isImplicit());
		Assert.assertFalse(configuredServletMapping.isPathMapped());
		Assert.assertFalse(configuredServletMapping.isPathMapped());
		Assert.assertTrue(configuredServletMapping.isMatch("asdf.xhtml"));
		Assert.assertFalse(configuredServletMapping.isMatch("xhtml/asdf"));
		Assert.assertNull(configuredServletMapping.getServletPath());

		// FACES-3265 WebConfigParserImpl doesn't obtain context params
		Map<String, String> configuredContextParams = webConfig.getConfiguredContextParams();
		Assert.assertEquals("com.liferay.faces.alloy.tagdecorator.internal.TagDecoratorAlloyImpl",
			configuredContextParams.get("jakarta.faces.FACELETS_DECORATORS"));
		Assert.assertEquals("true",
			configuredContextParams.get("jakarta.faces.ALWAYS_PERFORM_VALIDATION_WHEN_REQUIRED_IS_TRUE"));
		Assert.assertEquals("Production", configuredContextParams.get("jakarta.faces.PROJECT_STAGE"));
		Assert.assertEquals("/WEB-INF/resources",
			configuredContextParams.get("jakarta.faces.WEBAPP_RESOURCES_DIRECTORY"));
		Assert.assertNull(configuredContextParams.get("asdf"));
	}
}
