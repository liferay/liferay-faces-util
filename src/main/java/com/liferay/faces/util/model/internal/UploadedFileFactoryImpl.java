/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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

import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.FileUploadIOException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;

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

		private static UploadedFile getUploadedFile(Exception e) {

			FileUploadException fileUploadException = null;

			if (e instanceof FileUploadException) {
				fileUploadException = (FileUploadException) e;
			}
			else if (e instanceof FileUploadIOException) {

				Throwable causeThrowable = e.getCause();

				if (causeThrowable instanceof FileUploadException) {
					fileUploadException = (FileUploadException) causeThrowable;
				}
			}
			else {
				throw new UnsupportedOperationException();
			}

			UploadedFile uploadedFile;

			if (fileUploadException != null) {

				if (fileUploadException instanceof SizeLimitExceededException) {
					uploadedFile = new UploadedFileErrorImpl(fileUploadException,
							UploadedFile.Status.REQUEST_SIZE_LIMIT_EXCEEDED);
				}
				else if (fileUploadException instanceof FileSizeLimitExceededException) {
					uploadedFile = new UploadedFileErrorImpl(fileUploadException,
							UploadedFile.Status.FILE_SIZE_LIMIT_EXCEEDED);
				}
				else {
					uploadedFile = new UploadedFileErrorImpl(fileUploadException);
				}
			}
			else {
				uploadedFile = new UploadedFileErrorImpl(fileUploadException);
			}

			return uploadedFile;
		}
	}
}
