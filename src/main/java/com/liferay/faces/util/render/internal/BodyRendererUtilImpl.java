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
package com.liferay.faces.util.render.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.faces.render.RendererWrapper;

import com.liferay.faces.util.context.FacesRequestContext;


/**
 * This class extends {@link RendererWrapper} in order to decorate body renderers provided by non-Liferay/3rd-Party
 * component suites like ICEfaces, PrimeFaces, and RichFaces. It ensures that scripts contained in {@link
 * FacesRequestContext#getScripts()} are encoded before the closing <code>&lt;/body&gt;</code> element.
 *
 * @author  Kyle Stiemann
 */
public class BodyRendererUtilImpl extends RendererWrapper {

	// Private Members
	private Renderer wrappedBodyRenderer;

	public BodyRendererUtilImpl(Renderer wrappedBodyRenderer) {
		this.wrappedBodyRenderer = wrappedBodyRenderer;
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		boolean ajaxRequest = facesContext.getPartialViewContext().isAjaxRequest();
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (!ajaxRequest) {

			BodyScriptEncodingResponseWriter bodyScriptEncodingResponseWriter = new BodyScriptEncodingResponseWriter(
					responseWriter, facesContext);
			facesContext.setResponseWriter(bodyScriptEncodingResponseWriter);
		}

		super.encodeEnd(facesContext, uiComponent);

		if (!ajaxRequest) {
			facesContext.setResponseWriter(responseWriter);
		}
	}

	@Override
	public Renderer getWrapped() {
		return wrappedBodyRenderer;
	}
}
