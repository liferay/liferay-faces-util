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
package com.liferay.faces.util.render;

import javax.faces.FacesWrapper;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
public abstract class FacesURLEncoderFactory implements FacesWrapper<FacesURLEncoderFactory> {

	/**
	 * @return  a stateless, thread-safe (and potentially singleton) instance of {@link FacesURLEncoder} from the {@link
	 *          FacesURLEncoderFactory} found by the {@link FactoryExtensionFinder}. FacesURLEncoder is stateless,
	 *          thread-safe (and potentially singleton) because it is designed to be used by a {@link
	 *          javax.faces.render.Renderer}, which is stateless and thread-safe (and potentially singleton).
	 */
	public static FacesURLEncoder getFacesURLEncoderInstance() {

		FacesURLEncoderFactory facesURLEncoderFactory = (FacesURLEncoderFactory) FactoryExtensionFinder.getFactory(
				FacesURLEncoderFactory.class);

		return facesURLEncoderFactory.getFacesURLEncoder();
	}

	/**
	 * @return  a stateless, thread-safe (and potentially singleton) instance of {@link FacesURLEncoder}.
	 *          FacesURLEncoder is stateless, thread-safe (and potentially singleton) because it is designed to be used
	 *          by a {@link javax.faces.render.Renderer}, which is stateless and thread-safe (and potentially
	 *          singleton).
	 */
	public abstract FacesURLEncoder getFacesURLEncoder();

	/**
	 * @return  the wrapped factory instance if this factory has been decorated. Otherwise, this method returns null.
	 */
	@Override
	public abstract FacesURLEncoderFactory getWrapped();
}
