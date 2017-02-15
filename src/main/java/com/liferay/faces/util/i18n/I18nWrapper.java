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

import java.util.Locale;

import javax.faces.FacesWrapper;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public abstract class I18nWrapper implements I18n, FacesWrapper<I18n> {

	@Override
	public abstract I18n getWrapped();

	/**
	 * See {@link I18n#getFacesMessage(FacesContext, Locale, FacesMessage.Severity, String)}
	 */
	@Override
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId) {
		return getWrapped().getFacesMessage(facesContext, locale, severity, messageId);
	}

	/**
	 * See {@link I18n#getFacesMessage(FacesContext, Locale, FacesMessage.Severity, String, Object...)}
	 */
	@Override
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId, Object... arguments) {
		return getWrapped().getFacesMessage(facesContext, locale, severity, messageId, arguments);
	}

	/**
	 * See {@link I18n#getMessage(FacesContext, Locale, String)}
	 */
	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId) {
		return getWrapped().getMessage(facesContext, locale, messageId);
	}

	/**
	 * See {@link I18n#getMessage(FacesContext, Locale, String, Object...)}
	 */
	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {
		return getWrapped().getMessage(facesContext, locale, messageId, arguments);
	}

	/**
	 * See {@link I18n#getMessage(FacesContext, String, Locale, String, Object...)}
	 */
	@Override
	public String getMessage(FacesContext facesContext, String bundleKey, Locale locale, String messageId,
		Object... arguments) {
		return getWrapped().getMessage(facesContext, bundleKey, locale, messageId, arguments);
	}
}
