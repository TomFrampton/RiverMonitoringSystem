package u1171639.sensor.main.java.view.fxml;

import u1171639.sensor.main.java.utils.FXMLViewLoader;

public class SimulationViewController extends ViewController {
	
	public SimulationViewController() {
		FXMLViewLoader.loadView(this, "simulation.fxml");
		init();
	}
	
	private void init() {
		
	}

}
