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

import java.util.Map;

import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;


/**
 * This class is a thread-safe implementation of {@link SAXParser}. However, it does not fully implement all of the
 * functionality of the one provided by the JRE.
 *
 * @author  Neil Griffin
 */
public class SAXParserImpl extends SAXParser {

	// Private Data Members
	private boolean namespaceAware;
	private boolean validating;
	private XMLReader xmlReader;

	public SAXParserImpl(boolean namespaceAware, boolean validating, Map<String, Boolean> featureMap) {
		this.namespaceAware = namespaceAware;
		this.validating = validating;
		this.xmlReader = new XMLReaderImpl(featureMap);
	}

	@Override
	@SuppressWarnings("deprecation")
	public org.xml.sax.Parser getParser() throws SAXException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {

		try {
			return getXMLReader().getProperty(name);
		}
		catch (SAXException e) {
			throw new SAXNotRecognizedException(e.getMessage());
		}
	}

	@Override
	public XMLReader getXMLReader() throws SAXException {
		return xmlReader;
	}

	@Override
	public boolean isNamespaceAware() {
		return namespaceAware;
	}

	@Override
	public boolean isValidating() {
		return validating;
	}

	@Override
	public void reset() {
		// This method needs to be overriden in order to prevent the superclass from throwing a
		// UnsupportedOperationException. Since this implementation is thread-safe, it inherently
		// supports the reset functionality.
	}

	@Override
	public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {

		try {
			getXMLReader().setProperty(name, value);
		}
		catch (SAXException e) {
			throw new SAXNotRecognizedException(e.getMessage());
		}
	}

}
