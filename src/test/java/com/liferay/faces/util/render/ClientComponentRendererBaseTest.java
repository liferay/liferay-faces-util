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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author  Kyle Stiemann
 */
public final class ClientComponentRendererBaseTest {

	private static void assertEncodedFunctionCallEquals(String expectedEncodedFunctionCall, String functionName,
		Object... functionParameters) throws IOException {

		final ClientComponentRendererBase CLIENT_COMPONENT_RENDERER = new ClientComponentRendererTestImpl();
		StringWriter stringWriter = new StringWriter();
		ResponseWriter responseWriter = new ResponseWriterMockImpl(stringWriter);
		CLIENT_COMPONENT_RENDERER.encodeFunctionCall(responseWriter, functionName, functionParameters);
		Assert.assertEquals(expectedEncodedFunctionCall, stringWriter.toString());
	}

	@Test
	public void testEncodeFunctionCall() throws IOException {
		assertEncodedFunctionCallEquals("helloWorld(1,true,2,false,3,'hi\\x21\\x22\\x27',4,[hi,100,false]);",
			"helloWorld", 1, true, 2, false, 3, /* FACES-3234 */
			"hi!\"'", 4, new Object[] { new JavaScriptFragment("hi"), 100, false });
	}

	private static final class ClientComponentRendererTestImpl extends ClientComponentRendererBase {

		@Override
		public void encodeJavaScript(FacesContext facesContext, UIComponent uiComponent) throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
			throw new UnsupportedOperationException();
		}
	}
}
