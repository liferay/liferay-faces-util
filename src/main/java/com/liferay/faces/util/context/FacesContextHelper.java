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
package com.liferay.faces.util.context;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.client.Script;


/**
 * @author  Neil Griffin
 */
@ProviderType
public interface FacesContextHelper {

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public void addComponentErrorMessage(String clientId, String messageId);

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public void addComponentErrorMessage(String clientId, String messageId, Object... arguments);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public void addComponentErrorMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments);

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public void addComponentInfoMessage(String clientId, String messageId);

	/**
	 * Adds the specified key as a message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>. The message will not be global, rather it will be
	 * associated with the <code>javax.faces.component.UIComponent</code> that is associated with the specified
	 * clientId.
	 */
	public void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public void addComponentInfoMessage(String clientId, String messageId, Object... arguments);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key. The message will not be global, rather it will be associated with the <code>
	 * javax.faces.component.UIComponent</code> that is associated with the specified clientId.
	 */
	public void addComponentInfoMessage(FacesContext facesContext, String clientId, String messageId,
		Object... arguments);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>.
	 */
	public void addGlobalErrorMessage(String messageId);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code>.
	 */
	public void addGlobalErrorMessage(FacesContext facesContext, String messageId);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public void addGlobalErrorMessage(String messageId, Object... arguments);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_ERROR</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public void addGlobalErrorMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>.
	 */
	public void addGlobalInfoMessage(String messageId);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code>.
	 */
	public void addGlobalInfoMessage(FacesContext facesContext, String messageId);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public void addGlobalInfoMessage(String messageId, Object... arguments);

	/**
	 * Adds the specified key as a global message to the FacesContext with severity <code>
	 * javax.faces.application.FacesMessage.SEVERITY_INFO</code> and substitutes the specified argument(s) for the
	 * tokens in the specified key.
	 */
	public void addGlobalInfoMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * Adds a global information message to the current FacesContext with "your-request-processed-successfully" key as
	 * the messageId.
	 */
	public void addGlobalSuccessInfoMessage();

	/**
	 * Adds a global information message to the current FacesContext with "your-request-processed-successfully" key as
	 * the messageId.
	 */
	public void addGlobalSuccessInfoMessage(FacesContext facesContext);

	/**
	 * Adds a global error message to the current FacesContext with "an-unexpected-error-occurred" key as the messageId.
	 */
	public void addGlobalUnexpectedErrorMessage();

	/**
	 * Adds a global error message to the current FacesContext with "an-unexpected-error-occurred" key as the messageId.
	 */
	public void addGlobalUnexpectedErrorMessage(FacesContext facesContext);

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public void addMessage(String clientId, Severity severity, String messageId);

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public void addMessage(FacesContext facesContext, String clientId, Severity severity, String messageId);

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public void addMessage(String clientId, Severity severity, String messageId, Object... arguments);

	/**
	 * Adds the specified key as a message associated with the UIComponent that has the specified clientId. Specify
	 * clientId=null for global messages.
	 */
	public void addMessage(FacesContext facesContext, String clientId, Severity severity, String messageId,
		Object... arguments);

	/**
	 * Adds the specified {@link Script} to the list of scripts that are to be executed on the client.
	 *
	 * @param  script  The script that is to be added.
	 *
	 * @since  3.2
	 */
	public void addScript(Script script);

	/**
	 * Adds the specified script to the list of scripts that are to be executed on the client.
	 *
	 * @param  scriptString  The script that is to be added.
	 *
	 * @since  3.2
	 */
	public void addScript(String scriptString);

	/**
	 * Adds the specified {@link Script} to the list of scripts that are to be executed on the client.
	 *
	 * @param  facesContext  The current faces context.
	 * @param  script        The script that is to be added.
	 *
	 * @since  3.2
	 */
	public void addScript(FacesContext facesContext, Script script);

	/**
	 * Adds the specified script to the list of scripts that are to be executed on the client.
	 *
	 * @param  facesContext  The current faces context.
	 * @param  scriptString  The script that is to be added.
	 *
	 * @since  3.2
	 */
	public void addScript(FacesContext facesContext, String scriptString);

	/**
	 * Gets the underlying/wrapped FacesContext ThreadLocal singleton instance.
	 */
	public FacesContext getFacesContext();

	/**
	 * Returns the locale of the viewRoot of the current JSF FacesContext
	 */
	public Locale getLocale();

	/**
	 * Returns the locale of the viewRoot of the current JSF FacesContext
	 */
	public Locale getLocale(FacesContext facesContext);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale.
	 */
	public String getMessage(String messageId);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale.
	 */
	public String getMessage(FacesContext facesContext, String messageId);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale and arguments that are to be substituted.
	 */
	public String getMessage(String messageId, Object... arguments);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale.
	 */
	public String getMessage(Locale locale, String messageId);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the current locale and arguments that are to be substituted.
	 */
	public String getMessage(FacesContext facesContext, String messageId, Object... arguments);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale.
	 */
	public String getMessage(FacesContext facesContext, Locale locale, String messageId);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale and arguments that are to be substituted.
	 */
	public String getMessage(Locale locale, String messageId, Object... arguments);

	/**
	 * Returns the message associated with the specified messageId by delegating to the FacesMessageFactory, according
	 * to the specified locale and arguments that are to be substituted.
	 */
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments);

	/**
	 * Delegates to the underlying {@link ExternalContext#encodeNamespace(String)} method in order to get the
	 * application namespace.
	 */
	public String getNamespace();

	/**
	 * Delegates to the underlying {@link ExternalContext#encodeNamespace(String)} method in order to get the
	 * application namespace.
	 */
	public String getNamespace(FacesContext facesContext);

	/**
	 * Returns the parent form of the given component or <code>null</code> if no parent form is found.
	 *
	 * @param  uiComponent  The component whose parent is to be found.
	 */
	public UIForm getParentForm(final UIComponent uiComponent);

	/**
	 * Returns the value of the request attribute associated with the specified name.
	 */
	public Object getRequestAttribute(String name);

	/**
	 * Returns the value of the request attribute associated with the specified name.
	 */
	public Object getRequestAttribute(FacesContext facesContext, String name);

	/**
	 * Returns The request context path. {@link FacesContext#getExternalContext()} {@link
	 * ExternalContext#getRequestContextPath()}
	 */
	public String getRequestContextPath();

	/**
	 * Returns The request context path. {@link FacesContext#getExternalContext()} {@link
	 * ExternalContext#getRequestContextPath()}
	 */
	public String getRequestContextPath(FacesContext facesContext);

	/**
	 * Retrieves the specified parameter passed as part of the request
	 */
	public String getRequestParameter(String name);

	/**
	 * Retrieves the specified parameter passed as part of the request
	 */
	public String getRequestParameter(FacesContext facesContext, String name);

	/**
	 * Retrieves the specified parameter passed as part of the request as a boolean. The values "yes", "true", "y", and
	 * "1" are accetable values for "TRUE".
	 */
	public boolean getRequestParameterAsBool(String name, boolean defaultValue);

	/**
	 * Retrieves the specified parameter passed as part of the request as a boolean. The values "yes", "true", "y", and
	 * "1" are accetable values for "TRUE".
	 */
	public boolean getRequestParameterAsBool(FacesContext facesContext, String name, boolean defaultValue);

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public int getRequestParameterAsInt(String name, int defaultValue);

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public int getRequestParameterAsInt(FacesContext facesContext, String name, int defaultValue);

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public long getRequestParameterAsLong(String name, long defaultValue);

	/**
	 * Retrieves the specified parameter passed as part of the request as an integer.
	 */
	public long getRequestParameterAsLong(FacesContext facesContext, String name, long defaultValue);

	/**
	 * Retrieves the specified parameter from the ExternalContext's request parameter map.
	 */
	public String getRequestParameterFromMap(String name);

	/**
	 * Retrieves the specified parameter from the ExternalContext's request parameter map.
	 */
	public String getRequestParameterFromMap(FacesContext facesContext, String name);

	/**
	 * Returns the map of request parameters from the ExternalContext.
	 *
	 * @see  ExternalContext#getRequestParameterMap()
	 */
	public Map<String, String> getRequestParameterMap();

	/**
	 * Returns the map of request parameters from the ExternalContext.
	 *
	 * @see  ExternalContext#getRequestParameterMap()
	 */
	public Map<String, String> getRequestParameterMap(FacesContext facesContext);

	/**
	 * Retrieves the value of the original "javax.servlet.forward.query_string" request attribute.
	 */
	public String getRequestQueryString();

	/**
	 * Retrieves the value of the original "javax.servlet.forward.query_string" request attribute.
	 */
	public String getRequestQueryString(FacesContext facesContext);

	/**
	 * Retrieves the value of the specified parameter name from the original "javax.servlet.forward.query_string"
	 * request attribute.
	 */
	public String getRequestQueryStringParameter(String name);

	/**
	 * Retrieves the value of the specified parameter name from the original "javax.servlet.forward.query_string"
	 * request attribute.
	 */
	public String getRequestQueryStringParameter(FacesContext facesContext, String name);

	/**
	 * Returns an immutable list of scripts that were added via the {@link #addScript(FacesContext,Script)} or {@link
	 * #addScript(FacesContext,String)} method.
	 *
	 * @since  3.2
	 */
	public List<Script> getScripts();

	/**
	 * Returns an immutable list of scripts that were added via the {@link #addScript(FacesContext,Script)} or {@link
	 * #addScript(FacesContext,String)} method.
	 *
	 * @param  facesContext  The current faces context.
	 *
	 * @since  3.2
	 */
	public List<Script> getScripts(FacesContext facesContext);

	/**
	 * Returns the session object associated with the current FacesContext.
	 *
	 * @param  create  Flag indicating whether or not a session should be created if it doesn't yet exist.
	 */
	public Object getSession(boolean create);

	/**
	 * Returns the session object associated with the current FacesContext.
	 *
	 * @param  create  Flag indicating whether or not a session should be created if it doesn't yet exist.
	 */
	public Object getSession(FacesContext facesContext, boolean create);

	/**
	 * Returns the value of the session attribute associated with the specified name.
	 */
	public Object getSessionAttribute(String name);

	/**
	 * Returns the value of the session attribute associated with the specified name.
	 */
	public Object getSessionAttribute(FacesContext facesContext, String name);

	/**
	 * Traverses the component tree starting at the specified UIComponent parent and returns the first UIComponent child
	 * that contains the specified partialClientId.
	 */
	public UIComponent matchComponentInHierarchy(UIComponent parent, String partialClientId);

	/**
	 * Traverses the component tree starting at the specified UIComponent parent and returns the first UIComponent child
	 * that contains the specified partialClientId.
	 */
	public UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent, String partialClientId);

	/**
	 * Traverses the component tree associated with the UIViewRoot of this FacesContext and returns the first
	 * UIComponent child that contains the specified partialClientId.
	 */
	public UIComponent matchComponentInViewRoot(String partialClientId);

	/**
	 * Traverses the component tree associated with the UIViewRoot of this FacesContext and returns the first
	 * UIComponent child that contains the specified partialClientId.
	 */
	public UIComponent matchComponentInViewRoot(FacesContext facesContext, String partialClientId);

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  fromAction  The "from action" as specified in a JSF navigation rule. Can be null to if no action is
	 *                     specified in the rule.
	 * @param  outcome     The "from outcome" as specified in a JSF navigation rule
	 */
	public void navigate(String fromAction, String outcome);

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  fromAction  The "from action" as specified in a JSF navigation rule. Can be null to if no action is
	 *                     specified in the rule.
	 * @param  outcome     The "from outcome" as specified in a JSF navigation rule
	 */
	public void navigate(FacesContext facesContext, String fromAction, String outcome);

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  outcome  The "from outcome" as specified in a JSF navigation rule
	 */
	public void navigateTo(String outcome);

	/**
	 * Sets the current JSF navigation to the specified outcome.
	 *
	 * @param  outcome  The "from outcome" as specified in a JSF navigation rule
	 */
	public void navigateTo(FacesContext facesContext, String outcome);

	/**
	 * Delete the whole component tree. This causes the tree to be rebuilt the next time it is accessed. This addresses
	 * the problem that immediate actions cannot change the values of input components. To clear these values, use this
	 * method.
	 */
	public void recreateComponentTree();

	/**
	 * Delete the whole component tree. This causes the tree to be rebuilt the next time it is accessed. This addresses
	 * the problem that immediate actions cannot change the values of input components. To clear these values, use this
	 * method.
	 */
	public void recreateComponentTree(FacesContext facesContext);

	/**
	 * Register a {@link PhaseListener} programatically (instead of in the faces-config.xml). Such a PhaseListener can
	 * therefore be controlled by spring and use dependency injection, which is not possible otherwise.
	 */
	public void registerPhaseListener(PhaseListener phaseListener);

	/**
	 * clear component tree since input fields will not be refreshed. See <a
	 * href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a> for more information.
	 *
	 * @param  clientId  all children of the component with this id are cleared.
	 */
	public void removeChildrenFromComponentTree(String clientId);

	/**
	 * clear component tree since input fields will not be refreshed. See <a
	 * href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a> for more information.
	 *
	 * @param  clientId  all children of the component with this id are cleared.
	 */
	public void removeChildrenFromComponentTree(FacesContext facesContext, String clientId);

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with the specified clientId.
	 */
	public void removeMessages(String clientId);

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with the specified clientId.
	 */
	public void removeMessages(FacesContext facesContext, String clientId);

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public void removeMessagesForImmediateComponents();

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public void removeMessagesForImmediateComponents(FacesContext facesContext);

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public void removeMessagesForImmediateComponents(UIComponent uiComponent);

	/**
	 * Removes all FacesMessage instances from this FacesContext that are associated with components whose immediate
	 * attribute is true.The typical use case for this method is when seemingly bogus messages are added to the
	 * FacesContext on components whose immediate attribute is true. This happens because {@link UIInput#processDecodes}
	 * calls validate() if the the immediate attribute is true.
	 */
	public void removeMessagesForImmediateComponents(FacesContext facesContext, UIComponent uiComponent);

	/**
	 * Delete the component subtree of a given component. This causes the subtree to be rebuilt the next time it is
	 * accessed. This addresses the problem that immediate actions cannot change the values of input components. To
	 * clear these values, use this method.
	 */
	public void removeParentFormFromComponentTree(final UIComponent uiComponent);

	/**
	 * Causes the current view's component tree to be discarded and re-rendered.
	 *
	 * @see  #resetView(boolean)
	 */
	public void resetView();

	/**
	 * Causes the current view's component tree to be discarded and re-rendered.
	 *
	 * @see  #resetView(boolean)
	 */
	public void resetView(FacesContext facesContext);

	/**
	 * Causes the current view's component tree to be discarded and (optionally) re-rendered. This is useful whenever an
	 * action causes navigation back to the current view, but the data in the backing bean(s) has changed substantially.
	 * The view is rendered as if the user is visiting for the first time.
	 *
	 * @param  renderResponse  causes the response to be rendered immediately if true.
	 *
	 * @see    <a href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a>
	 */
	public void resetView(boolean renderResponse);

	/**
	 * Causes the current view's component tree to be discarded and (optionally) re-rendered. This is useful whenever an
	 * action causes navigation back to the current view, but the data in the backing bean(s) has changed substantially.
	 * The view is rendered as if the user is visiting for the first time.
	 *
	 * @param  renderResponse  causes the response to be rendered immediately if true.
	 *
	 * @see    <a href="http://wiki.apache.org/myfaces/ClearInputComponents">ClearInputComponents</a>
	 */
	public void resetView(FacesContext facesContext, boolean renderResponse);

	/**
	 * Returns the object associated with the specified EL expression.
	 */
	public Object resolveExpression(String elExpression);

	/**
	 * Returns the object associated with the specified EL expression.
	 */
	public Object resolveExpression(FacesContext facesContext, String elExpression);

	/**
	 * Sets the value of the a request attribute using the specified name and value.
	 */
	public void setRequestAttribute(String name, Object value);

	/**
	 * Sets the value of the a request attribute using the specified name and value.
	 */
	public void setRequestAttribute(FacesContext facesContext, String name, Object value);

	/**
	 * Sets the value of the a session attribute using the specified name and value.
	 */
	public void setSessionAttribute(String name, Object value);

	/**
	 * Sets the value of the a session attribute using the specified name and value.
	 */
	public void setSessionAttribute(FacesContext facesContext, String name, Object value);
}
