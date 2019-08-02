/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.osgi.internal;

import java.util.Collection;

import org.osgi.framework.Bundle;


/**
 * @author  Kyle Stiemann
 */
public abstract class FacesBundlesHandlerBase<ReturnValueType> {

	public final ReturnValueType handleFacesBundles(Object context) {

		ReturnValueType returnValueObject = getInitialReturnValueObject();
		ReturnValueReference<ReturnValueType> returnValueReference = new ReturnValueReference<ReturnValueType>(
				returnValueObject);

		if (context != null) {

			handleCurrentFacesWab(FacesBundleUtil.getCurrentFacesWab(context), returnValueReference);

			Collection<Bundle> facesBundles = FacesBundleUtil.getFacesBundles(context);

			if (facesBundles != null) {

				for (Bundle facesBundle : facesBundles) {
					handleFacesBundle(facesBundle, returnValueReference);
				}
			}
		}

		return returnValueReference.get();
	}

	protected abstract ReturnValueType getInitialReturnValueObject();

	protected abstract void handleFacesBundle(Bundle bundle,
		ReturnValueReference<ReturnValueType> returnValueReference);

	protected void handleCurrentFacesWab(Bundle currentFacesWab,
		ReturnValueReference<ReturnValueType> returnValueReference) {
		handleFacesBundle(currentFacesWab, returnValueReference);
	}

	protected static final class ReturnValueReference<ReturnValueType> {

		// Private Data Members
		private ReturnValueType returnValueObject;

		public ReturnValueReference(ReturnValueType returnValueObject) {
			this.returnValueObject = returnValueObject;
		}

		public ReturnValueType get() {
			return returnValueObject;
		}

		public boolean isEmpty() {
			return returnValueObject == null;
		}

		public void set(ReturnValueType returnValueObject) {
			this.returnValueObject = returnValueObject;
		}
	}
}
