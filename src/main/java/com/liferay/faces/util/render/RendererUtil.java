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
package com.liferay.faces.util.render;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ProviderType
public class RendererUtil {

	// Private Constants
	private static final String JAVA_SCRIPT_HEX_PREFIX = "\\x";
	private static final char[] _HEX_DIGITS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RendererUtil.class);

	/**
	 * Adds an Ajax behavior to the specified client behavior holder according to its default event. If the Ajax
	 * behavior already exists, then this method takes no action.
	 *
	 * @param  clientBehaviorHolder  The client behavior holder for which to add an Ajax behavior.
	 * @param  execute               Space delimited list of execute ids. Typically this is the value of the "execute"
	 *                               attribute of a UI component.
	 * @param  process               Space delimited list of process ids. Typically this is the value of the "process"
	 *                               attribute of a UI component.
	 * @param  defaultExecute        The value for execute in case both the execute and process parameters are <code>
	 *                               null</code>.
	 * @param  render                Space delimited list of render ids. Typically this is the value of the "render"
	 *                               attribute of a UI component.
	 * @param  update                Space delimited list of update ids. Typically thi sis the value of the "update"
	 *                               attribute of a UI component.
	 * @param  defaultRender         The value for render in case both the render and process parameters are <code>
	 *                               null</code>.
	 */
	public static void addDefaultAjaxBehavior(ClientBehaviorHolder clientBehaviorHolder, String execute, String process,
		String defaultExecute, String render, String update, String defaultRender) {

		if (getDefaultAjaxBehavior(clientBehaviorHolder) == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			Application application = facesContext.getApplication();
			AjaxBehavior ajaxBehavior = (AjaxBehavior) application.createBehavior(AjaxBehavior.BEHAVIOR_ID);
			Collection<String> executeIds = getExecuteIds(execute, process, defaultExecute);
			ajaxBehavior.setExecute(executeIds);

			Collection<String> renderIds = getRenderIds(render, update, defaultRender);
			ajaxBehavior.setRender(renderIds);

			String defaultEventName = clientBehaviorHolder.getDefaultEventName();
			clientBehaviorHolder.addClientBehavior(defaultEventName, ajaxBehavior);
		}
	}

	public static void decodeClientBehaviors(FacesContext facesContext, UIComponent uiComponent) {

		if (uiComponent instanceof ClientBehaviorHolder) {

			ClientBehaviorHolder clientBehaviorHolder = (ClientBehaviorHolder) uiComponent;
			Map<String, List<ClientBehavior>> clientBehaviorMap = clientBehaviorHolder.getClientBehaviors();

			Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
			String behaviorEvent = requestParameterMap.get("javax.faces.behavior.event");

			if (behaviorEvent != null) {

				List<ClientBehavior> clientBehaviors = clientBehaviorMap.get(behaviorEvent);

				if (clientBehaviors != null) {
					String source = requestParameterMap.get("javax.faces.source");

					if (source != null) {
						String clientId = uiComponent.getClientId(facesContext);

						if (clientId.startsWith(source)) {

							for (ClientBehavior behavior : clientBehaviors) {
								behavior.decode(facesContext, uiComponent);
							}
						}
					}
				}
			}
		}
	}

	public static void encodeStyleable(ResponseWriter responseWriter, Styleable styleable, String... classNames)
		throws IOException {

		StringBuilder allCssClasses = new StringBuilder();
		String cssClasses = ComponentUtil.concatCssClasses(classNames);

		if (cssClasses != null) {
			allCssClasses.append(cssClasses);
			allCssClasses.append(" ");
		}

		String styleClass = styleable.getStyleClass();

		if (styleClass != null) {
			allCssClasses.append(styleClass);
		}

		if (allCssClasses.length() > 0) {
			responseWriter.writeAttribute("class", allCssClasses.toString(), Styleable.STYLE_CLASS);
		}

		String style = styleable.getStyle();

		if (style != null) {
			responseWriter.writeAttribute(Styleable.STYLE, style, Styleable.STYLE);
		}
	}

	/**
	 * Escapes JavaScript so that it can safely be rendered as string in the browser. This method escapes JS according
	 * to the recommendations provided in <a
	 * href="https://www.owasp.org/index.php/XSS_%28Cross_Site_Scripting%29_Prevention_Cheat_Sheet#RULE_.233_-_JavaScript_Escape_Before_Inserting_Untrusted_Data_into_JavaScript_Data_Values">
	 * OWASP's Cross Site Scripting (XSS) Prevention Cheat Sheet</a>. <strong>Note:</strong> escaped JS can only be
	 * rendered into certain JS strings. If it is rendered anywhere else, the site will still be vulnerable to XSS. See
	 * the link for more details.
	 *
	 * @param   javaScript  a JavaScript string which has not been escaped to prevent XSS.
	 *
	 * @return  an escaped JavaScript string.
	 */
	public static String escapeJavaScript(String javaScript) {

		StringBuilder stringBuilder = new StringBuilder();
		char[] javaScriptCharArray = javaScript.toCharArray();

		for (char character : javaScriptCharArray) {

			if ((character > 255) || Character.isLetterOrDigit(character)) {

				stringBuilder.append(character);
			}
			else {
				stringBuilder.append(JAVA_SCRIPT_HEX_PREFIX);

				String hexString = toHexString(character);

				if (hexString.length() == 1) {
					stringBuilder.append("0");
				}

				stringBuilder.append(hexString);
			}
		}

		if (stringBuilder.length() != javaScript.length()) {
			javaScript = stringBuilder.toString();
		}

		return javaScript;
	}

	/**
	 * Gets the Ajax behavior associated with the default event of the specified client behavior holder. If not found,
	 * then returns <code>null</code>.
	 *
	 * @param  clientBehaviorHolder  The client behavior holder that potentially has an Ajax behavior associated with
	 *                               its default event.
	 *
	 * @since  1.1
	 * @since  2.1
	 * @since  3.1
	 */
	public static AjaxBehavior getDefaultAjaxBehavior(ClientBehaviorHolder clientBehaviorHolder) {

		Map<String, List<ClientBehavior>> clientBehaviorMap = clientBehaviorHolder.getClientBehaviors();
		String defaultEventName = clientBehaviorHolder.getDefaultEventName();
		List<ClientBehavior> clientBehaviors = clientBehaviorMap.get(defaultEventName);

		if (clientBehaviors != null) {

			for (ClientBehavior clientBehavior : clientBehaviors) {

				if (clientBehavior instanceof AjaxBehavior) {
					return (AjaxBehavior) clientBehavior;
				}
			}
		}

		return null;
	}

	private static Collection<String> getExecuteIds(String execute, String process, String defaultValue) {

		// If the values of the execute and process attributes differ, then
		if (!execute.equals(process)) {

			// If the process attribute was specified and the execute attribute was omitted, then use the value of the
			// process attribute.
			if (execute.equals(defaultValue)) {
				execute = process;
			}

			// Otherwise, if both the execute and process attributes were specified with different values, then log a
			// warning indicating that the value of the execute attribute takes precedence.
			else if (!process.equals(defaultValue)) {
				logger.warn(
					"Different values were specified for the execute=[{0}] and process=[{0}]. The value for execute takes precedence.");
			}
		}

		return Arrays.asList(execute.split(" "));
	}

	private static Collection<String> getRenderIds(String render, String update, String defaultValue) {

		// If the values of the render and update attributes differ, then
		if (!render.equals(update)) {

			// If the update attribute was specified and the render attribute was omitted, then use the value of the
			// update attribute.
			if (render.equals(defaultValue)) {
				render = update;
			}

			// Otherwise, if both the render and update attributes were specified with different values, then log a
			// warning indicating that the value of the render attribute takes precedence.
			else if (!update.equals(defaultValue)) {
				logger.warn(
					"Different values were specified for the render=[{0}] and update=[{0}]. The value for render takes precedence.");
			}
		}

		return Arrays.asList(render.split(" "));
	}

	private static String toHexString(int i) {
		char[] buffer = new char[8];

		int index = 8;

		do {
			buffer[--index] = _HEX_DIGITS[i & 15];

			i >>>= 4;
		}
		while (i != 0);

		return new String(buffer, index, 8 - index);
	}
}
