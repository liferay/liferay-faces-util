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
package com.liferay.faces.util.context;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.PartialResponseWriter;

import org.osgi.annotation.versioning.ProviderType;


/**
 * @author  Neil Griffin
 */
@ProviderType
public class PartialResponseWriterWrapper extends PartialResponseWriter {

	// Private Data Members
	private PartialResponseWriter wrappedPartialResponseWriter;

	public PartialResponseWriterWrapper(PartialResponseWriter partialResponseWriter) {
		super(partialResponseWriter.getWrapped());
		this.wrappedPartialResponseWriter = partialResponseWriter;
	}

	@Override
	public void delete(String targetId) throws IOException {
		wrappedPartialResponseWriter.delete(targetId);
	}

	@Override
	public void endDocument() throws IOException {
		wrappedPartialResponseWriter.endDocument();
	}

	@Override
	public void endError() throws IOException {
		wrappedPartialResponseWriter.endError();
	}

	@Override
	public void endEval() throws IOException {
		wrappedPartialResponseWriter.endEval();
	}

	@Override
	public void endExtension() throws IOException {
		wrappedPartialResponseWriter.endExtension();
	}

	@Override
	public void endInsert() throws IOException {
		wrappedPartialResponseWriter.endInsert();
	}

	@Override
	public void endUpdate() throws IOException {
		wrappedPartialResponseWriter.endUpdate();
	}

	@Override
	public void redirect(String url) throws IOException {
		wrappedPartialResponseWriter.redirect(url);
	}

	@Override
	public void startDocument() throws IOException {
		wrappedPartialResponseWriter.startDocument();
	}

	@Override
	public void startError(String errorName) throws IOException {
		wrappedPartialResponseWriter.startError(errorName);
	}

	@Override
	public void startEval() throws IOException {
		wrappedPartialResponseWriter.startEval();
	}

	@Override
	public void startExtension(Map<String, String> attributes) throws IOException {
		wrappedPartialResponseWriter.startExtension(attributes);
	}

	@Override
	public void startInsertAfter(String targetId) throws IOException {
		wrappedPartialResponseWriter.startInsertAfter(targetId);
	}

	@Override
	public void startInsertBefore(String targetId) throws IOException {
		wrappedPartialResponseWriter.startInsertBefore(targetId);
	}

	@Override
	public void startUpdate(String targetId) throws IOException {
		wrappedPartialResponseWriter.startUpdate(targetId);
	}

	@Override
	public void updateAttributes(String targetId, Map<String, String> attributes) throws IOException {
		wrappedPartialResponseWriter.updateAttributes(targetId, attributes);
	}

}
