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
package com.liferay.faces.util.osgi.internal;

import java.util.Map;

import javax.el.ELContextListener;
import javax.el.ExpressionFactory;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.faces.application.Application;
import javax.faces.application.ApplicationWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.SystemEvent;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides the ability for Mojarra to integrate with the OSGI CDI Integration feature of Liferay Portal. It
 * does this by by adding an {@link javax.el.ELResolver} that can resolve CDI beans found in JSP EL Expressions, and
 * also enables the Mojarra {@link com.sun.faces.util.getCdiBeanManager(FacesContext)} method to find the current CDI
 * bean manager.
 *
 * @author  Neil Griffin
 */
public class ApplicationOSGiCDIImpl extends ApplicationWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicationOSGiCDIImpl.class);

	// Private Data Members
	private ExpressionFactory expressionFactory;
	private Application wrappedApplication;

	public ApplicationOSGiCDIImpl(Application wrappedApplication) {
		this.wrappedApplication = wrappedApplication;

		CDI<Object> currentCDI = CDI.current();

		if (currentCDI != null) {

			wrappedApplication.addELResolver(new BeanManagerELResolver());

			try {
				Class<?> listenerClass = Class.forName("org.jboss.weld.module.web.el.WeldELContextListener");
				wrappedApplication.addELContextListener((ELContextListener) listenerClass.newInstance());
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	@Override
	public ExpressionFactory getExpressionFactory() {

		if (expressionFactory == null) {
			expressionFactory = new BeanManagerExpressionFactory(wrappedApplication.getExpressionFactory());
		}

		return expressionFactory;
	}

	@Override
	public Application getWrapped() {
		return wrappedApplication;
	}

	@Override
	public void publishEvent(FacesContext facesContext, Class<? extends SystemEvent> systemEventClass, Object source) {

		initBeanManagerApplicationScopedBean(facesContext.getExternalContext());

		super.publishEvent(facesContext, systemEventClass, source);
	}

	@Override
	public void publishEvent(FacesContext facesContext, Class<? extends SystemEvent> systemEventClass,
		Class<?> sourceBaseType, Object source) {

		initBeanManagerApplicationScopedBean(facesContext.getExternalContext());

		super.publishEvent(facesContext, systemEventClass, sourceBaseType, source);
	}

	private void initBeanManagerApplicationScopedBean(ExternalContext externalContext) {

		Map<String, Object> applicationMap = externalContext.getApplicationMap();

		if (!applicationMap.containsKey("org.jboss.weld.environment.servlet.javax.enterprise.inject.spi.BeanManager")) {

			// Enable the Mojarra com.sun.faces.util.getCdiBeanManager(FacesContext) method to find the current CDI bean
			// manager.
			CDI<Object> current = CDI.current();

			if (current != null) {

				try {
					BeanManager beanManager = current.getBeanManager();

					if (beanManager != null) {
						applicationMap.put("org.jboss.weld.environment.servlet.javax.enterprise.inject.spi.BeanManager",
							beanManager);
					}
				}
				catch (IllegalStateException e) {
					logger.error(e);
				}
			}
		}
	}

}
