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
package com.liferay.faces.util.logging;

import com.liferay.faces.util.logging.internal.LoggerDefaultImpl;
import com.liferay.faces.util.logging.internal.LoggerLog4JImpl;
import org.apache.log4j.LogManager;


/**
 * In order to minimize dependencies, this class provides as a layer of abstraction over different logging mechanisms
 * including Log4J and standard Java SE logging.
 *
 * @author  Neil Griffin
 */
public class LoggerFactory {

	// Private Constants
	private static final String CLASS_NAME_LOG4J_LOGGER = "org.apache.log4j.Logger";
	private static final boolean LOG4J_AVAILABLE;

	static {

		boolean log4jAvailable = true;

		try {
			Class.forName(CLASS_NAME_LOG4J_LOGGER);

			try {
				LogManager.getLogger(CLASS_NAME_LOG4J_LOGGER);
			}
			catch (NoClassDefFoundError e) {

				String className = LoggerFactory.class.getName();
				System.out.println(className + " (INFO): If you are using Liferay Faces in Wildfly, add WEB-INF/log4j.jar to activate Log4J logging");
				System.err.println(className +
					" (WARN): Possibly an incompatible version of log4j.jar in the classpath: " + e.getMessage());

				log4jAvailable = false;
			}
		}
		catch (Exception e) {
			log4jAvailable = false;
		}

		LOG4J_AVAILABLE = log4jAvailable;
	}

	/**
	 * This method gets a logger from the underlying logging implementation. First it tries Log4J, then standard Java SE
	 * logging mechanism. NOTE: In the future, support should be added for detection of Apache Commons-Logging and
	 * SLF4J.
	 *
	 * @param   name  The name associated with the logger.
	 *
	 * @return  The logger associated with the specified name.
	 */
	public static final Logger getLogger(String name) {

		if (LOG4J_AVAILABLE) {
			return new LoggerLog4JImpl(name);
		}
		else {
			return new LoggerDefaultImpl(name);
		}
	}

	/**
	 * This method gets a logger from the underlying logging implementation. First it tries Log4J, then standard Java SE
	 * logging mechanism. NOTE: In the future, support should be added for detection of Apache Commons-Logging and
	 * SLF4J.
	 *
	 * @param   clazz  The class associated with the logger.
	 *
	 * @return  The logger associated with the specified class.
	 */
	public static final Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}
}
