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
package com.liferay.faces.util.product.internal;

import com.liferay.faces.util.product.ProductConstants;


/**
 * @author  Neil Griffin
 */
public class ProductJSFImpl extends ProductBaseImpl {

	// Private Constants
	private static final String SNAPSHOT = "-SNAPSHOT";

	// Private Data Members
	private boolean mojarra;
	private String toStringValue;

	public ProductJSFImpl() {

		try {
			Class<?> jsfImplClass;

			try {
				this.title = ProductConstants.MOJARRA;
				jsfImplClass = Class.forName("com.sun.faces.RIConstants");
				init(jsfImplClass, ProductConstants.MOJARRA);
				mojarra = true;

			}
			catch (ClassNotFoundException e) {
				this.title = ProductConstants.MYFACES;
				jsfImplClass = Class.forName("org.apache.myfaces.util.ContainerUtils");
				init(jsfImplClass, ProductConstants.MYFACES);
			}
		}
		catch (Exception e) {
			// Ignore -- JSF implementation is likely not present.
		}
	}

	@Override
	public String toString() {

		if (toStringValue == null) {
			toStringValue = super.toString();

			// Some versions of Mojarra are mislabeled "-SNAPSHOT" (i.e.: "1.2_15-20100816-SNAPSHOT")
			if (mojarra && (toStringValue != null)) {
				int pos = toStringValue.indexOf(SNAPSHOT);

				if (pos > 0) {
					toStringValue = toStringValue.substring(0, pos);
				}
			}
		}

		return toStringValue;

	}

}
