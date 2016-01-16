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
package com.liferay.faces.util.application;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;


/**
 * @author  Neil Griffin
 */
public final class ResourceUtil {

	// Prevent instantiation since this is a static utility class.
	private ResourceUtil() {
		throw new AssertionError();
	}

	public static String getComponentValue(UIComponent componentResource) {

		String componentResourceValue = null;

		if (componentResource instanceof ValueHolder) {
			ValueHolder valueHolder = (ValueHolder) componentResource;
			Object valueAsObject = valueHolder.getValue();

			if (valueAsObject != null) {
				componentResourceValue = valueAsObject.toString();
			}
		}

		return componentResourceValue;
	}

	public static String getResourceDependencyId(UIComponent componentResource) {

		String library = null;
		String name = null;

		if (componentResource != null) {

			Map<String, Object> componentResourceAttributes = componentResource.getAttributes();
			library = (String) componentResourceAttributes.get("library");
			name = (String) componentResourceAttributes.get("name");
		}

		return getResourceDependencyId(library, name);
	}

	public static String getResourceDependencyId(String library, String name) {

		String resourceDependencyId;

		if (library == null) {
			resourceDependencyId = name;
		}
		else if (name == null) {
			resourceDependencyId = library;
		}
		else {
			resourceDependencyId = library.concat(":").concat(name);
		}

		return resourceDependencyId;
	}
}
