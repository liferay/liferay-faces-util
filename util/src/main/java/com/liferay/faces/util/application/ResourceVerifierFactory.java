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
package com.liferay.faces.util.application;

import javax.faces.FacesWrapper;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * This class provides the contract for a factory that can create instances of {@link ResourceVerifier}. It implements
 * {@link FacesWrapper} in order to follow the standard factory delegation pattern found in the Faces API. If a concrete
 * implementation of this class has a one-arg constructor and the type of the argument is {@link
 * ResourceVerifierFactory} then the constructor will be called with the next factory instance in the delegation chain.
 *
 * @author  Kyle Stiemann
 */
public abstract class ResourceVerifierFactory implements FacesWrapper<ResourceVerifierFactory> {

	/**
	 * Gets an instance of {@link ResourceVerifier} from the {@link ResourceVerifierFactory} found by the {@link
	 * FactoryExtensionFinder}.
	 */
	public static ResourceVerifier getResourceDependencyHandlerInstance() {

		ResourceVerifierFactory resourceVerifierFactory = (ResourceVerifierFactory) FactoryExtensionFinder.getFactory(
				ResourceVerifierFactory.class);

		return resourceVerifierFactory.getResourceVerifier();
	}

	/**
	 * Gets an instance of {@link ResourceVerifier} which is the first member of the delegation chain.
	 */
	public abstract ResourceVerifier getResourceVerifier();

	/**
	 * If this factory has been decorated then this method provides access to the wrapped factory instance.
	 */
	@Override
	public abstract ResourceVerifierFactory getWrapped();
}
