/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
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

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ProviderType;


/**
 * @author  Neil Griffin
 */
@ProviderType
public class ComponentUtil {

	// Private Constants
	private static final String DOUBLE_BACKSLASH_COLON = "\\\\\\\\:";
	private static final String REGEX_COLON = "[:]";

	public static String concatCssClasses(String... classNames) {

		StringBuilder cssClassBuilder = new StringBuilder();
		boolean first = true;

		for (String className : classNames) {

			if (className != null) {

				if (!first) {
					cssClassBuilder.append(" ");
				}

				cssClassBuilder.append(className);
				first = false;
			}
		}

		String allClasses = cssClassBuilder.toString();

		if (allClasses.length() == 0) {
			allClasses = null;
		}

		return allClasses;
	}

	public static String escapeClientId(String clientId) {
		String escapedClientId = clientId;

		if (escapedClientId != null) {

			// JSF clientId values contain colons, which must be preceeded by double backslashes in order to have them
			// work with JavaScript functions like AUI.one(String). http://yuilibrary.com/projects/yui3/ticket/2528057
			escapedClientId = escapedClientId.replaceAll(REGEX_COLON, DOUBLE_BACKSLASH_COLON);
		}

		return escapedClientId;
	}

	public static String findClientId(String expression) {
		return findClientId(FacesContext.getCurrentInstance(), expression);
	}

	public static String findClientId(FacesContext facesContext, String expression) {
		String clientId = null;
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		UIComponent uiComponent = uiViewRoot.findComponent(expression);

		if (uiComponent == null) {
			uiComponent = matchComponentInHierarchy(facesContext, uiViewRoot, expression);
		}

		if (uiComponent != null) {
			clientId = uiComponent.getClientId(facesContext);
		}

		return clientId;
	}

	public static String getComponentLabel(UIComponent uiComponent) {

		String componentLabel = null;

		if (uiComponent != null) {

			componentLabel = getParentFieldLabelValue(uiComponent);

			if (componentLabel == null) {
				componentLabel = getSiblingLabelValue((uiComponent));
			}
		}

		return componentLabel;
	}

	public static UIComponent matchComponentInHierarchy(FacesContext facesContext, UIComponent parent,
		String partialClientId) {
		UIComponent uiComponent = null;

		if (parent != null) {

			String parentClientId = parent.getClientId(facesContext);

			if ((parentClientId != null) && (parentClientId.indexOf(partialClientId) >= 0)) {
				uiComponent = parent;
			}
			else {
				Iterator<UIComponent> itr = parent.getFacetsAndChildren();

				if (itr != null) {

					while (itr.hasNext()) {
						UIComponent child = itr.next();
						uiComponent = matchComponentInHierarchy(facesContext, child, partialClientId);

						if (uiComponent != null) {
							break;
						}
					}
				}
			}
		}

		return uiComponent;
	}

	private static String getParentFieldLabelValue(UIComponent uiComponent) {

		String parentFieldLabel = null;

		if (uiComponent != null) {
			UIComponent parent = uiComponent.getParent();

			if (parent != null) {

				Method method = null;

				try {
					method = parent.getClass().getMethod("getLabel", (Class<?>[]) null);
				}
				catch (NoSuchMethodException e) {
					// ignore
				}

				if (method != null) {

					try {
						parentFieldLabel = (String) method.invoke(parent, (Object[]) null);
					}
					catch (Exception e) {
						// ignore
					}
				}
				else {
					parentFieldLabel = getParentFieldLabelValue(parent);
				}
			}
		}

		return parentFieldLabel;
	}

	private static String getSiblingLabelValue(UIComponent uiComponent) {

		String siblingLabelValue = null;

		if (uiComponent != null) {

			UIComponent parent = uiComponent.getParent();

			if (parent != null) {

				List<UIComponent> children = parent.getChildren();

				if (children != null) {

					for (UIComponent child : children) {

						if (child instanceof HtmlOutputLabel) {
							HtmlOutputLabel htmlOutputLabel = (HtmlOutputLabel) child;

							if (uiComponent.getId().equals(htmlOutputLabel.getFor())) {
								Object labelValue = htmlOutputLabel.getValue();

								if (labelValue != null) {
									siblingLabelValue = labelValue.toString();
								}
							}
						}
					}
				}
			}
		}

		return siblingLabelValue;
	}
}
