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
public class ProductInfoRichFacesImpl extends ProductInfoBaseImpl {

	public ProductInfoRichFacesImpl() {

		try {
			this.title = "RichFaces";

			try {
				Class<?> versionBeanClass = Class.forName("org.richfaces.VersionBean");
				Object versionObj = versionBeanClass.getDeclaredField("VERSION").get(Object.class);
				Method method = versionObj.getClass().getMethod("getVersion");
				String version = (String) method.invoke(versionObj, (Object[]) null);

				if (version != null) {
					version = version.replaceFirst("[^0-9]*", "");
					initVersionInfo(version);
				}

				if (this.majorVersion > 0) {
					this.detected = true;
				}
			}
			catch (SecurityException e) {

				// Workaround for https://issues.jboss.org/browse/RF-12805
				Class<?> utilClass = Class.forName("org.richfaces.util.Util");
				init(utilClass, "RichFaces Core Implementation");
				this.title = "RichFaces";
			}
		}
		catch (Exception e) {
			// Ignore -- RichFaces is likely not present.
		}

		initStringValue(version);
	}
}
