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

import java.io.Serializable;

import javax.el.ELContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.liferay.faces.util.jsp.PageContextFactory;


/**
 * @author  Kyle Stiemann
 */
public class PageContextFactoryImpl extends PageContextFactory implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4886312841579943217L;

	@Override
	public PageContext getStringPageContext(HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, ELContext elContext) {

		JspFactory jspFactory = JspFactory.getDefaultFactory();

		PageContext wrappedPageContext = jspFactory.getPageContext(new PageAdapter(
					httpServletRequest.getServletContext()), httpServletRequest, httpServletResponse, null, false, 0,
				false);

		return new PageContextStringImpl(elContext, wrappedPageContext);
	}

	@Override
	public PageContextFactory getWrapped() {

		// Since this is the factory instance provided by default, it will never wrap another factory.
		return null;
	}
}
