/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.text;

import com.liferay.faces.util.helper.Wrapper;

import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Neil Griffin
 */
public abstract class RichTextFactory implements Wrapper<RichTextFactory> {

	/**
	 * @return  an instance of {@link RichText} from the {@link RichTextFactory} found by the {@link
	 *          FactoryExtensionFinder}.
	 */
	public static RichText getRichTextInstance(RichText.Type type, String value) {

		RichTextFactory richTextFactory = (RichTextFactory) FactoryExtensionFinder.getFactory(RichTextFactory.class);

		return richTextFactory.getRichText(type, value);
	}

	public abstract RichText getRichText(RichText.Type type, String value);
}
