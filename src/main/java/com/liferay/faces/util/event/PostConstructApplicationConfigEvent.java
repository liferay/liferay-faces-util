/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.event;

import javax.faces.event.SystemEvent;

import com.liferay.faces.util.config.ApplicationConfig;


/**
 * <p>This event is published after all application configuration resources have been scanned, parsed, and processed at
 * startup. This provides the ability for listeners to safely call {@link
 * com.liferay.faces.util.factory.FactoryExtensionFinder#getFactory(Class)} or to get the application configuration by
 * calling {@link #getApplicationConfig()}.</p>
 *
 * @author  Neil Griffin
 */
public class PostConstructApplicationConfigEvent extends SystemEvent {

	// serialVersionUID
	private static final long serialVersionUID = 8134490708842412398L;

	public PostConstructApplicationConfigEvent(ApplicationConfig applicationConfig) {
		super(applicationConfig);
	}

	/**
	 * Returns the source {@link ApplicationConfig} that sent this event.
	 */
	public ApplicationConfig getApplicationConfig() {
		return (ApplicationConfig) getSource();
	}
}
