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
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererWrapper;

import com.liferay.faces.util.application.ComponentResource;
import com.liferay.faces.util.application.ComponentResourceFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * This class extends {@link RendererWrapper} in order to decorate head renderers. It ensures that unrenderable {@link
 * ComponentResource}s are not rendered.
 *
 * @author  Kyle Stiemann
 */
public class HeadRendererUtilImpl extends RendererWrapper {

	// Private Members
	private Renderer wrappedRenderer;

	public HeadRendererUtilImpl(Renderer wrappedRenderer) {
		this.wrappedRenderer = wrappedRenderer;
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {

		UIViewRoot viewRoot = facesContext.getViewRoot();
		List<UIComponent> removedComponentResources = removeUnrenderableComponentResources(viewRoot, facesContext);
		super.encodeBegin(facesContext, component);
		addComponentResources(removedComponentResources, viewRoot, facesContext);
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException {

		UIViewRoot viewRoot = facesContext.getViewRoot();
		List<UIComponent> removedComponentResources = removeUnrenderableComponentResources(viewRoot, facesContext);
		super.encodeChildren(facesContext, component);
		addComponentResources(removedComponentResources, viewRoot, facesContext);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {

		UIViewRoot viewRoot = facesContext.getViewRoot();
		List<UIComponent> removedComponentResources = removeUnrenderableComponentResources(viewRoot, facesContext);
		super.encodeEnd(facesContext, component);
		addComponentResources(removedComponentResources, viewRoot, facesContext);
	}

	private void addComponentResources(List<UIComponent> componentResources, UIViewRoot viewRoot,
		FacesContext facesContext) {

		for (UIComponent removedComponentResource : componentResources) {
			viewRoot.addComponentResource(facesContext, removedComponentResource, "head");
		}
	}

	/**
	 * Note: By default, component resources are only rendered during encodeEnd(), however, since the JSF head renderer
	 * can be overriden, it is safer to remove unrenderable resources in all rendering methods.
	 */
	private List<UIComponent> removeUnrenderableComponentResources(UIViewRoot viewRoot, FacesContext facesContext) {

		List<UIComponent> componentResources = viewRoot.getComponentResources(facesContext, "head");
		List<UIComponent> removedComponentResources = new ArrayList<UIComponent>();
		ComponentResourceFactory componentResourceFactory = (ComponentResourceFactory) FactoryExtensionFinder
			.getFactory(ComponentResourceFactory.class);

		for (UIComponent uiComponentResource : componentResources) {

			ComponentResource componentResource = componentResourceFactory.getComponentResource(uiComponentResource);

			if (!componentResource.isRenderable()) {

				viewRoot.removeComponentResource(facesContext, uiComponentResource, "head");
				removedComponentResources.add(uiComponentResource);
			}
		}

		return removedComponentResources;
	}

	@Override
	public Renderer getWrapped() {
		return wrappedRenderer;
	}
}
