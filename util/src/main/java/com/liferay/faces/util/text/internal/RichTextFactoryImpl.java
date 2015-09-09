/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.text.internal;

import com.liferay.faces.util.text.RichText;
import com.liferay.faces.util.text.RichTextFactory;


/**
 * @author  Neil Griffin
 */
public class RichTextFactoryImpl extends RichTextFactory {

	@Override
	public RichText getRichText(RichText.Type type, String value) {

		switch (type) {

		case BBCODE: {
			return new RichTextBBCodeImpl(value);
		}

		case CREOLE: {
			return new RichTextCreoleImpl(value);
		}

		default: {
			return new RichTextHTMLImpl(value);
		}
		}
	}

	@Override
	public RichTextFactory getWrapped() {

		// Since this is the factory instance provided by default, it will never wrap another factory.
		return null;
	}
}
