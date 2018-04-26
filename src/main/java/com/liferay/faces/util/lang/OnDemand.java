/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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

/**
 * This class represents a lazily initialized value. The value is lazily initialized in a thread-safe way such that
 * {@link #computeInitialValue(java.lang.Object)} is only called once by a single thread.
 *
 * @param   <OnDemandType>  The type that will be returned from the {@link #get(java.lang.Object)} and {@link
 *                          #computeInitialValue(java.lang.Object)} methods.
 * @param   <Arguments>     The type of arguments that will be passed to the {@link #get(java.lang.Object)} and {@link
 *                          #computeInitialValue(java.lang.Object)} methods in order to initialize the {@link OnDemand}
 *                          value.
 *
 * @author  Kyle Stiemann
 */
public abstract class OnDemand<OnDemandType, Arguments> {

	// Instance field must be declared volatile in order for the double-check idiom to work (requires JRE 1.5+)
	private volatile OnDemandType onDemandItem;

	/**
	 * Returns the {@link OnDemand} value. The value is lazily initialized by the first thread that attempts to access
	 * it.
	 *
	 * @param  args  The arguments needed to initialize the {@link OnDemand} value.
	 */
	public final OnDemandType get(Arguments args) {

		OnDemandType onDemandItem = this.onDemandItem;

		// First check without locking (not yet thread-safe)
		if (onDemandItem == null) {

			synchronized (this) {

				onDemandItem = this.onDemandItem;

				// Second check with locking (thread-safe)
				if (onDemandItem == null) {
					onDemandItem = this.onDemandItem = computeInitialValue(args);
				}
			}
		}

		return onDemandItem;
	}

	/**
	 * Returns true if the {@link OnDemand} value has been initialized.
	 */
	public final boolean isInitialized() {
		return onDemandItem != null;
	}

	/**
	 * Returns the initial value of the {@link OnDemand}. This method will only be called once by a single thread to
	 * obtain the initial value. Subclasses must override this method to provide the process necessary to compute the
	 * initial value of the {@link OnDemand}.
	 *
	 * @param  args  The arguments needed to initialize the {@link OnDemand} value.
	 */
	protected abstract OnDemandType computeInitialValue(Arguments args);
}
