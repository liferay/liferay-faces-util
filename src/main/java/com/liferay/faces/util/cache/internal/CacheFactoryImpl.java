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

import com.liferay.faces.util.cache.Cache;
import com.liferay.faces.util.cache.CacheFactory;


/**
 * @author  Kyle Stiemann
 */
public class CacheFactoryImpl extends CacheFactory {

	@Override
	public <K, V> Cache<K, V> getConcurrentCache(int initialCapacity) throws IllegalArgumentException {

		validateInitialCapacity(initialCapacity);

		return new ConcurrentCacheImpl<K, V>(initialCapacity);
	}

	@Override
	public <K, V> Cache<K, V> getConcurrentLRUCache(int initialCapacity, int maxCapacity)
		throws IllegalArgumentException {

		validateInitialCapacity(initialCapacity);
		validateMaxCapacity(maxCapacity);

		return new ConcurrentLRUCacheImpl<K, V>(initialCapacity, maxCapacity);
	}

	@Override
	public CacheFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}

	private void validateInitialCapacity(int initialCapacity) {

		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Invalid initialCapacity of " + initialCapacity +
				". initialCapacity must be greater than -1.");
		}
	}

	private void validateMaxCapacity(int maxCapacity) {

		if (maxCapacity < 1) {
			throw new IllegalArgumentException("Invalid maxCapacity of " + maxCapacity +
				". maxCapacity must be greater than 0.");
		}
	}
}
