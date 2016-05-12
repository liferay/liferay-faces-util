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
package com.liferay.faces.util.lifecycle;

import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class DebugPhaseListener implements PhaseListener {

	// serialVersionUID
	private static final long serialVersionUID = 5431973221176870776L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DebugPhaseListener.class);

	public void afterPhase(PhaseEvent phaseEvent) {

		if (logger.isDebugEnabled()) {
			PhaseId phaseId = phaseEvent.getPhaseId();

			String viewId = null;
			UIViewRoot uiViewRoot = phaseEvent.getFacesContext().getViewRoot();

			if (uiViewRoot != null) {
				viewId = uiViewRoot.getViewId();
			}

			logger.debug("AFTER phaseId=[{0}] viewId=[{1}]", phaseId.toString(), viewId);
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {

		if (logger.isDebugEnabled()) {
			PhaseId phaseId = phaseEvent.getPhaseId();

			String viewId = null;
			UIViewRoot uiViewRoot = phaseEvent.getFacesContext().getViewRoot();

			if (uiViewRoot != null) {
				viewId = uiViewRoot.getViewId();
			}

			logger.debug("BEFORE phaseId=[{0}] viewId=[{1}]", phaseId.toString(), viewId);
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
