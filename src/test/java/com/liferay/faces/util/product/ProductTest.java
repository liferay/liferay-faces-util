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
package com.liferay.faces.util.product;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;

import javax.portlet.PortletContext;

import org.junit.Assert;
import org.junit.Test;

import com.liferay.faces.util.classloader.TestClassLoader;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.internal.ProductBaseImpl;
import com.liferay.faces.util.product.internal.ProductFactoryImpl;
import com.liferay.faces.util.product.internal.ProductInfo;
import com.liferay.faces.util.product.internal.ProductPortletApiImpl;


/**
 * @author  Kyle Stiemann
 */
public class ProductTest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ProductTest.class);

	static {
		logger.info(
			"Liferay Faces Util's version cannot be obtained at test time (likely because the MANIFEST.MF cannot be found).");
		logger.info(
			"However, Liferay Faces Util's version can be obtained normally when running in a servlet or portlet container.");
	}

	public static ProductInfo createProductInfo(String title, String version) {

		boolean detected = false;

		if (version != null) {
			detected = true;
		}

		return new ProductInfo(detected, title, version);
	}

	private static void assertPortletApiMajorMinorVersionDetected(int expectedMajorVersion, int expectedMinorVersion,
		Class<?> productPortletApiImplClass, String liferayPortalVersion, String plutoVersion) throws Exception {

		Object productPortletApiImpl = createPortletApiImplObject(productPortletApiImplClass, liferayPortalVersion,
				plutoVersion);
		boolean portletApiDetected = ProductTest.<Boolean>invokeMethod(productPortletApiImplClass,
				productPortletApiImpl, "isDetected");
		Assert.assertTrue("Portlet API was not detected.", portletApiDetected);

		int portletApiMajorVersion = ProductTest.<Integer>invokeMethod(productPortletApiImplClass,
				productPortletApiImpl, "getMajorVersion");
		Assert.assertEquals(expectedMajorVersion, portletApiMajorVersion);

		int portletApiMinorVersion = ProductTest.<Integer>invokeMethod(productPortletApiImplClass,
				productPortletApiImpl, "getMinorVersion");
		Assert.assertEquals(expectedMinorVersion, portletApiMinorVersion);
	}

	private static Object createPortletApiImplObject(Class<?> productPortletApiImplClass, String liferayPortalVersion,
		String plutoVersion) throws Exception {

		Constructor<?> constructor = productPortletApiImplClass.getConstructor(Product.class, Product.class);

		return constructor.newInstance(new ProductLiferayPortalMockImpl(liferayPortalVersion),
				new ProductPlutoMockImpl(plutoVersion));
	}

	private static <T> T invokeMethod(Class<?> clazz, Object object, String methodName) throws Exception {

		Method isDetectedMethod = clazz.getMethod(methodName);

		return (T) isDetectedMethod.invoke(object);
	}

	private static Class<?> loadPortletApiProductImplWithoutParentLoader(TestClassLoader testClassLoader)
		throws ClassNotFoundException {

		testClassLoader.loadClassWithoutParentLoader(ProductInfo.class);
		testClassLoader.loadClassWithoutParentLoader(ProductBaseImpl.class);

		return testClassLoader.loadClassWithoutParentLoader(ProductPortletApiImpl.class);
	}

	private static Class<?> loadPortletApiWithInfoFromJar(String pathToJarProperty, ClassLoader parentClassLoader)
		throws Exception {
		return loadPortletApiWithInfoFromJar(pathToJarProperty, parentClassLoader, null);
	}

	private static Class<?> loadPortletApiWithInfoFromJar(String pathToJarProperty, ClassLoader parentClassLoader,
		String overriddenPortletApiVersion) throws Exception {

		String pathToJar = System.getProperty(pathToJarProperty);
		File file = new File(pathToJar);
		URI jarURI = file.toURI();
		URL jarURL = jarURI.toURL();
		TestClassLoader classLoader = new TestClassLoader(parentClassLoader);
		Class<?> portletContextClass;

		if (overriddenPortletApiVersion != null) {
			portletContextClass = classLoader.loadClassFromJar(PortletContext.class, jarURL,
					overriddenPortletApiVersion);
		}
		else {
			portletContextClass = classLoader.loadClassFromJar(PortletContext.class, jarURL);
		}

		Assert.assertFalse(PortletContext.class.equals(portletContextClass));

		return loadPortletApiProductImplWithoutParentLoader(classLoader);
	}

	@Test
	public void portletApiVersionsDetected() throws Exception {

		ClassLoader parentClassLoader = this.getClass().getClassLoader();

		// Ensure that the Portlet API is correctly NOT detected when the Portlet API jar is not on the classpath.
		UtilOnlyClassLoader utilOnlyClassLoader = new UtilOnlyClassLoader(parentClassLoader);

		try {

			utilOnlyClassLoader.loadClass(PortletContext.class.getName());
			throw new Exception(PortletContext.class.getName() +
				" was loaded when the Portlet API was not supposed to be present.");
		}
		catch (ClassNotFoundException e) {
			// continue
		}

		Class<?> productPortletApiImplClass = loadPortletApiProductImplWithoutParentLoader(utilOnlyClassLoader);
		Object productPortletApiImpl = createPortletApiImplObject(productPortletApiImplClass, null, null);
		boolean portletApiDetected = ProductTest.<Boolean>invokeMethod(productPortletApiImplClass,
				productPortletApiImpl, "isDetected");
		Assert.assertFalse(portletApiDetected);

		// Ensure that the Portlet API version is correctly detected when the Portlet 2.0 API jar is on the classpath.
		productPortletApiImplClass = loadPortletApiWithInfoFromJar("javax.portlet:portlet-api:jar", parentClassLoader);
		assertPortletApiMajorMinorVersionDetected(2, 0, productPortletApiImplClass, null, "2.0");
		assertPortletApiMajorMinorVersionDetected(2, 0, productPortletApiImplClass, "6.2.4", null);

		// Ensure that the Portlet API version is correctly detected when a Portlet API jar with invalid version
		// information is on the classpath. This is accomplished by using the Portlet 2.0 API jar, but overriding any
		// versions found in the MANIFEST.MF with "" (the empty string).
		productPortletApiImplClass = loadPortletApiWithInfoFromJar("javax.portlet:portlet-api:jar", parentClassLoader,
				"");
		assertPortletApiMajorMinorVersionDetected(2, 0, productPortletApiImplClass, "6.2.5", null);
		assertPortletApiMajorMinorVersionDetected(2, 0, productPortletApiImplClass, "7.0.3", null);

		// Ensure that the Portlet API version is correctly detected when the Portlet 2.1 API jar is on the classpath.
		productPortletApiImplClass = loadPortletApiWithInfoFromJar("org.apache.portals:portlet-api_2.1.0_spec:jar",
				parentClassLoader);
		assertPortletApiMajorMinorVersionDetected(2, 1, productPortletApiImplClass, "7.0.4", null);

		// Ensure that the Portlet API version is correctly detected when the Portlet 3.0 API jar is on the classpath.
		// This is accomplished by using the Portlet 2.0 API jar, but overriding any versions found in the MANIFEST.MF
		// with 3.0.0.
		productPortletApiImplClass = loadPortletApiWithInfoFromJar("javax.portlet:portlet-api:jar", parentClassLoader,
				"3.0.0");
		assertPortletApiMajorMinorVersionDetected(3, 0, productPortletApiImplClass, null, "3.0");
		assertPortletApiMajorMinorVersionDetected(3, 0, productPortletApiImplClass, "7.1.0", null);
	}

	@Test
	public void productFactoryCanCreateAllProducts() {

		ProductFactory productFactory = new ProductFactoryImpl();

		for (Product.Name productName : Product.Name.values()) {

			Product product = productFactory.getProductInfo(productName);
			Assert.assertNotNull("ProductFactory.get(Product.Name." + productName +
				") did not return a Product implementation. Instead it returned null.", product);
		}

		logger.info("All Products were correctly obtained from the default ProductFactory.");
	}

	@Test
	public void productNotDetected() {

		ProductFactory productFactory = new ProductFactoryImpl();

		// Note: This product could be replaced with any other product except Liferay Faces Util and Mojarra for
		// this test.
		Product.Name productName = Product.Name.PRIMEFACES;
		Product product = productFactory.getProductInfo(productName);
		Assert.assertNotNull("ProductFactory.get(Product.Name." + productName +
			") did not return a Product implementation. Instead it returned null.", product);
		Assert.assertFalse("ProductFactory.get(Product.Name." + productName +
			") is detected even though it is not present.", product.isDetected());

		String title = product.getTitle();
		Assert.assertNotNull("ProductFactory.get(Product.Name." + productName +
			").getTitle() did not return a title. Instead it returned null.", title);

		int majorVersion = product.getMajorVersion();
		Assert.assertEquals(title + "'s major version is not 0 even though " + title + " is not detected. Instead " +
			title + "'s major version is " + majorVersion + ".", 0, majorVersion);

		int minorVersion = product.getMinorVersion();
		Assert.assertEquals(title + "'s minor version is not 0 even though " + title + " is not detected. Instead " +
			title + "'s minor version is " + minorVersion + ".", 0, minorVersion);

		int patchVersion = product.getPatchVersion();
		Assert.assertEquals(title + "'s patch version is not 0 even though " + title + " is not detected. Instead " +
			title + "'s patch version is " + patchVersion + ".", 0, patchVersion);

		int buildId = product.getBuildId();
		Assert.assertEquals(title + "'s build ID is not 0 even though " + title + " is not detected. Instead " + title +
			"'s build ID is " + buildId + ".", 0, buildId);

		String version = product.getVersion();
		Assert.assertEquals(title + "'s version is not \"0.0.0\" even though " + title + " is not detected. Instead " +
			title + "'s version is \"" + version + "\".", "0.0.0", version);

		String productString = product.toString();
		Assert.assertNotNull("ProductFactory.get(Product.Name." + productName +
			").toString() did not string. Instead it returned null.", productString);
		logger.info("{0} was correctly not detected.", title);
		logger.info("ProductFactory.get(Product.Name.{0}).toString() equals \"{1}\"", productName, productString);
	}

	@Test
	public void testProductFactoryWorksWithoutFacesContext() {

		Assert.assertNotNull(StaticProductHolder.LIFERAY_FACES_UTIL);
		Assert.assertTrue(StaticProductHolder.LIFERAY_FACES_UTIL.isDetected());
	}

	@Test
	public void utilDetected() {

		ProductFactory productFactory = new ProductFactoryImpl();

		// Note: Util's version cannot be obtained at test time (likely because the MANIFEST.MF cannot be found), so
		// we cannot test that feature here. However, the version can be obtained normally when running in a servlet or
		// portlet container.
		Product utilProduct = productFactory.getProductInfo(Product.Name.LIFERAY_FACES_UTIL);
		Assert.assertNotNull(
			"ProductFactory.get(Product.Name.LIFERAY_FACES_UTIL) did not return a Product implementation. Instead it returned null.",
			utilProduct);
		Assert.assertTrue("Liferay Faces Util is not detected even though it is present.", utilProduct.isDetected());
		logger.info("Liferay Faces Util was correctly detected.");
	}

	private static final class ProductLiferayPortalMockImpl extends ProductBaseImpl {

		public ProductLiferayPortalMockImpl(String version) {
			super(createProductInfo("Liferay Portal", version));
		}
	}

	private static final class ProductPlutoMockImpl extends ProductBaseImpl {

		public ProductPlutoMockImpl(String version) {
			super(createProductInfo("Pluto Portal", version));
		}
	}

	private static final class StaticProductHolder {

		// Private Constants
		private static final Product LIFERAY_FACES_UTIL = ProductFactory.getProduct(Product.Name.LIFERAY_FACES_UTIL);
	}
}
