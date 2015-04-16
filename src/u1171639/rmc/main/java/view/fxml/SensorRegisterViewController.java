package u1171639.rmc.main.java.view.fxml;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import u1171639.rmc.main.java.model.RMCSensor;
import u1171639.rmc.main.java.users.HomeUser;
import u1171639.rmc.main.java.utils.FXMLViewLoader;
import u1171639.rmc.main.java.view.ViewManager;

public class SensorRegisterViewController extends ViewController {

	@FXML private TableView<HomeUser> registeredUsersTable;
	
	private NewHomeUserViewController homeUserViewController;
	private ExistingHomeUserViewController existingHomeUserViewController;
	
	private ObservableList<HomeUser> registeredUsers = FXCollections.observableArrayList();
	
	private RMCSensor sensor;
	
	public SensorRegisterViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "sensor_register.fxml");
		
		this.homeUserViewController = new NewHomeUserViewController(viewManager);
		this.existingHomeUserViewController = new ExistingHomeUserViewController(viewManager);
		
		this.init();
	}
	
	private void init() {
		this.registeredUsersTable.getColumns().addAll(SensorRegisterViewController.getColumns(this.registeredUsersTable));
		this.registeredUsersTable.setItems(this.registeredUsers);
		this.registeredUsersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	public void setSensor(RMCSensor sensor) {
		this.sensor = sensor;
		updateUsersList();
	}
	
	private void updateUsersList() {
		this.registeredUsers.clear();
		this.registeredUsers.addAll(this.sensor.getAllRegisteredUsers());
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
