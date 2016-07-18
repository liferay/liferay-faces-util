package com.liferay.faces.util.product.internal;


public class ProductLiferayFacesBridgeExtImpl extends ProductBaseImpl {

	public ProductLiferayFacesBridgeExtImpl() {

		try {
			this.title = "Liferay Faces Bridge Ext";

			Class<?> clazz = Class.forName("com.liferay.faces.bridge.ext.GenericLiferayFacesPortlet");
			init(clazz, "Liferay Faces Bridge Ext");
		}
		catch (Exception e) {
			// Ignore -- Liferay Faces Bridge Ext is likely not present.
		}
	}
}
