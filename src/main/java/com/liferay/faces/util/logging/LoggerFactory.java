/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;


/**
 * In order to minimize dependencies, this class provides a layer of abstraction over different logging mechanisms
 * including Log4J and standard Java SE logging.
 *
 * @author  Neil Griffin
 */
public abstract class LoggerFactory {

	private static final LoggerFactory loggerFactory;

	static {

		ServiceFinder<LoggerFactory> serviceLoader = ServiceFinder.load(LoggerFactory.class);

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

	public abstract Logger getLoggerImplementation(String name);

	private static final class ServiceFinder<S> implements Iterable<S> {

		private Class<S> serviceClass;

		private ServiceFinder(Class<S> serviceClass) {
			this.serviceClass = serviceClass;
		}

		private static <S> ServiceFinder<S> load(Class<S> serviceClass) {
			return new ServiceFinder(serviceClass);
		}

		public Iterator<S> iterator() {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			List<S> instances = new ArrayList<S>();
			Enumeration<URL> resources = null;

			try {
				resources = classLoader.getResources("META-INF/services/" + serviceClass.getName());
			}
			catch (IOException e) {

				// Since the logging hasn't been initialized, the best we can do is print to stderr.
				System.err.println(LoggerFactory.class.getName() +
					" (ERROR): Unable to obtain resources via path=[META-INF/services/" + serviceClass.getName() +
					"]:");
				e.printStackTrace();
			}

			while ((resources != null) && resources.hasMoreElements()) {

				URL resource = resources.nextElement();
				InputStream inputStream = null;
				InputStreamReader inputStreamReader = null;
				BufferedReader bufferedReader = null;
				String className = null;

				try {

					inputStream = resource.openStream();
					inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
					bufferedReader = new BufferedReader(inputStreamReader);
					className = bufferedReader.readLine();

				}
				catch (IOException e) {

					// Since logging hasn't been initialized, the best we can do is print to stderr.
					System.err.println("Unable to read contents of resource=[" + resource.getPath() + "]");
				}
				finally {

					try {

						if (bufferedReader != null) {
							bufferedReader.close();
						}

						if (inputStreamReader != null) {
							inputStreamReader.close();
						}

						if (inputStream != null) {
							inputStream.close();
						}
					}
					catch (IOException e) {
						// ignore
					}
				}

				if (className != null) {

					try {

						Class<?> clazz = Class.forName(className);
						S instance = (S) clazz.newInstance();
						instances.add(instance);
					}
					catch (Exception e) {

						// Since logging hasn't been initialized, the best we can do is print to stderr.
						System.err.println(LoggerFactory.class.getName() + " (ERROR): Unable to instantiate class=[" +
							className + "]:");
						e.printStackTrace();
					}
				}
			}

			return instances.iterator();
		}
	}
}
