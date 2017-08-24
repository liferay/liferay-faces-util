/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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

/**
 * This interface should be implemented by classes that extend {@link javax.faces.component.UIComponent}
 * if they provide the ability to access the value of a client-side UI component instance (Liferay Component)
 * via JavaScript.
 *
 * @author  Neil Griffin
 */
public interface ClientComponent {

	public static final String CLIENT_KEY = "clientKey";

	public String getClientId();

	public String getClientKey();

	public void setClientKey(String clientKey);
}
