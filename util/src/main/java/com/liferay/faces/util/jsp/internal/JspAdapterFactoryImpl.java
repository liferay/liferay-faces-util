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

import javax.el.ELContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import com.liferay.faces.util.jsp.JspAdapterFactory;


/**
 * @author  Kyle Stiemann
 */
public class JspAdapterFactoryImpl extends JspAdapterFactory {

	@Override
	public BodyContent getStringBodyContent(JspWriter stringJspWriter) {
		return new BodyContentStringImpl(stringJspWriter);
	}

	@Override
	public JspWriter getStringJspWriter() {
		return new JspWriterStringImpl();
	}

	@Override
	public PageContext getStringPageContext(HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, ELContext elContext, JspWriter stringJspWriter) {
		return new PageContextStringImpl(httpServletRequest, httpServletResponse, elContext, stringJspWriter);
	}

	@Override
	public JspAdapterFactory getWrapped() {

		// Since this is the factory instance provided by default, it will never wrap another factory.
		return null;
	}
}
