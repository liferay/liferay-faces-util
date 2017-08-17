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

import javax.faces.FacesWrapper;
import javax.faces.context.ExternalContext;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 * @since   3.1
 * @since   2.1
 * @since   1.1
 */
public abstract class CacheFactory implements FacesWrapper<CacheFactory> {

	/**
	 * Returns a new instance of {@link Cache} from the {@link CacheFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request thread, so it
	 * is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  <K>              The type of the cache's keys.
	 * @param  <V>              The type of the cache's values.
	 * @param  externalContext  The external context associated with the current faces context. It is needed in order
	 *                          for the {@link FactoryExtensionFinder} to be able to find the factory.
	 */
	public static <K, V> Cache<K, V> getCacheInstance(ExternalContext externalContext) {

		CacheFactory cacheFactory = (CacheFactory) FactoryExtensionFinder.getFactory(externalContext,
				CacheFactory.class);

		return cacheFactory.<K, V>getCache();
	}

	/**
	 * Returns a new instance of {@link Cache} from the {@link CacheFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request thread, so it
	 * is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  <K>              The type of the cache's keys.
	 * @param  <V>              The type of the cache's values.
	 * @param  externalContext  The external context associated with the current faces context. It is needed in order
	 *                          for the {@link FactoryExtensionFinder} to be able to find the factory.
	 * @param  initialCapacity  The initial capacity of the cache.
	 */
	public static <K, V> Cache<K, V> getCacheInstance(ExternalContext externalContext, int initialCapacity) {

		CacheFactory cacheFactory = (CacheFactory) FactoryExtensionFinder.getFactory(externalContext,
				CacheFactory.class);

		return cacheFactory.<K, V>getCache(initialCapacity);
	}

	/**
	 * Returns a new instance of {@link Cache} from the {@link CacheFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be used during execution of a request thread, so it
	 * is not guaranteed to be {@link java.io.Serializable}. The returned cache will avoid exceeding the maximum cache
	 * capacity. The algorithm used to ensure that the cache size does not exceed the max capacity (for example
	 * least-recently-used) may cause significant performance differences between the returned cache and a cache
	 * returned from {@link #getCache()} or {@link #getCache(int)}.
	 *
	 * @param  <K>              The type of the cache's keys.
	 * @param  <V>              The type of the cache's values.
	 * @param  externalContext  The external context associated with the current faces context. It is needed in order
	 *                          for the {@link FactoryExtensionFinder} to be able to find the factory.
	 * @param  initialCapacity  The initial capacity of the cache.
	 * @param  maxCapacity      The maximum capacity of the cache.
	 */
	public static <K, V> Cache<K, V> getCacheInstance(ExternalContext externalContext, int initialCapacity,
		int maxCapacity) {

		CacheFactory cacheFactory = (CacheFactory) FactoryExtensionFinder.getFactory(externalContext,
				CacheFactory.class);

		return cacheFactory.<K, V>getCache(initialCapacity, maxCapacity);
	}

	/**
	 * Returns a new instance of {@link Cache} from the {@link CacheFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be accessed and modified by multiple threads
	 * concurrently, so it is guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  <K>              The type of the cache's keys.
	 * @param  <V>              The type of the cache's values.
	 * @param  externalContext  The external context associated with the current faces context.
	 */
	public static <K, V> Cache<K, V> getConcurrentCacheInstance(ExternalContext externalContext) {

		CacheFactory cacheFactory = (CacheFactory) FactoryExtensionFinder.getFactory(externalContext,
				CacheFactory.class);

		return cacheFactory.<K, V>getConcurrentCache();
	}

	/**
	 * Returns a new instance of {@link Cache} from the {@link CacheFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be accessed and modified by multiple threads
	 * concurrently, so it is guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  <K>              The type of the cache's keys.
	 * @param  <V>              The type of the cache's values.
	 * @param  externalContext  The external context associated with the current faces context.
	 * @param  initialCapacity  The initial capacity of the cache.
	 */
	public static <K, V> Cache<K, V> getConcurrentCacheInstance(ExternalContext externalContext, int initialCapacity) {

		CacheFactory cacheFactory = (CacheFactory) FactoryExtensionFinder.getFactory(externalContext,
				CacheFactory.class);

		return cacheFactory.<K, V>getConcurrentCache(initialCapacity);
	}

	/**
	 * Returns a new instance of {@link Cache} from the {@link CacheFactory} found by the {@link
	 * FactoryExtensionFinder}. The returned instance is designed to be accessed and modified by multiple threads
	 * concurrently, so it is guaranteed to be {@link java.io.Serializable}. The returned cache will avoid exceeding the
	 * maximum cache capacity. The algorithm used to ensure that the cache size does not exceed the max capacity (for
	 * example least-recently-used) may cause significant performance differences between the returned cache and a cache
	 * returned from {@link #getConcurrentCache()} or {@link #getConcurrentCache(int)}.
	 *
	 * @param  <K>              The type of the cache's keys.
	 * @param  <V>              The type of the cache's values.
	 * @param  externalContext  The external context associated with the current faces context.
	 * @param  initialCapacity  The initial capacity of the cache.
	 * @param  maxCapacity      The maximum capacity of the cache.
	 */
	public static <K, V> Cache<K, V> getConcurrentCacheInstance(ExternalContext externalContext, int initialCapacity,
		int maxCapacity) {

		CacheFactory cacheFactory = (CacheFactory) FactoryExtensionFinder.getFactory(externalContext,
				CacheFactory.class);

		return cacheFactory.<K, V>getConcurrentCache(initialCapacity, maxCapacity);
	}

	/**
	 * Returns a new instance of {@link Cache}. The returned instance is designed to be used during execution of a
	 * request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  <K>  The type of the cache's keys.
	 * @param  <V>  The type of the cache's values.
	 */
	public abstract <K, V> Cache<K, V> getCache();

	/**
	 * Returns a new instance of {@link Cache}. The returned instance is designed to be used during execution of a
	 * request thread, so it is not guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  <K>              The type of the cache's keys.
	 * @param  <V>              The type of the cache's values.
	 * @param  initialCapacity  The initial capacity of the cache.
	 */
	public abstract <K, V> Cache<K, V> getCache(int initialCapacity);

	/**
	 * Returns a new instance of {@link Cache}. The returned instance is designed to be used during execution of a
	 * request thread, so it is not guaranteed to be {@link java.io.Serializable}. The returned cache will avoid
	 * exceeding the maximum cache capacity. The algorithm used to ensure that the cache size does not exceed the max
	 * capacity (for example least-recently-used) may cause significant performance differences between the returned
	 * cache and a cache returned from {@link #getCache()} or {@link #getCache(int)}.
	 *
	 * @param  <K>              The type of the cache's keys.
	 * @param  <V>              The type of the cache's values.
	 * @param  initialCapacity  The initial capacity of the cache.
	 * @param  maxCapacity      The maximum capacity of the cache.
	 */
	public abstract <K, V> Cache<K, V> getCache(int initialCapacity, int maxCapacity);

	/**
	 * Returns a new instance of {@link Cache}. The returned instance is designed to be accessed and modified by
	 * multiple threads concurrently, so it is guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  <K>  The type of the cache's keys.
	 * @param  <V>  The type of the cache's values.
	 */
	public abstract <K, V> Cache<K, V> getConcurrentCache();

	/**
	 * Returns a new instance of {@link Cache}. The returned instance is designed to be accessed and modified by
	 * multiple threads concurrently, so it is guaranteed to be {@link java.io.Serializable}.
	 *
	 * @param  <K>              The type of the cache's keys.
	 * @param  <V>              The type of the cache's values.
	 * @param  initialCapacity  The initial capacity of the cache.
	 */
	public abstract <K, V> Cache<K, V> getConcurrentCache(int initialCapacity);

	/**
	 * Returns a new instance of {@link Cache}. The returned instance is designed to be accessed and modified by
	 * multiple threads concurrently, so it is guaranteed to be {@link java.io.Serializable}. The returned cache will
	 * avoid exceeding the maximum cache capacity. The algorithm used to ensure that the cache size does not exceed the
	 * max capacity (for example least-recently-used) may cause significant performance differences between the returned
	 * cache and a cache returned from {@link #getConcurrentCache()} or {@link #getConcurrentCache(int)}.
	 *
	 * @param  <K>              The type of the cache's keys.
	 * @param  <V>              The type of the cache's values.
	 * @param  initialCapacity  The initial capacity of the cache.
	 * @param  maxCapacity      The maximum capacity of the cache.
	 */
	public abstract <K, V> Cache<K, V> getConcurrentCache(int initialCapacity, int maxCapacity);

	/**
	 * Returns the default initial capacity for a cache. This method is provided as a convenience to pass to {@link
	 * #getCache(int, int)} and {@link #getConcurrentCache(int, int)}. The default implementation returns 16.
	 */
	public abstract int getDefaultInitialCapacity();

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract CacheFactory getWrapped();
}
