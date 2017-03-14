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
package com.liferay.faces.util.application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


/**
 * @author  Kyle Stiemann
 */
public final class ApplicationUtil {

	private ApplicationUtil() {
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getOrCreateApplicationCache(FacesContext facesContext, String cacheName) {
		Map<String, Object> applicationMap = facesContext.getExternalContext().getApplicationMap();

		Map<String, Object> cache = (Map<String, Object>) applicationMap.get(cacheName);

		if (cache == null) {
			cache = new ConcurrentHashMap<String, Object>();
			applicationMap.put(cacheName, cache);
		}

		return cache;
	}

	/**
	 * Returns true if the JSF application is starting up or shutting down.
	 */
	public static boolean isStartupOrShutdown(FacesContext facesContext) {

		Object request = null;

		try {

			if (facesContext != null) {

				ExternalContext externalContext = facesContext.getExternalContext();

				if (externalContext != null) {
					request = externalContext.getRequest();
				}
			}
		}
		catch (UnsupportedOperationException e) {
			// do nothing
		}

		return (request == null);
	}
}
