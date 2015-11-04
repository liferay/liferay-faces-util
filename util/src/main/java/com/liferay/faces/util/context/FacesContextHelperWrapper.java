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
package com.liferay.faces.util.context;

import java.util.Locale;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;


/**
 * @author  Neil Griffin
 */
public abstract class FacesContextHelperWrapper implements FacesContextHelper, FacesWrapper<FacesContextHelper> {

	@Override
	public void addComponentErrorMessage(String clientId, String messageId) {
		getWrapped().addComponentErrorMessage(clientId, messageId);
	}

	@Override
	public void addComponentErrorMessage(String clientId, String messageId, Object argument) {
		getWrapped().addComponentErrorMessage(clientId, messageId, argument);
	}

	@Override
	public void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		getWrapped().addComponentErrorMessage(clientId, messageId, arguments);
	}

	@Override
	public void addComponentInfoMessage(String clientId, String messageId) {
		getWrapped().addComponentInfoMessage(clientId, messageId);
	}

	@Override
	public void addComponentInfoMessage(String clientId, String messageId, Object argument) {
		getWrapped().addComponentInfoMessage(clientId, messageId, argument);
	}

	@Override
	public void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		getWrapped().addComponentInfoMessage(clientId, messageId, arguments);
	}

	@Override
	public void addGlobalErrorMessage(String messageId) {
		getWrapped().addGlobalErrorMessage(messageId);
	}

	@Override
	public void addGlobalErrorMessage(String messageId, Object argument) {
		getWrapped().addGlobalErrorMessage(messageId, argument);
	}

	@Override
	public void addGlobalErrorMessage(String messageId, Object... arguments) {
		getWrapped().addGlobalErrorMessage(messageId, arguments);
	}

	@Override
	public void addGlobalInfoMessage(String messageId) {
		getWrapped().addGlobalInfoMessage(messageId);
	}

	@Override
	public void addGlobalInfoMessage(String messageId, Object argument) {
		getWrapped().addGlobalInfoMessage(messageId, argument);
	}

	@Override
	public void addGlobalInfoMessage(String messageId, Object... arguments) {
		getWrapped().addGlobalInfoMessage(messageId, arguments);
	}

	@Override
	public void addGlobalSuccessInfoMessage() {
		getWrapped().addGlobalSuccessInfoMessage();
	}

	@Override
	public void addGlobalUnexpectedErrorMessage() {
		getWrapped().addGlobalUnexpectedErrorMessage();
	}

	@Override
	public void addMessage(String clientId, FacesMessage.Severity severity, String messageId) {
		getWrapped().addMessage(clientId, severity, messageId);
	}

	@Override
	public void addMessage(String clientId, FacesMessage.Severity severity, String messageId, Object argument) {
		getWrapped().addMessage(clientId, severity, messageId, argument);
	}

	@Override
	public void addMessage(String clientId, FacesMessage.Severity severity, String messageId, Object... arguments) {
		getWrapped().addMessage(clientId, severity, messageId, arguments);
	}

	@Override
	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		return getWrapped().matchComponentInHierarchy(parent, partialClientId);
	}

	@Override
	public UIComponent matchComponentInViewRoot(String partialClientId) {
		return getWrapped().matchComponentInViewRoot(partialClientId);
	}

	@Override
	public void navigate(String fromAction, String outcome) {
		getWrapped().navigate(fromAction, outcome);
	}

	@Override
	public void navigateTo(String outcome) {
		getWrapped().navigateTo(outcome);
	}

	@Override
	public void recreateComponentTree() {
		getWrapped().recreateComponentTree();
	}

	@Override
	public void registerPhaseListener(PhaseListener phaseListener) {
		getWrapped().registerPhaseListener(phaseListener);
	}

	@Override
	public void removeChildrenFromComponentTree(String clientId) {
		getWrapped().removeChildrenFromComponentTree(clientId);
	}

	@Override
	public void removeMessages(String clientId) {
		getWrapped().removeMessages(clientId);
	}

	@Override
	public void removeMessagesForImmediateComponents() {
		getWrapped().removeMessagesForImmediateComponents();
	}

	@Override
	public void removeMessagesForImmediateComponents(UIComponent uiComponent) {
		getWrapped().removeMessagesForImmediateComponents(uiComponent);
	}

	@Override
	public void removeParentFormFromComponentTree(UIComponent uiComponent) {
		getWrapped().removeParentFormFromComponentTree(uiComponent);
	}

	@Override
	public void resetView() {
		getWrapped().resetView();
	}

	@Override
	public void resetView(boolean renderResponse) {
		getWrapped().resetView(renderResponse);
	}

	@Override
	public Object resolveExpression(String elExpression) {
		return getWrapped().resolveExpression(elExpression);
	}

	@Override
	public FacesContext getFacesContext() {
		return getWrapped().getFacesContext();
	}

	@Override
	public Locale getLocale() {
		return getWrapped().getLocale();
	}

	@Override
	public String getMessage(String messageId) {
		return getWrapped().getMessage(messageId);
	}

	@Override
	public String getMessage(String messageId, Object... arguments) {
		return getWrapped().getMessage(messageId, arguments);
	}

	@Override
	public String getMessage(Locale locale, String messageId) {
		return getWrapped().getMessage(locale, messageId);
	}

	@Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return getWrapped().getMessage(locale, messageId, arguments);
	}

	@Override
	public String getNamespace() {
		return getWrapped().getNamespace();
	}

	@Override
	public UIForm getParentForm(UIComponent uiComponent) {
		return getWrapped().getParentForm(uiComponent);
	}

	@Override
	public Object getRequestAttribute(String name) {
		return getWrapped().getRequestAttribute(name);
	}

	@Override
	public void setRequestAttribute(String name, Object value) {
		getWrapped().setRequestAttribute(name, value);
	}

	@Override
	public String getRequestContextPath() {
		return getWrapped().getRequestContextPath();
	}

	@Override
	public String getRequestParameter(String name) {
		return getWrapped().getRequestParameter(name);
	}

	@Override
	public boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		return getWrapped().getRequestParameterAsBool(name, defaultValue);
	}

	@Override
	public int getRequestParameterAsInt(String name, int defaultValue) {
		return getWrapped().getRequestParameterAsInt(name, defaultValue);
	}

	@Override
	public long getRequestParameterAsLong(String name, long defaultValue) {
		return getWrapped().getRequestParameterAsLong(name, defaultValue);
	}

	@Override
	public String getRequestParameterFromMap(String name) {
		return getWrapped().getRequestParameterFromMap(name);
	}

	@Override
	public Map<String, String> getRequestParameterMap() {
		return getWrapped().getRequestParameterMap();
	}

	@Override
	public String getRequestQueryString() {
		return getWrapped().getRequestQueryString();
	}

	@Override
	public String getRequestQueryStringParameter(String name) {
		return getWrapped().getRequestQueryStringParameter(name);
	}

	@Override
	public Object getSession(boolean create) {
		return getWrapped().getSession(create);
	}

	@Override
	public Object getSessionAttribute(String name) {
		return getWrapped().getSessionAttribute(name);
	}

	@Override
	public void setSessionAttribute(String name, Object value) {
		getWrapped().setSessionAttribute(name, value);
	}

	@Override
	public abstract FacesContextHelper getWrapped();
}
