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

import javax.faces.FacesWrapper;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 */
public abstract class FacesRequestContextFactory implements FacesWrapper<FacesRequestContextFactory> {

	/**
	 * Returns a {@link ThreadLocal} singleton instance of {@link FacesRequestContext} from the {@link
	 * FacesRequestContextFactory} found by the {@link FactoryExtensionFinder}. The returned instance is designed to be
	 * used during execution of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public static FacesRequestContext getFacesRequestContextInstance() {

		FacesRequestContextFactory facesRequestContextFactory = (FacesRequestContextFactory) FactoryExtensionFinder
			.getFactory(FacesRequestContextFactory.class);

		return facesRequestContextFactory.getFacesRequestContext();
	}

	/**
	 * Returns a {@link ThreadLocal} singleton instance of {@link FacesRequestContext}. The returned instance is
	 * designed to be used during execution of a request thread, so it is not guaranteed to be {@link
	 * java.io.Serializable}.
	 */
	public abstract FacesRequestContext getFacesRequestContext();

	/**
	 * Returns the wrapped factory instance if this factory has been decorated. Otherwise, this method returns null.
	 */
	@Override
	public abstract FacesRequestContextFactory getWrapped();
}
