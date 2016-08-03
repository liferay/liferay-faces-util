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

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ProductWebSphereImpl extends ProductBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ProductWebSphereImpl.class);

	public ProductWebSphereImpl() {

		try {
			this.title = "WebSphere";
			// TODO: FACES-2859 - Unable to detect the version of WebSphere
		}
		catch (Exception e) {
			// Ignore -- WebSphere is likely not present.
		}
	}
}
