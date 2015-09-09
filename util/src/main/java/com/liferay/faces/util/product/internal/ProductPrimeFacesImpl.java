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

import java.io.InputStream;
import java.util.Properties;

import com.liferay.faces.util.product.ProductConstants;


/**
 * @author  Neil Griffin
 */
public class ProductPrimeFacesImpl extends ProductBaseImpl {

	public ProductPrimeFacesImpl() {

		try {

			Class<?> constantsClass = Class.forName("org.primefaces.util.Constants");
			String version = (String) constantsClass.getDeclaredField("VERSION").get(String.class);
			initVersionInfo(version);
			this.buildId = (this.majorVersion * 100) + (this.minorVersion * 10) + this.revisionVersion;
			this.title = ProductConstants.PRIMEFACES;

			if (this.majorVersion > 0) {
				this.detected = true;
			}
		}
		catch (NoSuchFieldException e) {

			try {
				Properties pomProperties = new Properties();
				Class<?> constantsClass = Class.forName("org.primefaces.util.Constants");
				ClassLoader classLoader = constantsClass.getClassLoader();
				InputStream inputStream = classLoader.getResourceAsStream(
						"META-INF/maven/org.primefaces/primefaces/pom.properties");

				if (inputStream != null) {
					pomProperties.load(inputStream);
					inputStream.close();

					version = pomProperties.getProperty("version");
					initVersionInfo(version);
					this.buildId = (this.majorVersion * 100) + (this.minorVersion * 10) + this.revisionVersion;
					this.title = ProductConstants.PRIMEFACES;

					if (this.majorVersion > 0) {
						this.detected = true;
					}
				}
			}
			catch (Exception e2) {
				// Ignore -- PrimeFaces is likely not present.
			}
		}
		catch (Exception e) {
			// Ignore -- PrimeFaces is likely not present.
		}

	}
}
