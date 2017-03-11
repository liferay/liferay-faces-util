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
package com.liferay.faces.util.helper;

/**
 * This class provides additional methods that operate against the javax.lang.Long system class.
 */
public class LongHelper {

	public static long toLong(String value) {
		return toLong(value, 0);
	}

	public static long toLong(String value, long defaultValue) {

		long valueAsLong = defaultValue;

		try {
			valueAsLong = Long.parseLong(value);
		}
		catch (NumberFormatException e) {
			// ignore
		}

		return valueAsLong;
	}
}
