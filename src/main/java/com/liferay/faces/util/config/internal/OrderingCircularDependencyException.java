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
package com.liferay.faces.util.config.internal;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;


/**
 * @author  Vernon Singleton
 */
public class OrderingCircularDependencyException extends Exception {

	// serialVersionUID
	private static final long serialVersionUID = 6848057442511766527L;

	public OrderingCircularDependencyException(Ordering.Path path, List<FacesConfigDescriptor> facesConfigs) {
		super(createMessage(path, facesConfigs));
	}

	private static String createMessage(Ordering.Path path, List<FacesConfigDescriptor> facesConfigs) {

		StringBuilder message = new StringBuilder();
		message.append("Circular dependencies detected when traversing '");

		message.append(path.name());
		message.append("' declarations:");

		for (FacesConfigDescriptor facesConfigDescriptor : facesConfigs) {
			Ordering someOrdering = facesConfigDescriptor.getOrdering();
			EnumMap<Ordering.Path, String[]> someRoutes = someOrdering.getRoutes();
			String[] someNames = someRoutes.get(path);

			if (someNames.length != 0) {
				message.append(" ");
				message.append(facesConfigDescriptor.getName());
				message.append(" ");
				message.append(path.name());
				message.append(": ");
				message.append(Arrays.asList(someNames).toString());
				message.append("\n");
			}
		}

		return message.toString();

	}
}
