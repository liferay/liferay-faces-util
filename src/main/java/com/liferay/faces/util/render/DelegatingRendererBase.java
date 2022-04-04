/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
import javax.faces.convert.ConverterException;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This is an abstract class that implements the {@link DelegatingRenderer} interface in order to provide base
 * functionality for delegating the responsibility of rendering a {@link UIComponent} to a different renderer.
 *
 * @author  Neil Griffin
 */
@ProviderType
public abstract class DelegatingRendererBase extends Renderer implements DelegatingRenderer {

	/**
	 * @see  DelegatingRenderer#getDelegateComponentFamily()
	 */
	@Override
	public abstract String getDelegateComponentFamily();

	/**
	 * @see  DelegatingRenderer#getDelegateRendererType()
	 */
	@Override
	public abstract String getDelegateRendererType();

	@Override
	public String convertClientId(FacesContext facesContext, String clientId) {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);

		return delegateRenderer.convertClientId(facesContext, clientId);
	}

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.decode(facesContext, uiComponent);
	}

	/**
	 * @see  DelegatingRenderer#encodeAll(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeAll(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeBegin(facesContext, uiComponent);
		delegateRenderer.encodeChildren(facesContext, uiComponent);
		delegateRenderer.encodeEnd(facesContext, uiComponent);
	}

	/**
	 * @see  DelegatingRenderer#encodeAll(javax.faces.context.FacesContext, javax.faces.component.UIComponent,
	 *       javax.faces.context.ResponseWriter)
	 */
	@Override
	public void encodeAll(FacesContext facesContext, UIComponent uiComponent, ResponseWriter delegationResponseWriter)
		throws IOException {

		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		facesContext.setResponseWriter(delegationResponseWriter);

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeBegin(facesContext, uiComponent);
		delegateRenderer.encodeChildren(facesContext, uiComponent);
		delegateRenderer.encodeEnd(facesContext, uiComponent);
		facesContext.setResponseWriter(originalResponseWriter);
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeBegin(facesContext, uiComponent);
	}

	/**
	 * @see  DelegatingRenderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent,
	 *       javax.faces.context.ResponseWriter)
	 */
	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent, ResponseWriter delegationResponseWriter)
		throws IOException {

		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		facesContext.setResponseWriter(delegationResponseWriter);

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeBegin(facesContext, uiComponent);
		facesContext.setResponseWriter(originalResponseWriter);
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeChildren(facesContext, uiComponent);
	}

	/**
	 * @see  DelegatingRenderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent,
	 *       javax.faces.context.ResponseWriter)
	 */
	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent,
		ResponseWriter delegationResponseWriter) throws IOException {

		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		facesContext.setResponseWriter(delegationResponseWriter);

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeChildren(facesContext, uiComponent);
		facesContext.setResponseWriter(originalResponseWriter);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeEnd(facesContext, uiComponent);
	}

	/**
	 * @see  DelegatingRenderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent,
	 *       javax.faces.context.ResponseWriter)
	 */
	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent, ResponseWriter delegationResponseWriter)
		throws IOException {

		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		facesContext.setResponseWriter(delegationResponseWriter);

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeEnd(facesContext, uiComponent);
		facesContext.setResponseWriter(originalResponseWriter);
	}

	@Override
	public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue)
		throws ConverterException {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		Object convertedValue = delegateRenderer.getConvertedValue(facesContext, uiComponent, submittedValue);

		return convertedValue;
	}

	/**
	 * @see  DelegatingRenderer#getDelegateRenderer(javax.faces.context.FacesContext)
	 */
	@Override
	public Renderer getDelegateRenderer(FacesContext facesContext) {

		RenderKit renderKit = facesContext.getRenderKit();
		Renderer delegateRenderer = renderKit.getRenderer(getDelegateComponentFamily(), getDelegateRendererType());

		return delegateRenderer;
	}

	@Override
	public boolean getRendersChildren() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Renderer delegateRenderer = getDelegateRenderer(facesContext);

		return delegateRenderer.getRendersChildren();
	}
}
