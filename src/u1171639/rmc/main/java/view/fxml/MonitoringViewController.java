package u1171639.rmc.main.java.view.fxml;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
	
	public MonitoringViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "monitoring.fxml");
		
		this.logsViewController = new LogsViewController(viewManager);
		this.zoneConfigViewController = new ZoneConfigViewController(viewManager);
		this.sensorConfigViewController = new SensorConfigViewController(viewManager);
		
		init();
	}
	
	private void init() {
		this.monitoringTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>()
        {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
            	if(observable.getValue() != null) {
            		// A Locality Node
	                if(observable.getValue().getParent() == MonitoringViewController.this.root) {                	
	                	MonitoringViewController.this.logsViewController.showLogs(getRMCController().getLocalityByName(newValue.getValue()));
	                	MonitoringViewController.this.logsViewController.showInRightPanel();
	                
	                // Zone Node
	                } else if(observable.getValue().getParent() != null &&
	                		  observable.getValue().getParent().getParent() == MonitoringViewController.this.root) {
	                	
	                	String localityName = observable.getValue().getParent().getValue();
	                	String zoneName = observable.getValue().getValue();
	                	
	                	Locality locality = getRMCController().getLocalityByName(localityName);
	                	RMCZone zone = locality.getZoneByName(zoneName);
	                	
	                	MonitoringViewController.this.zoneConfigViewController.setZone(zone);
	                	MonitoringViewController.this.zoneConfigViewController.showInRightPanel();
	                	
	                // Sensor Node
	                } else if(observable.getValue().getParent() != null && 
	                		  observable.getValue().getParent().getParent() != null &&
	                		  observable.getValue().getParent().getParent().getParent() == MonitoringViewController.this.root) {
	                	
	                	String localityName = observable.getValue().getParent().getParent().getValue();
	                	String zoneName = observable.getValue().getParent().getValue();
	                	
	                	Locality locality = getRMCController().getLocalityByName(localityName);
	                	RMCZone zone = locality.getZoneByName(zoneName);
	                	RMCSensor sensor = zone.getSensorByName(newValue.getValue());
	                	
	                	sensor.setZoneName(zoneName);
	                	sensor.setLocalityName(localityName);
	                	
	                	MonitoringViewController.this.sensorConfigViewController.setSensor(sensor);
	                	MonitoringViewController.this.sensorConfigViewController.showInRightPanel();
	                } else {
	                	clearRightPanel();
	                }
            	}
            }
        });
		updateTreeView();
	}
	
	public void updateTreeView() {
		RMCController controller = getRMCController();
		List<Locality> localities = controller.getLocalities();
		
		this.root = new TreeItem<String>("Region");
		this.monitoringTree.setRoot(this.root);
		this.root.setExpanded(true);
		 
		for(Locality locality : localities) {
			List<RMCZone> zones = locality.getUpdatedZones();
			
			TreeItem<String> localityItem = new TreeItem<String>(locality.getName());
			this.root.getChildren().add(localityItem);
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
