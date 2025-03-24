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
package com.liferay.faces.util.context.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitContextFactory;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextWrapper;
import javax.faces.event.PhaseId;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * <p>This class is a wrapper around the {@link PartialViewContext}. Its purpose is to provide a way to provide a way
 * for components to handle validation for required fields with "onchange" behavior rather than "onblur" behavior. For
 * example, the ICEfaces <a href="http://res.icesoft.org/docs/v3_latest/ace/tld/ace/textEntry.html">ace:textEntry</a>
 * component only suppors the "onblur" event when used with <a
 * href="http://res.icesoft.org/docs/v3_latest/ace/tld/ace/ajax.html">ace:ajax</a>. In the case of required="true", the
 * JSF PROCESS_VALIDATIONS phase will add a {@link EditableValueHolder#REQUIRED_MESSAGE_ID} {@link FacesMessage} when
 * the user tabs-out of the field. By specifying the following in the WEB-INF/faces-config.xml descriptor, the
 * FacesMessages will be removed which approximates the behavior of the "onchange" event:</p>
 *
 * <pre>&lt;partial-view-context-factory&gt;com.liferay.faces.util.context.PartialViewContextFactoryOnChangeImpl&lt;/partial-view-context-factory&gt;</pre>
 *
 * @author  Neil Griffin
 */
public class PartialViewContextOnChangeImpl extends PartialViewContextWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PartialViewContextOnChangeImpl.class);

	// Private Data Members
	private FacesContext facesContext;
	private PartialViewContext wrappedPartialViewContext;
	private Map<String, Object> valueMap;
	private Map<String, Boolean> validMap;

	public PartialViewContextOnChangeImpl(PartialViewContext partialViewContext, FacesContext facesContext) {
		this.wrappedPartialViewContext = partialViewContext;
		this.facesContext = facesContext;
		this.validMap = new HashMap<String, Boolean>();
		this.valueMap = new HashMap<String, Object>();
	}

	@Override
	public PartialViewContext getWrapped() {
		return wrappedPartialViewContext;
	}

	@Override
	public void processPartial(PhaseId phaseId) {

		// If processing a partial request during the "Apply Request Values" phase of the JSF lifecycle, then register
		// a callback that will save-off the values of the components that are visited in the tree-walk.
		if (phaseId == PhaseId.APPLY_REQUEST_VALUES) {
			VisitContextFactory visitContextFactory = (VisitContextFactory) FactoryFinder.getFactory(
					FactoryFinder.VISIT_CONTEXT_FACTORY);

			Collection<String> renderIds = wrappedPartialViewContext.getExecuteIds();
			EnumSet<VisitHint> visitHints = EnumSet.of(VisitHint.EXECUTE_LIFECYCLE);
			VisitContext visitContext = visitContextFactory.getVisitContext(facesContext, renderIds, visitHints);
			VisitCallback visitCallback = new VisitCallbackApplyRequestValuesImpl(valueMap, validMap);
			facesContext.getViewRoot().visitTree(visitContext, visitCallback);
		}

		// Otherwise, if processing a partial request during the "Render Response" phase of the JSF lifecycle, then
		// register a callback that will remove extraneous FacesMessages for components that are visited in the
		// tree-walk.
		else if (phaseId == PhaseId.RENDER_RESPONSE) {
			VisitContextFactory visitContextFactory = (VisitContextFactory) FactoryFinder.getFactory(
					FactoryFinder.VISIT_CONTEXT_FACTORY);
			Collection<String> renderIds = wrappedPartialViewContext.getExecuteIds();
			EnumSet<VisitHint> visitHints = EnumSet.of(VisitHint.EXECUTE_LIFECYCLE);
			VisitContext visitContext = visitContextFactory.getVisitContext(facesContext, renderIds, visitHints);
			VisitCallback visitCallback = new VisitCallbackRenderResponseImpl(facesContext, valueMap, validMap);
			facesContext.getViewRoot().visitTree(visitContext, visitCallback);
		}

		// Ask the delegation chain to continue partial request processing.
		super.processPartial(phaseId);
	}

	/**
	 * This method is missing from the {@link PartialViewContextWrapper} class so it must be implemented here.
	 */
	@Override
	public void setPartialRequest(boolean isPartialRequest) {
		wrappedPartialViewContext.setPartialRequest(isPartialRequest);
	}

	private static final class VisitCallbackApplyRequestValuesImpl implements VisitCallback {

		private Map<String, Object> valueMap;
		private Map<String, Boolean> validMap;

		public VisitCallbackApplyRequestValuesImpl(Map<String, Object> valueMap, Map<String, Boolean> validMap) {
			this.validMap = validMap;
			this.valueMap = valueMap;
		}

		public VisitResult visit(VisitContext visitContext, UIComponent uiComponent) {

			if (uiComponent instanceof EditableValueHolder) {
				EditableValueHolder editableValueHolder = (EditableValueHolder) uiComponent;
				String clientId = uiComponent.getClientId();
				validMap.put(clientId, editableValueHolder.isValid());
				valueMap.put(clientId, editableValueHolder.getValue());
			}

			// Indicate to the caller that the tree walk should continue and that the specified component's subtree
			// should be processed as well.
			return VisitResult.ACCEPT;
		}

	}

	private static final class VisitCallbackRenderResponseImpl implements VisitCallback {

		private FacesContext facesContext;
		private Map<String, Object> valueMap;
		private Map<String, Boolean> validMap;

		public VisitCallbackRenderResponseImpl(FacesContext facesContext, Map<String, Object> valueMap,
			Map<String, Boolean> validMap) {
			this.facesContext = facesContext;
			this.validMap = validMap;
			this.valueMap = valueMap;
		}

		public VisitResult visit(VisitContext visitContext, UIComponent uiComponent) {

			// If the specified component participates in input validation, then
			if (uiComponent instanceof EditableValueHolder) {

				// Temporarily push the component to the EL stack so that the following call to isRequired() will work
				// with EL expressions.
				EditableValueHolder editableValueHolder = (EditableValueHolder) uiComponent;
				uiComponent.pushComponentToEL(facesContext, uiComponent);

				boolean required = editableValueHolder.isRequired();
				uiComponent.popComponentFromEL(facesContext);

				// If the specified component is required, then
				if (required) {

					String clientId = uiComponent.getClientId();
					Object previousValue = valueMap.get(clientId);
					Object submittedValue = editableValueHolder.getSubmittedValue();
					boolean submittedValueEmpty = ((submittedValue == null) || "".equals(submittedValue));
					boolean previouslyValid = validMap.get(clientId);
					logger.debug(
						"previousValue=[{0}] submittedValue=[{1}] submittedValueEmpty=[{2}] previouslyValid=[{3}]",
						previousValue, submittedValue, submittedValueEmpty, previouslyValid);

					// If the user hasn't provided value yet, then remove any FacesMessages associated with the
					// component. This will prevent "Value is required" type messages from being displayed to the user
					// when they simply tab-out of a field that triggered an "onblur" event.
					if ((previousValue == null) && submittedValueEmpty && previouslyValid) {

						List<FacesMessage> messageList = facesContext.getMessageList(clientId);

						if (messageList != null) {

							List<FacesMessage> facesMessagesForClientId = new ArrayList<FacesMessage>(messageList);

							for (FacesMessage facesMessage : facesMessagesForClientId) {
								Iterator<FacesMessage> allFacesMessagesItr = facesContext.getMessages();

								while (allFacesMessagesItr.hasNext()) {
									FacesMessage curFacesMessage = allFacesMessagesItr.next();

									if (facesMessage.equals(curFacesMessage)) {
										allFacesMessagesItr.remove();
										editableValueHolder.setValid(true);

										if (logger.isDebugEnabled()) {
											String summary = facesMessage.getSummary();
											logger.debug("Removed facesMessage summary=[{0}] for clientId=[{1}]",
												summary, clientId);
										}
									}
								}
							}
						}
					}
				}

			}

			// Indicate to the caller that the tree walk should continue and that the specified component's subtree
			// should be processed as well.
			return VisitResult.ACCEPT;
		}

	}

}
