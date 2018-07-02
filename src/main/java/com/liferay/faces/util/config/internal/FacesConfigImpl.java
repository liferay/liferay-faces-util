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
package com.liferay.faces.util.config.internal;

import java.util.ArrayList;
import java.util.List;

import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.config.ConfiguredManagedBean;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.ConfiguredSystemEventListener;
import com.liferay.faces.util.config.FacesConfig;


/**
 * @author  Neil Griffin
 */
public class FacesConfigImpl implements FacesConfig {

	// Private Data Members
	private List<ConfiguredElement> configuredApplicationExtensions;
	private List<ConfiguredElement> configuredFactoryExtensions;
	private List<ConfiguredServletMapping> configuredFacesServletMappings;
	private List<ConfiguredManagedBean> configuredManagedBeans;
	private List<String> configuredSuffixes;
	private List<ConfiguredSystemEventListener> configuredSystemEventListeners;

	public FacesConfigImpl() {
		this.configuredApplicationExtensions = new ArrayList<ConfiguredElement>();
		this.configuredFacesServletMappings = new ArrayList<ConfiguredServletMapping>();
		this.configuredFactoryExtensions = new ArrayList<ConfiguredElement>();
		this.configuredManagedBeans = new ArrayList<ConfiguredManagedBean>();
		this.configuredSuffixes = new ArrayList<String>();
		this.configuredSystemEventListeners = new ArrayList<ConfiguredSystemEventListener>();
	}

	public FacesConfigImpl(List<ConfiguredServletMapping> configuredFacesServletMappings,
		List<String> configuredSuffixes) {
		this();
		this.configuredFacesServletMappings = configuredFacesServletMappings;
		this.configuredSuffixes = configuredSuffixes;
	}

	public FacesConfigImpl(List<ConfiguredElement> configuredApplicationExtensions,
		List<ConfiguredElement> configuredFactoryExtensions,
		List<ConfiguredServletMapping> configuredFacesServletMappings,
		List<ConfiguredManagedBean> configuredManagedBeans, List<String> configuredSuffixes,
		List<ConfiguredSystemEventListener> configuredSystemEventListeners) {

		this.configuredApplicationExtensions = configuredApplicationExtensions;
		this.configuredSuffixes = configuredSuffixes;
		this.configuredFacesServletMappings = configuredFacesServletMappings;
		this.configuredFactoryExtensions = configuredFactoryExtensions;
		this.configuredManagedBeans = configuredManagedBeans;
		this.configuredSystemEventListeners = configuredSystemEventListeners;
	}

	public List<ConfiguredElement> getConfiguredApplicationExtensions() {
		return configuredApplicationExtensions;
	}

	public List<ConfiguredServletMapping> getConfiguredFacesServletMappings() {
		return configuredFacesServletMappings;
	}

	public List<ConfiguredElement> getConfiguredFactoryExtensions() {
		return configuredFactoryExtensions;
	}

	public List<ConfiguredManagedBean> getConfiguredManagedBeans() {
		return configuredManagedBeans;
	}

	public List<String> getConfiguredSuffixes() {
		return configuredSuffixes;
	}

	public List<ConfiguredSystemEventListener> getConfiguredSystemEventListeners() {
		return configuredSystemEventListeners;
	}
}
