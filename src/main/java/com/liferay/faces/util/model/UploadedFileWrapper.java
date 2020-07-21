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
package com.liferay.faces.util.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import javax.faces.FacesWrapper;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * @author  Neil Griffin
 */
@ConsumerType
public abstract class UploadedFileWrapper implements UploadedFile, FacesWrapper<UploadedFile> {

	public abstract UploadedFile getWrapped();

	public void delete() throws IOException {
		getWrapped().delete();
	}

	public String getAbsolutePath() {
		return getWrapped().getAbsolutePath();
	}

	public Map<String, Object> getAttributes() {
		return getWrapped().getAttributes();
	}

	public byte[] getBytes() throws IOException {
		return getWrapped().getBytes();
	}

	public String getCharSet() {
		return getWrapped().getCharSet();
	}

	public String getContentType() {
		return getWrapped().getContentType();
	}

	public String getHeader(String name) {
		return getWrapped().getHeader(name);
	}

	public Collection<String> getHeaderNames() {
		return getWrapped().getHeaderNames();
	}

	public Collection<String> getHeaders(String name) {
		return getWrapped().getHeaders(name);
	}

	public String getId() {
		return getWrapped().getId();
	}

	public InputStream getInputStream() throws IOException {
		return getWrapped().getInputStream();
	}

	public String getMessage() {
		return getWrapped().getMessage();
	}

	public String getName() {
		return getWrapped().getName();
	}

	public long getSize() {
		return getWrapped().getSize();
	}

	public Status getStatus() {
		return getWrapped().getStatus();
	}

	public void write(String fileName) throws IOException {
		getWrapped().write(fileName);
	}
}
