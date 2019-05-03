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
package com.liferay.faces.util.product.internal;

import java.lang.reflect.Method;

import com.liferay.faces.util.helper.IntegerHelper;


/**
 * @author  Neil Griffin
 */
public class ProductRichFacesImpl extends ProductBase {

	public ProductRichFacesImpl() {
		super(newInstance());
	}

	private static ProductInfo newInstance() {

		boolean detected = false;
		String version = null;
		ProductInfo productInfo = null;

		try {

			try {

				Class<?> versionBeanClass = Class.forName("org.richfaces.VersionBean");
				Object versionObj = versionBeanClass.getDeclaredField("VERSION").get(Object.class);
				Method method = versionObj.getClass().getMethod("getVersion");
				version = (String) method.invoke(versionObj, (Object[]) null);

				if (version != null) {

					version = version.replaceFirst("[^0-9]*", "");

					String[] versionParts = version.split(ProductInfo.REGEX_VERSION_DELIMITER);
					int majorVersion = 0;

					if (versionParts.length > 0) {
						majorVersion = IntegerHelper.toInteger(versionParts[0]);
					}

					if (majorVersion > 0) {
						detected = true;
					}
				}
			}
			catch (SecurityException e) {

				// Workaround for https://issues.jboss.org/browse/RF-12805
				productInfo = ProductInfo.newInstance("RichFaces", "org.richfaces.util.Util");
			}
		}
		catch (Exception e) {
			// Ignore -- RichFaces is likely not present.
		}

		if (productInfo == null) {
			productInfo = new ProductInfo(detected, "RichFaces", version);
		}

		return productInfo;
	}
}
