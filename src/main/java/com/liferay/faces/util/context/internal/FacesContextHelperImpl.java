/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.client.ScriptFactory;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.helper.IntegerHelper;
import com.liferay.faces.util.helper.LongHelper;
import com.liferay.faces.util.i18n.I18n;
import com.liferay.faces.util.i18n.I18nFactory;


/**
 * @author  Neil Griffin
 */
public class FacesContextHelperImpl implements FacesContextHelper, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 5363868926989717581L;

	// Private Constants
	private static final String UNEXPECTED_ERROR_MSG_ID = "an-unexpected-error-occurred";
	private static final String SCRIPTS_KEY = FacesContextHelperImpl.class.getName() + "_SCRIPTS";
	private static final String SUCCESS_INFO_MSG_ID = "your-request-processed-successfully";

	@Override
	public void addComponentErrorMessage(String clientId, String messageId) {
		addComponentErrorMessage(FacesContext.getCurrentInstance(), clientId, messageId);
	}

	@Override
	public void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId) {
		addMessage(facesContext, clientId, FacesMessage.SEVERITY_ERROR, messageId);
	}

	@Override
	public void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		addComponentErrorMessage(FacesContext.getCurrentInstance(), clientId, messageId, arguments);
	}

	@Override
	public void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments) {
		addMessage(facesContext, clientId, FacesMessage.SEVERITY_ERROR, messageId, arguments);
	}

	@Override
	public void addComponentInfoMessage(String clientId, String messageId) {
		addComponentInfoMessage(FacesContext.getCurrentInstance(), clientId, messageId);
	}

	@Override
	public void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId) {
		addMessage(facesContext, clientId, FacesMessage.SEVERITY_INFO, messageId);
	}

	@Override
	public void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		addComponentInfoMessage(FacesContext.getCurrentInstance(), clientId, messageId, arguments);
	}

	@Override
	public void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments) {
		addMessage(facesContext, clientId, FacesMessage.SEVERITY_INFO, messageId, arguments);
	}

	@Override
	public void addGlobalErrorMessage(String messageId) {
		addGlobalErrorMessage(FacesContext.getCurrentInstance(), messageId);
	}

	@Override
	public void addGlobalErrorMessage(FacesContext facesContext, String messageId) {
		addComponentErrorMessage(facesContext, null, messageId);
	}

	@Override
	public void addGlobalErrorMessage(String messageId, Object... arguments) {
		addGlobalErrorMessage(FacesContext.getCurrentInstance(), messageId, arguments);
	}

	@Override
	public void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object... arguments) {
		addComponentErrorMessage(facesContext, null, messageId, arguments);
	}

	@Override
	public void addGlobalInfoMessage(String messageId) {
		addGlobalInfoMessage(FacesContext.getCurrentInstance(), messageId);
	}

	@Override
	public void addGlobalInfoMessage(FacesContext facesContext, String messageId) {
		addComponentInfoMessage(facesContext, null, messageId);
	}

	@Override
	public void addGlobalInfoMessage(String messageId, Object... arguments) {
		addGlobalInfoMessage(FacesContext.getCurrentInstance(), messageId, arguments);
	}

	@Override
	public void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object... arguments) {
		addComponentInfoMessage(facesContext, null, messageId, arguments);
	}

	/**
	 * @see  FacesContextHelper#addGlobalSuccessInfoMessage()
	 */
	@Override
	public void addGlobalSuccessInfoMessage() {
		addGlobalSuccessInfoMessage(FacesContext.getCurrentInstance());
	}

	/**
	 * @see  FacesContextHelper#addGlobalSuccessInfoMessage()
	 */
	@Override
	public void addGlobalSuccessInfoMessage(FacesContext facesContext) {
		addGlobalInfoMessage(facesContext, SUCCESS_INFO_MSG_ID);
	}

	/**
	 * @see  FacesContextHelper#addGlobalUnexpectedErrorMessage()
	 */
	@Override
	public void addGlobalUnexpectedErrorMessage() {
		addGlobalUnexpectedErrorMessage(FacesContext.getCurrentInstance());
	}

	/**
	 * @see  FacesContextHelper#addGlobalUnexpectedErrorMessage()
	 */
	@Override
	public void addGlobalUnexpectedErrorMessage(FacesContext facesContext) {
		addGlobalErrorMessage(facesContext, UNEXPECTED_ERROR_MSG_ID);
	}

	@Override
	public void addMessage(String clientId, Severity severity, String messageId) {
		addMessage(FacesContext.getCurrentInstance(), clientId, severity, messageId);
	}

	@Override
	public void addMessage(FacesContext facesContext, String clientId, Severity severity, String messageId) {

		Locale locale = getLocale(facesContext);
		I18n i18n = getI18n(facesContext);
		FacesMessage facesMessage = i18n.getFacesMessage(facesContext, locale, severity, messageId);
		facesContext.addMessage(clientId, facesMessage);
	}

	@Override
	public void addMessage(String clientId, Severity severity, String messageId, Object... arguments) {
		addMessage(FacesContext.getCurrentInstance(), clientId, severity, messageId, arguments);
	}

	@Override
	public void addMessage(FacesContext facesContext, String clientId, Severity severity, String messageId,
		Object... arguments) {

		Locale locale = getLocale(facesContext);
		I18n i18n = getI18n(facesContext);
		FacesMessage facesMessage = i18n.getFacesMessage(facesContext, locale, severity, messageId, arguments);
		facesContext.addMessage(clientId, facesMessage);
	}

	@Override
	public void addScript(Script script) {
		addScript(FacesContext.getCurrentInstance(), script);
	}

	@Override
	public void addScript(String scriptString) {
		addScript(FacesContext.getCurrentInstance(), scriptString);
	}

	@Override
	public void addScript(FacesContext facesContext, Script script) {
		getModifiableScriptsList(facesContext).add(script);
	}

	@Override
	public void addScript(FacesContext facesContext, String scriptString) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Script script = ScriptFactory.getScriptInstance(externalContext, scriptString);
		addScript(facesContext, script);
	}

	@Override
	public FacesContext getFacesContext() {
		return getFacesContext(FacesContext.getCurrentInstance());
	}

	public FacesContext getFacesContext(FacesContext facesContext) {
		return facesContext;
	}

	@Override
	public Locale getLocale() {
		return getLocale(FacesContext.getCurrentInstance());
	}

	@Override
	public Locale getLocale(FacesContext facesContext) {

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

	@Override
	public String getMessage(String messageId) {
		return getMessage(FacesContext.getCurrentInstance(), messageId);
	}

	@Override
	public String getMessage(FacesContext facesContext, String messageId) {
		return getMessage(facesContext, getLocale(facesContext), messageId);
	}

	@Override
	public String getMessage(String messageId, Object... arguments) {
		return getMessage(FacesContext.getCurrentInstance(), messageId, arguments);
	}

	@Override
	public String getMessage(Locale locale, String messageId) {
		return getMessage(FacesContext.getCurrentInstance(), locale, messageId);
	}

	@Override
	public String getMessage(FacesContext facesContext, String messageId, Object... arguments) {

		I18n i18n = getI18n(facesContext);

		return i18n.getMessage(facesContext, getLocale(facesContext), messageId, arguments);
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId) {

		I18n i18n = getI18n(facesContext);

		return i18n.getMessage(facesContext, locale, messageId);
	}

	@Override
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return getMessage(FacesContext.getCurrentInstance(), locale, messageId, arguments);
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {

		I18n i18n = getI18n(facesContext);

		return i18n.getMessage(facesContext, locale, messageId, arguments);
	}

	@Override
	public String getNamespace() {
		return getNamespace(FacesContext.getCurrentInstance());
	}

	@Override
	public String getNamespace(FacesContext facesContext) {
		return facesContext.getExternalContext().encodeNamespace("");
	}

	@Override
	public UIForm getParentForm(final UIComponent uiComponent) {

		UIComponent parent = uiComponent;

		while ((parent != null) && !(parent instanceof UIForm)) {
			parent = parent.getParent();
		}

		return (UIForm) parent;
	}

	@Override
	public Object getRequestAttribute(String name) {
		return getRequestAttribute(FacesContext.getCurrentInstance(), name);
	}

	@Override
	public Object getRequestAttribute(FacesContext facesContext, String name) {

		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();

		return httpServletRequest.getAttribute(name);
	}

	@Override
	public String getRequestContextPath() {
		return getRequestContextPath(FacesContext.getCurrentInstance());
	}

	@Override
	public String getRequestContextPath(FacesContext facesContext) {

		ExternalContext externalContext = facesContext.getExternalContext();

		return externalContext.getRequestContextPath();
	}

	@Override
	public String getRequestParameter(String name) {
		return getRequestParameter(FacesContext.getCurrentInstance(), name);
	}

	@Override
	public String getRequestParameter(FacesContext facesContext, String name) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();

		return requestParameterMap.get(name);
	}

	@Override
	public boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		return getRequestParameterAsBool(FacesContext.getCurrentInstance(), name, defaultValue);
	}

	@Override
	public boolean getRequestParameterAsBool(FacesContext facesContext, String name, boolean defaultValue) {
		return BooleanHelper.toBoolean(getRequestParameter(facesContext, name), defaultValue);
	}

	@Override
	public int getRequestParameterAsInt(String name, int defaultValue) {
		return getRequestParameterAsInt(FacesContext.getCurrentInstance(), name, defaultValue);
	}

	@Override
	public int getRequestParameterAsInt(FacesContext facesContext, String name, int defaultValue) {
		return IntegerHelper.toInteger(getRequestParameter(facesContext, name), defaultValue);
	}

	@Override
	public long getRequestParameterAsLong(String name, long defaultValue) {
		return getRequestParameterAsLong(FacesContext.getCurrentInstance(), name, defaultValue);
	}

	@Override
	public long getRequestParameterAsLong(FacesContext facesContext, String name, long defaultValue) {
		return LongHelper.toLong(getRequestParameter(facesContext, name), defaultValue);
	}

	@Override
	public String getRequestParameterFromMap(String name) {
		return getRequestParameterFromMap(FacesContext.getCurrentInstance(), name);
	}

	@Override
	public String getRequestParameterFromMap(FacesContext facesContext, String name) {

		ExternalContext externalContext = facesContext.getExternalContext();

		return externalContext.getRequestParameterMap().get(name);
	}

	@Override
	public Map<String, String> getRequestParameterMap() {
		return getRequestParameterMap(FacesContext.getCurrentInstance());
	}

	@Override
	public Map<String, String> getRequestParameterMap(FacesContext facesContext) {

		ExternalContext externalContext = facesContext.getExternalContext();

		return externalContext.getRequestParameterMap();
	}

	@Override
	public String getRequestQueryString() {
		return getRequestQueryString(FacesContext.getCurrentInstance());
	}

	@Override
	public String getRequestQueryString(FacesContext facesContext) {
		return (String) getRequestAttribute(facesContext, "javax.servlet.forward.query_string");
	}

	@Override
	public String getRequestQueryStringParameter(String name) {
		return getRequestQueryStringParameter(FacesContext.getCurrentInstance(), name);
	}

	@Override
	public String getRequestQueryStringParameter(FacesContext facesContext, String name) {

		String value = null;
		String queryString = getRequestQueryString(facesContext);

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

	@Override
	public List<Script> getScripts() {
		return getScripts(FacesContext.getCurrentInstance());
	}

	@Override
	public List<Script> getScripts(FacesContext facesContext) {
		return Collections.unmodifiableList(getModifiableScriptsList(facesContext));
	}

	@Override
	public Object getSession(boolean create) {
		return getSession(FacesContext.getCurrentInstance(), create);
	}

	@Override
	public Object getSession(FacesContext facesContext, boolean create) {

		ExternalContext externalContext = facesContext.getExternalContext();

		return externalContext.getSession(create);
	}

	@Override
	public Object getSessionAttribute(String name) {
		return getSessionAttribute(FacesContext.getCurrentInstance(), name);
	}

	@Override
	public Object getSessionAttribute(FacesContext facesContext, String name) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();

		return sessionMap.get(name);
	}

	@Override
	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		return matchComponentInHierarchy(FacesContext.getCurrentInstance(), parent, partialClientId);
	}

	@Override
	public UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent,
		String partialClientId) {
		return ComponentUtil.matchComponentInHierarchy(facesContext, parent, partialClientId);
	}

	@Override
	public UIComponent matchComponentInViewRoot(String partialClientId) {
		return matchComponentInViewRoot(FacesContext.getCurrentInstance(), partialClientId);
	}

	@Override
	public UIComponent matchComponentInViewRoot(FacesContext facesContext, String partialClientId) {

		UIViewRoot viewRoot = facesContext.getViewRoot();

		return matchComponentInHierarchy(facesContext, viewRoot, partialClientId);
	}

	@Override
	public void navigate(String fromAction, String outcome) {
		navigate(FacesContext.getCurrentInstance(), fromAction, outcome);
	}

	@Override
	public void navigate(FacesContext facesContext, String fromAction, String outcome) {

		Application application = facesContext.getApplication();
		NavigationHandler navigationHandler = application.getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, fromAction, outcome);
	}

	@Override
	public void navigateTo(String outcome) {
		navigateTo(FacesContext.getCurrentInstance(), outcome);
	}

	@Override
	public void navigateTo(FacesContext facesContext, String outcome) {
		navigate(facesContext, null, outcome);
	}

	@Override
	public void recreateComponentTree() {
		recreateComponentTree(FacesContext.getCurrentInstance());
	}

	@Override
	public void recreateComponentTree(FacesContext facesContext) {

		Application application = facesContext.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot oldViewRoot = facesContext.getViewRoot();
		UIViewRoot viewRoot = viewHandler.createView(facesContext, oldViewRoot.getViewId());
		facesContext.setViewRoot(viewRoot);
		facesContext.renderResponse();
	}

	@Override
	public void registerPhaseListener(PhaseListener phaseListener) throws IllegalStateException {

		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(
				FactoryFinder.LIFECYCLE_FACTORY);

		for (Iterator<String> lifecycleIds = lifecycleFactory.getLifecycleIds(); lifecycleIds.hasNext();) {
			String lifecycleId = lifecycleIds.next();
			lifecycleFactory.getLifecycle(lifecycleId).addPhaseListener(phaseListener);
		}
	}

	@Override
	public void removeChildrenFromComponentTree(String clientId) {
		removeChildrenFromComponentTree(FacesContext.getCurrentInstance(), clientId);
	}

	@Override
	public void removeChildrenFromComponentTree(FacesContext facesContext, String clientId) {

		UIComponent uiComponent = facesContext.getViewRoot().findComponent(clientId);

		if (uiComponent != null) {
			uiComponent.getChildren().clear();
			uiComponent.getFacets().clear();
		}
	}

	@Override
	public void removeMessages(String clientId) {
		removeMessages(FacesContext.getCurrentInstance(), clientId);
	}

	@Override
	public void removeMessages(FacesContext facesContext, String clientId) {

		Iterator<FacesMessage> facesMessages = facesContext.getMessages(clientId);

		while (facesMessages.hasNext()) {
			facesMessages.next();
			facesMessages.remove();
		}
	}

	@Override
	public void removeMessagesForImmediateComponents() {
		removeMessagesForImmediateComponents(FacesContext.getCurrentInstance());
	}

	@Override
	public void removeMessagesForImmediateComponents(FacesContext facesContext) {
		removeMessagesForImmediateComponents(facesContext, facesContext.getViewRoot());
	}

	@Override
	public void removeMessagesForImmediateComponents(UIComponent uiComponent) {
		removeMessagesForImmediateComponents(FacesContext.getCurrentInstance(), uiComponent);
	}

	@Override
	public void removeMessagesForImmediateComponents(FacesContext facesContext, UIComponent uiComponent) {

		if (uiComponent instanceof ActionSource) {

			ActionSource actionSource = (ActionSource) uiComponent;

			if (actionSource.isImmediate()) {
				removeMessages(facesContext, uiComponent.getClientId(facesContext));
			}
		}
		else if (uiComponent instanceof EditableValueHolder) {

			EditableValueHolder editableValueHolder = (EditableValueHolder) uiComponent;

			if (editableValueHolder.isImmediate()) {
				removeMessages(facesContext, uiComponent.getClientId(facesContext));
			}
		}

		List<UIComponent> childComponents = uiComponent.getChildren();

		for (UIComponent childComponent : childComponents) {
			removeMessagesForImmediateComponents(facesContext, childComponent);
		}
	}

	@Override
	public void removeParentFormFromComponentTree(final UIComponent uiComponent) {

		UIComponent form = getParentForm(uiComponent);

		if (form != null) {
			form.getChildren().clear();
			form.getFacets().clear();
		}
	}

	@Override
	public void resetView() {
		resetView(FacesContext.getCurrentInstance());
	}

	@Override
	public void resetView(FacesContext facesContext) {
		resetView(facesContext, true);
	}

	@Override
	public void resetView(boolean renderResponse) {
		resetView(FacesContext.getCurrentInstance(), renderResponse);
	}

	@Override
	public void resetView(FacesContext facesContext, boolean renderResponse) {

		Application application = facesContext.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		UIViewRoot emptyView = viewHandler.createView(facesContext, viewRoot.getViewId());
		facesContext.setViewRoot(emptyView);

		if (renderResponse) {
			facesContext.renderResponse();
		}
	}

	@Override
	public Object resolveExpression(String elExpression) {
		return resolveExpression(FacesContext.getCurrentInstance(), elExpression);
	}

	@Override
	public Object resolveExpression(FacesContext facesContext, String elExpression) {

		Application application = facesContext.getApplication();
		ELResolver elResolver = application.getELResolver();
		ELContext elContext = facesContext.getELContext();

		return elResolver.getValue(elContext, null, elExpression);
	}

	@Override
	public void setRequestAttribute(String name, Object value) {
		setRequestAttribute(FacesContext.getCurrentInstance(), name, value);
	}

	@Override
	public void setRequestAttribute(FacesContext facesContext, String name, Object value) {

		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();
		httpServletRequest.setAttribute(name, value);
	}

	@Override
	public void setSessionAttribute(String name, Object value) {
		setSessionAttribute(FacesContext.getCurrentInstance(), name, value);
	}

	@Override
	public void setSessionAttribute(FacesContext facesContext, String name, Object value) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put(name, value);
	}

	private I18n getI18n(FacesContext facesContext) {

		ExternalContext externalContext = facesContext.getExternalContext();

		return I18nFactory.getI18nInstance(externalContext);
	}

	private List<Script> getModifiableScriptsList(FacesContext facesContext) {

		Map<Object, Object> attributes = facesContext.getAttributes();
		List<Script> scripts = (List<Script>) attributes.get(SCRIPTS_KEY);

		if (scripts == null) {

			scripts = new ArrayList<Script>();
			attributes.put(SCRIPTS_KEY, scripts);
		}

		return scripts;
	}
}
