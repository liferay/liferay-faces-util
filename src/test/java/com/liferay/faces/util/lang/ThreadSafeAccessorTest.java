/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.lang;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author  Kyle Stiemann
 */
public class ThreadSafeAccessorTest {

	// Private Constants
	private static final long MAX_WAIT_TIME_IN_SECONDS = 3;
	private static final OnDemandTestResult STATIC_ON_DEMAND_TEST_RESULT = new OnDemandTestResult();
	private static final long WAIT_INTERVAL_IN_MILLIS = 10;

	// Private Final Data Members
	private final OnDemandTestResult onDemandTestResult = new OnDemandTestResult();

	private static void assertTrue(OnDemandTestResult onDemandTestResult, Thread testThread) {
		Assert.assertTrue("More than " + MAX_WAIT_TIME_IN_SECONDS +
			" seconds elapsed waiting for the thread to be blocked.", onDemandTestResult.get(testThread));
	}

	private static void testThreadSafe(final OnDemandTestResult ON_DEMAND_TEST_RESULT) {

		final Thread TEST_THREAD = Thread.currentThread();

		// Create and start a thread that will get stuck in the while loop in TestResultInitializer until the current
		// thread requests the test result value.
		Thread waitingForWhileLoopThread = new Thread() {
				@Override
				public void run() {
					assertTrue(ON_DEMAND_TEST_RESULT, TEST_THREAD);
				}
			};

		waitingForWhileLoopThread.start();

		// Wait for waitingForWhileLoopThread to enter the while loop in TestResultInitializer.
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {
			// Do nothing.
		}

		// Attempt to obtain the test result. If the current thread is blocked, waitingForWhileLoopThread will exit the
		// while loop in ThreadSafeAccessor and the current thread will be unblocked and ThreadSafeAccessor.get() will
		// return true.
		assertTrue(ON_DEMAND_TEST_RESULT, TEST_THREAD);
	}

	@Test
	public void testStaticThreadSafe() {
		testThreadSafe(STATIC_ON_DEMAND_TEST_RESULT);
	}

	@Test
	public void testThreadSafe() {
		testThreadSafe(onDemandTestResult);
	}

	private static final class OnDemandTestResult extends ThreadSafeAccessor<Boolean, Thread> {

		@Override
		protected Boolean computeValue(Thread thread) {

			int waitTime = 0;

			// Wait until the passed thread is blocked to prove that at most only 1 thread can access the
			// computeValue() method.
			while (!Thread.State.BLOCKED.equals(thread.getState())) {

				Assert.assertFalse(Thread.currentThread().equals(thread));

				// Sleep
				try {
					Thread.sleep(WAIT_INTERVAL_IN_MILLIS);
					waitTime += WAIT_INTERVAL_IN_MILLIS;
				}
				catch (InterruptedException e) {
					// Do nothing.
				}

				// Fail the test (break the infinite loop) if the max wait time has been exceeded.
				if (waitTime > ((MAX_WAIT_TIME_IN_SECONDS + 1) * 1000)) {
					return false;
				}
			}

			return true;
		}
	}
}
