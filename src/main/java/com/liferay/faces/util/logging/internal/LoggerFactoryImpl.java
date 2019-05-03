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
package com.liferay.faces.util.logging.internal;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Kyle Stiemann
 */
public class LoggerFactoryImpl extends LoggerFactory {

	// Private Constants
	private static final String CLASS_NAME_LOG4J_LOGGER = "org.apache.log4j.Logger";
	private static final boolean LOG4J_AVAILABLE;

	static {

		boolean log4jAvailable;

		try {
			Class.forName(CLASS_NAME_LOG4J_LOGGER);
			log4jAvailable = true;

			try {
				new LoggerLog4JImpl(CLASS_NAME_LOG4J_LOGGER);
			}

			// LoggerLog4JImpl catches all exceptions in its constructor. However, NoClassDefFoundError is an Error so
			// it will not be caught in the constructor.
			catch (NoClassDefFoundError e) {

				System.err.println(LoggerFactory.class.getName() +
					" (WARN): Incompatible version of log4j.jar in the classpath. If you are using JBoss/Wildfly Server, add WEB-INF/lib/log4j.jar to activate Log4J logging.");
				System.err.println(e.getMessage());
				log4jAvailable = false;
			}
		}
		catch (Exception e) {
			log4jAvailable = false;
		}

		LOG4J_AVAILABLE = log4jAvailable;
	}

	@Override
	public Logger getLoggerImplementation(String name) {

		if (LOG4J_AVAILABLE) {
			return new LoggerLog4JImpl(name);
		}
		else {
			return new LoggerDefaultImpl(name);
		}
	}
}
