/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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

import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.helper.IntegerHelper;
import com.liferay.faces.util.helper.LongHelper;


/**
 * This is a utility class that provides static utility methods for getting values from {@link ServletContext}
 * init-param values.
 *
 * @author  Neil Griffin
 */
@ProviderType
public class WebConfigParamUtil {

	public static boolean getBooleanValue(ExternalContext externalContext, String name, String alternateName,
		boolean defaultBooleanValue) {

		boolean booleanValue = defaultBooleanValue;

		String configuredValue = getConfiguredValue(externalContext, name, alternateName);

		if (configuredValue != null) {
			booleanValue = BooleanHelper.isTrueToken(configuredValue);
		}

		return booleanValue;
	}

	public static String getConfiguredValue(ExternalContext externalContext, String name, String alternateName) {

		String configuredValue = externalContext.getInitParameter(name);

		if ((configuredValue == null) && (alternateName != null)) {
			configuredValue = externalContext.getInitParameter(alternateName);
		}

		return configuredValue;
	}

	public static int getIntegerValue(ExternalContext externalContext, String name, String alternateName,
		int defaultIntegerValue) {

		int integerValue = defaultIntegerValue;

		String configuredValue = getConfiguredValue(externalContext, name, alternateName);

		if (configuredValue != null) {
			integerValue = IntegerHelper.toInteger(configuredValue);
		}

		return integerValue;
	}

	public static long getLongValue(ExternalContext externalContext, String name, String alternateName,
		long defaultLongValue) {

		long longValue = defaultLongValue;

		String configuredValue = getConfiguredValue(externalContext, name, alternateName);

		if (configuredValue != null) {
			longValue = LongHelper.toLong(configuredValue);
		}

		return longValue;
	}

	public static String getStringValue(ExternalContext externalContext, String name, String alternateName,
		String defaultStringValue) {

		String stringValue = defaultStringValue;

		String configuredValue = getConfiguredValue(externalContext, name, alternateName);

		if (configuredValue != null) {
			stringValue = configuredValue;
		}

		return stringValue;
	}

	public static boolean isSpecified(ExternalContext externalContext, String name, String alternateName) {
		return (getConfiguredValue(externalContext, name, alternateName) != null);
	}
}
