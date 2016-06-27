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
package com.liferay.faces.util.client;

import javax.faces.FacesWrapper;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * A factory for creating a {@link Script} which can be rendered on the client via {@link
 * com.liferay.faces.util.context.FacesRequestContext#addScript(com.liferay.faces.util.client.Script)}.
 *
 * @author  Kyle Stiemann
 */
public abstract class ScriptFactory implements FacesWrapper<ScriptFactory> {

	/**
	 * @return  an instance of {@link Script} from the {@link ScriptFactory} found by the {@link
	 *          FactoryExtensionFinder}.
	 */
	public static Script getScriptInstance(String sourceCode) {

		ScriptFactory scriptFactory = (ScriptFactory) FactoryExtensionFinder.getFactory(ScriptFactory.class);

		return scriptFactory.getScript(sourceCode);
	}

	/**
	 * @return  an instance of {@link Script} from the {@link ScriptFactory} found by the {@link
	 *          FactoryExtensionFinder}.
	 */
	public static Script getScriptInstance(String sourceCode, String[] modules, Script.ModulesType modulesType) {

		ScriptFactory scriptFactory = (ScriptFactory) FactoryExtensionFinder.getFactory(ScriptFactory.class);

		return scriptFactory.getScript(sourceCode, modules, modulesType);
	}

	/**
	 * Creates a {@link Script} with the specified source code. As a convenience, it is possible to call {@link
	 * com.liferay.faces.util.context.FacesRequestContext#addScript(java.lang.String)} instead since it will create the
	 * Script and add it to the list of Scripts which will be rendered to the response.
	 *
	 * @param   sourceCode  The source code of the Script.
	 *
	 * @return  A {@link Script} which can be rendered on the client via {@link
	 *          com.liferay.faces.util.context.FacesRequestContext#addScript(com.liferay.faces.util.client.Script)}
	 */
	public abstract Script getScript(String sourceCode);

	/**
	 * Creates a {@link Script} with the specified source code, modules, and type.
	 *
	 * @param   sourceCode  The source code of the Script.
	 * @param   modules     The modules which the Script depends on.
	 * @param   modulesType The {@link Script.ModulesType} of the Script.
	 *
	 * @return  A {@link Script} which can be rendered on the client via {@link
	 *          com.liferay.faces.util.context.FacesRequestContext#addScript(com.liferay.faces.util.client.Script)}
	 */
	public abstract Script getScript(String sourceCode, String[] modules, Script.ModulesType modulesType);
}
