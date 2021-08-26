/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

import org.osgi.annotation.versioning.ProviderType;


/**
 * @author  Neil Griffin
 */
@ProviderType
public class StringHelper {

	public static String[] append(String[] array, String... values) {

		String[] newArray = new String[array.length + values.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		if (values.length == 1) {
			newArray[array.length] = values[0];
		}
		else {
			System.arraycopy(values, 0, newArray, array.length, values.length);
		}

		return newArray;
	}

	public static String toString(Object value, String defaultValue) {

		if (value != null) {
			return value.toString();
		}
		else {
			return defaultValue;
		}
	}

	public static String toString(String[] values, String defaultValue) {

		if ((values != null) && (values.length > 0)) {
			return values[0];
		}
		else {
			return defaultValue;
		}
	}
}
