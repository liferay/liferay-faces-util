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
package com.liferay.faces.util.product.internal;

import com.liferay.faces.util.product.Product;


/**
 * @author  Kyle Stiemann
 */
public class ProductSpecImpl implements Product {

	// Private Members
	private String specTitle;
	private Product wrappedProduct;

	public ProductSpecImpl(String specTitle, Product... specImplProducts) {

		for (Product specImplProduct : specImplProducts) {

			if (specImplProduct.isDetected()) {

				this.wrappedProduct = specImplProduct;

				break;
			}
		}

		if (this.wrappedProduct == null) {

			this.wrappedProduct = new ProductBaseImpl();
			this.specTitle = specTitle;
		}
	}

	@Override
	public int getBuildId() {
		return wrappedProduct.getBuildId();
	}

	@Override
	public int getMajorVersion() {
		return wrappedProduct.getMajorVersion();
	}

	@Override
	public int getMinorVersion() {
		return wrappedProduct.getMinorVersion();
	}

	@Override
	public int getPatchVersion() {
		return wrappedProduct.getPatchVersion();
	}

	@Override
	public String getTitle() {

		if (!isDetected()) {
			return specTitle;
		}
		else {
			return wrappedProduct.getTitle();
		}
	}

	@Override
	public String getVersion() {
		return wrappedProduct.getVersion();
	}

	@Override
	public boolean isDetected() {
		return wrappedProduct.isDetected();
	}

	@Override
	public String toString() {
		return wrappedProduct.toString();
	}
}
