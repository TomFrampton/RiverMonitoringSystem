package u1171639.rmc.main.java.view.fxml;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;

import u1171639.rmc.main.java.model.Locality;
import u1171639.rmc.main.java.utils.FXMLViewLoader;
import u1171639.rmc.main.java.view.ViewManager;
import u1171639.shared.main.java.logging.LogItem;

public class LogsViewController extends ViewController {
	
	@FXML private TableView<LogItem> logTable;
	
	private ObservableList<LogItem> retrievedLogs = FXCollections.observableArrayList();
	
	public LogsViewController(ViewManager viewManager) {
		super(viewManager);
		FXMLViewLoader.loadView(this, "logs.fxml");
		init();
	}
	
	private void init() {
		this.logTable.getColumns().addAll(LogsViewController.getColumns(this.logTable));
		this.logTable.setItems(this.retrievedLogs);
		this.logTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	public void showLogs(Locality locality) {
		this.retrievedLogs.clear();
		this.retrievedLogs.addAll(locality.getUpdatedLogs());
	}
	
	public static ArrayList<TableColumn<LogItem, ?>> getColumns(final TableView<LogItem> table) {
		ArrayList<TableColumn<LogItem, ?>> columns = new ArrayList<>();
		
		TableColumn<LogItem,String> logTimeCol = new TableColumn<LogItem,String>("Time");
		logTimeCol.setCellValueFactory(new javafx.util.Callback<CellDataFeatures<LogItem, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<LogItem, String> param) {
				return new SimpleStringProperty(param.getValue().getTime().toString());
				
			}
		});
		
		TableColumn<LogItem,String> logEventCol = new TableColumn<LogItem,String>("Event Type");
		logEventCol.setCellValueFactory(new javafx.util.Callback<CellDataFeatures<LogItem, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<LogItem, String> param) {
				return new SimpleStringProperty(param.getValue().getEvent().toString());
				
			}
		 });
		
		TableColumn<LogItem,String> logMessageCol = new TableColumn<LogItem,String>("Message");
		logMessageCol.setCellValueFactory(new javafx.util.Callback<CellDataFeatures<LogItem, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<LogItem, String> param) {
				return new SimpleStringProperty(param.getValue().getMessage());
				
			}
		});	
			
		columns.add(logTimeCol);
		columns.add(logEventCol);
		columns.add(logMessageCol);
	
		return columns;
		
	}
}
