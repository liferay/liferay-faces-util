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

import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;


/**
 * This class contains a set of static convenience methods that delegate to the corresponding methods on {@link
 * FacesContextHelper}.
 *
 * @author  Neil Griffin
 */
public class FacesContextHelperUtil {

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public static void addComponentErrorMessage(String clientId, String messageId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentErrorMessage(clientId, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentErrorMessage(String clientId, String messageId, Object argument) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentErrorMessage(clientId, messageId,
			argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentErrorMessage(clientId, messageId,
			arguments);
	}

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public static void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentErrorMessage(facesContext, clientId,
			messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object argument) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentErrorMessage(facesContext, clientId,
			messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentErrorMessage(facesContext, clientId,
			messageId, arguments);
	}

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public static void addComponentInfoMessage(String clientId, String messageId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentInfoMessage(clientId, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentInfoMessage(String clientId, String messageId, Object argument) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentInfoMessage(clientId, messageId,
			argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentInfoMessage(clientId, messageId,
			arguments);
	}

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public static void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentInfoMessage(facesContext, clientId,
			messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object argument) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentInfoMessage(facesContext, clientId,
			messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addComponentInfoMessage(facesContext, clientId,
			messageId, arguments);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>.
	 */
	public static void addGlobalErrorMessage(String messageId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalErrorMessage(messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public static void addGlobalErrorMessage(String messageId, Object argument) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalErrorMessage(messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public static void addGlobalErrorMessage(String messageId, Object... arguments) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalErrorMessage(messageId, arguments);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>.
	 */
	public static void addGlobalErrorMessage(FacesContext facesContext, String messageId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalErrorMessage(facesContext, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public static void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object argument) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalErrorMessage(facesContext, messageId,
			argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public static void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object... arguments) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalErrorMessage(facesContext, messageId,
			arguments);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>.
	 */
	public static void addGlobalInfoMessage(String messageId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalInfoMessage(messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public static void addGlobalInfoMessage(String messageId, Object argument) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalInfoMessage(messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public static void addGlobalInfoMessage(String messageId, Object... arguments) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalInfoMessage(messageId, arguments);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>.
	 */
	public static void addGlobalInfoMessage(FacesContext facesContext, String messageId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalInfoMessage(facesContext, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public static void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object argument) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalInfoMessage(facesContext, messageId,
			argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public static void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object... arguments) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalInfoMessage(facesContext, messageId,
			arguments);
	}

	/**
	 * Adds a global information message to the current FacesContext with "your-request-processed-successfully" key as
	 * the messageId.
	 */
	public static void addGlobalSuccessInfoMessage() {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalSuccessInfoMessage();
	}

	/**
	 * Adds a global information message to the current FacesContext with "your-request-processed-successfully" key as
	 * the messageId.
	 */
	public static void addGlobalSuccessInfoMessage(FacesContext facesContext) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalSuccessInfoMessage(facesContext);
	}

	/**
	 * Adds a global error message to the current FacesContext with "an-unexpected-error-occurred" key as the messageId.
	 */
	public static void addGlobalUnexpectedErrorMessage() {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalUnexpectedErrorMessage();
	}

	/**
	 * Adds a global error message to the current FacesContext with "an-unexpected-error-occurred" key as the messageId.
	 */
	public static void addGlobalUnexpectedErrorMessage(FacesContext facesContext) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addGlobalUnexpectedErrorMessage(facesContext);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(String clientId, FacesMessage.Severity severity, String messageId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addMessage(clientId, severity, messageId);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(String clientId, FacesMessage.Severity severity, String messageId, Object argument) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addMessage(clientId, severity, messageId, argument);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(String clientId, FacesMessage.Severity severity, String messageId,
		Object... arguments) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addMessage(clientId, severity, messageId, arguments);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addMessage(facesContext, clientId, severity,
			messageId);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId, Object argument) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addMessage(facesContext, clientId, severity,
			messageId, argument);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId, Object... arguments) {
		FacesContextHelperFactory.getFacesContextHelperInstance().addMessage(facesContext, clientId, severity,
			messageId, arguments);
	}

	/**
	 * Returns the locale of the viewRoot of the current JSF FacesContext
	 */
	public static Locale getLocale() {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getLocale();
	}

	/**
	 * Returns the locale of the viewRoot of the current JSF FacesContext
	 */
	public static Locale getLocale(FacesContext facesContext) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getLocale(facesContext);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale.
	 */
	public static String getMessage(String messageId) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getMessage(messageId);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale and arguments that are to be substituted.
	 */
	public static String getMessage(String messageId, Object... arguments) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getMessage(messageId, arguments);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale.
	 */
	public static String getMessage(Locale locale, String messageId) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getMessage(locale, messageId);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale.
	 */
	public static String getMessage(FacesContext facesContext, String messageId) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getMessage(facesContext, messageId);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale and arguments that are to be substituted.
	 */
	public static String getMessage(Locale locale, String messageId, Object... arguments) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getMessage(locale, messageId, arguments);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale and arguments that are to be substituted.
	 */
	public static String getMessage(FacesContext facesContext, String messageId, Object... arguments) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getMessage(facesContext, messageId, arguments);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale.
	 */
	public static String getMessage(FacesContext facesContext, Locale locale, String messageId) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getMessage(facesContext, locale, messageId);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale and arguments that are to be substituted.
	 */
	public static String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getMessage(facesContext, locale, messageId,
				arguments);
	}

	/**
	 * Delegates to the underlying {@link ExternalContext#encodeNamespace(String)} method in order to get the
	 * application namespace.
	 */
	public static String getNamespace() {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getNamespace();
	}

	/**
	 * Delegates to the underlying {@link ExternalContext#encodeNamespace(String)} method in order to get the
	 * application namespace.
	 */
	public static String getNamespace(FacesContext facesContext) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getNamespace(facesContext);
	}

	/**
	 * Returns the parent form of the given component or <code>null</code> if no parent form is found.
	 *
	 * @param  uiComponent  The component whose parent is to be found.
	 */
	public static UIForm getParentForm(UIComponent uiComponent) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getParentForm(uiComponent);
	}

	/**
	 * Returns the value of the request attribute associated with the specified name.
	 */
	public static Object getRequestAttribute(String name) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestAttribute(name);
	}

	/**
	 * Returns the value of the request attribute associated with the specified name.
	 */
	public static Object getRequestAttribute(FacesContext facesContext, String name) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestAttribute(facesContext, name);
	}

	/**
	 * Returns The request context path. {@link FacesContext#getExternalContext()} {@link
	 * ExternalContext#getRequestContextPath()}
	 */
	public static String getRequestContextPath() {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestContextPath();
	}

	/**
	 * Returns The request context path. {@link FacesContext#getExternalContext()} {@link
	 * ExternalContext#getRequestContextPath()}
	 */
	public static String getRequestContextPath(FacesContext facesContext) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestContextPath(facesContext);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request
	 */
	public static String getRequestParameter(String name) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameter(name);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request
	 */
	public static String getRequestParameter(FacesContext facesContext, String name) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameter(facesContext, name);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as a boolean. The values "yes", "true", "y", and
	 * "1" are accetable values for "TRUE".
	 */
	public static boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameterAsBool(name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as a boolean. The values "yes", "true", "y", and
	 * "1" are accetable values for "TRUE".
	 */
	public static boolean getRequestParameterAsBool(FacesContext facesContext, String name, boolean defaultValue) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameterAsBool(facesContext, name,
				defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public static int getRequestParameterAsInt(String name, int defaultValue) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameterAsInt(name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public static int getRequestParameterAsInt(FacesContext facesContext, String name, int defaultValue) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameterAsInt(facesContext, name,
				defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public static long getRequestParameterAsLong(String name, long defaultValue) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameterAsLong(name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public static long getRequestParameterAsLong(FacesContext facesContext, String name, long defaultValue) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameterAsLong(facesContext, name,
				defaultValue);
	}

	/**
	 * Retrieves the specified parameter from the ExternalContext's request parameter map.
	 */
	public static String getRequestParameterFromMap(String name) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameterFromMap(name);
	}

	/**
	 * Retrieves the specified parameter from the ExternalContext's request parameter map.
	 */
	public static String getRequestParameterFromMap(FacesContext facesContext, String name) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameterFromMap(facesContext, name);
	}

	/**
	 * Returns the map of request parameters from the ExternalContext.
	 *
	 * @see  {@link ExternalContext#getRequestParameterMap()}.
	 */
	public static Map<String, String> getRequestParameterMap() {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameterMap();
	}

	/**
	 * Returns the map of request parameters from the ExternalContext.
	 *
	 * @see  {@link ExternalContext#getRequestParameterMap()}.
	 */
	public static Map<String, String> getRequestParameterMap(FacesContext facesContext) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestParameterMap(facesContext);
	}

	/**
	 * Retrieves the value of the original "javax.servlet.forward.query_string" request attribute.
	 */
	public static String getRequestQueryString() {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestQueryString();
	}

	/**
	 * Retrieves the value of the original "javax.servlet.forward.query_string" request attribute.
	 */
	public static String getRequestQueryString(FacesContext facesContext) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestQueryString(facesContext);
	}

	/**
	 * Retrieves the value of the specified parameter name from the original "javax.servlet.forward.query_string"
	 * request attribute.
	 */
	public static String getRequestQueryStringParameter(String name) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestQueryStringParameter(name);
	}

	/**
	 * Retrieves the value of the specified parameter name from the original "javax.servlet.forward.query_string"
	 * request attribute.
	 */
	public static String getRequestQueryStringParameter(FacesContext facesContext, String name) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getRequestQueryStringParameter(facesContext,
				name);
	}

	/**
	 * Returns the session object associated with the current FacesContext.
	 *
	 * @param  create  Flag indicating whether or not a session should be created if it doesn't yet exist.
	 */
	public static Object getSession(boolean create) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getSession(create);
	}

	/**
	 * Returns the session object associated with the current FacesContext.
	 *
	 * @param  create  Flag indicating whether or not a session should be created if it doesn't yet exist.
	 */
	public static Object getSession(FacesContext facesContext, boolean create) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getSession(facesContext, create);
	}

	/**
	 * Returns the value of the session attribute associated with the specified name.
	 */
	public static Object getSessionAttribute(String name) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getSessionAttribute(name);
	}

	/**
	 * Returns the value of the session attribute associated with the specified name.
	 */
	public static Object getSessionAttribute(FacesContext facesContext, String name) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getSessionAttribute(facesContext, name);
	}

	/**
	 * Traverses the component tree starting at the specified UIComponent parent and returns the first UIComponent child
	 * that contains the specified partialClientId.
	 */
	public static UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().matchComponentInHierarchy(parent,
				partialClientId);
	}

	/**
	 * Traverses the component tree starting at the specified UIComponent parent and returns the first UIComponent child
	 * that contains the specified partialClientId.
	 */
	public static UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent,
		String partialClientId) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().matchComponentInHierarchy(facesContext, parent,
				partialClientId);
	}

	/**
	 * Traverses the component tree associated with the UIViewRoot of this FacesContext and returns the first
	 * UIComponent child that contains the specified partialClientId.
	 */
	public static UIComponent matchComponentInViewRoot(String partialClientId) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().matchComponentInViewRoot(partialClientId);
	}

	/**
	 * Traverses the component tree associated with the UIViewRoot of this FacesContext and returns the first
	 * UIComponent child that contains the specified partialClientId.
	 */
	public static UIComponent matchComponentInViewRoot(FacesContext facesContext, String partialClientId) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().matchComponentInViewRoot(facesContext,
				partialClientId);
	}

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  fromAction  The "from action" as specified in a JSF navigation rule. Can be null to if no action is
	 *                     specified in the rule.
	 * @param  outcome     The "from outcome" as specified in a JSF navigation rule
	 */
	public static void navigate(String fromAction, String outcome) {
		FacesContextHelperFactory.getFacesContextHelperInstance().navigate(fromAction, outcome);
	}

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  fromAction  The "from action" as specified in a JSF navigation rule. Can be null to if no action is
	 *                     specified in the rule.
	 * @param  outcome     The "from outcome" as specified in a JSF navigation rule
	 */
	public static void navigate(FacesContext facesContext, String fromAction, String outcome) {
		FacesContextHelperFactory.getFacesContextHelperInstance().navigate(facesContext, fromAction, outcome);
	}

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  outcome  The "from outcome" as specified in a JSF navigation rule
	 */
	public static void navigateTo(String outcome) {
		FacesContextHelperFactory.getFacesContextHelperInstance().navigateTo(outcome);
	}

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  outcome  The "from outcome" as specified in a JSF navigation rule
	 */
	public static void navigateTo(FacesContext facesContext, String outcome) {
		FacesContextHelperFactory.getFacesContextHelperInstance().navigateTo(facesContext, outcome);
	}

	/**
	 * Delete the whole component tree. This causes the tree to be rebuilt the next time it is accessed. This addresses
	 * the problem that immediate actions cannot change the values of input components. To clear these values, use this
	 * method.
	 */
	public static void recreateComponentTree() {
		FacesContextHelperFactory.getFacesContextHelperInstance().recreateComponentTree();
	}

	/**
	 * Delete the whole component tree. This causes the tree to be rebuilt the next time it is accessed. This addresses
	 * the problem that immediate actions cannot change the values of input components. To clear these values, use this
	 * method.
	 */
	public static void recreateComponentTree(FacesContext facesContext) {
		FacesContextHelperFactory.getFacesContextHelperInstance().recreateComponentTree(facesContext);
	}

	/**
	 * Register a {@link PhaseListener} programatically (instead of in the faces-config.xml). Such a PhaseListener can
	 * therefore be controlled by spring and use dependency injection, which is not possible otherwise.
	 */
	public static void registerPhaseListener(PhaseListener phaseListener) {
		FacesContextHelperFactory.getFacesContextHelperInstance().registerPhaseListener(phaseListener);
	}

	/**
	 * clear component tree since input fields will not be refreshed. See <a
	 * href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a> for more information.
	 *
	 * @param  clientId  all children of the component with this id are cleared.
	 */
	public static void removeChildrenFromComponentTree(String clientId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().removeChildrenFromComponentTree(clientId);
	}

	/**
	 * clear component tree since input fields will not be refreshed. See <a
	 * href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a> for more information.
	 *
	 * @param  clientId  all children of the component with this id are cleared.
	 */
	public static void removeChildrenFromComponentTree(FacesContext facesContext, String clientId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().removeChildrenFromComponentTree(facesContext,
			clientId);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with the specified clientId.
	 */
	public static void removeMessages(String clientId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().removeMessages(clientId);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with the specified clientId.
	 */
	public static void removeMessages(FacesContext facesContext, String clientId) {
		FacesContextHelperFactory.getFacesContextHelperInstance().removeMessages(facesContext, clientId);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public static void removeMessagesForImmediateComponents() {
		FacesContextHelperFactory.getFacesContextHelperInstance().removeMessagesForImmediateComponents();
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public static void removeMessagesForImmediateComponents(UIComponent uiComponent) {
		FacesContextHelperFactory.getFacesContextHelperInstance().removeMessagesForImmediateComponents(uiComponent);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public static void removeMessagesForImmediateComponents(FacesContext facesContext) {
		FacesContextHelperFactory.getFacesContextHelperInstance().removeMessagesForImmediateComponents(facesContext);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public static void removeMessagesForImmediateComponents(FacesContext facesContext, UIComponent uiComponent) {
		FacesContextHelperFactory.getFacesContextHelperInstance().removeMessagesForImmediateComponents(facesContext,
			uiComponent);
	}

	/**
	 * Delete the component subtree of a given component. This causes the subtree to be rebuilt the next time it is
	 * accessed. This addresses the problem that immediate actions cannot change the values of input components. To
	 * clear these values, use this method.
	 */
	public static void removeParentFormFromComponentTree(UIComponent uiComponent) {
		FacesContextHelperFactory.getFacesContextHelperInstance().removeParentFormFromComponentTree(uiComponent);
	}

	/**
	 * Causes the current view's component tree to be discarded and re-rendered.
	 *
	 * @see  {@link FacesContextHelper#resetView(boolean)}
	 */
	public static void resetView() {
		FacesContextHelperFactory.getFacesContextHelperInstance().resetView();
	}

	/**
	 * Causes the current view's component tree to be discarded and (optionally) re-rendered. This is useful whenever an
	 * action causes navigation back to the current view, but the data in the backing bean(s) has changed substantially.
	 * The view is rendered as if the user is visiting for the first time.
	 *
	 * @param  renderResponse  causes the response to be rendered immediately if true.
	 *
	 * @see    <a href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a>
	 */
	public static void resetView(boolean renderResponse) {
		FacesContextHelperFactory.getFacesContextHelperInstance().resetView(renderResponse);
	}

	/**
	 * Causes the current view's component tree to be discarded and re-rendered.
	 *
	 * @see  {@link FacesContextHelper#resetView(boolean)}
	 */
	public static void resetView(FacesContext facesContext) {
		FacesContextHelperFactory.getFacesContextHelperInstance().resetView(facesContext);
	}

	/**
	 * Causes the current view's component tree to be discarded and (optionally) re-rendered. This is useful whenever an
	 * action causes navigation back to the current view, but the data in the backing bean(s) has changed substantially.
	 * The view is rendered as if the user is visiting for the first time.
	 *
	 * @param  renderResponse  causes the response to be rendered immediately if true.
	 *
	 * @see    <a href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a>
	 */
	public static void resetView(FacesContext facesContext, boolean renderResponse) {
		FacesContextHelperFactory.getFacesContextHelperInstance().resetView(facesContext, renderResponse);
	}

	/**
	 * Returns the object associated with the specified EL expression.
	 */
	public static Object resolveExpression(String elExpression) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().resolveExpression(elExpression);
	}

	/**
	 * Returns the object associated with the specified EL expression.
	 */
	public static Object resolveExpression(FacesContext facesContext, String elExpression) {
		return FacesContextHelperFactory.getFacesContextHelperInstance().resolveExpression(facesContext, elExpression);
	}

	/**
	 * Sets the value of the a request attribute using the specified name and value.
	 */
	public static void setRequestAttribute(String name, Object value) {
		FacesContextHelperFactory.getFacesContextHelperInstance().setRequestAttribute(name, value);
	}

	/**
	 * Sets the value of the a request attribute using the specified name and value.
	 */
	public static void setRequestAttribute(FacesContext facesContext, String name, Object value) {
		FacesContextHelperFactory.getFacesContextHelperInstance().setRequestAttribute(facesContext, name, value);
	}

	/**
	 * Sets the value of the a session attribute using the specified name and value.
	 */
	public static void setSessionAttribute(String name, Object value) {
		FacesContextHelperFactory.getFacesContextHelperInstance().setSessionAttribute(name, value);
	}

	/**
	 * Sets the value of the a session attribute using the specified name and value.
	 */
	public static void setSessionAttribute(FacesContext facesContext, String name, Object value) {
		FacesContextHelperFactory.getFacesContextHelperInstance().setSessionAttribute(facesContext, name, value);
	}

	/**
	 * Gets the underlying/wrapped FacesContext ThreadLocal singleton instance.
	 */
	public FacesContext getFacesContext() {
		return FacesContextHelperFactory.getFacesContextHelperInstance().getFacesContext();
	}
}
