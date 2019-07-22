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
package com.liferay.faces.util.i18n.internal;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.cache.Cache;
import com.liferay.faces.util.cache.CacheFactory;
import com.liferay.faces.util.config.WebConfigParam;
import com.liferay.faces.util.i18n.I18n;
import com.liferay.faces.util.i18n.I18nUtil;
import com.liferay.faces.util.internal.TCCLUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class I18nImpl implements I18n, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 707385608167301726L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(I18nImpl.class);

	public I18nImpl() {

		// This class is instantiated by the I18nFactoryImpl class during application startup.
		FacesContext startupFacesContext = FacesContext.getCurrentInstance();

		// Store the resource bundle cache in the application map (as a Servlet Context attribute).
		if (startupFacesContext != null) {

			ExternalContext externalContext = startupFacesContext.getExternalContext();
			Cache<Locale, ResourceBundle> facesResourceBundleCache;
			int initialCacheCapacity = WebConfigParam.I18nInitialCacheCapacity.getIntegerValue(externalContext);
			int maxCacheCapacity = WebConfigParam.I18nMaxCacheCapacity.getIntegerValue(externalContext);

			if (maxCacheCapacity > -1) {
				facesResourceBundleCache = CacheFactory.getConcurrentLRUCacheInstance(externalContext,
						initialCacheCapacity, maxCacheCapacity);
			}
			else {
				facesResourceBundleCache = CacheFactory.getConcurrentCacheInstance(externalContext,
						initialCacheCapacity);
			}

			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			applicationMap.put(I18nImpl.class.getName(), facesResourceBundleCache);
		}
		else {
			logger.error("Unable to store the resource bundle cache in the application map");
		}
	}

	@Override
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId) {
		return I18nUtil.getFacesMessage(this, facesContext, locale, severity, messageId);
	}

	@Override
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId, Object... arguments) {
		return I18nUtil.getFacesMessage(this, facesContext, locale, severity, messageId, arguments);
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId) {
		return getMessage(facesContext, locale, messageId, new Object[] {});
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {

		String message = null;

		try {
			ClassLoader classLoader = TCCLUtil.getThreadContextClassLoaderOrDefault(getClass());
			ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n", locale, classLoader, new UTF8Control());
			message = resourceBundle.getString(messageId);
		}
		catch (MissingResourceException e) {
			// ignore
		}

		if (message == null) {

			ResourceBundle facesResourceBundle = getFacesResourceBundle(facesContext, locale);

			if (facesResourceBundle != null) {

				try {
					message = facesResourceBundle.getString(messageId);
				}
				catch (MissingResourceException e) {
					// ignore
				}
			}
		}

		if (message == null) {
			message = messageId;
		}
		else {

			if ((arguments != null) && (arguments.length > 0)) {
				message = MessageFormat.format(message, arguments);
			}
		}

		return message;
	}

	private ResourceBundle getFacesResourceBundle(FacesContext facesContext, Locale locale) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> applicationMap = externalContext.getApplicationMap();
		Cache<Locale, ResourceBundle> facesResourceBundleCache = (Cache<Locale, ResourceBundle>) applicationMap.get(
				I18nImpl.class.getName());

		ResourceBundle facesResourceBundle = null;

		if (facesResourceBundleCache != null) {
			facesResourceBundle = facesResourceBundleCache.getValue(locale);
		}

		if (facesResourceBundle == null) {

			Application application = facesContext.getApplication();
			String messageBundle = application.getMessageBundle();

			if (messageBundle == null) {
				messageBundle = FacesMessage.FACES_MESSAGES;
			}

			ClassLoader classLoader = TCCLUtil.getThreadContextClassLoaderOrDefault(getClass());
			facesResourceBundle = ResourceBundle.getBundle(messageBundle, locale, classLoader, new UTF8Control());

			if (facesResourceBundleCache != null) {
				facesResourceBundle = facesResourceBundleCache.putValueIfAbsent(locale, facesResourceBundle);
			}
		}

		return facesResourceBundle;
	}
}
