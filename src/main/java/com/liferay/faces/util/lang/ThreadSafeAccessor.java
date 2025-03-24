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
package com.liferay.faces.util.lang;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * This class represents a lazily initialized, effectively immutable value. The value is initialized in a thread-safe
 * way such that {@link #computeValue(java.lang.Object)} is only called once by a single thread.
 *
 * @param   <T>  The type that will be returned from the {@link #get(java.lang.Object)} and {@link
 *               #computeValue(java.lang.Object)} methods.
 * @param   <X>  The type of argument that will be passed to the {@link #get(java.lang.Object)} and {@link
 *               #computeValue(java.lang.Object)} methods in order to initialize the {@link ThreadSafeAccessor} value.
 *
 * @author  Kyle Stiemann
 */
@ConsumerType
public abstract class ThreadSafeAccessor<T, X> {

	// Instance field must be declared volatile in order for the double-check idiom to work (requires JRE 1.5+)
	private volatile T t;

	/**
	 * Returns the {@link ThreadSafeAccessor} value. The value is lazily initialized by the first thread that attempts
	 * to access it.
	 *
	 * @param  arg  The argument needed to initialize the {@link ThreadSafeAccessor} value.
	 */
	public final T get(X arg) {

		T t = this.t;

		// First check without locking (not yet thread-safe)
		if (t == null) {

			synchronized (this) {

				t = this.t;

				// Second check with locking (thread-safe)
				if (t == null) {
					t = this.t = computeValue(arg);
				}
			}
		}

		return t;
	}

	/**
	 * Returns the initial value of the {@link ThreadSafeAccessor}. This method will only be called once by a single
	 * thread to obtain the initial value. Subclasses must override this method to provide the process necessary to
	 * compute the initial value of the {@link ThreadSafeAccessor}.
	 *
	 * @param  args  The argument needed to initialize the {@link ThreadSafeAccessor} value.
	 */
	protected abstract T computeValue(X args);
}
