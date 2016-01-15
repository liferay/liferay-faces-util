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
package com.liferay.faces.util.application.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.application.ResourceDependencyHandler;
import com.liferay.faces.util.application.ResourceDependencyHandlerFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Kyle Stiemann
 */
public class UIViewRootUtilImpl extends UIViewRootWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UIViewRootUtilImpl.class);

	// Private Members
	private UIViewRoot wrappedUIViewRoot;

	public UIViewRootUtilImpl(UIViewRoot wrappedUIViewRoot) {
		this.wrappedUIViewRoot = wrappedUIViewRoot;
	}

	@Override
	public void removeComponentResource(FacesContext context, UIComponent componentResource) {

		if (componentResource instanceof UnrenderedResourceDependency) {

			UnrenderedResourceDependency unrenderedResourceDependency = (UnrenderedResourceDependency)
				componentResource;
			componentResource = unrenderedResourceDependency.getWrapped();
		}

		super.removeComponentResource(context, componentResource);
	}

	@Override
	public void removeComponentResource(FacesContext context, UIComponent componentResource, String target) {

		if (componentResource instanceof UnrenderedResourceDependency) {

			UnrenderedResourceDependency unrenderedResourceDependency = (UnrenderedResourceDependency)
				componentResource;
			componentResource = unrenderedResourceDependency.getWrapped();
		}

		super.removeComponentResource(context, componentResource, target);
	}

	@Override
	public List<UIComponent> getComponentResources(FacesContext facesContext, String target) {

		List<UIComponent> componentResources = super.getComponentResources(facesContext, target);
		componentResources = new ArrayList<UIComponent>(componentResources);

		ListIterator<UIComponent> componentResourceListIterator = componentResources.listIterator();

		ResourceDependencyHandlerFactory resourceDependencyHandlerFactory = (ResourceDependencyHandlerFactory)
			FactoryExtensionFinder.getFactory(ResourceDependencyHandlerFactory.class);
		ResourceDependencyHandler resourceDependencyHandler =
			resourceDependencyHandlerFactory.getResourceDependencyHandler();

		while (componentResourceListIterator.hasNext()) {

			UIComponent componentResource = componentResourceListIterator.next();

			if (resourceDependencyHandler.isSatisfied(componentResource)) {

				if (!(componentResource instanceof UnrenderedResourceDependency)) {

					componentResourceListIterator.remove();
					componentResource = new UnrenderedResourceDependency(componentResource);
					componentResourceListIterator.add(componentResource);
				}

				if (logger.isDebugEnabled()) {

					Map<String, Object> componentResourceAttributes = componentResource.getAttributes();

					logger.debug(
						"Suppressed rendering of resource: name=[{0}] library=[{1}] rendererType=[{2}] value=[{3}] className=[{4}]",
						new Object[] {
							componentResourceAttributes.get("name"), componentResourceAttributes.get("library"),
							componentResource.getRendererType(), getComponentValue(componentResource),
							componentResource.getClass().getName(),
						});
				}
			}
		}

		return Collections.unmodifiableList(componentResources);
	}

	private String getComponentValue(UIComponent componentResource) {

		String componentResourceValue = null;

		if (componentResource instanceof ValueHolder) {
			ValueHolder valueHolder = (ValueHolder) componentResource;
			Object valueAsObject = valueHolder.getValue();

			if (valueAsObject != null) {
				componentResourceValue = valueAsObject.toString();
			}
		}

		return componentResourceValue;
	}

	@Override
	public UIViewRoot getWrapped() {
		return wrappedUIViewRoot;
	}
}
