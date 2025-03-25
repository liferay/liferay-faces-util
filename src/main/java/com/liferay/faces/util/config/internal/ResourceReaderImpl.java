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
package com.liferay.faces.util.config.internal;

import java.io.InputStream;

import jakarta.faces.context.ExternalContext;
import jakarta.servlet.ServletContext;

/**
 * @author Neil Griffin
 */
public class ResourceReaderImpl implements ResourceReader {

	// Private Data Members
	private final ExternalContext externalContext;
	private final ServletContext servletContext;

	public ResourceReaderImpl(ExternalContext externalContext) {
		this.externalContext = externalContext;
		this.servletContext = null;
	}

	public ResourceReaderImpl(ServletContext servletContext) {
		this.externalContext = null;
		this.servletContext = servletContext;
	}

	public InputStream getResourceAsStream(String path) {

		if (servletContext != null) {
			return servletContext.getResourceAsStream(path);
		}

		return externalContext.getResourceAsStream(path);
	}
}
