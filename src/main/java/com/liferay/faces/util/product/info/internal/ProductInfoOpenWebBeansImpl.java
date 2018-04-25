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
package com.liferay.faces.util.product.info.internal;

/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class ProductInfoOpenWebBeansImpl extends ProductInfoBaseImpl {

	public ProductInfoOpenWebBeansImpl() {

		try {
			this.title = "OpenWebBeans";

			Class<?> cdiImplClass = Class.forName("org.apache.webbeans.util.WebBeansConstants");
			init(cdiImplClass, "OpenWebBeans Core");
		}
		catch (Exception e) {
			// Ignore -- OpenWebBeans is likely not present.
		}
	}
}
