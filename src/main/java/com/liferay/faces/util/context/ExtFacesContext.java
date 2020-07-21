/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKit;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class is deprecated and has been replaced by {@link com.liferay.faces.util.context.FacesContextHelperUtil}. For
 * more information, see <a href="https://issues.liferay.com/browse/FACES-2502">FACES-2502</a>.
 *
 * @deprecated  Please use {@link com.liferay.faces.util.context.FacesContextHelperUtil} instead.
 * @author      Neil Griffin
 */
@Deprecated
@ProviderType
public abstract class ExtFacesContext extends FacesContext implements FacesContextHelper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExtFacesContext.class);

	/**
	 * Returns the implementation {@link javax.faces.bean.ApplicationScoped} instance.
	 */
	public static ExtFacesContext getInstance() {

		Map<String, Object> applicationMap = getApplicationMap();
		ExtFacesContext extFacesContext = (ExtFacesContext) applicationMap.get("extFacesContext");

		if (extFacesContext == null) {
			logger.error("Instance not initialized -- caller might be static");
		}

		return extFacesContext;
	}

	/**
	 * Sets the implementation {@link javax.faces.bean.ApplicationScoped} instance.
	 */
	public static void setInstance(ExtFacesContext extFacesContext) {

		Map<String, Object> applicationMap = getApplicationMap();
		applicationMap.put("extFacesContext", extFacesContext);
	}

	private static Map<String, Object> getApplicationMap() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		return externalContext.getApplicationMap();
	}

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentErrorMessage(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentErrorMessage(String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentErrorMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentErrorMessage(String clientId, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentErrorMessage(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentErrorMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentInfoMessage(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentInfoMessage(String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentInfoMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentInfoMessage(String clientId, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentInfoMessage(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addComponentInfoMessage(String, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalErrorMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalErrorMessage(String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalErrorMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalErrorMessage(String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalErrorMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalErrorMessage(FacesContext facesContext, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalErrorMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalInfoMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalInfoMessage(String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalInfoMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalInfoMessage(String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalInfoMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalInfoMessage(FacesContext facesContext, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalInfoMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalSuccessInfoMessage()} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalSuccessInfoMessage();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalSuccessInfoMessage()} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalSuccessInfoMessage(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalUnexpectedErrorMessage()} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalUnexpectedErrorMessage();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addGlobalUnexpectedErrorMessage()} instead.
	 */
	@Deprecated
	@Override
	public abstract void addGlobalUnexpectedErrorMessage(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContext#addMessage(String, FacesMessage)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addMessage(String s, FacesMessage facesMessage);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addMessage(String, FacesMessage.Severity, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addMessage(String clientId, FacesMessage.Severity severity, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addMessage(String, FacesMessage.Severity, String, Object...)}
	 *              instead.
	 */
	@Deprecated
	@Override
	public abstract void addMessage(String clientId, FacesMessage.Severity severity, String messageId,
		Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addMessage(String, FacesMessage.Severity, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#addMessage(String, FacesMessage.Severity, String, Object...)}
	 *              instead.
	 */
	@Deprecated
	@Override
	public abstract void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContext#getApplication()} instead.
	 */
	@Deprecated
	@Override
	public abstract Application getApplication();

	/**
	 * @deprecated  Call {@link FacesContext#getAttributes()} instead.
	 */
	@Deprecated
	@Override
	public abstract Map<Object, Object> getAttributes();

	/**
	 * @deprecated  Call {@link FacesContext#getClientIdsWithMessages()} instead.
	 */
	@Deprecated
	@Override
	public abstract Iterator<String> getClientIdsWithMessages();

	/**
	 * @deprecated  Call {@link FacesContext#getCurrentPhaseId()} instead.
	 */
	@Deprecated
	@Override
	public abstract PhaseId getCurrentPhaseId();

	/**
	 * @deprecated  Call {@link FacesContext#getELContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract ELContext getELContext();

	/**
	 * @deprecated  Call {@link FacesContext#getExceptionHandler()} instead.
	 */
	@Deprecated
	@Override
	public abstract ExceptionHandler getExceptionHandler();

	/**
	 * @deprecated  Call {@link FacesContext#getExternalContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract ExternalContext getExternalContext();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getFacesContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract FacesContext getFacesContext();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getLocale()} instead.
	 */
	@Deprecated
	@Override
	public abstract Locale getLocale();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getLocale()} instead.
	 */
	@Deprecated
	@Override
	public abstract Locale getLocale(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContext#getMaximumSeverity()} instead.
	 */
	@Deprecated
	@Override
	public abstract FacesMessage.Severity getMaximumSeverity();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(Locale, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(Locale locale, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(FacesContext facesContext, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(Locale, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(Locale locale, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(Locale, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(FacesContext facesContext, Locale locale, String messageId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getMessage(Locale, String, Object...)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments);

	/**
	 * @deprecated  Call {@link FacesContext#getMessageList()} instead.
	 */
	@Deprecated
	@Override
	public abstract List<FacesMessage> getMessageList();

	/**
	 * @deprecated  Call {@link FacesContext#getMessageList(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract List<FacesMessage> getMessageList(String clientId);

	/**
	 * @deprecated  Call {@link FacesContext#getMessages()} instead.
	 */
	@Deprecated
	@Override
	public abstract Iterator<FacesMessage> getMessages();

	/**
	 * @deprecated  Call {@link FacesContext#getMessages(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Iterator<FacesMessage> getMessages(String s);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getNamespace()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getNamespace();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getNamespace()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getNamespace(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContext#getNamingContainerSeparatorChar()} instead.
	 */
	@Deprecated
	@Override
	public abstract char getNamingContainerSeparatorChar();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getParentForm(UIComponent)} instead.
	 */
	@Deprecated
	@Override
	public abstract UIForm getParentForm(UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContext#getPartialViewContext()} instead.
	 */
	@Deprecated
	@Override
	public abstract PartialViewContext getPartialViewContext();

	/**
	 * @deprecated  Call {@link FacesContext#getRenderKit()} instead.
	 */
	@Deprecated
	@Override
	public abstract RenderKit getRenderKit();

	/**
	 * @deprecated  Call {@link FacesContext#getRenderResponse()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean getRenderResponse();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestAttribute(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getRequestAttribute(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestAttribute(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getRequestAttribute(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestContextPath()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestContextPath();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestContextPath()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestContextPath(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameter(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestParameter(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameter(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestParameter(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsBool(String, boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean getRequestParameterAsBool(String name, boolean defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsBool(String, boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean getRequestParameterAsBool(FacesContext facesContext, String name, boolean defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsInt(String, int)} instead.
	 */
	@Deprecated
	@Override
	public abstract int getRequestParameterAsInt(String name, int defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsInt(String, int)} instead.
	 */
	@Deprecated
	@Override
	public abstract int getRequestParameterAsInt(FacesContext facesContext, String name, int defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsLong(String, long)} instead.
	 */
	@Deprecated
	@Override
	public abstract long getRequestParameterAsLong(String name, long defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterAsLong(String, long)} instead.
	 */
	@Deprecated
	@Override
	public abstract long getRequestParameterAsLong(FacesContext facesContext, String name, long defaultValue);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterFromMap(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestParameterFromMap(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterFromMap(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestParameterFromMap(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterMap()} instead.
	 */
	@Deprecated
	@Override
	public abstract Map<String, String> getRequestParameterMap();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestParameterMap()} instead.
	 */
	@Deprecated
	@Override
	public abstract Map<String, String> getRequestParameterMap(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestQueryString()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestQueryString();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestQueryString()} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestQueryString(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestQueryStringParameter(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestQueryStringParameter(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getRequestQueryStringParameter(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract String getRequestQueryStringParameter(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContext#getResourceLibraryContracts()} instead.
	 */
	@Deprecated
	@Override
	public abstract List<String> getResourceLibraryContracts();

	/**
	 * @deprecated  Call {@link FacesContext#getResponseComplete()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean getResponseComplete();

	/**
	 * @deprecated  Call {@link FacesContext#getResponseStream()} instead.
	 */
	@Deprecated
	@Override
	public abstract ResponseStream getResponseStream();

	/**
	 * @deprecated  Call {@link FacesContext#getResponseWriter()} instead.
	 */
	@Deprecated
	@Override
	public abstract ResponseWriter getResponseWriter();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getSession(boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getSession(boolean create);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getSession(boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getSession(FacesContext facesContext, boolean create);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getSessionAttribute(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getSessionAttribute(String name);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#getSessionAttribute(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object getSessionAttribute(FacesContext facesContext, String name);

	/**
	 * @deprecated  Call {@link FacesContext#getViewRoot()} instead.
	 */
	@Deprecated
	@Override
	public abstract UIViewRoot getViewRoot();

	/**
	 * @deprecated  Call {@link FacesContext#isPostback()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isPostback();

	/**
	 * @deprecated  Call {@link FacesContext#isProcessingEvents()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isProcessingEvents();

	/**
	 * @deprecated  Call {@link FacesContext#isProjectStage(ProjectStage)} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isProjectStage(ProjectStage stage);

	/**
	 * @deprecated  Call {@link FacesContext#isReleased()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isReleased();

	/**
	 * @deprecated  Call {@link FacesContext#isValidationFailed()} instead.
	 */
	@Deprecated
	@Override
	public abstract boolean isValidationFailed();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#matchComponentInHierarchy(UIComponent, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#matchComponentInHierarchy(UIComponent, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent,
		String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#matchComponentInViewRoot(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract UIComponent matchComponentInViewRoot(String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#matchComponentInViewRoot(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract UIComponent matchComponentInViewRoot(FacesContext facesContext, String partialClientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#navigate(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void navigate(String fromAction, String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#navigate(String, String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void navigate(FacesContext facesContext, String fromAction, String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#navigateTo(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void navigateTo(String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#navigateTo(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void navigateTo(FacesContext facesContext, String outcome);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#recreateComponentTree()} instead.
	 */
	@Deprecated
	@Override
	public abstract void recreateComponentTree();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#recreateComponentTree()} instead.
	 */
	@Deprecated
	@Override
	public abstract void recreateComponentTree(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#registerPhaseListener(PhaseListener)} instead.
	 */
	@Deprecated
	@Override
	public abstract void registerPhaseListener(PhaseListener phaseListener);

	/**
	 * @deprecated  Call {@link FacesContext#release()} instead.
	 */
	@Deprecated
	@Override
	public abstract void release();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeChildrenFromComponentTree(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeChildrenFromComponentTree(String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeChildrenFromComponentTree(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeChildrenFromComponentTree(FacesContext facesContext, String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessages(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessages(String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessages(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessages(FacesContext facesContext, String clientId);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessagesForImmediateComponents()} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessagesForImmediateComponents();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessagesForImmediateComponents(UIComponent)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessagesForImmediateComponents(UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessagesForImmediateComponents()} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessagesForImmediateComponents(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeMessagesForImmediateComponents(UIComponent)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeMessagesForImmediateComponents(FacesContext facesContext, UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#removeParentFormFromComponentTree(UIComponent)} instead.
	 */
	@Deprecated
	@Override
	public abstract void removeParentFormFromComponentTree(UIComponent uiComponent);

	/**
	 * @deprecated  Call {@link FacesContext#renderResponse()} instead.
	 */
	@Deprecated
	@Override
	public abstract void renderResponse();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resetView()} instead.
	 */
	@Deprecated
	@Override
	public abstract void resetView();

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resetView(boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract void resetView(boolean renderResponse);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resetView()} instead.
	 */
	@Deprecated
	@Override
	public abstract void resetView(FacesContext facesContext);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resetView(boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract void resetView(FacesContext facesContext, boolean renderResponse);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resolveExpression(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object resolveExpression(String elExpression);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#resolveExpression(String)} instead.
	 */
	@Deprecated
	@Override
	public abstract Object resolveExpression(FacesContext facesContext, String elExpression);

	/**
	 * @deprecated  Call {@link FacesContext#responseComplete()} instead.
	 */
	@Deprecated
	@Override
	public abstract void responseComplete();

	/**
	 * @deprecated  Call {@link FacesContext#setCurrentPhaseId(PhaseId)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setCurrentPhaseId(PhaseId currentPhaseId);

	/**
	 * @deprecated  Call {@link FacesContext#setExceptionHandler(ExceptionHandler)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setExceptionHandler(ExceptionHandler exceptionHandler);

	/**
	 * @deprecated  Call {@link FacesContext#setProcessingEvents(boolean)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setProcessingEvents(boolean processingEvents);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#setRequestAttribute(String, Object)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setRequestAttribute(String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#setRequestAttribute(String, Object)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setRequestAttribute(FacesContext facesContext, String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContext#setResourceLibraryContracts(List)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setResourceLibraryContracts(List<String> contracts);

	/**
	 * @deprecated  Call {@link FacesContext#setResponseStream(ResponseStream)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setResponseStream(ResponseStream responseStream);

	/**
	 * @deprecated  Call {@link FacesContext#setResponseWriter(ResponseWriter)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setResponseWriter(ResponseWriter responseWriter);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#setSessionAttribute(String, Object)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setSessionAttribute(String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContextHelperUtil#setSessionAttribute(String, Object)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setSessionAttribute(FacesContext facesContext, String name, Object value);

	/**
	 * @deprecated  Call {@link FacesContext#setViewRoot(UIViewRoot)} instead.
	 */
	@Deprecated
	@Override
	public abstract void setViewRoot(UIViewRoot uiViewRoot);

	/**
	 * @deprecated  Call {@link FacesContext#validationFailed()} instead.
	 */
	@Deprecated
	@Override
	public abstract void validationFailed();
}
