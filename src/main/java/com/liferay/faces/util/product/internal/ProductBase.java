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
package com.liferay.faces.util.product.internal;

import com.liferay.faces.util.product.Product;


/**
 * @author  Neil Griffin
 */
public abstract class ProductBase implements Product {

	// Private Final Data Members
	private final ProductInfo productInfo;

	protected ProductBase(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	@Override
	public int getBuildId() {
		return productInfo.buildId;
	}

	@Override
	public int getMajorVersion() {
		return productInfo.majorVersion;
	}

	@Override
	public int getMinorVersion() {
		return productInfo.minorVersion;
	}

	@Override
	public int getPatchVersion() {
		return productInfo.patchVersion;
	}

	public final ProductInfo getProductInfo() {
		return productInfo;
	}

	@Override
	public String getTitle() {
		return productInfo.title;
	}

	@Override
	public String getVersion() {
		return productInfo.version;
	}

	@Override
	public boolean isDetected() {
		return productInfo.detected;
	}

	@Override
	public String toString() {
		return productInfo.stringValue;
	}
}
