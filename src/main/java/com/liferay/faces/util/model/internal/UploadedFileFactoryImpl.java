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
package com.liferay.faces.util.model.internal;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.liferay.faces.util.model.UploadedFile;
import com.liferay.faces.util.model.UploadedFileFactory;

/**
 * @author Neil Griffin
 */
public class UploadedFileFactoryImpl extends UploadedFileFactory implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1276552178390469143L;

	@Override
	public UploadedFile getUploadedFile(Exception e) {
		return new UploadedFileErrorImpl(e);
	}

	@Override
	public UploadedFile getUploadedFile(Exception e, UploadedFile.Status uploadedFileStatus) {
		return new UploadedFileErrorImpl(e, uploadedFileStatus);
	}

	@Override
	public UploadedFile getUploadedFile(String absolutePath, Map<String, Object> attributes, String charSet,
		String contentType, Map<String, List<String>> headers, String id, String message, String name, long size,
		UploadedFile.Status status) {
		return new UploadedFileImpl(absolutePath, attributes, charSet, contentType, headers, id, message, name, size,
			status);
	}

	@Override
	public UploadedFileFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
