package org.geogebra.web.full.gui.dialog;

import org.geogebra.common.GeoGebraConstants;
import org.geogebra.common.util.debug.Analytics;
import org.geogebra.web.full.main.AppWFull;
import org.geogebra.web.html5.gui.GPopupPanel;
import org.geogebra.web.html5.gui.view.button.StandardButton;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.html5.util.Dom;
import org.geogebra.web.html5.util.Persistable;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Calculator chooser for suite
 */
public class CalculatorSwitcherDialog extends GPopupPanel implements Persistable, ResizeHandler {

	/**
	 * constructor
	 * @param app see {@link AppW}
	 */
	public CalculatorSwitcherDialog(AppW app, boolean autoHide) {
		super(autoHide, app.getPanel(), app);
		setGlassEnabled(true);
		addStyleName("calcChooser");
		Dom.toggleClass(this, "smallScreen", app.getWidth() < 914);
		app.registerPopup(this);
		buildGUI();
		Window.addResizeHandler(this);
	}

	private void buildGUI() {
		FlowPanel contentPanel = new FlowPanel();
		Label title = new Label(app.getLocalization().getMenu("ChooseCalculator"));
		title.addStyleName("title");
		contentPanel.add(title);

		buildAndAddCalcButton(GeoGebraConstants.GRAPHING_APPCODE, contentPanel);
		if (app.getSettings().getEuclidian(-1).isEnabled()) {
			buildAndAddCalcButton(GeoGebraConstants.G3D_APPCODE, contentPanel);
		}
		buildAndAddCalcButton(GeoGebraConstants.GEOMETRY_APPCODE, contentPanel);
		if (app.getSettings().getCasSettings().isEnabled()) {
			buildAndAddCalcButton(GeoGebraConstants.CAS_APPCODE, contentPanel);
		}
		buildAndAddCalcButton(GeoGebraConstants.PROBABILITY_APPCODE, contentPanel);

		add(contentPanel);
	}

	private void buildAndAddCalcButton(String subAppCode, FlowPanel contentPanel) {
		AppDescription description = AppDescription.get(subAppCode) ;
		String appNameKey = description.getNameKey();
		StandardButton button =  new StandardButton(72, description.getIcon(),
				 app.getLocalization().getMenu(appNameKey));
		button.setStyleName("calcBtn");
		if (subAppCode.equals(app.getConfig().getSubAppCode())) {
			button.addStyleName("selected");
		}

		button.addFastClickHandler(source -> {
			hide();
			((AppWFull) app).setSuiteHeaderButton(subAppCode);
			((AppWFull) app).switchToSubapp(subAppCode);
			Analytics.logEvent(Analytics.Event.APP_SWITCHED, Analytics.Param.SUB_APP,
					Analytics.Param.convertToSubAppParam(subAppCode));
		});

		contentPanel.add(button);
	}

	@Override
	public void show() {
		super.show();
		super.center();
	}

	@Override
	public void onResize(ResizeEvent event) {
		if (isShowing()) {
			Dom.toggleClass(this, "smallScreen", app.getWidth() < 914);
			super.centerAndResize(((AppW) app).getAppletFrame().getKeyboardHeight());
		}
	}
}
