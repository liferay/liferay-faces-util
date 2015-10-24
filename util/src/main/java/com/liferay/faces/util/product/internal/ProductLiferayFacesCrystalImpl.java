/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.product.ProductConstants;


/**
 * @author  Neil Griffin
 */
public class ProductLiferayFacesCrystalImpl extends ProductBaseImpl {

	public ProductLiferayFacesCrystalImpl() {

		try {
			this.title = ProductConstants.LIFERAY_FACES_CRYSTAL;

			Class<?> clazz = Class.forName("com.liferay.faces.crystal.component.inputtext.InputText");
			init(clazz, ProductConstants.LIFERAY_FACES_CRYSTAL);
		}
		catch (Exception e) {
			// Ignore -- Liferay Faces Alloy is likely not present.
		}
	}
}
