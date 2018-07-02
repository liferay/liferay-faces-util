/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
