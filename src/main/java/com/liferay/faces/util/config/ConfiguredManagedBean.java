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
package com.liferay.faces.util.config;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This interface defines the properties for a configured managed-bean that is discovered in META-INF/faces-config.xml
 * or WEB-INF/faces-config.xml descriptors.
 *
 * @author  Neil Griffin
 */
@ProviderType
public interface ConfiguredManagedBean {

	/**
	 * Returns the value of the managed-bean-class element of the configured managed-bean.
	 */
	public String getManagedBeanClass();

	/**
	 * Returns the value of the managed-mean-name element of the configured managed-bean.
	 */
	public String getManagedBeanName();

	/**
	 * Returns the value of the managed-bean-scope element of the configured managed-bean.
	 */
	public String getManagedBeanScope();
}
