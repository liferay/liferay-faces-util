/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.application.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.FactoryFinder;
import javax.faces.application.ViewHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageFactory;

import com.liferay.faces.util.application.ResourceValidator;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ResourceValidatorImpl implements ResourceValidator, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1439627171348715228L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceValidatorImpl.class);

	// Private Constants
	private static final Pattern BANNED_PATHS_PATTERN = Pattern.compile(".*/(?:META-INF|WEB-INF)/.*",
			Pattern.CASE_INSENSITIVE);
	private static final String[] BANNED_SEQUENCES = new String[] {
			"//", "\\\\", "/\\", "\\/", "..", "./", ".\\", "%"
		};
	private static final String LEGACY_FACELETS_VIEW_MAPPINGS_PARAM_NAME = "facelets.VIEW_MAPPINGS";

	// Final Data Members
	private final List<Pattern> excludeResourcePatterns;
	private final List<Pattern> excludeLibraryPatterns;

	/**
	 * @param  excludeResourcePatterns  a {@link java.io.Serializable} {@link ArrayList} of excluded resource patterns.
	 * @param  excludeLibraryPatterns   a {@link java.io.Serializable} {@link ArrayList} of excluded library patterns.
	 */
	public ResourceValidatorImpl(ArrayList<Pattern> excludeResourcePatterns,
		ArrayList<Pattern> excludeLibraryPatterns) {

		if (excludeResourcePatterns != null) {
			this.excludeResourcePatterns = Collections.unmodifiableList(excludeResourcePatterns);
		}
		else {
			this.excludeResourcePatterns = null;
		}

		if (excludeLibraryPatterns != null) {
			this.excludeLibraryPatterns = Collections.unmodifiableList(excludeLibraryPatterns);
		}
		else {
			this.excludeLibraryPatterns = null;
		}
	}

	private static List<String> getViewMappings(ExternalContext externalContext, String... viewMappingsParamNames) {

		List<String> viewMappings = new ArrayList<String>();

		for (String viewMappingsParamName : viewMappingsParamNames) {

			String configuredMappings = externalContext.getInitParameter(viewMappingsParamName);

			if (configuredMappings != null) {

				String[] mappings = configuredMappings.split(";");

				Collections.addAll(viewMappings, mappings);
			}
		}

		return Collections.unmodifiableList(viewMappings);
	}

	@Override
	public boolean containsBannedPath(String resourceId) {

		Matcher matcher = BANNED_PATHS_PATTERN.matcher(resourceId);

		boolean matches = matcher.matches();

		if (matches) {
			logger.trace("MATCHED BANNED PATH resourceId=[{0}] matcher=[{1}]", resourceId, matcher);
		}

		return matches;
	}

	@Override
	public boolean isBannedSequence(String resourceId) {
		boolean bannedSequence = false;

		if (resourceId != null) {

			for (String sequence : BANNED_SEQUENCES) {

				if (resourceId.contains(sequence)) {

					logger.trace("MATCHED BANNED SEQUENCE resourceId=[{0}] sequence=[{1}]", resourceId, sequence);
					bannedSequence = true;

					break;
				}
			}
		}

		return bannedSequence;
	}

	@Override
	public boolean isFaceletDocument(FacesContext facesContext, String resourceId) {

		boolean faceletDocument = false;

		List<String> faceletExtensions = new ArrayList<String>();
		List<String> faceletPathMappings = new ArrayList<String>();
		faceletExtensions.add(ViewHandler.DEFAULT_FACELETS_SUFFIX);

		ExternalContext externalContext = facesContext.getExternalContext();
		String configuredExtension = externalContext.getInitParameter(ViewHandler.FACELETS_SUFFIX_PARAM_NAME);

		if ((configuredExtension != null) && !faceletExtensions.contains(configuredExtension)) {
			faceletExtensions.add(configuredExtension);
		}

		List<String> viewMappings = getViewMappings(externalContext, ViewHandler.FACELETS_VIEW_MAPPINGS_PARAM_NAME,
				LEGACY_FACELETS_VIEW_MAPPINGS_PARAM_NAME);

		for (String viewMapping : viewMappings) {

			viewMapping = viewMapping.trim();

			if (viewMapping.startsWith("*")) {

				String faceletExtension = viewMapping.substring(1);

				if (!faceletExtensions.contains(faceletExtension)) {
					faceletExtensions.add(faceletExtension);
				}
			}
			else if (viewMapping.endsWith("*")) {

				String faceletPathMapping = viewMapping.substring(0, viewMapping.length() - 1);

				if (!faceletPathMappings.contains(faceletPathMapping)) {
					faceletPathMappings.add(faceletPathMapping);
				}
			}
		}

		for (String faceletPathMapping : faceletPathMappings) {

			if (resourceId.startsWith(faceletPathMapping)) {

				logger.trace("MATCHED FACELET PATH MAPPING resourceId=[{0}] faceletPathMapping=[{1}]", resourceId,
					faceletPathMapping);
				faceletDocument = true;

				break;
			}
		}

		for (String faceletExtension : faceletExtensions) {

			if (resourceId.contains(faceletExtension)) {

				logger.trace("MATCHED FACELET EXTENSION MAPPING resourceId=[{0}] faceletExtension=[{1}] ", resourceId,
					faceletExtension);
				faceletDocument = true;

				break;
			}
		}

		if (!faceletDocument) {
			faceletDocument = isFaceletsVDL(resourceId);
		}

		return faceletDocument;
	}

	@Override
	public boolean isSelfReferencing(FacesContext facesContext, String resourceId) {
		return false;
	}

	public boolean isValidLibraryName(String libraryName) {

		boolean validLibraryName = true;

		if (excludeLibraryPatterns != null) {

			for (Pattern excludeLibraryPattern : excludeLibraryPatterns) {

				Matcher matcher = excludeLibraryPattern.matcher(libraryName);

				if (matcher.matches()) {

					validLibraryName = false;
					logger.trace("MATCHED LIBRARY NAME EXCLUSION PATTERN libraryName=[{0}] matcher=[{1}]", libraryName,
						matcher);

					break;
				}
			}
		}

		return validLibraryName;
	}

	public boolean isValidResourceName(String resourceName) {

		boolean validResourceName = true;

		if (excludeResourcePatterns != null) {

			for (Pattern excludeResourcePattern : excludeResourcePatterns) {

				Matcher matcher = excludeResourcePattern.matcher(resourceName);

				if (matcher.matches()) {

					validResourceName = false;
					logger.trace("MATCHED RESOURCE NAME EXCLUSION PATTERN resourceName=[{0}] matcher=[{1}] ",
						resourceName, matcher);

					break;
				}
			}
		}

		return validResourceName;
	}

	protected boolean isFaceletsVDL(String viewId) {

		boolean faceletsVDL = false;
		ViewDeclarationLanguageFactory viewDeclarationLanguageFactory = (ViewDeclarationLanguageFactory) FactoryFinder
			.getFactory(FactoryFinder.VIEW_DECLARATION_LANGUAGE_FACTORY);
		ViewDeclarationLanguage viewDeclarationLanguage = viewDeclarationLanguageFactory.getViewDeclarationLanguage(
				viewId);

		if (viewDeclarationLanguage != null) {

			faceletsVDL = viewDeclarationLanguage.getId().equals(
					ViewDeclarationLanguage.FACELETS_VIEW_DECLARATION_LANGUAGE_ID);

			if (faceletsVDL) {
				logger.trace("MATCHED FACELETS VDL viewId=[{0}]", viewId);
			}
		}

		return faceletsVDL;
	}
}
