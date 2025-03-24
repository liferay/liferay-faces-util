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
package com.liferay.faces.util.model;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * @author Neil Griffin
 */
@ConsumerType
public class SortCriterion {

	/**
	 * @author Neil Griffin
	 */
	public enum Order {
		ASCENDING, DESCENDING, NONE
	}

	// Private Data Members
	private String columnId;
	private Order order;

	public SortCriterion(String columnId, Order order) {
		this.columnId = columnId;
		this.order = order;
	}

	public String getColumnId() {
		return columnId;
	}

	public Order getOrder() {
		return order;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
