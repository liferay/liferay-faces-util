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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.i18n.I18n;


/**
 * @author  Neil Griffin
 */
public class I18nImpl implements I18n {

	// Private Data Members
	private Map<Locale, ResourceBundle> facesResourceBundleMap = new ConcurrentHashMap<Locale, ResourceBundle>();

	@Override
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId) {
		return getFacesMessage(facesContext, locale, severity, messageId, null);
	}

	@Override
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId, Object... arguments) {

		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(severity);
		facesMessage.setDetail(null);

		String summary;

		if (arguments == null) {
			summary = getMessage(facesContext, locale, messageId);
		}
		else {
			summary = getMessage(facesContext, locale, messageId, arguments);
		}

		if (summary == null) {
			facesMessage.setSummary(messageId);
		}
		else {
			facesMessage.setSummary(summary);

			String detailMessageId = messageId + "_detail";
			String detail;

			if (arguments == null) {
				detail = getMessage(facesContext, locale, detailMessageId);
			}
			else {
				detail = getMessage(facesContext, locale, detailMessageId, arguments);
			}

			if (detail == null) {
				facesMessage.setDetail(detailMessageId);
			}
			else {
				facesMessage.setDetail(detail);
			}
		}

		return facesMessage;
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId) {
		return getMessage(facesContext, locale, messageId, null);
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {

		String message = null;

		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n", locale);
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

			if (arguments != null) {
				message = MessageFormat.format(message, arguments);
			}
		}

		return message;
	}

	private ResourceBundle getFacesResourceBundle(FacesContext facesContext, Locale locale) {

		ResourceBundle facesResourceBundle = facesResourceBundleMap.get(locale);

		if (facesResourceBundle == null) {

			Application application = facesContext.getApplication();
			String messageBundle = application.getMessageBundle();

			if (messageBundle == null) {
				messageBundle = FacesMessage.FACES_MESSAGES;
			}

			facesResourceBundle = ResourceBundle.getBundle(messageBundle, locale);
			facesResourceBundleMap.put(locale, facesResourceBundle);
		}

		return facesResourceBundle;
	}
}
