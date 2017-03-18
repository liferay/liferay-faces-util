/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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

import javax.faces.FacesWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
public abstract class UploadedFileFactory implements FacesWrapper<UploadedFileFactory> {

	/**
	 * @deprecated  Call {@link #getUploadedFileInstance(ExternalContext, Exception)} instead.
	 *
	 *              <p>Returns a new instance of {@link UploadedFile} from the {@link UploadedFileFactory} found by the
	 *              {@link FactoryExtensionFinder}. The returned instance is guaranteed to be {@link
	 *              java.io.Serializable} but not guaranteed to be thread-safe.</p>
	 *
	 * @param       e  The exception associated with the failed file upload.
	 */
	@Deprecated
	public static UploadedFile getUploadedFileInstance(Exception e) {
		return getUploadedFileInstance(FacesContext.getCurrentInstance().getExternalContext(), e);
	}

	/**
	 * Returns a new instance of {@link UploadedFile} from the {@link UploadedFileFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is guaranteed to be {@link java.io.Serializable} but not
	 * guaranteed to be thread-safe.
	 *
	 * @param  externalContext  The external context associated with the current faces context. It is needed in order
	 *                          for the {@link FactoryExtensionFinder} to be able to find the factory.
	 * @param  e                The exception associated with the failed file upload.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static UploadedFile getUploadedFileInstance(ExternalContext externalContext, Exception e) {

		UploadedFileFactory uploadedFileFactory = (UploadedFileFactory) FactoryExtensionFinder.getFactory(
				externalContext, UploadedFileFactory.class);

		return uploadedFileFactory.getUploadedFile(e);
	}

	/**
	 * @deprecated  Call {@link #getUploadedFileInstance(ExternalContext, String, Map, String, String, Map, String,
	 *              String, String, long, UploadedFile.Status)} instead.
	 *
	 *              <p>Returns a new instance of {@link UploadedFile} from the {@link UploadedFileFactory} found by the
	 *              {@link FactoryExtensionFinder}. The returned instance is guaranteed to be {@link
	 *              java.io.Serializable} but not guaranteed to be thread-safe.</p>
	 *
	 * @param       absolutePath  The absolute path of the uploaded file.
	 * @param       attributes    The map of attributes associated with the uploaded file.
	 * @param       charSet       The character set of the uploaded file.
	 * @param       contentType   The content type of the uploaded file.
	 * @param       headers       The map of headers associated with the uploaded file.
	 * @param       id            The unique identifier of the uploaded file.
	 * @param       message       The message associated with the file upload.
	 * @param       name          The name of the uploaded file.
	 * @param       size          The size (in bytes) of the uploaded file.
	 * @param       status        The {@link UploadedFile.Status} of the uploaded file.
	 */
	@Deprecated
	public static UploadedFile getUploadedFileInstance(String absolutePath, Map<String, Object> attributes,
		String charSet, String contentType, Map<String, List<String>> headers, String id, String message, String name,
		long size, UploadedFile.Status status) {
		return getUploadedFileInstance(FacesContext.getCurrentInstance().getExternalContext(), absolutePath, attributes,
				charSet, contentType, headers, id, message, name, size, status);
	}

	/**
	 * Returns a new instance of {@link UploadedFile} from the {@link UploadedFileFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is guaranteed to be {@link java.io.Serializable} but not
	 * guaranteed to be thread-safe.
	 *
	 * @param  externalContext  The external context associated with the current faces context. It is needed in order
	 *                          for the {@link FactoryExtensionFinder} to be able to find the factory.
	 * @param  absolutePath     The absolute path of the uploaded file.
	 * @param  attributes       The map of attributes associated with the uploaded file.
	 * @param  charSet          The character set of the uploaded file.
	 * @param  contentType      The content type of the uploaded file.
	 * @param  headers          The map of headers associated with the uploaded file.
	 * @param  id               The unique identifier of the uploaded file.
	 * @param  message          The message associated with the file upload.
	 * @param  name             The name of the uploaded file.
	 * @param  size             The size (in bytes) of the uploaded file.
	 * @param  status           The {@link UploadedFile.Status} of the uploaded file.
	 *
	 * @since  3.1
	 * @since  2.1
	 * @since  1.1
	 */
	public static UploadedFile getUploadedFileInstance(ExternalContext externalContext, String absolutePath,
		Map<String, Object> attributes, String charSet, String contentType, Map<String, List<String>> headers,
		String id, String message, String name, long size, UploadedFile.Status status) {

		UploadedFileFactory uploadedFileFactory = (UploadedFileFactory) FactoryExtensionFinder.getFactory(
				externalContext, UploadedFileFactory.class);

		return uploadedFileFactory.getUploadedFile(absolutePath, attributes, charSet, contentType, headers, id, message,
				name, size, status);
	}

	/**
	 * Returns a new instance of {@link UploadedFile}. The returned instance is guaranteed to be {@link
	 * java.io.Serializable} but not guaranteed to be thread-safe.
	 *
	 * @param  e  The exception associated with the failed file upload.
	 */
	public abstract UploadedFile getUploadedFile(Exception e);

	/**
	 * Returns a new instance of {@link UploadedFile}. The returned instance is guaranteed to be {@link
	 * java.io.Serializable} but not guaranteed to be thread-safe.
	 *
	 * @param  absolutePath  The absolute path of the uploaded file.
	 * @param  attributes    The map of attributes associated with the uploaded file.
	 * @param  charSet       The character set of the uploaded file.
	 * @param  contentType   The content type of the uploaded file.
	 * @param  headers       The map of headers associated with the uploaded file.
	 * @param  id            The unique identifier of the uploaded file.
	 * @param  message       The message associated with the file upload.
	 * @param  name          The name of the uploaded file.
	 * @param  size          The size (in bytes) of the uploaded file.
	 * @param  status        The {@link UploadedFile.Status} of the uploaded file.
	 */
	public abstract UploadedFile getUploadedFile(String absolutePath, Map<String, Object> attributes, String charSet,
		String contentType, Map<String, List<String>> headers, String id, String message, String name, long size,
		UploadedFile.Status status);

	@Override
	public abstract UploadedFileFactory getWrapped();
}
