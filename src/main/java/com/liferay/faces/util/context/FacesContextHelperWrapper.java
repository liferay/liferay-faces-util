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
package com.liferay.faces.util.context;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;

import org.osgi.annotation.versioning.ConsumerType;

import com.liferay.faces.util.client.Script;


/**
 * @author  Neil Griffin
 */
@ConsumerType
public abstract class FacesContextHelperWrapper implements FacesContextHelper, FacesWrapper<FacesContextHelper> {

	@Override
	public abstract FacesContextHelper getWrapped();

	@Override
	public void addComponentErrorMessage(String clientId, String messageId) {
		getWrapped().addComponentErrorMessage(clientId, messageId);
	}

	@Override
	public void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId) {
		getWrapped().addComponentErrorMessage(facesContext, clientId, messageId);
	}

	@Override
	public void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		getWrapped().addComponentErrorMessage(clientId, messageId, arguments);
	}

	@Override
	public void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments) {
		getWrapped().addComponentErrorMessage(facesContext, clientId, messageId, arguments);
	}

	@Override
	public void addComponentInfoMessage(String clientId, String messageId) {
		getWrapped().addComponentInfoMessage(clientId, messageId);
	}

	@Override
	public void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId) {
		getWrapped().addComponentInfoMessage(facesContext, clientId, messageId);
	}

	@Override
	public void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		getWrapped().addComponentInfoMessage(clientId, messageId, arguments);
	}

	@Override
	public void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments) {
		getWrapped().addComponentInfoMessage(facesContext, clientId, messageId, arguments);
	}

	@Override
	public void addGlobalErrorMessage(String messageId) {
		getWrapped().addGlobalErrorMessage(messageId);
	}

	@Override
	public void addGlobalErrorMessage(FacesContext facesContext, String messageId) {
		getWrapped().addGlobalErrorMessage(facesContext, messageId);
	}

	@Override
	public void addGlobalErrorMessage(String messageId, Object... arguments) {
		getWrapped().addGlobalErrorMessage(messageId, arguments);
	}

	@Override
	public void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object... arguments) {
		getWrapped().addGlobalErrorMessage(facesContext, messageId, arguments);
	}

	@Override
	public void addGlobalInfoMessage(String messageId) {
		getWrapped().addGlobalInfoMessage(messageId);
	}

	@Override
	public void addGlobalInfoMessage(FacesContext facesContext, String messageId) {
		getWrapped().addGlobalInfoMessage(facesContext, messageId);
	}

	@Override
	public void addGlobalInfoMessage(String messageId, Object... arguments) {
		getWrapped().addGlobalInfoMessage(messageId, arguments);
	}

	@Override
	public void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object... arguments) {
		getWrapped().addGlobalInfoMessage(facesContext, messageId, arguments);
	}

	@Override
	public void addGlobalSuccessInfoMessage() {
		getWrapped().addGlobalSuccessInfoMessage();
	}

	@Override
	public void addGlobalSuccessInfoMessage(FacesContext facesContext) {
		getWrapped().addGlobalSuccessInfoMessage(facesContext);
	}

	@Override
	public void addGlobalUnexpectedErrorMessage() {
		getWrapped().addGlobalUnexpectedErrorMessage();
	}

	@Override
	public void addGlobalUnexpectedErrorMessage(FacesContext facesContext) {
		getWrapped().addGlobalUnexpectedErrorMessage(facesContext);
	}

	@Override
	public void addMessage(String clientId, FacesMessage.Severity severity, String messageId) {
		getWrapped().addMessage(clientId, severity, messageId);
	}

	@Override
	public void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId) {
		getWrapped().addMessage(facesContext, clientId, severity, messageId);
	}

	@Override
	public void addMessage(String clientId, FacesMessage.Severity severity, String messageId, Object... arguments) {
		getWrapped().addMessage(clientId, severity, messageId, arguments);
	}

	@Override
	public void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity, String messageId,
		Object... arguments) {
		getWrapped().addMessage(facesContext, clientId, severity, messageId, arguments);
	}

	@Override
	public void addScript(Script script) {
		getWrapped().addScript(script);
	}

	@Override
	public void addScript(String scriptString) {
		getWrapped().addScript(scriptString);
	}

	@Override
	public void addScript(FacesContext facesContext, Script script) {
		getWrapped().addScript(facesContext, script);
	}

	@Override
	public void addScript(FacesContext facesContext, String scriptString) {
		getWrapped().addScript(facesContext, scriptString);
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
	public Locale getLocale(FacesContext facesContext) {
		return getWrapped().getLocale(facesContext);
	}

	@Override
	public String getMessage(String messageId) {
		return getWrapped().getMessage(messageId);
	}

	@Override
	public String getMessage(FacesContext facesContext, String messageId) {
		return getWrapped().getMessage(facesContext, messageId);
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
	public String getMessage(FacesContext facesContext, String messageId, Object... arguments) {
		return getWrapped().getMessage(facesContext, messageId, arguments);
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId) {
		return getWrapped().getMessage(facesContext, locale, messageId);
	}

	@Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return getWrapped().getMessage(locale, messageId, arguments);
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {
		return getWrapped().getMessage(facesContext, locale, messageId, arguments);
	}

	@Override
	public String getNamespace() {
		return getWrapped().getNamespace();
	}

	@Override
	public String getNamespace(FacesContext facesContext) {
		return getWrapped().getNamespace(facesContext);
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
	public Object getRequestAttribute(FacesContext facesContext, String name) {
		return getWrapped().getRequestAttribute(facesContext, name);
	}

	@Override
	public String getRequestContextPath() {
		return getWrapped().getRequestContextPath();
	}

	@Override
	public String getRequestContextPath(FacesContext facesContext) {
		return getWrapped().getRequestContextPath(facesContext);
	}

	@Override
	public String getRequestParameter(String name) {
		return getWrapped().getRequestParameter(name);
	}

	@Override
	public String getRequestParameter(FacesContext facesContext, String name) {
		return getWrapped().getRequestParameter(facesContext, name);
	}

	@Override
	public boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		return getWrapped().getRequestParameterAsBool(name, defaultValue);
	}

	@Override
	public boolean getRequestParameterAsBool(FacesContext facesContext, String name, boolean defaultValue) {
		return getWrapped().getRequestParameterAsBool(facesContext, name, defaultValue);
	}

	@Override
	public int getRequestParameterAsInt(String name, int defaultValue) {
		return getWrapped().getRequestParameterAsInt(name, defaultValue);
	}

	@Override
	public int getRequestParameterAsInt(FacesContext facesContext, String name, int defaultValue) {
		return getWrapped().getRequestParameterAsInt(facesContext, name, defaultValue);
	}

	@Override
	public long getRequestParameterAsLong(String name, long defaultValue) {
		return getWrapped().getRequestParameterAsLong(name, defaultValue);
	}

	@Override
	public long getRequestParameterAsLong(FacesContext facesContext, String name, long defaultValue) {
		return getWrapped().getRequestParameterAsLong(facesContext, name, defaultValue);
	}

	@Override
	public String getRequestParameterFromMap(String name) {
		return getWrapped().getRequestParameterFromMap(name);
	}

	@Override
	public String getRequestParameterFromMap(FacesContext facesContext, String name) {
		return getWrapped().getRequestParameterFromMap(facesContext, name);
	}

	@Override
	public Map<String, String> getRequestParameterMap() {
		return getWrapped().getRequestParameterMap();
	}

	@Override
	public Map<String, String> getRequestParameterMap(FacesContext facesContext) {
		return getWrapped().getRequestParameterMap(facesContext);
	}

	@Override
	public String getRequestQueryString() {
		return getWrapped().getRequestQueryString();
	}

	@Override
	public String getRequestQueryString(FacesContext facesContext) {
		return getWrapped().getRequestQueryString(facesContext);
	}

	@Override
	public String getRequestQueryStringParameter(String name) {
		return getWrapped().getRequestQueryStringParameter(name);
	}

	@Override
	public String getRequestQueryStringParameter(FacesContext facesContext, String name) {
		return getWrapped().getRequestQueryStringParameter(facesContext, name);
	}

	@Override
	public List<Script> getScripts() {
		return getWrapped().getScripts();
	}

	@Override
	public List<Script> getScripts(FacesContext facesContext) {
		return getWrapped().getScripts();
	}

	@Override
	public Object getSession(boolean create) {
		return getWrapped().getSession(create);
	}

	@Override
	public Object getSession(FacesContext facesContext, boolean create) {
		return getWrapped().getSession(facesContext, create);
	}

	@Override
	public Object getSessionAttribute(String name) {
		return getWrapped().getSessionAttribute(name);
	}

	@Override
	public Object getSessionAttribute(FacesContext facesContext, String name) {
		return getWrapped().getSessionAttribute(facesContext, name);
	}

	@Override
	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		return getWrapped().matchComponentInHierarchy(parent, partialClientId);
	}

	@Override
	public UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent,
		String partialClientId) {
		return getWrapped().matchComponentInHierarchy(facesContext, parent, partialClientId);
	}

	@Override
	public UIComponent matchComponentInViewRoot(String partialClientId) {
		return getWrapped().matchComponentInViewRoot(partialClientId);
	}

	@Override
	public UIComponent matchComponentInViewRoot(FacesContext facesContext, String partialClientId) {
		return getWrapped().matchComponentInViewRoot(facesContext, partialClientId);
	}

	@Override
	public void navigate(String fromAction, String outcome) {
		getWrapped().navigate(fromAction, outcome);
	}

	@Override
	public void navigate(FacesContext facesContext, String fromAction, String outcome) {
		getWrapped().navigate(facesContext, fromAction, outcome);
	}

	@Override
	public void navigateTo(String outcome) {
		getWrapped().navigateTo(outcome);
	}

	@Override
	public void navigateTo(FacesContext facesContext, String outcome) {
		getWrapped().navigateTo(facesContext, outcome);
	}

	@Override
	public void recreateComponentTree() {
		getWrapped().recreateComponentTree();
	}

	@Override
	public void recreateComponentTree(FacesContext facesContext) {
		getWrapped().recreateComponentTree(facesContext);
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
	public void removeChildrenFromComponentTree(FacesContext facesContext, String clientId) {
		getWrapped().removeChildrenFromComponentTree(facesContext, clientId);
	}

	@Override
	public void removeMessages(String clientId) {
		getWrapped().removeMessages(clientId);
	}

	@Override
	public void removeMessages(FacesContext facesContext, String clientId) {
		getWrapped().removeMessages(facesContext, clientId);
	}

	@Override
	public void removeMessagesForImmediateComponents() {
		getWrapped().removeMessagesForImmediateComponents();
	}

	@Override
	public void removeMessagesForImmediateComponents(FacesContext facesContext) {
		getWrapped().removeMessagesForImmediateComponents(facesContext);
	}

	@Override
	public void removeMessagesForImmediateComponents(UIComponent uiComponent) {
		getWrapped().removeMessagesForImmediateComponents(uiComponent);
	}

	@Override
	public void removeMessagesForImmediateComponents(FacesContext facesContext, UIComponent uiComponent) {
		getWrapped().removeMessagesForImmediateComponents(facesContext, uiComponent);
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
	public void resetView(FacesContext facesContext) {
		getWrapped().resetView(facesContext);
	}

	@Override
	public void resetView(boolean renderResponse) {
		getWrapped().resetView(renderResponse);
	}

	@Override
	public void resetView(FacesContext facesContext, boolean renderResponse) {
		getWrapped().resetView(facesContext, renderResponse);
	}

	@Override
	public Object resolveExpression(String elExpression) {
		return getWrapped().resolveExpression(elExpression);
	}

	@Override
	public Object resolveExpression(FacesContext facesContext, String elExpression) {
		return getWrapped().resolveExpression(facesContext, elExpression);
	}

	@Override
	public void setRequestAttribute(String name, Object value) {
		getWrapped().setRequestAttribute(name, value);
	}

	@Override
	public void setRequestAttribute(FacesContext facesContext, String name, Object value) {
		getWrapped().setRequestAttribute(facesContext, name, value);
	}

	@Override
	public void setSessionAttribute(String name, Object value) {
		getWrapped().setSessionAttribute(name, value);
	}

	@Override
	public void setSessionAttribute(FacesContext facesContext, String name, Object value) {
		getWrapped().setSessionAttribute(facesContext, name, value);
	}
}
