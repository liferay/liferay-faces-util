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

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Assert;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.faces.util.cache.internal.CacheFactoryImpl;


/**
 * @author  Kyle Stiemann
 */
public class CacheTest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);

	@Test
	public void runCacheMaxCapacityLRUTest() throws Exception {

		CacheFactoryImpl cacheFactoryImpl = new CacheFactoryImpl();
		int defaultInitialCacheCapacity = cacheFactoryImpl.getDefaultInitialCapacity();
		Cache<String, String> cache1 = cacheFactoryImpl.getCache(defaultInitialCacheCapacity, 1000);
		testCache(cache1, 1000);

		Cache<String, String> cache2 = cacheFactoryImpl.getConcurrentCache(defaultInitialCacheCapacity, 10);

		for (int i = 0; i < 100; i++) {

			String key = "key" + (i % 25);
			String value = "value" + i;
			String cachedString = cache2.get(key);

			if (cachedString == null) {
				cachedString = cache2.putIfAbsent(key, value);
			}

			Assert.assertNotNull(cachedString);
			cachedString = cache2.put(key, value);
			Assert.assertEquals(value, cachedString);
		}

		int maxCacheCapacity = 10;
		Cache<String, String> cache3 = cacheFactoryImpl.getCache(defaultInitialCacheCapacity, maxCacheCapacity);

		for (int i = 0; i < maxCacheCapacity; i++) {
			cache3.putIfAbsent("key" + i, "value" + i);
		}

		// Verify that get(), put(), and putIfAbsent() all mark the entry as recently used.
		Assert.assertNotNull(cache3.get("key0"));
		cache3.putIfAbsent("key1", "value1");
		cache3.put("key2", "value2");
		cache3.put("key" + maxCacheCapacity, "value" + maxCacheCapacity);
		Assert.assertNull(cache3.get("key3"));
	}

	@Test
	public void runCacheTest() {

		CacheFactoryImpl cacheFactoryImpl = new CacheFactoryImpl();
		Cache<String, String> cache = cacheFactoryImpl.getCache();
		testCache(cache, 1000);
	}

	@Test
	public void runConcurrentCacheMaxCapacityLRUTest() throws Exception {

		CacheFactoryImpl cacheFactoryImpl = new CacheFactoryImpl();
		int defaultInitialCacheCapacity = cacheFactoryImpl.getDefaultInitialCapacity();
		Cache<String, String> cache1 = cacheFactoryImpl.getConcurrentCache(defaultInitialCacheCapacity, 1000);
		testCache(cache1, 1000);

		final Cache<String, String> cache2 = cacheFactoryImpl.getConcurrentCache(defaultInitialCacheCapacity, 10);
		final Queue<Throwable> testErrors = new ConcurrentLinkedQueue<Throwable>();
		final Queue<AssertionError> testFailures = new ConcurrentLinkedQueue<AssertionError>();
		testConcurrentCache(cache2, testErrors, testFailures);

		final Cache<String, String> cache3 = cacheFactoryImpl.getConcurrentCache(defaultInitialCacheCapacity, 10);
		testConcurrentMaxCacheCapacityCache(cache3, 10, testErrors, testFailures);
	}

	@Test
	public void runConcurrentCacheTest() throws Exception {

		CacheFactoryImpl cacheFactoryImpl = new CacheFactoryImpl();
		Cache<String, String> cache1 = cacheFactoryImpl.getConcurrentCache();
		testCache(cache1, 1000);

		final Cache<String, String> cache2 = cacheFactoryImpl.getConcurrentCache();
		final Queue<AssertionError> testFailures = new ConcurrentLinkedQueue<AssertionError>();
		final Queue<Throwable> testErrors = new ConcurrentLinkedQueue<Throwable>();
		testConcurrentCache(cache2, testErrors, testFailures);
	}

	private void errorOrFailTestIfNecessary(final Queue<Throwable> testErrors, final Queue<AssertionError> testFailures)
		throws AssertionError, Exception {

		for (Throwable testError : testErrors) {
			logger.error("", testError);
		}

		for (AssertionError testFailure : testFailures) {
			logger.error("", testFailure);
		}

		int testErrorsCapacity = testErrors.size();

		if (testErrorsCapacity > 0) {
			throw new Exception(testErrorsCapacity + " threads threw an error during test execution.");
		}

		int testFailuresCapacity = testFailures.size();

		if (testFailuresCapacity > 0) {
			throw new AssertionError(testFailuresCapacity + " threads reported a failure during test execution.");
		}
	}

	private void testCache(Cache cache, int iterations) {

		for (int i = 0; i < iterations; i++) {

			String key = "key" + i;
			String value = "value" + i;
			Assert.assertNull(cache.get(key));
			Assert.assertEquals(value, cache.put(key, value));
			Assert.assertEquals(value, cache.putIfAbsent(key, "different" + value));
			Assert.assertEquals(value, cache.get(key));
			Assert.assertEquals("different" + value, cache.put(key, "different" + value));
			Assert.assertEquals("different" + value, cache.get(key));

			if ((i % 2) == 1) {

				Assert.assertEquals("different" + value, cache.remove(key));
				Assert.assertNull(cache.get(key));
			}
		}

		Assert.assertEquals(iterations / 2, cache.keySet().size());
	}

	private void testConcurrentCache(Cache<String, String> cache, Queue<Throwable> testErrors,
		Queue<AssertionError> testFailures) throws Exception {

		Thread[] threads = new Thread[100];
		Random random = new Random(System.currentTimeMillis());

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new TestConcurrentCacheThread(cache, testFailures, testErrors, "key" + (i % 25), "value" + i,
					random.nextInt(25));
		}

		for (Thread thread : threads) {
			thread.start();
		}

		errorOrFailTestIfNecessary(testErrors, testFailures);
	}

	private void testConcurrentMaxCacheCapacityCache(final Cache cache, final int maxCacheCapacity,
		Queue<Throwable> testErrors, Queue<AssertionError> testFailures) throws Exception {

		if (maxCacheCapacity < 10) {
			throw new IllegalArgumentException("This test must be run with at least 10 values.");
		}

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {

					for (int i = 0; i < maxCacheCapacity; i++) {
						cache.putIfAbsent("key" + i, "value" + i);
					}
				}
			}.run();

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {
					Assert.assertNotNull(cache.get("key0"));
				}
			}.run();

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {
					cache.putIfAbsent("key1", "value1");
				}
			}.run();

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {
					cache.put("key2", "value2");
				}
			}.run();

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {
					cache.put("key" + maxCacheCapacity, "value" + maxCacheCapacity);
				}
			}.run();

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {
					Assert.assertNull(cache.get("key3"));
				}
			}.run();

		errorOrFailTestIfNecessary(testErrors, testFailures);
	}

	private abstract static class TestThreadBase extends Thread {

		// Protected Final Data Members
		protected final Cache<String, String> cache;
		protected final Queue<AssertionError> testFailures;
		protected final Queue<Throwable> testErrors;
		protected final Integer sleepTimeInMillis;

		public TestThreadBase(Cache<String, String> cache, Queue<AssertionError> testFailures,
			Queue<Throwable> testErrors) {
			this(cache, testFailures, testErrors, null);
		}

		public TestThreadBase(Cache<String, String> cache, Queue<AssertionError> testFailures,
			Queue<Throwable> testErrors, Integer sleepTimeInMillis) {
			this.cache = cache;
			this.testFailures = testFailures;
			this.testErrors = testErrors;
			this.sleepTimeInMillis = sleepTimeInMillis;
		}

		@Override
		public void run() {

			if (sleepTimeInMillis != null) {

				try {
					sleep(sleepTimeInMillis);
				}
				catch (InterruptedException e) {
					// do nothing.
				}
			}

			try {
				testCache();
			}
			catch (AssertionError e) {
				testFailures.add(e);
			}
			catch (Throwable e) {
				testErrors.add(e);
			}
		}

		protected abstract void testCache();
	}

	private static final class TestConcurrentCacheThread extends TestThreadBase {

		// Private Final Data Members
		protected final String key;
		protected final String value;

		public TestConcurrentCacheThread(Cache<String, String> cache, Queue<AssertionError> testFailures,
			Queue<Throwable> testErrors, String key, String value, int sleepTimeInMillis) {

			super(cache, testFailures, testErrors, sleepTimeInMillis);
			this.key = key;
			this.value = value;
		}

		@Override
		protected void testCache() {

			String cachedString = cache.get(key);

			if (cachedString == null) {
				cachedString = cache.putIfAbsent(key, value);
			}

			Assert.assertNotNull(cachedString);
			cachedString = cache.put(key, value);
			Assert.assertEquals(value, cachedString);
		}
	}
}
