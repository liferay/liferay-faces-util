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

import java.util.Map;

import javax.el.ELContextListener;
import javax.el.ExpressionFactory;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.Application;
import javax.faces.application.ApplicationWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PostRestoreStateEvent;
import javax.faces.event.SystemEvent;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.sun.faces.RIConstants;


/**
 * This class provides the ability for Mojarra to integrate with the OSGi CDI Integration feature of Liferay Portal. It
 * does this by by adding an {@link javax.el.ELResolver} that can resolve CDI beans found in JSP EL Expressions, and
 * also enables the Mojarra {@link com.sun.faces.util.getCdiBeanManager(FacesContext)} method to find the current CDI
 * bean manager at startup.
 *
 * @author  Neil Griffin
 */
public class ApplicationOSGiCDIImpl extends ApplicationWrapper {

	// Private Constants
	private static String WELD_EL_CONTEXT_LISTENER = "org.jboss.weld.module.web.el.WeldELContextListener";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicationOSGiCDIImpl.class);

	// Private Data Members
	private ExpressionFactory expressionFactory;
	private Application wrappedApplication;

	public ApplicationOSGiCDIImpl(Application wrappedApplication) {
		this.wrappedApplication = wrappedApplication;

		CDI<Object> currentCDI = CDI.current();

		if (currentCDI != null) {

			BeanManager beanManager = currentCDI.getBeanManager();

			// If the bean manager is not present, then that likely means we're running in Liferay and relying on
			// CDI+OSGi integration. In this case, create a temporary "startup" bean manager that will satisfy the
			// startup processing of Mojarra.
			if (beanManager == null) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				Map<String, Object> applicationMap = externalContext.getApplicationMap();

				if (!applicationMap.containsKey(RIConstants.CDI_BEAN_MANAGER)) {
					applicationMap.put(RIConstants.CDI_BEAN_MANAGER,
						new BeanManagerStartupImpl(new BeanManagerELResolver(),
							(applicationMap.get(FacesConfig.class.getName()) != null)));
				}
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

		if (PostRestoreStateEvent.class.equals(systemEventClass)) {

			Map<Object, Object> attributes = facesContext.getAttributes();

			// If Mojarra is not aware of the CDI bean manager, then that likely means we're running in Liferay and
			// relying on CDI+OSGi integration. In this case, help Mojarra become aware of the CDI bean manager.
			if (!attributes.containsKey(RIConstants.CDI_BEAN_MANAGER)) {

				CDI<Object> current = CDI.current();

				if (current != null) {
					attributes.put(RIConstants.CDI_BEAN_MANAGER, current.getBeanManager());
				}
			}
		}

		super.publishEvent(facesContext, systemEventClass, source);
	}

	@Override
	public void publishEvent(FacesContext facesContext, Class<? extends SystemEvent> systemEventClass,
		Class<?> sourceBaseType, Object source) {

		super.publishEvent(facesContext, systemEventClass, sourceBaseType, source);

		if (PostConstructApplicationEvent.class.equals(systemEventClass)) {

			CDI<Object> currentCDI = CDI.current();

			if (currentCDI != null) {

				BeanManager beanManager = currentCDI.getBeanManager();

				// If the bean manager is not present, then that likely means we're running in Liferay and relying on
				// CDI+OSGi integration. In this case, remove the temporary "startup" bean manager that was introduced
				// at the time of construction.
				if (beanManager == null) {
					ExternalContext externalContext = facesContext.getExternalContext();
					Map<String, Object> applicationMap = externalContext.getApplicationMap();

					if (applicationMap.containsKey(RIConstants.CDI_BEAN_MANAGER)) {
						applicationMap.remove(RIConstants.CDI_BEAN_MANAGER);
					}

					Map<Object, Object> attributes = facesContext.getAttributes();

					if (attributes.containsKey(RIConstants.CDI_BEAN_MANAGER)) {
						attributes.remove(RIConstants.CDI_BEAN_MANAGER);
					}
				}
			}

			// If the WeldELContextListener wasn't added automatically, then add it.
			boolean found = false;
			ELContextListener[] elContextListeners = wrappedApplication.getELContextListeners();

			if (elContextListeners != null) {

				for (ELContextListener elContextListener : elContextListeners) {

					if (WELD_EL_CONTEXT_LISTENER.equals(elContextListener.getClass().getName())) {
						found = true;

						break;
					}
				}
			}

			if (!found) {

				try {
					Class<?> listenerClass = Class.forName(WELD_EL_CONTEXT_LISTENER);
					wrappedApplication.addELContextListener((ELContextListener) listenerClass.newInstance());
				}
				catch (Exception e) {
					logger.error(e);
				}
			}
		}
	}
}
