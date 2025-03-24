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
package com.liferay.faces.util.component;

import org.osgi.annotation.versioning.ProviderType;


/**
 * This interface should be implemented by classes that extend {@link javax.faces.component.UIComponent} if they render
 * HTML that is styleable with CSS class names.
 *
 * @author  Neil Griffin
 */
@ProviderType
public interface Styleable {

	// Public Constants
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";

	public String getStyle();

	public String getStyleClass();

	public void setStyle(String style);

	public void setStyleClass(String styleClass);
}
