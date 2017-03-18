/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.render.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.render.Renderer;
import javax.faces.render.RendererWrapper;

import com.liferay.faces.util.application.ResourceVerifier;
import com.liferay.faces.util.application.ResourceVerifierFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class wraps resources and uses {@link ResourceVerifier#isDependencySatisfied(FacesContext, UIComponent)} to
 * determine if the resources should be rendered.
 *
 * @author  Kyle Stiemann
 */
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public class ResourceRendererUtilImpl extends RendererWrapper implements ComponentSystemEventListener, StateHolder {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceRendererUtilImpl.class);

	// Private Members
	private boolean transientFlag;
	private Renderer wrappedRenderer;

	/**
	 * This zero-arg constructor is required by the {@link javax.faces.component.StateHolderSaver} class during the
	 * RESTORE_VIEW phase of the JSF lifecycle. The reason why this class is involved in restoring state is because the
	 * {@link javax.faces.component.UIComponent.ComponentSystemEventListenerAdapter} implements {@link
	 * javax.faces.component.StateHolder} and will attempt to restore the state of any class in the restored view that
	 * implements {@link ComponentSystemEventListener}. It does this first by instantiating the class with a zero-arg
	 * constructor, and then calls the {@link #restoreState(FacesContext, Object)} method.
	 */
	public ResourceRendererUtilImpl() {

		// Defer initialization of wrappedRenderer until restoreState(FacesContext, Object) is called.
	}

	public ResourceRendererUtilImpl(Renderer wrappedRenderer) {
		this.wrappedRenderer = wrappedRenderer;
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		ResourceVerifier resourceVerifier = ResourceVerifierFactory.getResourceVerifierInstance(externalContext);
		if (resourceVerifier.isDependencySatisfied(facesContext, uiComponent)) {

			if (logger.isDebugEnabled()) {

				Map<String, Object> componentResourceAttributes = uiComponent.getAttributes();

				logger.debug(
					"Resource dependency already satisfied: name=[{0}] library=[{1}] rendererType=[{2}] value=[{3}] className=[{4}]",
					componentResourceAttributes.get("name"), componentResourceAttributes.get("library"),
					uiComponent.getRendererType(), getComponentValue(uiComponent), uiComponent.getClass().getName());
			}
		}
		else {
			super.encodeBegin(facesContext, uiComponent);
		}
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		ResourceVerifier resourceVerifier = ResourceVerifierFactory.getResourceVerifierInstance(externalContext);
		if (resourceVerifier.isDependencySatisfied(facesContext, uiComponent)) {

			if (logger.isDebugEnabled()) {

				Map<String, Object> componentResourceAttributes = uiComponent.getAttributes();

				logger.debug(
					"Resource dependency already satisfied: name=[{0}] library=[{1}] rendererType=[{2}] value=[{3}] className=[{4}]",
					componentResourceAttributes.get("name"), componentResourceAttributes.get("library"),
					uiComponent.getRendererType(), getComponentValue(uiComponent), uiComponent.getClass().getName());
			}
		}
		else {
			super.encodeChildren(facesContext, uiComponent);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		ResourceVerifier resourceVerifier = ResourceVerifierFactory.getResourceVerifierInstance(externalContext);
		if (resourceVerifier.isDependencySatisfied(facesContext, uiComponent)) {

			if (logger.isDebugEnabled()) {

				Map<String, Object> componentResourceAttributes = uiComponent.getAttributes();

				logger.debug(
					"Resource dependency already satisfied: name=[{0}] library=[{1}] rendererType=[{2}] value=[{3}] className=[{4}]",
					componentResourceAttributes.get("name"), componentResourceAttributes.get("library"),
					uiComponent.getRendererType(), getComponentValue(uiComponent), uiComponent.getClass().getName());
			}
		}
		else {
			super.encodeEnd(facesContext, uiComponent);
		}
	}

	@Override
	public Renderer getWrapped() {
		return wrappedRenderer;
	}

	@Override
	public boolean isTransient() {
		return transientFlag;
	}

	/**
	 * Since the Mojarra {@link com.sun.faces.renderkit.html_basic.ScriptStyleBaseRenderer} class implements {@link
	 * ComponentSystemEventListener}, this class must implement that interface too, since this is a wrapper type of
	 * class. Mojarra uses this method to intercept {@link PostAddToViewEvent} in order to add script and link resources
	 * to the head (if the target attribute has a value of "head").
	 */
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {

		// If the wrapped renderer has the ability to listen to component system events, then invoke the event
		// processing on the wrapped renderer. This is necessary when wrapping the Mojarra ScriptRenderer or
		// StylesheetRenderer because they extend ScriptStyleBaseRenderer which attempts to add the component
		// associated with the specified event as a resource on the view root.
		if (wrappedRenderer instanceof ComponentSystemEventListener) {

			ComponentSystemEventListener wrappedListener = (ComponentSystemEventListener) wrappedRenderer;
			wrappedListener.processEvent(event);
		}
		else {
			logger.debug("Wrapped renderer=[{0}] does not implement ComponentSystemEventListener", wrappedRenderer);
		}
	}

	@Override
	public void restoreState(FacesContext facesContext, Object state) {

		if (wrappedRenderer == null) {

			try {
				String wrappedRendererClassName = (String) state;
				Class<?> wrappedRendererClass = Class.forName(wrappedRendererClassName);
				wrappedRenderer = (Renderer) wrappedRendererClass.newInstance();
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	@Override
	public Object saveState(FacesContext facesContext) {
		return wrappedRenderer.getClass().getName();
	}

	@Override
	public void setTransient(boolean transientFlag) {
		this.transientFlag = transientFlag;
	}

	private String getComponentValue(UIComponent componentResource) {

		String componentResourceValue = null;

		if (componentResource instanceof ValueHolder) {
			ValueHolder valueHolder = (ValueHolder) componentResource;
			Object valueAsObject = valueHolder.getValue();

			if (valueAsObject != null) {
				componentResourceValue = valueAsObject.toString();
			}
		}

		return componentResourceValue;
	}
}
