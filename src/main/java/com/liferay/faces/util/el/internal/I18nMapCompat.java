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

import javax.faces.application.ProjectStage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.util.cache.Cache;
import com.liferay.faces.util.cache.CacheFactory;
import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class I18nMapCompat implements Map<String, Object>, SystemEventListener, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 170055432633295830L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(I18nMapCompat.class);

	// Protected Data Members
	protected boolean cacheEnabled;

	public I18nMapCompat() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext == null) {
			this.cacheEnabled = true;
		}
		else {
			this.cacheEnabled = !facesContext.isProjectStage(ProjectStage.Development);
		}
	}

	@Override
	public boolean isListenerForSource(Object source) {
		return (source instanceof ApplicationConfig);
	}

	/**
	 * This method initializes the message cache for I18nMap. The initialization cannot be performed in the constructor
	 * since this class is created by {@link UtilELResolver} before the {@link CacheFactory} has been created.
	 */
	@Override
	public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {

		FacesContext startupFacesContext = FacesContext.getCurrentInstance();

		// Store the i18n message cache in the application map (as a Servlet Context attribute).
		if (startupFacesContext != null) {

			ExternalContext externalContext = startupFacesContext.getExternalContext();
			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			Cache<String, String> messageCache;

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
		}
		else {
			logger.error("Unable to store the i18n message cache in the application map");
		}
	}

	protected Cache<String, String> getMessageCache(ExternalContext externalContext) {

		Map<String, Object> applicationMap = externalContext.getApplicationMap();

		return (Cache<String, String>) applicationMap.get(I18nMap.class.getName());
	}
}
