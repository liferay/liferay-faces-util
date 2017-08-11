/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
 * @author  Neil Griffin
 */
public class I18nUtil {

	public static FacesMessage getFacesMessage(I18n i18n, FacesContext facesContext, Locale locale,
		FacesMessage.Severity severity, String messageId) {
		return getFacesMessage(i18n, facesContext, locale, severity, messageId, new Object[] {});
	}

	public static FacesMessage getFacesMessage(I18n i18n, FacesContext facesContext, Locale locale,
		FacesMessage.Severity severity, String messageId, Object... arguments) {

		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(severity);
		facesMessage.setDetail(null);

		String summary;

		if (arguments == null) {
			summary = i18n.getMessage(facesContext, locale, messageId);
		}
		else {
			summary = i18n.getMessage(facesContext, locale, messageId, arguments);
		}

		if (summary == null) {
			facesMessage.setSummary(messageId);
		}
		else {
			facesMessage.setSummary(summary);

			String detailMessageId = messageId + "_detail";
			String detail;

			if (arguments == null) {
				detail = i18n.getMessage(facesContext, locale, detailMessageId);
			}
			else {
				detail = i18n.getMessage(facesContext, locale, detailMessageId, arguments);
			}

			if ((detail == null) || detailMessageId.equals(detail)) {
				facesMessage.setDetail(summary);
			}
			else {
				facesMessage.setDetail(detail);
			}
		}

		return facesMessage;
	}
}
