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
public abstract class ScriptsEncoderFactory implements FacesWrapper<ScriptsEncoderFactory> {

	/**
	 * @deprecated  Call {@link #getScriptsEncoderInstance(ExternalContext)} instead.
	 *
	 *              <p>Returns a stateless, thread-safe singleton instance of {@link ScriptsEncoder} from the {@link
	 *              ScriptsEncoderFactory} found by the {@link FactoryExtensionFinder}. ScriptsEncoder is a stateless,
	 *              thread-safe (and potentially singleton) because it is designed to be used by a {@link
	 *              javax.faces.render.Renderer}, which is stateless and thread-safe singleton.</p>
	 */
	@Deprecated
	public static ScriptsEncoder getScriptsEncoderInstance() {
		return getScriptsEncoderInstance(FacesContext.getCurrentInstance().getExternalContext());
	}

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link ScriptsEncoder} from the {@link
	 * ScriptsEncoderFactory} found by the {@link FactoryExtensionFinder}. ScriptsEncoder is a stateless, thread-safe
	 * (and potentially singleton) because it is designed to be used by a {@link javax.faces.render.Renderer}, which is
	 * stateless and thread-safe singleton.
	 *
	 * @param  externalContext  The external context associated with the current faces context. It is needed in order
	 *                          for the {@link FactoryExtensionFinder} to be able to find the factory.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static ScriptsEncoder getScriptsEncoderInstance(ExternalContext externalContext) {

		ScriptsEncoderFactory scriptsEncoderFactory = (ScriptsEncoderFactory) FactoryExtensionFinder.getFactory(
				externalContext, ScriptsEncoderFactory.class);

		return scriptsEncoderFactory.getScriptsEncoder();
	}

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link ScriptsEncoder}. ScriptsEncoder is a stateless,
	 * thread-safe singleton because it is designed to be used by a {@link javax.faces.render.Renderer}, which is
	 * stateless and thread-safe singleton.
	 */
	public abstract ScriptsEncoder getScriptsEncoder();

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract ScriptsEncoderFactory getWrapped();
}
