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
package com.liferay.faces.util.render.internal;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.client.ScriptsEncoder;
import com.liferay.faces.util.client.ScriptsEncoderFactory;
import com.liferay.faces.util.context.FacesContextHelperUtil;


/**
 * The purpose of this class is to render target="body" scripts, external Library scripts (e.g. PrimeFaces
 * RequestContext scripts), and {@link FacesContextHelperUtil} scripts before the closing &lt;body&gt; tag in a single
 * &lt;script&gt; tag. All other functionality is delegated to the wrapped {@link ResponseWriter}, so all other content
 * will be rendered normally.
 *
 * @author  Kyle Stiemann
 */
public class BodyScriptEncodingResponseWriter extends ResponseWriterWrapper {

	// Private Members
	private FacesContext facesContext;
	private ResponseWriter wrappedResponseWriter;
	private BufferedScript bufferedScript;

	public BodyScriptEncodingResponseWriter(ResponseWriter wrappedResponseWriter, FacesContext facesContext) {

		this.facesContext = facesContext;
		this.wrappedResponseWriter = wrappedResponseWriter;
		this.bufferedScript = new BufferedScript();
	}

	@Override
	public void endElement(String name) throws IOException {

		if (bufferedScript.isBuffering() && "script".equals(name)) {

			// If the script is an external resource, then write it to the response.
			if (bufferedScript.isResource()) {
				bufferedScript.write(wrappedResponseWriter);
			}

			// Otherwise ensure that the script is written before the closing <body> tag.
			else {

				String scriptSource = bufferedScript.toString();
				FacesContextHelperUtil.addScript(facesContext, scriptSource);
			}

			bufferedScript.clear();
		}
		else {

			if ("body".equals(name)) {

				List<Script> scripts = FacesContextHelperUtil.getScripts(facesContext);

				if (!scripts.isEmpty()) {

					ExternalContext externalContext = facesContext.getExternalContext();
					ScriptsEncoder scriptsEncoder = ScriptsEncoderFactory.getScriptsEncoderInstance(externalContext);
					facesContext.setResponseWriter(wrappedResponseWriter);
					scriptsEncoder.encodeBodyScripts(facesContext, scripts);
					facesContext.setResponseWriter(this);
				}
			}

			super.endElement(name);
		}
	}

	@Override
	public ResponseWriter getWrapped() {
		return wrappedResponseWriter;
	}

	@Override
	public void startElement(String name, UIComponent uiComponent) throws IOException {

		if ("script".equals(name)) {
			bufferedScript.startBuffering(uiComponent);
		}
		else {
			super.startElement(name, uiComponent);
		}
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {

		if (bufferedScript.isBuffering()) {
			bufferedScript.bufferSourceCode(cbuf, off, len);
		}
		else {
			super.write(cbuf, off, len);
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (bufferedScript.isBuffering()) {
			bufferedScript.bufferAttribute(name, value, property);
		}
		else {
			super.writeAttribute(name, value, property);
		}
	}

	@Override
	public void writeText(Object text, String property) throws IOException {

		// JavaScript does not need to be escaped. See
		// https://github.com/javaserverfaces/mojarra/blob/2.2.12/jsf-ri/src/main/java/com/sun/faces/renderkit/html_basic/HtmlResponseWriter.java#L1280-L1283
		// for more details.
		if (bufferedScript.isBuffering()) {
			bufferedScript.bufferSourceCode(text);
		}
		else {
			super.writeText(text, property);
		}
	}

	@Override
	public void writeText(char[] cbuf, int off, int len) throws IOException {

		// JavaScript does not need to be escaped. See
		// https://github.com/javaserverfaces/mojarra/blob/2.2.12/jsf-ri/src/main/java/com/sun/faces/renderkit/html_basic/HtmlResponseWriter.java#L1280-L1283
		// for more details.
		if (bufferedScript.isBuffering()) {
			bufferedScript.bufferSourceCode(cbuf, off, len);
		}
		else {
			super.writeText(cbuf, off, len);
		}
	}

	@Override
	public void writeText(Object text, UIComponent component, String property) throws IOException {

		// JavaScript does not need to be escaped. See
		// https://github.com/javaserverfaces/mojarra/blob/2.2.12/jsf-ri/src/main/java/com/sun/faces/renderkit/html_basic/HtmlResponseWriter.java#L1280-L1283
		// for more details.
		if (bufferedScript.isBuffering()) {
			bufferedScript.bufferSourceCode(text);
		}
		else {
			super.writeText(text, component, property);
		}
	}

	@Override
	public void writeURIAttribute(String name, Object value, String property) throws IOException {

		if (bufferedScript.isBuffering()) {
			bufferedScript.bufferURIAttribute(name, value, property);
		}
		else {
			super.writeURIAttribute(name, value, property);
		}
	}

	private static final class BufferedScript {

		// Private Members
		private Map<String, BufferedScriptAttribute> attributes;
		private UIComponent scriptComponent;
		private StringWriter sourceCodeWriter;
		private boolean writingScript;

		private BufferedScript() {

			this.sourceCodeWriter = new StringWriter();
			this.attributes = new HashMap<String, BufferedScriptAttribute>();
		}

		@Override
		public String toString() {
			return sourceCodeWriter.toString();
		}

		private void bufferAttribute(String name, Object value, String property) {
			attributes.put(name, new BufferedScriptAttribute(value, property, false));
		}

		private void bufferSourceCode(Object sourceCode) {
			sourceCodeWriter.write((String) sourceCode);
		}

		private void bufferSourceCode(char[] cbuf, int off, int len) {
			sourceCodeWriter.write(cbuf, off, len);
		}

		private void bufferURIAttribute(String name, Object value, String property) {
			attributes.put(name, new BufferedScriptAttribute(value, property, true));
		}

		private void clear() {

			writingScript = false;
			attributes.clear();
			scriptComponent = null;

			StringBuffer sourceCodeWriterBuffer = sourceCodeWriter.getBuffer();
			sourceCodeWriterBuffer.setLength(0);
		}

		private boolean isBuffering() {
			return writingScript;
		}

		private boolean isResource() {
			return attributes.containsKey("src");
		}

		private void startBuffering(UIComponent scriptComponent) {

			writingScript = true;
			this.scriptComponent = scriptComponent;
		}

		private void write(ResponseWriter responseWriter) throws IOException {

			responseWriter.startElement("script", scriptComponent);

			Set<String> attributeNames = attributes.keySet();

			for (String attributeName : attributeNames) {

				BufferedScriptAttribute attribute = attributes.get(attributeName);

				if (attribute.uriAttribute) {
					responseWriter.writeURIAttribute(attributeName, attribute.value, attribute.property);
				}
				else {
					responseWriter.writeAttribute(attributeName, attribute.value, attribute.property);
				}
			}

			responseWriter.endElement("script");
		}

		private static final class BufferedScriptAttribute {

			// Private Members
			private Object value;
			private String property;
			private boolean uriAttribute;

			public BufferedScriptAttribute(Object value, String property, boolean uriAttribute) {
				this.value = value;
				this.property = property;
				this.uriAttribute = uriAttribute;
			}
		}
	}
}
