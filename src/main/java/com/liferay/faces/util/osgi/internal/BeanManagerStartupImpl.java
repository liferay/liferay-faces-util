/**
 * Copyright (c) 2000-2022 Liferay, Inc. All rights reserved.
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
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InjectionTargetFactory;
import javax.enterprise.inject.spi.InterceptionFactory;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.ProducerFactory;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BeanManagerStartupImpl implements BeanManager {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BeanManagerStartupImpl.class);

	// Private Data Members
	private ELResolver elResolver;
	private boolean facesConfigAnnotationPresent;

	public BeanManagerStartupImpl(ELResolver elResolver, boolean facesConfigAnnotationPresent) {
		this.elResolver = elResolver;
		this.facesConfigAnnotationPresent = facesConfigAnnotationPresent;
	}

	@Override
	public boolean areInterceptorBindingsEquivalent(Annotation interceptorBinding1, Annotation interceptorBinding2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean areQualifiersEquivalent(Annotation qualifier1, Annotation qualifier2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> AnnotatedType<T> createAnnotatedType(Class<T> type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> Bean<T> createBean(BeanAttributes<T> attributes, Class<T> beanClass,
		InjectionTargetFactory<T> injectionTargetFactory) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T, X> Bean<T> createBean(BeanAttributes<T> attributes, Class<X> beanClass,
		ProducerFactory<X> producerFactory) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> BeanAttributes<T> createBeanAttributes(AnnotatedType<T> type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BeanAttributes<?> createBeanAttributes(AnnotatedMember<?> type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> CreationalContext<T> createCreationalContext(Contextual<T> contextual) {
		return new CreationalContextStartupImpl<T>();
	}

	@Override
	public InjectionPoint createInjectionPoint(AnnotatedField<?> field) {
		throw new UnsupportedOperationException();
	}

	@Override
	public InjectionPoint createInjectionPoint(AnnotatedParameter<?> parameter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> InjectionTarget<T> createInjectionTarget(AnnotatedType<T> type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Instance<Object> createInstance() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> InterceptionFactory<T> createInterceptionFactory(CreationalContext<T> ctx, Class<T> clazz) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void fireEvent(Object event, Annotation... qualifiers) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Bean<?>> getBeans(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Bean<?>> getBeans(Type beanType, Annotation... qualifiers) {

		Set<Bean<?>> beans = new HashSet<>();
		beans.add(new BeanStartupImpl((Class) beanType));

		return beans;
	}

	@Override
	public Context getContext(Class<? extends Annotation> scopeType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ELResolver getELResolver() {
		return elResolver;
	}

	@Override
	public Event<Object> getEvent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends Extension> T getExtension(Class<T> extensionClass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getInjectableReference(InjectionPoint ij, CreationalContext<?> ctx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> InjectionTargetFactory<T> getInjectionTargetFactory(AnnotatedType<T> annotatedType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Annotation> getInterceptorBindingDefinition(Class<? extends Annotation> bindingType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInterceptorBindingHashCode(Annotation interceptorBinding) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Bean<?> getPassivationCapableBean(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <X> ProducerFactory<X> getProducerFactory(AnnotatedField<? super X> field, Bean<X> declaringBean) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <X> ProducerFactory<X> getProducerFactory(AnnotatedMethod<? super X> method, Bean<X> declaringBean) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getQualifierHashCode(Annotation qualifier) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getReference(Bean<?> bean, Type beanType, CreationalContext<?> ctx) {

		try {
			Class<?> beanClass = bean.getBeanClass();
			Object newInstance = beanClass.newInstance();
			String typeTypeName = beanType.getTypeName();

			if (typeTypeName.equals("com.sun.faces.cdi.CdiExtension")) {
				Class<?> newInstanceClass = newInstance.getClass();
				Method method = newInstanceClass.getMethod("setAddBeansForJSFImplicitObjects",
						new Class[] { boolean.class });
				method.invoke(newInstance, new Object[] { facesConfigAnnotationPresent });
			}

			return newInstance;
		}
		catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

	@Override
	public Set<Annotation> getStereotypeDefinition(Class<? extends Annotation> stereotype) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInterceptorBinding(Class<? extends Annotation> annotationType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNormalScope(Class<? extends Annotation> annotationType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPassivatingScope(Class<? extends Annotation> annotationType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isQualifier(Class<? extends Annotation> annotationType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isScope(Class<? extends Annotation> annotationType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isStereotype(Class<? extends Annotation> annotationType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <X> Bean<? extends X> resolve(Set<Bean<? extends X>> beans) {

		if ((beans != null) && !beans.isEmpty()) {

			// Simply return the first bean
			for (Bean<? extends X> bean : beans) {
				return bean;
			}
		}

		return null;
	}

	@Override
	public List<Decorator<?>> resolveDecorators(Set<Type> types, Annotation... qualifiers) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Interceptor<?>> resolveInterceptors(InterceptionType type, Annotation... interceptorBindings) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> Set<ObserverMethod<? super T>> resolveObserverMethods(T event, Annotation... qualifiers) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void validate(InjectionPoint injectionPoint) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExpressionFactory wrapExpressionFactory(ExpressionFactory expressionFactory) {
		throw new UnsupportedOperationException();
	}
}
