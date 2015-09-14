/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public class ComponentStateHelper implements StateHelper {

	private UIComponent uiComponent;
	private Map<Serializable, Object> stateMap;

	public ComponentStateHelper(UIComponent uiComponent) {
		this.uiComponent = uiComponent;
		this.stateMap = new HashMap<Serializable, Object>();
	}

	public Object eval(Serializable propertyKey, Object defaultValue) {

		Object value = stateMap.get(propertyKey);

		if (value == null) {

			ValueExpression valueExpression = uiComponent.getValueExpression(propertyKey.toString());

			if (valueExpression != null) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ELContext elContext = facesContext.getELContext();
				value = valueExpression.getValue(elContext);
			}
		}

		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	public void put(Serializable propertyKey, Object value) {
		stateMap.put(propertyKey, value);
	}

	@Override
	public void restoreState(FacesContext facesContext, Object state) {

		if (state != null) {

			Object[] stateArray = (Object[]) state;
			Serializable[] keys = (Serializable[]) stateArray[0];

			for (int i = 1; i < stateArray.length; i++) {
				stateMap.put(keys[i - 1], stateArray[i]);
			}
		}
	}

	@Override
	public Object saveState(FacesContext facesContext) {

		Set<Map.Entry<Serializable, Object>> entrySet = stateMap.entrySet();
		int entrySetSize = entrySet.size();
		Serializable[] keys = new Serializable[entrySetSize];
		Object[] state = new Object[entrySetSize + 1];
		int i = 0;

		for (Map.Entry<Serializable, Object> mapEntry : entrySet) {
			keys[i++] = mapEntry.getKey();
			state[i] = mapEntry.getValue();
		}

		state[0] = keys;

		return state;
	}
}
