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
package com.liferay.faces.util.client;

import javax.faces.FacesWrapper;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
public abstract class ScriptsEncoderFactory implements FacesWrapper<ScriptsEncoderFactory> {

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link ScriptsEncoder} from the {@link
	 * ScriptsEncoderFactory} found by the {@link FactoryExtensionFinder}. ScriptsEncoder is stateless, thread-safe (and
	 * potentially singleton) because it is designed to be used by a {@link javax.faces.render.Renderer}, which is
	 * stateless and thread-safe singleton.
	 */
	public static ScriptsEncoder getScriptsEncoderInstance() {

		ScriptsEncoderFactory scriptsEncoderFactory = (ScriptsEncoderFactory) FactoryExtensionFinder.getFactory(
				ScriptsEncoderFactory.class);

		return scriptsEncoderFactory.getScriptsEncoder();
	}

	/**
	 * Returns a stateless, thread-safe singleton instance of {@link ScriptsEncoder}. ScriptsEncoder is stateless,
	 * thread-safe singleton because it is designed to be used by a {@link javax.faces.render.Renderer}, which is
	 * stateless and thread-safe singleton.
	 */
	public abstract ScriptsEncoder getScriptsEncoder();

	/**
	 * Returns the wrapped factory instance if this factory has been decorated. Otherwise, this method returns null.
	 */
	@Override
	public abstract ScriptsEncoderFactory getWrapped();
}
