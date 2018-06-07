/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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

import org.osgi.annotation.versioning.ProviderType;


/**
 * This interface represents a product that a Liferay Faces project depends on. Implementations of this interface must
 * be thread-safe and immutable.
 *
 * @author  Neil Griffin
 */
@ProviderType
public interface Product {

	/**
	 * @author  Kyle Stiemann
	 */
	public enum Name {
		ADF_FACES, ANGULARBEANS, ANGULARFACES, BOOTSFACES, BUTTERFACES, CDI, CDI_API, CLOSURE_TEMPLATES, DELTASPIKE, GLASSFISH,
		HIGHFACES, ICEFACES, JETTY, JSF, JSF_API, LIFERAY_FACES_ALLOY, LIFERAY_FACES_BRIDGE, LIFERAY_FACES_BRIDGE_EXT,
		LIFERAY_FACES_CLAY,
		@Deprecated
		LIFERAY_FACES_METAL, LIFERAY_FACES_PORTAL, LIFERAY_FACES_SHOWCASE, LIFERAY_FACES_UTIL, LIFERAY_PORTAL, MOJARRA,
		MYFACES, OMNIFACES, OPEN_WEB_BEANS, PORTLET_API, PLUTO, PORTLET_CONTAINER, PRIMEFACES, PRIMEFACES_EXTENSIONS,
		RESIN, RICHFACES, SERVLET_API, SERVLET_CONTAINER, SPRING_FRAMEWORK, TOMCAT, WELD, WEBLOGIC, WEBSPHERE, WILDFLY;
	}

	int getBuildId();

	int getMajorVersion();

	int getMinorVersion();

	int getPatchVersion();

	String getTitle();

	String getVersion();

	boolean isDetected();

	@Override
	String toString();
}
