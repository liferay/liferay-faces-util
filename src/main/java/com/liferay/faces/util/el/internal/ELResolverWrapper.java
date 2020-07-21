/**
 * Copyright (c) 2000-2020 Liferay, Inc. All rights reserved.
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
import javax.faces.FacesWrapper;


/**
 * @author  Kyle Stiemann
 */
public abstract class ELResolverWrapper extends ELResolver implements FacesWrapper<ELResolver> {

	@Override
	public boolean equals(Object obj) {
		return getWrapped().equals(obj);
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return getWrapped().getCommonPropertyType(context, base);
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		return getWrapped().getFeatureDescriptors(context, base);
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		return getWrapped().getType(context, base, property);
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		return getWrapped().getValue(context, base, property);
	}

	@Override
	public int hashCode() {
		return getWrapped().hashCode();
	}

	@Override
	public Object invoke(ELContext context, Object base, Object method, Class<?>[] paramTypes, Object[] params) {
		return getWrapped().invoke(context, base, method, paramTypes, params);
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		return getWrapped().isReadOnly(context, base, property);
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		getWrapped().setValue(context, base, property, value);
	}

	@Override
	public String toString() {
		return getWrapped().toString();
	}
}
