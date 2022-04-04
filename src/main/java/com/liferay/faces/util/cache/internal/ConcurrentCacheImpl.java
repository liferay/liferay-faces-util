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
package com.liferay.faces.util.cache.internal;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.liferay.faces.util.cache.Cache;


/**
 * @author  Kyle Stiemann
 */
public class ConcurrentCacheImpl<K, V> implements Cache<K, V>, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2468038135762834477L;

	// Private Final Data Members
	private final ConcurrentHashMap<K, V> internalCache;

	public ConcurrentCacheImpl() {
		this.internalCache = new ConcurrentHashMap<K, V>();
	}

	public ConcurrentCacheImpl(int initialCapacity) {
		this.internalCache = new ConcurrentHashMap<K, V>(initialCapacity);
	}

	@Override
	public boolean containsKey(K key) {
		return internalCache.containsKey(key);
	}

	@Override
	public Set<K> getKeys() {
		return internalCache.keySet();
	}

	@Override
	public int getSize() {
		return internalCache.size();
	}

	@Override
	public V getValue(K key) {
		return internalCache.get(key);
	}

	@Override
	public V putValueIfAbsent(K key, V value) {

		V retValue = internalCache.putIfAbsent(key, value);

		if (retValue == null) {
			retValue = value;
		}

		return retValue;
	}

	@Override
	public V removeValue(K key) {
		return internalCache.remove(key);
	}
}
