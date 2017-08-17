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
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class I18nMapCompat implements Map<String, Object>, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 170055432633295830L;

	// Protected Data Members
	protected boolean cacheEnabled;

	// Instance field must be declared volatile in order for the double-check idiom to work (requires JRE 1.5+)
	private volatile Boolean cacheInitialized;

	public I18nMapCompat() {
		this.cacheEnabled = true;
	}

	/**
	 * This method initializes the message cache for I18nMap. The initialization cannot be performed in the constructor
	 * since this class is created by {@link UtilELResolver} before the {@link CacheFactory} has been created.
	 */
	protected Cache<String, String> getMessageCache(ExternalContext externalContext) {

		Map<String, Object> applicationMap = externalContext.getApplicationMap();
		Cache<String, String> messageCache = (Cache<String, String>) applicationMap.get(I18nMap.class.getName());
		Boolean cacheInitialized = this.cacheInitialized;

		// First check without locking (not yet thread-safe)
		if (cacheInitialized == null) {

			synchronized (this) {

				cacheInitialized = this.cacheInitialized;

				// Second check with locking (thread-safe)
				if (cacheInitialized == null) {

					String maxCacheCapacityString = externalContext.getInitParameter(
							"com.liferay.faces.util.el.i18n.maxCacheCapacity");

					if (maxCacheCapacityString != null) {

						CacheFactory cacheFactory = (CacheFactory) FactoryExtensionFinder.getFactory(externalContext,
								CacheFactory.class);
						int initialCacheCapacity = cacheFactory.getDefaultInitialCapacity();
						int maxCacheCapacity = Integer.parseInt(maxCacheCapacityString);
						messageCache = cacheFactory.getConcurrentCache(initialCacheCapacity, maxCacheCapacity);
					}
					else {
						messageCache = CacheFactory.getConcurrentCacheInstance(externalContext);
					}

					applicationMap.put(I18nMap.class.getName(), messageCache);
					cacheInitialized = this.cacheInitialized = true;
				}
			}
		}

		return messageCache;
	}
}
