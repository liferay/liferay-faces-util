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
package com.liferay.faces.util.product.internal;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductFactory;


/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class ProductFactoryImpl extends ProductFactory {

	private static final Map<Product.Name, Product> PRODUCTS;

	static {

		Map<Product.Name, Product> productMap = new EnumMap<Product.Name, Product>(Product.Name.class);
		productMap.put(Product.Name.ANGULARBEANS, new ProductAngularBeansImpl());
		productMap.put(Product.Name.ANGULARFACES, new ProductAngularFacesImpl());
		productMap.put(Product.Name.BOOTSFACES, new ProductBootsFacesImpl());
		productMap.put(Product.Name.BUTTERFACES, new ProductButterFacesImpl());
		productMap.put(Product.Name.DELTASPIKE, new ProductDeltaSpikeImpl());
		productMap.put(Product.Name.ICEFACES, new ProductICEfacesImpl());
		productMap.put(Product.Name.LIFERAY_FACES_ALLOY, new ProductLiferayFacesAlloyImpl());
		productMap.put(Product.Name.LIFERAY_FACES_BRIDGE, new ProductLiferayFacesBridgeImpl());
		productMap.put(Product.Name.LIFERAY_FACES_METAL, new ProductLiferayFacesMetalImpl());
		productMap.put(Product.Name.LIFERAY_FACES_PORTAL, new ProductLiferayFacesPortalImpl());
		productMap.put(Product.Name.LIFERAY_FACES_SHOWCASE, new ProductLiferayFacesShowcaseImpl());
		productMap.put(Product.Name.LIFERAY_FACES_UTIL, new ProductLiferayFacesUtilImpl());

		ProductLiferayPortalImpl productLiferayPortalImpl = new ProductLiferayPortalImpl();
		productMap.put(Product.Name.LIFERAY_PORTAL, productLiferayPortalImpl);

		Product productMojarraImpl = new ProductMojarraImpl();
		productMap.put(Product.Name.MOJARRA, productMojarraImpl);

		Product productMyfacesImpl = new ProductMyfacesImpl();
		productMap.put(Product.Name.MYFACES, productMyfacesImpl);
		productMap.put(Product.Name.JSF, new ProductSpecImpl("JSF", productMojarraImpl, productMyfacesImpl));
		productMap.put(Product.Name.OMNIFACES, new ProductOmniFacesImpl());

		ProductPlutoImpl productPlutoImpl = new ProductPlutoImpl();
		productMap.put(Product.Name.PLUTO, productPlutoImpl);
		productMap.put(Product.Name.PORTAL, new ProductSpecImpl("Portal", productLiferayPortalImpl, productPlutoImpl));
		productMap.put(Product.Name.PRIMEFACES, new ProductPrimeFacesImpl());
		productMap.put(Product.Name.PRIMEFACES_EXTENSIONS, new ProductPrimeFacesExtensionsImpl());
		productMap.put(Product.Name.RESIN, new ProductResinImpl());
		productMap.put(Product.Name.RICHFACES, new ProductRichFacesImpl());
		productMap.put(Product.Name.SPRING_FRAMEWORK, new ProductSpringFrameworkImpl());

		Product productOpenWebBeansImpl = new ProductOpenWebBeansImpl();
		productMap.put(Product.Name.OPEN_WEB_BEANS, productOpenWebBeansImpl);

		Product productWeldImpl = new ProductWeldImpl();
		productMap.put(Product.Name.WELD, productWeldImpl);
		productMap.put(Product.Name.CDI, new ProductSpecImpl("CDI", productWeldImpl, productOpenWebBeansImpl));
		productMap.put(Product.Name.WILDFLY, new ProductWildFlyImpl());
		PRODUCTS = Collections.unmodifiableMap(productMap);
	}

	@Override
	public Product getProductImplementation(Product.Name productId) {
		return PRODUCTS.get(productId);
	}
}
