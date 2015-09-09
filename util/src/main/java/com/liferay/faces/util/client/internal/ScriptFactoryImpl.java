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
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.client.ScriptFactory;


/**
 * @author  Kyle Stiemann
 */
public class ScriptFactoryImpl extends ScriptFactory {

	@Override
	public AlloyScript getAlloyScript(String content, String[] modules) {
		return new AlloyScriptImpl(content, modules);
	}

	@Override
	public Script getScript(String content) {
		return new ScriptImpl(content);
	}

	@Override
	public ScriptFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
