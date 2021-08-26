/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.config.WebConfig;


/**
 * @author  Neil Griffin
 */
public class ApplicationConfigImpl implements ApplicationConfig {

	// Private Data Members
	private String contextPath;
	private FacesConfig facesConfig;
	private WebConfig webConfig;

	public ApplicationConfigImpl(String contextPath, FacesConfig facesConfig, WebConfig webConfig) {
		this.contextPath = contextPath;
		this.facesConfig = facesConfig;
		this.webConfig = webConfig;
	}

	public String getContextPath() {
		return contextPath;
	}

	public FacesConfig getFacesConfig() {
		return facesConfig;
	}

	public WebConfig getWebConfig() {
		return webConfig;
	}
}
