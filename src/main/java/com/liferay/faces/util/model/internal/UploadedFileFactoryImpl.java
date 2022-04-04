/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.FileUploadIOException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.InvalidFileNameException;

import com.liferay.faces.util.model.UploadedFile;
import com.liferay.faces.util.model.UploadedFileFactory;


/**
 * @author  Neil Griffin
 */
public class UploadedFileFactoryImpl extends UploadedFileFactory implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1276552178390469143L;

	@Override
	public UploadedFile getUploadedFile(Exception e) {

		UploadedFile uploadedFile;

		if (e.getClass().getName().startsWith("org.apache.commons.fileupload.")) {
			uploadedFile = CommonsFileUploadErrorUtil.getUploadedFile(e);
		}
		else {
			uploadedFile = new UploadedFileErrorImpl(e);
		}

		return uploadedFile;
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

	private static final class CommonsFileUploadErrorUtil {

		private CommonsFileUploadErrorUtil() {
			throw new AssertionError();
		}

		private static UploadedFile getUploadedFile(Throwable t) {

			FileUploadException fileUploadException = null;

			if ((t instanceof FileUploadBase.IOFileUploadException) || (t instanceof FileUploadIOException)) {

				Throwable causeThrowable = t.getCause();

				if (causeThrowable != null) {
					t = causeThrowable;
				}

				if (t instanceof FileUploadException) {
					fileUploadException = (FileUploadException) t;
				}
			}
			else if (t instanceof FileUploadException) {
				fileUploadException = (FileUploadException) t;
			}

			UploadedFile uploadedFile;

			if (fileUploadException instanceof SizeLimitExceededException) {
				uploadedFile = new UploadedFileErrorImpl(fileUploadException,
						UploadedFile.Status.REQUEST_SIZE_LIMIT_EXCEEDED);
			}
			else if (fileUploadException instanceof FileSizeLimitExceededException) {
				uploadedFile = new UploadedFileErrorImpl(fileUploadException,
						UploadedFile.Status.FILE_SIZE_LIMIT_EXCEEDED);
			}
			else if (t instanceof InvalidFileNameException) {
				uploadedFile = new UploadedFileErrorImpl(t, UploadedFile.Status.FILE_INVALID_NAME_PATTERN);
			}
			else if (fileUploadException != null) {
				uploadedFile = new UploadedFileErrorImpl(fileUploadException);
			}
			else {
				uploadedFile = new UploadedFileErrorImpl(t);
			}

			return uploadedFile;
		}
	}
}
