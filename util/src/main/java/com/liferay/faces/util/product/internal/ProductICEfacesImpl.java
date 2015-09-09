/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

/**
 * @author  Neil Griffin
 */
public class ProductICEfacesImpl extends ProductBaseImpl {

	public ProductICEfacesImpl() {

		try {
			Class<?> productInfoClass = null;

			try {
				productInfoClass = Class.forName("org.icefaces.application.ProductInfo");
			}
			catch (ClassNotFoundException e) {
				productInfoClass = Class.forName("com.icesoft.faces.application.ProductInfo");
			}

			this.buildId = parseInt((String) productInfoClass.getDeclaredField("REVISION").get(String.class));
			this.majorVersion = parseInt((String) productInfoClass.getDeclaredField("PRIMARY").get(String.class));
			this.minorVersion = parseInt((String) productInfoClass.getDeclaredField("SECONDARY").get(String.class));
			this.revisionVersion = parseInt((String) productInfoClass.getDeclaredField("TERTIARY").get(String.class));
			this.title = (String) productInfoClass.getDeclaredField("PRODUCT").get(String.class);

			StringBuilder buf = new StringBuilder();
			buf.append(this.majorVersion);
			buf.append(".");
			buf.append(this.minorVersion);
			buf.append(".");
			buf.append(this.revisionVersion);
			this.version = buf.toString();

			if (this.majorVersion > 0) {
				this.detected = true;
			}
		}
		catch (Exception e) {
			// Ignore -- ICEfaces is likely not present.
		}
	}
}
