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
package com.liferay.faces.util.cache;

import java.util.Set;

import javax.faces.FacesWrapper;


/**
 * @author  Kyle Stiemann
 */
public abstract class CacheWrapper<K, V> implements Cache<K, V>, FacesWrapper<Cache<K, V>> {

	/**
	 * @see  Cache#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(K key) {
		return getWrapped().containsKey(key);
	}

	/**
	 * @see  Cache#getKeys()
	 */
	@Override
	public Set<K> getKeys() {
		return getWrapped().getKeys();
	}

	/**
	 * @see  Cache#getSize()
	 */
	@Override
	public int getSize() {
		return getWrapped().getSize();
	}

	/**
	 * @see  Cache#getValue(java.lang.Object)
	 */
	@Override
	public V getValue(K key) {
		return getWrapped().getValue(key);
	}

	/**
	 * @see  Cache#putValueIfAbsent(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V putValueIfAbsent(K key, V value) {
		return getWrapped().putValueIfAbsent(key, value);
	}

	/**
	 * @see  Cache#removeValue(java.lang.Object)
	 */
	@Override
	public V removeValue(K key) {
		return getWrapped().removeValue(key);
	}
}
