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

import java.lang.reflect.Method;


/**
 * @author  Neil Griffin
 */
public class ProductInfoLiferayPortalImpl extends ProductInfoBaseImpl {

	public ProductInfoLiferayPortalImpl() {

		try {
			this.title = "Liferay Portal";

			Class<?> releaseInfoClass = Class.forName("com.liferay.portal.kernel.util.ReleaseInfo");
			Class<?>[] emptyClassArray = new Class[] {};
			Object[] emptyObjectArray = new Object[] {};
			Method method = releaseInfoClass.getMethod("getBuildNumber", emptyClassArray);
			this.buildId = (Integer) method.invoke(null, emptyObjectArray);
			method = releaseInfoClass.getMethod("getVersion", emptyClassArray);
			initVersionInfo((String) method.invoke(null, emptyObjectArray));

			if (this.majorVersion > 0) {
				this.detected = true;
			}

			initStringValue(version);
		}
		catch (Exception e) {
			// Ignore -- Liferay Portal is likely not present.
		}

	}
}
