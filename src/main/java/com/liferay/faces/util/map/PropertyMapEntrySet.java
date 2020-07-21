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
package com.liferay.faces.util.map;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;


/**
 * @author  Neil Griffin
 */
@ProviderType
public class PropertyMapEntrySet<V> extends HashSet<Map.Entry<String, V>> {

	// serialVersionUID
	private static final long serialVersionUID = 6500855053442038977L;

	@Override
	public Iterator<Map.Entry<String, V>> iterator() {
		return new IteratorWrapper(super.iterator());
	}

	public class IteratorWrapper implements Iterator<Map.Entry<String, V>> {

		private Map.Entry<String, V> currentEntry;
		private Iterator<Map.Entry<String, V>> wrappedIterator;

		public IteratorWrapper(Iterator<Map.Entry<String, V>> iterator) {
			this.wrappedIterator = iterator;
		}

		public boolean hasNext() {
			return wrappedIterator.hasNext();
		}

		public Map.Entry<String, V> next() {
			currentEntry = wrappedIterator.next();

			return currentEntry;
		}

		public void remove() {

			if (currentEntry != null) {

				if (currentEntry instanceof AbstractPropertyMapEntry) {
					AbstractPropertyMapEntry<V> propertyMapEntry = (AbstractPropertyMapEntry<V>) currentEntry;
					propertyMapEntry.remove();
				}
			}

			wrappedIterator.remove();
		}

	}
}
