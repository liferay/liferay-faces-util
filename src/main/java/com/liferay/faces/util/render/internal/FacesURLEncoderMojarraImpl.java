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
package com.liferay.faces.util.render.internal;

import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Method;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.FacesURLEncoder;


/**
 * @author  Kyle Stiemann
 */
public class FacesURLEncoderMojarraImpl implements FacesURLEncoder, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2057180600133838130L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesURLEncoderMojarraImpl.class);

	// Private Final Data Members
	private final Method mojarraMethodWriteURL;

	public FacesURLEncoderMojarraImpl(Method mojarraMethodWriteURL) {
		this.mojarraMethodWriteURL = mojarraMethodWriteURL;
	}

	@Override
	public String encode(String url, String encoding) {

		String encodedURL = url;

		if (url != null) {

			try {

				StringWriter stringWriter = new StringWriter();
				char[] urlBuf = new char[url.length() * 2];
				mojarraMethodWriteURL.invoke(null, stringWriter, url, urlBuf, encoding);
				stringWriter.flush();
				encodedURL = stringWriter.toString();
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return encodedURL;
	}
}
