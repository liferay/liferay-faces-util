/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
import javax.faces.render.Renderer;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This interface defines a contract for a contract for a {@link Renderer} that manifests a "client component", meaning
 * a component that has markup as well as some type of client-side API that is referenced by JavaScript.
 *
 * @author  Neil Griffin
 */
@ProviderType
public interface ClientComponentRenderer {

	public void decodeClientState(FacesContext facesContext, UIComponent uiComponent);

	public void encodeClientState(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException;

	public void encodeJavaScript(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException;
}
