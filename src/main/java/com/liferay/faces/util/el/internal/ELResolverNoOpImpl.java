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
package com.liferay.faces.util.el.internal;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;


/**
 * @author  Kyle Stiemann
 */
public class ELResolverNoOpImpl extends ELResolver {

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		context.setPropertyResolved(false);

		return null;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		context.setPropertyResolved(false);

		return null;
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		context.setPropertyResolved(false);

		return null;
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		context.setPropertyResolved(false);

		return null;
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		context.setPropertyResolved(false);

		return false;
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		context.setPropertyResolved(false);
	}
}
