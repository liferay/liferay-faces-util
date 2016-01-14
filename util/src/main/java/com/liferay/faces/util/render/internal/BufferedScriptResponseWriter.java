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
package com.liferay.faces.util.render.internal;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;


/**
 * @author  Neil Griffin
 */
public class BufferedScriptResponseWriter extends ResponseWriter {

	StringWriter stringWriter = null;

	public BufferedScriptResponseWriter() {
		this.stringWriter = new StringWriter();
	}

	@Override
	public ResponseWriter cloneWithWriter(Writer writer) {
		return null;
	}

	@Override
	public void close() throws IOException {
		stringWriter.close();
	}

	@Override
	public void endDocument() throws IOException {
	}

	@Override
	public void endElement(String name) throws IOException {
	}

	@Override
	public void flush() throws IOException {
		stringWriter.flush();
	}

	@Override
	public void startDocument() throws IOException {
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {
	}

	@Override
	public String toString() {
		return stringWriter.toString();
	}

	@Override
	public void write(char[] text, int off, int len) throws IOException {
		stringWriter.write(text, off, len);
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {
	}

	@Override
	public void writeComment(Object comment) throws IOException {

		if (comment != null) {
			stringWriter.write(comment.toString());
		}
	}

	@Override
	public void writeText(Object text, String property) throws IOException {

		// JavaScript does not need to be escaped. See
		// https://github.com/javaserverfaces/mojarra/blob/2.2.12/jsf-ri/src/main/java/com/sun/faces/renderkit/html_basic/HtmlResponseWriter.java#L1280-L1283
		// for more details.
		if (text != null) {
			stringWriter.write(text.toString());
		}
	}

	@Override
	public void writeText(char[] text, int off, int len) throws IOException {

		// JavaScript does not need to be escaped. See
		// https://github.com/javaserverfaces/mojarra/blob/2.2.12/jsf-ri/src/main/java/com/sun/faces/renderkit/html_basic/HtmlResponseWriter.java#L1280-L1283
		// for more details.
		if (text != null) {
			stringWriter.write(text, off, len);
		}
	}

	@Override
	public void writeURIAttribute(String name, Object value, String property) throws IOException {
		writeAttribute(name, value, property);
	}

	@Override
	public String getCharacterEncoding() {
		return null;
	}

	@Override
	public String getContentType() {
		return null;
	}

}
