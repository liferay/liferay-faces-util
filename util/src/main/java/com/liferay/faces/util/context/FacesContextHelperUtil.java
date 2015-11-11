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
		FacesContextHelperFactory.getInstance().addComponentErrorMessage(clientId, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentErrorMessage(String clientId, String messageId, Object argument) {
		FacesContextHelperFactory.getInstance().addComponentErrorMessage(clientId, messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentErrorMessage(String clientId, String messageId, Object... arguments) {
		FacesContextHelperFactory.getInstance().addComponentErrorMessage(clientId, messageId, arguments);
	}

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public static void addComponentInfoMessage(String clientId, String messageId) {
		FacesContextHelperFactory.getInstance().addComponentInfoMessage(clientId, messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentInfoMessage(String clientId, String messageId, Object argument) {
		FacesContextHelperFactory.getInstance().addComponentInfoMessage(clientId, messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public static void addComponentInfoMessage(String clientId, String messageId, Object... arguments) {
		FacesContextHelperFactory.getInstance().addComponentInfoMessage(clientId, messageId, arguments);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>.
	 */
	public static void addGlobalErrorMessage(String messageId) {
		FacesContextHelperFactory.getInstance().addGlobalErrorMessage(messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public static void addGlobalErrorMessage(String messageId, Object argument) {
		FacesContextHelperFactory.getInstance().addGlobalErrorMessage(messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public static void addGlobalErrorMessage(String messageId, Object... arguments) {
		FacesContextHelperFactory.getInstance().addGlobalErrorMessage(messageId, arguments);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>.
	 */
	public static void addGlobalInfoMessage(String messageId) {
		FacesContextHelperFactory.getInstance().addGlobalInfoMessage(messageId);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument for the tokens
	 * in the specified key.
	 */
	public static void addGlobalInfoMessage(String messageId, Object argument) {
		FacesContextHelperFactory.getInstance().addGlobalInfoMessage(messageId, argument);
	}

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public static void addGlobalInfoMessage(String messageId, Object... arguments) {
		FacesContextHelperFactory.getInstance().addGlobalInfoMessage(messageId, arguments);
	}

	/**
	 * Adds a global information message to the current FacesContext with "your-request-processed-successfully" key as
	 * the messageId.
	 */
	public static void addGlobalSuccessInfoMessage() {
		FacesContextHelperFactory.getInstance().addGlobalSuccessInfoMessage();
	}

	/**
	 * Adds a global error message to the current FacesContext with "an-unexpected-error-occurred" key as the messageId.
	 */
	public static void addGlobalUnexpectedErrorMessage() {
		FacesContextHelperFactory.getInstance().addGlobalUnexpectedErrorMessage();
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(String clientId, FacesMessage.Severity severity, String messageId) {
		FacesContextHelperFactory.getInstance().addMessage(clientId, severity, messageId);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(String clientId, FacesMessage.Severity severity, String messageId, Object argument) {
		FacesContextHelperFactory.getInstance().addMessage(clientId, severity, messageId, argument);
	}

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public static void addMessage(String clientId, FacesMessage.Severity severity, String messageId,
		Object... arguments) {
		FacesContextHelperFactory.getInstance().addMessage(clientId, severity, messageId, arguments);
	}

	/**
	 * Traverses the component tree starting at the specified UIComponent parent and returns the first UIComponent child
	 * that contains the specified partialClientId.
	 */
	public static UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId) {
		return FacesContextHelperFactory.getInstance().matchComponentInHierarchy(parent, partialClientId);
	}

	/**
	 * Traverses the component tree associated with the UIViewRoot of this FacesContext and returns the first
	 * UIComponent child that contains the specified partialClientId.
	 */
	public static UIComponent matchComponentInViewRoot(String partialClientId) {
		return FacesContextHelperFactory.getInstance().matchComponentInViewRoot(partialClientId);
	}

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  fromAction  The "from action" as specified in a JSF navigation rule. Can be null to if no action is
	 *                     specified in the rule.
	 * @param  outcome     The "from outcome" as specified in a JSF navigation rule
	 */
	public static void navigate(String fromAction, String outcome) {
		FacesContextHelperFactory.getInstance().navigate(fromAction, outcome);
	}

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  outcome  The "from outcome" as specified in a JSF navigation rule
	 */
	public static void navigateTo(String outcome) {
		FacesContextHelperFactory.getInstance().navigateTo(outcome);
	}

	/**
	 * Delete the whole component tree. This causes the tree to be rebuilt the next time it is accessed. This addresses
	 * the problem that immediate actions cannot change the values of input components. To clear these values, use this
	 * method.
	 */
	public static void recreateComponentTree() {
		FacesContextHelperFactory.getInstance().recreateComponentTree();
	}

	/**
	 * Register a {@link PhaseListener} programatically (instead of in the faces-config.xml). Such a PhaseListener can
	 * therefore be controlled by spring and use dependency injection, which is not possible otherwise.
	 */
	public static void registerPhaseListener(PhaseListener phaseListener) {
		FacesContextHelperFactory.getInstance().registerPhaseListener(phaseListener);
	}

	/**
	 * clear component tree since input fields will not be refreshed. See <a
	 * href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a> for more information.
	 *
	 * @param  clientId  all children of the component with this id are cleared.
	 */
	public static void removeChildrenFromComponentTree(String clientId) {
		FacesContextHelperFactory.getInstance().removeChildrenFromComponentTree(clientId);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with the specified clientId.
	 */
	public static void removeMessages(String clientId) {
		FacesContextHelperFactory.getInstance().removeMessages(clientId);
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public static void removeMessagesForImmediateComponents() {
		FacesContextHelperFactory.getInstance().removeMessagesForImmediateComponents();
	}

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public static void removeMessagesForImmediateComponents(UIComponent uiComponent) {
		FacesContextHelperFactory.getInstance().removeMessagesForImmediateComponents(uiComponent);
	}

	/**
	 * Delete the component subtree of a given component. This causes the subtree to be rebuilt the next time it is
	 * accessed. This addresses the problem that immediate actions cannot change the values of input components. To
	 * clear these values, use this method.
	 */
	public static void removeParentFormFromComponentTree(UIComponent uiComponent) {
		FacesContextHelperFactory.getInstance().removeParentFormFromComponentTree(uiComponent);
	}

	/**
	 * Causes the current view's component tree to be discarded and re-rendered.
	 *
	 * @see  {@link FacesContextHelper#resetView(boolean)}
	 */
	public static void resetView() {
		FacesContextHelperFactory.getInstance().resetView();
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
		FacesContextHelperFactory.getInstance().resetView(renderResponse);
	}

	/**
	 * Returns the object associated with the specified EL expression.
	 */
	public static Object resolveExpression(String elExpression) {
		return FacesContextHelperFactory.getInstance().resolveExpression(elExpression);
	}

	/**
	 * Returns the locale of the viewRoot of the current JSF FacesContext
	 */
	public static Locale getLocale() {
		return FacesContextHelperFactory.getInstance().getLocale();
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale.
	 */
	public static String getMessage(String messageId) {
		return FacesContextHelperFactory.getInstance().getMessage(messageId);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale and arguments that are to be substituted.
	 */
	public static String getMessage(String messageId, Object... arguments) {
		return FacesContextHelperFactory.getInstance().getMessage(messageId, arguments);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale.
	 */
	public static String getMessage(Locale locale, String messageId) {
		return FacesContextHelperFactory.getInstance().getMessage(locale, messageId);
	}

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale and arguments that are to be substituted.
	 */
	public static String getMessage(Locale locale, String messageId, Object... arguments) {
		return FacesContextHelperFactory.getInstance().getMessage(locale, messageId, arguments);
	}

	/**
	 * Delegates to the underlying {@link ExternalContext#encodeNamespace(String)} method in order to get the
	 * application namespace.
	 */
	public static String getNamespace() {
		return FacesContextHelperFactory.getInstance().getNamespace();
	}

	/**
	 * Return the parent form of the given component.
	 *
	 * @param   uiComponent  The component whose parent is to be found.
	 *
	 * @return  The parent form or <code>null</code> if no parent form is found.
	 */
	public static UIForm getParentForm(UIComponent uiComponent) {
		return FacesContextHelperFactory.getInstance().getParentForm(uiComponent);
	}

	/**
	 * Returns the value of the request attribute associated with the specified name.
	 */
	public static Object getRequestAttribute(String name) {
		return FacesContextHelperFactory.getInstance().getRequestAttribute(name);
	}

	/**
	 * Sets the value of the a request attribute using the specified name and value.
	 */
	public static void setRequestAttribute(String name, Object value) {
		FacesContextHelperFactory.getInstance().setRequestAttribute(name, value);
	}

	/**
	 * @return  The request context path. {@link FacesContext#getExternalContext()} {@link
	 *          ExternalContext#getRequestContextPath()}
	 */
	public static String getRequestContextPath() {
		return FacesContextHelperFactory.getInstance().getRequestContextPath();
	}

	/**
	 * Retrieves the specified parameter passed as part of the request
	 */
	public static String getRequestParameter(String name) {
		return FacesContextHelperFactory.getInstance().getRequestParameter(name);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as a boolean. The values "yes", "true", "y", and
	 * "1" are accetable values for "TRUE".
	 */
	public static boolean getRequestParameterAsBool(String name, boolean defaultValue) {
		return FacesContextHelperFactory.getInstance().getRequestParameterAsBool(name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public static int getRequestParameterAsInt(String name, int defaultValue) {
		return FacesContextHelperFactory.getInstance().getRequestParameterAsInt(name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public static long getRequestParameterAsLong(String name, long defaultValue) {
		return FacesContextHelperFactory.getInstance().getRequestParameterAsLong(name, defaultValue);
	}

	/**
	 * Retrieves the specified parameter from the ExternalContext's request parameter map.
	 */
	public static String getRequestParameterFromMap(String name) {
		return FacesContextHelperFactory.getInstance().getRequestParameterFromMap(name);
	}

	/**
	 * Returns the map of request parameters from the ExternalContext.
	 *
	 * @see  {@link ExternalContext#getRequestParameterMap()}.
	 */
	public static Map<String, String> getRequestParameterMap() {
		return FacesContextHelperFactory.getInstance().getRequestParameterMap();
	}

	/**
	 * Retrieves the value of the original "javax.servlet.forward.query_string" request attribute.
	 */
	public static String getRequestQueryString() {
		return FacesContextHelperFactory.getInstance().getRequestQueryString();
	}

	/**
	 * Retrieves the value of the specified parameter name from the original "javax.servlet.forward.query_string"
	 * request attribute.
	 */
	public static String getRequestQueryStringParameter(String name) {
		return FacesContextHelperFactory.getInstance().getRequestQueryStringParameter(name);
	}

	/**
	 * Returns the session object associated with the current FacesContext.
	 *
	 * @param  create  Flag indicating whether or not a session should be created if it doesn't yet exist.
	 */
	public static Object getSession(boolean create) {
		return FacesContextHelperFactory.getInstance().getSession(create);
	}

	/**
	 * Returns the value of the session attribute associated with the specified name.
	 */
	public static Object getSessionAttribute(String name) {
		return FacesContextHelperFactory.getInstance().getSessionAttribute(name);
	}

	/**
	 * Sets the value of the a session attribute using the specified name and value.
	 */
	public static void setSessionAttribute(String name, Object value) {
		FacesContextHelperFactory.getInstance().setSessionAttribute(name, value);
	}

	/**
	 * Gets the underlying/wrapped FacesContext ThreadLocal singleton instance.
	 */
	public FacesContext getFacesContext() {
		return FacesContextHelperFactory.getInstance().getFacesContext();
	}
}
