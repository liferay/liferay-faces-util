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
import com.liferay.faces.util.config.WebConfigParam;


/**
 * @author  Kyle Stiemann
 */
public class CacheTest {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);

	// Private Constants
	private static final int DEFAULT_INITIAL_CACHE_CAPACITY = 16;

	@Test
	public void runCacheFactoryIllegalArgumentExceptionTest() {

		CacheFactory cacheFactory = new CacheFactoryImpl();
		int[] invalidInitialCacheCapacityValues = new int[] { -1, -100, Integer.MIN_VALUE };

		for (int invalidInitialCacheCapacityValue : invalidInitialCacheCapacityValues) {

			try {

				cacheFactory.getConcurrentCache(invalidInitialCacheCapacityValue);
				throw new AssertionError(
					"Expected IllegalArgumentException was not thrown when initial cache capacity of " +
					invalidInitialCacheCapacityValue + " was passed.");
			}
			catch (IllegalArgumentException e) {
				// Do nothing.
			}
		}

		int[] validInitialCacheCapacityValues = new int[] { 0, DEFAULT_INITIAL_CACHE_CAPACITY, Integer.MAX_VALUE };

		for (int validInitialCacheCapacityValue : validInitialCacheCapacityValues) {

			try {
				cacheFactory.getConcurrentCache(validInitialCacheCapacityValue);
			}
			catch (IllegalArgumentException e) {
				throw new AssertionError(
					"Unexpected IllegalArgumentException was thrown when initial cache capacity of " +
					validInitialCacheCapacityValue + " was passed.");
			}
		}

		int[] invalidMaxCacheCapacityValues = new int[] { 0, -100, Integer.MIN_VALUE };

		for (int invalidMaxCacheCapacityValue : invalidMaxCacheCapacityValues) {

			try {

				cacheFactory.getConcurrentLRUCache(DEFAULT_INITIAL_CACHE_CAPACITY, invalidMaxCacheCapacityValue);
				throw new AssertionError(
					"Expected IllegalArgumentException was not thrown when max cache capacity of " +
					invalidMaxCacheCapacityValue + " was passed.");
			}
			catch (IllegalArgumentException e) {
				// Do nothing.
			}
		}

		int[] validMaxCacheCapacityValues = new int[] { 1, 100, Integer.MAX_VALUE };

		for (int validMaxCacheCapacityValue : validMaxCacheCapacityValues) {

			try {
				cacheFactory.getConcurrentLRUCache(DEFAULT_INITIAL_CACHE_CAPACITY, validMaxCacheCapacityValue);
			}
			catch (IllegalArgumentException e) {
				throw new AssertionError("Unexpected IllegalArgumentException was thrown when max cache capacity of " +
					validMaxCacheCapacityValue + " was passed.");
			}
		}
	}

	@Test
	public void runConcurrentCacheTest() throws Exception {

		CacheFactoryImpl cacheFactoryImpl = new CacheFactoryImpl();
		Cache<String, String> cache1 = cacheFactoryImpl.getConcurrentCache(DEFAULT_INITIAL_CACHE_CAPACITY);
		testCache(cache1, 1000);

		final Cache<String, String> cache2 = cacheFactoryImpl.getConcurrentCache(DEFAULT_INITIAL_CACHE_CAPACITY);
		final Queue<AssertionError> testFailures = new ConcurrentLinkedQueue<AssertionError>();
		final Queue<Throwable> testErrors = new ConcurrentLinkedQueue<Throwable>();
		testConcurrentCache(cache2, testErrors, testFailures);
	}

	@Test
	public void runConcurrentLRUCacheTest() throws Exception {

		CacheFactoryImpl cacheFactoryImpl = new CacheFactoryImpl();
		int maxCacheCapacity = 1000;
		Cache<String, String> cache1 = cacheFactoryImpl.getConcurrentLRUCache(DEFAULT_INITIAL_CACHE_CAPACITY,
				maxCacheCapacity);
		testCache(cache1, maxCacheCapacity);

		final Cache<String, String> cache2 = cacheFactoryImpl.getConcurrentLRUCache(DEFAULT_INITIAL_CACHE_CAPACITY, 10);
		final Queue<Throwable> testErrors = new ConcurrentLinkedQueue<Throwable>();
		final Queue<AssertionError> testFailures = new ConcurrentLinkedQueue<AssertionError>();
		testConcurrentCache(cache2, testErrors, testFailures);

		maxCacheCapacity = 10;

		final Cache<String, String> cache3 = cacheFactoryImpl.getConcurrentLRUCache(DEFAULT_INITIAL_CACHE_CAPACITY,
				maxCacheCapacity);
		testConcurrentLRUCache(cache3, maxCacheCapacity, testErrors, testFailures);
	}

	private void errorOrFailTestIfNecessary(final Queue<Throwable> testErrors, final Queue<AssertionError> testFailures)
		throws AssertionError, Exception {

		for (Throwable testError : testErrors) {
			logger.error("", testError);
		}

		for (AssertionError testFailure : testFailures) {
			logger.error("", testFailure);
		}

		int testErrorsSize = testErrors.size();

		if (testErrorsSize > 0) {
			throw new Exception(testErrorsSize + " threads threw an error during test execution.");
		}

		int testFailuresSize = testFailures.size();

		if (testFailuresSize > 0) {
			throw new AssertionError(testFailuresSize + " threads reported a failure during test execution.");
		}
	}

	private void testCache(Cache<String, String> cache, int iterations) {

		for (int i = 0; i < iterations; i++) {

			String key = "key" + i;
			String value = "value" + i;
			Assert.assertNull(cache.getValue(key));
			Assert.assertEquals(value, cache.putValueIfAbsent(key, value));
			Assert.assertEquals(value, cache.putValueIfAbsent(key, "different" + value));
			Assert.assertEquals(value, cache.getValue(key));
			Assert.assertEquals(value, cache.removeValue(key));
			Assert.assertEquals("different" + value, cache.putValueIfAbsent(key, "different" + value));
			Assert.assertEquals("different" + value, cache.getValue(key));

			if ((i % 2) == 1) {

				Assert.assertEquals("different" + value, cache.removeValue(key));
				Assert.assertNull(cache.getValue(key));
			}
		}

		Assert.assertEquals(iterations / 2, cache.getKeys().size());
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

	/**
	 * This test uses threads to put and access values in the cache to verify that the least-recently-used value will be
	 * ejected when the cache is full. Although this test utilizes threads, it cannot run them concurrently. Instead it
	 * calls {@link Thread#run()} to run the threads sequentially to ensure that the least-recently-used value is always
	 * the same for each test run. A similar non-concurrent version of this test appears in {@link #runLRUCacheTest()}.
	 */
	private void testConcurrentLRUCache(final Cache<String, String> cache, final int maxCacheCapacity,
		Queue<Throwable> testErrors, Queue<AssertionError> testFailures) throws Exception {

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {

					for (int i = 0; i < maxCacheCapacity; i++) {
						cache.putValueIfAbsent("key" + i, "value" + i);
					}
				}
			}.run();

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {
					Assert.assertNotNull(cache.getValue("key0"));
				}
			}.run();

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {
					cache.putValueIfAbsent("key1", "value1");
				}
			}.run();

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {
					cache.putValueIfAbsent("key" + maxCacheCapacity, "value" + maxCacheCapacity);
				}
			}.run();

		new TestThreadBase(cache, testFailures, testErrors) {

				@Override
				protected void testCache() {
					Assert.assertNull(cache.getValue("key2"));
				}
			}.run();

		errorOrFailTestIfNecessary(testErrors, testFailures);
		Assert.assertEquals(maxCacheCapacity, cache.getSize());
	}

	private abstract static class TestThreadBase extends Thread {

		// Final Data Members
		final Cache<String, String> cache;
		final Queue<AssertionError> testFailures;
		final Queue<Throwable> testErrors;
		final Integer sleepTimeInMillis;

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

		// Final Data Members
		final String key;
		final String value;

		public TestConcurrentCacheThread(Cache<String, String> cache, Queue<AssertionError> testFailures,
			Queue<Throwable> testErrors, String key, String value, int sleepTimeInMillis) {

			super(cache, testFailures, testErrors, sleepTimeInMillis);
			this.key = key;
			this.value = value;
		}

		@Override
		protected void testCache() {

			String cachedString = cache.getValue(key);

			if (cachedString == null) {
				cachedString = cache.putValueIfAbsent(key, value);
			}

			Assert.assertNotNull(cachedString);
		}
	}
}
