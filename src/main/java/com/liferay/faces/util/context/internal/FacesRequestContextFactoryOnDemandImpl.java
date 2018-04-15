/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.context.internal;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.context.FacesRequestContextFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Kyle Stiemann
 */
public class FacesRequestContextFactoryOnDemandImpl extends FacesRequestContextFactory implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4557353458470834725L;

	// Since this class is not referenced until FacesRequestContext.getCurrentInstance() is called, the
	// FacesRequestContext instance will be lazily initialized if/when FacesRequestContext.getCurrentInstance() is
	// called. Lazy class initialization is thread-safe. For more details on this pattern, see
	// http://stackoverflow.com/questions/7420504/threading-lazy-initialization-vs-static-lazy-initialization and
	// http://docs.oracle.com/javase/specs/jls/se7/html/jls-12.html#jls-12.4.2
	public static final FacesRequestContext FACES_REQUEST_CONTEXT = new FacesRequestContextImpl();

	@Override
	public FacesRequestContext getFacesRequestContext() {
		return FACES_REQUEST_CONTEXT;
	}

	@Override
	public FacesRequestContextFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}

	private static final class FacesRequestContextImpl extends FacesRequestContext {

		// Logger
		private static final Logger logger = LoggerFactory.getLogger(FacesRequestContextImpl.class);

		private FacesRequestContextImpl() {
			// no-op
		}

		@Override
		public void addScript(Script script) {
			FacesContextHelperUtil.addScript(script);
		}

		@Override
		@Deprecated
		public void addScript(String script) {
			FacesContextHelperUtil.addScript(script);
		}

		@Override
		public void addScript(FacesContext facesContext, String script) {
			FacesContextHelperUtil.addScript(facesContext, script);
		}

		@Override
		public List<Script> getScripts() {
			return FacesContextHelperUtil.getScripts();
		}

		@Override
		public void release() {
			logger.warn(
				"Ignoring call to FacesRequestContext.release() since FacesRequestContext is static singleton.");
		}
	}
}
