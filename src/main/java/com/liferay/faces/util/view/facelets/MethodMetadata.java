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
package com.liferay.faces.util.view.facelets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jakarta.el.MethodExpression;
import jakarta.faces.view.facelets.FaceletContext;
import jakarta.faces.view.facelets.Metadata;
import jakarta.faces.view.facelets.TagAttribute;
import jakarta.faces.view.facelets.TagAttributeException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Neil Griffin
 */
@ProviderType
public class MethodMetadata extends Metadata {

	// Private Data Members
	Class<?>[] args;
	TagAttribute tagAttribute;
	Method writeMethod;

	public MethodMetadata(TagAttribute tagAttribute, Method writeMethod, Class<?>[] args) {
		this.tagAttribute = tagAttribute;
		this.writeMethod = writeMethod;
		this.args = args;
	}

	@Override
	public void applyMetadata(FaceletContext faceletContext, Object instance) {
		MethodExpression methodExpression = tagAttribute.getMethodExpression(faceletContext, null, args);

		try {
			writeMethod.invoke(instance, methodExpression);
		}
		catch (InvocationTargetException e) {
			throw new TagAttributeException(tagAttribute, e.getCause());
		}
		catch (Exception e) {
			throw new TagAttributeException(tagAttribute, e);
		}
	}
}
