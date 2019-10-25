/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.render.internal;

import javax.faces.component.UIOutput;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitWrapper;
import javax.faces.render.Renderer;


/**
 * This class extends {@link RenderKitWrapper} in order to programmatically control the {@link RenderKit} delegation
 * chain and wrapping of renderers.
 *
 * @author  Kyle Stiemann
 */
public class RenderKitUtilImpl extends RenderKitWrapper {

	// Private Data Members
	private RenderKit wrappedRenderKit;

	public RenderKitUtilImpl(RenderKit wrappedRenderKit) {
		this.wrappedRenderKit = wrappedRenderKit;
	}

	@Override
	public Renderer getRenderer(String family, String rendererType) {

		Renderer renderer = super.getRenderer(family, rendererType);

		if (UIOutput.COMPONENT_FAMILY.equals(family) && "javax.faces.Body".equals(rendererType)) {
			renderer = new BodyRendererUtilImpl(renderer);
		}
		else if (UIOutput.COMPONENT_FAMILY.equals(family) &&
				("javax.faces.resource.Script".equals(rendererType) ||
					"javax.faces.resource.Stylesheet".equals(rendererType))) {
			renderer = new ResourceRendererUtilImpl(renderer);
		}

		return renderer;
	}

	@Override
	public RenderKit getWrapped() {
		return wrappedRenderKit;
	}
}
