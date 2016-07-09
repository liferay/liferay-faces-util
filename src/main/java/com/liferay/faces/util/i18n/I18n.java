/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.i18n;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 * This interface defines a contract for obtaining internationalized messages of type {@link String} or {@link
 * FacesMessage}.
 *
 * @author  Neil Griffin
 */
public interface I18n {

	/**
	 * Creates (if necessary) and returns an internationalized {@link FacesMessage} based on the specified {@code
	 * locale}, {@code severity}, and {@code messageId}.
	 *
	 * @param  facesContext  The current faces context.
	 * @param  locale        The locale of the message.
	 * @param  severity      The severity of the message.
	 * @param  messageId     The id of the message.
	 */
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId);

	/**
	 * Creates (if necessary) and returns an internationalized {@link FacesMessage} based on the specified {@code
	 * locale}, {@code severity}, and {@code messageId}. If the message contains a {@link java.text.MessageFormat}
	 * pattern then the specified {@code arguments} are inserted into the message accordingly.
	 *
	 * @param  facesContext  The current faces context.
	 * @param  locale        The locale of the message.
	 * @param  severity      The severity of the message.
	 * @param  messageId     The id of the message.
	 * @param  arguments     The values that are to be inserted according to the {@link java.text.MessageFormat}
	 *                       pattern.
	 */
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId, Object... arguments);

	/**
	 * Creates (if necessary) and returns an internationalized {@link String} message based on the specified {@code
	 * locale} and {@code messageId}.
	 *
	 * @param  facesContext  The current faces context.
	 * @param  locale        The locale of the message.
	 * @param  messageId     The id of the message.
	 */
	public String getMessage(FacesContext facesContext, Locale locale, String messageId);

	/**
	 * Creates (if necessary) and returns an internationalized {@link String} message based on the specified {@code
	 * locale}, {@code messageId}. If the message contains a {@link java.text.MessageFormat} pattern then the specified
	 * {@code arguments} are inserted into the message accordingly.
	 *
	 * @param  facesContext  The current faces context.
	 * @param  locale        The locale of the message.
	 * @param  messageId     The id of the message.
	 * @param  arguments     The values that are to be inserted according to the {@link java.text.MessageFormat}
	 *                       pattern.
	 */
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments);
}
