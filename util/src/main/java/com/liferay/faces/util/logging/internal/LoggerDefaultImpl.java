/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import com.liferay.faces.util.logging.Logger;


/**
 * @author  Neil Griffin
 */
public class LoggerDefaultImpl implements Logger {

	// Private Data Members
	private java.util.logging.Logger wrappedLogger;

	public LoggerDefaultImpl() {
	}

	public LoggerDefaultImpl(String className) {
		this.wrappedLogger = java.util.logging.Logger.getLogger(className);
	}

	public void debug(String message) {

		if (isDebugEnabled()) {
			LogRecord logRecord = getLogRecord(java.util.logging.Level.FINE, message, null);
			wrappedLogger.log(logRecord);
		}
	}

	public void debug(String message, Object... arguments) {

		if (isDebugEnabled()) {
			Throwable throwable = getThrowable(arguments);
			String formattedMessage = formatMessage(message, arguments);
			LogRecord logRecord = getLogRecord(java.util.logging.Level.FINE, formattedMessage, throwable);
			wrappedLogger.log(logRecord);
		}

	}

	public void error(Throwable throwable) {

		if (isErrorEnabled()) {
			String message = throwable.getMessage();
			LogRecord logRecord = getLogRecord(java.util.logging.Level.SEVERE, message, throwable);
			wrappedLogger.log(logRecord);
		}
	}

	public void error(String message) {

		if (isErrorEnabled()) {
			LogRecord logRecord = getLogRecord(java.util.logging.Level.SEVERE, message, null);
			wrappedLogger.log(logRecord);
		}
	}

	public void error(String message, Object... arguments) {

		if (isErrorEnabled()) {
			Throwable throwable = getThrowable(arguments);
			String formattedMessage = formatMessage(message, arguments);
			LogRecord logRecord = getLogRecord(java.util.logging.Level.SEVERE, formattedMessage, throwable);
			wrappedLogger.log(logRecord);
		}
	}

	public void info(String message) {

		if (isInfoEnabled()) {
			LogRecord logRecord = getLogRecord(java.util.logging.Level.INFO, message, null);
			wrappedLogger.log(logRecord);
		}
	}

	public void info(String message, Object... arguments) {

		if (isInfoEnabled()) {
			String formattedMessage = formatMessage(message, arguments);
			Throwable throwable = getThrowable(arguments);
			LogRecord logRecord = getLogRecord(java.util.logging.Level.INFO, formattedMessage, throwable);
			wrappedLogger.log(logRecord);
		}
	}

	public void trace(String message) {

		if (isTraceEnabled()) {
			LogRecord logRecord = getLogRecord(java.util.logging.Level.FINEST, message, null);
			wrappedLogger.log(logRecord);
		}
	}

	public void trace(String message, Object... arguments) {

		if (isTraceEnabled()) {
			String formattedMessage = formatMessage(message, arguments);
			Throwable throwable = getThrowable(arguments);
			LogRecord logRecord = getLogRecord(java.util.logging.Level.FINEST, formattedMessage, throwable);
			wrappedLogger.log(logRecord);
		}
	}

	public void warn(String message) {

		if (isWarnEnabled()) {
			LogRecord logRecord = getLogRecord(java.util.logging.Level.WARNING, message, null);
			wrappedLogger.log(logRecord);
		}
	}

	public void warn(String message, Object... arguments) {

		if (isWarnEnabled()) {
			Throwable throwable = getThrowable(arguments);
			String formattedMessage = formatMessage(message, arguments);
			LogRecord logRecord = getLogRecord(java.util.logging.Level.WARNING, formattedMessage, throwable);
			wrappedLogger.log(logRecord);
		}
	}

	protected String formatMessage(String message, Object[] arguments) {

		if ((message == null) || (arguments == null) || (arguments.length == 0)) {
			return message;
		}
		else {
			List<Object> argumentList = new ArrayList<Object>();

			for (Object argument : arguments) {

				if ((argument == null) || (argument instanceof Exception)) {
					argumentList.add(null);
				}
				else {

					if (argument.getClass().isArray()) {
						Object[] argArray = (Object[]) argument;
						StringBuilder arrayAsString = new StringBuilder("L[");
						boolean firstArg = true;

						for (Object arg : argArray) {

							if (firstArg) {
								firstArg = false;
							}
							else {
								arrayAsString.append(", ");
							}

							arrayAsString.append(arg);
						}

						arrayAsString.append("]");
						argumentList.add(arrayAsString.toString());
					}
					else {
						argumentList.add(argument);
					}
				}
			}

			String formattedMessage = message;

			try {

				// MessageFormat requires single quote (apostrophe characters) to be escaped.
				if (message.indexOf("'") >= 0) {
					message = message.replaceAll("'", "''");
				}

				formattedMessage = MessageFormat.format(message, argumentList.toArray(new Object[] {}));
			}
			catch (IllegalArgumentException e) {
				System.err.println("ERROR " + e.getClass() + ": " + e.getMessage() + ": " + message);
			}

			return formattedMessage;
		}
	}

	public boolean isDebugEnabled() {
		return wrappedLogger.isLoggable(java.util.logging.Level.FINE);
	}

	public boolean isErrorEnabled() {
		return wrappedLogger.isLoggable(java.util.logging.Level.SEVERE);
	}

	public boolean isInfoEnabled() {
		return wrappedLogger.isLoggable(java.util.logging.Level.INFO);
	}

	public boolean isTraceEnabled() {
		return wrappedLogger.isLoggable(java.util.logging.Level.FINEST);
	}

	public boolean isWarnEnabled() {
		return wrappedLogger.isLoggable(java.util.logging.Level.WARNING);
	}

	protected LogRecord getLogRecord(java.util.logging.Level level, String message, Throwable thrown) {

		// Create a new LogRecord instance.
		LogRecord logRecord = new LogRecord(level, message);

		// Determine the source class name and source method name.
		Throwable source = new Throwable();
		StackTraceElement[] stackTraceElements = source.getStackTrace();
		StackTraceElement callerStackTraceElement = stackTraceElements[2];

		// Set the source class name and source method name.
		logRecord.setSourceClassName(callerStackTraceElement.getClassName());
		logRecord.setSourceMethodName(callerStackTraceElement.getMethodName());

		// If specified, set the throwable associated with the log event.
		if (thrown != null) {
			logRecord.setThrown(thrown);
		}

		// Return the new LogRecord instance.
		return logRecord;
	}

	protected Throwable getThrowable(Object[] arguments) {
		Throwable throwable = null;

		if (arguments != null) {

			for (Object arg : arguments) {

				if (arg != null) {

					if (arg instanceof Throwable) {
						throwable = (Throwable) arg;

						break;
					}
				}
			}
		}

		return throwable;
	}

}
