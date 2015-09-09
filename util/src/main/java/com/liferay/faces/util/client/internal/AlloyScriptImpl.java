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
package com.liferay.faces.util.client.internal;

import com.liferay.faces.util.client.AlloyScript;


/**
 * @author  Kyle Stiemann
 */
public class AlloyScriptImpl extends ScriptImpl implements AlloyScript {

	// Private Data Members
	private String[] modules;

	public AlloyScriptImpl(String content, String[] modules) {

		super(content);

		if (modules == null) {
			throw new NullPointerException(
				"modules cannot be null, please use com.liferay.faces.util.client.Script instead.");
		}

		this.modules = modules;
	}

	public String[] getModules() {
		return modules;
	}
}
