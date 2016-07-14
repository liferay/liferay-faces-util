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
package com.liferay.faces.util.model;

import java.util.List;
import java.util.Map;

import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class UploadedFileFactory implements Wrapper<UploadedFileFactory> {

	/**
	 * Returns a new instance of {@link UploadedFile} from the {@link UploadedFileFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is guaranteed to be {@link java.io.Serializable} but not
	 * guaranteed to be thread-safe.
	 */
	public static UploadedFile getUploadedFileInstance(Exception e) {

		UploadedFileFactory uploadedFileFactory = (UploadedFileFactory) FactoryExtensionFinder.getFactory(
				UploadedFileFactory.class);

		return uploadedFileFactory.getUploadedFile(e);
	}

	/**
	 * Returns a new instance of {@link UploadedFile} from the {@link UploadedFileFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is guaranteed to be {@link java.io.Serializable} but not
	 * guaranteed to be thread-safe.
	 */
	public static UploadedFile getUploadedFileInstance(String absolutePath, Map<String, Object> attributes,
		String charSet, String contentType, Map<String, List<String>> headers, String id, String message, String name,
		long size, UploadedFile.Status status) {

		UploadedFileFactory uploadedFileFactory = (UploadedFileFactory) FactoryExtensionFinder.getFactory(
				UploadedFileFactory.class);

		return uploadedFileFactory.getUploadedFile(absolutePath, attributes, charSet, contentType, headers, id, message,
				name, size, status);
	}

	/**
	 * Returns a new instance of {@link UploadedFile}. The returned instance is guaranteed to be {@link
	 * java.io.Serializable} but not guaranteed to be thread-safe.
	 */
	public abstract UploadedFile getUploadedFile(Exception e);

	/**
	 * Returns a new instance of {@link UploadedFile}. The returned instance is guaranteed to be {@link
	 * java.io.Serializable} but not guaranteed to be thread-safe.
	 */
	public abstract UploadedFile getUploadedFile(String absolutePath, Map<String, Object> attributes, String charSet,
		String contentType, Map<String, List<String>> headers, String id, String message, String name, long size,
		UploadedFile.Status status);

	/**
	 * Returns the wrapped factory instance if this factory has been decorated. Otherwise, this method returns null.
	 */
	@Override
	public abstract UploadedFileFactory getWrapped();
}
