/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
 * This class provides additional methods that operate against the javax.lang.Boolean system class.
 */
@ProviderType
public class BooleanHelper {

	public static final String[] TRUE_VALUES = { "true", "1", "t", "y", "yes", "on", "checked" };
	public static final String[] FALSE_VALUES = { "false", "0", "f", "n", "no", "off", "unchecked" };

	public static boolean isBooleanToken(String value) {

		return isTrueToken(value) || isFalseToken(value);
	}

	public static boolean isFalseToken(String value) {

		boolean flag = false;

		if (value != null) {
			String trimmedValue = value.trim();
			flag = (trimmedValue.equalsIgnoreCase(FALSE_VALUES[0]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[1]) ||
					trimmedValue.equalsIgnoreCase(FALSE_VALUES[2]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[3]) ||
					trimmedValue.equalsIgnoreCase(FALSE_VALUES[3]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[4]) ||
					trimmedValue.equalsIgnoreCase(FALSE_VALUES[5]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[6]));
		}

		return flag;
	}

	public static boolean isTrueToken(String value) {

		boolean flag = false;

		if (value != null) {
			String trimmedValue = value.trim();
			flag = (trimmedValue.equalsIgnoreCase(TRUE_VALUES[0]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[1]) ||
					trimmedValue.equalsIgnoreCase(TRUE_VALUES[2]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[3]) ||
					trimmedValue.equalsIgnoreCase(TRUE_VALUES[3]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[4]) ||
					trimmedValue.equalsIgnoreCase(TRUE_VALUES[5]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[6]));
		}

		return flag;
	}

	public static boolean toBoolean(String value) {
		return toBoolean(value, false);
	}

	public static boolean toBoolean(Object value, boolean defaultValue) {

		String valueAsString = null;

		if (value != null) {
			valueAsString = value.toString();
		}

		return toBoolean(valueAsString, defaultValue);
	}

	public static boolean toBoolean(String value, boolean defaultValue) {

		if (value == null) {

			return defaultValue;
		}
		else {

			if (isTrueToken(value)) {
				return true;
			}
			else if (isFalseToken(value)) {
				return false;
			}
			else {
				return defaultValue;
			}

		}
	}
}
