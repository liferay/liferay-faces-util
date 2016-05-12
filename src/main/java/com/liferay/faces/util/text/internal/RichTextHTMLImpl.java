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
package com.liferay.faces.util.text.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author  Neil Griffin
 */
public class RichTextHTMLImpl extends RichTextBaseImpl {

	// Private Constants
	private static final Pattern TAG_PATTERN = Pattern.compile("<.+?>");
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

	public RichTextHTMLImpl(String value) {
		super(value);
	}

	@Override
	public int getPlainTextLength() {

		int count = 0;

		String value = getValue();

		if (value != null) {
			String cleanString = null;

			if (value instanceof String) {
				cleanString = (String) value;
			}
			else {
				cleanString = value.toString();
			}

			Matcher tagMatcher = TAG_PATTERN.matcher(cleanString);

			cleanString = tagMatcher.replaceAll("");
			cleanString = cleanString.replaceAll("&nbsp;", " ");
			cleanString = WHITESPACE_PATTERN.matcher(cleanString).replaceAll(" ");
			cleanString = cleanString.trim();
			count = cleanString.length();
		}

		return count;
	}

	@Override
	public Type getType() {
		return Type.HTML;
	}
}
