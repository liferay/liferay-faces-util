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
package com.liferay.faces.util;

/**
 * @author  Brian Wing Shun Chan
 */
public interface HttpHeaders {

	public static final String ACCEPT = "ACCEPT";

	public static final String ACCEPT_ENCODING = "Accept-Encoding";

	public static final String CACHE_CONTROL = "Cache-Control";

	public static final String CACHE_CONTROL_NO_CACHE_VALUE = "private, no-cache, no-store, must-revalidate";

	public static final String CONTENT_DISPOSITION = "Content-Disposition";

	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

	public static final String USER_AGENT = "User-Agent";
}
