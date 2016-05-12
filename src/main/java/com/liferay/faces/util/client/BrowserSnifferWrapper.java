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
package com.liferay.faces.util.client;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BrowserSnifferWrapper implements BrowserSniffer, Wrapper<BrowserSniffer> {

	@Override
	public boolean acceptsGzip() {
		return getWrapped().acceptsGzip();
	}

	@Override
	public boolean isIeOnWin32() {
		return getWrapped().isIeOnWin32();
	}

	@Override
	public boolean isIeOnWin64() {
		return getWrapped().isIeOnWin64();
	}

	@Override
	public boolean isMozilla() {
		return getWrapped().isMozilla();
	}

	@Override
	public boolean isOpera() {
		return getWrapped().isOpera();
	}

	@Override
	public String getBrowserId() {
		return getWrapped().getBrowserId();
	}

	@Override
	public boolean isMac() {
		return getWrapped().isMac();
	}

	@Override
	public boolean isAndroid() {
		return getWrapped().isAndroid();
	}

	@Override
	public boolean isChrome() {
		return getWrapped().isChrome();
	}

	@Override
	public boolean isIe() {
		return getWrapped().isIe();
	}

	@Override
	public boolean isIphone() {
		return getWrapped().isIphone();
	}

	@Override
	public boolean isMobile() {
		return getWrapped().isMobile();
	}

	@Override
	public boolean isRtf() {
		return getWrapped().isRtf();
	}

	@Override
	public boolean isSafari() {
		return getWrapped().isSafari();
	}

	@Override
	public boolean isWapXhtml() {
		return getWrapped().isWapXhtml();
	}

	@Override
	public boolean isWml() {
		return getWrapped().isWml();
	}

	@Override
	public float getMajorVersion() {
		return getWrapped().getMajorVersion();
	}

	@Override
	public boolean isSun() {
		return getWrapped().isSun();
	}

	@Override
	public boolean isGecko() {
		return getWrapped().isGecko();
	}

	@Override
	public boolean isWap() {
		return getWrapped().isWap();
	}

	@Override
	public boolean isAir() {
		return getWrapped().isAir();
	}

	@Override
	public String getRevision() {
		return getWrapped().getRevision();
	}

	@Override
	public boolean isWindows() {
		return getWrapped().isWindows();
	}

	@Override
	public boolean isWebKit() {
		return getWrapped().isWebKit();
	}

	@Override
	public String getVersion() {
		return getWrapped().getVersion();
	}

	@Override
	public abstract BrowserSniffer getWrapped();

	@Override
	public boolean isFirefox() {
		return getWrapped().isFirefox();
	}

	@Override
	public boolean isLinux() {
		return getWrapped().isLinux();
	}

}
