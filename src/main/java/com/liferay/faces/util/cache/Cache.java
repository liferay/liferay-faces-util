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


/**
 * This class contains a minimal set of methods required to manipulate a cache of key+value pairs.
 *
 * @author  Kyle Stiemann
 * @since   3.1
 * @since   2.1
 * @since   1.1
 */
public interface Cache<K, V> {

	/**
	 * Returns true if the key (and therefor a corresponding value) is present in the cache or false otherwise.
	 *
	 * @param  key  The key that the value is mapped to.
	 */
	public boolean containsKey(K key);

	/**
	 * Returns the set of all keys currently in the cache at the time this method was called.
	 */
	public Set<K> getKeys();

	/**
	 * Returns the number of key-value pairs in the cache.
	 */
	public int getSize();

	/**
	 * Returns the cached value that is mapped to this key or null if the value is not in the cache.
	 *
	 * @param  key  The key that the value is mapped to.
	 */
	public V getValue(K key);

	/**
	 * Puts the cached key-value pair into the cache if the key does not already exist in the cache. If the key was not
	 * already added to the cache, this method returns the passed value for convenience. Otherwise this method returns
	 * the value associated with the key from the cache.
	 *
	 * @param  key    The key that the value is mapped to.
	 * @param  value  The value to be cached.
	 */
	public V putValueIfAbsent(K key, V value);

	/**
	 * Removes an entry from the cache. Returns the value of the removed key-value pair.
	 *
	 * @param  key  The key that the value is mapped to.
	 */
	public V removeValue(K key);
}
