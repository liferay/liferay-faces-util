/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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

import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public interface ResourceValidator {

	public boolean containsBannedPath(String resourceId);

	public boolean isBannedSequence(String resourceId);

	public boolean isFaceletDocument(FacesContext facesContext, String resourceId);

	public boolean isSelfReferencing(FacesContext facesContext, String resourceId);

	public boolean isValidLibraryName(String libraryName);

	public boolean isValidResourceName(String resourceName);
}
