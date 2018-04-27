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
package com.liferay.faces.util.product.internal;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductFactory;


/**
 * @author  Kyle Stiemann
 */
public class ProductFactoryImpl extends ProductFactory {

	// Private Final Data Members
	private final Map<Product.Name, Product> productMap;

	public ProductFactoryImpl() {

		Map<Product.Name, Product> productMap = new EnumMap<Product.Name, Product>(Product.Name.class);
		productMap.put(Product.Name.ANGULARBEANS, new ProductAngularBeansImpl());
		productMap.put(Product.Name.ANGULARFACES, new ProductAngularFacesImpl());
		productMap.put(Product.Name.BOOTSFACES, new ProductBootsFacesImpl());
		productMap.put(Product.Name.BUTTERFACES, new ProductButterFacesImpl());

		ProductBase cdiApi = new ProductCDIApiImpl();
		productMap.put(Product.Name.CDI_API, cdiApi);
		productMap.put(Product.Name.CLOSURE_TEMPLATES, new ProductClosureTemplatesImpl());
		productMap.put(Product.Name.DELTASPIKE, new ProductDeltaSpikeImpl());

		ProductBase glassfish = new ProductGlassfishImpl();
		productMap.put(Product.Name.GLASSFISH, glassfish);
		productMap.put(Product.Name.HIGHFACES, new ProductHighFacesImpl());
		productMap.put(Product.Name.ICEFACES, new ProductICEfacesImpl());

		ProductBase jetty = new ProductJettyImpl();
		productMap.put(Product.Name.JETTY, jetty);
		productMap.put(Product.Name.LIFERAY_FACES_ALLOY, new ProductLiferayFacesAlloyImpl());
		productMap.put(Product.Name.LIFERAY_FACES_BRIDGE, new ProductLiferayFacesBridgeImpl());
		productMap.put(Product.Name.LIFERAY_FACES_BRIDGE_EXT, new ProductLiferayFacesBridgeExtImpl());
		productMap.put(Product.Name.LIFERAY_FACES_METAL, new ProductLiferayFacesMetalImpl());
		productMap.put(Product.Name.LIFERAY_FACES_CLAY, new ProductLiferayFacesClayImpl());
		productMap.put(Product.Name.LIFERAY_FACES_PORTAL, new ProductLiferayFacesPortalImpl());
		productMap.put(Product.Name.LIFERAY_FACES_SHOWCASE, new ProductLiferayFacesShowcaseImpl());
		productMap.put(Product.Name.LIFERAY_FACES_UTIL, new ProductLiferayFacesUtilImpl());

		ProductBase liferayPortal = new ProductLiferayPortalImpl();
		productMap.put(Product.Name.LIFERAY_PORTAL, liferayPortal);

		ProductBase mojarra = new ProductMojarraImpl();
		productMap.put(Product.Name.MOJARRA, mojarra);

		ProductBase myFaces = new ProductMyfacesImpl();
		productMap.put(Product.Name.MYFACES, myFaces);

		ProductBase jsfApi = new ProductJSFApiImpl(mojarra, myFaces);
		productMap.put(Product.Name.JSF_API, jsfApi);
		productMap.put(Product.Name.JSF, new ProductSpecImpl("JSF", mojarra, myFaces, jsfApi));
		productMap.put(Product.Name.OMNIFACES, new ProductOmniFacesImpl());

		ProductBase openWebBeans = new ProductOpenWebBeansImpl();
		productMap.put(Product.Name.OPEN_WEB_BEANS, openWebBeans);

		ProductBase pluto = new ProductPlutoImpl();
		productMap.put(Product.Name.PLUTO, pluto);

		ProductBase portletApi = new ProductPortletApiImpl(liferayPortal, pluto);
		productMap.put(Product.Name.PORTLET_API, portletApi);
		productMap.put(Product.Name.PORTLET_CONTAINER, new ProductSpecImpl("Portlet", liferayPortal, pluto));
		productMap.put(Product.Name.PRIMEFACES, new ProductPrimeFacesImpl());
		productMap.put(Product.Name.PRIMEFACES_EXTENSIONS, new ProductPrimeFacesExtensionsImpl());

		ProductBase resin = new ProductResinImpl();
		productMap.put(Product.Name.RESIN, resin);
		productMap.put(Product.Name.RICHFACES, new ProductRichFacesImpl());

		ProductBase servletApi = new ProductServletApiImpl();
		productMap.put(Product.Name.SERVLET_API, servletApi);
		productMap.put(Product.Name.SPRING_FRAMEWORK, new ProductSpringFrameworkImpl());

		ProductBase tomcat = new ProductTomcatImpl();
		productMap.put(Product.Name.TOMCAT, tomcat);

		ProductBase weld = new ProductWeldImpl();
		productMap.put(Product.Name.WELD, weld);
		productMap.put(Product.Name.CDI, new ProductSpecImpl("CDI", weld, openWebBeans));

		ProductBase weblogic = new ProductWebLogicImpl();
		productMap.put(Product.Name.WEBLOGIC, weblogic);

		ProductBase websphere = new ProductWebSphereImpl();
		productMap.put(Product.Name.WEBSPHERE, websphere);

		ProductBase wildfly = new ProductWildFlyImpl();
		productMap.put(Product.Name.WILDFLY, wildfly);

		if (liferayPortal.isDetected() && (liferayPortal.getMajorVersion() >= 7)) {
			productMap.put(Product.Name.SERVLET_CONTAINER,
				new ProductSpecImpl("Servlet",
					new ProductLiferayWabExtenderImpl(liferayPortal.isDetected(), liferayPortal.getVersion(),
						liferayPortal.getMajorVersion(), liferayPortal.getMinorVersion(),
						liferayPortal.getPatchVersion(), liferayPortal.getBuildId())));
		}
		else {
			productMap.put(Product.Name.SERVLET_CONTAINER,
				new ProductSpecImpl("Servlet", tomcat, wildfly, resin, glassfish, weblogic, websphere, jetty));
		}

		this.productMap = Collections.unmodifiableMap(productMap);
	}

	@Override
	public Product getProductInfo(Product.Name productName) {
		return productMap.get(productName);
	}

	@Override
	public ProductFactory getWrapped() {

		// Since this is the default factory instance, it will never wrap another factory.
		return null;
	}
}
