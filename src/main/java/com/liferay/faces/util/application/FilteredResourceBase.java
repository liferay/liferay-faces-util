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
package com.liferay.faces.util.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.faces.application.ResourceWrapper;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides an extension point that allows developers to filter the text of a wrapped JSF resource.
 *
 * @author  Kyle Stiemann
 */
public abstract class FilteredResourceBase extends ResourceWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FilteredResourceBase.class);

	/**
	 * Since this method is not supplied by the {@link ResourceWrapper} class it has to be implemented here.
	 */
	@Override
	public String getContentType() {
		return getWrapped().getContentType();
	}

	@Override
	public InputStream getInputStream() throws IOException {

		InputStream inputStream = super.getInputStream();

		if (inputStream != null) {

			String contentType = getContentType();

			// Only filter text resources. Content type prefixes were obtained from here:
			// http://www.iana.org/assignments/media-types/media-types.xhtml
			if ((contentType == null) ||
					!(contentType.startsWith("audio") || contentType.startsWith("image") ||
						contentType.startsWith("model") || contentType.startsWith("video"))) {

				if ((contentType == null) || !contentType.startsWith("text")) {
					logger.debug("Content-Type is \"{0}\" which may not be a filterable text content type.",
						contentType);
				}

				Map<String, String> responseHeaders = getResponseHeaders();
				String encoding = responseHeaders.get("Content-Encoding");

				if (encoding == null) {
					encoding = getEncoding();
				}

				String inputStreamAsString = ResourceUtil.toString(inputStream, encoding, getBufferSize());
				inputStreamAsString = filter(inputStreamAsString);
				inputStream = ResourceUtil.toInputStream(inputStreamAsString, encoding);
			}
			else {
				logger.warn("Resource not filtered because it's Content-Type=[{0}] which is not a text content type.",
					contentType);
			}
		}

		return inputStream;
	}

	/**
	 * Since this method is not supplied by the {@link ResourceWrapper} class it has to be implemented here.
	 */
	@Override
	public String getLibraryName() {
		return getWrapped().getLibraryName();
	}

	/**
	 * Since this method is not supplied by the {@link ResourceWrapper} class it has to be implemented here.
	 */
	@Override
	public String getResourceName() {
		return getWrapped().getResourceName();
	}

	/**
	 * Since this method is not supplied by the {@link ResourceWrapper} class it has to be implemented here.
	 */
	@Override
	public void setContentType(String contentType) {
		getWrapped().setContentType(contentType);
	}

	/**
	 * Since this method is not supplied by the {@link ResourceWrapper} class it has to be implemented here.
	 */
	@Override
	public void setLibraryName(String libraryName) {
		getWrapped().setLibraryName(libraryName);
	}

	/**
	 * Since this method is not supplied by the {@link ResourceWrapper} class it has to be implemented here.
	 */
	@Override
	public void setResourceName(String resourceName) {
		getWrapped().setResourceName(resourceName);
	}

	/**
	 * Since this method is not supplied by the {@link ResourceWrapper} class it has to be implemented here.
	 */
	@Override
	public String toString() {
		return getWrapped().toString();
	}

	/**
	 * This is an abstract method that allows developers to filter the contents of a resource (typically via {@link
	 * String#replace(char, char)} etc.) before it is written to the response.
	 *
	 * @param   resourceText  The string containing the text content of the resource.
	 *
	 * @return  the filtered contents of the resource.
	 */
	protected abstract String filter(String resourceText);

	/**
	 * Returns the character buffer size used to convert the wrapped resource's {@link InputStream} to a string.
	 * Override this method to provide a different buffer size than the default (1024).
	 */
	protected int getBufferSize() {
		return 1024;
	}

	/**
	 * Returns the encoding of the wrapped resource's {@link InputStream}. If the Content-Encoding request header is not
	 * specified, the encoding returned from this method is used to read the InputStream. Override this method to
	 * provide a different encoding than the default ("UTF-8").
	 */
	protected String getEncoding() {
		return "UTF-8";
	}
}
