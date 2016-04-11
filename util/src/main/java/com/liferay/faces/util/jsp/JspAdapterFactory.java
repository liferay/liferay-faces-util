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
package com.liferay.faces.util.jsp;

import javax.el.ELContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.helper.Wrapper;


/**
 * This factory provides methods for creating instances of classes associated with the JavaServer Pages (JSP) API that
 * operate with Strings rather than JSPs. This provides the ability to run the lifecycle of a JSP tag so that the JSP
 * tag markup is captured in a String rather than written as output from a JSP.
 *
 * @author  Kyle Stiemann
 */
public abstract class JspAdapterFactory implements Wrapper<JspAdapterFactory> {

	/**
	 * @return  an instance of {@link StringBodyContent} from the {@link JspAdapterFactory} found by the {@link
	 *          FactoryExtensionFinder}.
	 */
	public static BodyContent getStringBodyContentInstance(JspWriter stringJspWriter) {

		JspAdapterFactory jspAdapterFactory = (JspAdapterFactory) FactoryExtensionFinder.getFactory(
				JspAdapterFactory.class);

		return jspAdapterFactory.getStringBodyContent(stringJspWriter);
	}

	/**
	 * @return  an instance of {@link StringJspWriter} from the {@link JspAdapterFactory} found by the {@link
	 *          FactoryExtensionFinder}.
	 */
	public static JspWriter getStringJspWriterInstance() {

		JspAdapterFactory jspAdapterFactory = (JspAdapterFactory) FactoryExtensionFinder.getFactory(
				JspAdapterFactory.class);

		return jspAdapterFactory.getStringJspWriter();
	}

	public abstract BodyContent getStringBodyContent(JspWriter stringJspWriter);

	public abstract JspWriter getStringJspWriter();

	public abstract PageContext getStringPageContext(HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, ELContext elContext, JspWriter stringJspWriter);
}
