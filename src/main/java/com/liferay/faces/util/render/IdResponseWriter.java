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
package com.liferay.faces.util.render;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This class is a {@link ResponseWriter} that ensures the "id" attribute is always written to a particular element.
 * There are two general use cases for this class: 1. Ensure that the id is always rendered for a specific element. 2.
 * Render a different id than the delegating renderer would normally render.
 *
 * @author  Neil Griffin
 */
@ProviderType
public class IdResponseWriter extends ResponseWriterWrapper {

	// Private Data Members
	private String idElement;
	private String idValue;
	private boolean writingIdElement;
	private ResponseWriter wrappedResponseWriter;

	public IdResponseWriter(ResponseWriter responseWriter, String idElement, String idValue) {
		this.wrappedResponseWriter = responseWriter;
		this.idElement = idElement;
		this.idValue = idValue;
	}

	@Override
	public void endElement(String name) throws IOException {

		super.endElement(name);

		if ((writingIdElement) && idElement.equals(name)) {
			writingIdElement = false;
		}
	}

	@Override
	public ResponseWriter getWrapped() {
		return wrappedResponseWriter;
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		super.startElement(name, component);

		if ((!writingIdElement) && (idElement != null) && idElement.equals(name)) {
			writingIdElement = true;
			super.writeAttribute("id", idValue, "id");
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if ("id".equals(name)) {

			if (!writingIdElement) {
				super.writeAttribute(name, value, property);
			}
		}
		else {
			super.writeAttribute(name, value, property);
		}
	}
}
