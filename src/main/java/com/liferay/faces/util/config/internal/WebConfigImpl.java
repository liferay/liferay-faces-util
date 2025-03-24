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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.faces.util.config.ConfiguredServlet;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.WebConfig;


/**
 * @author  Neil Griffin
 */
public class WebConfigImpl implements WebConfig {

	// Private Data Members
	private Map<String, String> configuredContextParams;
	private List<ConfiguredServlet> configuredServlets;
	private List<ConfiguredServletMapping> configuredServletMappings;

	public WebConfigImpl() {
		this.configuredContextParams = new HashMap<String, String>();
		this.configuredServlets = new ArrayList<ConfiguredServlet>();
		this.configuredServletMappings = new ArrayList<ConfiguredServletMapping>();
	}

	public WebConfigImpl(Map<String, String> configuredContextParams, List<ConfiguredServlet> configuredServlets,
		List<ConfiguredServletMapping> configuredServletMappings) {
		this.configuredContextParams = configuredContextParams;
		this.configuredServlets = configuredServlets;
		this.configuredServletMappings = configuredServletMappings;
	}

	public Map<String, String> getConfiguredContextParams() {
		return configuredContextParams;
	}

	public List<ConfiguredServletMapping> getConfiguredServletMappings() {
		return configuredServletMappings;
	}

	public List<ConfiguredServlet> getConfiguredServlets() {
		return configuredServlets;
	}
}
