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
package com.liferay.faces.util.client.internal;

import jakarta.servlet.http.HttpServletRequest;

import com.liferay.faces.util.client.BrowserSniffer;

/**
 * @author Neil Griffin
 */
public class BrowserSnifferImpl extends LiferayPortalBrowserSnifferImpl implements BrowserSniffer {

	// Private Data Members
	private HttpServletRequest httpServletRequest;

	public BrowserSnifferImpl(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	@Override
	public boolean acceptsGzip() {
		return acceptsGzip(httpServletRequest);
	}

	@Override
	public String getBrowserId() {
		return getBrowserId(httpServletRequest);
	}

	@Override
	public float getMajorVersion() {
		return getMajorVersion(httpServletRequest);
	}

	@Override
	public String getRevision() {
		return getRevision(httpServletRequest);
	}

	@Override
	public String getVersion() {
		return getVersion(httpServletRequest);
	}

	@Override
	public boolean isAir() {
		return isAir(httpServletRequest);
	}

	@Override
	public boolean isAndroid() {
		return isAndroid(httpServletRequest);
	}

	@Override
	public boolean isChrome() {
		return isChrome(httpServletRequest);
	}

	@Override
	public boolean isFirefox() {
		return isFirefox(httpServletRequest);
	}

	@Override
	public boolean isGecko() {
		return isGecko(httpServletRequest);
	}

	@Override
	public boolean isIe() {
		return isIe(httpServletRequest);
	}

	@Override
	public boolean isIeOnWin32() {
		return isIeOnWin32(httpServletRequest);
	}

	@Override
	public boolean isIeOnWin64() {
		return isIeOnWin64(httpServletRequest);
	}

	@Override
	public boolean isIpad() {
		String userAgent = getUserAgent(httpServletRequest);

		if (userAgent.contains("ipad")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isIphone() {
		return isIphone(httpServletRequest);
	}

	@Override
	public boolean isLinux() {
		return isLinux(httpServletRequest);
	}

	@Override
	public boolean isMac() {
		return isMac(httpServletRequest);
	}

	@Override
	public boolean isMobile() {
		return isMobile(httpServletRequest);
	}

	@Override
	public boolean isMozilla() {
		return isMozilla(httpServletRequest);
	}

	@Override
	public boolean isOpera() {
		return isOpera(httpServletRequest);
	}

	@Override
	public boolean isRtf() {
		return isRtf(httpServletRequest);
	}

	@Override
	public boolean isSafari() {
		return isSafari(httpServletRequest);
	}

	@Override
	public boolean isSun() {
		return isSun(httpServletRequest);
	}

	@Override
	public boolean isWebKit() {
		return isWebKit(httpServletRequest);
	}

	@Override
	public boolean isWindows() {
		return isWindows(httpServletRequest);
	}
}
