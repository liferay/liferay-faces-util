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
package com.liferay.faces.util.context;

import java.util.Iterator;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class FacesContextWrapper extends FacesContext implements Wrapper<FacesContext> {

	public abstract FacesContext getWrapped();

	@Override
	public void addMessage(String clientId, FacesMessage message) {
		getWrapped().addMessage(clientId, message);
	}

	@Override
	public Application getApplication() {
		return getWrapped().getApplication();
	}

	@Override
	public Iterator<String> getClientIdsWithMessages() {
		return getWrapped().getClientIdsWithMessages();
	}

	@Override
	public ELContext getELContext() {
		return getWrapped().getELContext();
	}

	@Override
	public ExternalContext getExternalContext() {
		return getWrapped().getExternalContext();
	}

	@Override
	public Severity getMaximumSeverity() {
		return getWrapped().getMaximumSeverity();
	}

	@Override
	public Iterator<FacesMessage> getMessages() {
		return getWrapped().getMessages();
	}

	@Override
	public Iterator<FacesMessage> getMessages(String clientId) {
		return getWrapped().getMessages(clientId);
	}

	@Override
	public RenderKit getRenderKit() {
		return getWrapped().getRenderKit();
	}

	@Override
	public boolean getRenderResponse() {
		return getWrapped().getRenderResponse();
	}

	@Override
	public boolean getResponseComplete() {
		return getWrapped().getResponseComplete();
	}

	@Override
	public ResponseStream getResponseStream() {
		return getWrapped().getResponseStream();
	}

	@Override
	public ResponseWriter getResponseWriter() {
		return getWrapped().getResponseWriter();
	}

	@Override
	public UIViewRoot getViewRoot() {
		return getWrapped().getViewRoot();
	}

	@Override
	public void release() {
		getWrapped().release();
	}

	@Override
	public void renderResponse() {
		getWrapped().renderResponse();
	}

	@Override
	public void responseComplete() {
		getWrapped().responseComplete();
	}

	@Override
	public void setResponseStream(ResponseStream responseStream) {
		getWrapped().setResponseStream(responseStream);
	}

	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		getWrapped().setResponseWriter(responseWriter);
	}

	@Override
	public void setViewRoot(UIViewRoot root) {
		getWrapped().setViewRoot(root);
	}
}
