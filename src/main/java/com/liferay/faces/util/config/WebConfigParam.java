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
package com.liferay.faces.util.config;

import javax.faces.context.ExternalContext;

import com.liferay.faces.util.config.ConfigParam;
import com.liferay.faces.util.config.WebConfigParamUtil;
import com.liferay.faces.util.helper.BooleanHelper;


/**
 * @author  Neil Griffin
 */
public enum WebConfigParam implements ConfigParam<ExternalContext> {

	/** Boolean indicating whether or not XML entities should be resolved */
	ResolveXMLEntities("com.liferay.faces.util.resolveXMLEntities", "com.liferay.faces.bridge.resolveXMLEntities",
		false),

	/**
	 * Absolute path to a directory (folder) in which the uploaded file data should be written to. Default value is the
	 * value of the system property "java.io.tmpdir".
	 */
	UploadedFilesDir("com.liferay.faces.util.uploadedFilesDir", System.getProperty("java.io.tmpdir")),

	/** Maximum file size for an uploaded file. Default is 104857600 bytes (100MB) */
	UploadedFileMaxSize("com.liferay.faces.util.uploadedFileMaxSize", 104857600);

	// Private Data Members
	private String alternateName;
	private boolean defaultBooleanValue;
	private String defaultStringValue;
	private int defaultIntegerValue;
	private long defaultLongValue;
	private String name;

	private WebConfigParam(String name, String defaultStringValue) {
		this.name = name;

		if (BooleanHelper.isTrueToken(defaultStringValue)) {
			this.defaultBooleanValue = true;
			this.defaultIntegerValue = 1;
		}
		else {
			this.defaultBooleanValue = false;
			this.defaultIntegerValue = 0;
		}

		this.defaultStringValue = defaultStringValue;
	}

	private WebConfigParam(String name, int defaultIntegerValue) {
		this.name = name;
		this.defaultBooleanValue = (defaultIntegerValue != 0);
		this.defaultIntegerValue = defaultIntegerValue;
		this.defaultLongValue = defaultIntegerValue;
		this.defaultStringValue = Integer.toString(defaultIntegerValue);
	}

	private WebConfigParam(String name, long defaultLongValue) {
		this.name = name;
		this.defaultBooleanValue = (defaultLongValue != 0);

		if (defaultLongValue < Integer.MIN_VALUE) {
			this.defaultIntegerValue = Integer.MIN_VALUE;
		}
		else if (defaultLongValue > Integer.MAX_VALUE) {
			this.defaultIntegerValue = Integer.MAX_VALUE;
		}
		else {
			this.defaultIntegerValue = (int) defaultLongValue;
		}

		this.defaultLongValue = defaultLongValue;
		this.defaultStringValue = Long.toString(defaultLongValue);
	}

	private WebConfigParam(String name, String alternateName, boolean defaultBooleanValue) {
		this.name = name;
		this.alternateName = alternateName;

		if (defaultBooleanValue) {
			this.defaultBooleanValue = true;
			this.defaultIntegerValue = 1;
			this.defaultLongValue = 1L;
			this.defaultStringValue = Boolean.TRUE.toString();
		}
		else {
			this.defaultBooleanValue = false;
			this.defaultIntegerValue = 0;
			this.defaultLongValue = 0L;
			this.defaultStringValue = Boolean.FALSE.toString();
		}
	}

	public String getAlternateName() {
		return alternateName;
	}

	@Override
	public boolean getBooleanValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getBooleanValue(externalContext, name, alternateName, defaultBooleanValue);
	}

	@Override
	public String getConfiguredValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getConfiguredValue(externalContext, name, alternateName);
	}

	public boolean getDefaultBooleanValue() {
		return defaultBooleanValue;
	}

	public int getDefaultIntegerValue() {
		return defaultIntegerValue;
	}

	@Override
	public long getDefaultLongValue() {
		return defaultLongValue;
	}

	public String getDefaultStringValue() {
		return defaultStringValue;
	}

	@Override
	public int getIntegerValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getIntegerValue(externalContext, name, alternateName, defaultIntegerValue);
	}

	@Override
	public long getLongValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getLongValue(externalContext, name, alternateName, defaultLongValue);
	}

	public String getName() {
		return name;
	}

	@Override
	public String getStringValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getStringValue(externalContext, name, alternateName, defaultStringValue);
	}

	@Override
	public boolean isConfigured(ExternalContext externalContext) {
		return WebConfigParamUtil.isSpecified(externalContext, name, alternateName);
	}
}
