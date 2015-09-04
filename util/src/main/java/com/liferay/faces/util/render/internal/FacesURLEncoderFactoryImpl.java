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

import java.io.Writer;
import java.lang.reflect.Method;

import com.liferay.faces.util.render.FacesURLEncoder;
import com.liferay.faces.util.render.FacesURLEncoderFactory;


/**
 * @author  Neil Griffin
 */
public class FacesURLEncoderFactoryImpl extends FacesURLEncoderFactory {

	// Private Constants
	private static final String MOJARRA_ENCODER_FQCN = "com.sun.faces.util.HtmlUtils";
	private static final String MOJARRA_METHOD_WRITE_URL = "writeURL";
	private static final String MYFACES_ENCODER_FQCN = "org.apache.myfaces.shared.renderkit.html.util.HTMLEncoder";
	private static final String MYFACES_METHOD_ENCODE_URI_ATTRIBUTE = "encodeURIAtributte";

	// Private Data Members
	private static Class<?> mojarraHtmlUtilsClass;
	private static Method mojarraMethodWriteURL;
	private static Class<?> myFacesHTMLEncoderClass;
	private static Method myFacesMethodEncodeURIAtribute;

	static {

		try {
			mojarraHtmlUtilsClass = Class.forName(MOJARRA_ENCODER_FQCN);
			mojarraMethodWriteURL = mojarraHtmlUtilsClass.getMethod(MOJARRA_METHOD_WRITE_URL,
					new Class[] { Writer.class, String.class, char[].class, String.class });
		}
		catch (Exception e1) {

			try {
				myFacesHTMLEncoderClass = Class.forName(MYFACES_ENCODER_FQCN);
				myFacesMethodEncodeURIAtribute = myFacesHTMLEncoderClass.getMethod(MYFACES_METHOD_ENCODE_URI_ATTRIBUTE,
						new Class[] { String.class, String.class });
			}
			catch (Exception e2) {
				// Ignore
			}
		}
	}

	@Override
	public FacesURLEncoder getFacesURLEncoder() {
		return new FacesURLEncoderImpl(mojarraMethodWriteURL, myFacesMethodEncodeURIAtribute);
	}

	@Override
	public FacesURLEncoderFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
