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

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This class is a {@link ResponseWriter} that is designed to intercept the encoding of a {@link HtmlInputText}
 * component. This class ensures that the "id" attribute is written correctly and that the "type" attribute is "hidden".
 *
 * @author  Neil Griffin
 */
@ProviderType
public class HiddenTextResponseWriter extends ResponseWriterWrapper {

	// Private Data Members
	private String id;
	private boolean wroteId;
	private ResponseWriter wrappedResponseWriter;

	public HiddenTextResponseWriter(ResponseWriter responseWriter, String id) {
		this.wrappedResponseWriter = responseWriter;
		this.id = id;
	}

	@Override
	public void endElement(String name) throws IOException {

		if ("input".equals(name)) {

			if (!wroteId) {
				super.writeAttribute("id", id, "id");
			}
		}

		super.endElement(name);
	}

	@Override
	public ResponseWriter getWrapped() {
		return wrappedResponseWriter;
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if ("id".equals(name)) {
			super.writeAttribute(name, id, property);
		}
		else if ("type".equals(name)) {
			super.writeAttribute(name, "hidden", property);
		}
		else {
			super.writeAttribute(name, value, property);
		}
	}
}
