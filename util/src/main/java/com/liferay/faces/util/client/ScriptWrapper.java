/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.client;

import javax.faces.FacesWrapper;


/**
 * Provides a simple implementation of {@link Script} that can be subclassed in order to decorate another instance of
 * the same type.
 *
 * @author  Kyle Stiemann
 */
public abstract class ScriptWrapper implements Script, FacesWrapper<Script> {

	/**
	 * @see  {@link Script#getModules()}
	 */
	@Override
	public String[] getModules() {
		return getWrapped().getModules();
	}

	/**
	 * @see  {@link Script#getSourceCode()}
	 */
	@Override
	public String getSourceCode() {
		return getWrapped().getSourceCode();
	}

	/**
	 * @see  {@link Script#getType()}
	 */
	@Override
	public Type getType() {
		return getWrapped().getType();
	}
}
