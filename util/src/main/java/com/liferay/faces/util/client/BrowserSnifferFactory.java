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

import javax.faces.FacesWrapper;
import javax.faces.context.ExternalContext;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
public abstract class BrowserSnifferFactory implements FacesWrapper<BrowserSnifferFactory> {

	/**
	 * @return  an instance of {@link BrowserSniffer} from the {@link BrowserSnifferFactory} found by the {@link
	 *          FactoryExtensionFinder}.
	 */
	public static BrowserSniffer getBrowserSnifferInstance(ExternalContext externalContext) {

		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);

		return browserSnifferFactory.getBrowserSniffer(externalContext);
	}

	public abstract BrowserSniffer getBrowserSniffer(ExternalContext externalContext);
}
