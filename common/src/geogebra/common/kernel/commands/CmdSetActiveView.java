package geogebra.common.kernel.commands;

import geogebra.common.kernel.Kernel;
import geogebra.common.kernel.arithmetic.Command;
import geogebra.common.kernel.geos.GeoNumeric;
import geogebra.common.main.AbstractApplication;
import geogebra.common.main.MyError;

/**
 *SetActiveView
 */
public class CmdSetActiveView extends CmdScripting {
	/**
	 * Creates new command processor
	 * @param kernel kernel
	 */
	public CmdSetActiveView(Kernel kernel) {
		super(kernel);
	}

	@Override
	protected
	final void perform(Command c) throws MyError {
		int n = c.getArgumentNumber();
		
		
		

		if (!app.isUsingFullGui()) return;
			
		switch (n) {
		case 1:
			arg = resArgs(c);
			if (arg[0].isGeoNumeric()) {
				GeoNumeric numGeo = (GeoNumeric) arg[0];

				int view = (int)numGeo.getDouble();
				
				// ignore all errors (eg when a view is not available etc)
				switch (view) {
				case 1:
					app.setActiveView(AbstractApplication.VIEW_EUCLIDIAN);
					 break;
				case 2:
					app.setActiveView(AbstractApplication.VIEW_EUCLIDIAN2);
					 break;
				case 3:
					app.setActiveView(AbstractApplication.VIEW_EUCLIDIAN3D);
					 break;
				case -1:
					app.setActiveView(AbstractApplication.VIEW_SPREADSHEET);
					 break;
				case -2:
					app.setActiveView(AbstractApplication.VIEW_ALGEBRA);
					 break;
				case -3:
					app.setActiveView(AbstractApplication.VIEW_CAS);
					 break;
				// default: // might be needed when support for more than 2 Euclidian Views added 
				}
				
				return;

			} 
			throw argErr(app, c.getName(), arg[0]);


		default:
			throw argNumErr(app, c.getName(), n);
		}
	}
}
