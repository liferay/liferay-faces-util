/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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


/**
 * @author      Kyle Stiemann
 * @deprecated  Please use {@link FacesContextHelperFactory} instead.
 */
@Deprecated
public abstract class FacesRequestContextFactory implements FacesWrapper<FacesRequestContextFactory> {

	/**
	 * Returns the value of {@link FacesRequestContext#getCurrentInstance()}. The returned instance is designed to be
	 * used during execution of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @deprecated  Call {@link
	 *              FacesContextHelperFactory#getFacesContextHelperInstance(javax.faces.context.ExternalContext)}
	 *              instead.
	 */
	@Deprecated
	public static FacesRequestContext getFacesRequestContextInstance() {
		return FacesRequestContext.getCurrentInstance();
	}

	/**
	 * Returns the value of {@link FacesRequestContext#getCurrentInstance()}. The returned instance is designed to be
	 * used during execution of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @deprecated  Call {@link
	 *              FacesContextHelperFactory#getFacesContextHelperInstance(javax.faces.context.ExternalContext)}
	 *              instead.
	 * @since       3.1
	 * @since       2.1
	 * @since       1.1
	 */
	@Deprecated
	public static FacesRequestContext getFacesRequestContextInstance(ExternalContext externalContext) {
		return FacesRequestContext.getCurrentInstance();
	}

	/**
	 * Returns the value of {@link FacesRequestContext#getCurrentInstance()}. The returned instance is designed to be
	 * used during execution of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @deprecated  Call {@link FacesContextHelperFactory#getFacesContextHelper()} instead.
	 */
	@Deprecated
	public abstract FacesRequestContext getFacesRequestContext();

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 *
	 * @deprecated  No replacement available.
	 */
	@Deprecated
	@Override
	public abstract FacesRequestContextFactory getWrapped();
}
