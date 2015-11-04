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
package com.liferay.faces.util.context.internal;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;

import com.liferay.faces.util.context.ExtFacesContext;
import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.context.FacesContextHelperUtil;


/**
 * @author  Neil Griffin
 */
public class ExtFacesContextImpl extends ExtFacesContext {

	public ExtFacesContextImpl() {
		setInstance(this);
	}

	/**
	 * @see  {@link FacesContextHelper#addComponentErrorMessage(String, String)}
	 */
	public void addComponentErrorMessage(String clientId, String messageId) {
		FacesContextHelperUtil.addComponentErrorMessage(clientId, messageId);
	}

	/**
	 * @see  {@link FacesContextHelper#addComponentErrorMessage(String, String, Object)}
	 */
	public void addComponentErrorMessage(String clientId, String messageId, Object argument) {
		FacesContextHelperUtil.addComponentErrorMessage(clientId, messageId, argument);
	}

	/**
	 * @see  {@link FacesContextHelper#addComponentErrorMessage(String, String, Object...)}
	 */
	public void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		FacesContextHelperUtil.addComponentErrorMessage(clientId, messageId, arguments);
	}

	/**
	 * @see  {@link FacesContextHelper#addComponentInfoMessage(String, String)}
	 */
	public void addComponentInfoMessage(String clientId, String messageId) {
		FacesContextHelperUtil.addComponentInfoMessage(clientId, messageId);
	}

	/**
	 * @see  {@link FacesContextHelper#addComponentInfoMessage(String, String, Object)}
	 */
	public void addComponentInfoMessage(String clientId, String messageId, Object argument) {
		FacesContextHelperUtil.addComponentInfoMessage(clientId, messageId, argument);
	}

	/**
	 * @see  {@link FacesContextHelper#addComponentInfoMessage(String, String, Object...)}
	 */
	public void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		FacesContextHelperUtil.addComponentInfoMessage(clientId, messageId, arguments);
	}

	/**
	 * @see  {@link FacesContextHelper#addGlobalErrorMessage(String)}
	 */
	public void addGlobalErrorMessage(String messageId) {
		FacesContextHelperUtil.addGlobalErrorMessage(messageId);
	}

	/**
	 * @see  {@link FacesContextHelper#addGlobalErrorMessage(String, Object)}
	 */
	public void addGlobalErrorMessage(String messageId, Object argument) {
		FacesContextHelperUtil.addGlobalErrorMessage(messageId, argument);
	}

	/**
	 * @see  {@link FacesContextHelper#addGlobalErrorMessage(String, Object...)}
	 */
	public void addGlobalErrorMessage(String messageId, Object... arguments) {
		FacesContextHelperUtil.addGlobalErrorMessage(messageId, arguments);
	}

	/**
	 * @see  {@link FacesContextHelper#addGlobalInfoMessage(String)}
	 */
	public void addGlobalInfoMessage(String messageId) {
		FacesContextHelperUtil.addGlobalInfoMessage(messageId);
	}

	/**
	 * @see  {@link FacesContextHelper#addGlobalInfoMessage(String, Object)}
	 */
	public void addGlobalInfoMessage(String messageId, Object argument) {
		FacesContextHelperUtil.addGlobalInfoMessage(messageId, argument);
	}

	/**
	 * @see  {@link FacesContextHelper#addGlobalInfoMessage(String, Object...)}
	 */
	public void addGlobalInfoMessage(String messageId, Object... arguments) {
		FacesContextHelperUtil.addGlobalInfoMessage(messageId, arguments);
	}

	/**
	 * @see  {@link FacesContextHelper#addGlobalSuccessInfoMessage()}
	 */
	public void addGlobalSuccessInfoMessage() {
		FacesContextHelperUtil.addGlobalSuccessInfoMessage();
	}

	/**
	 * @see  {@link FacesContextHelper#addGlobalUnexpectedErrorMessage()}
	 */
	public void addGlobalUnexpectedErrorMessage() {
		FacesContextHelperUtil.addGlobalUnexpectedErrorMessage();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void addMessage(String clientId, FacesMessage facesMessage) {
		FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
	}

	/**
	 * @see  {@link FacesContextHelper#addMessage(String, Severity, String)}
	 */
	public void addMessage(String clientId, Severity severity, String messageId) {
		FacesContextHelperUtil.addMessage(clientId, severity, messageId);
	}

	/**
	 * @see  {@link FacesContextHelper#addMessage(String, Severity, String, Object)}
	 */
	public void addMessage(String clientId, Severity severity, String messageId, Object argument) {
		FacesContextHelperUtil.addMessage(clientId, severity, messageId, argument);
	}

	/**
	 * @see  {@link FacesContextHelper#addMessage(String, Severity, String, Object...)}
	 */
	public void addMessage(String clientId, Severity severity, String messageId, Object... arguments) {
		FacesContextHelperUtil.addMessage(clientId, severity, messageId, arguments);
	}

	/**
	 * @see  {@link FacesContextHelper#matchComponentInHierarchy(UIComponent, String)}
	 */
	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		return FacesContextHelperUtil.matchComponentInHierarchy(parent, partialClientId);
	}

	/**
	 * @see  {@link FacesContextHelper#matchComponentInViewRoot(String)}
	 */
	public UIComponent matchComponentInViewRoot(String partialClientId) {
		return FacesContextHelperUtil.matchComponentInViewRoot(partialClientId);
	}

	/**
	 * @see  {@link FacesContextHelper#navigate(String, String)}
	 */
	public void navigate(String fromAction, String outcome) {
		FacesContextHelperUtil.navigate(fromAction, outcome);
	}

	/**
	 * @see  {@link FacesContextHelper#navigateTo(String)}
	 */
	public void navigateTo(String outcome) {
		FacesContextHelperUtil.navigateTo(outcome);
	}

	/**
	 * @see  {@link FacesContextHelper#recreateComponentTree()}
	 */
	public void recreateComponentTree() {
		FacesContextHelperUtil.recreateComponentTree();
	}

	/**
	 * @see  {@link FacesContextHelper#registerPhaseListener(PhaseListener)}
	 */
	public void registerPhaseListener(PhaseListener phaseListener) {
		FacesContextHelperUtil.registerPhaseListener(phaseListener);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void release() {
		FacesContext.getCurrentInstance().release();
	}

	/**
	 * @see  {@link FacesContextHelper#removeChildrenFromComponentTree(String)}
	 */
	public void removeChildrenFromComponentTree(String clientId) {
		FacesContextHelperUtil.removeChildrenFromComponentTree(clientId);
	}

	/**
	 * @see  {@link FacesContextHelper#removeMessages(String)}
	 */
	public void removeMessages(String clientId) {
		FacesContextHelperUtil.removeMessages(clientId);
	}

	/**
	 * @see  {@link FacesContextHelper#removeMessagesForImmediateComponents()}
	 */
	public void removeMessagesForImmediateComponents() {
		FacesContextHelperUtil.removeMessagesForImmediateComponents();
	}

	/**
	 * @see  {@link FacesContextHelper#removeMessagesForImmediateComponents(UIComponent)}
	 */
	public void removeMessagesForImmediateComponents(UIComponent uiComponent) {
		FacesContextHelperUtil.removeMessagesForImmediateComponents(uiComponent);
	}

	/**
	 * @see  {@link FacesContextHelper#removeParentFormFromComponentTree(UIComponent)}
	 */
	public void removeParentFormFromComponentTree(UIComponent uiComponent) {
		FacesContextHelperUtil.removeParentFormFromComponentTree(uiComponent);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void renderResponse() {
		FacesContext.getCurrentInstance().renderResponse();
	}

	/**
	 * @see  {@link FacesContextHelper#resetView()}
	 */
	public void resetView() {
		FacesContextHelperUtil.resetView();
	}

	/**
	 * @see  {@link FacesContextHelper#resetView(boolean)
	 */
	public void resetView(boolean renderResponse) {
		FacesContextHelperUtil.resetView();
	}

	/**
	 * @see  {@link FacesContextHelper#resolveExpression(String)}
	 */
	public Object resolveExpression(String elExpression) {
		return FacesContextHelperUtil.resolveExpression(elExpression);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void responseComplete() {
		FacesContext.getCurrentInstance().responseComplete();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Application getApplication() {
		return FacesContext.getCurrentInstance().getApplication();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Iterator<String> getClientIdsWithMessages() {
		return FacesContext.getCurrentInstance().getClientIdsWithMessages();
	}

	/**
	 * @since  JSF 1.2
	 */
	@Override
	public ELContext getELContext() {
		return FacesContext.getCurrentInstance().getELContext();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * @see  {@link FacesContextHelper#getFacesContext()}
	 */
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * @see  {@link FacesContextHelper#getLocale()}
	 */
	public Locale getLocale() {
		return FacesContextHelperUtil.getLocale();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Severity getMaximumSeverity() {
		return FacesContext.getCurrentInstance().getMaximumSeverity();
	}

	/**
	 * @see  {@link FacesContextHelper#getMessage(String)}
	 */
	public String getMessage(String messageId) {
		return FacesContextHelperUtil.getMessage(messageId);
	}

	/**
	 * @see  {@link FacesContextHelper#getMessage(String, Object...)}
	 */
	public String getMessage(String messageId, Object... arguments) {
		return FacesContextHelperUtil.getMessage(messageId, arguments);
	}

	/**
	 * @see  {@link FacesContextHelper#getMessage(Locale, String)}
	 */
	public String getMessage(Locale locale, String messageId) {
		return FacesContextHelperUtil.getMessage(locale, messageId);
	}

	/**
	 * @see  {@link FacesContextHelper#getMessage(Locale, String, Object...)}
	 */
	public String getMessage(Locale locale, String messageId, Object... arguments) {
		return FacesContextHelperUtil.getMessage(locale, messageId, arguments);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Iterator<FacesMessage> getMessages() {
		return FacesContext.getCurrentInstance().getMessages();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public Iterator<FacesMessage> getMessages(String clientId) {
		return FacesContext.getCurrentInstance().getMessages(clientId);
	}

	/**
	 * @see  {@link FacesContextHelper#getNamespace()}
	 */
	public String getNamespace() {
		return FacesContextHelperUtil.getNamespace();
	}

	/**
	 * @see  {@link FacesContextHelper#getParentForm(UIComponent)}
	 */
	public UIForm getParentForm(UIComponent uiComponent) {
		return FacesContextHelperUtil.getParentForm(uiComponent);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public RenderKit getRenderKit() {
		return FacesContext.getCurrentInstance().getRenderKit();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public boolean getRenderResponse() {
		return FacesContext.getCurrentInstance().getRenderResponse();
	}

	/**
	 * @see  {@link FacesContextHelper#getRequestAttribute(String)}
	 */
	public Object getRequestAttribute(String name) {
		return FacesContextHelperUtil.getRequestAttribute(name);
	}

	/**
	 * @see  {@link FacesContextHelper#setRequestAttribute(String, Object)}
	 */
	public void setRequestAttribute(String name, Object value) {
		FacesContextHelperUtil.setRequestAttribute(name, value);
	}

	/**
	 * @see  {@link FacesContextHelper#getRequestContextPath()}
	 */
	public String getRequestContextPath() {
		return FacesContextHelperUtil.getRequestContextPath();
	}

	/**
	 * @see  {@link FacesContextHelper#getRequestParameter(String)}
	 */
	public String getRequestParameter(String name) {
		return FacesContextHelperUtil.getRequestParameter(name);
	}

	/**
	 * @see  {@link FacesContextHelper#getRequestParameterAsBool(String, boolean)}
	 */
	public boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		return FacesContextHelperUtil.getRequestParameterAsBool(name, defaultValue);
	}

	/**
	 * @see  {@link FacesContextHelper#getRequestParameterAsInt(String, int)}
	 */
	public int getRequestParameterAsInt(String name, int defaultValue) {
		return FacesContextHelperUtil.getRequestParameterAsInt(name, defaultValue);
	}

	/**
	 * @see  {@link FacesContextHelper#getRequestParameterAsLong(String, long)}
	 */
	public long getRequestParameterAsLong(String name, long defaultValue) {
		return FacesContextHelperUtil.getRequestParameterAsLong(name, defaultValue);
	}

	/**
	 * @see  {@link FacesContextHelper#getRequestParameterFromMap(String)}
	 */
	public String getRequestParameterFromMap(String name) {
		return FacesContextHelperUtil.getRequestParameterFromMap(name);
	}

	/**
	 * @see  {@link FacesContextHelper#getRequestParameterMap()}
	 */
	public Map<String, String> getRequestParameterMap() {
		return FacesContextHelperUtil.getRequestParameterMap();
	}

	/**
	 * @see  {@link FacesContextHelper#getRequestQueryString()}
	 */
	public String getRequestQueryString() {
		return FacesContextHelperUtil.getRequestQueryString();
	}

	/**
	 * @see  {@link FacesContextHelper#getRequestQueryStringParameter(String)}
	 */
	public String getRequestQueryStringParameter(String name) {
		return FacesContextHelperUtil.getRequestQueryStringParameter(name);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public boolean getResponseComplete() {
		return FacesContext.getCurrentInstance().getRenderResponse();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public ResponseStream getResponseStream() {
		return FacesContext.getCurrentInstance().getResponseStream();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setResponseStream(ResponseStream responseStream) {
		FacesContext.getCurrentInstance().setResponseStream(responseStream);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public ResponseWriter getResponseWriter() {
		return FacesContext.getCurrentInstance().getResponseWriter();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		FacesContext.getCurrentInstance().setResponseWriter(responseWriter);
	}

	/**
	 * @see  {@link FacesContextHelper#getSession(boolean)
	 */
	public Object getSession(boolean create) {
		return FacesContextHelperUtil.getSession(create);
	}

	/**
	 * @see  {@link FacesContextHelper#getSessionAttribute(String)}
	 */
	public Object getSessionAttribute(String name) {
		return FacesContextHelperUtil.getSessionAttribute(name);
	}

	/**
	 * @see  {@link FacesContextHelper#setSessionAttribute(String, Object)}
	 */
	public void setSessionAttribute(String name, Object value) {
		FacesContextHelperUtil.setSessionAttribute(name, value);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public UIViewRoot getViewRoot() {
		return FacesContext.getCurrentInstance().getViewRoot();
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public void setViewRoot(UIViewRoot viewRoot) {
		FacesContext.getCurrentInstance().setViewRoot(viewRoot);
	}
}
