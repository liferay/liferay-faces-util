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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;

import com.liferay.faces.util.client.Script;


/**
 * This class contains a set of static convenience methods that delegate to the corresponding methods on {@link
 * FacesContextHelper}.
 *
 * @author  Neil Griffin
 */
public final class FacesContextHelperUtil {

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public static void addComponentErrorMessage(String clientId, String messageId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentErrorMessage(clientId, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentErrorMessage(String clientId, String messageId, Object argument) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentErrorMessage(clientId, messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentErrorMessage(clientId, messageId, arguments);
	}

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public static void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentErrorMessage(facesContext, clientId, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object argument) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentErrorMessage(facesContext, clientId, messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentErrorMessage(facesContext, clientId, messageId, arguments);
	}

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public static void addComponentInfoMessage(String clientId, String messageId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentInfoMessage(clientId, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentInfoMessage(String clientId, String messageId, Object argument) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentInfoMessage(clientId, messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentInfoMessage(clientId, messageId, arguments);
	}

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public static void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentInfoMessage(facesContext, clientId, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object argument) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentInfoMessage(facesContext, clientId, messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addComponentInfoMessage(facesContext, clientId, messageId, arguments);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>.
	 */
	public static void addGlobalErrorMessage(String messageId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalErrorMessage(messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public static void addGlobalErrorMessage(String messageId, Object argument) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalErrorMessage(messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public static void addGlobalErrorMessage(String messageId, Object... arguments) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalErrorMessage(messageId, arguments);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>.
	 */
	public static void addGlobalErrorMessage(FacesContext facesContext, String messageId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalErrorMessage(facesContext, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public static void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object argument) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalErrorMessage(facesContext, messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public static void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object... arguments) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalErrorMessage(facesContext, messageId, arguments);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>.
	 */
	public static void addGlobalInfoMessage(String messageId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalInfoMessage(messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public static void addGlobalInfoMessage(String messageId, Object argument) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalInfoMessage(messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public static void addGlobalInfoMessage(String messageId, Object... arguments) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalInfoMessage(messageId, arguments);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>.
	 */
	public static void addGlobalInfoMessage(FacesContext facesContext, String messageId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalInfoMessage(facesContext, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public static void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object argument) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalInfoMessage(facesContext, messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public static void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object... arguments) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalInfoMessage(facesContext, messageId, arguments);
	}

	/**
	 * Adds a global information message to the current FacesContext with "your-request-processed-successfully" key as
	 * the messageId.
	 */
	public static void addGlobalSuccessInfoMessage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalSuccessInfoMessage();
	}

	/**
	 * Adds a global information message to the current FacesContext with "your-request-processed-successfully" key as
	 * the messageId.
	 */
	public static void addGlobalSuccessInfoMessage(FacesContext facesContext) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalSuccessInfoMessage(facesContext);
	}

	/**
	 * Adds a global error message to the current FacesContext with "an-unexpected-error-occurred" key as the messageId.
	 */
	public static void addGlobalUnexpectedErrorMessage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalUnexpectedErrorMessage();
	}

	/**
	 * Adds a global error message to the current FacesContext with "an-unexpected-error-occurred" key as the messageId.
	 */
	public static void addGlobalUnexpectedErrorMessage(FacesContext facesContext) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addGlobalUnexpectedErrorMessage(facesContext);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(String clientId, FacesMessage.Severity severity, String messageId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addMessage(clientId, severity, messageId);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(String clientId, FacesMessage.Severity severity, String messageId, Object argument) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addMessage(clientId, severity, messageId, argument);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(String clientId, FacesMessage.Severity severity, String messageId,
		Object... arguments) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addMessage(clientId, severity, messageId, arguments);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addMessage(facesContext, clientId, severity, messageId);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId, Object argument) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addMessage(facesContext, clientId, severity, messageId, argument);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(FacesContext facesContext, String clientId, FacesMessage.Severity severity,
		String messageId, Object... arguments) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addMessage(facesContext, clientId, severity, messageId, arguments);
	}

	/**
	 * Adds the specified {@link Script} to the list of scripts that are to be executed on the client.
	 *
	 * @param  script  The script that is to be added.
	 *
	 * @since  3.2
	 */
	public static void addScript(Script script) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addScript(script);
	}

	/**
	 * Adds the specified script to the list of scripts that are to be executed on the client.
	 *
	 * @param  scriptString  The script that is to be added.
	 *
	 * @since  3.2
	 */
	public static void addScript(String scriptString) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addScript(scriptString);
	}

	/**
	 * Adds the specified {@link Script} to the list of scripts that are to be executed on the client.
	 *
	 * @param  facesContext  The current faces context.
	 * @param  script        The script that is to be added.
	 *
	 * @since  3.2
	 */
	public static void addScript(FacesContext facesContext, Script script) {

		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addScript(facesContext, script);
	}

	/**
	 * Adds the specified script to the list of scripts that are to be executed on the client.
	 *
	 * @param  facesContext  The current faces context.
	 * @param  scriptString  The script that is to be added.
	 *
	 * @since  3.2
	 */
	public static void addScript(FacesContext facesContext, String scriptString) {

		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.addScript(facesContext, scriptString);
	}

	/**
	 * Returns the locale of the viewRoot of the current JSF FacesContext
	 */
	public static Locale getLocale() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getLocale();
	}

	/**
	 * Returns the locale of the viewRoot of the current JSF FacesContext
	 */
	public static Locale getLocale(FacesContext facesContext) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getLocale(facesContext);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale.
	 */
	public static String getMessage(String messageId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getMessage(messageId);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale and arguments that are to be substituted.
	 */
	public static String getMessage(String messageId, Object... arguments) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getMessage(messageId, arguments);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale.
	 */
	public static String getMessage(Locale locale, String messageId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getMessage(locale, messageId);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale.
	 */
	public static String getMessage(FacesContext facesContext, String messageId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getMessage(facesContext, messageId);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale and arguments that are to be substituted.
	 */
	public static String getMessage(Locale locale, String messageId, Object... arguments) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getMessage(locale, messageId, arguments);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale and arguments that are to be substituted.
	 */
	public static String getMessage(FacesContext facesContext, String messageId, Object... arguments) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getMessage(facesContext, messageId, arguments);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale.
	 */
	public static String getMessage(FacesContext facesContext, Locale locale, String messageId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getMessage(facesContext, locale, messageId);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale and arguments that are to be substituted.
	 */
	public static String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getMessage(facesContext, locale, messageId, arguments);
	}

	/**
	 * Delegates to the underlying {@link ExternalContext#encodeNamespace(String)} method in order to get the
	 * application namespace.
	 */
	public static String getNamespace() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getNamespace();
	}

	/**
	 * Delegates to the underlying {@link ExternalContext#encodeNamespace(String)} method in order to get the
	 * application namespace.
	 */
	public static String getNamespace(FacesContext facesContext) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getNamespace(facesContext);
	}

	/**
	 * Returns the parent form of the given component or <code>null</code> if no parent form is found.
	 *
	 * @param  uiComponent  The component whose parent is to be found.
	 */
	public static UIForm getParentForm(UIComponent uiComponent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getParentForm(uiComponent);
	}

	/**
	 * Returns the value of the request attribute associated with the specified name.
	 */
	public static Object getRequestAttribute(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestAttribute(name);
	}

	/**
	 * Returns the value of the request attribute associated with the specified name.
	 */
	public static Object getRequestAttribute(FacesContext facesContext, String name) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestAttribute(facesContext, name);
	}

	/**
	 * Returns The request context path. {@link FacesContext#getExternalContext()} {@link
	 * ExternalContext#getRequestContextPath()}
	 */
	public static String getRequestContextPath() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestContextPath();
	}

	/**
	 * Returns The request context path. {@link FacesContext#getExternalContext()} {@link
	 * ExternalContext#getRequestContextPath()}
	 */
	public static String getRequestContextPath(FacesContext facesContext) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestContextPath(facesContext);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request
	 */
	public static String getRequestParameter(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameter(name);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request
	 */
	public static String getRequestParameter(FacesContext facesContext, String name) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameter(facesContext, name);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as a boolean. The values "yes", "true", "y", and
	 * "1" are accetable values for "TRUE".
	 */
	public static boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameterAsBool(name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as a boolean. The values "yes", "true", "y", and
	 * "1" are accetable values for "TRUE".
	 */
	public static boolean getRequestParameterAsBool(FacesContext facesContext, String name, boolean defaultValue) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameterAsBool(facesContext, name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public static int getRequestParameterAsInt(String name, int defaultValue) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameterAsInt(name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public static int getRequestParameterAsInt(FacesContext facesContext, String name, int defaultValue) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameterAsInt(facesContext, name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public static long getRequestParameterAsLong(String name, long defaultValue) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameterAsLong(name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public static long getRequestParameterAsLong(FacesContext facesContext, String name, long defaultValue) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameterAsLong(facesContext, name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter from the ExternalContext's request parameter map.
	 */
	public static String getRequestParameterFromMap(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameterFromMap(name);
	}

	/**
	 * Retrieves the specified parameter from the ExternalContext's request parameter map.
	 */
	public static String getRequestParameterFromMap(FacesContext facesContext, String name) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameterFromMap(facesContext, name);
	}

	/**
	 * Returns the map of request parameters from the ExternalContext.
	 *
	 * @see  ExternalContext#getRequestParameterMap()
	 */
	public static Map<String, String> getRequestParameterMap() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameterMap();
	}

	/**
	 * Returns the map of request parameters from the ExternalContext.
	 *
	 * @see  ExternalContext#getRequestParameterMap()
	 */
	public static Map<String, String> getRequestParameterMap(FacesContext facesContext) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestParameterMap(facesContext);
	}

	/**
	 * Retrieves the value of the original "javax.servlet.forward.query_string" request attribute.
	 */
	public static String getRequestQueryString() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestQueryString();
	}

	/**
	 * Retrieves the value of the original "javax.servlet.forward.query_string" request attribute.
	 */
	public static String getRequestQueryString(FacesContext facesContext) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestQueryString(facesContext);
	}

	/**
	 * Retrieves the value of the specified parameter name from the original "javax.servlet.forward.query_string"
	 * request attribute.
	 */
	public static String getRequestQueryStringParameter(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestQueryStringParameter(name);
	}

	/**
	 * Retrieves the value of the specified parameter name from the original "javax.servlet.forward.query_string"
	 * request attribute.
	 */
	public static String getRequestQueryStringParameter(FacesContext facesContext, String name) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getRequestQueryStringParameter(facesContext, name);
	}

	/**
	 * Returns an immutable list of scripts that were added via the {@link #addScript(FacesContext,Script)} or {@link
	 * #addScript(FacesContext,String)} method.
	 *
	 * @since  3.2
	 */
	public static List<Script> getScripts() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getScripts();
	}

	/**
	 * Returns an immutable list of scripts that were added via the {@link #addScript(FacesContext,Script)} or {@link
	 * #addScript(FacesContext,String)} method.
	 *
	 * @param  facesContext  The current faces context.
	 *
	 * @since  3.2
	 */
	public static List<Script> getScripts(FacesContext facesContext) {

		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getScripts();
	}

	/**
	 * Returns the session object associated with the current FacesContext.
	 *
	 * @param  create  Flag indicating whether or not a session should be created if it doesn't yet exist.
	 */
	public static Object getSession(boolean create) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getSession(create);
	}

	/**
	 * Returns the session object associated with the current FacesContext.
	 *
	 * @param  create  Flag indicating whether or not a session should be created if it doesn't yet exist.
	 */
	public static Object getSession(FacesContext facesContext, boolean create) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getSession(facesContext, create);
	}

	/**
	 * Returns the value of the session attribute associated with the specified name.
	 */
	public static Object getSessionAttribute(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getSessionAttribute(name);
	}

	/**
	 * Returns the value of the session attribute associated with the specified name.
	 */
	public static Object getSessionAttribute(FacesContext facesContext, String name) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.getSessionAttribute(facesContext, name);
	}

	/**
	 * Traverses the component tree starting at the specified UIComponent parent and returns the first UIComponent child
	 * that contains the specified partialClientId.
	 */
	public static UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.matchComponentInHierarchy(parent, partialClientId);
	}

	/**
	 * Traverses the component tree starting at the specified UIComponent parent and returns the first UIComponent child
	 * that contains the specified partialClientId.
	 */
	public static UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent,
		String partialClientId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.matchComponentInHierarchy(facesContext, parent, partialClientId);
	}

	/**
	 * Traverses the component tree associated with the UIViewRoot of this FacesContext and returns the first
	 * UIComponent child that contains the specified partialClientId.
	 */
	public static UIComponent matchComponentInViewRoot(String partialClientId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.matchComponentInViewRoot(partialClientId);
	}

	/**
	 * Traverses the component tree associated with the UIViewRoot of this FacesContext and returns the first
	 * UIComponent child that contains the specified partialClientId.
	 */
	public static UIComponent matchComponentInViewRoot(FacesContext facesContext, String partialClientId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.matchComponentInViewRoot(facesContext, partialClientId);
	}

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  fromAction  The "from action" as specified in a JSF navigation rule. Can be null to if no action is
	 *                     specified in the rule.
	 * @param  outcome     The "from outcome" as specified in a JSF navigation rule
	 */
	public static void navigate(String fromAction, String outcome) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.navigate(fromAction, outcome);
	}

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  fromAction  The "from action" as specified in a JSF navigation rule. Can be null to if no action is
	 *                     specified in the rule.
	 * @param  outcome     The "from outcome" as specified in a JSF navigation rule
	 */
	public static void navigate(FacesContext facesContext, String fromAction, String outcome) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.navigate(facesContext, fromAction, outcome);
	}

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  outcome  The "from outcome" as specified in a JSF navigation rule
	 */
	public static void navigateTo(String outcome) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.navigateTo(outcome);
	}

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  outcome  The "from outcome" as specified in a JSF navigation rule
	 */
	public static void navigateTo(FacesContext facesContext, String outcome) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.navigateTo(facesContext, outcome);
	}

	/**
	 * Delete the whole component tree. This causes the tree to be rebuilt the next time it is accessed. This addresses
	 * the problem that immediate actions cannot change the values of input components. To clear these values, use this
	 * method.
	 */
	public static void recreateComponentTree() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.recreateComponentTree();
	}

	/**
	 * Delete the whole component tree. This causes the tree to be rebuilt the next time it is accessed. This addresses
	 * the problem that immediate actions cannot change the values of input components. To clear these values, use this
	 * method.
	 */
	public static void recreateComponentTree(FacesContext facesContext) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.recreateComponentTree(facesContext);
	}

	/**
	 * Register a {@link PhaseListener} programatically (instead of in the faces-config.xml). Such a PhaseListener can
	 * therefore be controlled by spring and use dependency injection, which is not possible otherwise.
	 */
	public static void registerPhaseListener(PhaseListener phaseListener) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.registerPhaseListener(phaseListener);
	}

	/**
	 * clear component tree since input fields will not be refreshed. See <a
	 * href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a> for more information.
	 *
	 * @param  clientId  all children of the component with this id are cleared.
	 */
	public static void removeChildrenFromComponentTree(String clientId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.removeChildrenFromComponentTree(clientId);
	}

	/**
	 * clear component tree since input fields will not be refreshed. See <a
	 * href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a> for more information.
	 *
	 * @param  clientId  all children of the component with this id are cleared.
	 */
	public static void removeChildrenFromComponentTree(FacesContext facesContext, String clientId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.removeChildrenFromComponentTree(facesContext, clientId);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with the specified clientId.
	 */
	public static void removeMessages(String clientId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.removeMessages(clientId);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with the specified clientId.
	 */
	public static void removeMessages(FacesContext facesContext, String clientId) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.removeMessages(facesContext, clientId);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public static void removeMessagesForImmediateComponents() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.removeMessagesForImmediateComponents();
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public static void removeMessagesForImmediateComponents(UIComponent uiComponent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.removeMessagesForImmediateComponents(uiComponent);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public static void removeMessagesForImmediateComponents(FacesContext facesContext) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.removeMessagesForImmediateComponents(facesContext);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public static void removeMessagesForImmediateComponents(FacesContext facesContext, UIComponent uiComponent) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.removeMessagesForImmediateComponents(facesContext, uiComponent);
	}

	/**
	 * Delete the component subtree of a given component. This causes the subtree to be rebuilt the next time it is
	 * accessed. This addresses the problem that immediate actions cannot change the values of input components. To
	 * clear these values, use this method.
	 */
	public static void removeParentFormFromComponentTree(UIComponent uiComponent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.removeParentFormFromComponentTree(uiComponent);
	}

	/**
	 * Causes the current view's component tree to be discarded and re-rendered.
	 *
	 * @see  FacesContextHelper#resetView(boolean)
	 */
	public static void resetView() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.resetView();
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
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.resetView(renderResponse);
	}

	/**
	 * Causes the current view's component tree to be discarded and re-rendered.
	 *
	 * @see  FacesContextHelper#resetView(boolean)
	 */
	public static void resetView(FacesContext facesContext) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.resetView(facesContext);
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
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.resetView(facesContext, renderResponse);
	}

	/**
	 * Returns the object associated with the specified EL expression.
	 */
	public static Object resolveExpression(String elExpression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.resolveExpression(elExpression);
	}

	/**
	 * Returns the object associated with the specified EL expression.
	 */
	public static Object resolveExpression(FacesContext facesContext, String elExpression) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelper.resolveExpression(facesContext, elExpression);
	}

	/**
	 * Sets the value of the a request attribute using the specified name and value.
	 */
	public static void setRequestAttribute(String name, Object value) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.setRequestAttribute(name, value);
	}

	/**
	 * Sets the value of the a request attribute using the specified name and value.
	 */
	public static void setRequestAttribute(FacesContext facesContext, String name, Object value) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.setRequestAttribute(facesContext, name, value);
	}

	/**
	 * Sets the value of the a session attribute using the specified name and value.
	 */
	public static void setSessionAttribute(String name, Object value) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.setSessionAttribute(name, value);
	}

	/**
	 * Sets the value of the a session attribute using the specified name and value.
	 */
	public static void setSessionAttribute(FacesContext facesContext, String name, Object value) {
		FacesContextHelper facesContextHelper = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());
		facesContextHelper.setSessionAttribute(facesContext, name, value);
	}

	/**
	 * Gets the underlying/wrapped FacesContext ThreadLocal singleton instance.
	 *
	 * @deprecated  Call {@link FacesContext#getCurrentInstance()} instead.
	 */
	@Deprecated
	public FacesContext getFacesContext() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesContextHelper facesContextHelperInstance = FacesContextHelperFactory.getFacesContextHelperInstance(
				facesContext.getExternalContext());

		return facesContextHelperInstance.getFacesContext();
	}
}
