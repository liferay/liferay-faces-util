/**
 * Copyright (c) 2000-2025 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.config.internal;

import com.liferay.faces.util.config.ConfiguredManagedBean;

/**
 * @author Neil Griffin
 */
public class ConfiguredManagedBeanImpl implements ConfiguredManagedBean {

	// Private Data Members
	private String managedBeanClass;
	private String managedBeanName;
	private String managedBeanScope;

	public ConfiguredManagedBeanImpl(String managedBeanClass, String managedBeanName, String managedBeanScope) {
		this.managedBeanClass = managedBeanClass;
		this.managedBeanName = managedBeanName;
		this.managedBeanScope = managedBeanScope;
	}

	public String getManagedBeanClass() {
		return managedBeanClass;
	}

	public String getManagedBeanName() {
		return managedBeanName;
	}

	public String getManagedBeanScope() {
		return managedBeanScope;
	}

}
