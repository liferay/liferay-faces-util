/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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
import java.util.regex.Pattern;

import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.application.ResourceValidator;
import com.liferay.faces.util.application.ResourceValidatorFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ResourceValidatorFactoryImpl extends ResourceValidatorFactory implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1540511281114845438L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceValidatorFactoryImpl.class);

	// Private Data Members
	private ResourceValidator resourceValidator;

	public ResourceValidatorFactoryImpl() {

		String excludeResourceExtensions = null;
		FacesContext startupFacesContext = FacesContext.getCurrentInstance();

		if (startupFacesContext != null) {

			ExternalContext externalContext = startupFacesContext.getExternalContext();

			String resourceExcludes = externalContext.getInitParameter(ResourceHandler.RESOURCE_EXCLUDES_PARAM_NAME);

			if ((resourceExcludes == null) || (resourceExcludes.trim().length() == 0)) {
				excludeResourceExtensions = ResourceHandler.RESOURCE_EXCLUDES_DEFAULT_VALUE;
			}
		}

		// The list of patterns are declared as ArrayList to ensure that they are Serializable.
		ArrayList<Pattern> excludeResourcePatterns = null;
		ArrayList<Pattern> excludeLibraryPatterns = null;

		if (excludeResourceExtensions != null) {

			String[] extensions = excludeResourceExtensions.split(" ");

			excludeResourcePatterns = new ArrayList<Pattern>(extensions.length + 1);

			for (String extension : extensions) {
				Pattern pattern = Pattern.compile(".*\\" + extension + ".*");
				excludeResourcePatterns.add(pattern);
				logger.debug("Excluding resource pattern=[{0}]", pattern);
			}

			// Prevent for a leading dot character for resource names and library names
			Pattern pattern = Pattern.compile("^\\..*");
			excludeResourcePatterns.add(pattern);
			logger.debug("Excluding resource pattern=[{0}]", pattern);
			excludeLibraryPatterns = new ArrayList<Pattern>(1);
			excludeLibraryPatterns.add(pattern);
			logger.debug("Excluding library pattern=[{0}]", pattern);
		}

		resourceValidator = new ResourceValidatorImpl(excludeResourcePatterns, excludeLibraryPatterns);
	}

	@Override
	public ResourceValidator getResourceValidator() {
		return resourceValidator;
	}

	@Override
	public ResourceValidatorFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
