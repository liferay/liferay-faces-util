/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.util.render.internal;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.FacesURLEncoder;


/**
 * @author  Neil Griffin
 */
public class FacesURLEncoderImpl implements FacesURLEncoder {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesURLEncoderImpl.class);

	// Private Data Members
	private Method mojarraMethodWriteURL;
	private Method myFacesMethodEncodeURIAtribute;

	public FacesURLEncoderImpl(Method mojarraMethodWriteURL, Method myFacesMethodEncodeURIAtribute) {
		this.mojarraMethodWriteURL = mojarraMethodWriteURL;
		this.myFacesMethodEncodeURIAtribute = myFacesMethodEncodeURIAtribute;
	}

	public String encode(String url, String encoding) {
		String encodedURL = url;

		if (url != null) {

			try {

				if (mojarraMethodWriteURL != null) {
					StringWriter stringWriter = new StringWriter();
					char[] urlBuf = new char[url.length() * 2];
					mojarraMethodWriteURL.invoke(null, stringWriter, url, urlBuf, encoding);
					stringWriter.flush();
					encodedURL = stringWriter.toString();
				}
				else if (myFacesMethodEncodeURIAtribute != null) {
					encodedURL = (String) myFacesMethodEncodeURIAtribute.invoke(null, url, encoding);
				}
				else {
					encodedURL = URLEncoder.encode(url, encoding);
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return encodedURL;
	}
}
