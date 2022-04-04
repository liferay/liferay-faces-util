/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.util.osgi.internal.OSGiEnvironment;


/**
 * @author  Neil Griffin
 */
public class ExternalContextFactoryUtilImpl extends ExternalContextFactory {

	// Private Data Members
	private ExternalContextFactory wrappedFactory;

	public ExternalContextFactoryUtilImpl(ExternalContextFactory externalContextFactory) {
		this.wrappedFactory = externalContextFactory;
	}

	@Override
	public ExternalContext getExternalContext(Object context, Object request, Object response) throws FacesException {

		if (OSGiEnvironment.isApiDetected()) {

			// The Trinidad ResourceServlet (ADF) calls this method outside of the portlet lifecycle. In this case,
			// simply return the ExternalContext instance from the wrapped (Mojarra) factory.
			if ((context instanceof ServletContext) && (request instanceof HttpServletRequest) &&
					(response instanceof HttpServletResponse)) {
				return wrappedFactory.getExternalContext(context, request, response);
			}
			else {
				return new ExternalContextUtilOSGiImpl(context);
			}
		}

		return null;
	}

	/**
	 * This is an overridden method that provides the ability for the FacesWrapper decorator pattern to delegate to
	 * other ExternalContextFactory implementations that are registered.
	 */
	@Override
	public ExternalContextFactory getWrapped() {
		return wrappedFactory;
	}
}
