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
package com.liferay.faces.util.cache.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liferay.faces.util.cache.Cache;


/**
 * @author  Kyle Stiemann
 */
public class CacheMaxCapacityLRUImpl<K, V> implements Cache<K, V> {

	// Private Final Data Members
	private final Map<K, V> internalCache;

	public CacheMaxCapacityLRUImpl(int intitialCapacity, int maxCapacity) {
		internalCache = new LinkedHashMapMaxCapacityLRUImpl<K, V>(intitialCapacity, maxCapacity);
	}

	@Override
	public boolean containsKey(K key) {
		return internalCache.containsKey(key);
	}

	@Override
	public V get(K key) {
		return internalCache.get(key);
	}

	@Override
	public Set<K> keySet() {
		return internalCache.keySet();
	}

	@Override
	public V put(K key, V value) {

		internalCache.put(key, value);

		return value;
	}

	@Override
	public V putIfAbsent(K key, V value) {

		V retValue;

		if (!internalCache.containsKey(key)) {
			retValue = put(key, value);
		}
		else {
			retValue = get(key);
		}

		return retValue;
	}

	@Override
	public V remove(K key) {
		return internalCache.remove(key);
	}

	private static class LinkedHashMapMaxCapacityLRUImpl<K, V> extends LinkedHashMap<K, V> {

		// Private Final Data Members
		private final int maxSize;

		public LinkedHashMapMaxCapacityLRUImpl(int initialCapacity, int maxSize) {

			// Call the constructor and set the intialCapacity and loadFactor to default values while setting
			// accessOrder to true to ensure that the LinkedHashMap removes the least recently used value. For more
			// details, see here: https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html
			super(initialCapacity, 0.75f, true);
			this.maxSize = maxSize;
		}

		@Override
		protected boolean removeEldestEntry(Entry<K, V> eldest) {
			return size() > maxSize;
		}
	}
}
