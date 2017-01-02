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
package com.liferay.faces.util.i18n.internal;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.application.ApplicationUtil;
import com.liferay.faces.util.i18n.I18n;
import com.liferay.faces.util.i18n.I18nUtil;


/**
 * @author  Neil Griffin
 */
public class I18nImpl implements I18n, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 707385608167301726L;

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
		return getMessage(facesContext, "i18n", locale, messageId, arguments);
	}

	@Override
	public String getMessage(FacesContext facesContext, String bundleKey, Locale locale, String messageId,
		Object... arguments) {

		String message = null;

		try {
			ResourceBundle resourceBundle = null;

			if (locale == null) {
				resourceBundle = ResourceBundle.getBundle(bundleKey, new UTF8Control());
			}
			else {
				resourceBundle = ResourceBundle.getBundle(bundleKey, locale, new UTF8Control());
			}

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

		Map<String, Object> facesResourceBundleCache = getFacesResourceBundleCache();
		ResourceBundle facesResourceBundle = (ResourceBundle) facesResourceBundleCache.get(locale.toString());

		if (facesResourceBundle == null) {

			Application application = facesContext.getApplication();
			String messageBundle = application.getMessageBundle();

			if (messageBundle == null) {
				messageBundle = FacesMessage.FACES_MESSAGES;
			}

			facesResourceBundle = ResourceBundle.getBundle(messageBundle, locale, new UTF8Control());
			facesResourceBundleCache.put(locale.toString(), facesResourceBundle);
		}

		return facesResourceBundle;
	}

	private Map<String, Object> getFacesResourceBundleCache() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String cacheName = I18nImpl.class.getName();

		return ApplicationUtil.getOrCreateApplicationCache(facesContext, cacheName);
	}
}
