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
package com.liferay.faces.util.render;

import java.io.Serializable;


/**
 * This is a simple marker class that wraps a String. It marks the fact that the wrapped string is a fragment of
 * JavaScript code.
 *
 * @author  Neil Griffin
 */
public final class JavaScriptFragment implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 5918907480864436697L;

	// Private Data Members
	private String value;

	public JavaScriptFragment(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
