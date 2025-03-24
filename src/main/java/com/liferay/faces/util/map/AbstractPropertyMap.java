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
package com.liferay.faces.util.map;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Neil Griffin
 */
@ProviderType
public abstract class AbstractPropertyMap<V> implements Map<String, V> {

	public void clear() {
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {

			while (propertyNames.hasMoreElements()) {
				removeProperty(propertyNames.nextElement());
			}
		}
	}

	public boolean containsKey(Object key) {
		boolean found = false;

		if (key != null) {
			String keyAsString = key.toString();

			// NOTE: This is an inefficient mechanism because getPropertyNames() can potentially be slow since it has to
			// return a new (non-cached) Enumeration each time it is called. Because of this, it is best to override
			// this containsKey method with optimizations when possible.
			Enumeration<String> propertyNames = getPropertyNames();

			if (propertyNames != null) {

				while (!found && propertyNames.hasMoreElements()) {
					String propertyName = propertyNames.nextElement();
					found = propertyName.equals(keyAsString);
				}
			}
		}

		return found;
	}

	public boolean containsValue(Object value) {
		boolean found = false;
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {

			while (!found && propertyNames.hasMoreElements()) {
				String propertyName = propertyNames.nextElement();
				Object propertyValue = getProperty(propertyName);

				if (propertyValue == null) {
					found = (value == null);
				}
				else {
					found = propertyValue.equals(value);
				}
			}
		}

		return found;
	}

	public Set<Map.Entry<String, V>> entrySet() {
		Set<Map.Entry<String, V>> entrySet = null;
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {
			entrySet = new PropertyMapEntrySet<V>();

			while (propertyNames.hasMoreElements()) {
				String name = propertyNames.nextElement();
				AbstractPropertyMapEntry<V> propertyMapEntry = createPropertyMapEntry(name);
				entrySet.add(propertyMapEntry);
			}
		}

		return entrySet;
	}

	public V get(Object key) {
		V value = null;

		if (key != null) {
			String keyAsString = key.toString();
			value = getProperty(keyAsString);
		}

		return value;
	}

	public boolean isEmpty() {
		Enumeration<String> propertyNames = getPropertyNames();

		return ((propertyNames == null) || !propertyNames.hasMoreElements());
	}

	public Set<String> keySet() {
		Set<String> keySet = null;
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {
			keySet = new HashSet<String>();

			while (propertyNames.hasMoreElements()) {
				String propertyName = propertyNames.nextElement();
				keySet.add(propertyName);
			}
		}

		return keySet;
	}

	public V put(String key, V value) {
		V oldValue = getProperty(key);
		setProperty(key, value);

		return oldValue;
	}

	public void putAll(Map<? extends String, ? extends V> t) {

		if (t != null) {
			Set<? extends String> keySet = t.keySet();

			if (keySet != null) {

				for (String key : keySet) {
					setProperty(key, t.get(key));
				}
			}
		}
	}

	public V remove(Object key) {
		V oldValue = null;

		if (key != null) {
			String keyAsString = key.toString();
			oldValue = getProperty(keyAsString);
			removeProperty(keyAsString);
		}

		return oldValue;
	}

	public int size() {
		int size = 0;
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {

			while (propertyNames.hasMoreElements()) {
				size++;
				propertyNames.nextElement();
			}
		}

		return size;
	}

	public Collection<V> values() {
		Collection<V> values = null;
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {
			values = new HashSet<V>();

			while (propertyNames.hasMoreElements()) {
				String propertyName = propertyNames.nextElement();
				V value = getProperty(propertyName);
				values.add(value);
			}
		}

		return values;
	}

	protected abstract AbstractPropertyMapEntry<V> createPropertyMapEntry(String name);

	protected abstract V getProperty(String name);

	protected abstract Enumeration<String> getPropertyNames();

	protected abstract void removeProperty(String name);

	protected abstract void setProperty(String name, V value);
}
