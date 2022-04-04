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
package com.liferay.faces.util.application;

import java.util.Map;

import javax.faces.FacesWrapper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * Provides a simple implementation of {@link ResourceVerifier} that can be subclassed in order to decorate another
 * instance of the same type.
 *
 * @author  Kyle Stiemann
 */
@ConsumerType
public abstract class ResourceVerifierWrapper implements ResourceVerifier, FacesWrapper<ResourceVerifier> {

	/**
	 * @see  ResourceVerifier#isDependencySatisfied(FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public boolean isDependencySatisfied(FacesContext facesContext, UIComponent componentResource) {
		return getWrapped().isDependencySatisfied(facesContext, componentResource);
	}
}
