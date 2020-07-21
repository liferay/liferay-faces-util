/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.client;

import javax.faces.FacesWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
@ProviderType
public abstract class BrowserSnifferFactory implements FacesWrapper<BrowserSnifferFactory> {

	/**
	 * @deprecated  Call {@link #getBrowserSnifferInstance(ExternalContext)} instead.
	 *
	 *              <p>Returns a new instance of {@link BrowserSniffer} from the {@link BrowserSnifferFactory} found by
	 *              the {@link FactoryExtensionFinder}. The returned instance is designed to be used during execution of
	 *              a request thread, so it is not guaranteed to be {@link java.io.Serializable}.</p>
	 */
	@Deprecated
	public static BrowserSniffer getBrowserSnifferInstance() {
		return getBrowserSnifferInstance(FacesContext.getCurrentInstance().getExternalContext());
	}

	/**
	 * Returns a new instance of {@link BrowserSniffer} from the {@link BrowserSnifferFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request thread, so it
	 * is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  externalContext  The external context associated with the current faces context. It is needed in order
	 *                          for the {@link FactoryExtensionFinder} to be able to find the factory.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static BrowserSniffer getBrowserSnifferInstance(ExternalContext externalContext) {

		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				externalContext, BrowserSnifferFactory.class);

		return browserSnifferFactory.getBrowserSniffer(externalContext);
	}

	/**
	 * Returns a new instance of {@link BrowserSniffer}. The returned instance is designed to be used during execution
	 * of a request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 */
	public abstract BrowserSniffer getBrowserSniffer(ExternalContext externalContext);

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract BrowserSnifferFactory getWrapped();
}
