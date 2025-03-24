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
package com.liferay.faces.util.render;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.osgi.annotation.versioning.ProviderType;


/**
 * @author  Neil Griffin
 */
@ProviderType
public interface DelegatingClientComponentRenderer extends DelegatingRenderer, ClientComponentRenderer {

	/**
	 * Calls the delegate renderer's {@link ClientComponentRenderer#encodeMarkupBegin(FacesContext, UIComponent)} method
	 * with the specified {@link ResponseWriter}.
	 */
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent,
		ResponseWriter delegationResponseWriter) throws IOException;

	/**
	 * Calls the delegate renderer's {@link ClientComponentRenderer#encodeMarkupEnd(FacesContext, UIComponent)} method
	 * with the specified {@link ResponseWriter}.
	 */
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent,
		ResponseWriter delegationResponseWriter) throws IOException;
}
