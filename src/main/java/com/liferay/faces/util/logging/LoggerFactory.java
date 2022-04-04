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
package com.liferay.faces.util.logging;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.osgi.annotation.versioning.ProviderType;


/**
 * In order to minimize dependencies, this class provides a layer of abstraction over different logging mechanisms
 * including Log4J and standard Java SE logging.
 *
 * @author  Neil Griffin
 */
@ProviderType
public abstract class LoggerFactory {

	// Private Constants
	private static final LoggerFactory LOGGER_FACTORY;

	static {

		ServiceLoader<LoggerFactory> serviceLoader = ServiceLoader.load(LoggerFactory.class);

		if (serviceLoader != null) {

			Iterator<LoggerFactory> iterator = serviceLoader.iterator();

			LoggerFactory loggerFactoryImpl = null;

			while ((loggerFactoryImpl == null) && iterator.hasNext()) {
				loggerFactoryImpl = iterator.next();
			}

			if (loggerFactoryImpl == null) {

				try {

					// FACES-2966 Netbeans auto completion fails for Liferay Faces components
					Class<?> clazz = Class.forName("com.liferay.faces.util.logging.internal.LoggerFactoryImpl");
					loggerFactoryImpl = (LoggerFactory) clazz.newInstance();
				}
				catch (Exception e) {
					throw new RuntimeException("Unable locate service for " + LoggerFactory.class.getName(), e);
				}
			}

			LOGGER_FACTORY = loggerFactoryImpl;
		}
		else {
			throw new NullPointerException("Unable to acquire ServiceLoader for " + LoggerFactory.class.getName());
		}
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
		return LOGGER_FACTORY.getLoggerImplementation(name);
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

	public abstract Logger getLoggerImplementation(String name);
}
