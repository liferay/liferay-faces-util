/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.context.map;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.map.AbstractPropertyMap;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * This class serves as a layer of abstraction for a {@link Map} of strings, each representing a separate fragment of
 * JavaScript. If ICEfaces is detected, then the implementation delegates to the ICEfaces {@link
 * com.icesoft.faces.context.effects.JavascriptContext} so that ICEfaces can have a chance to render JavaScript
 * fragments. Otherwise, the implementation stores the map as a request attribute.
 *
 * @author  Neil Griffin
 */
public class JavaScriptMap extends AbstractPropertyMap<String> {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(JavaScriptMap.class);

	// Private Constants
	private static final String FQCN_ICEFACES_JS_RUNNER = "com.icesoft.faces.context.effects.JavascriptContext";
	private static final boolean ICEFACES_DETECTED = ProductMap.getInstance().get(ProductConstants.ICEFACES)
		.isDetected();
	private static final String METHOD_RUNSCRIPT = "addJavascriptCall";

	@Override
	protected AbstractPropertyMapEntry<String> createPropertyMapEntry(String name) {
		return new JavaScriptMapEntry(this, name);
	}

	@Override
	protected void removeProperty(String name) {

		if (!ICEFACES_DETECTED) {
			getRequestAttribute().remove(name);
		}
	}

	@Override
	protected String getProperty(String name) {

		if (ICEFACES_DETECTED) {
			return null;
		}
		else {
			return getRequestAttribute().get(name);
		}
	}

	@Override
	protected void setProperty(String name, String value) {

		if (ICEFACES_DETECTED) {

			try {
				Class<?> jsRunnerClass = Class.forName(FQCN_ICEFACES_JS_RUNNER);
				Method runScriptMethod = jsRunnerClass.getMethod(METHOD_RUNSCRIPT,
						new Class[] { FacesContext.class, String.class });
				FacesContext facesContext = FacesContext.getCurrentInstance();
				runScriptMethod.invoke(null, new Object[] { facesContext, value });
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
		else {
			Map<String, String> javaScriptMap = getRequestAttribute();
			javaScriptMap.put(name, value);
		}
	}

	@Override
	protected Enumeration<String> getPropertyNames() {
		return Collections.enumeration(getRequestAttribute().keySet());
	}

	protected Map<String, String> getRequestAttribute() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
		String key = JavaScriptMap.class.getName();
		@SuppressWarnings("unchecked")
		Map<String, String> javaScriptMap = (Map<String, String>) requestMap.get(key);

		if (javaScriptMap == null) {
			javaScriptMap = new HashMap<String, String>();
			requestMap.put(key, javaScriptMap);
		}

		return javaScriptMap;
	}
}
