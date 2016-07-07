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
package com.liferay.faces.util.el.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextFactory;


/**
 * @author  Neil Griffin
 */
public class I18N extends I18NCompat {

	// Private Constants
	private static final Enumeration<String> EMPTY_KEYS = Collections.enumeration(new ArrayList<String>());

	// Private Data Members
	private Map<String, String> cache;

	public I18N() {
		super();
		this.cache = new ConcurrentHashMap<String, String>();
	}

	/**
	 * This method is required by the ResourceBundle abstract class, but it will never be called in the normal running
	 * of a JSF webapp using the EL. Therefore, it just returns an empty Enumeration of Strings.
	 */
	@Override
	public Enumeration<String> getKeys() {
		return EMPTY_KEYS;
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

		MessageContext messageContext = MessageContextFactory.getMessageContextInstance();

		return messageContext.getMessage(locale, messageId, arg1);
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

		MessageContext messageContext = MessageContextFactory.getMessageContextInstance();

		return messageContext.getMessage(locale, messageId, arg1, arg2);
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

		MessageContext messageContext = MessageContextFactory.getMessageContextInstance();

		return messageContext.getMessage(locale, messageId, arg1, arg2, arg3);
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

		MessageContext messageContext = MessageContextFactory.getMessageContextInstance();

		return messageContext.getMessage(locale, messageId, arg1, arg2, arg3, arg4);
	}

	@Override
	protected Object handleGetObject(String key) {

		String message = null;

		if (key != null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIViewRoot viewRoot = facesContext.getViewRoot();
			Locale locale = viewRoot.getLocale();

			if (locale == null) {
				Application application = facesContext.getApplication();
				locale = application.getDefaultLocale();
			}

			MessageContext messageContext = MessageContextFactory.getMessageContextInstance();

			if (cacheEnabled) {

				String messageKey = key;

				if (locale != null) {
					messageKey = locale.toString().concat(key);
				}

				message = cache.get(messageKey);

				if (message == null) {
					message = messageContext.getMessage(locale, key);

					if (message != null) {
						cache.put(messageKey, message);
					}
				}
			}
			else {
				message = messageContext.getMessage(locale, key);
			}
		}

		return message;
	}
}
