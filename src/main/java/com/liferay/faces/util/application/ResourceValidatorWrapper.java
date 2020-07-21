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
package com.liferay.faces.util.application;

import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * @author  Neil Griffin
 */
@ConsumerType
public abstract class ResourceValidatorWrapper implements ResourceValidator, FacesWrapper<ResourceValidator> {

	@Override
	public abstract ResourceValidator getWrapped();

	@Override
	public boolean containsBannedPath(String resourceId) {
		return getWrapped().containsBannedPath(resourceId);
	}

	@Override
	public boolean isBannedSequence(String resourceId) {
		return getWrapped().isBannedSequence(resourceId);
	}

	@Override
	public boolean isFaceletDocument(FacesContext facesContext, String resourceId) {
		return getWrapped().isFaceletDocument(facesContext, resourceId);
	}

	@Override
	public boolean isSelfReferencing(FacesContext facesContext, String resourceId) {
		return getWrapped().isSelfReferencing(facesContext, resourceId);
	}

	@Override
	public boolean isValidLibraryName(String libraryName) {
		return getWrapped().isValidLibraryName(libraryName);
	}

	@Override
	public boolean isValidResourceName(String resourceName) {
		return getWrapped().isValidResourceName(resourceName);
	}
}
