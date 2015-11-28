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
package com.liferay.faces.util.client;

import javax.faces.FacesWrapper;


/**
 * A factory for creating {@link Script}s which can be rendered on the client via {@link
 * com.liferay.faces.util.context.FacesRequestContext#addScript(com.liferay.faces.util.client.Script)}.
 *
 * @author  Kyle Stiemann
 */
public abstract class ScriptFactory implements FacesWrapper<ScriptFactory> {

	/**
	 * Creates a {@link Script} with the specified text. Please consider using {@link
	 * com.liferay.faces.util.context.FacesRequestContext#addScript(java.lang.String)} instead since it will create the
	 * Script and add it to the list of Scripts which will be rendered to the response.
	 *
	 * @param   content  The text of the Script.
	 *
	 * @return  A {@link Script} which can be rendered on the client via {@link
	 *          com.liferay.faces.util.context.FacesRequestContext#addScript(com.liferay.faces.util.client.Script)}
	 */
	public abstract Script getScript(String content);

	/**
	 * Creates a {@link Script} with the specified text, modules, and type.
	 *
	 * @param   content  The text of the Script.
	 * @param   modules  The modules which the Script depends on.
	 * @param   type     The {@link Script.Type} of the Script.
	 *
	 * @return  A {@link Script} which can be rendered on the client via {@link
	 *          com.liferay.faces.util.context.FacesRequestContext#addScript(com.liferay.faces.util.client.Script)}
	 */
	public abstract Script getScript(String content, String[] modules, Script.Type type);
}
