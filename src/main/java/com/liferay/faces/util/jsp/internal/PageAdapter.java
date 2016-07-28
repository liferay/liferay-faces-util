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

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;


/**
 * @author  Neil Griffin
 */
public class PageAdapter extends HttpServlet {

	private ServletConfig servletConfig;
	private ServletContext servletContext;

	public PageAdapter(ServletContext servletContext) {
		this.servletConfig = new PageAdapterServletConfig();
		this.servletContext = servletContext;
	}

	public void destroy() {
	}

	public ServletConfig getServletConfig() {
		return servletConfig;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	public String getServletInfo() {
		return PageAdapter.class.getName();
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		this.servletConfig = servletConfig;
	}

	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException,
		IOException {
		throw new UnsupportedOperationException();
	}

	private class PageAdapterServletConfig implements ServletConfig {

		@Override
		public String getInitParameter(String name) {
			return null;
		}

		@Override
		public Enumeration<String> getInitParameterNames() {
			return Collections.enumeration(Collections.<String>emptyList());
		}

		@Override
		public ServletContext getServletContext() {
			return servletContext;
		}

		@Override
		public String getServletName() {
			return PageAdapterServletConfig.class.getName();
		}

	}

}
