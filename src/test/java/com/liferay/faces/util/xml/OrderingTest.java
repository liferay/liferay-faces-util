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
package com.liferay.faces.util.xml;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Assert;
import org.junit.Test;

import com.liferay.faces.util.config.internal.FacesConfigDescriptor;
import com.liferay.faces.util.config.internal.FacesConfigDescriptorParser;
import com.liferay.faces.util.config.internal.FacesConfigDescriptorParserImpl;
import com.liferay.faces.util.config.internal.Ordering;
import com.liferay.faces.util.config.internal.OrderingBeforeAndAfterException;
import com.liferay.faces.util.config.internal.OrderingCircularDependencyException;
import com.liferay.faces.util.config.internal.OrderingUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
public class OrderingTest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OrderingTest.class);

	// Private Constants
	private static final String META_INF_FACES_CONFIG_XML = "META-INF/faces-config.xml";
	private static final String MOJARRA_CONFIG_XML = "com/sun/faces/jsf-ri-runtime.xml";
	private static final String WEB_INF_FACES_CONFIG_XML = "WEB-INF/faces-config.xml";

	public static void parseConfigurationResources(String testCase, List<FacesConfigDescriptor> facesConfigDescriptors,
		String configPath) {

		try {
			SAXParserFactory saxParserFactory = ConcurrentSAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
			FacesConfigDescriptorParser facesConfigDescriptorParser = new FacesConfigDescriptorParserImpl(saxParser,
					false);
			ClassLoader classLoader = OrderingTest.class.getClassLoader();
			Enumeration<URL> orderingUrls = classLoader.getResources(testCase);

			if (orderingUrls.hasMoreElements()) {
				URL orderingUrl = orderingUrls.nextElement();
				File orderingDirectory = new File(orderingUrl.toURI());
				File[] configurationDirectories = orderingDirectory.listFiles();

				if (configurationDirectories != null) {

					for (File directory : configurationDirectories) {

						// config path should be MOJARRA_CONFIG_XML, META_INF_FACES_CONFIG_XML, or
						// WEB_INF_FACES_CONFIG_XML
						String resourcePath = testCase + "/" + directory.getName() + "/" + configPath;
						Enumeration<URL> facesConfigUrls = classLoader.getResources(resourcePath);

						while (facesConfigUrls.hasMoreElements()) {
							URL facesConfigURL = facesConfigUrls.nextElement();

							logger.trace("parseConfigurationResources: " + facesConfigURL.toString());

							InputStream inputStream = facesConfigURL.openStream();
							FacesConfigDescriptor facesConfigDescriptor = facesConfigDescriptorParser.parse(inputStream,
									facesConfigURL);

							logger.trace("parseConfigurationResources: >" + facesConfigDescriptor.getName() + "<");
							facesConfigDescriptors.add(facesConfigDescriptor);
							inputStream.close();
						}
					}
				}
			}
		}
		catch (Exception e) {
			throw new AssertionError("An unexpected exception was thrown: ", e);
		}
	}

	public static List<List<String>> permute(List<String> list) {

		if (list.size() == 0) {
			List<List<String>> newLols = new ArrayList<List<String>>();
			newLols.add(new ArrayList<String>());

			return newLols;
		}

		List<List<String>> lols = new ArrayList<List<String>>();

		String first = list.remove(0);

		List<List<String>> resultingLols = permute(list);

		for (List<String> resultingList : resultingLols) {

			for (int index = 0; index <= resultingList.size(); index++) {
				List<String> listOfStrings = new ArrayList<String>(resultingList);
				listOfStrings.add(index, first);
				lols.add(listOfStrings);
			}

		}

		return lols;
	}

	private static String[] extractNames(List<FacesConfigDescriptor> facesConfigDescriptors) {
		String[] extractedNames = new String[facesConfigDescriptors.size()];
		int i = 0;

		for (FacesConfigDescriptor facesConfigDescriptor : facesConfigDescriptors) {
			extractedNames[i] = facesConfigDescriptor.getName();
			i++;
		}

		return extractedNames;
	}

	// This fails intermittently without the preSort
	@Test
	public void test00_0() throws Exception {

		logger.trace("test00_0: beginning ...");

		List<FacesConfigDescriptor> mojarraConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/00", mojarraConfigDescriptors, MOJARRA_CONFIG_XML);

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/00", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/00", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor webInfFacesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = webInfFacesConfig.getAbsoluteOrdering();
		String[] originalOrder = extractNames(facesConfigDescriptors);
		Assert.assertNull("test00_0: absoluteOrdering != null. It should be null.", absoluteOrdering);

		List<FacesConfigDescriptor> order = OrderingUtil.getOrderedFacesConfigDescriptors(mojarraConfigDescriptors.get(
					0), facesConfigDescriptors, webInfFacesConfig);

		String[] orderedNames = extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("Mojarra", "LiferayFacesUtil", "LiferayFacesBridge", "prettyfaces",
				"LiferayFacesAlloy", "");

		// solutions:
		// LiferayFacesUtil, LiferayFacesBridge, prettyfaces, LiferayFacesAlloy,
		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test00_0: Passed");
		logger.trace(message);

	}

	// This fails intermittently without the preSort
	@Test
	public void test00_1() throws Exception {

		logger.trace("test00_1: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/04", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// extractedNames = [ICEfacesAce, LiferayFacesBridge, LiferayFacesAlloy, LiferayFacesUtil, ICEfacesCore]
// Map<String, FacesConfigDescriptor> configMap = Ordering.getConfigMap(facesConfigDescriptors);
// List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
// temp.add(configMap.get("ICEfacesAce"));
// temp.add(configMap.get("LiferayFacesBridge"));
// temp.add(configMap.get("LiferayFacesAlloy"));
// temp.add(configMap.get("LiferayFacesUtil"));
// temp.add(configMap.get("ICEfacesCore"));
// facesConfigDescriptors = temp;

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/04", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor webInfFacesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = webInfFacesConfig.getAbsoluteOrdering();
		String[] originalOrder = extractNames(facesConfigDescriptors);
		Assert.assertNull("test00_1: absoluteOrdering != null. It should be null.", absoluteOrdering);

		List<FacesConfigDescriptor> order = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors,
				webInfFacesConfig);

		String[] orderedNames = extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> possibility1 = Arrays.asList("ICEfacesCore", "LiferayFacesUtil", "LiferayFacesBridge",
				"LiferayFacesAlloy", "ICEfacesAce", "");
		List<String> possibility2 = Arrays.asList("ICEfacesCore", "ICEfacesAce", "LiferayFacesUtil",
				"LiferayFacesBridge", "LiferayFacesAlloy", "");

		// some solutions:
		// [ICEfacesCore, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, ICEfacesAce, ]
		// [ICEfacesCore, ICEfacesAce, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, ]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test00_1: Passed");
		logger.trace(message);

	}

	// This fails intermittently without the preSort
	@Test
	public void test00_2() throws Exception {

		logger.trace("test00_2: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/05", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// extractedNames = [ICEfacesAce, LiferayFacesBridge, LiferayFacesAlloy, LiferayFacesUtil, ICEfacesCore]
// Map<String, FacesConfigDescriptor> configMap = Ordering.getConfigMap(facesConfigDescriptors);
// List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
// temp.add(configMap.get("ICEfacesAce"));
// temp.add(configMap.get("LiferayFacesBridge"));
// temp.add(configMap.get("LiferayFacesAlloy"));
// temp.add(configMap.get("LiferayFacesUtil"));
// temp.add(configMap.get("ICEfacesCore"));
// facesConfigDescriptors = temp;

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/05", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor webInfFacesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = webInfFacesConfig.getAbsoluteOrdering();
		String[] originalOrder = extractNames(facesConfigDescriptors);
		Assert.assertNull("test00_2: absoluteOrdering != null. It should be null.", absoluteOrdering);

		List<FacesConfigDescriptor> order = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors,
				webInfFacesConfig);

		String[] orderedNames = extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> possibility1 = Arrays.asList("ICEfacesCore", "LiferayFacesUtil", "LiferayFacesBridge",
				"LiferayFacesAlloy", "richfaces_core", "ICEfacesAce", "");
		List<String> possibility2 = Arrays.asList("ICEfacesCore", "LiferayFacesUtil", "LiferayFacesBridge",
				"LiferayFacesAlloy", "ICEfacesAce", "richfaces_core", "");
		List<String> possibility3 = Arrays.asList("ICEfacesCore", "LiferayFacesUtil", "LiferayFacesBridge",
				"richfaces_core", "LiferayFacesAlloy", "ICEfacesAce", "");

		// some solutions:
		// [ICEfacesCore, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, richfaces_core, ICEfacesAce, ]
		// [ICEfacesCore, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, ICEfacesAce, richfaces_core, ]
		// [ICEfacesCore, LiferayFacesUtil, LiferayFacesBridge, richfaces_core, LiferayFacesAlloy, ICEfacesAce, ]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
				actually.equals(possibility3));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n       or: " + possibility3 + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test00_2: Passed");
		logger.trace(message);

	}

	@Test
	public void test00_3() throws Exception {

		logger.trace("test00_1: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/06", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// extractedNames = [ICEfacesAce, LiferayFacesBridge, LiferayFacesAlloy, LiferayFacesUtil, ICEfacesCore]
// Map<String, FacesConfigDescriptor> configMap = Ordering.getConfigMap(facesConfigDescriptors);
// List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
// temp.add(configMap.get("ICEfacesAce"));
// temp.add(configMap.get("LiferayFacesBridge"));
// temp.add(configMap.get("LiferayFacesAlloy"));
// temp.add(configMap.get("LiferayFacesUtil"));
// temp.add(configMap.get("ICEfacesCore"));
// facesConfigDescriptors = temp;

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/04", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor webInfFacesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = webInfFacesConfig.getAbsoluteOrdering();
		String[] originalOrder = extractNames(facesConfigDescriptors);
		Assert.assertNull("test00_1: absoluteOrdering != null. It should be null.", absoluteOrdering);

		List<FacesConfigDescriptor> order = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors,
				webInfFacesConfig);

		String[] orderedNames = extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> possibility1 = Arrays.asList("ICEfacesCore", "LiferayFacesUtil", "LiferayFacesBridge",
				"LiferayFacesAlloy", "ICEfacesAce", "");
		List<String> possibility2 = Arrays.asList("ICEfacesCore", "ICEfacesAce", "LiferayFacesUtil",
				"LiferayFacesBridge", "LiferayFacesAlloy", "");

		// some solutions:
		// [ICEfacesCore, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, ICEfacesAce, ]
		// [ICEfacesCore, ICEfacesAce, LiferayFacesUtil, LiferayFacesBridge, LiferayFacesAlloy, ]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test00_3: Passed");
		logger.trace(message);

	}

	@Test
	public void test01_fromSpec() throws Exception {

		logger.trace("test01_fromSpec: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/01", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/01", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor webInfFacesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = webInfFacesConfig.getAbsoluteOrdering();
		String[] originalOrder = extractNames(facesConfigDescriptors);
		Assert.assertNull("test01_fromSpec: absoluteOrdering != null. It should be null.", absoluteOrdering);

		List<FacesConfigDescriptor> order = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors,
				webInfFacesConfig);

		String[] orderedNames = extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("C", "B", "A", "my");

		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test01_fromSpec: Passed");
		logger.trace(message);

	}

	@Test
	public void test02_fromSpec() throws Exception {

		logger.trace("test02_fromSpec: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/02", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/02", webInfFacesConfigDescriptors, WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor webInfFacesConfigDescriptor = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = webInfFacesConfigDescriptor.getAbsoluteOrdering();
		String[] originalOrder = extractNames(facesConfigDescriptors);
		Assert.assertNotNull("test02_fromSpec: absoluteOrdering == null. It should not be null.", absoluteOrdering);

		List<FacesConfigDescriptor> order = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors,
				webInfFacesConfigDescriptor);

		String[] orderedNames = extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("C", "A", "my");

		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test02_fromSpec: Passed");
		logger.trace(message);

	}

	@Test
	public void test03_getAbsoluteOrdering() throws Exception {

		logger.trace("test03_getAbsoluteOrdering: beginning ...");

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/GetAbsoluteOrdering_01", webInfFacesConfigDescriptors,
			WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor webInfFacesConfig = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = webInfFacesConfig.getAbsoluteOrdering();

//      List<String> actually = Arrays.asList(absoluteOrdering.get(0), absoluteOrdering.get(1),
//              absoluteOrdering.get(2));
		List<String> actually = absoluteOrdering;
		List<String> expected = Arrays.asList("a", "b", "c");

		String message = "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test03_getAbsoluteOrdering: " + message);

		webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/GetAbsoluteOrdering_02", webInfFacesConfigDescriptors,
			WEB_INF_FACES_CONFIG_XML);

		webInfFacesConfig = webInfFacesConfigDescriptors.get(0);
		absoluteOrdering = webInfFacesConfig.getAbsoluteOrdering();

//      actually = Arrays.asList(absoluteOrdering.get(0), absoluteOrdering.get(1), absoluteOrdering.get(2),
//              absoluteOrdering.get(3));
		actually = absoluteOrdering;
		expected = Arrays.asList("a", "b", Ordering.OTHERS, "c");

		message = "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.trace("test03_getAbsoluteOrdering: " + message);

		webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/GetAbsoluteOrdering_03", webInfFacesConfigDescriptors,
			WEB_INF_FACES_CONFIG_XML);

		webInfFacesConfig = webInfFacesConfigDescriptors.get(0);
		absoluteOrdering = webInfFacesConfig.getAbsoluteOrdering();

		Assert.assertTrue("absoluteOrdering != null. But it should be null.", absoluteOrdering == null);

		logger.info("test03_getAbsoluteOrdering: Passed\n");
	}

	@Test
	public void test04_absoluteOrdering() throws Exception {

		logger.trace("test04_absoluteOrdering: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AbsoluteOrdering_01", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// Parse the WEB-INF/faces-config.xml to get any absolute-ordering, if any.
		List<FacesConfigDescriptor> webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AbsoluteOrdering_01", webInfFacesConfigDescriptors,
			WEB_INF_FACES_CONFIG_XML);

		FacesConfigDescriptor webInfFacesConfigDescriptor = webInfFacesConfigDescriptors.get(0);
		List<String> absoluteOrdering = webInfFacesConfigDescriptor.getAbsoluteOrdering();
		String[] originalOrder = extractNames(facesConfigDescriptors);
		Assert.assertNotNull("test04_absoluteOrdering: absoluteOrdering == null. It should not be null.",
			absoluteOrdering);

		List<FacesConfigDescriptor> order = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors,
				webInfFacesConfigDescriptor);
		String[] orderedNames = extractNames(order);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("a", "b", "c", "my");

		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.trace("test04_absoluteOrdering: " + message);

		webInfFacesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AbsoluteOrdering_02", webInfFacesConfigDescriptors,
			WEB_INF_FACES_CONFIG_XML);

		webInfFacesConfigDescriptor = webInfFacesConfigDescriptors.get(0);

		originalOrder = extractNames(facesConfigDescriptors);

		Assert.assertNotNull("test04_absoluteOrdering: absoluteOrdering == null. It should not be null.",
			absoluteOrdering);
		logger.trace("test04_absoluteOrdering: absoluteOrdering = " + absoluteOrdering);
		order = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors,
				webInfFacesConfigDescriptor);

		orderedNames = extractNames(order);

		// solutions:
		// [a, b, e, d, f, c, my]
		// [a, b, f, e, d, c, my]
		// [a, b, f, d, e, c, my]
		// [a, b, d, e, f, c, my]
		// [a, b, d, f, e, c, my]
		// [a, b, e, f, d, c, my]

		original = Arrays.asList(originalOrder);
		actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("a", "b", "e", "d", "f", "c", "my");
		List<String> possibility2 = Arrays.asList("a", "b", "f", "e", "d", "c", "my");
		List<String> possibility3 = Arrays.asList("a", "b", "f", "d", "e", "c", "my");
		List<String> possibility4 = Arrays.asList("a", "b", "d", "e", "f", "c", "my");
		List<String> possibility5 = Arrays.asList("a", "b", "d", "f", "e", "c", "my");
		List<String> possibility6 = Arrays.asList("a", "b", "e", "f", "d", "c", "my");

		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
				actually.equals(possibility3) || actually.equals(possibility4) || actually.equals(possibility5) ||
				actually.equals(possibility6));
		message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n       or: " + possibility3 + "\n       or: " + possibility4 + "\n       or: " + possibility5 +
			"\n       or: " + possibility6 + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test04_absoluteOrdering: Passed");
		logger.trace(message);

	}

	@Test
	public void test05_AafterCOthers_BbeforeOthers_CafterOthers_FbeforeCOthers() throws Exception {

		logger.trace("test05_afterAfterOthers: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterCOthers_BbeforeOthers_CafterOthers_FbeforeCOthers",
			facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("f", "b", "e", "d", "c", "a");
		List<String> possibility2 = Arrays.asList("f", "b", "d", "e", "c", "a");
		List<String> possibility3 = Arrays.asList("b", "f", "d", "e", "c", "a");
		List<String> possibility4 = Arrays.asList("b", "f", "e", "d", "c", "a");

		// some solutions:
		// [f, b, e, d, c, a]
		// [f, b, d, e, c, a]
		// [b, f, d, e, c, a]
		// [b, f, e, d, c, a]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
				actually.equals(possibility3) || actually.equals(possibility4));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n       or: " + possibility3 + "\n       or: " + possibility4 + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test05_AafterCOthers_BbeforeOthers_CafterOthers_FbeforeCOthers: Passed");
		logger.trace(message);

	}

	// This fails intermittently without the preSort
	@Test
	public void test06_CafterDbeforeOthers() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/CafterDbeforeOthers", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("d", "c", "a", "b");
		List<String> possibility2 = Arrays.asList("d", "c", "b", "a");

		// some solutions:
		// [d, c, a, b]
		// [d, c, b, a]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test06_CafterDbeforeOthers: Passed");
		logger.trace(message);

	}

	// This fails intermittently without the preSort
	@Test
	public void test07_eachAfterTheNext() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/EachAfterTheNext", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("d", "c", "b", "a");

		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test07_eachAfterTheNext: Passed");
		logger.trace(message);

	}

	// This fails intermittently without the preSort
	@Test
	public void test08_eachBeforeThePrevious() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/EachBeforeThePrevious", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("d", "c", "b", "a");

		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test08_eachBeforeThePrevious: Passed");
		logger.trace(message);

	}

	@Test
	public void test10_AafterBbeforeB_CbeforeOthers() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterBbeforeB_CbeforeOthers", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		try {
			OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);
			Assert.fail("Before and after exception should have been thrown");
		}
		catch (Exception e) {

			Assert.assertTrue("An exception stating 'both before and after' should have been thrown. Instead, got: " +
				e.getMessage() + "\n", e instanceof OrderingBeforeAndAfterException);
			logger.info("test10_AafterBbeforeB_CbeforeOthers: Passed\n Expected exception thrown: e.getMessage() = " +
				e.getMessage());
		}

	}

	@Test
	public void test11_CafterOthersbeforeB() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/CafterOthersbeforeB", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("a", "d", "c", "b");
		List<String> possibility2 = Arrays.asList("d", "a", "c", "b");

		// some solutions:
		// [a, d, c, b]
		// [d, a, c, b]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test11_CafterOthersbeforeB: Passed");
		logger.trace(message);

	}

	@Test
	public void test12_BafterC_CafterB() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/BafterC_CafterB", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		try {
			OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);
			Assert.fail("Circular ordering exception should have been thrown");
		}
		catch (Exception e) {

			Assert.assertTrue(
				"An exception stating 'circular dependencies detected' should have been thrown. Instead, received: " +
				e.getMessage() + "\n", e instanceof OrderingCircularDependencyException);
			logger.info("test10_AafterBbeforeB_CbeforeOthers: Passed\n Expected exception thrown: e.getMessage() = " +
				e.getMessage());
		}

	}

	@Test
	public void test13_circularFollowingAfterIds() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/CircularFollowingAfter", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// checkForSpecExceptions: names = [b, d, c, a] ... this order reduces the circle to
		// Ordering.BOTH_BEFORE_AND_AFTER Map<String, FacesConfigDescriptor> configMap =
		// Ordering.getConfigMap(facesConfigDescriptors); List<FacesConfigDescriptor> temp = new
		// ArrayList<FacesConfigDescriptor>(); temp.add(configMap.get("b")); temp.add(configMap.get("d"));
		// temp.add(configMap.get("c")); temp.add(configMap.get("a")); facesConfigDescriptors = temp;

		try {
			OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);
			Assert.fail("Circular ordering exception should have been thrown");
		}
		catch (Exception e) {

			if (e instanceof OrderingCircularDependencyException) {

				// this is the expected result
				logger.info("test13_circularFollowingAfterIds: Passed\n Expected exception thrown: e.getMessage() = " +
					e.getMessage());
			}
			else if (e instanceof OrderingBeforeAndAfterException) {

				// this is the expected result
				logger.info(
					"test13_circularFollowingAfterIds: Passed\n A circular order can quickly reduce to 'both before and after' depending on the inital order.");
				logger.info("test13_circularFollowingAfterIds: Passed\n Expected exception thrown: e.getMessage() = " +
					e.getMessage());
			}
			else {
				throw new AssertionError(
					"An exception stating 'circular dependencies detected' or 'both before and after' should have been thrown. Instead, got: ",
					e);
			}
		}

	}

	@Test
	public void test14_circularFollowingBeforeIds() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/CircularFollowingBefore", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// checkForSpecExceptions: names = [d, b, a, c] ... this order reduces the circle to
		// Ordering.BOTH_BEFORE_AND_AFTER Map<String, FacesConfigDescriptor> configMap =
		// Ordering.getConfigMap(facesConfigDescriptors); List<FacesConfigDescriptor> temp = new
		// ArrayList<FacesConfigDescriptor>(); temp.add(configMap.get("d")); temp.add(configMap.get("b"));
		// temp.add(configMap.get("a")); temp.add(configMap.get("c")); facesConfigDescriptors = temp;

		try {
			OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);
			Assert.fail("Circular ordering exception should have been thrown");
		}
		catch (Exception e) {

			if (e instanceof OrderingCircularDependencyException) {

				// this is the expected result
				logger.info("test14_circularFollowingBeforeIds: Passed\n Expected exception thrown: e.getMessage() = " +
					e.getMessage());
			}
			else if (e instanceof OrderingBeforeAndAfterException) {

				// this is the expected result
				logger.info(
					"test14_circularFollowingBeforeIds: Passed\n A circular order can quickly reduce to 'both before and after' depending on the inital order.");
				logger.info("test14_circularFollowingBeforeIds: Passed\n Expected exception thrown: e.getMessage() = " +
					e.getMessage());
			}
			else {
				throw new AssertionError(
					"An exception stating 'circular dependencies detected' or 'both before and after' should have been thrown. Instead, got: ",
					e);
			}
		}

	}

	@Test
	public void test15_BafterC_CbeforeB() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/BafterC_CbeforeB", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> possibility1 = Arrays.asList("a", "c", "d", "b");
		List<String> possibility2 = Arrays.asList("c", "d", "a", "b");
		List<String> possibility3 = Arrays.asList("c", "a", "d", "b");

		// some solutions:
		// [a, c, d, b]
		// [c, d, a, b]
		// [c, a, d, b]
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
				actually.equals(possibility3));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n       or: " + possibility3 + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test15_BafterC_CbeforeB: Passed");
		logger.trace(message);

	}

	@Test
	public void test16_AafterB_CbeforeOthers() throws Exception {

		logger.trace("test16_AafterB_CbeforeOthers: beginning ...");

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterB_CbeforeOthers", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> possibility1 = Arrays.asList("c", "b", "d", "a");
		List<String> possibility2 = Arrays.asList("c", "d", "b", "a");

		// solutions:
		// "c", "b", "d", "a"
		// "c", "d", "b", "a"
		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test16_AafterB_CbeforeOthers: Passed");
		logger.trace(message);

	}

	// This fails without the preSort
	// not submitted as an issue in mojarra, but this is the first one we created to check mojarra against:
	// https://java.net/jira/browse/JAVASERVERFACES-3757
	@Test
	public void test18_AafterC_BafterCbeforeOthers_CafterDbeforeB_EafterD() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterC_BafterCbeforeOthers_CafterDbeforeB_EafterD",
			facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// extractedNames = [ICEfacesAce, LiferayFacesBridge, LiferayFacesAlloy, LiferayFacesUtil, ICEfacesCore]
		Map<String, FacesConfigDescriptor> configMap = OrderingUtil.getConfigMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configMap.get("e"));
		temp.add(configMap.get("d"));
		temp.add(configMap.get("b"));
		temp.add(configMap.get("c"));
		temp.add(configMap.get("f"));
		temp.add(configMap.get("a"));
		facesConfigDescriptors = temp;

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("d", "c", "b", "f", "a", "e");

		// solution:
		// d, c, b, f, a, e
		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test18_AafterC_BafterCbeforeOthers_CafterDbeforeB_EafterD: Passed");
		logger.trace(message);

	}

	// This fails without the preSort
	@Test
	public void test19_FbeforeD_EbeforeDafterOthers_DbeforeCafterE_BbeforeC() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/FbeforeD_EbeforeDafterOthers_DbeforeCafterE_BbeforeC",
			facesConfigDescriptors, META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		// [e, a, f, b, d, c]
		Map<String, FacesConfigDescriptor> configMap = OrderingUtil.getConfigMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configMap.get("e"));
		temp.add(configMap.get("a"));
		temp.add(configMap.get("f"));
		temp.add(configMap.get("b"));
		temp.add(configMap.get("d"));
		temp.add(configMap.get("c"));
		facesConfigDescriptors = temp;

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		// some solutions:
		// [f, e, d, b, a, c]
		// [f, e, d, b, c, a]
		// [a, b, f, e, d, c]

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("f", "e", "d", "b", "a", "c");
		List<String> possibility2 = Arrays.asList("f", "e", "d", "b", "c", "a");
		List<String> possibility3 = Arrays.asList("a", "b", "f", "e", "d", "c");

		boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
				actually.equals(possibility3));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
			"\n       or: " + possibility3 + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, assertion);
		logger.info("test19_FbeforeD_EbeforeDafterOthers_DbeforeCafterE_BbeforeC: Passed");
		logger.trace(message);

	}

	// TODO
	@Test
	public void test20_beforeCafterOthers_BbeforeOthers_DafterOthers_EbeforeOthers_permute() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/_beforeCafterOthers_BbeforeOthers_DafterOthers_EbeforeOthers",
			facesConfigDescriptors, META_INF_FACES_CONFIG_XML);

		Map<String, FacesConfigDescriptor> configMap = OrderingUtil.getConfigMap(facesConfigDescriptors);

		List<String> strList = new ArrayList<String>();
		strList.add("");
		strList.add("b");
		strList.add("c");
		strList.add("d");
		strList.add("e");
		strList.add("f");

		List<List<String>> lists = permute(strList);

		String message = "";

		for (List<String> list : lists) {

			List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();

			for (String i : list) {
				temp.add(configMap.get(i));
			}

			facesConfigDescriptors = temp;

			String[] originalOrder = extractNames(facesConfigDescriptors);
			facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

			// some solutions:
			// [B, E, F, , C, D]
			// [B, E, F, D, , C]
			// [e, b, f, , c, d]

			String[] orderedNames = extractNames(facesConfigDescriptors);

			List<String> original = Arrays.asList(originalOrder);
			List<String> actually = Arrays.asList(orderedNames);

			List<String> possibility1 = Arrays.asList("b", "e", "f", "", "c", "d");
			List<String> possibility2 = Arrays.asList("b", "e", "f", "d", "", "c");
			List<String> possibility3 = Arrays.asList("e", "b", "f", "", "c", "d");

			boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
					actually.equals(possibility3));
			message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
				"\n       or: " + possibility3 + "\n actually: " + actually + "\n";
			Assert.assertTrue(message, assertion);
			logger.trace(message);

		}

		logger.info("test20_beforeCafterOthers_BbeforeOthers_DafterOthers_EbeforeOthers_permute: Passed");
	}

	// TODO
	@Test
	public void test21_AafterOthers_BbeforeOthers_DafterOthers_EafterCbeforeOthers_permute() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterOthers_BbeforeOthers_DafterOthers_EafterCbeforeOthers_permute",
			facesConfigDescriptors, META_INF_FACES_CONFIG_XML);

		Map<String, FacesConfigDescriptor> configMap = OrderingUtil.getConfigMap(facesConfigDescriptors);

		List<String> strList = new ArrayList<String>();
		strList.add("a");
		strList.add("b");
		strList.add("c");
		strList.add("d");
		strList.add("e");
		strList.add("f");

		List<List<String>> lists = permute(strList);

		String message = "";

		for (List<String> list : lists) {

			List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();

			for (String i : list) {
				temp.add(configMap.get(i));
			}

			facesConfigDescriptors = temp;

			String[] originalOrder = extractNames(facesConfigDescriptors);
			facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

			// some solutions:
			// [B, C, E, F, A, D]
			// [B, C, E, F, D, A]
			// [C, E, B, F, A, D]
			// [C, E, B, F, D, A]

			String[] orderedNames = extractNames(facesConfigDescriptors);

			List<String> original = Arrays.asList(originalOrder);
			List<String> actually = Arrays.asList(orderedNames);

			List<String> possibility1 = Arrays.asList("b", "c", "e", "f", "a", "d");
			List<String> possibility2 = Arrays.asList("b", "c", "e", "f", "d", "a");
			List<String> possibility3 = Arrays.asList("c", "e", "b", "f", "a", "d");
			List<String> possibility4 = Arrays.asList("c", "e", "b", "f", "d", "a");

			boolean assertion = (actually.equals(possibility1) || actually.equals(possibility2) ||
					actually.equals(possibility3) || actually.equals(possibility4));
			message = "\n original: " + original + "\n expected: " + possibility1 + "\n       or: " + possibility2 +
				"\n       or: " + possibility3 + "\n       or: " + possibility4 + "\n actually: " + actually + "\n";
			Assert.assertTrue(message, assertion);
			logger.trace(message);
		}

		logger.info("test21_AafterOthers_BbeforeOthers_DafterOthers_EafterCbeforeOthers_permute: Passed");
	}

	// submitted as an issue in mojarra:
	// https://java.net/jira/browse/JAVASERVERFACES-3757
	@Test
	public void test_JAVASERVERFACES_3757_AafterD_BafterCbeforeOthers_CafterDbeforeB_startWithABCD() throws Exception {
		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterD_BafterCbeforeOthers_CafterDbeforeB", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		Map<String, FacesConfigDescriptor> configMap = OrderingUtil.getConfigMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configMap.get("a"));
		temp.add(configMap.get("b"));
		temp.add(configMap.get("c"));
		temp.add(configMap.get("d"));
		facesConfigDescriptors = temp;

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("d", "c", "b", "a");

		// solution:
		// ['d', 'c', 'b', 'a']
		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test_JAVASERVERFACES_3757_AafterD_BafterCbeforeOthers_CafterDbeforeB_startWithABCD: Passed");
		logger.trace(message);
	}

	// submitted as an issue in mojarra:
	// https://java.net/jira/browse/JAVASERVERFACES-3757
	@Test
	public void test_JAVASERVERFACES_3757_AafterD_BafterCbeforeOthers_CafterDbeforeB_startWithADBC() throws Exception {
		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/AafterD_BafterCbeforeOthers_CafterDbeforeB", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);
		Collections.shuffle(facesConfigDescriptors);

		Map<String, FacesConfigDescriptor> configMap = OrderingUtil.getConfigMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configMap.get("a"));
		temp.add(configMap.get("d"));
		temp.add(configMap.get("b"));
		temp.add(configMap.get("c"));
		facesConfigDescriptors = temp;

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);
		List<String> expected = Arrays.asList("d", "c", "b", "a");

		// solution:
		// ['d', 'c', 'b', 'a']
		String message = "\n original: " + original + "\n expected: " + expected + "\n actually: " + actually + "\n";
		Assert.assertTrue(message, expected.equals(actually));
		logger.info("test_JAVASERVERFACES_3757_AafterD_BafterCbeforeOthers_CafterDbeforeB_startWithADBC: Passed");
		logger.trace(message);
	}

	// submitted as an issue in mojarra:
	// https://java.net/jira/browse/JAVASERVERFACES-3757
	@Test
	public void test_JAVASERVERFACES_3757_Caftera_startWithCab() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/Caftera_startWithCab", facesConfigDescriptors, META_INF_FACES_CONFIG_XML);

		// Start with C, a,
		Map<String, FacesConfigDescriptor> configMap = OrderingUtil.getConfigMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configMap.get("C"));
		temp.add(configMap.get("a"));
		temp.add(configMap.get(""));
		facesConfigDescriptors = temp;

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		// a solution:
		// ['a', '', 'C']
		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("a", "", "C");

		boolean assertion = (actually.equals(possibility1));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n actually: " + actually +
			"\n";
		Assert.assertTrue(message, assertion);
		logger.info("test_JAVASERVERFACES_3757_Caftera_startWithCab: Passed");
		logger.trace(message);
	}

	// submitted as an issue in mojarra:
	// https://java.net/jira/browse/JAVASERVERFACES-3757
	@Test
	public void test_JAVASERVERFACES_3757_noOrdering_startWithCab() throws Exception {

		List<FacesConfigDescriptor> facesConfigDescriptors = new ArrayList<FacesConfigDescriptor>();
		parseConfigurationResources("ordering/noOrdering_startWithCab", facesConfigDescriptors,
			META_INF_FACES_CONFIG_XML);

		// Start with C, a,
		Map<String, FacesConfigDescriptor> configMap = OrderingUtil.getConfigMap(facesConfigDescriptors);
		List<FacesConfigDescriptor> temp = new ArrayList<FacesConfigDescriptor>();
		temp.add(configMap.get("C"));
		temp.add(configMap.get("a"));
		temp.add(configMap.get(""));
		facesConfigDescriptors = temp;

		String[] originalOrder = extractNames(facesConfigDescriptors);

		facesConfigDescriptors = OrderingUtil.getOrderedFacesConfigDescriptors(null, facesConfigDescriptors, null);

		String[] orderedNames = extractNames(facesConfigDescriptors);

		// a solution:
		// ['C', 'a', '']
		List<String> original = Arrays.asList(originalOrder);
		List<String> actually = Arrays.asList(orderedNames);

		List<String> possibility1 = Arrays.asList("C", "a", "");

		boolean assertion = (actually.equals(possibility1));
		String message = "\n original: " + original + "\n expected: " + possibility1 + "\n actually: " + actually +
			"\n";
		Assert.assertTrue(message, assertion);
		logger.info("test_JAVASERVERFACES_3757_noOrdering_startWithCab: Passed");
		logger.trace(message);
	}
}
