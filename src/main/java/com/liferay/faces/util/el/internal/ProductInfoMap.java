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
package com.liferay.faces.util.el.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liferay.faces.util.product.info.ProductInfo;
import com.liferay.faces.util.product.info.ProductInfoFactory;


/**
 * @author  Kyle Stiemann
 */
public class ProductInfoMap implements Map<ProductInfo.Name, ProductInfo>, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2730600575388304439L;

	// Private Final Data Members
	private final ProductInfoFactory productInfoFactory;

	public ProductInfoMap(ProductInfoFactory productInfoFactory) {
		this.productInfoFactory = productInfoFactory;
	}

	/* package-private */ static ProductInfo getProductInfo(ProductInfoFactory productInfoFactory, Object productInfoNameObject) {

		ProductInfo.Name productInfoName = null;

		if (productInfoNameObject instanceof ProductInfo.Name) {
			productInfoName = (ProductInfo.Name) productInfoNameObject;
		}
		else if (productInfoNameObject != null) {

			String productInfoNameString = productInfoNameObject.toString();
			productInfoName = ProductInfo.Name.valueOf(productInfoNameString);
		}

		return productInfoFactory.getProductInfo(productInfoName);
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Entry<ProductInfo.Name, ProductInfo>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ProductInfo get(Object key) {
		return getProductInfo(productInfoFactory, key);
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<ProductInfo.Name> keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ProductInfo put(ProductInfo.Name key, ProductInfo value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends ProductInfo.Name, ? extends ProductInfo> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ProductInfo remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<ProductInfo> values() {
		throw new UnsupportedOperationException();
	}
}
