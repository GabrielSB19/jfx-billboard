package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.*;

public class InfrastructureDepartmentGUI {
	
	private InfrastructureDepartment infrastructureDepartemt;
	
	public InfrastructureDepartmentGUI (InfrastructureDepartment infrastructureDepartemt) {
		this.infrastructureDepartemt = infrastructureDepartemt;
		
    	
		Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("Billboards");
    	alert.setHeaderText("Load Data");

		
		try {
			boolean loaded =infrastructureDepartemt.loadData();
			if(!loaded) {
				alert.setContentText("The data file does not exist. The data was not loaded");
				alert.showAndWait();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			alert.setContentText("The data file is damaged. The data was not loaded");
			alert.showAndWait();
		}
	}
		
	@FXML
    private Pane mainPane;
	

    @FXML
    private Pane addPane;
    
    @FXML
    private Pane showPane;
    
    @FXML
    private TextField txtWidth;

    @FXML
    private TextField txtHeigth;
    
    @FXML
    private ToggleGroup Use;
    
    @FXML
    private RadioButton rdbYes;

    @FXML
    private RadioButton rdbNo;

    @FXML
    private TextField txtBrand;
    
    @FXML
    private TableView<Billboard> mainTable;

    @FXML
    private TableColumn<Billboard, Double> tbcWidth;

    @FXML
    private TableColumn<Billboard, Double> tbcHeight;

    @FXML
    private TableColumn<Billboard, Boolean> tbcUse;

    @FXML
    private TableColumn<Billboard, String> tbcBrand;

    @FXML
    private TableColumn<Billboard, Double> tbcArea;



    @FXML
    public void onAbout(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("About information");
    	alert.setHeaderText("Credits");
		alert.setContentText("Gabriel Suarez / APO II");
		alert.showAndWait();
    }

    @FXML
    public void onAdd(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddBillBoard.fxml"));
    	fxmlLoader.setController(this);
    	Parent addBillboard = fxmlLoader.load();
    	mainPane.getChildren().clear();
    	mainPane.getChildren().setAll(addBillboard);
    }

    @FXML
    public void onExit(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    public void onExport(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Save File");
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files","*.txt"));
    	File f = fileChooser.showSaveDialog(mainPane.getScene().getWindow());
    	if(f != null) {
    		try {
    			infrastructureDepartemt.exportData(f.getAbsolutePath());
				Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Export Dangerous Billboards");
			    alert.setContentText("The report of the dangerous billaboards was exported succesfully");
			    alert.showAndWait();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Export Dangerous Billboards");
			    alert.setContentText("The report of the dangerous billaboards was not exported. An error ocurred");
			    
			    alert.showAndWait();
			}
    	}
    }

    @FXML
    public void onImport(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Data File");
    	File f = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
    	if (f != null) {
    		try {
    			infrastructureDepartemt.importData(f.getAbsolutePath());
				Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Import Billboards");
			    alert.setContentText("The billboards data was imported succesfully");
			    alert.showAndWait();
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Import Billboards");
			    alert.setContentText("The billboards data was not imported. An error ocurred");
			    alert.showAndWait();
			}
    	}
    }

    @FXML
    public void onShow(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShowBillboard.fxml"));
    	fxmlLoader.setController(this);
    	Parent showBillboard = fxmlLoader.load();
    	mainPane.getChildren().clear();
    	mainPane.getChildren().setAll(showBillboard);
    	
    	onTable ();
    }
    
    @FXML
    public void onAddBill(ActionEvent event) throws IOException {
    	try {
    		if (!txtWidth.getText().isEmpty() && !txtHeigth.getText().isEmpty() &&
    	    		!txtBrand.getText().isEmpty()) {
    	    		double width = Double.parseDouble(txtWidth.getText());
    	    		double heigth = Double.parseDouble(txtHeigth.getText());
    	    		infrastructureDepartemt.addBillboard(width, heigth, selectUse(), txtBrand.getText());
    	    		
    	        	Alert alertCreateAccount = new Alert(AlertType.INFORMATION);
    	        	alertCreateAccount.setTitle("Billboard created");
    	        	alertCreateAccount.setHeaderText("New billboard");
    	        	alertCreateAccount.setContentText("The new billboard has been created");
    	        	alertCreateAccount.showAndWait();
    	        	
    	        	onAdd(event);
    	    	}else {
    	    		Alert alertErrorCreateAccount = new Alert(AlertType.ERROR);
    	    		alertErrorCreateAccount.setTitle("Validation Error");
    	    		alertErrorCreateAccount.setHeaderText("Billboard not created");
    	    		alertErrorCreateAccount.setContentText("You must fill each field in the form");
    	    		alertErrorCreateAccount.showAndWait();
    	    		
    	    		onAdd(event);
    	    	}
    	} catch (NumberFormatException e) {
    		Alert alertErrorCreateAccount = new Alert(AlertType.ERROR);
    		alertErrorCreateAccount.setTitle("Data Error");
    		alertErrorCreateAccount.setHeaderText("Misdata");
    		alertErrorCreateAccount.setContentText("Please only enter number in width and length");
    		alertErrorCreateAccount.showAndWait();
    		
    		onAdd(event);
    	}
    }
    
    public boolean selectUse () {
    	boolean use = false;
    	if (rdbYes.isSelected()) {
    		use = true;
    	} else if (rdbNo.isSelected()){
    		use = false;
    	}
    	return use;
    }
    
    @FXML
    public void onBackAddToMain(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BillboardMain.fxml"));
    	fxmlLoader.setController(this);
    	Parent backBillboard = fxmlLoader.load();
    	addPane.getChildren().clear();
    	addPane.getChildren().setAll(backBillboard);
    }

    @FXML
    public void onBackShowToMain(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BillboardMain.fxml"));
    	fxmlLoader.setController(this);
    	Parent backBillboard = fxmlLoader.load();
    	showPane.getChildren().clear();
    	showPane.getChildren().setAll(backBillboard);
    }

    public void onTable () {
    	ObservableList<Billboard> newListBillboard;
    	
    	newListBillboard = FXCollections.observableArrayList(infrastructureDepartemt.getBillboard());
    	
    	mainTable.setItems(newListBillboard);
    	tbcWidth.setCellValueFactory(new PropertyValueFactory<Billboard, Double>("width"));
    	tbcHeight.setCellValueFactory(new PropertyValueFactory<Billboard, Double>("height"));
    	tbcUse.setCellValueFactory(new PropertyValueFactory<Billboard, Boolean>("use"));
    	tbcBrand.setCellValueFactory(new PropertyValueFactory<Billboard, String>("brand"));
    	tbcArea.setCellValueFactory(new PropertyValueFactory<Billboard, Double>("area"));
    }
}
