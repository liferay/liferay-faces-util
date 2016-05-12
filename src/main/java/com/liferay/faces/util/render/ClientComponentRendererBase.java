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
package com.liferay.faces.util.render;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.client.ScriptFactory;
import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.context.FacesRequestContext;


/**
 * This is an abstract class that provides base rendering functionality. It extends normal rendering with methods such
 * as {@link #encodeMarkupBegin(FacesContext, UIComponent)} and {@link #encodeMarkupEnd(FacesContext, UIComponent)} that
 * provide a more fine-grained rendering sequence.
 *
 * @author  Neil Griffin
 */
public abstract class ClientComponentRendererBase extends Renderer {

	// Private Constants
	private static final String JAVA_SCRIPT_HEX_PREFIX = "\\x";
	private static final String BACKSLASH_COLON = "\\\\:";
	private static final String REGEX_COLON = "[:]";
	private static final char[] _HEX_DIGITS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		decodeClientState(facesContext, uiComponent);
		super.decode(facesContext, uiComponent);
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeBegin(facesContext, uiComponent);

		encodeMarkupBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		encodeClientState(facesContext, responseWriter, uiComponent);
		encodeMarkupEnd(facesContext, uiComponent);
		encodeJavaScript(facesContext, uiComponent);
	}

	protected abstract void encodeJavaScript(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	protected abstract void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	protected abstract void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	protected void decodeClientState(FacesContext facesContext, UIComponent uiComponent) {
		// no-op
	}

	protected void encodeBooleanProperty(ResponseWriter responseWriter, String propertyName, Boolean propertyValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(",");
		}

		responseWriter.write(propertyName);
		responseWriter.write(":");
		responseWriter.write(propertyValue.toString());
	}

	protected void encodeClientIdProperty(ResponseWriter responseWriter, String propertyName, String clientId,
		boolean first) throws IOException {

		String escapedClientId = "#" + clientId.replaceAll(REGEX_COLON, BACKSLASH_COLON);
		encodeStringProperty(responseWriter, propertyName, escapedClientId, first);
	}

	protected void encodeClientIdProperty(ResponseWriter responseWriter, String propertyName, String clientId,
		UIComponent uiComponent, boolean first) throws IOException {

		UIComponent forComponent = uiComponent.findComponent(clientId);
		String escapedClientId = clientId;

		if (forComponent != null) {
			escapedClientId = forComponent.getClientId();
		}

		encodeClientIdProperty(responseWriter, propertyName, escapedClientId, first);
	}

	protected void encodeClientState(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {
		// no-op
	}

	protected void encodeFunctionCall(ResponseWriter responseWriter, String functionName, Object... parameters)
		throws IOException {

		responseWriter.write(functionName);
		responseWriter.write("(");

		boolean first = true;

		for (Object parameter : parameters) {

			if (first) {
				first = false;
			}
			else {
				responseWriter.write(",");
			}

			encodeFunctionParameter(responseWriter, parameter);
		}

		responseWriter.write(");");
	}

	protected void encodeIntegerProperty(ResponseWriter responseWriter, String propertyName, Integer propertyValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(",");
		}

		responseWriter.write(propertyName);
		responseWriter.write(":");
		responseWriter.write(propertyValue.toString());
	}

	protected void encodeNonEscapedObjectProperty(ResponseWriter responseWriter, String propertyName,
		Object propertyValue, boolean first) throws IOException {

		if (!first) {
			responseWriter.write(",");
		}

		responseWriter.write(propertyName);
		responseWriter.write(":");
		responseWriter.write(propertyValue.toString());
	}

	protected void encodeStringProperty(ResponseWriter responseWriter, String propertyName, Object propertyValue,
		boolean first) throws IOException {

		String escapedAttributeValue = escapeJavaScript(propertyValue.toString());

		if (!first) {
			responseWriter.write(",");
		}

		responseWriter.write(propertyName);
		responseWriter.write(":'");
		responseWriter.write(escapedAttributeValue);
		responseWriter.write("'");
	}

	// TODO el method
	protected String escapeClientId(String clientId) {

		String escapedClientId = clientId;

		if (escapedClientId != null) {

			escapedClientId = escapedClientId.replaceAll(REGEX_COLON, BACKSLASH_COLON);
			escapedClientId = escapeJavaScript(escapedClientId);
		}

		return escapedClientId;
	}

	protected String escapeJavaScript(String javaScript) {

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

	protected void renderScript(FacesContext facesContext, String bufferedScriptString, String[] modules,
		Script.Type scriptType) {

		Script script;

		if (modules != null) {
			script = ScriptFactory.getScriptInstance(bufferedScriptString, modules, scriptType);
		}
		else {
			script = ScriptFactory.getScriptInstance(bufferedScriptString);
		}

		FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
		facesRequestContext.addScript(script);
	}

	private void encodeFunctionParameter(ResponseWriter responseWriter, Object parameter) throws IOException {

		if (parameter == null) {
			responseWriter.write("null");
		}
		else {

			if (parameter instanceof Object[]) {
				Object[] parameterItems = (Object[]) parameter;

				if (parameterItems.length == 0) {
					responseWriter.write("[]");
				}
				else {
					responseWriter.write("[");

					boolean firstIndex = true;

					for (Object parameterItem : parameterItems) {

						if (firstIndex) {
							firstIndex = false;
						}
						else {
							responseWriter.write(",");
						}

						encodeFunctionParameter(responseWriter, parameterItem);
					}

					responseWriter.write("]");
				}
			}
			else if (parameter instanceof String) {
				responseWriter.write("'" + parameter.toString() + "'");
			}
			else {
				responseWriter.write(parameter.toString());
			}
		}
	}

	private String toHexString(int i) {
		char[] buffer = new char[8];

		int index = 8;

		do {
			buffer[--index] = _HEX_DIGITS[i & 15];

			i >>>= 4;
		}
		while (i != 0);

		return new String(buffer, index, 8 - index);
	}

	protected String getClientVarName(FacesContext facesContext, ClientComponent clientComponent) {

		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String clientId = clientComponent.getClientId();
		String regex = "[" + separatorChar + "]";
		String clientVarName = clientId.replaceAll(regex, "_");

		return clientVarName;
	}
}
