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
package com.liferay.faces.util.application;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This interface provides a contract for verifying whether or not a resource dependency is already satisfied.
 *
 * <p>Resource dependencies are added to the Faces view automatically when a {@link javax.faces.render.Renderer} is
 * annotated with @{@link ResourceDependency}. They can also be added manually by calling {@link
 * javax.faces.component.UIViewRoot#addComponentResource(FacesContext, UIComponent)} or {@link
 * javax.faces.component.UIViewRoot#addComponentResource(FacesContext, UIComponent, String)}.</p>
 *
 * <p>In some situations, resource dependencies may already be satisfied (meaning they are guaranteed to appear on the
 * page already, or are already provided as a resource with a different library name or resource name). When this
 * happens it unnecessary to render the resource. For example, Liferay Portal includes Bootstrap CSS on the page
 * automatically. It would therefore be unnecessary for <a
 * href="http://www.liferay.com/community/liferay-projects/liferay-faces/alloy">Liferay Faces Alloy</a> or <a
 * href="http://www.bootsfaces.net/">BootsFaces</a> to render a Bootstrap CSS resource. Another example would be when a
 * developer uses BootsFaces and PrimeFaces components in the same view. Each of those component libraries add the <a
 * href="http://jquery.com/">jQuery</a> JavaScript library as a dependency, but only one would need to be rendered.</p>
 *
 * @author  Kyle Stiemann
 */
@ProviderType
public interface ResourceVerifier {

	/**
	 * Determines whether or not a {@link ResourceDependency} represented by a {@link UIComponent} has already been
	 * satisfied.
	 *
	 * <p>Since the {@link ResourceVerifier} utilizes the delegation pattern, if an implementation in the delegation
	 * chain determines that a resource dependency has been satisfied, then it returns <code>true</code>. Otherwise it
	 * will return the value from the next member in the delegation chain. The default implementation returns <code>
	 * false</code> assuming that a resource dependency has not already been satisfied and therefore needs to be
	 * rendered.</p>
	 *
	 * @return  <code>true</code> when the resource dependency is already satisfied, otherwise <code>false</code>.
	 */
	public boolean isDependencySatisfied(FacesContext facesContext, UIComponent componentResource);
}
