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
package com.liferay.faces.util.context.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.context.MessageContext;
import com.liferay.faces.util.context.MessageContextFactory;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.helper.IntegerHelper;
import com.liferay.faces.util.helper.LongHelper;


/**
 * @author  Neil Griffin
 */
public class FacesContextHelperImpl implements FacesContextHelper {

	// Private Constants
	private static final String UNEXPECTED_ERROR_MSG_ID = "an-unexpected-error-occurred";
	private static final String SUCCESS_INFO_MSG_ID = "your-request-processed-successfully";

	public void addComponentErrorMessage(String clientId, String messageId) {
		addMessage(clientId, FacesMessage.SEVERITY_ERROR, messageId);
	}

	public void addComponentErrorMessage(String clientId, String messageId, Object argument) {
		addMessage(clientId, FacesMessage.SEVERITY_ERROR, messageId, argument);
	}

	public void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		addMessage(clientId, FacesMessage.SEVERITY_ERROR, messageId, arguments);
	}

	public void addComponentInfoMessage(String clientId, String messageId) {
		addMessage(clientId, FacesMessage.SEVERITY_INFO, messageId);
	}

	public void addComponentInfoMessage(String clientId, String messageId, Object argument) {
		addMessage(clientId, FacesMessage.SEVERITY_INFO, messageId, argument);
	}

	public void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		addMessage(clientId, FacesMessage.SEVERITY_INFO, messageId, arguments);
	}

	public void addGlobalErrorMessage(String messageId) {
		addComponentErrorMessage(null, messageId);
	}

	public void addGlobalErrorMessage(String messageId, Object argument) {
		addComponentErrorMessage(null, messageId, argument);
	}

	public void addGlobalErrorMessage(String messageId, Object... arguments) {
		addComponentErrorMessage(null, messageId, arguments);
	}

	public void addGlobalInfoMessage(String messageId) {
		addComponentInfoMessage(null, messageId);
	}

	public void addGlobalInfoMessage(String messageId, Object argument) {
		addComponentInfoMessage(null, messageId, argument);
	}

	public void addGlobalInfoMessage(String messageId, Object... arguments) {
		addComponentInfoMessage(null, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalSuccessInfoMessage()
	 */
	public void addGlobalSuccessInfoMessage() {
		addGlobalInfoMessage(SUCCESS_INFO_MSG_ID);
	}

	/**
	 * @see  FacesContextHelper#addGlobalUnexpectedErrorMessage()
	 */
	public void addGlobalUnexpectedErrorMessage() {
		addGlobalErrorMessage(UNEXPECTED_ERROR_MSG_ID);
	}

	public void addMessage(String clientId, Severity severity, String messageId) {

		Locale locale = getLocale();
		MessageContext messageContext = getMessageContext();
		FacesMessage facesMessage = messageContext.newFacesMessage(locale, severity, messageId);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(clientId, facesMessage);
	}

	public void addMessage(String clientId, Severity severity, String messageId, Object argument) {

		Locale locale = getLocale();
		MessageContext messageContext = getMessageContext();
		FacesMessage facesMessage = messageContext.newFacesMessage(locale, severity, messageId, argument);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(clientId, facesMessage);
	}

	public void addMessage(String clientId, Severity severity, String messageId, Object... arguments) {

		Locale locale = getLocale();
		MessageContext messageContext = getMessageContext();
		FacesMessage facesMessage = messageContext.newFacesMessage(locale, severity, messageId, arguments);
		FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
	}

	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		return ComponentUtil.matchComponentInHierarchy(facesContext, parent, partialClientId);
	}

	public UIComponent matchComponentInViewRoot(String partialClientId) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();

		return matchComponentInHierarchy(viewRoot, partialClientId);
	}

	public void navigate(String fromAction, String outcome) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		NavigationHandler navigationHandler = application.getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, fromAction, outcome);
	}

	public void navigateTo(String outcome) {
		navigate(null, outcome);
	}

	public void recreateComponentTree() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot oldViewRoot = facesContext.getViewRoot();
		UIViewRoot viewRoot = viewHandler.createView(facesContext, oldViewRoot.getViewId());
		facesContext.setViewRoot(viewRoot);
		facesContext.renderResponse();
	}

	public void registerPhaseListener(PhaseListener phaseListener) throws IllegalStateException {

		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(
				FactoryFinder.LIFECYCLE_FACTORY);

		for (Iterator<String> lifecycleIds = lifecycleFactory.getLifecycleIds(); lifecycleIds.hasNext();) {
			String lifecycleId = lifecycleIds.next();
			lifecycleFactory.getLifecycle(lifecycleId).addPhaseListener(phaseListener);
		}
	}

	public void removeChildrenFromComponentTree(String clientId) {

		UIComponent uiComponent = FacesContext.getCurrentInstance().getViewRoot().findComponent(clientId);

		if (uiComponent != null) {
			uiComponent.getChildren().clear();
			uiComponent.getFacets().clear();
		}
	}

	public void removeMessages(String clientId) {

		Iterator<FacesMessage> facesMessages = FacesContext.getCurrentInstance().getMessages(clientId);

		while (facesMessages.hasNext()) {
			facesMessages.next();
			facesMessages.remove();
		}
	}

	public void removeMessagesForImmediateComponents() {
		removeMessagesForImmediateComponents(FacesContext.getCurrentInstance().getViewRoot());
	}

	public void removeMessagesForImmediateComponents(UIComponent uiComponent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (uiComponent instanceof ActionSource) {

			ActionSource actionSource = (ActionSource) uiComponent;

			if (actionSource.isImmediate()) {
				removeMessages(uiComponent.getClientId(facesContext));
			}
		}
		else if (uiComponent instanceof EditableValueHolder) {

			EditableValueHolder editableValueHolder = (EditableValueHolder) uiComponent;

			if (editableValueHolder.isImmediate()) {
				removeMessages(uiComponent.getClientId(facesContext));
			}
		}

		List<UIComponent> childComponents = uiComponent.getChildren();

		for (UIComponent childComponent : childComponents) {
			removeMessagesForImmediateComponents(childComponent);
		}
	}

	public void removeParentFormFromComponentTree(final UIComponent uiComponent) {

		UIComponent form = getParentForm(uiComponent);

		if (form != null) {
			form.getChildren().clear();
			form.getFacets().clear();
		}
	}

	public void resetView() {
		resetView(true);
	}

	public void resetView(boolean renderResponse) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		UIViewRoot emptyView = viewHandler.createView(facesContext, viewRoot.getViewId());
		facesContext.setViewRoot(emptyView);

		if (renderResponse) {
			facesContext.renderResponse();
		}
	}

	public Object resolveExpression(String elExpression) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		ELResolver elResolver = application.getELResolver();
		ELContext elContext = facesContext.getELContext();

		return elResolver.getValue(elContext, null, elExpression);
	}

	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public Locale getLocale() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale locale = viewRoot.getLocale();

		// If the JSF ViewRoot didn't return a locale, then try and get it from the JSF Application.
		if (locale == null) {
			Application application = facesContext.getApplication();
			locale = application.getDefaultLocale();
		}

		// Otherwise, if we couldn't determine the locale, just use the server's default value.
		if (locale == null) {
			locale = Locale.getDefault();
		}

		return locale;
	}

	public String getMessage(String messageId) {
		return getMessage(getLocale(), messageId);
	}

	public String getMessage(String messageId, Object... arguments) {

		MessageContext messageContext = getMessageContext();

		return messageContext.getMessage(getLocale(), messageId, arguments);
	}

	public String getMessage(Locale locale, String messageId) {

		MessageContext messageContext = getMessageContext();

		return messageContext.getMessage(locale, messageId);
	}

	public String getMessage(Locale locale, String messageId, Object... arguments) {

		MessageContext messageContext = getMessageContext();

		return messageContext.getMessage(locale, messageId, arguments);
	}

	protected MessageContext getMessageContext() {
		return MessageContextFactory.getMessageContextInstance();
	}

	public String getNamespace() {
		return FacesContext.getCurrentInstance().getExternalContext().encodeNamespace("");
	}

	public UIForm getParentForm(final UIComponent uiComponent) {

		UIComponent parent = uiComponent;

		while ((parent != null) && !(parent instanceof UIForm)) {
			parent = parent.getParent();
		}

		return (UIForm) parent;
	}

	public Object getRequestAttribute(String name) {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();

		return httpServletRequest.getAttribute(name);
	}

	public void setRequestAttribute(String name, Object value) {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();
		httpServletRequest.setAttribute(name, value);
	}

	public String getRequestContextPath() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		return externalContext.getRequestContextPath();
	}

	public String getRequestParameter(String name) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();

		return requestParameterMap.get(name);
	}

	public boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		return BooleanHelper.toBoolean(getRequestParameter(name), defaultValue);
	}

	public int getRequestParameterAsInt(String name, int defaultValue) {
		return IntegerHelper.toInteger(getRequestParameter(name), defaultValue);
	}

	public long getRequestParameterAsLong(String name, long defaultValue) {
		return LongHelper.toLong(getRequestParameter(name), defaultValue);
	}

	public String getRequestParameterFromMap(String name) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		return externalContext.getRequestParameterMap().get(name);
	}

	public Map<String, String> getRequestParameterMap() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		return externalContext.getRequestParameterMap();
	}

	public String getRequestQueryString() {
		return (String) getRequestAttribute("javax.servlet.forward.query_string");
	}

	public String getRequestQueryStringParameter(String name) {

		String value = null;
		String queryString = getRequestQueryString();

		if (queryString != null) {
			String[] queryStringTokens = queryString.split("&");
			boolean found = false;

			for (int i = 0; (!found && (i < queryStringTokens.length)); i++) {
				String nameValuePair = queryStringTokens[i];
				String[] nameValuePairArray = nameValuePair.split("=");
				found = nameValuePairArray[0].equals(name);

				if (found && (nameValuePairArray.length > 1)) {
					value = nameValuePairArray[1];
				}
			}
		}

		return value;
	}

	public Object getSession(boolean create) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		return externalContext.getSession(create);
	}

	public Object getSessionAttribute(String name) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		return sessionMap.get(name);
	}

	public void setSessionAttribute(String name, Object value) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put(name, value);
	}
}
