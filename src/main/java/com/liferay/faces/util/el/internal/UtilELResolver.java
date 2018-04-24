/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
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
import com.liferay.faces.util.product.info.ProductInfo;
import com.liferay.faces.util.product.info.ProductInfoFactory;


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

	// Instance field must be declared volatile in order for the double-check idiom to work (requires JRE 1.5+)
	private volatile ProductMap productMap;
	private volatile ProductInfoMap productInfoMap;

	public UtilELResolver() {
		super(getFeatureDescriptor("browserSniffer", BrowserSniffer.class), getFeatureDescriptor("i18n", String.class),
			getFeatureDescriptor("product", Product.class), getFeatureDescriptor("productInfo", ProductInfo.class));
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
			else if (varName.equals("productInfo")) {

				FacesContext facesContext = FacesContext.getCurrentInstance();
				value = getProductInfoMap(facesContext);
			}
			else if (varName.equals("product")) {

				FacesContext facesContext = FacesContext.getCurrentInstance();
				value = getProductMap(facesContext);
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

	private ProductInfoMap getProductInfoMap(FacesContext facesContext) {

		ProductInfoMap productInfoMap = this.productInfoMap;

		// First check without locking (not yet thread-safe)
		if (productInfoMap == null) {

			synchronized (this) {

				productInfoMap = this.productInfoMap;

				// Second check with locking (thread-safe)
				if (productInfoMap == null) {

					ExternalContext externalContext = facesContext.getExternalContext();
					ProductInfoFactory productInfoFactory = (ProductInfoFactory) FactoryExtensionFinder.getFactory(
							externalContext, ProductInfoFactory.class);
					productInfoMap = this.productInfoMap = new ProductInfoMap(productInfoFactory);
				}
			}
		}

		return productInfoMap;
	}

	private ProductMap getProductMap(FacesContext facesContext) {

		ProductMap productMap = this.productMap;

		// First check without locking (not yet thread-safe)
		if (productMap == null) {

			synchronized (this) {

				productMap = this.productMap;

				// Second check with locking (thread-safe)
				if (productMap == null) {

					ExternalContext externalContext = facesContext.getExternalContext();
					ProductInfoFactory productInfoFactory = (ProductInfoFactory) FactoryExtensionFinder.getFactory(
							externalContext, ProductInfoFactory.class);
					productMap = this.productMap = new ProductMap(productInfoFactory);
				}
			}
		}

		return productMap;
	}
}
