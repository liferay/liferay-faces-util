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
package com.liferay.faces.util.context.internal;

import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;

import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.context.FacesRequestContextFactory;


/**
 * The purpose of this class is to provide a way to automatically instantiate and release the {@link
 * FacesRequestContext} {@link ThreadLocal} singleton.
 *
 * @author  Kyle Stiemann
 */
public class FacesContextUtilImpl extends FacesContextWrapper {

	// Private Data Members
	private FacesContext wrappedFacesContext;

	FacesContextUtilImpl(FacesContext facesContext) {

		this.wrappedFacesContext = facesContext;

		FacesRequestContext facesRequestContext = FacesRequestContextFactory.getFacesRequestContextInstance();
		FacesRequestContext.setCurrentInstance(facesRequestContext);
	}

	@Override
	public void release() {

		FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();

		// FACES-2527: Need to check for null value when rendering runtime JSF portlets contained in the same WAR.
		if (facesRequestContext != null) {
			facesRequestContext.release();
			FacesRequestContext.setCurrentInstance(null);
		}

		super.release();
	}

	@Override
	public FacesContext getWrapped() {
		return wrappedFacesContext;
	}
}
