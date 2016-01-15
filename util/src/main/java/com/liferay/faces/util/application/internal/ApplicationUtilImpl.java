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
package com.liferay.faces.util.application.internal;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ApplicationWrapper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


/**
 * The purpose of this class is to ensure that instances of {@link UIViewRootUtilImpl} are created when
 * "javax.faces.ViewRoot" is specified as the component type.
 *
 * @author  Kyle Stiemann
 */
public class ApplicationUtilImpl extends ApplicationWrapper {

	// Private Members
	private Application wrappedApplication;

	public ApplicationUtilImpl(Application wrappedApplication) {
		this.wrappedApplication = wrappedApplication;
	}

	@Override
	public UIComponent createComponent(String componentType) throws FacesException {

		if (UIViewRoot.COMPONENT_TYPE.equals(componentType)) {
			return new UIViewRootUtilImpl();
		}
		else {
			return super.createComponent(componentType);
		}
	}

	@Override
	public UIComponent createComponent(FacesContext context, String componentType, String rendererType) {

		if (UIViewRoot.COMPONENT_TYPE.equals(componentType)) {
			return new UIViewRootUtilImpl();
		}
		else {
			return super.createComponent(context, componentType, rendererType);
		}
	}

	@Override
	public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType)
		throws FacesException {

		if (UIViewRoot.COMPONENT_TYPE.equals(componentType)) {
			return new UIViewRootUtilImpl();
		}
		else {
			return super.createComponent(componentExpression, context, componentType);
		}
	}

	@Override
	@Deprecated
	@SuppressWarnings("deprecation")
	public UIComponent createComponent(ValueBinding componentBinding, FacesContext context, String componentType)
		throws FacesException {

		if (UIViewRoot.COMPONENT_TYPE.equals(componentType)) {
			return new UIViewRootUtilImpl();
		}
		else {
			return super.createComponent(componentBinding, context, componentType);
		}
	}

	@Override
	public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType,
		String rendererType) {

		if (UIViewRoot.COMPONENT_TYPE.equals(componentType)) {
			return new UIViewRootUtilImpl();
		}
		else {
			return super.createComponent(componentExpression, context, componentType, rendererType);
		}
	}

	@Override
	public Application getWrapped() {
		return wrappedApplication;
	}
}
