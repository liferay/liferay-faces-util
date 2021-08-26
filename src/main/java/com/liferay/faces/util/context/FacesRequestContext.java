/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

import java.util.List;

import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.context.internal.FacesRequestContextFactoryOnDemandImpl;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class defines the usage of static singleton that can be used to add scripts to a response.
 *
 * @deprecated  Please use {@link FacesContextHelperUtil} instead.
 * @author      Kyle Stiemann
 */
@Deprecated
@ProviderType
public abstract class FacesRequestContext {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesRequestContext.class);

	/**
	 * Returns a static singleton instance of {@link FacesRequestContext}.
	 *
	 * @deprecated  No replacement available.
	 */
	@Deprecated
	public static FacesRequestContext getCurrentInstance() {
		return FacesRequestContextFactoryOnDemandImpl.FACES_REQUEST_CONTEXT;
	}

	/**
	 * This method has no effect.
	 *
	 * @deprecated  Please use {@link FacesContextHelperFactory} to decorate the response script functionality instead.
	 *
	 * @param       facesRequestContext  If a non-null value is specified, then it will become the singleton value. If
	 *                                   null is specified, then singleton value is removed from the ThreadLocal. is
	 *                                   removed.
	 */
	@Deprecated
	public static void setCurrentInstance(FacesRequestContext facesRequestContext) {
		logger.warn(
			"Ignoring call to FacesRequestContext.setCurrentInstance() since FacesRequestContext is static singleton.");
	}

	/**
	 * Adds the specified {@link Script} to the list of scripts that are to be executed on the client.
	 *
	 * @deprecated  Call {@link FacesContextHelperUtil#addScript(javax.faces.context.FacesContext,
	 *              com.liferay.faces.util.client.Script)} instead.
	 */
	@Deprecated
	public abstract void addScript(Script script);

	/**
	 * Adds the specified script to the list of scripts that are to be executed on the client.
	 *
	 * @deprecated  Call {@link FacesContextHelperUtil#addScript(javax.faces.context.FacesContext, java.lang.String)}
	 *              instead.
	 *
	 * @param       script  The script that is to be added.
	 */
	@Deprecated
	public abstract void addScript(String script);

	/**
	 * Adds the specified script to the list of scripts that are to be executed on the client.
	 *
	 * @deprecated  Call {@link FacesContextHelperUtil#addScript(javax.faces.context.FacesContext, java.lang.String)}
	 *              instead.
	 *
	 * @param       facesContext  The current faces context.
	 * @param       script        The script that is to be added.
	 *
	 * @since       3.1
	 * @since       2.1
	 * @since       1.1
	 */
	@Deprecated
	public abstract void addScript(FacesContext facesContext, String script);

	/**
	 * Returns an immutable list of scripts that were added via the {@link #addScript(Script)} or {@link
	 * #addScript(FacesContext,String)} method.
	 *
	 * @deprecated  Call {@link FacesContextHelperUtil#getScripts(javax.faces.context.FacesContext)} instead.
	 */
	@Deprecated
	public abstract List<Script> getScripts();

	/**
	 * Releases any resources that are associated with this {@link FacesRequestContext} instance.
	 *
	 * @deprecated  No replacement available.
	 */
	@Deprecated
	public abstract void release();
}
