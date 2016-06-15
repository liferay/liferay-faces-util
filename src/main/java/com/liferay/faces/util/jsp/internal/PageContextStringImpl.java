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
import java.io.Writer;
import java.util.Enumeration;

import javax.el.ELContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class serves as a {@link PageContext} adapter for invoking JSP {@link Tag} classes directly (outside of JSP)
 * during the execution of the JSF lifecycle.
 *
 * @author  Neil Griffin
 */
public class PageContextStringImpl extends PageContext {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PageContextStringImpl.class);

	// Private Data Members
	private ELContext elContext;
	private JspWriter stringJspWriter;
	private PageContext wrappedPageContext;

	public PageContextStringImpl(ELContext elContext, PageContext pageContext) {

		this.elContext = elContext;
		this.stringJspWriter = new JspWriterStringImpl();
		this.wrappedPageContext = pageContext;

	}

	@Override
	public Object findAttribute(String name) {
		return wrappedPageContext.findAttribute(name);
	}

	@Override
	public void forward(String relativeUrlPath) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getAttribute(String name) {
		return wrappedPageContext.findAttribute(name);
	}

	@Override
	public Object getAttribute(String name, int scope) {
		return wrappedPageContext.getAttribute(name, scope);
	}

	@Override
	public Enumeration<String> getAttributeNamesInScope(int scope) {
		return wrappedPageContext.getAttributeNamesInScope(scope);
	}

	@Override
	public int getAttributesScope(String name) {
		return wrappedPageContext.getAttributesScope(name);
	}

	@Override
	public ELContext getELContext() {
		return elContext;
	}

	@Override
	public Exception getException() {
		return wrappedPageContext.getException();
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.servlet.jsp.el.ExpressionEvaluator getExpressionEvaluator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public JspWriter getOut() {
		return stringJspWriter;
	}

	@Override
	public Object getPage() {
		return wrappedPageContext.getPage();
	}

	@Override
	public ServletRequest getRequest() {
		return wrappedPageContext.getRequest();
	}

	@Override
	public ServletResponse getResponse() {
		return wrappedPageContext.getResponse();
	}

	@Override
	public ServletConfig getServletConfig() {
		return wrappedPageContext.getServletConfig();
	}

	@Override
	public ServletContext getServletContext() {
		return wrappedPageContext.getServletContext();
	}

	@Override
	public HttpSession getSession() {
		return wrappedPageContext.getSession();
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.servlet.jsp.el.VariableResolver getVariableResolver() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void handlePageException(Exception e) throws ServletException, IOException {
		logger.error(e);
	}

	@Override
	public void handlePageException(Throwable t) throws ServletException, IOException {
		logger.error(t);
	}

	@Override
	public void include(String relativeUrlPath) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void include(String relativeUrlPath, boolean flush) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void initialize(Servlet servlet, ServletRequest request, ServletResponse response,
		java.lang.String errorPageURL, boolean needsSession, int bufferSize, boolean autoFlush) throws IOException,
		IllegalStateException, IllegalArgumentException {

		wrappedPageContext.initialize(servlet, request, response, errorPageURL, needsSession, bufferSize, autoFlush);
	}

	@Override
	public JspWriter popBody() {
		return wrappedPageContext.popBody();
	}

	@Override
	public BodyContent pushBody() {
		return wrappedPageContext.pushBody();
	}

	@Override
	public JspWriter pushBody(Writer writer) {
		return wrappedPageContext.pushBody(writer);
	}

	@Override
	public void release() {
		elContext = null;
		stringJspWriter = null;
	}

	@Override
	public void removeAttribute(String name) {
		wrappedPageContext.removeAttribute(name);
	}

	@Override
	public void removeAttribute(String name, int scope) {
		wrappedPageContext.removeAttribute(name, scope);
	}

	@Override
	public void setAttribute(String name, Object value) {
		wrappedPageContext.setAttribute(name, value);
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		wrappedPageContext.setAttribute(name, value, scope);
	}
}
