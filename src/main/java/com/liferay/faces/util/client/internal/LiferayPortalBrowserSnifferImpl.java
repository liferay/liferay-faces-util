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
package com.liferay.faces.util.client.internal;

import javax.servlet.http.HttpServletRequest;

//J-
/**
 * This class was copied from Liferay Portal in order to utilize the features of the Portal's BrowserSniffer without
 * depending on the Liferay Portal API.
 *
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Eduardo Lundgren
 * @author Nate Cavanaugh
 */
public class LiferayPortalBrowserSnifferImpl extends LiferayPortalBrowserSnifferCompat implements LiferayPortalBrowserSniffer {
	@Override
	public boolean acceptsGzip(HttpServletRequest request) {
		String acceptEncoding = request.getHeader("Accept-Encoding");

		if ((acceptEncoding != null) && acceptEncoding.contains("gzip")) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String getBrowserId(HttpServletRequest request) {
		if (isIe(request)) {
			return BROWSER_ID_IE;
		}
		else if (isFirefox(request)) {
			return BROWSER_ID_FIREFOX;
		}
		else {
			return BROWSER_ID_OTHER;
		}
	}

	@Override
	public float getMajorVersion(HttpServletRequest request) {
		return GetterUtil.getFloat(getVersion(request));
	}

	@Override
	public String getRevision(HttpServletRequest request) {
		String revision = (String)request.getAttribute(
			WebKeys.BROWSER_SNIFFER_REVISION);

		if (revision != null) {
			return revision;
		}

		revision = parseVersion(
			getUserAgent(request), revisionLeadings, revisionSeparators);

		request.setAttribute(WebKeys.BROWSER_SNIFFER_REVISION, revision);

		return revision;
	}

	@Override
	public String getVersion(HttpServletRequest request) {
		String version = (String)request.getAttribute(
			WebKeys.BROWSER_SNIFFER_VERSION);

		if (version != null) {
			return version;
		}

		String userAgent = getUserAgent(request);

		version = parseVersion(userAgent, versionLeadings, versionSeparators);

		if (version.isEmpty()) {
			version = parseVersion(
				userAgent, revisionLeadings, revisionSeparators);
		}

		request.setAttribute(WebKeys.BROWSER_SNIFFER_VERSION, version);

		return version;
	}

	@Override
	public boolean isAir(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("adobeair")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isAndroid(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("android")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isChrome(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("chrome")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isFirefox(HttpServletRequest request) {
		if (!isMozilla(request)) {
			return false;
		}

		String userAgent = getUserAgent(request);

		for (String firefoxAlias : _FIREFOX_ALIASES) {
			if (userAgent.contains(firefoxAlias)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isGecko(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("gecko")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isIe(HttpServletRequest request) {
		return isIe(getUserAgent(request));
	}

	@Override
	public boolean isIeOnWin32(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (isIe(userAgent) &&
			!(userAgent.contains("wow64") || userAgent.contains("win64"))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isIeOnWin64(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (isIe(userAgent) &&
			(userAgent.contains("wow64") || userAgent.contains("win64"))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isIphone(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("iphone")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLinux(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("linux")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMac(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("mac")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMobile(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("mobile") ||
			(isAndroid(request) && userAgent.contains("nexus"))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMozilla(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("mozilla") &&
			!(userAgent.contains("compatible") ||
			  userAgent.contains("webkit"))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isOpera(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("opera")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRtf(HttpServletRequest request) {
		if (isAndroid(request)) {
			return true;
		}

		if (isChrome(request)) {
			return true;
		}

		float majorVersion = getMajorVersion(request);

		if (isIe(request) && (majorVersion >= 5.5)) {
			return true;
		}

		if (isMozilla(request) && (majorVersion >= 1.3)) {
			return true;
		}

		if (isOpera(request)) {
			if (isMobile(request) && (majorVersion >= 10.0)) {
				return true;
			}
			else if (!isMobile(request)) {
				return true;
			}
		}

		if (isSafari(request)) {
			if (isMobile(request) && (majorVersion >= 5.0)) {
				return true;
			}
			else if (!isMobile(request) && (majorVersion >= 3.0)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isSafari(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (isWebKit(request) && userAgent.contains("safari")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSun(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("sunos")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isWebKit(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		for (String webKitAlias : _WEBKIT_ALIASES) {
			if (userAgent.contains(webKitAlias)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isWindows(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		for (String windowsAlias : _WINDOWS_ALIASES) {
			if (userAgent.contains(windowsAlias)) {
				return true;
			}
		}

		return false;
	}

	protected static String parseVersion(
		String userAgent, String[] leadings, char[] separators) {

		leading:
		for (String leading : leadings) {
			int index = 0;

			version:
			while (true) {
				index = userAgent.indexOf(leading, index);

				if ((index < 0) ||
					(((index += leading.length()) + 2) > userAgent.length())) {

					continue leading;
				}

				char c1 = userAgent.charAt(index);
				char c2 = userAgent.charAt(++index);

				if (((c2 >= '0') && (c2 <= '9')) || (c2 == '.')) {
					for (char separator : separators) {
						if (c1 == separator) {
							break version;
						}
					}
				}
			}

			// Major

			int majorStart = index;
			int majorEnd = index + 1;

			for (int i = majorStart; i < userAgent.length(); i++) {
				char c = userAgent.charAt(i);

				if ((c < '0') || (c > '9')) {
					majorEnd = i;

					break;
				}
			}

			String major = userAgent.substring(majorStart, majorEnd);

			if (userAgent.charAt(majorEnd) != '.') {
				return major;
			}

			// Minor

			int minorStart = majorEnd + 1;
			int minorEnd = userAgent.length();

			for (int i = minorStart; i < userAgent.length(); i++) {
				char c = userAgent.charAt(i);

				if ((c < '0') || (c > '9')) {
					minorEnd = i;

					break;
				}
			}

			String minor = userAgent.substring(minorStart, minorEnd);

			return major.concat(".").concat(minor);
		}

		return "";
	}

	protected String getAccept(HttpServletRequest request) {
		String accept = "";

		if (request == null) {
			return accept;
		}

		accept = String.valueOf(request.getAttribute("ACCEPT"));

		if (Validator.isNotNull(accept)) {
			return accept;
		}

		accept = request.getHeader("ACCEPT");

		if (accept != null) {
			accept = StringUtil.toLowerCase(accept);
		}
		else {
			accept = "";
		}

		request.setAttribute("ACCEPT", accept);

		return accept;
	}

	protected String getUserAgent(HttpServletRequest request) {
		String userAgent = "";

		if (request == null) {
			return userAgent;
		}

		userAgent = String.valueOf(
			request.getAttribute("User-Agent"));

		if (Validator.isNotNull(userAgent)) {
			return userAgent;
		}

		userAgent = request.getHeader("User-Agent");

		if (userAgent != null) {
			userAgent = StringUtil.toLowerCase(userAgent);
		}
		else {
			userAgent = "";
		}

		request.setAttribute("User-Agent", userAgent);

		return userAgent;
	}

	protected boolean isIe(String userAgent) {
		if (userAgent.contains("msie") && !userAgent.contains("opera")) {
			return true;
		}

		return false;
	}

	protected static String[] revisionLeadings = {"rv", "it", "ra", "ie"};
	protected static char[] revisionSeparators =
		{CharPool.BACK_SLASH, CharPool.COLON, CharPool.SLASH, CharPool.SPACE};
	protected static String[] versionLeadings =
		{"version", "firefox", "minefield", "chrome"};
	protected static char[] versionSeparators =
		{CharPool.BACK_SLASH, CharPool.SLASH};

	private static final String[] _FIREFOX_ALIASES = {
		"firefox", "minefield", "granparadiso", "bonecho", "firebird",
		"phoenix", "camino"
	};

	private static final String[] _WEBKIT_ALIASES = {"khtml", "applewebkit"};

	private static final String[] _WINDOWS_ALIASES = {
		"windows", "win32", "16bit"
	};
}
//J+
