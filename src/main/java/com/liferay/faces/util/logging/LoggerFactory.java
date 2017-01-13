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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.ServiceLoader;


/**
 * In order to minimize dependencies, this class provides a layer of abstraction over different logging mechanisms
 * including Log4J and standard Java SE logging.
 *
 * @author  Neil Griffin
 */
public abstract class LoggerFactory {

	private static final LoggerFactory loggerFactory;

	static {

		ServiceLoader<LoggerFactory> serviceLoader = ServiceLoader.load(LoggerFactory.class);

		if (serviceLoader != null) {

			Iterator<LoggerFactory> iterator = serviceLoader.iterator();

			LoggerFactory loggerFactoryImpl = null;

			while ((loggerFactoryImpl == null) && iterator.hasNext()) {
				loggerFactoryImpl = iterator.next();
			}

			// loggerFactoryImpl should never be null in production.
			if (loggerFactoryImpl == null) {

				InputStream inputStream = null;
				InputStreamReader inputStreamReader = null;
				BufferedReader bufferedReader = null;

				try {

					// FACES-2966 Netbeans auto completion fails for Liferay Faces components
					URL resource = LoggerFactory.class.getResource("/META-INF/resources/services/" +
							LoggerFactory.class.getName());
					inputStream = resource.openStream();
					inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
					bufferedReader = new BufferedReader(inputStreamReader);

					String className = bufferedReader.readLine();
					Class<?> clazz = Class.forName(className);
					loggerFactoryImpl = (LoggerFactory) clazz.newInstance();
				}
				catch (Exception e) {
					// do nothing
				}
				finally {

					close(inputStream);
					close(inputStreamReader);
					close(bufferedReader);

					if (loggerFactoryImpl == null) {
						throw new NullPointerException("Unable locate service for " + LoggerFactory.class.getName());
					}
				}
			}

			loggerFactory = loggerFactoryImpl;
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
		return loggerFactory.getLoggerImplementation(name);
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

	private static void close(Closeable closable) {

		if (closable != null) {

			try {
				closable.close();
			}
			catch (IOException e) {
				// do nothing
			}
		}
	}

	public abstract Logger getLoggerImplementation(String name);
}
