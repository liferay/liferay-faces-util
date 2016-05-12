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
package com.liferay.faces.util.jsp.internal;

import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestScopeEntry extends AbstractPropertyMapEntry<Object> {

	// Private Data Members
	private HttpServletRequest httpServletRequest;

	public RequestScopeEntry(HttpServletRequest httpServletRequest, String key) {
		super(key);
		this.httpServletRequest = httpServletRequest;
	}

	public Object getValue() {
		return httpServletRequest.getAttribute(getKey());
	}

	public Object setValue(Object value) {

		Object oldValue = getValue();
		httpServletRequest.setAttribute(getKey(), value);

		return oldValue;
	}

}
