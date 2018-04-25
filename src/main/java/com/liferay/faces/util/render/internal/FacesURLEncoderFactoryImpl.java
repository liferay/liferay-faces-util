/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
import java.io.Writer;
import java.lang.reflect.Method;

import com.liferay.faces.util.render.FacesURLEncoder;
import com.liferay.faces.util.render.FacesURLEncoderFactory;


/**
 * @author  Neil Griffin
 */
public class FacesURLEncoderFactoryImpl extends FacesURLEncoderFactory implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 6782130515246024964L;

	// Private Final Data Members
	private final FacesURLEncoder facesURLEncoder;

	public FacesURLEncoderFactoryImpl() {

		Method mojarraMethodWriteURL = null;
		Method myFacesMethodEncodeURIAtribute = null;

		try {

			final String MOJARRA_ENCODER_FQCN = "com.sun.faces.util.HtmlUtils";
			Class<?> mojarraHtmlUtilsClass = Class.forName(MOJARRA_ENCODER_FQCN);
			final String MOJARRA_WRITE_URL_METHOD_NAME = "writeURL";
			mojarraMethodWriteURL = mojarraHtmlUtilsClass.getMethod(MOJARRA_WRITE_URL_METHOD_NAME,
					new Class[] { Writer.class, String.class, char[].class, String.class });
		}
		catch (Exception e1) {

			try {

				final String MYFACES_ENCODER_FQCN = "org.apache.myfaces.shared.renderkit.html.util.HTMLEncoder";
				Class<?> myFacesHTMLEncoderClass = Class.forName(MYFACES_ENCODER_FQCN);
				final String MYFACES_ENCODE_URI_ATTRIBUTE_METHOD_NAME = "encodeURIAtributte";
				myFacesMethodEncodeURIAtribute = myFacesHTMLEncoderClass.getMethod(
						MYFACES_ENCODE_URI_ATTRIBUTE_METHOD_NAME, new Class[] { String.class, String.class });
			}
			catch (Exception e2) {
				// Ignore
			}
		}

		if (mojarraMethodWriteURL != null) {
			facesURLEncoder = new FacesURLEncoderMojarraImpl(mojarraMethodWriteURL);
		}
		else if (myFacesMethodEncodeURIAtribute != null) {
			facesURLEncoder = new FacesURLEncoderMyFacesImpl(myFacesMethodEncodeURIAtribute);
		}
		else {
			facesURLEncoder = new FacesURLEncoderImpl();
		}
	}

	@Override
	public FacesURLEncoder getFacesURLEncoder() {
		return facesURLEncoder;
	}

	@Override
	public FacesURLEncoderFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
