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
package com.liferay.faces.util.logging;

/**
 * @author  Neil Griffin
 */
public interface Logger {

	public static final String SEPARATOR = "----------------------------------------------------------------------";

	public void debug(String message);

	public void debug(String message, Object... arguments);

	public void error(String message);

	public void error(Throwable throwable);

	public void error(String message, Object... arguments);

	public void info(String message);

	public void info(String message, Object... arguments);

	public void trace(String message);

	public void trace(String message, Object... arguments);

	public void warn(String message);

	public void warn(String message, Object... arguments);

	public boolean isDebugEnabled();

	public boolean isErrorEnabled();

	public boolean isInfoEnabled();

	public boolean isTraceEnabled();

	public boolean isWarnEnabled();
}
