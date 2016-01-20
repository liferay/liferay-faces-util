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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.application.ResourceVerifier;
import com.liferay.faces.util.application.ResourceVerifierFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class ensures that the resource dependencies that have already been satisfied are not present in the list of
 * component resources returned by {@link #getComponentResources(FacesContext, String)}. This in turn ensures that
 * resources that have already been satisfied are not rendered when the head renderer attempts to render component
 * resources in the &lt;head&gt;...&lt;/head&gt; section of the page.
 *
 * @author  Kyle Stiemann
 */
public class UIViewRootUtilImpl extends UIViewRoot {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UIViewRootUtilImpl.class);

	@Override
	public List<UIComponent> getComponentResources(FacesContext facesContext, String target) {

		// Get the list of all component resources.
		List<UIComponent> allComponentResources = super.getComponentResources(facesContext, target);

		// Determine which of the component resources are unsatisfied.
		List<UIComponent> unsatisfiedComponentResources = new ArrayList<UIComponent>(allComponentResources.size());
		ResourceVerifier resourceVerifier = ResourceVerifierFactory.getResourceDependencyHandlerInstance();

		for (UIComponent componentResource : allComponentResources) {

			if (resourceVerifier.isDependencySatisfied(facesContext, componentResource)) {

				if (logger.isDebugEnabled()) {

					Map<String, Object> componentResourceAttributes = componentResource.getAttributes();

					logger.debug(
						"Resource dependency already satisfied: name=[{0}] library=[{1}] rendererType=[{2}] value=[{3}] className=[{4}]",
						componentResourceAttributes.get("name"), componentResourceAttributes.get("library"),
						componentResource.getRendererType(), getComponentValue(componentResource),
						componentResource.getClass().getName());
				}
			}
			else {
				unsatisfiedComponentResources.add(componentResource);
			}
		}

		// Return an immutable list of unsatisfied resources.
		return Collections.unmodifiableList(unsatisfiedComponentResources);
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
}
