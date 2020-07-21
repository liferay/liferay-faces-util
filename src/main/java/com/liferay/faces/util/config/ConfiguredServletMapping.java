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
package com.liferay.faces.util.config;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This interface provides a representation of a servlet-mapping entry from a web-app descriptor.
 *
 * @author  Neil Griffin
 */
@ProviderType
public interface ConfiguredServletMapping {

	/**
	 * If the servlet-mapping url-pattern is extension-mapped (like *.faces), then this method returns the .faces
	 * extension. Otherwise returns null.
	 */
	public String getExtension();

	/**
	 * Returns the servlet-mapping servlet-name.
	 */
	public String getServletName();

	/**
	 * If the servlet-mapping url-pattern is path-mapped (like /views/foo/bar/*), then this method returns the
	 * /views/foo/bar path. Otherwise returns null.
	 */
	public String getServletPath();

	/**
	 * Returns the servlet-mapping url-pattern.
	 */
	public String getUrlPattern();

	/**
	 * Flag indicating whether or not the servlet-mapping url-pattern is extension-mapped.
	 */
	public boolean isExtensionMapped();

	/**
	 * Flag indicating whether or not the servlet-mapping is implicit, meaning it is associated with {@link
	 * javax.faces.application.ViewHandler#DEFAULT_SUFFIX} or {@link
	 * javax.faces.application.ViewHandler#DEFAULT_SUFFIX_PARAM_NAME}.
	 */
	public boolean isImplicit();

	/**
	 * Flag indicating whether or not the specified <code>uri</code> matches the servlet-mapping url-pattern.
	 */
	public boolean isMatch(String uri);

	/**
	 * Flag indicating whether or not the servlet-mapping url-pattern is path-mapped.
	 */
	public boolean isPathMapped();
}
