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
package com.liferay.faces.util.product.info;

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
import com.liferay.faces.util.product.info.internal.ProductInfoBaseImpl;
import com.liferay.faces.util.product.info.internal.ProductInfoFactoryImpl;
import com.liferay.faces.util.product.info.internal.ProductInfoPortletApiImpl;


/**
 * @author  Kyle Stiemann
 */
public class ProductInfoTest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ProductInfoTest.class);

	static {
		logger.info(
			"Liferay Faces Util's version cannot be obtained at test time (likely because the MANIFEST.MF cannot be found).");
		logger.info(
			"However, Liferay Faces Util's version can be obtained normally when running in a servlet or portlet container.");
	}

	private static void assertPortletApiMajorMinorVersionDetected(int expectedMajorVersion, int expectedMinorVersion,
		Class<?> productInfoPortletApiImplClass, String liferayPortalVersion, String plutoVersion) throws Exception {

		Object productInfoPortletApiImpl = createPortletApiImplObject(productInfoPortletApiImplClass,
				liferayPortalVersion, plutoVersion);
		boolean portletApiDetected = ProductInfoTest.<Boolean>invokeMethod(productInfoPortletApiImplClass,
				productInfoPortletApiImpl, "isDetected");
		Assert.assertTrue("Portlet API was not detected.", portletApiDetected);

		int portletApiMajorVersion = ProductInfoTest.<Integer>invokeMethod(productInfoPortletApiImplClass,
				productInfoPortletApiImpl, "getMajorVersion");
		Assert.assertEquals(expectedMajorVersion, portletApiMajorVersion);

		int portletApiMinorVersion = ProductInfoTest.<Integer>invokeMethod(productInfoPortletApiImplClass,
				productInfoPortletApiImpl, "getMinorVersion");
		Assert.assertEquals(expectedMinorVersion, portletApiMinorVersion);
	}

	private static Object createPortletApiImplObject(Class<?> productInfoPortletApiImplClass,
		String liferayPortalVersion, String plutoVersion) throws Exception {

		Constructor<?> constructor = productInfoPortletApiImplClass.getConstructor(ProductInfo.class,
				ProductInfo.class);

		return constructor.newInstance(new ProductInfoLiferayPortalMockImpl(liferayPortalVersion),
				new ProductInfoPlutoMockImpl(plutoVersion));
	}

	private static <T> T invokeMethod(Class<?> clazz, Object object, String methodName) throws Exception {

		Method isDetectedMethod = clazz.getMethod(methodName);

		return (T) isDetectedMethod.invoke(object);
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

		return classLoader.loadClassWithoutParentLoader(ProductInfoPortletApiImpl.class);
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

		Class<?> productInfoPortletApiImplClass = utilOnlyClassLoader.loadClassWithoutParentLoader(
				ProductInfoPortletApiImpl.class);
		Object productInfoPortletApiImpl = createPortletApiImplObject(productInfoPortletApiImplClass, null, null);
		boolean portletApiDetected = ProductInfoTest.<Boolean>invokeMethod(productInfoPortletApiImplClass,
				productInfoPortletApiImpl, "isDetected");
		Assert.assertFalse(portletApiDetected);

		// Ensure that the Portlet API version is correctly detected when the Portlet 2.0 API jar is on the classpath.
		productInfoPortletApiImplClass = loadPortletApiWithInfoFromJar("javax.portlet:portlet-api:jar",
				parentClassLoader);
		assertPortletApiMajorMinorVersionDetected(2, 0, productInfoPortletApiImplClass, null, "2.0");
		assertPortletApiMajorMinorVersionDetected(2, 0, productInfoPortletApiImplClass, "6.2.4", null);

		// Ensure that the Portlet API version is correctly detected when a Portlet API jar with invalid version
		// information is on the classpath. This is accomplished by using the Portlet 2.0 API jar, but overriding any
		// versions found in the MANIFEST.MF with "" (the empty string).
		productInfoPortletApiImplClass = loadPortletApiWithInfoFromJar("javax.portlet:portlet-api:jar",
				parentClassLoader, "");
		assertPortletApiMajorMinorVersionDetected(2, 0, productInfoPortletApiImplClass, "6.2.5", null);
		assertPortletApiMajorMinorVersionDetected(2, 0, productInfoPortletApiImplClass, "7.0.3", null);

		// Ensure that the Portlet API version is correctly detected when the Portlet 2.1 API jar is on the classpath.
		productInfoPortletApiImplClass = loadPortletApiWithInfoFromJar("org.apache.portals:portlet-api_2.1.0_spec:jar",
				parentClassLoader);
		assertPortletApiMajorMinorVersionDetected(2, 1, productInfoPortletApiImplClass, "7.0.4", null);

		// Ensure that the Portlet API version is correctly detected when the Portlet 3.0 API jar is on the classpath.
		// This is accomplished by using the Portlet 2.0 API jar, but overriding any versions found in the MANIFEST.MF
		// with 3.0.0.
		productInfoPortletApiImplClass = loadPortletApiWithInfoFromJar("javax.portlet:portlet-api:jar",
				parentClassLoader, "3.0.0");
		assertPortletApiMajorMinorVersionDetected(3, 0, productInfoPortletApiImplClass, null, "3.0");
		assertPortletApiMajorMinorVersionDetected(3, 0, productInfoPortletApiImplClass, "7.1.0", null);
	}

	@Test
	public void productInfoFactoryCanCreateAllProductInfos() {

		ProductInfoFactory productInfoFactory = new ProductInfoFactoryImpl();

		for (ProductInfo.Name productInfoName : ProductInfo.Name.values()) {

			ProductInfo productInfo = productInfoFactory.getProductInfo(productInfoName);
			Assert.assertNotNull("ProductInfoFactory.get(ProductInfo.Name." + productInfoName +
				") did not return a ProductInfo implementation. Instead it returned null.", productInfo);
		}

		logger.info("All ProductInfos were correctly obtained from the default ProductInfoFactory.");
	}

	@Test
	public void productInfoNotDetected() {

		ProductInfoFactory productInfoFactory = new ProductInfoFactoryImpl();

		// Note: This productInfo could be replaced with any other productInfo except Liferay Faces Util and Mojarra for
		// this test.
		ProductInfo.Name productInfoName = ProductInfo.Name.PRIMEFACES;
		ProductInfo productInfo = productInfoFactory.getProductInfo(productInfoName);
		Assert.assertNotNull("ProductInfoFactory.get(ProductInfo.Name." + productInfoName +
			") did not return a ProductInfo implementation. Instead it returned null.", productInfo);
		Assert.assertFalse("ProductInfoFactory.get(ProductInfo.Name." + productInfoName +
			") is detected even though it is not present.", productInfo.isDetected());

		String title = productInfo.getTitle();
		Assert.assertNotNull("ProductInfoFactory.get(ProductInfo.Name." + productInfoName +
			").getTitle() did not return a title. Instead it returned null.", title);

		int majorVersion = productInfo.getMajorVersion();
		Assert.assertEquals(title + "'s major version is not 0 even though " + title + " is not detected. Instead " +
			title + "'s major version is " + majorVersion + ".", 0, majorVersion);

		int minorVersion = productInfo.getMinorVersion();
		Assert.assertEquals(title + "'s minor version is not 0 even though " + title + " is not detected. Instead " +
			title + "'s minor version is " + minorVersion + ".", 0, minorVersion);

		int patchVersion = productInfo.getPatchVersion();
		Assert.assertEquals(title + "'s patch version is not 0 even though " + title + " is not detected. Instead " +
			title + "'s patch version is " + patchVersion + ".", 0, patchVersion);

		int buildId = productInfo.getBuildId();
		Assert.assertEquals(title + "'s build ID is not 0 even though " + title + " is not detected. Instead " + title +
			"'s build ID is " + buildId + ".", 0, buildId);

		String version = productInfo.getVersion();
		Assert.assertEquals(title + "'s version is not \"0.0.0\" even though " + title + " is not detected. Instead " +
			title + "'s version is \"" + version + "\".", "0.0.0", version);

		String productInfoString = productInfo.toString();
		Assert.assertNotNull("ProductInfoFactory.get(ProductInfo.Name." + productInfoName +
			").toString() did not string. Instead it returned null.", productInfoString);
		logger.info("{0} was correctly not detected.", title);
		logger.info("ProductInfoFactory.get(ProductInfo.Name.{0}).toString() equals \"{1}\"", productInfoName,
			productInfoString);
	}

	@Test
	public void utilDetected() {

		ProductInfoFactory productInfoFactory = new ProductInfoFactoryImpl();

		// Note: Util's version cannot be obtained at test time (likely because the MANIFEST.MF cannot be found), so
		// we cannot test that feature here. However, the version can be obtained normally when running in a servlet or
		// portlet container.
		ProductInfo utilProductInfo = productInfoFactory.getProductInfo(ProductInfo.Name.LIFERAY_FACES_UTIL);
		Assert.assertNotNull(
			"ProductInfoFactory.get(ProductInfo.Name.LIFERAY_FACES_UTIL) did not return a ProductInfo implementation. Instead it returned null.",
			utilProductInfo);
		Assert.assertTrue("Liferay Faces Util is not detected even though it is present.",
			utilProductInfo.isDetected());
		logger.info("Liferay Faces Util was correctly detected.");
	}

	private static final class ProductInfoLiferayPortalMockImpl extends ProductInfoBaseImpl {

		public ProductInfoLiferayPortalMockImpl(String version) {

			if (version != null) {

				this.detected = true;
				initVersionInfo(version);
			}
		}
	}

	private static final class ProductInfoPlutoMockImpl extends ProductInfoBaseImpl {

		public ProductInfoPlutoMockImpl(String version) {

			if (version != null) {

				this.detected = true;
				initVersionInfo(version);
			}
		}
	}
}
