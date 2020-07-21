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
package com.liferay.faces.util.client.internal;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.client.ScriptsEncoder;


/**
 * @author  Kyle Stiemann
 */
public class ScriptsEncoderImpl implements ScriptsEncoder, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 8419754847219420589L;

	@Override
	public void encodeBodyScripts(FacesContext facesContext, List<Script> scripts) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("script", null);
		responseWriter.writeAttribute("type", "text/javascript", null);
		encodeScripts(responseWriter, scripts);
		responseWriter.endElement("script");
	}

	@Override
	public void encodeEvalScripts(FacesContext facesContext, List<Script> scripts) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		encodeScripts(responseWriter, scripts);
	}

	private void encodeScripts(ResponseWriter responseWriter, List<Script> scripts) throws IOException {

		for (Script script : scripts) {
			responseWriter.write(script.getSourceCode());
		}
	}
}
