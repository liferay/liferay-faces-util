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
package com.liferay.faces.util.context.internal;

import java.io.Serializable;

import jakarta.faces.context.FacesContext;
import jakarta.faces.context.PartialViewContext;
import jakarta.faces.context.PartialViewContextFactory;

/**
 * @author Neil Griffin
 */
public class PartialViewContextFactoryImpl extends PartialViewContextFactory implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 7873536780716976024L;

	// Private Data Members
	private PartialViewContextFactory wrappedPartialViewContextFactory;

	public PartialViewContextFactoryImpl(PartialViewContextFactory partialViewContextFactory) {
		this.wrappedPartialViewContextFactory = partialViewContextFactory;
	}

	@Override
	public PartialViewContext getPartialViewContext(FacesContext facesContext) {

		PartialViewContext wrappedPartialViewContext =
			wrappedPartialViewContextFactory.getPartialViewContext(facesContext);

		return new PartialViewContextImpl(wrappedPartialViewContext);
	}

	@Override
	public PartialViewContextFactory getWrapped() {
		return wrappedPartialViewContextFactory;
	}
}
