/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.application.view;

import java.beans.BeanInfo;
import java.io.IOException;
import java.util.List;

import javax.faces.FacesWrapper;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.AttachedObjectHandler;
import javax.faces.view.StateManagementStrategy;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewMetadata;


/**
 * @author  Neil Griffin
 */
public abstract class ViewDeclarationLanguageWrapper extends ViewDeclarationLanguage
	implements FacesWrapper<ViewDeclarationLanguage> {

	public abstract ViewDeclarationLanguage getWrapped();

	@Override
	public void buildView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException {
		getWrapped().buildView(facesContext, uiViewRoot);
	}

	@Override
	public UIViewRoot createView(FacesContext facesContext, String viewId) {
		return getWrapped().createView(facesContext, viewId);
	}

	@Override
	public BeanInfo getComponentMetadata(FacesContext facesContext, Resource componentResource) {
		return getWrapped().getComponentMetadata(facesContext, componentResource);
	}

	@Override
	public String getId() {
		return getWrapped().getId();
	}

	@Override
	public Resource getScriptComponentResource(FacesContext facesContext, Resource componentResource) {
		return getWrapped().getScriptComponentResource(facesContext, componentResource);
	}

	@Override
	public StateManagementStrategy getStateManagementStrategy(FacesContext facesContext, String viewId) {
		return getWrapped().getStateManagementStrategy(facesContext, viewId);
	}

	@Override
	public ViewMetadata getViewMetadata(FacesContext facesContext, String viewId) {
		return getWrapped().getViewMetadata(facesContext, viewId);
	}

	@Override
	public void renderView(FacesContext facesContext, UIViewRoot uiViewRoot) throws IOException {
		getWrapped().renderView(facesContext, uiViewRoot);
	}

	@Override
	public UIViewRoot restoreView(FacesContext facesContext, String viewId) {
		return getWrapped().restoreView(facesContext, viewId);
	}

	@Override
	public void retargetAttachedObjects(FacesContext facesContext, UIComponent uiComponent,
		List<AttachedObjectHandler> handlers) {
		getWrapped().retargetAttachedObjects(facesContext, uiComponent, handlers);
	}

	@Override
	public void retargetMethodExpressions(FacesContext facesContext, UIComponent uiComponent) {
		getWrapped().retargetMethodExpressions(facesContext, uiComponent);
	}

	@Override
	public boolean viewExists(FacesContext facesContext, String viewId) {
		return getWrapped().viewExists(facesContext, viewId);
	}

}
