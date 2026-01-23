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
package com.liferay.faces.util.osgi.internal;

import jakarta.el.ELContext;
import jakarta.el.ExpressionFactory;
import jakarta.el.MethodExpression;
import jakarta.el.ValueExpression;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.CDI;

/**
 * @author Neil Griffin
 */
public class BeanManagerExpressionFactory extends ExpressionFactory {

	// Private Data Members
	private ExpressionFactory wrappedExpressionFactory;
	private ExpressionFactory beanManagerExpressionFactory;

	public BeanManagerExpressionFactory(ExpressionFactory expressionFactory) {
		this.wrappedExpressionFactory = expressionFactory;
	}

	@Override
	public <T> T coerceToType(Object o, Class<T> aClass) {
		ExpressionFactory expressionFactory = getExpressionFactory();

		return expressionFactory.coerceToType(o, aClass);
	}

	@Override
	public MethodExpression createMethodExpression(ELContext elContext, String s, Class<?> aClass, Class<?>[] classes) {

		ExpressionFactory expressionFactory = getExpressionFactory();

		return expressionFactory.createMethodExpression(elContext, s, aClass, classes);
	}

	@Override
	public ValueExpression createValueExpression(Object o, Class<?> aClass) {

		ExpressionFactory expressionFactory = getExpressionFactory();

		return expressionFactory.createValueExpression(o, aClass);
	}

	@Override
	public ValueExpression createValueExpression(ELContext elContext, String s, Class<?> aClass) {

		ExpressionFactory expressionFactory = getExpressionFactory();

		return expressionFactory.createValueExpression(elContext, s, aClass);
	}

	private ExpressionFactory getExpressionFactory() {

		if (beanManagerExpressionFactory == null) {

			CDI<Object> currentCDI = CDI.current();

			if (currentCDI != null) {
				BeanManager beanManager = currentCDI.getBeanManager();

				if (beanManager != null) {
					beanManagerExpressionFactory = beanManager.wrapExpressionFactory(wrappedExpressionFactory);
				}
			}
		}

		if (beanManagerExpressionFactory == null) {
			return wrappedExpressionFactory;
		}

		return beanManagerExpressionFactory;
	}
}
