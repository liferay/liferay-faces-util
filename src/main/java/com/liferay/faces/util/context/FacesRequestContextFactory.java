/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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

import javax.faces.FacesWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 */
public abstract class FacesRequestContextFactory implements FacesWrapper<FacesRequestContextFactory> {

	/**
	 * @deprecated  Call {@link #getFacesRequestContextInstance(ExternalContext)} instead.
	 *
	 *              <p>Returns a {@link ThreadLocal} singleton instance of {@link FacesRequestContext} from the {@link
	 *              FacesRequestContextFactory} found by the {@link FactoryExtensionFinder}. The returned instance is
	 *              designed to be used during execution of a request thread, so it is not guaranteed to be {@link
	 *              java.io.Serializable}.</p>
	 */
	@Deprecated
	public static FacesRequestContext getFacesRequestContextInstance() {
		return getFacesRequestContextInstance(FacesContext.getCurrentInstance().getExternalContext());
	}

	/**
	 * Returns a {@link ThreadLocal} singleton instance of {@link FacesRequestContext} from the {@link
	 * FacesRequestContextFactory} found by the {@link FactoryExtensionFinder}. The returned instance is designed to be
	 * used during execution of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  externalContext  The external context associated with the current faces context. It is needed in order
	 *                          for the {@link FactoryExtensionFinder} to be able to find the factory.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static FacesRequestContext getFacesRequestContextInstance(ExternalContext externalContext) {

		FacesRequestContextFactory facesRequestContextFactory = (FacesRequestContextFactory) FactoryExtensionFinder
			.getFactory(externalContext, FacesRequestContextFactory.class);

		return facesRequestContextFactory.getFacesRequestContext();
	}

	/**
	 * Returns a {@link ThreadLocal} singleton instance of {@link FacesRequestContext}. The returned instance is
	 * designed to be used during execution of a request thread, so it is not guaranteed to be {@link
	 * java.io.Serializable}.
	 */
	public abstract FacesRequestContext getFacesRequestContext();

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract FacesRequestContextFactory getWrapped();
}
