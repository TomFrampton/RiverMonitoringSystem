package u1171639.rmc.main.java.view.fxml;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import u1171639.rmc.main.java.view.JavaFXRMCView;
import u1171639.rmc.main.java.view.ViewManager;
import u1171639.rmc.main.java.controller.RMCController;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.model.RMCZone;
import u1171639.rmc.main.java.utils.FXMLViewLoader;

public class MonitoringViewController extends ViewController {
	@FXML private TreeView<String> monitoringTree;
	@FXML private TreeItem<String> root;
	
	private LogsViewController logsViewController;
	private ZoneConfigViewController zoneConfigViewController;
	private SensorConfigViewController sensorConfigViewController;
	private SensorRegisterViewController sensorRegisterViewController;
	
	public MonitoringViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "monitoring.fxml");
		
		this.logsViewController = new LogsViewController(viewManager);
		this.zoneConfigViewController = new ZoneConfigViewController(viewManager);
		this.sensorConfigViewController = new SensorConfigViewController(viewManager);
		this.sensorRegisterViewController = new SensorRegisterViewController(viewManager);
		
		init();
	}
	
	private void init() {
		this.monitoringTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>()
        {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
            	if(observable.getValue() != null) {
            		// A Locality Node
	                if(observable.getValue().getParent() == root) {                	
	                	logsViewController.showLogs(getRMCController().getLocalityByName(newValue.getValue()));
	                	logsViewController.showInCentrePanel();
	                	clearRightPanel();
	                
	                // Zone Node
	                } else if(observable.getValue().getParent() != null &&
	                		  observable.getValue().getParent().getParent() == root) {
	                	
	                	String localityName = observable.getValue().getParent().getValue();
	                	String zoneName = observable.getValue().getValue();
	                	
	                	Locality locality = getRMCController().getLocalityByName(localityName);
	                	RMCZone zone = locality.getZoneByName(zoneName);
	                	
	                	zoneConfigViewController.setZone(zone);
	                	zoneConfigViewController.showInCentrePanel();
	                	clearRightPanel();
	                	
	                // Sensor Node
	                } else if(observable.getValue().getParent() != null && 
	                		  observable.getValue().getParent().getParent() != null &&
	                		  observable.getValue().getParent().getParent().getParent() == root) {
	                	
	                	String localityName = observable.getValue().getParent().getParent().getValue();
	                	String zoneName = observable.getValue().getParent().getValue();
	                	
	                	Locality locality = getRMCController().getLocalityByName(localityName);
	                	RMCZone zone = locality.getZoneByName(zoneName);
	                	RMCSensor sensor = zone.getSensorByName(newValue.getValue());
	                	
	                	sensor.setZoneName(zoneName);
	                	sensor.setLocalityName(localityName);
	                	
	                	sensorConfigViewController.setSensor(sensor);
	                	sensorConfigViewController.showInCentrePanel();
	                	
	                	sensorRegisterViewController.setSensor(sensor);
	                	sensorRegisterViewController.showInRightPanel();
	                } else {
	                	clearCentrePanel();
	                	clearRightPanel();
	                }
            	}
            }
        });
		updateTreeView();
	}
	
	public void updateTreeView() {
		RMCController controller = this.getRMCController();
		List<Locality> localities = controller.getLocalities();
		
		this.root = new TreeItem<String>("Region");
		this.monitoringTree.setRoot(root);
		root.setExpanded(true);
		 
		for(Locality locality : localities) {
			List<RMCZone> zones = locality.getUpdatedZones();
			
			TreeItem<String> localityItem = new TreeItem<String>(locality.getName());
			root.getChildren().add(localityItem);
			localityItem.setExpanded(true);
			
			for(RMCZone zone : zones) {
				TreeItem<String> zoneItem = new TreeItem<String>(zone.getName());
				localityItem.getChildren().add(zoneItem);
				zoneItem.setExpanded(true);
				
				List<RMCSensor> sensors = zone.getSensors();
				for(RMCSensor sensor : sensors) {
					TreeItem<String> sensorItem = new TreeItem<String>(sensor.getName());
					zoneItem.getChildren().add(sensorItem);
				}
			}
		}
	}
	
	@FXML protected void handleUpdateButtonAction() {
		updateTreeView();
	}
}
