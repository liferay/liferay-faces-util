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

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;

/**
 * @author Neil Griffin
 */
public class BrowserSnifferFactoryImpl extends BrowserSnifferFactory implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2480449562969899228L;

	@Override
	public BrowserSniffer getBrowserSniffer(ExternalContext externalContext) {

		HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();

		return new BrowserSnifferImpl(httpServletRequest);
	}

	@Override
	public BrowserSnifferFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
