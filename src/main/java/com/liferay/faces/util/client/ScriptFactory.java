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
package com.liferay.faces.util.client;

import javax.faces.FacesWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.faces.util.factory.FactoryExtensionFinder;

/**
 * A factory for creating a {@link Script} which can be rendered on the client via
 * {@link com.liferay.faces.util.context.FacesContextHelperUtil#addScript(javax.faces.context.FacesContext, com.liferay.faces.util.client.Script)}.
 *
 * @author Kyle Stiemann
 */
@ProviderType
public abstract class ScriptFactory implements FacesWrapper<ScriptFactory> {

	/**
	 * @deprecated Call {@link #getScriptInstance(ExternalContext, String)} instead.
	 *
	 *             <p>
	 *             Returns a new instance of {@link Script} from the {@link ScriptFactory} found by the
	 *             {@link FactoryExtensionFinder}. The returned instance is guaranteed to be
	 *             {@link java.io.Serializable} but not guaranteed to be thread-safe.
	 *             </p>
	 *
	 * @param sourceCode The source code of the script.
	 */
	@Deprecated
	public static Script getScriptInstance(String sourceCode) {
		return getScriptInstance(FacesContext.getCurrentInstance().getExternalContext(), sourceCode);
	}

	/**
	 * Returns a new instance of {@link Script} from the {@link ScriptFactory} found by the
	 * {@link FactoryExtensionFinder}. The returned instance is guaranteed to be {@link java.io.Serializable} but not
	 * guaranteed to be thread-safe.
	 *
	 * @param externalContext The external context associated with the current faces context. It is needed in order for
	 *                        the {@link FactoryExtensionFinder} to be able to find the factory.
	 * @param sourceCode      The source code of the script.
	 *
	 * @since 3.1
	 * @since 2.1
	 * @since 1.1
	 */
	public static Script getScriptInstance(ExternalContext externalContext, String sourceCode) {

		ScriptFactory scriptFactory =
			(ScriptFactory) FactoryExtensionFinder.getFactory(externalContext, ScriptFactory.class);

		return scriptFactory.getScript(sourceCode);
	}

	/**
	 * @deprecated Call {@link #getScriptInstance(ExternalContext, String, String[], Script.ModulesType)} instead.
	 *
	 *             <p>
	 *             Returns a new instance of {@link Script} from the {@link ScriptFactory} found by the
	 *             {@link FactoryExtensionFinder}. The returned instance is guaranteed to be
	 *             {@link java.io.Serializable} but not guaranteed to be thread-safe.
	 *             </p>
	 *
	 * @param sourceCode  The source code of the Script.
	 * @param modules     The modules which the Script depends on.
	 * @param modulesType The {@link Script.ModulesType} of the Script.
	 */
	@Deprecated
	public static Script getScriptInstance(String sourceCode, String[] modules, Script.ModulesType modulesType) {
		return getScriptInstance(FacesContext.getCurrentInstance().getExternalContext(), sourceCode, modules,
			modulesType);
	}

	/**
	 * Returns a new instance of {@link Script} from the {@link ScriptFactory} found by the
	 * {@link FactoryExtensionFinder}. The returned instance is guaranteed to be {@link java.io.Serializable} but not
	 * guaranteed to be thread-safe.
	 *
	 * @param externalContext The external context associated with the current faces context. It is needed in order for
	 *                        the {@link FactoryExtensionFinder} to be able to find the factory.
	 * @param sourceCode      The source code of the Script.
	 * @param modules         The modules which the Script depends on.
	 * @param modulesType     The {@link Script.ModulesType} of the Script.
	 *
	 * @since 3.1
	 * @since 2.1
	 * @since 1.1
	 */
	public static Script getScriptInstance(ExternalContext externalContext, String sourceCode, String[] modules,
		Script.ModulesType modulesType) {

		ScriptFactory scriptFactory =
			(ScriptFactory) FactoryExtensionFinder.getFactory(externalContext, ScriptFactory.class);

		return scriptFactory.getScript(sourceCode, modules, modulesType);
	}

	/**
	 * Returns a new instance of {@link Script} with the specified source code. The returned instance is guaranteed to
	 * be {@link java.io.Serializable} but not guaranteed to be thread-safe. The Script can be rendered on the client
	 * via
	 * {@link com.liferay.faces.util.context.FacesContextHelperUtil#addScript(javax.faces.context.FacesContext, com.liferay.faces.util.client.Script)}.
	 * As a convenience, it is possible to call
	 * {@link com.liferay.faces.util.context.FacesContextHelperUtil#addScript(javax.faces.context.FacesContext, com.liferay.faces.util.client.Script)}
	 * instead since it will create the Script and add it to the list of Scripts which will be rendered to the response.
	 *
	 * @param sourceCode The source code of the Script.
	 */
	public abstract Script getScript(String sourceCode);

	/**
	 * Returns a new instance of {@link Script} with the specified source code, modules, and type. The returned instance
	 * is guaranteed to be {@link java.io.Serializable} but not guaranteed to be thread-safe. The Script can be rendered
	 * on the client via
	 * {@link com.liferay.faces.util.context.FacesContextHelperUtil#addScript(javax.faces.context.FacesContext, com.liferay.faces.util.client.Script)}.
	 *
	 * @param sourceCode  The source code of the Script.
	 * @param modules     The modules which the Script depends on.
	 * @param modulesType The {@link Script.ModulesType} of the Script.
	 */
	public abstract Script getScript(String sourceCode, String[] modules, Script.ModulesType modulesType);

	/**
	 * Returns the wrapped factory instance if this factory decorates another. Otherwise, this method returns null.
	 */
	@Override
	public abstract ScriptFactory getWrapped();
}
