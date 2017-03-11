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
package com.liferay.faces.util.client;

/**
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author  Brian Wing Shun Chan
 */
public interface BrowserSniffer {

	public boolean acceptsGzip();

	public String getBrowserId();

	public float getMajorVersion();

	public String getRevision();

	public String getVersion();

	public boolean isAir();

	public boolean isAndroid();

	public boolean isChrome();

	public boolean isFirefox();

	public boolean isGecko();

	public boolean isIe();

	public boolean isIeOnWin32();

	public boolean isIeOnWin64();

	public boolean isIpad();

	public boolean isIphone();

	public boolean isLinux();

	public boolean isMac();

	public boolean isMobile();

	public boolean isMozilla();

	public boolean isOpera();

	public boolean isRtf();

	public boolean isSafari();

	public boolean isSun();

	public boolean isWebKit();

	public boolean isWindows();
}
