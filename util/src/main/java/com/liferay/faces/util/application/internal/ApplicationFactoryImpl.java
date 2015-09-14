/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import java.util.EventObject;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

import com.liferay.faces.util.event.internal.ApplicationStartupListener;
import com.liferay.faces.util.helper.Wrapper;


/**
 * This class is registered in META-INF/faces-config.xml as an application-factory. When it is initialized during
 * startup, it sends an event to the {@link ApplicationStartupListener} in order to trigger classpath scanning of
 * application configuration files.
 *
 * @author  Neil Griffin
 */
public class ApplicationFactoryImpl extends ApplicationFactory implements Wrapper<ApplicationFactory> {

	// Private Data Members
	private ApplicationFactory wrappedFactory;

	public ApplicationFactoryImpl(ApplicationFactory applicationFactory) {

		wrappedFactory = applicationFactory;

		ApplicationStartupListener applicationStartupListener = new ApplicationStartupListener();
		EventObject systemEvent = new PostConstructApplicationFactoryEvent(getApplication());
		applicationStartupListener.processSystemEvent(systemEvent);
	}

	@Override
	public Application getApplication() {
		return wrappedFactory.getApplication();
	}

	@Override
	public void setApplication(Application application) {
		wrappedFactory.setApplication(application);
	}

	@Override
	public ApplicationFactory getWrapped() {
		return wrappedFactory;
	}

	protected class PostConstructApplicationFactoryEvent extends EventObject {

		// serialVersionUID
		private static final long serialVersionUID = 8843150652809856957L;

		public PostConstructApplicationFactoryEvent(Application application) {
			super(application);
		}
	}
}
