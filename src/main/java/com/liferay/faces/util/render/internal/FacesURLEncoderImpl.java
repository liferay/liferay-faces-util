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
package com.liferay.faces.util.render.internal;

import java.io.Serializable;
import java.net.URLEncoder;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.FacesURLEncoder;

/**
 * @author Neil Griffin
 */
public class FacesURLEncoderImpl implements FacesURLEncoder, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 5849771243243889350L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesURLEncoderImpl.class);

	@Override
	public String encode(String url, String encoding) {

		String encodedURL = url;

		if (url != null) {

			try {
				encodedURL = URLEncoder.encode(url, encoding);
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return encodedURL;
	}
}
