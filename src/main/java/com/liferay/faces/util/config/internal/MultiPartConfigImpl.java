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

import com.liferay.faces.util.config.MultiPartConfig;


/**
 * @author  Neil Griffin
 */
public class MultiPartConfigImpl implements MultiPartConfig {

	// Private Data Members
	private String location;
	private long maxFileSize;

	public MultiPartConfigImpl(String location, long maxFileSize) {
		this.location = location;
		this.maxFileSize = maxFileSize;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public long getMaxFileSize() {
		return maxFileSize;
	}
}
