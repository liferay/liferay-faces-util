/**
 * Copyright (c) 2000-2025 Liferay, Inc. All rights reserved.
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

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This interface defines a contract for obtaining internationalized messages of type {@link String} or
 * {@link FacesMessage}.
 *
 * @author Neil Griffin
 */
@ProviderType
public interface I18n {

	/**
	 * Creates (if necessary) and returns an internationalized {@link FacesMessage} based on the specified {@code
	 * locale}, {@code severity}, and {@code messageId}.
	 *
	 * @param facesContext The current faces context.
	 * @param locale       The locale of the message.
	 * @param severity     The severity of the message.
	 * @param messageId    The id of the message.
	 */
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId);

	/**
	 * Creates (if necessary) and returns an internationalized {@link FacesMessage} based on the specified {@code
	 * locale}, {@code severity}, and {@code messageId}. If the message contains a {@link java.text.MessageFormat}
	 * pattern then the specified {@code arguments} are inserted into the message accordingly.
	 *
	 * @param facesContext The current faces context.
	 * @param locale       The locale of the message.
	 * @param severity     The severity of the message.
	 * @param messageId    The id of the message.
	 * @param arguments    The values that are to be inserted according to the {@link java.text.MessageFormat} pattern.
	 */
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId, Object... arguments);

	/**
	 * Creates (if necessary) and returns an internationalized {@link String} message based on the specified {@code
	 * locale} and {@code messageId}.
	 *
	 * @param facesContext The current faces context.
	 * @param locale       The locale of the message.
	 * @param messageId    The id of the message.
	 */
	public String getMessage(FacesContext facesContext, Locale locale, String messageId);

	/**
	 * Creates (if necessary) and returns an internationalized {@link String} message based on the specified {@code
	 * locale}, {@code messageId}. If the message contains a {@link java.text.MessageFormat} pattern then the specified
	 * {@code arguments} are inserted into the message accordingly.
	 *
	 * @param facesContext The current faces context.
	 * @param locale       The locale of the message.
	 * @param messageId    The id of the message.
	 * @param arguments    The values that are to be inserted according to the {@link java.text.MessageFormat} pattern.
	 */
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments);
}
