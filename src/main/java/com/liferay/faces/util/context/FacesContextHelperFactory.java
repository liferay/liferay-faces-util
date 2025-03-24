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
package com.liferay.faces.util.context;

import javax.faces.FacesWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
@ProviderType
public abstract class FacesContextHelperFactory implements FacesWrapper<FacesContextHelperFactory> {

	/**
	 * @deprecated  Call {@link #getFacesContextHelperInstance(ExternalContext)} instead.
	 *
	 *              <p>Returns a stateless, thread-safe singleton instance of {@link FacesContextHelper} from the {@link
	 *              FacesContextHelperFactory} found by the {@link FactoryExtensionFinder}.</p>
	 */
	@Deprecated
	public static FacesContextHelper getFacesContextHelperInstance() {
		return getFacesContextHelperInstance(FacesContext.getCurrentInstance().getExternalContext());
	}

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link FacesContextHelper} from the {@link
	 * FacesContextHelperFactory} found by the {@link FactoryExtensionFinder}.
	 *
	 * @param  externalContext  The external context associated with the current faces context. It is needed in order
	 *                          for the {@link FactoryExtensionFinder} to be able to find the factory.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static FacesContextHelper getFacesContextHelperInstance(ExternalContext externalContext) {

		FacesContextHelperFactory facesContextHelperFactory = (FacesContextHelperFactory) FactoryExtensionFinder
			.getFactory(externalContext, FacesContextHelperFactory.class);

		return facesContextHelperFactory.getFacesContextHelper();
	}

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link FacesContextHelper}.
	 */
	public abstract FacesContextHelper getFacesContextHelper();

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract FacesContextHelperFactory getWrapped();
}
