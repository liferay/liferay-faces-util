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
package com.liferay.faces.util.client;

/**
 * A Script which can be rendered on the client via {@link
 * com.liferay.faces.util.context.FacesRequestContext#addScript(com.liferay.faces.util.client.Script)}.
 *
 * @author  Kyle Stiemann
 */
public interface Script {

	/**
	 * The Type is used to determine how to handle the Script's modules.
	 *
	 * @author  Kyle Stiemann
	 */
	public enum Type {
		ALLOY
	}

	/**
	 * @return  An array of modules which the Script depends on. Returns null if the Script does not depend on any
	 *          modules.
	 */
	public String[] getModules();

	/**
	 * @return  The Script's text.
	 */
	public String getSourceCode();

	/**
	 * @return  The {@link Script.Type} of the Script.
	 */
	public Type getType();
}
