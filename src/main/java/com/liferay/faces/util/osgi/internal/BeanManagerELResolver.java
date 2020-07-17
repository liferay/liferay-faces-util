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
package com.liferay.faces.util.osgi.internal;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;


/**
 * @author  Neil Griffin
 */
public class BeanManagerELResolver extends ELResolver {

	@Override
	public Class<?> getCommonPropertyType(ELContext elContext, Object base) {

		ELResolver beanManagerELResolver = getBeanManagerELResolver();

		if (beanManagerELResolver == null) {
			return null;
		}

		return beanManagerELResolver.getCommonPropertyType(elContext, base);
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext elContext, Object base) {

		ELResolver beanManagerELResolver = getBeanManagerELResolver();

		if (beanManagerELResolver == null) {
			return null;
		}

		return beanManagerELResolver.getFeatureDescriptors(elContext, base);
	}

	@Override
	public Class<?> getType(ELContext elContext, Object base, Object property) {

		ELResolver beanManagerELResolver = getBeanManagerELResolver();

		if (beanManagerELResolver == null) {
			return null;
		}

		return beanManagerELResolver.getType(elContext, base, property);
	}

	@Override
	public Object getValue(ELContext elContext, Object base, Object property) {

		ELResolver beanManagerELResolver = getBeanManagerELResolver();

		if (beanManagerELResolver == null) {
			return null;
		}

		return beanManagerELResolver.getValue(elContext, base, property);
	}

	@Override
	public boolean isReadOnly(ELContext elContext, Object base, Object property) {

		ELResolver beanManagerELResolver = getBeanManagerELResolver();

		if (beanManagerELResolver == null) {
			return true;
		}

		return beanManagerELResolver.isReadOnly(elContext, base, property);
	}

	@Override
	public void setValue(ELContext elContext, Object base, Object property, Object value) {

		ELResolver beanManagerELResolver = getBeanManagerELResolver();

		if (beanManagerELResolver != null) {
			beanManagerELResolver.setValue(elContext, base, property, value);
		}
	}

	private ELResolver getBeanManagerELResolver() {

		CDI<Object> currentCDI = CDI.current();

		BeanManager beanManager = currentCDI.getBeanManager();

		if (beanManager == null) {
			return null;
		}

		return beanManager.getELResolver();
	}
}
