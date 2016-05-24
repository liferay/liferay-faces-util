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
package com.liferay.faces.util.context.internal;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextFactory;


/**
 * @author  Neil Griffin
 */
public class MessageContextImpl implements MessageContext {

	private Map<Locale, ResourceBundle> facesResourceBundleMap = new ConcurrentHashMap<Locale, ResourceBundle>();

	@Override
	public String getMessage(Locale locale, String messageId) {

		String message = null;

		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = ResourceBundle.getBundle("i18n", locale);
			message = resourceBundle.getString(messageId);
		}
		catch (MissingResourceException e) {
			// ignore
		}

		if (message == null) {
			resourceBundle = getFacesResourceBundle(locale);

			if (resourceBundle != null) {

				try {
					message = resourceBundle.getString(messageId);
				}
				catch (MissingResourceException e) {
					// ignore
				}
			}
		}

		if (message == null) {
			message = messageId;
		}

		return message;
	}

	@Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		String message = getMessage(locale, messageId);

		if (message != null) {
			message = MessageFormat.format(message, arguments);
		}

		return message;
	}

	@Override
	public FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String key) {

		String messageId = key;
		Locale locale = facesContext.getViewRoot().getLocale();

		return newFacesMessage(locale, severity, messageId);
	}

	@Override
	public FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId) {
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(severity);
		facesMessage.setSummary(messageId);
		facesMessage.setDetail(null);

		MessageContext messageContext = MessageContextFactory.getMessageContextInstance();
		String summary = messageContext.getMessage(locale, messageId);

		if (summary != null) {
			facesMessage.setSummary(summary);

			String detailMessageId = messageId + "_detail";
			String detail = getMessage(locale, detailMessageId);

			if ((detail != null) && (!detailMessageId.equals(detail))) {
				facesMessage.setDetail(detail);
			}
		}

		return facesMessage;
	}

	@Override
	public FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String key, Object... arguments) {

		String messageId = key;
		Locale locale = facesContext.getViewRoot().getLocale();

		return newFacesMessage(locale, severity, messageId, arguments);
	}

	@Override
	public FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId, Object... arguments) {

		FacesMessage facesMessage = newFacesMessage(locale, severity, messageId);

		String summary = facesMessage.getSummary();

		if (summary != null) {
			facesMessage.setSummary(MessageFormat.format(summary, arguments));
		}

		String detail = facesMessage.getDetail();

		if (detail != null) {
			facesMessage.setDetail(MessageFormat.format(detail, arguments));
		}

		return facesMessage;
	}

	protected ResourceBundle getFacesResourceBundle(Locale locale) {
		ResourceBundle facesResourceBundle = facesResourceBundleMap.get(locale);

		if (facesResourceBundle == null) {
			String messageBundle = FacesContext.getCurrentInstance().getApplication().getMessageBundle();

			if (messageBundle == null) {
				messageBundle = FacesMessage.FACES_MESSAGES;
			}

			facesResourceBundle = ResourceBundle.getBundle(messageBundle, locale);
			facesResourceBundleMap.put(locale, facesResourceBundle);
		}

		return facesResourceBundle;
	}

}
