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
 * @author  Neil Griffin
 */
public abstract class ResourceValidatorFactory implements FacesWrapper<ResourceValidatorFactory> {

	/**
	 * @return  an instance of {@link ResourceValidator} from the {@link ResourceValidatorFactory} found by the {@link
	 *          FactoryExtensionFinder}.
	 */
	public static ResourceValidator getResourceValidatorInstance() {

		ResourceValidatorFactory resourceValidatorFactory = (ResourceValidatorFactory) FactoryExtensionFinder
			.getFactory(ResourceValidatorFactory.class);

		return resourceValidatorFactory.getResourceValidator();
	}

	public abstract ResourceValidator getResourceValidator();
}
