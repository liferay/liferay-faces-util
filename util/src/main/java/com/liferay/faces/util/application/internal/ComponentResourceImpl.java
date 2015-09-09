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

import com.liferay.faces.util.application.ComponentResource;


/**
 * @author  Neil Griffin
 */
public class ComponentResourceImpl implements ComponentResource {

	// Private Data Members
	private String id;
	private String library;
	private String name;
	private boolean renderable;

	public ComponentResourceImpl(String id, String library, String name, boolean renderable) {
		this.id = id;
		this.library = library;
		this.name = name;
		this.renderable = renderable;
	}

	@Override
	public boolean isRenderable() {
		return renderable;
	}

	public String getId() {
		return id;
	}

	public String getLibrary() {
		return library;
	}

	public String getName() {
		return name;
	}
}
