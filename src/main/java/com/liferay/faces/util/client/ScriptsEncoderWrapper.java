/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.client;

import java.io.IOException;
import java.util.List;

import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * @author  Neil Griffin
 */
@ConsumerType
public abstract class ScriptsEncoderWrapper implements ScriptsEncoder, FacesWrapper<ScriptsEncoder> {

	public abstract ScriptsEncoder getWrapped();

	@Override
	public void encodeBodyScripts(FacesContext facesContext, List<Script> scripts) throws IOException {
		getWrapped().encodeBodyScripts(facesContext, scripts);
	}

	@Override
	public void encodeEvalScripts(FacesContext facesContext, List<Script> scripts) throws IOException {
		getWrapped().encodeEvalScripts(facesContext, scripts);
	}
}
