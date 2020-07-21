/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.liferay.faces.util.internal.CloseableUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class is designed to work-around a problem with JBoss AS such that the {@link
 * Class#getPackage()#getImplementationVersion()} method returns null during WAR application deployment. For more
 * information, see: http://issues.liferay.com/browse/FACES-1296
 *
 * @author  Neil Griffin
 */
public class PackageManifest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PackageManifest.class);

	// Private Constants
	private static final String MANIFEST_MF_PATH = "META-INF/MANIFEST.MF";

	// Private Data Members
	private String implementationVersion;

	public PackageManifest(Class<?> clazz, String expectedImplementationTitle) {

		InputStream inputStream = null;

		try {

			// For each of the "META-INF/MANIFEST.MF" resources found by the ClassLoader:
			Enumeration<URL> manifestURLs = clazz.getClassLoader().getResources(MANIFEST_MF_PATH);
			boolean found = false;

			while (manifestURLs.hasMoreElements() && !found) {
				URL manifestURL = manifestURLs.nextElement();
				inputStream = manifestURL.openStream();

				Manifest manifest = new Manifest(inputStream);
				Attributes mainAttributes = manifest.getMainAttributes();

				// If the current resource matches the specified title, then retrieve the Implementation-Version.
				if (expectedImplementationTitle.equals(mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE))) {
					implementationVersion = mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
					found = true;
				}

				inputStream.close();
			}
		}
		catch (IOException e) {
			logger.error(e);
		}
		finally {
			CloseableUtil.close(inputStream);
		}
	}

	public String getImplementationVersion() {
		return implementationVersion;
	}
}
