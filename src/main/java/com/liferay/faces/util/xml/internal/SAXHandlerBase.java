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
package com.liferay.faces.util.xml.internal;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

/**
 * @author Neil Griffin
 */
public abstract class SAXHandlerBase extends DefaultHandler {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SAXHandlerBase.class);

	// Protected Data Members
	protected StringBuilder content;

	// Private Data Members
	private boolean resolveEntities;
	private URL url;

	public SAXHandlerBase(boolean resolveEntities) {
		super();
		this.resolveEntities = resolveEntities;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		if ((content != null) && (ch != null) && (length > 0)) {
			content.append(ch, start, length);
		}
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {

		InputSource inputSource = new InputSource(new StringReader(""));

		if (resolveEntities) {
			inputSource = super.resolveEntity(publicId, systemId);

			if (inputSource == null) {

				try {

					// Note: Not sure why, but following line of code has suffered terrible performance problems.
					// At times, it could take over a minute for the stream to open. This is why the web.xml
					// default for resolving entities is false.
					inputSource = new InputSource(new URL(systemId).openStream());
				}
				catch (IOException ioException) {

					// Don't bother logging this as a warning or an error, because we can't assume connectivity to
					// the Internet to download a public URL.
					logger.trace("Unable to download publicId=[{0}], systemId=[{1}], referenced-in=[{2}]",
						new Object[] { publicId, systemId, url });
				}
			}
		}

		return inputSource;
	}
}
