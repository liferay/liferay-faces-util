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
package com.liferay.faces.util.product.internal;

/**
 * @author  Neil Griffin
 */
public class ProductPrimeFacesImpl extends ProductBaseImpl {

	public ProductPrimeFacesImpl() {
		super(obtainProductInfo());
	}

	private static ProductInfo obtainProductInfo() {

		boolean detected = false;
		String version = null;

		try {

			Class<?> constantsClass = Class.forName("org.primefaces.util.Constants");
			detected = true;

			try {
				version = (String) constantsClass.getDeclaredField("VERSION").get(String.class);
			}
			catch (NoSuchFieldException e) {

				PomProperties pomProperties = new PomProperties(constantsClass,
						"META-INF/maven/org.primefaces/primefaces/pom.properties");
				version = pomProperties.getVersion();
			}
		}
		catch (Exception e) {
			// Ignore -- PrimeFaces is likely not present.
		}

		return new ProductInfo(detected, "PrimeFaces", version);
	}
}
