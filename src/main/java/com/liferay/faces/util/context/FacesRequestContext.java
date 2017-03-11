/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.context;

import java.util.List;

import com.liferay.faces.util.client.Script;


/**
 * This class defines the usage of a {@link ThreadLocal} singleton that exists alongside the {@link
 * javax.faces.context.FacesContext}.
 *
 * @author  Kyle Stiemann
 */
public abstract class FacesRequestContext {

	// Private Static Data Members
	private static ThreadLocal<FacesRequestContext> instance = new ThreadLocal<FacesRequestContext>();

	public static FacesRequestContext getCurrentInstance() {
		return instance.get();
	}

	/**
	 * Sets or removes the value of the {@link ThreadLocal} singleton instance.
	 *
	 * @param  facesRequestContext  If a non-null value is specified, then it will become the singleton value. If null
	 *                              is specified, then singleton value is removed from the ThreadLocal. is removed.
	 */
	public static void setCurrentInstance(FacesRequestContext facesRequestContext) {

		if (facesRequestContext == null) {
			instance.remove();
		}
		else {
			instance.set(facesRequestContext);
		}
	}

	/**
	 * Adds the specified {@link Script} to the list of scripts that are to be executed on the client.
	 */
	public abstract void addScript(Script script);

	/**
	 * Adds the specified script to the list of scripts that are to be executed on the client.
	 */
	public abstract void addScript(String script);

	/**
	 * Returns an immutable list of scripts that were added via the {@link #addScript(Script)} or {@link
	 * #addScript(String)} method.
	 */
	public abstract List<Script> getScripts();

	/**
	 * Releases any resources that are associated with this {@link FacesRequestContext} instance.
	 */
	public abstract void release();
}
