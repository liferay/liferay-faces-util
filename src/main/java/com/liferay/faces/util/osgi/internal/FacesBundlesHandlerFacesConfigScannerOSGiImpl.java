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
package com.liferay.faces.util.osgi.internal;

import java.net.URL;
import java.util.List;

import org.osgi.framework.Bundle;

import com.liferay.faces.util.config.internal.FacesConfigScanner;
import com.liferay.faces.util.config.internal.FacesConfigScannerBase;


/**
 * @author  Kyle Stiemann
 */
public class FacesBundlesHandlerFacesConfigScannerOSGiImpl extends FacesBundlesHandlerResourceProviderOSGiImpl {

	public FacesBundlesHandlerFacesConfigScannerOSGiImpl(String path, String facesBundleFilePattern) {
		super(path, facesBundleFilePattern);
	}

	@Override
	protected void handleFacesBundle(Bundle bundle, ReturnValueReference<List<URL>> returnValueReference,
		boolean recurse) {

		List<URL> resourceURLs = returnValueReference.get();
		URL mojarraConfigURL = bundle.getEntry("/" + FacesConfigScannerBase.MOJARRA_CONFIG_PATH);

		if (mojarraConfigURL != null) {
			resourceURLs.add(mojarraConfigURL);
		}

		super.handleFacesBundle(bundle, returnValueReference, recurse);
	}
}
