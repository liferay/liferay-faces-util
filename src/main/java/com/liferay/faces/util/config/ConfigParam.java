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
package com.liferay.faces.util.config;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This is a generic interface that defines the contract for getting configuration param values.
 *
 * @author  Neil Griffin
 */
@ProviderType
public interface ConfigParam<T> {

	/**
	 * Returns the alternate name of the config param (or null if there is not an alternate name).
	 */
	public String getAlternateName();

	/**
	 * Returns the boolean value of the config param. If not specified in the config file, then the value of {@link
	 * #getDefaultBooleanValue()} is returned.
	 */
	public boolean getBooleanValue(T config);

	/**
	 * Returns the value of the config param that was specified in the configuration file.
	 */
	public String getConfiguredValue(T config);

	/**
	 * Returns the default boolean value of the config param.
	 */
	public boolean getDefaultBooleanValue();

	/**
	 * Returns the default int value of the config param.
	 */
	public int getDefaultIntegerValue();

	/**
	 * Returns the default long value of the config param.
	 */
	public long getDefaultLongValue();

	/**
	 * Returns the default String value of the config param.
	 */
	public String getDefaultStringValue();

	/**
	 * Returns the int value of the config param. If not specified in the config file, then the value of {@link
	 * #getDefaultIntegerValue()} is returned.
	 */
	public int getIntegerValue(T config);

	/**
	 * Returns the long value of the config param. If not specified in the config file, then the value of {@link
	 * #getDefaultLongValue()} is returned.
	 */
	public long getLongValue(T config);

	/**
	 * Returns the name of the config param.
	 */
	public String getName();

	/**
	 * Returns the String value of the config param. If not specified in the config file, then the value of {@link
	 * #getDefaultStringValue()} is returned.
	 */
	public String getStringValue(T config);

	/**
	 * Flag indicating whether or not the config param was specified in the configuration file.
	 */
	public boolean isConfigured(T config);
}
