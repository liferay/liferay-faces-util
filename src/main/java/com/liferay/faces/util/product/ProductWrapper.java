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
package com.liferay.faces.util.product;

import javax.faces.FacesWrapper;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * @author Kyle Stiemann
 */
@ConsumerType
public abstract class ProductWrapper implements Product, FacesWrapper<Product> {

	@Override
	public abstract Product getWrapped();

	@Override
	public int getBuildId() {
		return getWrapped().getBuildId();
	}

	@Override
	public int getMajorVersion() {
		return getWrapped().getMajorVersion();
	}

	@Override
	public int getMinorVersion() {
		return getWrapped().getMinorVersion();
	}

	@Override
	public int getPatchVersion() {
		return getWrapped().getPatchVersion();
	}

	@Override
	public String getTitle() {
		return getWrapped().getTitle();
	}

	@Override
	public String getVersion() {
		return getWrapped().getVersion();
	}

	@Override
	public boolean isDetected() {
		return getWrapped().isDetected();
	}

	@Override
	public String toString() {
		return getWrapped().toString();
	}
}
