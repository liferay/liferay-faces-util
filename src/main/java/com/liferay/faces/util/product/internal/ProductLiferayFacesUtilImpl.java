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
package com.liferay.faces.util.product.internal;

/**
 * @author  Neil Griffin
 */
public class ProductLiferayFacesUtilImpl extends ProductBaseImpl {

	public ProductLiferayFacesUtilImpl() {
		super(new ProductInfo(getImplTitle(), getImplVersion()));
	}

	private static String getImplTitle() {

		Package _package = ProductLiferayFacesUtilImpl.class.getPackage();
		String implTitle = _package.getImplementationTitle();

		if (implTitle == null) {
			implTitle = "Liferay Faces Util";
		}

		return implTitle;
	}

	private static String getImplVersion() {

		Package _package = ProductLiferayFacesUtilImpl.class.getPackage();

		return _package.getImplementationVersion();
	}
}
