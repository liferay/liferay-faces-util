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
package com.liferay.faces.util.i18n;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.cache.Cache;
import com.liferay.faces.util.cache.CacheFactory;
import com.liferay.faces.util.internal.TCCLUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.osgi.internal.ResourceBundleUtil;


/**
 * This is an abstract class that provides a convenient base implementation for introducing an internationalized {@link
 * ResourceBundle} into the {@link I18n} delegation chain. For the sake of performance, lookups into the ResourceBundle
 * are cached by this class in a synchronized map.
 *
 * @author  Neil Griffin
 */
public abstract class I18nBundleBase extends I18nWrapper implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3785524975078495843L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(I18nBundleBase.class);

	// Private Final Data Members
	private final I18n wrappedI18n;

	public I18nBundleBase(I18n i18n) {

		this.wrappedI18n = i18n;

		// This class is instantiated by the I18nFactory delegation chain during application startup.
		FacesContext startupFacesContext = FacesContext.getCurrentInstance();

		// Store the message cache in the application map (as a Servlet Context attribute).
		if (startupFacesContext != null) {

			ExternalContext externalContext = startupFacesContext.getExternalContext();
			Cache<String, String> messageCache = newConcurrentMessageCache(externalContext);
			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			applicationMap.put(getClass().getName(), messageCache);
		}
		else {
			logger.error("Unable to store the resource bundle cache in the application map");
		}
	}

	public abstract String getBundleKey();

	@Override
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId) {
		return I18nUtil.getFacesMessage(this, facesContext, locale, severity, messageId);
	}

	@Override
	public FacesMessage getFacesMessage(FacesContext facesContext, Locale locale, FacesMessage.Severity severity,
		String messageId, Object... arguments) {
		return I18nUtil.getFacesMessage(this, facesContext, locale, severity, messageId, arguments);
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId) {

		String message = null;
		String key = messageId;

		if (locale != null) {
			key = locale.toString() + messageId;
		}

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> applicationMap = externalContext.getApplicationMap();
		Class<? extends I18nBundleBase> clazz = getClass();
		Cache<String, String> messageCache = (Cache<String, String>) applicationMap.get(clazz.getName());

		if ((messageCache != null) && messageCache.containsKey(key)) {

			message = messageCache.getValue(key);

			if ("".equals(message)) {
				message = null;
			}
		}
		else {

			ResourceBundle resourceBundle = null;

			try {
				String bundleKey = getBundleKey();

				if (locale == null) {
					resourceBundle = ResourceBundleUtil.getUTF8ResourceBundleInOSGiEnvironment(bundleKey,
							Locale.getDefault(), clazz);
				}
				else {
					resourceBundle = ResourceBundleUtil.getUTF8ResourceBundleInOSGiEnvironment(bundleKey, locale,
							clazz);
				}
			}
			catch (MissingResourceException e) {
				logger.error(e);
			}

			if (resourceBundle != null) {

				try {

					message = resourceBundle.getString(messageId);

					if (messageCache != null) {
						message = messageCache.putValueIfAbsent(key, message);
					}
				}
				catch (MissingResourceException e) {

					if (messageCache != null) {
						messageCache.putValueIfAbsent(key, "");
					}
				}
			}
		}

		if (message == null) {
			message = super.getMessage(facesContext, locale, messageId);
		}

		return message;
	}

	@Override
	public String getMessage(FacesContext facesContext, Locale locale, String messageId, Object... arguments) {

		String message = getMessage(facesContext, locale, messageId);

		if (message != null) {
			message = MessageFormat.format(message, arguments);
		}

		return message;
	}

	@Override
	public I18n getWrapped() {
		return wrappedI18n;
	}

	/**
	 * Returns a new message cache to be used by {@link I18nBundleBase}. The default implementation returns a Cache
	 * instance obtained from {@link CacheFactory#getConcurrentCacheInstance(javax.faces.context.ExternalContext, int)}
	 * (passing 16 as the initial cache capacity). The cache will be stored in the application map. This method is
	 * called from the constructor of I18nBundleBase, so this method must not cause side effects and should not expect
	 * I18nBundleBase (or its subclass) to be fully initialized.
	 *
	 * @param  externalContext  The external context associated with the current {@link FacesContext}.
	 */
	protected Cache<String, String> newConcurrentMessageCache(ExternalContext externalContext) {
		return CacheFactory.getConcurrentCacheInstance(externalContext, 16);
	}
}
