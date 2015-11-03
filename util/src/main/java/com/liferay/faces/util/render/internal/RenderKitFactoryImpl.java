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
package com.liferay.faces.util.render.internal;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;


/**
 * This {@link RenderKitFactory} is designed to ensure that Util's {@link RenderKitImpl} wraps other {@link RenderKit}s.
 * Since Util comes <code>&lt;before&gt;&lt;others /&gt;&lt;/before&gt;</code> in faces-config.xml configuration
 * scanning, Util's {@link RenderKitImpl} would be wrapped by other {@link RenderKit}s if it was declared in Util's
 * faces-config.xml. Instead, this {@link RenderKitFactory} causes all {@link RenderKit}s to be wrapped with Util's
 * {@link RenderKit}, effectively making {@link RenderKitImpl} the outermost {@link RenderKit}.<br />
 * <br />
 * <strong>Note:</strong> If another JSF library declares a {@link RenderKitFactory}, then Util's {@link RenderKitImpl}
 * is not guaranteed to be the outermost {@link RenderKit}. Currently, we are not aware of any JSF libraries which
 * utilize a {@link RenderKitFactory}.
 *
 * @author  Kyle Stiemann
 */
public class RenderKitFactoryImpl extends RenderKitFactory {

	private RenderKitFactory wrappedRenderKitFactory;

	public RenderKitFactoryImpl(RenderKitFactory renderKitFactory) {
		this.wrappedRenderKitFactory = renderKitFactory;
	}

	@Override
	public void addRenderKit(String renderKitId, RenderKit renderKit) {
		wrappedRenderKitFactory.addRenderKit(renderKitId, renderKit);
	}

	@Override
	public RenderKit getRenderKit(FacesContext facesContext, String renderKitId) {

		RenderKit renderKit = wrappedRenderKitFactory.getRenderKit(facesContext, renderKitId);

		return new RenderKitImpl(renderKit);
	}

	@Override
	public Iterator<String> getRenderKitIds() {
		return wrappedRenderKitFactory.getRenderKitIds();
	}

	@Override
	public RenderKitFactory getWrapped() {
		return wrappedRenderKitFactory;
	}
}
