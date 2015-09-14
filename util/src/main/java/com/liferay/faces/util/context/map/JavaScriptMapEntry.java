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
package com.liferay.faces.util.context.map;

import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class JavaScriptMapEntry extends AbstractPropertyMapEntry<String> {

	// Private Data Members
	private JavaScriptMap javaScriptMap;

	public JavaScriptMapEntry(JavaScriptMap javaScriptMap, String key) {
		super(key);
		this.javaScriptMap = javaScriptMap;
	}

	public String getValue() {
		return javaScriptMap.get(getKey());
	}

	public String setValue(String value) {
		return javaScriptMap.put(getKey(), value);
	}

}
