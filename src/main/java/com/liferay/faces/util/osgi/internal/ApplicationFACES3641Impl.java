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
package com.liferay.faces.util.osgi.internal;

import javax.faces.application.Application;
import javax.faces.application.ApplicationWrapper;
import javax.faces.convert.Converter;

/**
 * This class exists to workaround <a href="https://issues.liferay.com/browse/FACES-3641">FACES-3641</a>
 *
 * @author Neil Griffin
 */
public class ApplicationFACES3641Impl extends ApplicationWrapper {

	public ApplicationFACES3641Impl(Application wrapped) {
		super(wrapped);
	}

	@Override
	public Converter createConverter(Class<?> targetClass) {

		if (boolean.class.equals(targetClass)) {
			targetClass = Boolean.class;
		}
		else if (byte.class.equals(targetClass)) {
			targetClass = Byte.class;
		}
		else if (char.class.equals(targetClass)) {
			targetClass = Character.class;
		}
		else if (double.class.equals(targetClass)) {
			targetClass = Double.class;
		}
		else if (float.class.equals(targetClass)) {
			targetClass = Float.class;
		}
		else if (int.class.equals(targetClass)) {
			targetClass = Integer.class;
		}
		else if (long.class.equals(targetClass)) {
			targetClass = Long.class;
		}
		else if (short.class.equals(targetClass)) {
			targetClass = Short.class;
		}

		return super.createConverter(targetClass);
	}
}
