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
package com.liferay.faces.util.config.internal;

import java.util.EnumMap;


/**
 * @author  Vernon Singleton
 */
public interface Ordering {

	/**
	 * @author  Vernon Singleton
	 */
	public enum Path {
		BEFORE, AFTER
	}

	public String OTHERS = Ordering.class.getName() + ".OTHERS";

	public boolean isOrdered();

	public boolean isBefore(String name);

	public boolean isAfter(String name);

	public EnumMap<Path, String[]> getRoutes();

	public void setRoutes(EnumMap<Path, String[]> routes);

	public boolean isAfterOthers();

	public boolean isBeforeOthers();
}
