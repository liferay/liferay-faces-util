/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.util.context;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This is an abstract class that provides a convenient base implementation for introducing an internationalized {@link
 * ResourceBundle} into the {@link MessageContext} delegation chain. For the sake of performance, lookups into the
 * ResourceBundle are cached by this class in a synchronized map.
 *
 * @author  Neil Griffin
 */
public abstract class MessageContextBundleBase extends MessageContextWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MessageContextBundleBase.class);

	// Private Data Members
	private boolean initialized;
	private Map<String, String> messageMap;
	private MessageContext wrappedMessageContext;

	public MessageContextBundleBase(MessageContext messageContext) {
		this.wrappedMessageContext = messageContext;
		this.messageMap = new ConcurrentHashMap<String, String>();
	}

	public abstract String getBundleKey();

	@Override
	public String getMessage(Locale locale, String messageId) {

		String message = null;
		String key = messageId;

		if (locale != null) {
			key = locale.toString() + messageId;
		}

		if (messageMap.containsKey(key)) {
			message = messageMap.get(key);

			if ("".equals(message)) {
				message = null;
			}
		}
		else {

			ResourceBundle resourceBundle = null;

			try {
				String bundleKey = getBundleKey();
				resourceBundle = ResourceBundle.getBundle(bundleKey, locale);
			}
			catch (MissingResourceException e) {

				if (!initialized) {
					logger.error(e);
				}
			}

			initialized = true;

			if (resourceBundle != null) {

				try {
					message = resourceBundle.getString(messageId);
					messageMap.put(key, message);
				}
				catch (MissingResourceException e) {
					messageMap.put(key, "");
				}
			}
		}

		if (message == null) {
			message = super.getMessage(locale, messageId);
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
	public MessageContext getWrapped() {
		return wrappedMessageContext;
	}
}
