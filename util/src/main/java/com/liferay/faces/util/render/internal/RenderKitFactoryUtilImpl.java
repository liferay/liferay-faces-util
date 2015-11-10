/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.util.render.internal;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

import com.liferay.faces.util.context.FacesRequestContext;


/**
 * <p>Since liferay-faces-util.jar!META-INF/faces-config.xml specifies <code>
 * &lt;before&gt;&lt;others/&gt;&lt;/before&gt;</code>, the factories in this module can only decorate those provided by
 * the JSF implementation. As a result, factories registered by Non-Liferay/3rd-Party component suites like ICEfaces,
 * PrimeFaces, and RichFaces will end up decorating the factories registered by this module.</p>
 *
 * <p>However, in order to ensure that scripts contained in {@link FacesRequestContext#getScripts()} are encoded before
 * the closing <code>&lt;/body&gt;</code> element, {@link BodyRendererUtilImpl} needs to decorate body renderers
 * provided by any of the aforementioned component suites. This could be accomplished in one of two ways:
 *
 * <ol>
 *   <li>The {@link RenderKitUtilImpl} class from this module could exist in a separate module that specifies
 *     &lt;after&gt;&lt;others/&gt;&lt;/after&gt;</code> and the separate module would register an HTML_BASIC <code>
 *     render-kit</code>.
 *   </li>
 *
 *   <li>The {@link RenderKitUtilImpl} class could remain in this module, but this module would have to register a
 *     <code>render-kit-factory</code> in order to programatically control the {@link RenderKit} delegation chain and
 *     wrapping of renderers. But the only reason why this works is because <strong>none</strong> of the aforementioned
 *     component suites register a <code>render-kit-factory</code>.
 *   </li>
 * </ol>
 *
 * For the sake of minimizing the number of modules, the second option has been implemented.</p>
 *
 * <p><strong>Note:</strong> liferay-faces-bridge-impl.jar!META-INF/faces-config.xml also specifies a <code>
 * render-kit-factory</code> that will decorate this one.</p>
 *
 * @author  Kyle Stiemann
 */
public class RenderKitFactoryUtilImpl extends RenderKitFactory {

	// Private Data Members
	private RenderKitFactory wrappedRenderKitFactory;

	public RenderKitFactoryUtilImpl(RenderKitFactory renderKitFactory) {
		this.wrappedRenderKitFactory = renderKitFactory;
	}

	@Override
	public void addRenderKit(String renderKitId, RenderKit renderKit) {
		wrappedRenderKitFactory.addRenderKit(renderKitId, renderKit);
	}

	@Override
	public RenderKit getRenderKit(FacesContext facesContext, String renderKitId) {

		RenderKit renderKit = wrappedRenderKitFactory.getRenderKit(facesContext, renderKitId);

		if ("HTML_BASIC".equals(renderKitId)) {
			return new RenderKitUtilImpl(renderKit);
		}
		else {
			return renderKit;
		}
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
