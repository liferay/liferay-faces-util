/**
 * Copyright (c) 2000-2025 Liferay, Inc. All rights reserved.
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.factory.FactoryExtensionFinder;

/**
 * This class provides the contract for a factory that can create instances of {@link ResourceVerifier}. It implements
 * {@link FacesWrapper} in order to follow the standard factory delegation pattern found in the Faces API. If a concrete
 * implementation of this class has a one-arg constructor and the type of the argument is
 * {@link ResourceVerifierFactory} then the constructor will be called with the next factory instance in the delegation
 * chain.
 *
 * @author Kyle Stiemann
 */
@ProviderType
public abstract class ResourceVerifierFactory implements FacesWrapper<ResourceVerifierFactory> {

	/**
	 * @deprecated Call {@link #getResourceVerifierInstance(ExternalContext)} instead.
	 *
	 *             <p>
	 *             Returns a stateless, thread-safe singleton instance of {@link ResourceVerifier} from the
	 *             {@link ResourceVerifierFactory} found by the {@link FactoryExtensionFinder}. ResourceVerifier is a
	 *             stateless, thread-safe singleton because it is designed to be used by a
	 *             {@link javax.faces.render.Renderer}, which is a stateless, thread-safe singleton.
	 *             </p>
	 */
	@Deprecated
	public static ResourceVerifier getResourceVerifierInstance() {
		return getResourceVerifierInstance(FacesContext.getCurrentInstance().getExternalContext());
	}

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link ResourceVerifier} from the
	 * {@link ResourceVerifierFactory} found by the {@link FactoryExtensionFinder}. ResourceVerifier is a stateless,
	 * thread-safe singleton because it is designed to be used by a {@link javax.faces.render.Renderer}, which is a
	 * stateless, thread-safe singleton.
	 *
	 * @param externalContext The external context associated with the current faces context. It is needed in order for
	 *                        the {@link FactoryExtensionFinder} to be able to find the factory.
	 *
	 * @since 3.1
	 * @since 2.1
	 * @since 1.1
	 */
	public static ResourceVerifier getResourceVerifierInstance(ExternalContext externalContext) {

		ResourceVerifierFactory resourceVerifierFactory =
			(ResourceVerifierFactory) FactoryExtensionFinder.getFactory(externalContext, ResourceVerifierFactory.class);

		return resourceVerifierFactory.getResourceVerifier();
	}

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link ResourceVerifier}. The returned ResourceVerifier is
	 * the first member of a delegation chain. ResourceVerifier is a stateless, thread-safe (and potentially singleton)
	 * because it is designed to be used by a {@link javax.faces.render.Renderer}, which is a stateless, thread-safe
	 * singleton.
	 */
	public abstract ResourceVerifier getResourceVerifier();

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract ResourceVerifierFactory getWrapped();
}
