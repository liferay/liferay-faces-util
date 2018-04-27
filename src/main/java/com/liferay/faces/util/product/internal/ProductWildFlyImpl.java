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

import java.util.List;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;


/**
 * @author  Neil Griffin
 */
public class ProductWildFlyImpl extends ProductBaseImpl {

	public ProductWildFlyImpl() {
		super(obtainProductInfo());
	}

	private static ProductInfo obtainProductInfo() {

		boolean detected = false;
		String title = "Wildfly";
		String releaseVersion = null;

		try {

			List<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);

			for (MBeanServer mBeanServer : mBeanServers) {

				ObjectName objectName = new ObjectName("jboss.as:management-root=server");

				releaseVersion = (String) mBeanServer.getAttribute(objectName, "releaseVersion");

				if (releaseVersion != null) {

					detected = true;

					if (ProductInfo.getMajorVersion(releaseVersion) < 8) {
						title = "JBoss AS";
					}

					break;
				}
			}
		}
		catch (Exception e) {
			// Ignore -- WildFly is likely not present.
		}

		return new ProductInfo(detected, title, releaseVersion);
	}
}
