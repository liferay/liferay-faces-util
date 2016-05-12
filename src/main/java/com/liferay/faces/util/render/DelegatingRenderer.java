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
package com.liferay.faces.util.render;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * This interface defines a contract for a {@link Renderer} that has the ability to delegate to the corresponding
 * methods of a different renderer, as well as utilize an alternate {@link ResponseWriter} in order to control/filter
 * the encoding of elements and attributes.
 *
 * @author  Neil Griffin
 */
public interface DelegatingRenderer {

	/**
	 * Convenience method that calls the delegate renderer's {@link Renderer#encodeBegin(FacesContext, UIComponent)},
	 * {@link Renderer#encodeChildren(FacesContext, UIComponent)}, and {@link Renderer#encodeEnd(FacesContext,
	 * UIComponent)} methods.
	 */
	public void encodeAll(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	/**
	 * Convenience method that calls the delegate renderer's {@link Renderer#encodeBegin(FacesContext, UIComponent)},
	 * {@link Renderer#encodeChildren(FacesContext, UIComponent)}, and {@link Renderer#encodeEnd(FacesContext,
	 * UIComponent)} methods using the specified {@link DelegationResponseWriter}.
	 */
	public void encodeAll(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException;

	/**
	 * Calls the delegate renderer's {@link Renderer#encodeBegin(FacesContext, UIComponent)} method using the specified
	 * {@link DelegationResponseWriter}.
	 */
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException;

	/**
	 * Calls the delegate renderer's {@link Renderer#encodeChildren(FacesContext, UIComponent)} method using the
	 * specified {@link DelegationResponseWriter}.
	 */
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException;

	/**
	 * Calls the delegate renderer's {@link Renderer#encodeEnd(FacesContext, UIComponent)} method using the specified
	 * {@link DelegationResponseWriter}.
	 */
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException;

	/**
	 * Returns the component family associated with the delegate renderer.
	 */
	public String getDelegateComponentFamily();

	/**
	 * Returns the delegate renderer from the {@link RenderKit} associated with the specified {@link FacesContext}.
	 */
	public Renderer getDelegateRenderer(FacesContext facesContext);

	/**
	 * Returns the renderer-type associated with the delegate renderer.
	 */
	public String getDelegateRendererType();
}
