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

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;


/**
 * This class provides a contract for handling resource dependencies.
 *
 * @author  Kyle Stiemann
 */
public interface ResourceDependencyHandler {

	/**
	 * Determines if a {@link ResourceDependency} represented by a {@link UIComponent} has already been satisfied in a
	 * way that is undetectable by the JSF runtime. This method can be implemented to alert the Util rendering mechanism
	 * when a resource dependency has already been satisfied and does not need to be rendered. For example, in Liferay
	 * Portal, Bootstrap CSS is included on the page by default unbeknownst to the JSF runtime. It is unnecessary to
	 * render Bootstrap CSS twice, and if Bootstrap CSS is rendered by JSF, there is a potential for conflicts. So
	 * projects which use bootstrap in Liferay can implement this method and return true when the ResourceDependency is
	 * bootstrap. If the current ResourceDependencyHandler implementation determines that a resource dependency has been
	 * satisfied, it will return true. Otherwise it will delegate to the wrapped implementation. The default
	 * implementation will return false for all resources.
	 */
	public boolean isSatisfied(UIComponent componentResource);
}
