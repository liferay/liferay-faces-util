/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;


/**
 * @author  Neil Griffin
 */
public class BeanStartupImpl implements Bean {

	private Class<?> beanClass;

	public BeanStartupImpl(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public Object create(CreationalContext creationalContext) {

		try {
			return beanClass.newInstance();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void destroy(Object instance, CreationalContext creationalContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getBeanClass() {
		return beanClass;
	}

	@Override
	public Set<InjectionPoint> getInjectionPoints() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Annotation> getQualifiers() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<? extends Annotation> getScope() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Class<? extends Annotation>> getStereotypes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Type> getTypes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAlternative() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNullable() {
		throw new UnsupportedOperationException();
	}
}
