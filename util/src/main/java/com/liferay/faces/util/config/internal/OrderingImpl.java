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
package com.liferay.faces.util.config.internal;

import java.util.Arrays;
import java.util.EnumMap;


/**
 * @author  Vernon Singleton
 */
public class OrderingImpl implements Ordering {

	// Private Data Members
	private EnumMap<Path, String[]> routes;

	public OrderingImpl() {
		this.routes = new EnumMap<Path, String[]>(Path.class);
		this.routes.put(Path.BEFORE, new String[0]);
		this.routes.put(Path.AFTER, new String[0]);
	}

	public boolean isOrdered() {
		return ((routes.get(Path.BEFORE).length != 0) || (routes.get(Path.AFTER).length != 0));
	}

	public boolean isBefore(String name) {

		return (Arrays.binarySearch(routes.get(Path.BEFORE), name) >= 0);
	}

	public boolean isAfter(String name) {

		return (Arrays.binarySearch(routes.get(Path.AFTER), name) >= 0);
	}

	public EnumMap<Path, String[]> getRoutes() {
		return routes;
	}

	public void setRoutes(EnumMap<Path, String[]> routes) {
		this.routes = routes;
	}

	public boolean isAfterOthers() {

		boolean value = false;

		if (routes.get(Path.AFTER) != null) {
			value = (Arrays.binarySearch(routes.get(Path.AFTER), OTHERS) >= 0);
		}

		return value;
	}

	public boolean isBeforeOthers() {

		boolean value = false;

		if (routes.get(Path.BEFORE) != null) {
			value = (Arrays.binarySearch(routes.get(Path.BEFORE), OTHERS) >= 0);
		}

		return value;
	}
}
