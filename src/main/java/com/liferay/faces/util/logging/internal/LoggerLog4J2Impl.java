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
package com.liferay.faces.util.logging.internal;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.spi.ExtendedLogger;

/**
 * @author Neil Griffin
 */
public class LoggerLog4J2Impl extends LoggerDefaultImpl {

	// Private Constants
	private static final String CALLING_CLASS_FQCN = LoggerLog4J2Impl.class.getName();

	// Private Data Members
	private org.apache.logging.log4j.Logger wrappedLogger;
	private Boolean traceSupported;

	public LoggerLog4J2Impl(String className) {

		super();

		try {

			// Traverse the callstack to determine whether or not this class is being instantiated during
			// the shutdown of a Tomcat webapp context.
			boolean webappContextStopping = false;
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

			for (StackTraceElement stackTraceElement : stackTraceElements) {

				// Note: Currently only Tomcat is being detected.
				if (stackTraceElement.getClassName().equals("org.apache.catalina.core.StandardContext")
					&& stackTraceElement.getMethodName().equals("stop")) {
					webappContextStopping = true;
				}
			}

			// If this class is not being instantiated during the shutdown of a webapp context, then get
			// the Log4J logger and proceed. Otherwise, don't bother getting the logger, because it will
			// cause undesirable error output.
			if (!webappContextStopping) {
				wrappedLogger = LogManager.getLogger(className);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void debug(String message) {

		if (isDebugEnabled()) {

			try {
				_logMessage(Level.DEBUG, message);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void debug(String message, Object... arguments) {

		if (isDebugEnabled()) {

			try {
				String formattedMessage = formatMessage(message, arguments);
				Throwable throwable = getThrowable(arguments);
				_logMessage(Level.DEBUG, formattedMessage, throwable);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void error(Throwable throwable) {

		if (isErrorEnabled()) {

			try {
				String message = throwable.getMessage();
				_logMessage(Level.ERROR, message, throwable);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void error(String message) {

		if (isErrorEnabled()) {

			try {
				_logMessage(Level.ERROR, message);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void error(String message, Object... arguments) {

		if (isErrorEnabled()) {

			try {
				String formattedMessage = formatMessage(message, arguments);
				Throwable throwable = getThrowable(arguments);
				_logMessage(Level.ERROR, formattedMessage, throwable);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void info(String message) {

		if (isInfoEnabled()) {

			try {
				_logMessage(Level.INFO, message);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void info(String message, Object... arguments) {

		if (isInfoEnabled()) {

			try {
				String formattedMessage = formatMessage(message, arguments);
				Throwable throwable = getThrowable(arguments);
				_logMessage(Level.INFO, formattedMessage, throwable);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isDebugEnabled() {
		return wrappedLogger.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return wrappedLogger.isEnabled(Level.ERROR);
	}

	@Override
	public boolean isInfoEnabled() {
		return wrappedLogger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return wrappedLogger.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return wrappedLogger.isEnabled(Level.WARN);
	}

	@Override
	public void trace(String message) {

		if (isTraceSupported()) {

			if (isTraceEnabled()) {

				try {
					_logMessage(Level.TRACE, message);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {

			// Attempt debug if trace is not supported
			debug(message);
		}
	}

	@Override
	public void trace(String message, Object... arguments) {

		if (isTraceSupported()) {

			if (isTraceEnabled()) {

				try {
					String formattedMessage = formatMessage(message, arguments);
					Throwable throwable = getThrowable(arguments);
					_logMessage(Level.TRACE, formattedMessage, throwable);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {

			// Attempt debug if trace is not supported
			debug(message, arguments);
		}
	}

	@Override
	public void warn(String message) {

		if (isWarnEnabled()) {

			try {
				_logMessage(Level.WARN, message);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void warn(String message, Object... arguments) {

		if (isWarnEnabled()) {

			try {
				String formattedMessage = formatMessage(message, arguments);
				Throwable throwable = getThrowable(arguments);
				_logMessage(Level.WARN, formattedMessage, throwable);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected boolean isTraceSupported() {

		if (traceSupported == null) {

			try {
				isTraceEnabled();
				traceSupported = Boolean.TRUE;
			}
			catch (NoSuchMethodError e) {

				// log4j didn't support isTraceEnabled until 1.2.12
				traceSupported = Boolean.FALSE;
			}
		}

		return traceSupported.booleanValue();
	}

	private void _logMessage(Level level, String message) {
		_logMessage(level, message, null);
	}

	private void _logMessage(Level level, String message, Throwable throwable) {

		if (wrappedLogger instanceof ExtendedLogger) {
			ExtendedLogger extendedLogger = (ExtendedLogger) wrappedLogger;
			extendedLogger.logMessage(CALLING_CLASS_FQCN, level, null, new SimpleMessage(message), throwable);
		}
		else {
			wrappedLogger.logMessage(level, MarkerManager.getMarker("LFU"), CALLING_CLASS_FQCN, null,
				new SimpleMessage(message), throwable);
		}
	}
}
