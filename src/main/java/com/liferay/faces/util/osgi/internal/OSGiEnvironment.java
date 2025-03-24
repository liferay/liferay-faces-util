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
package com.liferay.faces.util.osgi.internal;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

/**
 * @author Neil Griffin
 */
public final class OSGiEnvironment {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OSGiEnvironment.class);

	private static final boolean API_DETECTED;

	static {
		boolean apiDetected = false;

		try {

			Class.forName("org.osgi.framework.FrameworkUtil");
			apiDetected = true;
		}
		catch (ClassNotFoundException e) {
			// Do nothing.
		}
		catch (NoClassDefFoundError e) {
			// Do nothing.
		}
		catch (Throwable t) {

			logger.error("An unexpected error occurred when attempting to detect OSGi:");
			logger.error(t);
		}

		API_DETECTED = apiDetected;
	}

	public static boolean isApiDetected() {
		return API_DETECTED;
	}
}
