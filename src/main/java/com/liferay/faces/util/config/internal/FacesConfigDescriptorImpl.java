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
package com.liferay.faces.util.config.internal;

import java.io.Serializable;
import java.util.List;


/**
 * @author  Neil Griffin
 */
public class FacesConfigDescriptorImpl implements FacesConfigDescriptor, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 5479857091609259746L;

	// Private Data Members
	private String name;
	private boolean isWebInfFacesConfig;
	private List<String> absoluteOrdering;
	private Ordering ordering;
	private String url;

	public FacesConfigDescriptorImpl(FacesConfigDescriptor facesConfigDescriptor) {
		this.name = facesConfigDescriptor.getName();
		this.isWebInfFacesConfig = facesConfigDescriptor.isWebInfFacesConfig();
		this.absoluteOrdering = facesConfigDescriptor.getAbsoluteOrdering();
		this.ordering = facesConfigDescriptor.getOrdering();

//      String[][] routes = ordering.getRoutes();
//      System.err.println("FacesConfigDescriptorImpl: name = " + name + " routes[1][1] = " + routes[1][1]);
		this.url = facesConfigDescriptor.getURL();
	}

	public FacesConfigDescriptorImpl(String name, String url, boolean isWebInfFacesConfig,
		List<String> absoluteOrdering, Ordering ordering) {
		this.name = name;
		this.isWebInfFacesConfig = isWebInfFacesConfig;
		this.absoluteOrdering = absoluteOrdering;
		this.ordering = ordering;
		this.url = url;
	}

	@Override
	public List<String> getAbsoluteOrdering() {
		return absoluteOrdering;
	}

	@Override
	public String getName() {

		if (name == null) {
			name = "";
		}

		return name;
	}

	@Override
	public Ordering getOrdering() {
		return ordering;
	}

	@Override
	public String getURL() {
		return url;
	}

	@Override
	public boolean hasAbsoluteOrdering() {
		return isWebInfFacesConfig && (absoluteOrdering != null);
	}

	@Override
	public boolean isWebInfFacesConfig() {
		return isWebInfFacesConfig;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}
}
