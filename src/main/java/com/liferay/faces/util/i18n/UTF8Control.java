/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.osgi.annotation.versioning.ProviderType;


/**
 * Control for getting resource bundles with UTF-8 encoding.
 *
 * @deprecated  No replacement available.
 * @author      Juan Gonzalez
 */
@Deprecated
@ProviderType
public class UTF8Control extends ResourceBundle.Control {

	/**
	 * @see  java.util.ResourceBundle.Control#newBundle(java.lang.String, java.util.Locale, java.lang.String,
	 *       java.lang.ClassLoader, boolean)
	 */
	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader classLoader,
		boolean reload) throws IllegalAccessException, InstantiationException, IOException {

		ResourceBundle resourceBundle = null;
		String bundleName = toBundleName(baseName, locale);
		String resourceName = toResourceName(bundleName, "properties");
		InputStream inputStream = null;

		if (reload) {

			URL resourceURL = classLoader.getResource(resourceName);

			if (resourceURL != null) {

				URLConnection urlConnection = resourceURL.openConnection();

				if (urlConnection != null) {
					urlConnection.setUseCaches(false);
					inputStream = urlConnection.getInputStream();
				}
			}
		}
		else {
			inputStream = classLoader.getResourceAsStream(resourceName);
		}

		if (inputStream != null) {

			try {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
				resourceBundle = new PropertyResourceBundle(inputStreamReader);
			}
			finally {
				inputStream.close();
			}
		}

		return resourceBundle;
	}
}
