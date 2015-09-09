/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import java.util.Map;

import javax.faces.component.UIComponent;

import com.liferay.faces.util.application.ComponentResource;
import com.liferay.faces.util.application.ComponentResourceFactory;
import com.liferay.faces.util.application.ComponentResourceUtil;


/**
 * @author  Neil Griffin
 */
public class ComponentResourceFactoryImpl extends ComponentResourceFactory {

	@Override
	public ComponentResource getComponentResource(UIComponent uiComponentResource) {

		Map<String, Object> attributes = uiComponentResource.getAttributes();
		String library = (String) attributes.get("library");
		String name = (String) attributes.get("name");
		String id = ComponentResourceUtil.getId(library, name);
		boolean renderable = true;

		return new ComponentResourceImpl(id, library, name, renderable);
	}

	@Override
	public ComponentResourceFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
