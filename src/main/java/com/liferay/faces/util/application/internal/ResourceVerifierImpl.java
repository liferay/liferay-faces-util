/**
 * Copyright (c) 2000-2025 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.application.ResourceVerifier;

/**
 * @author Kyle Stiemann
 */
public class ResourceVerifierImpl implements ResourceVerifier, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4133463355765570238L;

	@Override
	public boolean isDependencySatisfied(FacesContext facesContext, UIComponent componentResource) {

		// Assume that the specified resource dependency has not been satisfied and that it should therefore be
		// rendered.
		return false;
	}
}
