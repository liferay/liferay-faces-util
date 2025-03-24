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
package com.liferay.faces.util.config.internal;

import com.liferay.faces.util.config.ConfiguredSystemEventListener;


/**
 * @author  Neil Griffin
 */
public class ConfiguredSystemEventListenerImpl implements ConfiguredSystemEventListener {

	// Private Data Members
	private String sourceClass;
	private String systemEventClass;
	private String systemEventListenerClass;

	public ConfiguredSystemEventListenerImpl(String sourceClass, String systemEventClass,
		String systemEventListenerClass) {
		this.sourceClass = sourceClass;
		this.systemEventClass = systemEventClass;
		this.systemEventListenerClass = systemEventListenerClass;
	}

	public String getSourceClass() {
		return sourceClass;
	}

	public String getSystemEventClass() {
		return systemEventClass;
	}

	public String getSystemEventListenerClass() {
		return systemEventListenerClass;
	}

}
