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
package com.liferay.faces.util.i18n;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This is an abstract class that provides a convenient base implementation for introducing an internationalized {@link
 * ResourceBundle} into the {@link I18n} delegation chain. For the sake of performance, lookups into the ResourceBundle
 * are cached by this class in a synchronized map.
 *
 * @author  Neil Griffin
 */
public abstract class I18nBundleBase extends I18nWrapper implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3785524975078495843L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(I18nBundleBase.class);

	// Private Data Members
	private transient Map<String, String> messageCache;
	private I18n wrappedI18n;

	public I18nBundleBase(I18n i18n) {
		this.wrappedI18n = i18n;
		this.messageCache = new ConcurrentHashMap<String, String>();
	}

	public abstract String getBundleKey();

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

		String message = null;
		String key = messageId;

		if (locale != null) {
			key = locale.toString() + messageId;
		}

		if (messageCache.containsKey(key)) {
			message = messageCache.get(key);

			if ("".equals(message)) {
				message = null;
			}
		}
		else {

			ResourceBundle resourceBundle = null;

			try {
				String bundleKey = getBundleKey();
				resourceBundle = ResourceBundle.getBundle(bundleKey, locale, new UTF8Control());
			}
			catch (MissingResourceException e) {
				logger.error(e);
			}

			if (resourceBundle != null) {

				try {
					message = resourceBundle.getString(messageId);
					messageCache.put(key, message);
				}
				catch (MissingResourceException e) {
					messageCache.put(key, "");
				}
			}
		}

		if (message == null) {

			message = super.getMessage(facesContext, locale, messageId);
		}

		return message;
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {

		String message = getMessage(facesContext, locale, messageId);

		if (message != null) {
			message = MessageFormat.format(message, arguments);
		}

		return message;
	}

	@Override
	public I18n getWrapped() {
		return wrappedI18n;
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		wrappedI18n = (I18n) stream.readObject();
		messageCache = new ConcurrentHashMap<String, String>();
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(wrappedI18n);
	}
}
