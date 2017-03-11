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

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ProductCDIApiImpl extends ProductBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ProductCDIApiImpl.class);

	public ProductCDIApiImpl() {

		try {
			this.title = "CDI API";

			Class<?> clazz = Class.forName("javax.enterprise.context.SessionScoped");
			detected = true;

			PomProperties pomProperties = new PomProperties(clazz,
					"META-INF/maven/javax.enterprise/cdi-api/pom.properties");
			String implementationVersion = pomProperties.getVersion();

			if (implementationVersion != null) {
				initVersionInfo(implementationVersion);
			}
			else {
				logger.warn("Unable to obtain version information for {0}.", this.title);
			}
		}
		catch (Exception e) {
			// Ignore -- CDI API is likely not present.
		}
	}

}
