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
package com.liferay.faces.util.render.internal;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.client.ScriptEncoder;
import com.liferay.faces.util.client.ScriptEncoderFactory;
import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.render.RendererWrapper;


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

		if (!facesContext.getPartialViewContext().isAjaxRequest()) {

			FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
			List<Script> scripts = facesRequestContext.getScripts();

			if (!scripts.isEmpty()) {

				ResponseWriter responseWriter = facesContext.getResponseWriter();
				responseWriter.startElement("script", null);
				responseWriter.writeAttribute("type", "text/javascript", null);

				ScriptEncoderFactory scriptEncoderFactory = (ScriptEncoderFactory) FactoryExtensionFinder.getFactory(
						ScriptEncoderFactory.class);
				ScriptEncoder scriptEncoder = scriptEncoderFactory.getScriptEncoder();
				scriptEncoder.encodeScripts(facesContext, scripts);
				responseWriter.endElement("script");
			}
		}

		super.encodeEnd(facesContext, uiComponent);
	}

	@Override
	public Renderer getWrapped() {
		return wrappedBodyRenderer;
	}
}
