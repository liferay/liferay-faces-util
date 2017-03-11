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
package com.liferay.faces.util.context;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;

import com.liferay.faces.util.context.internal.ExtFacesContextImpl;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class is deprecated and has been replaced by {@link com.liferay.faces.util.context.FacesContextHelperUtil}. For
 * more information, see <a href="https://issues.liferay.com/browse/FACES-2502">FACES-2502</a>.
 *
 * @deprecated  Please use {@link com.liferay.faces.util.context.FacesContextHelperUtil} Instead.
 * @author      Neil Griffin
 */
@Deprecated
public abstract class ExtFacesContext extends FacesContext implements FacesContextHelper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExtFacesContext.class);

	// Singleton Instance
	private static transient ExtFacesContext instance = new ExtFacesContextImpl();

	/**
	 * Returns the implementation singleton instance.
	 */
	public static ExtFacesContext getInstance() {

		if (instance == null) {
			logger.error("Instance not initialized -- caller might be static");
		}

		return instance;
	}

	/**
	 * Sets the implementation singleton instance.
	 */
	public static void setInstance(ExtFacesContext extFacesContext) {
		instance = extFacesContext;
	}

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentErrorMessage(String, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addComponentErrorMessage(String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentErrorMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addComponentErrorMessage(String clientId, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentErrorMessage(String, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentErrorMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentInfoMessage(String, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addComponentInfoMessage(String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentInfoMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addComponentInfoMessage(String clientId, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentInfoMessage(String, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentInfoMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalErrorMessage(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalErrorMessage(String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalErrorMessage(String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalErrorMessage(String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalErrorMessage(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalErrorMessage(FacesContext facesContext, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalErrorMessage(String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalInfoMessage(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalInfoMessage(String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalInfoMessage(String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalInfoMessage(String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalInfoMessage(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalInfoMessage(FacesContext facesContext, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalInfoMessage(String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalSuccessInfoMessage()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalSuccessInfoMessage();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalSuccessInfoMessage()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalSuccessInfoMessage(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalUnexpectedErrorMessage()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalUnexpectedErrorMessage();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalUnexpectedErrorMessage()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addGlobalUnexpectedErrorMessage(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContext#addMessage(String, FacesMessage)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addMessage(String s, FacesMessage facesMessage);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addMessage(String, FacesMessage.Severity, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addMessage(String clientId, FacesMessage.Severity severity, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addMessage(String, FacesMessage.Severity, String, Object...)}
	 *              instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addMessage(String clientId, FacesMessage.Severity severity, String messageId,
		Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addMessage(String, FacesMessage.Severity, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addMessage(String, FacesMessage.Severity, String, Object...)}
	 *              instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContext#getApplication()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Application getApplication();

	/**
	 * @deprecated  Call {@link FacesContext#getClientIdsWithMessages()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Iterator<String> getClientIdsWithMessages();

	/**
	 * @deprecated  Call {@link FacesContext#getELContext()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract ELContext getELContext();

	/**
	 * @deprecated  Call {@link FacesContext#getExternalContext()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract ExternalContext getExternalContext();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getFacesContext()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract FacesContext getFacesContext();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getLocale()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Locale getLocale();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getLocale()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Locale getLocale(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContext#getMaximumSeverity()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract FacesMessage.Severity getMaximumSeverity();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getMessage(String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getMessage(String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(Locale, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getMessage(Locale locale, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getMessage(FacesContext facesContext, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(Locale, String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getMessage(Locale locale, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(Locale, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getMessage(FacesContext facesContext, Locale locale, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(Locale, String, Object...)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContext#getMessages()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Iterator<FacesMessage> getMessages();

	/**
	 * @deprecated  Call {@link FacesContext#getMessages(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Iterator<FacesMessage> getMessages(String s);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getNamespace()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getNamespace();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getNamespace()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getNamespace(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getParentForm(UIComponent)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract UIForm getParentForm(UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContext#getRenderKit()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract RenderKit getRenderKit();

	/**
	 * @deprecated  Call {@link FacesContext#getRenderResponse()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract boolean getRenderResponse();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestAttribute(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Object getRequestAttribute(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestAttribute(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Object getRequestAttribute(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestContextPath()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getRequestContextPath();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestContextPath()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getRequestContextPath(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameter(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getRequestParameter(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameter(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getRequestParameter(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsBool(String, boolean)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract boolean getRequestParameterAsBool(String name, boolean defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsBool(String, boolean)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract boolean getRequestParameterAsBool(FacesContext facesContext, String name, boolean defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsInt(String, int)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract int getRequestParameterAsInt(String name, int defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsInt(String, int)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract int getRequestParameterAsInt(FacesContext facesContext, String name, int defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsLong(String, long)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract long getRequestParameterAsLong(String name, long defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsLong(String, long)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract long getRequestParameterAsLong(FacesContext facesContext, String name, long defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterFromMap(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getRequestParameterFromMap(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterFromMap(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getRequestParameterFromMap(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterMap()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Map<String, String> getRequestParameterMap();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterMap()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Map<String, String> getRequestParameterMap(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestQueryString()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getRequestQueryString();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestQueryString()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getRequestQueryString(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestQueryStringParameter(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getRequestQueryStringParameter(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestQueryStringParameter(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract String getRequestQueryStringParameter(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContext#getResponseComplete()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract boolean getResponseComplete();

	/**
	 * @deprecated  Call {@link FacesContext#getResponseStream()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract ResponseStream getResponseStream();

	/**
	 * @deprecated  Call {@link FacesContext#getResponseWriter()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract ResponseWriter getResponseWriter();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getSession(boolean)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Object getSession(boolean create);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getSession(boolean)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Object getSession(FacesContext facesContext, boolean create);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getSessionAttribute(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Object getSessionAttribute(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getSessionAttribute(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Object getSessionAttribute(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContext#getViewRoot()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract UIViewRoot getViewRoot();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#matchComponentInHierarchy(UIComponent, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#matchComponentInHierarchy(UIComponent, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent,
		String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#matchComponentInViewRoot(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract UIComponent matchComponentInViewRoot(String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#matchComponentInViewRoot(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract UIComponent matchComponentInViewRoot(FacesContext facesContext, String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#navigate(String, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void navigate(String fromAction, String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#navigate(String, String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void navigate(FacesContext facesContext, String fromAction, String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#navigateTo(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void navigateTo(String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#navigateTo(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void navigateTo(FacesContext facesContext, String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#recreateComponentTree()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void recreateComponentTree();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#recreateComponentTree()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void recreateComponentTree(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#registerPhaseListener(PhaseListener)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void registerPhaseListener(PhaseListener phaseListener);

	/**
	 * @deprecated  Call {@link FacesContext#release()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void release();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeChildrenFromComponentTree(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void removeChildrenFromComponentTree(String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeChildrenFromComponentTree(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void removeChildrenFromComponentTree(FacesContext facesContext, String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessages(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void removeMessages(String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessages(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void removeMessages(FacesContext facesContext, String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessagesForImmediateComponents()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void removeMessagesForImmediateComponents();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessagesForImmediateComponents(UIComponent)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void removeMessagesForImmediateComponents(UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessagesForImmediateComponents()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void removeMessagesForImmediateComponents(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessagesForImmediateComponents(UIComponent)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void removeMessagesForImmediateComponents(FacesContext facesContext, UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeParentFormFromComponentTree(UIComponent)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void removeParentFormFromComponentTree(UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContext#renderResponse()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void renderResponse();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resetView()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void resetView();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resetView(boolean)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void resetView(boolean renderResponse);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resetView()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void resetView(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resetView(boolean)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void resetView(FacesContext facesContext, boolean renderResponse);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resolveExpression(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Object resolveExpression(String elExpression);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resolveExpression(String)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract Object resolveExpression(FacesContext facesContext, String elExpression);

	/**
	 * @deprecated  Call {@link FacesContext#responseComplete()} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void responseComplete();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#setRequestAttribute(String, Object)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void setRequestAttribute(String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#setRequestAttribute(String, Object)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void setRequestAttribute(FacesContext facesContext, String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContext#setResponseStream(ResponseStream)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void setResponseStream(ResponseStream responseStream);

	/**
	 * @deprecated  Call {@link FacesContext#setResponseWriter(ResponseWriter)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void setResponseWriter(ResponseWriter responseWriter);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#setSessionAttribute(String, Object)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void setSessionAttribute(String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#setSessionAttribute(String, Object)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void setSessionAttribute(FacesContext facesContext, String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContext#setViewRoot(UIViewRoot)} instead.
	 */
	@Deprecated
	// Java 1.6+ @Override
	public abstract void setViewRoot(UIViewRoot uiViewRoot);
}
