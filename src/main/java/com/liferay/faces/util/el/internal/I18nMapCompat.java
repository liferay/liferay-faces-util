/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.el.internal;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.ExternalContext;

import com.liferay.faces.util.cache.Cache;
import com.liferay.faces.util.cache.CacheFactory;
import com.liferay.faces.util.config.WebConfigParam;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class I18nMapCompat implements Map<String, Object>, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 170055432633295830L;

	// Static field must be declared volatile in order for the double-check idiom to work (requires JRE 1.5+)
	private static volatile Cache<String, String> messageCache;

	// Protected Data Members
	protected boolean cacheEnabled;

	public I18nMapCompat() {
		this.cacheEnabled = true;
	}

	/**
	 * This method initializes the message cache for I18nMap. The initialization cannot be performed in the constructor
	 * since this class is created by {@link UtilELResolver} before the {@link CacheFactory} has been created.
	 */
	protected Cache<String, String> getMessageCache(ExternalContext externalContext) {

		Cache<String, String> messageCache = I18nMapCompat.messageCache;

		// First check without locking (not yet thread-safe)
		if (messageCache == null) {

			synchronized (I18nMapCompat.class) {

				messageCache = I18nMapCompat.messageCache;

				// Second check with locking (thread-safe)
				if (messageCache == null) {

					int initialCacheCapacity = WebConfigParam.I18nELMapInitialCacheCapacity.getIntegerValue(
							externalContext);
					int maxCacheCapacity = WebConfigParam.I18nELMapMaxCacheCapacity.getIntegerValue(externalContext);

					if (maxCacheCapacity > -1) {
						messageCache = I18nMapCompat.messageCache = CacheFactory.getConcurrentLRUCacheInstance(
									externalContext, initialCacheCapacity, maxCacheCapacity);
					}
					else {
						messageCache = I18nMapCompat.messageCache = CacheFactory.getConcurrentCacheInstance(
									externalContext, initialCacheCapacity);
					}
				}
			}
		}

		return messageCache;
	}
}
