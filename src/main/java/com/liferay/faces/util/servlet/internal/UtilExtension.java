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
package com.liferay.faces.util.servlet.internal;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.servlet.ServletContext;

import com.liferay.faces.util.osgi.mojarra.spi.internal.OnDemandBeanManagerKey;


/**
 * @author  Kyle Stiemann
 */
public class UtilExtension implements Extension {

	public void initializeFacesBeanManager(@Observes
		@Initialized(ApplicationScoped.class)
		ServletContext servletContext, BeanManager beanManager) {

		if (servletContext.getAttribute(OnDemandBeanManagerKey.INSTANCE) == null) {
			servletContext.setAttribute(OnDemandBeanManagerKey.INSTANCE, beanManager);
		}
	}
}
