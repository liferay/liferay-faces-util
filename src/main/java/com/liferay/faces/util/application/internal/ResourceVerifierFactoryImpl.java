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
package com.liferay.faces.util.application.internal;

import java.io.Serializable;

import com.liferay.faces.util.application.ResourceVerifier;
import com.liferay.faces.util.application.ResourceVerifierFactory;

/**
 * @author Kyle Stiemann
 */
public class ResourceVerifierFactoryImpl extends ResourceVerifierFactory implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1703402757138333720L;

	// Private Data Members
	private ResourceVerifier resourceVerifier = new ResourceVerifierImpl();

	public ResourceVerifier getResourceVerifier() {
		return resourceVerifier;
	}

	@Override
	public ResourceVerifierFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
