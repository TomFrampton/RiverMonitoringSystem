package u1171639.rmc.main.java.view.fxml;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.users.HomeUser;
import u1171639.rmc.main.java.utils.FXMLViewLoader;
import u1171639.rmc.main.java.view.ViewManager;

public class SensorConfigViewController extends ViewController {

	@FXML private Label localityLabel;
	@FXML private Label zoneLabel;
	@FXML private Label sensorLabel;
	
	@FXML private Button sensorThresholdUpdateButton;
	@FXML private Button activationButton;
	
	@FXML private Slider thresholdSlider;
	
	@FXML private Label currentStateLabel;
	
	@FXML private TableView<HomeUser> registeredUsersTable;
	
	private NewHomeUserViewController homeUserViewController;
	private ExistingHomeUserViewController existingHomeUserViewController;
	
	private ObservableList<HomeUser> registeredUsers = FXCollections.observableArrayList();
	
	private RMCSensor sensor;
	
	public SensorConfigViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "sensor_config.fxml");
		
		this.homeUserViewController = new NewHomeUserViewController(viewManager);
		this.existingHomeUserViewController = new ExistingHomeUserViewController(viewManager);
		
		init();
	}
	
	private void init() {
		this.thresholdSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				SensorConfigViewController.this.sensorThresholdUpdateButton.setDisable(false);
			}
		});
		
		this.registeredUsersTable.getColumns().addAll(SensorConfigViewController.getColumns(this.registeredUsersTable));
		this.registeredUsersTable.setItems(this.registeredUsers);
		this.registeredUsersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	@FXML protected void handleUpdateSensorThresholdClicked(MouseEvent event) {
		Locality locality = getRMCController().getLocalityByName(this.sensor.getLocalityName());
		
		if(locality.setWarningThreshold(this.sensor.getZoneName(), this.sensor.getName(), Math.round(this.thresholdSlider.getValue()))) {
			this.sensorThresholdUpdateButton.setDisable(true);
		}
	}
	
	@FXML protected void handleActivationButtonClicked(MouseEvent event) {
		Locality locality = getRMCController().getLocalityByName(this.sensor.getLocalityName());
		
		if(this.sensor.isActive()) {
			if(locality.deactivateSensor(this.sensor.getZoneName(), this.sensor.getName())) {
				sensorDeactivated();
			}
		} else {
			if(locality.activateSensor(this.sensor.getZoneName(), this.sensor.getName())) {
				sensorActivated();
			}
		}
	}
	
	@FXML protected void handleRegisterExistingUserClicked(MouseEvent event) {
		this.existingHomeUserViewController.setSensor(this.sensor);
		this.existingHomeUserViewController.showInModal(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				updateUsersList();
				return null;
			}
		});
	}
	
	@FXML protected void handleRegisterNewUserClicked(MouseEvent event) {
		this.homeUserViewController.setSensor(this.sensor);
		this.homeUserViewController.showInModal(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				updateUsersList();
				return null;
			}
		});
	}
	
	public void setSensor(RMCSensor sensor) {
		this.sensor = sensor;
		
		this.localityLabel.setText(sensor.getLocalityName());
		this.zoneLabel.setText(sensor.getZoneName());
		this.sensorLabel.setText(sensor.getName());
		
		this.thresholdSlider.valueProperty().set(sensor.getThreshold());
		this.sensorThresholdUpdateButton.setDisable(true);
		
		if(sensor.isActive()) {
			sensorActivated();
		} else {
			sensorDeactivated();
		}
		
		updateUsersList();
	}
	
	private void sensorActivated() {
		this.currentStateLabel.setText("Active");
		this.currentStateLabel.setStyle("-fx-text-fill: #22b90f");
		this.activationButton.setText("Deactivate");
		this.sensor.setActive(true);
	}

	private void sensorDeactivated() {
		this.currentStateLabel.setText("Deactivated");
		this.currentStateLabel.setStyle("-fx-text-fill: #ff0000e8");
		this.activationButton.setText("Activate");
		this.sensor.setActive(false);
	}

	private void updateUsersList() {
		this.registeredUsers.clear();
		this.registeredUsers.addAll(getRMCController().getHomeUserManager().getAllUsersRegistered(this.sensor.getName()));
	}
	
	public static ArrayList<TableColumn<HomeUser, ?>> getColumns(final TableView<HomeUser> table) {
		ArrayList<TableColumn<HomeUser, ?>> columns = new ArrayList<>();
		
		TableColumn<HomeUser,String> forenameCol = new TableColumn<HomeUser,String>("Forename");
		forenameCol.setCellValueFactory(new javafx.util.Callback<CellDataFeatures<HomeUser, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<HomeUser, String> param) {
				return new SimpleStringProperty(param.getValue().getForename());
				
			}
		});
		
		TableColumn<HomeUser,String> surnameCol = new TableColumn<HomeUser,String>("Surname");
		surnameCol.setCellValueFactory(new javafx.util.Callback<CellDataFeatures<HomeUser, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<HomeUser, String> param) {
				return new SimpleStringProperty(param.getValue().getSurname());
				
			}
		});
		
		TableColumn<HomeUser,String> usernameCol = new TableColumn<HomeUser,String>("Username");
		usernameCol.setCellValueFactory(new javafx.util.Callback<CellDataFeatures<HomeUser, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<HomeUser, String> param) {
				return new SimpleStringProperty(param.getValue().getUsername());
				
			}
		});
			
		columns.add(forenameCol);
		columns.add(surnameCol);
		columns.add(usernameCol);
	
		return columns;
		
	}

}
