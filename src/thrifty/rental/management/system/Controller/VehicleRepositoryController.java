/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thrifty.rental.management.system.Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import thrifty.rental.management.system.Model.DatabaseUtility;

/**
 *
 * @author Vinay
 */
public class VehicleRepositoryController implements Initializable {

    @FXML
	private TableView<VehicleRepository> propertytable;
	@FXML
	private TableColumn<VehicleRepository,String>propertyVehicleID;
	@FXML
	private TableColumn<VehicleRepository,String>propertyMake;
	@FXML
	private TableColumn<VehicleRepository,String>propertyModel;
	@FXML
	private TableColumn<VehicleRepository,String>propertyYear;
	@FXML
	private TableColumn<VehicleRepository,String>propertyRentalStatus;
	@FXML
	private TableColumn<VehicleRepository,String>propertyVehicleType;
	@FXML
	private TableColumn<VehicleRepository,String>propertyNumberOfSeats;
        @FXML
	private TableColumn<VehicleRepository,String>propertyLastmanintenanceDate;
	@FXML
	private TableColumn<VehicleRepository,String>propertyImage;
	
	private ObservableList<VehicleRepository> data;
        public void Loaddata(ActionEvent event) throws Exception {
		
		
        try {
        	
           Connection conn = DatabaseUtility.ConnenctionManager();
           this.data=FXCollections.observableArrayList();
           
           ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM \"PUBLIC\".\"VEHICLES_TBL\"");
           while (rs.next()) {
             this.data.add(new VehicleRepository(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),rs.getString(9),rs.getString(10)));
          
           }
        }catch(Exception localException) {
			
		}
        this.propertyVehicleID.setCellValueFactory(new PropertyValueFactory("vehicleID"));
        this.propertyMake.setCellValueFactory(new PropertyValueFactory("make"));
        this.propertyYear.setCellValueFactory(new PropertyValueFactory("year"));
        this.propertyModel.setCellValueFactory(new PropertyValueFactory("model"));
        this.propertyVehicleType.setCellValueFactory(new PropertyValueFactory("vehicletype"));
        this.propertyRentalStatus.setCellValueFactory(new PropertyValueFactory("rentalstatus"));
        this.propertyNumberOfSeats.setCellValueFactory(new PropertyValueFactory("noofseats"));
        this.propertyLastmanintenanceDate.setCellValueFactory(new PropertyValueFactory("lastmaintenancedate"));
        
        this.propertytable.setItems(null);
         this.propertytable.setItems(this.data);
        }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
