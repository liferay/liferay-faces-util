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

import javax.faces.context.ExternalContext;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.i18n.I18n;


/**
 * @author  Neil Griffin
 */
@ProviderType
public enum WebConfigParam implements ConfigParam<ExternalContext> {

	/**
	 * Integer indicating the initial cache capacity for the message cache used by the i18n EL utility. The default
	 * value of this param is 16. For more details, see {@link
	 * com.liferay.faces.util.cache.CacheFactory#getConcurrentCache(int)} and {@link java.util.HashMap#HashMap()}.
	 *
	 * @since  1.1
	 * @since  2.1
	 * @since  3.1
	 */
	I18nELMapInitialCacheCapacity("com.liferay.faces.util.el.i18n.INITIAL_CACHE_CAPACITY", 16),

	/**
	 * Integer indicating the default maximum cache capacity for the resource bundle cache used by the i18n EL utility.
	 * The default value of this param is -1 which indicates that no maximum should be enforced. For more details, see
	 * {@link com.liferay.faces.util.cache.CacheFactory#getConcurrentCache(int)} and {@link
	 * java.util.HashMap#HashMap()}.
	 *
	 * @since  1.1
	 * @since  2.1
	 * @since  3.1
	 */
	I18nInitialCacheCapacity(I18n.class.getName() + ".INITIAL_CACHE_CAPACITY", 16),

	/**
	 * Integer indicating the default maximum cache capacity for the message cache used by the i18n EL utility. The
	 * default value of this param is -1 which indicates that no maximum capacity should be enforced (in other words,
	 * the cache will be obtained via {@link com.liferay.faces.util.cache.CacheFactory#getConcurrentCache(int)}).
	 * Otherwise, an LRU cache with the specified maximum capacity will be obtained via {@link
	 * com.liferay.faces.util.cache.CacheFactory#getConcurrentLRUCache(int, int)}.
	 *
	 * @since  1.1
	 * @since  2.1
	 * @since  3.1
	 */
	I18nELMapMaxCacheCapacity("com.liferay.faces.util.el.i18n.MAX_CACHE_CAPACITY", -1),

	/**
	 * Integer indicating the maximum cache capacity for the resource bundle cache used by the {@link I18n} utility. The
	 * default value of this param is -1 which indicates that no maximum capacity should be enforced (in other words,
	 * the cache will be obtained via {@link com.liferay.faces.util.cache.CacheFactory#getConcurrentCache(int)}).
	 * Otherwise, an LRU cache with the specified maximum capacity will be obtained via {@link
	 * com.liferay.faces.util.cache.CacheFactory#getConcurrentLRUCache(int, int)}.
	 *
	 * @since  1.1
	 * @since  2.1
	 * @since  3.1
	 */
	I18nMaxCacheCapacity(I18n.class.getName() + ".MAX_CACHE_CAPACITY", -1),

	/** Boolean indicating whether or not XML entities should be resolved */
	ResolveXMLEntities("com.liferay.faces.util.resolveXMLEntities", "com.liferay.faces.bridge.resolveXMLEntities",
		false),

	/**
	 * Absolute path to a directory (folder) in which the uploaded file data should be written to. Default value is the
	 * value of the system property "java.io.tmpdir".
	 */
	UploadedFilesDir("com.liferay.faces.util.uploadedFilesDir", System.getProperty("java.io.tmpdir")),

	/** Maximum file size for an uploaded file. Default is 104,857,600 (~100MB), upper limit is 2,147,483,647 (~2GB) */
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
