/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
 * @author  Kyle Stiemann
 */
public class ProductAngularBeansImpl extends ProductBaseImpl {

	public ProductAngularBeansImpl() {

		try {
			this.title = "AngularBeans";

			Class<?> clazz = Class.forName("angularBeans.api.NGApp");
			init(clazz, "AngularBeans", "META-INF/maven/com.github.bessemHmidi/angularBeans/pom.properties");
		}
		catch (Exception e) {
			// Ignore -- AngularBeans is likely not present.
		}
	}
}
