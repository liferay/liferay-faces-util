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
package com.liferay.faces.util.logging;

import org.junit.Assert;
import org.junit.Test;

import com.liferay.faces.util.factory.util.BlockServiceLoaderUtil;
import com.liferay.faces.util.logging.internal.LoggerFactoryImpl;


/**
 * @author  Kyle Stiemann
 */
public class LoggerTest {

	@Test
	public void loadLoggerFactoryWhenServicesDirectoryInaccessibleFACES_2966() {

		// FACES-2966 Netbeans auto completion fails for Liferay Faces components
		Logger logger = BlockServiceLoaderUtil.<Logger>getFactoryOutputWithBlockedServiceLoader(LoggerTest.class,
				LoggerFactory.class, LoggerFactoryImpl.class);
		Assert.assertNotNull(logger);
		Assert.assertTrue(logger.isErrorEnabled());
	}
}
