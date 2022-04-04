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
package com.liferay.faces.util.el.internal;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotWritableException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.el.ELResolverBase;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductFactory;


/**
 * @author  Neil Griffin
 */
public class UtilELResolver extends ELResolverBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UtilELResolver.class);

	// serialVersionUID
	private static final long serialVersionUID = 4993137243887595902L;

	// Private Data Members
	private final I18nMap i18nMap = new I18nMap();

	public UtilELResolver() {
		super(newFeatureDescriptor("browserSniffer", BrowserSniffer.class), newFeatureDescriptor("i18n", String.class),
			newFeatureDescriptor("product", Product.class));
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext elContext, Object base) {
		return null;
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

		if ((property != null) && (property instanceof String)) {
			String propertyAsString = (String) property;

			if (propertyAsString.equals("i18n")) {
				throw new PropertyNotWritableException(propertyAsString);
			}
			else if (propertyAsString.equals("browserSniffer")) {
				throw new PropertyNotWritableException(propertyAsString);
			}
		}
	}

	@Override
	protected Object resolveProperty(ELContext elContext, Object base, String property) {
		return null;
	}

	@Override
	protected Object resolveVariable(ELContext elContext, String varName) {

		Object value = null;

		try {

			if (varName.equals("i18n")) {
				value = i18nMap;
			}
			else if (varName.equals("browserSniffer")) {

				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				value = BrowserSnifferFactory.getBrowserSnifferInstance(externalContext);
			}
			else if (varName.equals("product")) {

				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				ProductFactory productFactory = (ProductFactory) FactoryExtensionFinder.getFactory(externalContext,
						ProductFactory.class);

				value = new ProductMap(productFactory);
			}
		}
		catch (Exception e) {
			throw new ELException("Failed to resolve variable [" + varName + "]", e);
		}

		if (value == null) {
			logger.debug("Resolved variable [{0}] value=[null]", varName);
		}
		else {
			logger.debug("Unable to resolve variable [{0}] value=[{1}]", varName, value);
		}

		return value;
	}
}
