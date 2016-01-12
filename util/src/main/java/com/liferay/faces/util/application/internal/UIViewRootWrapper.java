/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.application.internal;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.FacesWrapper;
import javax.faces.component.ContextCallback;
import javax.faces.component.TransientStateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;


/**
 * @author  Kyle Stiemann
 */
public abstract class UIViewRootWrapper extends UIViewRoot implements FacesWrapper<UIViewRoot> {

	@Override
	public void addClientBehavior(String eventName, ClientBehavior behavior) {
		getWrapped().addClientBehavior(eventName, behavior);
	}

	@Override
	public void addComponentResource(FacesContext context, UIComponent componentResource) {
		getWrapped().addComponentResource(context, componentResource);
	}

	@Override
	public void addComponentResource(FacesContext context, UIComponent componentResource, String target) {
		getWrapped().addComponentResource(context, componentResource, target);
	}

	@Override
	public void addPhaseListener(PhaseListener newPhaseListener) {
		getWrapped().addPhaseListener(newPhaseListener);
	}

	@Override
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		getWrapped().broadcast(event);
	}

	@Override
	public void broadcastEvents(FacesContext context, PhaseId phaseId) {
		getWrapped().broadcastEvents(context, phaseId);
	}

	@Override
	public void clearInitialState() {
		getWrapped().clearInitialState();
	}

	@Override
	public String createUniqueId() {

		// createUniqueId() is called in UIViewRoot's constructor before the wrappedUIViewRoot is initialized, so
		// return null unless wrappedUIViewRoot has been initialized.
		String uniqueId = null;
		UIViewRoot wrappedUIViewRoot = getWrapped();

		if (wrappedUIViewRoot != null) {
			uniqueId = wrappedUIViewRoot.createUniqueId();
		}

		return uniqueId;
	}

	@Override
	public String createUniqueId(FacesContext context, String seed) {
		return getWrapped().createUniqueId(context, seed);
	}

	@Override
	public void decode(FacesContext context) {
		getWrapped().decode(context);
	}

	@Override
	public void encodeAll(FacesContext context) throws IOException {
		getWrapped().encodeAll(context);
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		getWrapped().encodeBegin(context);
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		getWrapped().encodeChildren(context);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		getWrapped().encodeEnd(context);
	}

	@Override
	public UIComponent findComponent(String expr) {
		return getWrapped().findComponent(expr);
	}

	@Override
	public boolean initialStateMarked() {
		return getWrapped().initialStateMarked();
	}

	@Override
	public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback)
		throws FacesException {
		return getWrapped().invokeOnComponent(context, clientId, callback);
	}

	@Override
	public void markInitialState() {
		getWrapped().markInitialState();
	}

	@Override
	public void processApplication(FacesContext context) {
		getWrapped().processApplication(context);
	}

	@Override
	public void processDecodes(FacesContext context) {
		getWrapped().processDecodes(context);
	}

	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
		getWrapped().processEvent(event);
	}

	@Override
	public void processRestoreState(FacesContext context, Object state) {
		getWrapped().processRestoreState(context, state);
	}

	@Override
	public Object processSaveState(FacesContext context) {
		return getWrapped().processSaveState(context);
	}

	@Override
	public void processUpdates(FacesContext context) {
		getWrapped().processUpdates(context);
	}

	@Override
	public void processValidators(FacesContext context) {
		getWrapped().processValidators(context);
	}

	@Override
	public void queueEvent(FacesEvent event) {
		getWrapped().queueEvent(event);
	}

	@Override
	public void removeComponentResource(FacesContext context, UIComponent componentResource) {
		getWrapped().removeComponentResource(context, componentResource);
	}

	@Override
	public void removeComponentResource(FacesContext context, UIComponent componentResource, String target) {
		getWrapped().removeComponentResource(context, componentResource, target);
	}

	@Override
	public void removePhaseListener(PhaseListener toRemove) {
		getWrapped().removePhaseListener(toRemove);
	}

	@Override
	public void resetValues(FacesContext context, Collection<String> clientIds) {
		getWrapped().resetValues(context, clientIds);
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		getWrapped().restoreState(context, state);
	}

	@Override
	public void restoreTransientState(FacesContext context, Object state) {
		getWrapped().restoreTransientState(context, state);
	}

	@Override
	public void restoreViewScopeState(FacesContext context, Object state) {
		getWrapped().restoreViewScopeState(context, state);
	}

	@Override
	public Object saveState(FacesContext context) {
		return getWrapped().saveState(context);
	}

	@Override
	public Object saveTransientState(FacesContext context) {
		return getWrapped().saveTransientState(context);
	}

	@Override
	public void subscribeToEvent(Class<? extends SystemEvent> eventClass,
		ComponentSystemEventListener componentListener) {
		getWrapped().subscribeToEvent(eventClass, componentListener);
	}

	@Override
	public void subscribeToViewEvent(Class<? extends SystemEvent> systemEvent, SystemEventListener listener) {
		getWrapped().subscribeToViewEvent(systemEvent, listener);
	}

	@Override
	public void unsubscribeFromEvent(Class<? extends SystemEvent> eventClass,
		ComponentSystemEventListener componentListener) {
		getWrapped().unsubscribeFromEvent(eventClass, componentListener);
	}

	@Override
	public void unsubscribeFromViewEvent(Class<? extends SystemEvent> systemEvent, SystemEventListener listener) {
		getWrapped().unsubscribeFromViewEvent(systemEvent, listener);
	}

	@Override
	public boolean visitTree(VisitContext context, VisitCallback callback) {
		return getWrapped().visitTree(context, callback);
	}

	@Override
	public MethodExpression getAfterPhaseListener() {
		return getWrapped().getAfterPhaseListener();
	}

	@Override
	public void setAfterPhaseListener(MethodExpression newAfterPhase) {
		getWrapped().setAfterPhaseListener(newAfterPhase);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return getWrapped().getAttributes();
	}

	@Override
	public MethodExpression getBeforePhaseListener() {
		return getWrapped().getBeforePhaseListener();
	}

	@Override
	public void setBeforePhaseListener(MethodExpression newBeforePhase) {
		getWrapped().setBeforePhaseListener(newBeforePhase);
	}

	@Override
	public int getChildCount() {
		return getWrapped().getChildCount();
	}

	@Override
	public List<UIComponent> getChildren() {
		return getWrapped().getChildren();
	}

	@Override
	public Map<String, List<ClientBehavior>> getClientBehaviors() {
		return getWrapped().getClientBehaviors();
	}

	@Override
	public String getClientId() {
		return getWrapped().getClientId();
	}

	@Override
	public String getClientId(FacesContext context) {
		return getWrapped().getClientId(context);
	}

	@Override
	public List<UIComponent> getComponentResources(FacesContext context, String target) {
		return getWrapped().getComponentResources(context, target);
	}

	@Override
	public String getContainerClientId(FacesContext context) {
		return getWrapped().getContainerClientId(context);
	}

	@Override
	public boolean isRendered() {
		return getWrapped().isRendered();
	}

	@Override
	public String getDefaultEventName() {
		return getWrapped().getDefaultEventName();
	}

	@Override
	public Collection<String> getEventNames() {
		return getWrapped().getEventNames();
	}

	@Override
	public UIComponent getFacet(String name) {
		return getWrapped().getFacet(name);
	}

	@Override
	public int getFacetCount() {
		return getWrapped().getFacetCount();
	}

	@Override
	public Map<String, UIComponent> getFacets() {
		return getWrapped().getFacets();
	}

	@Override
	public Iterator<UIComponent> getFacetsAndChildren() {
		return getWrapped().getFacetsAndChildren();
	}

	@Override
	public String getFamily() {
		return getWrapped().getFamily();
	}

	@Override
	public String getId() {
		return getWrapped().getId();
	}

	@Override
	public void setId(String id) {

		// setId() is called in UIViewRoot's constructor before the wrappedUIViewRoot is initialized, so do nothing so
		// do nothing unless wrappedUIViewRoot has been initialized.
		UIViewRoot wrappedUIViewRoot = getWrapped();

		if (wrappedUIViewRoot != null) {
			wrappedUIViewRoot.setId(id);
		}
	}

	@Override
	public void setInView(boolean isInView) {
		getWrapped().setInView(isInView);
	}

	@Override
	public List<SystemEventListener> getListenersForEventClass(Class<? extends SystemEvent> eventClass) {
		return getWrapped().getListenersForEventClass(eventClass);
	}

	@Override
	public Locale getLocale() {
		return getWrapped().getLocale();
	}

	@Override
	public void setLocale(Locale locale) {
		getWrapped().setLocale(locale);
	}

	@Override
	public UIComponent getNamingContainer() {
		return getWrapped().getNamingContainer();
	}

	@Override
	public UIComponent getParent() {
		return getWrapped().getParent();
	}

	@Override
	public void setParent(UIComponent parent) {
		getWrapped().setParent(parent);
	}

	@Override
	public Map<String, Object> getPassThroughAttributes(boolean create) {
		return getWrapped().getPassThroughAttributes(create);
	}

	@Override
	public List<PhaseListener> getPhaseListeners() {
		return getWrapped().getPhaseListeners();
	}

	@Override
	public void setRendered(boolean rendered) {
		getWrapped().setRendered(rendered);
	}

	@Override
	public String getRendererType() {
		return getWrapped().getRendererType();
	}

	@Override
	public void setRendererType(String rendererType) {

		// setRendererType() is called in UIViewRoot's constructor before the wrappedUIViewRoot is initialized, so do
		// nothing unless wrappedUIViewRoot has been initialized.
		UIViewRoot wrappedUIViewRoot = getWrapped();

		if (wrappedUIViewRoot != null) {
			wrappedUIViewRoot.setRendererType(rendererType);
		}
	}

	@Override
	public String getRenderKitId() {
		return getWrapped().getRenderKitId();
	}

	@Override
	public void setRenderKitId(String renderKitId) {
		getWrapped().setRenderKitId(renderKitId);
	}

	@Override
	public boolean getRendersChildren() {
		return getWrapped().getRendersChildren();
	}

	@Override
	public Map<String, String> getResourceBundleMap() {
		return getWrapped().getResourceBundleMap();
	}

	@Override
	public boolean isTransient() {
		return getWrapped().isTransient();
	}

	@Override
	public void setTransient(boolean transientFlag) {
		getWrapped().setTransient(transientFlag);
	}

	@Override
	public TransientStateHelper getTransientStateHelper(boolean create) {
		return getWrapped().getTransientStateHelper(create);
	}

	@Override
	public ValueBinding getValueBinding(String name) {
		return getWrapped().getValueBinding(name);
	}

	@Override
	public void setValueBinding(String name, ValueBinding binding) {
		getWrapped().setValueBinding(name, binding);
	}

	@Override
	public ValueExpression getValueExpression(String name) {
		return getWrapped().getValueExpression(name);
	}

	@Override
	public void setValueExpression(String name, ValueExpression binding) {
		getWrapped().setValueExpression(name, binding);
	}

	@Override
	public String getViewId() {
		return getWrapped().getViewId();
	}

	@Override
	public void setViewId(String viewId) {
		getWrapped().setViewId(viewId);
	}

	@Override
	public List<SystemEventListener> getViewListenersForEventClass(Class<? extends SystemEvent> systemEvent) {
		return getWrapped().getViewListenersForEventClass(systemEvent);
	}

	@Override
	public Map<String, Object> getViewMap() {
		return getWrapped().getViewMap();
	}

	@Override
	public Map<String, Object> getViewMap(boolean create) {
		return getWrapped().getViewMap(create);
	}

	@Override
	public boolean isInView() {
		return getWrapped().isInView();
	}
}
