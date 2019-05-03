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

import com.liferay.faces.util.helper.IntegerHelper;


/**
 * @author  Neil Griffin
 */
public class ProductICEfacesImpl extends ProductBase {

	public ProductICEfacesImpl() {
		super(newInstance());
	}

	private static ProductInfo newInstance() {

		ProductInfo productInfo = null;

		try {

			Class<?> productInfoClass;

			try {
				productInfoClass = Class.forName("org.icefaces.application.ProductInfo");
			}
			catch (ClassNotFoundException e) {
				productInfoClass = Class.forName("com.icesoft.faces.application.ProductInfo");
			}

			int buildId = IntegerHelper.toInteger((String) productInfoClass.getDeclaredField("REVISION").get(
						String.class));
			int majorVersion = IntegerHelper.toInteger((String) productInfoClass.getDeclaredField("PRIMARY").get(
						String.class));
			int minorVersion = IntegerHelper.toInteger((String) productInfoClass.getDeclaredField("SECONDARY").get(
						String.class));
			int patchVersion = IntegerHelper.toInteger((String) productInfoClass.getDeclaredField("TERTIARY").get(
						String.class));
			String title = (String) productInfoClass.getDeclaredField("PRODUCT").get(String.class);

			if (title == null) {
				title = "ICEfaces";
			}

			boolean detected = false;

			if (majorVersion > 0) {
				detected = true;
			}

			StringBuilder buf = new StringBuilder();
			buf.append(majorVersion);
			buf.append(".");
			buf.append(minorVersion);
			buf.append(".");
			buf.append(patchVersion);

			String version = buf.toString();

			productInfo = new ProductInfo(detected, title, version, majorVersion, minorVersion, patchVersion, buildId);
		}
		catch (Exception e) {
			// Ignore -- ICEfaces is likely not present.
		}

		if (productInfo == null) {
			productInfo = new ProductInfo(false, "ICEfaces");
		}

		return productInfo;
	}
}
