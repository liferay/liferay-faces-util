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
package com.liferay.faces.util.context;

import com.liferay.faces.util.helper.Wrapper;

import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;


/**
 * @author  Neil Griffin
 */
public abstract class FacesContextHelperWrapper implements FacesContextHelper, Wrapper<FacesContextHelper> {

	// Java 1.6+ @Override
	public void addComponentErrorMessage(String clientId, String messageId) {
		getWrapped().addComponentErrorMessage(clientId, messageId);
	}

	// Java 1.6+ @Override
	public void addComponentErrorMessage(String clientId, String messageId, Object argument) {
		getWrapped().addComponentErrorMessage(clientId, messageId, argument);
	}

	// Java 1.6+ @Override
	public void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		getWrapped().addComponentErrorMessage(clientId, messageId, arguments);
	}

	// Java 1.6+ @Override
	public void addComponentInfoMessage(String clientId, String messageId) {
		getWrapped().addComponentInfoMessage(clientId, messageId);
	}

	// Java 1.6+ @Override
	public void addComponentInfoMessage(String clientId, String messageId, Object argument) {
		getWrapped().addComponentInfoMessage(clientId, messageId, argument);
	}

	// Java 1.6+ @Override
	public void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		getWrapped().addComponentInfoMessage(clientId, messageId, arguments);
	}

	// Java 1.6+ @Override
	public void addGlobalErrorMessage(String messageId) {
		getWrapped().addGlobalErrorMessage(messageId);
	}

	// Java 1.6+ @Override
	public void addGlobalErrorMessage(String messageId, Object argument) {
		getWrapped().addGlobalErrorMessage(messageId, argument);
	}

	// Java 1.6+ @Override
	public void addGlobalErrorMessage(String messageId, Object... arguments) {
		getWrapped().addGlobalErrorMessage(messageId, arguments);
	}

	// Java 1.6+ @Override
	public void addGlobalInfoMessage(String messageId) {
		getWrapped().addGlobalInfoMessage(messageId);
	}

	// Java 1.6+ @Override
	public void addGlobalInfoMessage(String messageId, Object argument) {
		getWrapped().addGlobalInfoMessage(messageId, argument);
	}

	// Java 1.6+ @Override
	public void addGlobalInfoMessage(String messageId, Object... arguments) {
		getWrapped().addGlobalInfoMessage(messageId, arguments);
	}

	// Java 1.6+ @Override
	public void addGlobalSuccessInfoMessage() {
		getWrapped().addGlobalSuccessInfoMessage();
	}

	// Java 1.6+ @Override
	public void addGlobalUnexpectedErrorMessage() {
		getWrapped().addGlobalUnexpectedErrorMessage();
	}

	// Java 1.6+ @Override
	public void addMessage(String clientId, FacesMessage.Severity severity, String messageId) {
		getWrapped().addMessage(clientId, severity, messageId);
	}

	// Java 1.6+ @Override
	public void addMessage(String clientId, FacesMessage.Severity severity, String messageId, Object argument) {
		getWrapped().addMessage(clientId, severity, messageId, argument);
	}

	// Java 1.6+ @Override
	public void addMessage(String clientId, FacesMessage.Severity severity, String messageId, Object... arguments) {
		getWrapped().addMessage(clientId, severity, messageId, arguments);
	}

	// Java 1.6+ @Override
	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		return getWrapped().matchComponentInHierarchy(parent, partialClientId);
	}

	// Java 1.6+ @Override
	public UIComponent matchComponentInViewRoot(String partialClientId) {
		return getWrapped().matchComponentInViewRoot(partialClientId);
	}

	// Java 1.6+ @Override
	public void navigate(String fromAction, String outcome) {
		getWrapped().navigate(fromAction, outcome);
	}

	// Java 1.6+ @Override
	public void navigateTo(String outcome) {
		getWrapped().navigateTo(outcome);
	}

	// Java 1.6+ @Override
	public void recreateComponentTree() {
		getWrapped().recreateComponentTree();
	}

	// Java 1.6+ @Override
	public void registerPhaseListener(PhaseListener phaseListener) {
		getWrapped().registerPhaseListener(phaseListener);
	}

	// Java 1.6+ @Override
	public void removeChildrenFromComponentTree(String clientId) {
		getWrapped().removeChildrenFromComponentTree(clientId);
	}

	// Java 1.6+ @Override
	public void removeMessages(String clientId) {
		getWrapped().removeMessages(clientId);
	}

	// Java 1.6+ @Override
	public void removeMessagesForImmediateComponents() {
		getWrapped().removeMessagesForImmediateComponents();
	}

	// Java 1.6+ @Override
	public void removeMessagesForImmediateComponents(UIComponent uiComponent) {
		getWrapped().removeMessagesForImmediateComponents(uiComponent);
	}

	// Java 1.6+ @Override
	public void removeParentFormFromComponentTree(UIComponent uiComponent) {
		getWrapped().removeParentFormFromComponentTree(uiComponent);
	}

	// Java 1.6+ @Override
	public void resetView() {
		getWrapped().resetView();
	}

	// Java 1.6+ @Override
	public void resetView(boolean renderResponse) {
		getWrapped().resetView(renderResponse);
	}

	// Java 1.6+ @Override
	public Object resolveExpression(String elExpression) {
		return getWrapped().resolveExpression(elExpression);
	}

	// Java 1.6+ @Override
	public FacesContext getFacesContext() {
		return getWrapped().getFacesContext();
	}

	// Java 1.6+ @Override
	public Locale getLocale() {
		return getWrapped().getLocale();
	}

	// Java 1.6+ @Override
	public String getMessage(String messageId) {
		return getWrapped().getMessage(messageId);
	}

	// Java 1.6+ @Override
	public String getMessage(String messageId, Object... arguments) {
		return getWrapped().getMessage(messageId, arguments);
	}

	// Java 1.6+ @Override
	public String getMessage(Locale locale, String messageId) {
		return getWrapped().getMessage(locale, messageId);
	}

	// Java 1.6+ @Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return getWrapped().getMessage(locale, messageId, arguments);
	}

	// Java 1.6+ @Override
	public String getNamespace() {
		return getWrapped().getNamespace();
	}

	// Java 1.6+ @Override
	public UIForm getParentForm(UIComponent uiComponent) {
		return getWrapped().getParentForm(uiComponent);
	}

	// Java 1.6+ @Override
	public Object getRequestAttribute(String name) {
		return getWrapped().getRequestAttribute(name);
	}

	// Java 1.6+ @Override
	public void setRequestAttribute(String name, Object value) {
		getWrapped().setRequestAttribute(name, value);
	}

	// Java 1.6+ @Override
	public String getRequestContextPath() {
		return getWrapped().getRequestContextPath();
	}

	// Java 1.6+ @Override
	public String getRequestParameter(String name) {
		return getWrapped().getRequestParameter(name);
	}

	// Java 1.6+ @Override
	public boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		return getWrapped().getRequestParameterAsBool(name, defaultValue);
	}

	// Java 1.6+ @Override
	public int getRequestParameterAsInt(String name, int defaultValue) {
		return getWrapped().getRequestParameterAsInt(name, defaultValue);
	}

	// Java 1.6+ @Override
	public long getRequestParameterAsLong(String name, long defaultValue) {
		return getWrapped().getRequestParameterAsLong(name, defaultValue);
	}

	// Java 1.6+ @Override
	public String getRequestParameterFromMap(String name) {
		return getWrapped().getRequestParameterFromMap(name);
	}

	// Java 1.6+ @Override
	public Map<String, String> getRequestParameterMap() {
		return getWrapped().getRequestParameterMap();
	}

	// Java 1.6+ @Override
	public String getRequestQueryString() {
		return getWrapped().getRequestQueryString();
	}

	// Java 1.6+ @Override
	public String getRequestQueryStringParameter(String name) {
		return getWrapped().getRequestQueryStringParameter(name);
	}

	// Java 1.6+ @Override
	public Object getSession(boolean create) {
		return getWrapped().getSession(create);
	}

	// Java 1.6+ @Override
	public Object getSessionAttribute(String name) {
		return getWrapped().getSessionAttribute(name);
	}

	// Java 1.6+ @Override
	public void setSessionAttribute(String name, Object value) {
		getWrapped().setSessionAttribute(name, value);
	}

	// Java 1.6+ @Override
	public abstract FacesContextHelper getWrapped();
}
