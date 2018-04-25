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
package com.liferay.faces.util.product.info.internal;

/**
 * @author  Neil Griffin
 */
public class ProductInfoLiferayWabExtenderImpl extends ProductInfoBaseImpl {

	public ProductInfoLiferayWabExtenderImpl(int buildId, int majorVersion, int minorVersion, int patchVersion,
		String version, boolean detected) {
		this.buildId = buildId;
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.patchVersion = patchVersion;
		this.version = version;
		this.detected = detected;
		this.title = "Liferay WAB Extender";
		initStringValue(version);
	}
}
