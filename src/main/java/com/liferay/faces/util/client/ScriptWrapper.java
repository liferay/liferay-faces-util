/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.helper.Wrapper;


/**
 * Provides a simple implementation of {@link Script} that can be subclassed in order to decorate another instance of
 * the same type.
 *
 * @author  Kyle Stiemann
 */
public abstract class ScriptWrapper implements Script, Wrapper<Script> {

	/**
	 * @see  Script#getModules()
	 */
	@Override
	public String[] getModules() {
		return getWrapped().getModules();
	}

	/**
	 * @see  Script#getModulesType()
	 */
	@Override
	public ModulesType getModulesType() {
		return getWrapped().getModulesType();
	}

	/**
	 * @see  Script#getSourceCode()
	 */
	@Override
	public String getSourceCode() {
		return getWrapped().getSourceCode();
	}
}
