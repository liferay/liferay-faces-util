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
package com.liferay.faces.util.jsp;

import jakarta.faces.view.facelets.FaceletHandler;
import jakarta.faces.view.facelets.Tag;
import jakarta.faces.view.facelets.TagConfig;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This is a dummy class that exists in order to prevent an exception from being thrown during startup on JBoss AS. For
 * more information, see <a href="http://issues.liferay.com/browse/FACES-1576">FACES-1576</a>.
 *
 * @author Neil Griffin
 */
@ProviderType
public class JspTagConfig implements TagConfig {

	// Private Data Members
	private FaceletHandler nextHandler;
	private Tag tag;
	private String tagId;

	public FaceletHandler getNextHandler() {
		return nextHandler;
	}

	public Tag getTag() {
		return tag;
	}

	public String getTagId() {
		return tagId;
	}

	public void setNextHandler(FaceletHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
}
