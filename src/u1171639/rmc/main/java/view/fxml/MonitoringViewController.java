package u1171639.rmc.main.java.view.fxml;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import u1171639.rmc.main.java.view.JavaFXRMCView;
import u1171639.rmc.main.java.view.ViewManager;
import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.Zone;
import u1171639.rmc.main.java.utils.FXMLViewLoader;

public class MonitoringViewController extends ViewController {
	@FXML private TreeView<String> monitoringTree;
	
	public MonitoringViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "monitoring.fxml");
		init();
	}
	
	private void init() {
		updateTreeView();
	}
	
	private void updateTreeView() {
		RMCController controller = this.getRMCController();
		List<Locality> localities = controller.getLocalities();
		
		TreeItem<String> root = new TreeItem<String>("Region");
		this.monitoringTree.setRoot(root);
		root.setExpanded(true);
		 
		for(Locality locality : localities) {
			locality.updateLocalityInfo();
			
			TreeItem<String> localityItem = new TreeItem<String>(locality.getName());
			root.getChildren().add(localityItem);
			localityItem.setExpanded(true);
			
			List<Zone> zones = locality.getZones();
			for(Zone zone : zones) {
				TreeItem<String> zoneItem = new TreeItem<String>(zone.getName());
				localityItem.getChildren().add(zoneItem);
			}
		}
	}
	
	@FXML protected void handleUpdateButtonAction() {
		updateTreeView();
	}
}
