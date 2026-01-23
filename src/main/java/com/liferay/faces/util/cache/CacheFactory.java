/**
 * Copyright (c) 2000-2025 Liferay, Inc. All rights reserved.
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

import jakarta.faces.FacesWrapper;
import jakarta.faces.context.ExternalContext;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.factory.FactoryExtensionFinder;

/**
 * @author Kyle Stiemann
 * @since 3.1
 * @since 2.1
 * @since 1.1
 */
@ProviderType
public abstract class CacheFactory implements FacesWrapper<CacheFactory> {

	/**
	 * Returns a new instance of {@link Cache} from the {@link CacheFactory} found by the
	 * {@link FactoryExtensionFinder}. The returned instance is designed to be accessed and modified by multiple threads
	 * concurrently, so it is guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param <K>             The type of the cache's keys.
	 * @param <V>             The type of the cache's values.
	 * @param externalContext The external context associated with the current faces context.
	 * @param initialCapacity The initial capacity of the cache.
	 *
	 * @throws IllegalArgumentException if the initial capacity is less than zero.
	 *
	 * @see #getConcurrentCache(int)
	 */
	public static <K, V> Cache<K, V> getConcurrentCacheInstance(ExternalContext externalContext, int initialCapacity)
		throws IllegalArgumentException {

		CacheFactory cacheFactory =
			(CacheFactory) FactoryExtensionFinder.getFactory(externalContext, CacheFactory.class);

		return cacheFactory.<K, V>getConcurrentCache(initialCapacity);
	}

	/**
	 * Returns a new instance of {@link Cache} from the {@link CacheFactory} found by the
	 * {@link FactoryExtensionFinder}. The returned instance is designed to be accessed and modified by multiple threads
	 * concurrently, so it is guaranteed to be {@link java.io.Serializable}. The returned cache will avoid exceeding the
	 * maximum cache capacity by using a least-recently-used algorithm which may cause a significant performance impact
	 * when compared to a cache that does not enforce a maximum size (for example, a cache returned from
	 * {@link #getConcurrentCache(int)}).
	 *
	 * @param <K>             The type of the cache's keys.
	 * @param <V>             The type of the cache's values.
	 * @param externalContext The external context associated with the current faces context.
	 * @param initialCapacity The initial capacity of the cache.
	 * @param maxCapacity     The maximum capacity of the cache.
	 *
	 * @throws IllegalArgumentException if the initial capacity is less than zero or the maximum capacity is less than
	 *                                  1.
	 *
	 * @see #getConcurrentLRUCache(int, int)
	 */
	public static <K, V> Cache<K, V> getConcurrentLRUCacheInstance(ExternalContext externalContext, int initialCapacity,
		int maxCapacity) throws IllegalArgumentException {

		CacheFactory cacheFactory =
			(CacheFactory) FactoryExtensionFinder.getFactory(externalContext, CacheFactory.class);

		return cacheFactory.<K, V>getConcurrentLRUCache(initialCapacity, maxCapacity);
	}

	/**
	 * Returns a new instance of {@link Cache}. The returned instance is designed to be accessed and modified by
	 * multiple threads concurrently, so it is guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param <K>             The type of the cache's keys.
	 * @param <V>             The type of the cache's values.
	 * @param initialCapacity The initial capacity of the cache.
	 *
	 * @throws IllegalArgumentException if the initial capacity is less than zero.
	 */
	public abstract <K, V> Cache<K, V> getConcurrentCache(int initialCapacity) throws IllegalArgumentException;

	/**
	 * Returns a new instance of {@link Cache}. The returned instance is designed to be accessed and modified by
	 * multiple threads concurrently, so it is guaranteed to be {@link java.io.Serializable}. The returned cache will
	 * avoid exceeding the maximum cache capacity by using a least-recently-used algorithm which may cause a significant
	 * performance impact when compared to a cache that does not enforce a maximum size (for example, a cache returned
	 * from {@link #getConcurrentCache(int)}).
	 *
	 * @param <K>             The type of the cache's keys.
	 * @param <V>             The type of the cache's values.
	 * @param initialCapacity The initial capacity of the cache.
	 * @param maxCapacity     The maximum capacity of the cache.
	 *
	 * @throws IllegalArgumentException if the initial capacity is less than zero or the maximum capacity is less than
	 *                                  1.
	 */
	public abstract <K, V> Cache<K, V> getConcurrentLRUCache(int initialCapacity, int maxCapacity)
		throws IllegalArgumentException;

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract CacheFactory getWrapped();
}
