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
package com.liferay.faces.util.product.info.internal;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import com.liferay.faces.util.product.info.ProductInfo;
import com.liferay.faces.util.product.info.ProductInfoFactory;


/**
 * @author  Kyle Stiemann
 */
public class ProductInfoFactoryImpl extends ProductInfoFactory {

	// Private Final Data Members
	private final Map<ProductInfo.Name, ProductInfo> productInfoMap;

	public ProductInfoFactoryImpl() {

		Map<ProductInfo.Name, ProductInfo> productInfoMap = new EnumMap<ProductInfo.Name, ProductInfo>(
				ProductInfo.Name.class);
		productInfoMap.put(ProductInfo.Name.ANGULARBEANS, new ProductInfoAngularBeansImpl());
		productInfoMap.put(ProductInfo.Name.ANGULARFACES, new ProductInfoAngularFacesImpl());
		productInfoMap.put(ProductInfo.Name.BOOTSFACES, new ProductInfoBootsFacesImpl());
		productInfoMap.put(ProductInfo.Name.BUTTERFACES, new ProductInfoButterFacesImpl());

		ProductInfo cdiApi = new ProductInfoCDIApiImpl();
		productInfoMap.put(ProductInfo.Name.CDI_API, cdiApi);
		productInfoMap.put(ProductInfo.Name.CLOSURE_TEMPLATES, new ProductInfoClosureTemplatesImpl());
		productInfoMap.put(ProductInfo.Name.DELTASPIKE, new ProductInfoDeltaSpikeImpl());

		ProductInfo glassfish = new ProductInfoGlassfishImpl();
		productInfoMap.put(ProductInfo.Name.GLASSFISH, glassfish);
		productInfoMap.put(ProductInfo.Name.HIGHFACES, new ProductInfoHighFacesImpl());
		productInfoMap.put(ProductInfo.Name.ICEFACES, new ProductInfoICEfacesImpl());

		ProductInfo jetty = new ProductInfoJettyImpl();
		productInfoMap.put(ProductInfo.Name.JETTY, jetty);
		productInfoMap.put(ProductInfo.Name.LIFERAY_FACES_ALLOY, new ProductInfoLiferayFacesAlloyImpl());
		productInfoMap.put(ProductInfo.Name.LIFERAY_FACES_BRIDGE, new ProductInfoLiferayFacesBridgeImpl());
		productInfoMap.put(ProductInfo.Name.LIFERAY_FACES_BRIDGE_EXT, new ProductInfoLiferayFacesBridgeExtImpl());
		productInfoMap.put(ProductInfo.Name.LIFERAY_FACES_CLAY, new ProductInfoLiferayFacesClayImpl());
		productInfoMap.put(ProductInfo.Name.LIFERAY_FACES_PORTAL, new ProductInfoLiferayFacesPortalImpl());
		productInfoMap.put(ProductInfo.Name.LIFERAY_FACES_SHOWCASE, new ProductInfoLiferayFacesShowcaseImpl());
		productInfoMap.put(ProductInfo.Name.LIFERAY_FACES_UTIL, new ProductInfoLiferayFacesUtilImpl());

		ProductInfo liferayPortal = new ProductInfoLiferayPortalImpl();
		productInfoMap.put(ProductInfo.Name.LIFERAY_PORTAL, liferayPortal);

		ProductInfo mojarra = new ProductInfoMojarraImpl();
		productInfoMap.put(ProductInfo.Name.MOJARRA, mojarra);

		ProductInfo myFaces = new ProductInfoMyfacesImpl();
		productInfoMap.put(ProductInfo.Name.MYFACES, myFaces);

		ProductInfo jsfApi = new ProductInfoJSFApiImpl(mojarra, myFaces);
		productInfoMap.put(ProductInfo.Name.JSF_API, jsfApi);
		productInfoMap.put(ProductInfo.Name.JSF, new ProductInfoSpecImpl("JSF", mojarra, myFaces, jsfApi));
		productInfoMap.put(ProductInfo.Name.OMNIFACES, new ProductInfoOmniFacesImpl());

		ProductInfo openWebBeans = new ProductInfoOpenWebBeansImpl();
		productInfoMap.put(ProductInfo.Name.OPEN_WEB_BEANS, openWebBeans);

		ProductInfo pluto = new ProductInfoPlutoImpl();
		productInfoMap.put(ProductInfo.Name.PLUTO, pluto);

		ProductInfo portletApi = new ProductInfoPortletApiImpl(liferayPortal, pluto);
		productInfoMap.put(ProductInfo.Name.PORTLET_API, portletApi);
		productInfoMap.put(ProductInfo.Name.PORTLET_CONTAINER,
			new ProductInfoSpecImpl("Portlet", liferayPortal, pluto));
		productInfoMap.put(ProductInfo.Name.PRIMEFACES, new ProductInfoPrimeFacesImpl());
		productInfoMap.put(ProductInfo.Name.PRIMEFACES_EXTENSIONS, new ProductInfoPrimeFacesExtensionsImpl());

		ProductInfo resin = new ProductInfoResinImpl();
		productInfoMap.put(ProductInfo.Name.RESIN, resin);
		productInfoMap.put(ProductInfo.Name.RICHFACES, new ProductInfoRichFacesImpl());

		ProductInfo servletApi = new ProductInfoServletApiImpl();
		productInfoMap.put(ProductInfo.Name.SERVLET_API, servletApi);
		productInfoMap.put(ProductInfo.Name.SPRING_FRAMEWORK, new ProductInfoSpringFrameworkImpl());

		ProductInfo tomcat = new ProductInfoTomcatImpl();
		productInfoMap.put(ProductInfo.Name.TOMCAT, tomcat);

		ProductInfo weld = new ProductInfoWeldImpl();
		productInfoMap.put(ProductInfo.Name.WELD, weld);
		productInfoMap.put(ProductInfo.Name.CDI, new ProductInfoSpecImpl("CDI", weld, openWebBeans));

		ProductInfo weblogic = new ProductInfoWebLogicImpl();
		productInfoMap.put(ProductInfo.Name.WEBLOGIC, weblogic);

		ProductInfo websphere = new ProductInfoWebSphereImpl();
		productInfoMap.put(ProductInfo.Name.WEBSPHERE, websphere);

		ProductInfo wildfly = new ProductInfoWildFlyImpl();
		productInfoMap.put(ProductInfo.Name.WILDFLY, wildfly);

		if (liferayPortal.isDetected() && (liferayPortal.getMajorVersion() >= 7)) {
			productInfoMap.put(ProductInfo.Name.SERVLET_CONTAINER,
				new ProductInfoSpecImpl("Servlet",
					new ProductInfoLiferayWabExtenderImpl(liferayPortal.getBuildId(), liferayPortal.getMajorVersion(),
						liferayPortal.getMinorVersion(), liferayPortal.getPatchVersion(), liferayPortal.getVersion(),
						liferayPortal.isDetected())));
		}
		else {
			productInfoMap.put(ProductInfo.Name.SERVLET_CONTAINER,
				new ProductInfoSpecImpl("Servlet", tomcat, wildfly, resin, glassfish, weblogic, websphere, jetty));
		}

		this.productInfoMap = Collections.unmodifiableMap(productInfoMap);
	}

	@Override
	public ProductInfo getProductInfo(ProductInfo.Name productInfoName) {
		return productInfoMap.get(productInfoName);
	}

	@Override
	public ProductInfoFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
