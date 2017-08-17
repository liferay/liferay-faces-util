/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Kyle Stiemann
 */
public abstract class CacheWrapper<K, V> implements Cache<K, V>, Wrapper<Cache<K, V>> {

	/**
	 * @see  Cache#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(K key) {
		return getWrapped().containsKey(key);
	}

	/**
	 * @see  Cache#get(java.lang.Object)
	 */
	@Override
	public V get(K key) {
		return getWrapped().get(key);
	}

	/**
	 * @see  Cache#keySet()
	 */
	@Override
	public Set<K> keySet() {
		return getWrapped().keySet();
	}

	/**
	 * @see  Cache#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		return getWrapped().put(key, value);
	}

	/**
	 * @see  Cache#putIfAbsent(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V putIfAbsent(K key, V value) {
		return getWrapped().putIfAbsent(key, value);
	}

	/**
	 * @see  Cache#remove(java.lang.Object)
	 */
	@Override
	public V remove(K key) {
		return getWrapped().remove(key);
	}
}
