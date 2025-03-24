/**
 * Copyright (c) 2000-2025 Liferay, Inc. All rights reserved.
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
import java.io.StringWriter;
import java.io.Writer;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

/**
 * This class is designed to write scripts to a string to be rendered to the response later. This class suppresses all
 * html markup.
 *
 * @author Neil Griffin
 */
@ProviderType
public class BufferedScriptResponseWriter extends ResponseWriter {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BufferedScriptResponseWriter.class);

	// Package-Private Data Members
	@Deprecated
	/* package-private */ StringWriter stringWriter = new StringWriter();

	// Private Final Data Members
	private final StringWriter _stringWriter = new StringWriter();

	@Override
	public ResponseWriter cloneWithWriter(Writer writer) {

		logger.debug(
			"Returning null from cloneWithWriter({0}) since BufferedScriptResponseWriter only supports writing scripts.",
			writer);

		return null;
	}

	@Override
	public void close() throws IOException {
		_stringWriter.close();
	}

	@Override
	public void endDocument() throws IOException {
		logger.debug(
			"Suppressing the output of endDocument() since BufferedScriptResponseWriter only supports writing scripts.");
	}

	@Override
	public void endElement(String name) throws IOException {
		logger.debug(
			"Suppressing the output of endElement({0}, {1}) since BufferedScriptResponseWriter only supports writing scripts.",
			name);
	}

	@Override
	public void flush() throws IOException {
		_stringWriter.flush();
	}

	@Override
	public String getCharacterEncoding() {

		logger.debug(
			"Returning null from getCharacterEncoding() since BufferedScriptResponseWriter only supports writing scripts.");

		return null;
	}

	@Override
	public String getContentType() {

		logger.debug(
			"Returning null from getContentType() since BufferedScriptResponseWriter only supports writing scripts.");

		return null;
	}

	@Override
	public void startDocument() throws IOException {
		logger.debug(
			"Suppressing the output of startDocument() since BufferedScriptResponseWriter only supports writing scripts.");
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {
		logger.debug(
			"Suppressing the output of startElement({0}, {1}) since BufferedScriptResponseWriter only supports writing scripts.",
			name, component);
	}

	@Override
	public String toString() {
		return _stringWriter.toString();
	}

	@Override
	public void write(char[] text, int off, int len) throws IOException {
		_stringWriter.write(text, off, len);
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {
		logger.debug(
			"Suppressing the output of writeAttribute({0}, {1}, {2}) since BufferedScriptResponseWriter only supports writing scripts.",
			name, value, property);
	}

	@Override
	public void writeComment(Object comment) throws IOException {

		if (comment != null) {

			_stringWriter.write("/*");
			_stringWriter.write(comment.toString());
			_stringWriter.write("*/");
		}
	}

	@Override
	public void writeText(Object text, String property) throws IOException {

		// JavaScript does not need to be escaped. See
		// https://github.com/javaserverfaces/mojarra/blob/2.2.12/jsf-ri/src/main/java/com/sun/faces/renderkit/html_basic/HtmlResponseWriter.java#L1280-L1283
		// for more details.
		if (text != null) {
			_stringWriter.write(text.toString());
		}
	}

	@Override
	public void writeText(char[] text, int off, int len) throws IOException {

		// JavaScript does not need to be escaped. See
		// https://github.com/javaserverfaces/mojarra/blob/2.2.12/jsf-ri/src/main/java/com/sun/faces/renderkit/html_basic/HtmlResponseWriter.java#L1280-L1283
		// for more details.
		if (text != null) {
			_stringWriter.write(text, off, len);
		}
	}

	@Override
	public void writeURIAttribute(String name, Object value, String property) throws IOException {
		logger.debug(
			"Suppressing the output of writeURIAttribute({0}, {1}, {2}) since BufferedScriptResponseWriter only supports writing scripts.",
			name, value, property);
	}
}
