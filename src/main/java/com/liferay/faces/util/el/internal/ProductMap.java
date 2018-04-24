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
import java.util.Set;

import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.info.ProductInfo;
import com.liferay.faces.util.product.info.ProductInfoFactory;
import com.liferay.faces.util.product.internal.ProductAdapterImpl;


/**
 * @author  Kyle Stiemann
 */
public class ProductMap implements Map<Product.Name, Product>, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 8051373438471283130L;

	// Private Final Data Members
	private final ProductInfoFactory productInfoFactory;

	public ProductMap(ProductInfoFactory productInfoFactory) {
		this.productInfoFactory = productInfoFactory;
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
	public Set<Entry<Product.Name, Product>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Product get(Object key) {

		ProductInfo productInfo = ProductInfoMap.getProductInfo(productInfoFactory, key);

		return new ProductAdapterImpl(productInfo);
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Product.Name> keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Product put(Product.Name key, Product value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends Product.Name, ? extends Product> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Product remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Product> values() {
		throw new UnsupportedOperationException();
	}
}
