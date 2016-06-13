/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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

import org.junit.Assert;
import org.junit.Test;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Kyle Stiemann
 */
public class ProductTest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ProductTest.class);

	static {
		logger.info("Liferay Faces Util's version cannot be obtained at test time (likely because the MANIFEST.MF cannot be found).");
		logger.info("However, Liferay Faces Util's version can be obtained normally when running in a servlet or portlet container.");
	}

	@Test
	public void productFactoryCanCreateAllProducts() {

		for (Product.Name productName : Product.Name.values()) {

			Product product = ProductFactory.getProduct(productName);
			Assert.assertNotNull("ProductFactory.get(Product.Name." + productName +
				") did not return a Product implementation. Instead it returned null.", product);
		}

		logger.info("All Products were correctly obtained from the default ProductFactory.");
	}

	@Test
	public void productNotDetected() {

		// Note: This product could be replaced with any other product except Liferay Faces Util for this test.
		Product.Name productName = Product.Name.MOJARRA;
		Product product = ProductFactory.getProduct(productName);
		Assert.assertNotNull("ProductFactory.get(Product.Name." + productName +
			") did not return a Product implementation. Instead it returned null.", product);
		Assert.assertFalse("ProductFactory.get(Product.Name." + productName +
			") is detected even though it is not present.", product.isDetected());

		String title = product.getTitle();
		Assert.assertNotNull("ProductFactory.get(Product.Name." + productName +
			").getTitle() did not return a title. Instead it returned null.", title);

		int majorVersion = product.getMajorVersion();
		Assert.assertEquals(title +
			"'s major version is not 0 even though Mojarra is not detected. Instead Mojarra's major version is " +
			majorVersion + ".", 0, majorVersion);

		int minorVersion = product.getMinorVersion();
		Assert.assertEquals(title +
			"'s minor version is not 0 even though Mojarra is not detected. Instead Mojarra's minor version is " +
			minorVersion + ".", 0, minorVersion);

		int revisionVersion = product.getRevisionVersion();
		Assert.assertEquals(title +
			"'s revision version is not 0 even though Mojarra is not detected. Instead Mojarra's revision version is " +
			revisionVersion + ".", 0, revisionVersion);

		int buildId = product.getBuildId();
		Assert.assertEquals(title +
			"'s build ID is not 0 even though Mojarra is not detected. Instead Mojarra's build ID is " + buildId + ".",
			0, buildId);

		String version = product.getVersion();
		Assert.assertEquals(title +
			"'s build ID is not \"0.0.0\" even though Mojarra is not detected. Instead Mojarra's build ID is \"" +
			version + "\".", "0.0.0", version);

		String productString = product.toString();
		Assert.assertNotNull("ProductFactory.get(Product.Name." + productName +
			").toString() did not string. Instead it returned null.", productString);
		logger.info("{0} was correctly not detected.", title);
		logger.info("ProductFactory.get(Product.Name." + productName + ").toString() equals \"{0}\"", productString);
	}

	@Test
	public void utilDetected() {

		// Note: Util's version cannot be obtained at test time (likely because the MANIFEST.MF cannot be found), so
		// we cannot test that feature here. However, the version can be obtained normally when running in a servlet or
		// portlet container.
		Product utilProduct = ProductFactory.getProduct(Product.Name.LIFERAY_FACES_UTIL);
		Assert.assertNotNull(
			"ProductFactory.get(Product.Name.LIFERAY_FACES_UTIL) did not return a Product implementation. Instead it returned null.",
			utilProduct);
		Assert.assertTrue("Liferay Faces Util is not detected even though it is present.", utilProduct.isDetected());
		logger.info("Liferay Faces Util was correctly detected.");
	}
}
