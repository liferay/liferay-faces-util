/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.context;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class MessageContextWrapper implements MessageContext, Wrapper<MessageContext> {

	@Override
	public FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId) {
		return getWrapped().newFacesMessage(locale, severity, messageId);
	}

	@Override
	public FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String messageId) {
		return getWrapped().newFacesMessage(facesContext, severity, messageId);
	}

	@Override
	public FacesMessage newFacesMessage(Locale locale, Severity severity, String messageId, Object... arguments) {
		return getWrapped().newFacesMessage(locale, severity, messageId, arguments);
	}

	@Override
	public FacesMessage newFacesMessage(FacesContext facesContext, Severity severity, String messageId,
		Object... arguments) {
		return getWrapped().newFacesMessage(facesContext, severity, messageId, arguments);
	}

	@Override
	public String getMessage(Locale locale, String messageId) {
		return getWrapped().getMessage(locale, messageId);
	}

	@Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return getWrapped().getMessage(locale, messageId, arguments);
	}

	public abstract MessageContext getWrapped();
}
