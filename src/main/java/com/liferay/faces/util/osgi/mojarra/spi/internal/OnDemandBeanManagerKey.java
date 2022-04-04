/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.osgi.mojarra.spi.internal;

import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.internal.ProductMojarraImpl;

import com.sun.faces.RIConstants;


/**
 * @author  Kyle Stiemann
 */
public final class OnDemandBeanManagerKey {

	public static final String INSTANCE;

	static {

		final Product MOJARRA = new ProductMojarraImpl();

		if (MOJARRA.isDetected()) {
			INSTANCE = RIConstants.CDI_BEAN_MANAGER;
		}
		else {
			INSTANCE = OnDemandBeanManagerKey.class.getName() + ".UNUSED";
		}
	}
}
