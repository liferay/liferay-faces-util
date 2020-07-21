/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.application.Application;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.cache.Cache;
import com.liferay.faces.util.cache.CacheFactory;
import com.liferay.faces.util.config.WebConfigParam;
import com.liferay.faces.util.i18n.I18n;
import com.liferay.faces.util.i18n.I18nFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class I18nMap implements Map<String, Object>, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 5549598732411060854L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(I18nMap.class);

	/**
	 * This method initializes the message cache for I18nMap. The initialization cannot be performed in the constructor
	 * since this class is created by {@link UtilELResolver} before the {@link CacheFactory} has been created. This
	 * method is called in {@link
	 * com.liferay.faces.util.event.internal.ApplicationStartupListener#processSystemEvent(java.util.EventObject)} to
	 * ensure that the CacheFactory has been created.
	 *
	 * @param  facesContext
	 */
	public static void initMessageCache(FacesContext facesContext) {

		if (!facesContext.isProjectStage(ProjectStage.Development)) {

			// Store the i18n message cache in the application map (as a Servlet Context attribute).
			ExternalContext externalContext = facesContext.getExternalContext();
			Cache<String, String> messageCache;
			int initialCacheCapacity = WebConfigParam.I18nELMapInitialCacheCapacity.getIntegerValue(externalContext);
			int maxCacheCapacity = WebConfigParam.I18nELMapMaxCacheCapacity.getIntegerValue(externalContext);

			if (maxCacheCapacity > -1) {
				messageCache = CacheFactory.getConcurrentLRUCacheInstance(externalContext, initialCacheCapacity,
						maxCacheCapacity);
			}
			else {
				messageCache = CacheFactory.getConcurrentCacheInstance(externalContext, initialCacheCapacity);
			}

			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			applicationMap.put(I18nMap.class.getName(), messageCache);
		}
	}

	private static Cache<String, String> getMessageCache(ExternalContext externalContext) {

		Map<String, Object> applicationMap = externalContext.getApplicationMap();

		return (Cache<String, String>) applicationMap.get(I18nMap.class.getName());
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object get(Object key) {

		String message = null;

		if (key != null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIViewRoot viewRoot = facesContext.getViewRoot();
			Locale locale = viewRoot.getLocale();

			if (locale == null) {
				Application application = facesContext.getApplication();
				locale = application.getDefaultLocale();
			}

			ExternalContext externalContext = facesContext.getExternalContext();
			I18n i18n = I18nFactory.getI18nInstance(externalContext);

			String keyAsString = key.toString();
			Cache<String, String> messageCache = getMessageCache(externalContext);

			if (messageCache != null) {

				String messageKey = keyAsString;

				if (locale != null) {
					messageKey = locale.toString().concat(keyAsString);
				}

				message = messageCache.getValue(messageKey);

				if (message == null) {

					message = i18n.getMessage(facesContext, locale, keyAsString);

					if (message != null) {
						message = messageCache.putValueIfAbsent(messageKey, message);
					}
				}
			}
			else {
				message = i18n.getMessage(facesContext, locale, keyAsString);
			}
		}

		return message;
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object put(String key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends String, ?> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object remove(Object key) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the message associated with the specified messageId according to the locale in the current FacesContext.
	 * This method is primarily meant to be called via EL, providing the implementation supports passing parameters
	 * (like JBoss EL).
	 *
	 * @param   messageId  The message key.
	 * @param   arg1       The first argument, assuming that the messageId has a {0} token.
	 *
	 * @return  The internationalized message.
	 */
	public String replace(String messageId, String arg1) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale locale = viewRoot.getLocale();

		if (locale == null) {
			Application application = facesContext.getApplication();
			locale = application.getDefaultLocale();
		}

		ExternalContext externalContext = facesContext.getExternalContext();
		I18n i18n = I18nFactory.getI18nInstance(externalContext);

		return i18n.getMessage(facesContext, locale, messageId, arg1);
	}

	/**
	 * Gets the message associated with the specified messageId according to the locale in the current FacesContext.
	 * This method is primarily meant to be called via EL, providing the implementation supports passing parameters
	 * (like JBoss EL).
	 *
	 * @param   messageId  The message key.
	 * @param   arg1       The first argument, assuming that the messageId has a {0} token.
	 * @param   arg2       The second argument, assuming that the messageId has a {1} token.
	 *
	 * @return  The internationalized message.
	 */
	public String replace(String messageId, String arg1, String arg2) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale locale = viewRoot.getLocale();

		if (locale == null) {
			Application application = facesContext.getApplication();
			locale = application.getDefaultLocale();
		}

		ExternalContext externalContext = facesContext.getExternalContext();
		I18n i18n = I18nFactory.getI18nInstance(externalContext);

		return i18n.getMessage(facesContext, locale, messageId, arg1, arg2);
	}

	/**
	 * Gets the message associated with the specified messageId according to the locale in the current FacesContext.
	 * This method is primarily meant to be called via EL, providing the implementation supports passing parameters
	 * (like JBoss EL).
	 *
	 * @param   messageId  The message key.
	 * @param   arg1       The first argument, assuming that the messageId has a {0} token.
	 * @param   arg2       The second argument, assuming that the messageId has a {1} token.
	 * @param   arg3       The third argument, assuming that the messageId has a {2} token.
	 *
	 * @return  The internationalized message.
	 */
	public String replace(String messageId, String arg1, String arg2, String arg3) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale locale = viewRoot.getLocale();

		if (locale == null) {
			Application application = facesContext.getApplication();
			locale = application.getDefaultLocale();
		}

		ExternalContext externalContext = facesContext.getExternalContext();
		I18n i18n = I18nFactory.getI18nInstance(externalContext);

		return i18n.getMessage(facesContext, locale, messageId, arg1, arg2, arg3);
	}

	/**
	 * Gets the message associated with the specified messageId according to the locale in the current FacesContext.
	 * This method is primarily meant to be called via EL, providing the implementation supports passing parameters
	 * (like JBoss EL).
	 *
	 * @param   messageId  The message key.
	 * @param   arg1       The first argument, assuming that the messageId has a {0} token.
	 * @param   arg2       The second argument, assuming that the messageId has a {1} token.
	 * @param   arg3       The third argument, assuming that the messageId has a {2} token.
	 * @param   arg4       The fourth argument, assuming that the messageId has a {3} token.
	 *
	 * @return  The internationalized message.
	 */
	public String replace(String messageId, String arg1, String arg2, String arg3, String arg4) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale locale = viewRoot.getLocale();

		if (locale == null) {
			Application application = facesContext.getApplication();
			locale = application.getDefaultLocale();
		}

		ExternalContext externalContext = facesContext.getExternalContext();
		I18n i18n = I18nFactory.getI18nInstance(externalContext);

		return i18n.getMessage(facesContext, locale, messageId, arg1, arg2, arg3, arg4);
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Object> values() {
		throw new UnsupportedOperationException();
	}
}
