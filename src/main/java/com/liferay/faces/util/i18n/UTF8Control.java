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
package com.liferay.faces.util.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;


/**
 * Control for getting resource bundles with UTF8 encoding. Based on default Control impl.
 *
 * @author  Juan Gonzalez
 */
public class UTF8Control extends Control {
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
		throws IllegalAccessException, InstantiationException, IOException {

		String bundleName = toBundleName(baseName, locale);
		ResourceBundle bundle = null;

		final String resourceName = toResourceName(bundleName, "properties");
		final ClassLoader classLoader = loader;
		final boolean reloadFlag = reload;
		InputStream stream = null;

		if (reloadFlag) {
			URL url = classLoader.getResource(resourceName);

			if (url != null) {
				URLConnection connection = url.openConnection();

				if (connection != null) {
					connection.setUseCaches(false);
					stream = connection.getInputStream();
				}
			}
		}
		else {
			stream = classLoader.getResourceAsStream(resourceName);
		}

		if (stream != null) {

			try {
				bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
			}
			finally {
				stream.close();
			}
		}

		return bundle;
	}
}
