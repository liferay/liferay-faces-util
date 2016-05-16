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
package com.liferay.faces.util.product.internal;

import com.liferay.faces.util.product.Product;


/**
 * @author  Neil Griffin
 */
public class ProductBaseImpl implements Product {

	// Private Constants
	private static final String REGEX_VERSION_DELIMITER = "[.[-]_]";

	// Protected Data Members
	protected int buildId;
	protected boolean detected;
	protected int majorVersion;
	protected int minorVersion;
	protected int revisionVersion;
	protected String stringValue;
	protected String title;
	protected String version;

	@Override
	public String toString() {

		if (stringValue == null) {
			StringBuilder buf = new StringBuilder();

			if (title != null) {
				buf.append(title);
				buf.append(" ");
			}

			if (version != null) {
				buf.append(version);
			}

			stringValue = buf.toString();
		}

		return stringValue;
	}

	protected void init(Class<?> clazz, String expectedTitle) {

		Package pkg = clazz.getPackage();

		if ((pkg != null) && (pkg.getImplementationVersion() != null)) {
			this.title = pkg.getImplementationTitle();

			if (this.title == null) {
				this.title = expectedTitle;
			}

			initVersionInfo(pkg.getImplementationVersion());
		}
		else {
			PackageManifest packageManifest = new PackageManifest(clazz, expectedTitle);
			this.title = packageManifest.getImplementationTitle();
			initVersionInfo(packageManifest.getImplementationVersion());
		}

		if (this.majorVersion > 0) {
			detected = true;
		}
	}

	protected void initVersionInfo(String version) {

		this.version = version;

		String[] versionParts = version.split(REGEX_VERSION_DELIMITER);

		if (versionParts != null) {

			if (versionParts.length > 0) {
				majorVersion = parseInt(versionParts[0]);
			}

			if (versionParts.length > 1) {
				minorVersion = parseInt(versionParts[1]);
			}

			if (versionParts.length > 2) {
				revisionVersion = parseInt(versionParts[2]);
			}
		}
	}

	protected int parseInt(String value) {
		int intValue = 0;

		try {
			intValue = Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			// ignore
		}

		return intValue;
	}

	public int getBuildId() {
		return buildId;
	}

	public boolean isDetected() {
		return detected;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	protected Package getPackage() {
		return null;
	}

	public int getRevisionVersion() {
		return revisionVersion;
	}

	public String getTitle() {
		return title;
	}

	public String getVersion() {
		return version;
	}
}
