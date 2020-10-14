package org.geogebra.web.full.gui.dialog;

import org.geogebra.web.full.gui.images.SvgPerspectiveResources;
import org.geogebra.web.full.main.AppWFull;
import org.geogebra.web.html5.gui.GPopupPanel;
import org.geogebra.web.html5.gui.util.AriaHelper;
import org.geogebra.web.html5.gui.util.NoDragImage;
import org.geogebra.web.html5.main.UserPreferredLanguage;
import org.geogebra.web.html5.util.Dom;
import org.geogebra.web.resources.SVGResource;
import org.geogebra.web.shared.SuiteHeaderAppPicker;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class CalcSwitcherPopup extends GPopupPanel {

	SuiteHeaderAppPicker appPickerButton;
	private final AppWFull app;

	private final static int X_COORDINATE_OFFSET = 8;

	/**
	 * @param app
	 *            - application
	 * @param pickerButton
	 *            - button for popup
	 */
	public CalcSwitcherPopup(AppWFull app, SuiteHeaderAppPicker pickerButton) {
		super(true, app.getPanel(), app);
		this.appPickerButton = pickerButton;
		this.app = app;
		addAutoHidePartner(appPickerButton.getElement());
		setGlassEnabled(false);
		addStyleName("calcPickerPopup");
		buildGUI(app);
		app.registerAutoclosePopup(this);
	}

	@Override
	public void show() {
		if (isShowing()) {
			hide();
		} else {
			this.setPopupPosition(getLeft(), 0);
			super.show();
			updateLanguage();
			appPickerButton.checkButtonVisibility();
		}
	}

	private void buildGUI(AppWFull app) {
		FlowPanel contentPanel = new FlowPanel();
		contentPanel.addStyleName("popupPanelForTranslation");
		SvgPerspectiveResources res = SvgPerspectiveResources.INSTANCE;
		addElement(app, res.menu_icon_algebra_transparent(), "GraphingCalculator.short",
				contentPanel);
		addElement(app, res.menu_icon_graphics3D_transparent(), "GeoGebra3DGrapher.short",
				contentPanel);
		addElement(app, res.menu_icon_geometry_transparent(), "Geometry", contentPanel);
		addElement(app, res.menu_icon_cas_transparent(), "CAS", contentPanel);
		add(contentPanel);
	}

	private void addElement(AppWFull app, SVGResource icon, String key, FlowPanel contentPanel) {
		FlowPanel rowPanel = new FlowPanel();
		NoDragImage img = new NoDragImage(icon, 24, 24);
		img.addStyleName("appIcon");
		rowPanel.add(img);

		Label label = new Label(app.getLocalization().getMenu(key));
		label.addStyleName("appPickerLabel");
		AriaHelper.setAttribute(label, "data-trans-key", key);
		rowPanel.add(label);
		rowPanel.setStyleName("appPickerRow");
		rowPanel.addDomHandler(event -> {
				// open app
			hide();
			appPickerButton.setIconAndLabel(icon, key, app);
		}, ClickEvent.getType());
		contentPanel.add(rowPanel);
	}

	private int getLeft() {
		return appPickerButton.getAbsoluteLeft() - X_COORDINATE_OFFSET ;
	}

	private void updateLanguage() {
		Element suitePopup = Dom.querySelector("popupPanelForTranslation");
		if (suitePopup != null) {
			UserPreferredLanguage.translate(app, suitePopup);
		}
	}
}
