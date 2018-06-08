/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.helper.IntegerHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Kyle Stiemann
 */
public class ProductInfo {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ProductInfo.class);

	// Private Constants
	public static final String REGEX_VERSION_DELIMITER = "[.[-]_]";

	// Public Final Data Members
	public final int buildId;
	public final boolean detected;
	public final int majorVersion;
	public final int minorVersion;
	public final int patchVersion;
	public final String stringValue;
	public final String title;
	public final String version;

	public ProductInfo(String title) {
		this(title, null, (Integer) null);
	}

	public ProductInfo(boolean detected, String title) {
		this(detected, title, (String) null);
	}

	public ProductInfo(String title, String version) {
		this(title, version, (Integer) null);
	}

	public ProductInfo(String title, String version, String stringValue) {
		this(true, title, version, stringValue, null, null, null, null);
	}

	public ProductInfo(boolean detected, String title, String version) {
		this(detected, title, version, null);
	}

	public ProductInfo(String title, String version, Integer buildId) {
		this(title, version, null, null, null, buildId);
	}

	public ProductInfo(boolean detected, String title, String version, Integer buildId) {
		this(detected, title, version, null, null, null, buildId);
	}

	public ProductInfo(String title, String version, Integer majorVersion, Integer minorVersion, Integer patchVersion,
		Integer buildId) {
		this(true, title, version, null, majorVersion, minorVersion, patchVersion, buildId);
	}

	public ProductInfo(boolean detected, String title, String version, Integer majorVersion, Integer minorVersion,
		Integer patchVersion, Integer buildId) {
		this(detected, title, version, null, majorVersion, minorVersion, patchVersion, buildId);
	}

	public ProductInfo(boolean detected, String title, String version, String stringValue, Integer majorVersion,
		Integer minorVersion, Integer patchVersion, Integer buildId) {
		this.detected = detected;
		this.title = title;
		this.version = (version != null) ? version : "0.0.0";

		if ((majorVersion == null) || (minorVersion == null) || (patchVersion == null)) {

			String[] versionParts = this.version.split(REGEX_VERSION_DELIMITER);

			if (majorVersion == null) {
				majorVersion = getSubVersion(versionParts, 0);
			}

			if (minorVersion == null) {
				minorVersion = getSubVersion(versionParts, 1);
			}

			if (patchVersion == null) {
				patchVersion = getSubVersion(versionParts, 2);
			}
		}

		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.patchVersion = patchVersion;
		this.buildId = (buildId != null) ? buildId : ((majorVersion * 100) + (minorVersion * 10) + patchVersion);
		this.stringValue = (stringValue != null) ? stringValue : (title + " " + this.version);
	}

	public static int getMajorVersion(String version) {

		String[] versionParts = version.split(ProductInfo.REGEX_VERSION_DELIMITER);

		return getSubVersion(versionParts, 0);
	}

	public static ProductInfo newInstance(String expectedTitle, String className) {
		return newInstance(expectedTitle, className, null);
	}

	public static ProductInfo newInstance(String expectedTitle, Class<?> clazz) {
		return newInstance(true, expectedTitle, clazz, null, null, true);
	}

	public static ProductInfo newInstance(String expectedTitle, String className, String pomPropertiesFile) {
		return newInstance(expectedTitle, className, pomPropertiesFile, null, true);
	}

	public static ProductInfo newInstance(String expectedTitle, String className, boolean warnOnFail) {
		return newInstance(expectedTitle, className, null, null, warnOnFail);
	}

	private static int getSubVersion(String[] versionParts, int index) {

		int subVersion = 0;

		if (versionParts.length > index) {
			subVersion = IntegerHelper.toInteger(versionParts[index]);
		}

		return subVersion;
	}

	private static ProductInfo newInstance(String expectedTitle, String className, String pomPropertiesFile,
		Integer buildId, boolean warnOnFail) {

		ProductInfo productInfo = null;
		boolean detected = false;
		String title = expectedTitle;

		try {

			Class<?> clazz = Class.forName(className);
			detected = true;
			productInfo = newInstance(detected, title, clazz, pomPropertiesFile, buildId, warnOnFail);
		}
		catch (Exception e) {
			// Ignore -- product likely not present.
		}

		if (productInfo == null) {
			productInfo = new ProductInfo(detected, title);
		}

		return productInfo;
	}

	private static ProductInfo newInstance(boolean detected, String expectedTitle, Class<?> clazz,
		String pomPropertiesFile, Integer buildId, boolean warnOnFail) {

		String title = expectedTitle;
		String version = null;
		Package pkg = clazz.getPackage();

		if ((pkg != null) && (pkg.getImplementationVersion() != null)) {

			String implementationTitle = pkg.getImplementationTitle();

			if (implementationTitle != null) {
				title = implementationTitle;
			}

			version = pkg.getImplementationVersion();
		}

		if (version == null) {

			PackageManifest packageManifest = new PackageManifest(clazz, expectedTitle);
			version = packageManifest.getImplementationVersion();
		}

		if ((version == null) && (pomPropertiesFile != null)) {

			PomProperties pomProperties = new PomProperties(clazz, pomPropertiesFile);
			version = pomProperties.getVersion();
		}

		if ((version == null) && warnOnFail) {
			logger.warn("Unable to obtain version information for {0}.", title);
		}

		return new ProductInfo(detected, title, version, buildId);
	}
}
