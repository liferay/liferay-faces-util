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
package com.liferay.faces.util.product;

/**
 * @author  Neil Griffin
 */
public interface Product {

	/**
	 * @author  Kyle Stiemann
	 */
	public enum Name {
		ANGULARBEANS, ANGULARFACES, BOOTSFACES, BUTTERFACES, CDI, DELTASPIKE, ICEFACES, JSF, LIFERAY_PORTAL,
		LIFERAY_FACES_ALLOY, LIFERAY_FACES_BRIDGE, LIFERAY_FACES_METAL, LIFERAY_FACES_PORTAL, LIFERAY_FACES_SHOWCASE,
		LIFERAY_FACES_UTIL, MOJARRA, MYFACES, OMNIFACES, OPEN_WEB_BEANS, PRIMEFACES, PRIMEFACES_EXTENSIONS, PLUTO,
		PORTAL, RESIN, RICHFACES, SPRING_FRAMEWORK, WELD, WILDFLY;
	}

	int getBuildId();

	int getMajorVersion();

	int getMinorVersion();

	int getRevisionVersion();

	String getTitle();

	String getVersion();

	boolean isDetected();

	@Override
	String toString();
}
