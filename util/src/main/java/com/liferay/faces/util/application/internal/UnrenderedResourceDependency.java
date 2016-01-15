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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


/**
 * @author  Kyle Stiemann
 */
public class UnrenderedResourceDependency extends UIComponentWrapper {

	// Private Members
	private UIComponent wrappedComponentResource;

	public UnrenderedResourceDependency(UIComponent wrappedComponentResource) {
		this.wrappedComponentResource = wrappedComponentResource;
	}

	@Override
	public void encodeAll(FacesContext context) throws IOException {

		if (isRendered()) {
			super.encodeAll(context);
		}
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {

		if (isRendered()) {
			super.encodeBegin(context);
		}
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException {

		if (isRendered()) {
			super.encodeChildren(context);
		}
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {

		if (isRendered()) {
			super.encodeEnd(context);
		}
	}

	@Override
	public boolean isRendered() {
		return false;
	}

	@Override
	public UIComponent getWrapped() {
		return wrappedComponentResource;
	}
}
