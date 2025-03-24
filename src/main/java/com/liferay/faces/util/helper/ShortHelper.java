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
package com.liferay.faces.util.helper;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This class provides additional methods that operate against the javax.lang.Short system class.
 */
@ProviderType
public class ShortHelper {

	public static short toShort(String value) {
		return toShort(value, (short) 0);
	}

	public static short toShort(String value, short defaultValue) {

		short valueAsShort = defaultValue;

		try {
			valueAsShort = Short.parseShort(value);
		}
		catch (NumberFormatException e) {
			// ignore
		}

		return valueAsShort;
	}
}
