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
package com.liferay.faces.util.el;

import java.beans.FeatureDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.application.Application;


/**
 * This is a convenience base class that extends {@link ELResolver}. A subclasses must be designed to be instantiated as
 * a singleton because the JavaDoc for {@link Application#getELResolver()} indicates that an {@code ELResolver} should
 * be a singleton instance. This class implements the {@link Serializable} interface as a clue to subclasses that they
 * should implement a stateless, thread-safe singleton design. Subclasses should call the static {@link
 * #addFeatureDescriptor(String, Class)} method from a {@code static} block in order to add to the list of feature
 * descriptors.
 *
 * @author  Neil Griffin
 */
public abstract class ELResolverBase extends ELResolver implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 8075201303544048292L;

	// Private Constants
	private static final ArrayList<FeatureDescriptor> FEATURE_DESCRIPTORS = new ArrayList<FeatureDescriptor>();

	protected static void addFeatureDescriptor(String featureName, Class<?> classType) {
		FeatureDescriptor featureDescriptor = new FeatureDescriptor();
		featureDescriptor.setName(featureName);
		featureDescriptor.setDisplayName(featureName);
		featureDescriptor.setShortDescription(featureName);
		featureDescriptor.setExpert(false);
		featureDescriptor.setHidden(false);
		featureDescriptor.setPreferred(true);
		featureDescriptor.setValue(ELResolver.TYPE, classType);
		featureDescriptor.setValue(ELResolver.RESOLVABLE_AT_DESIGN_TIME, true);
		FEATURE_DESCRIPTORS.add(featureDescriptor);
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext elContext, Object base) {
		return FEATURE_DESCRIPTORS.iterator();
	}

	@Override
	public Class<?> getType(ELContext elContext, Object base, Object property) {

		if (elContext == null) {

			// Throw an exception as directed by the JavaDoc for ELContext.
			throw new NullPointerException("elContext may not be null");
		}

		return String.class;
	}

	@Override
	public Object getValue(ELContext elContext, Object base, Object property) {

		if (elContext == null) {

			// Throw an exception as directed by the JavaDoc for ELContext.
			throw new NullPointerException("invalid ELContext");
		}
		else {

			Object value = null;

			if (base == null) {

				if (property instanceof String) {
					String varName = (String) property;
					value = resolveVariable(elContext, varName);
				}
			}
			else {

				if (property instanceof String) {
					String propertyName = (String) property;
					value = resolveProperty(elContext, base, propertyName);
				}
			}

			if (value != null) {
				elContext.setPropertyResolved(true);
			}

			return value;
		}
	}

	@Override
	public boolean isReadOnly(ELContext elContext, Object base, Object property) {
		return true;
	}

	@Override
	public void setValue(ELContext elContext, Object base, Object property, Object value) {

		if (elContext == null) {

			// Throw an exception as directed by the JavaDoc for ELContext.
			throw new NullPointerException("elContext may not be null");
		}
	}

	protected abstract Object resolveProperty(ELContext elContext, Object base, String property);

	protected abstract Object resolveVariable(ELContext elContext, String varName);
}
