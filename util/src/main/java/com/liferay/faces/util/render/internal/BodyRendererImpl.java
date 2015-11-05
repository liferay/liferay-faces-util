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
package com.liferay.faces.util.render.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.util.client.ScriptEncoder;
import com.liferay.faces.util.client.ScriptEncoderFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.render.RendererWrapper;


/**
 * This {@link Renderer} is designed to ensure that Util's {@link FacesRequestContext} scripts are rendered before the
 * closing &lt;body&lt; tag. In order to ensure that this {@link BodyRenderer} is compatible with other JSF libraries,
 * such as Primefaces, this {@link BodyRenderer} wraps other body {@link Renderer}s via Util's {@link RenderKitImpl} and
 * calls through to the wrapped implementation.
 *
 * @author  Kyle Stiemann
 */
public class BodyRendererImpl extends RendererWrapper {

	// Private Members
	private Renderer wrappedBodyRenderer;

	public BodyRendererImpl(Renderer wrappedBodyRenderer) {
		this.wrappedBodyRenderer = wrappedBodyRenderer;
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if (!facesContext.getPartialViewContext().isAjaxRequest()) {

			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.startElement("script", null);
			responseWriter.writeAttribute("type", "text/javascript", null);

			ScriptEncoderFactory scriptEncoderFactory = (ScriptEncoderFactory) FactoryExtensionFinder.getFactory(
					ScriptEncoderFactory.class);
			ScriptEncoder scriptEncoder = scriptEncoderFactory.getScriptEncoder();
			scriptEncoder.encodeScripts(responseWriter);
			responseWriter.endElement("script");
		}

		super.encodeEnd(facesContext, uiComponent);
	}

	@Override
	public Renderer getWrapped() {
		return wrappedBodyRenderer;
	}
}
