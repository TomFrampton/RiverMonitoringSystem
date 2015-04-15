package u1171639.rmc.main.java.view.fxml;

import u1171639.rmc.main.java.utils.FXMLViewLoader;
import u1171639.rmc.main.java.view.ViewManager;

public class HomeUserViewController extends ViewController {

	public HomeUserViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "home_user.fmxl");
	}

}
