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
package com.liferay.faces.util.render.internal;

import javax.faces.render.Renderer;

import org.junit.Assert;
import org.junit.Test;

import com.liferay.faces.util.render.internal.ResourceRendererUtilImpl;


/**
 * @author  Kyle Stiemann
 */
public final class ResourceRendererUtilImplTest {

	@Test
	public void testSaveRestoreStateFACES_3280() {

		Renderer renderer = new RendererMockImpl();
		ResourceRendererUtilImpl resourceRendererUtilImpl = new ResourceRendererUtilImpl(renderer);
		Object state = resourceRendererUtilImpl.saveState(null);

		Assert.assertEquals(renderer.getClass(), state);
		resourceRendererUtilImpl = new ResourceRendererUtilImpl();
		Assert.assertNull(resourceRendererUtilImpl.getWrapped());
		resourceRendererUtilImpl.restoreState(null, state);
		Assert.assertNotNull(resourceRendererUtilImpl.getWrapped());
	}

	/* package-private */ static final class RendererMockImpl extends Renderer {
	}
}
