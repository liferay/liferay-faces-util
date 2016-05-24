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

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.map.AbstractPropertyMap;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestScope extends AbstractPropertyMap<Object> {

	// Private Data Members
	private HttpServletRequest httpServletRequest;

	public RequestScope(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	@Override
	protected AbstractPropertyMapEntry<Object> createPropertyMapEntry(String name) {
		return new RequestScopeEntry(httpServletRequest, name);
	}

	@Override
	protected Object getProperty(String name) {
		return httpServletRequest.getAttribute(name);
	}

	@Override
	protected Enumeration<String> getPropertyNames() {
		return (Enumeration<String>) httpServletRequest.getAttributeNames();
	}

	@Override
	protected void removeProperty(String name) {
		httpServletRequest.removeAttribute(name);
	}

	@Override
	protected void setProperty(String name, Object value) {
		httpServletRequest.setAttribute(name, value);
	}

}
