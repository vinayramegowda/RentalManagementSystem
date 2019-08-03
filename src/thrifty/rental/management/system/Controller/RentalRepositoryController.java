/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thrifty.rental.management.system.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import thrifty.rental.management.system.Model.RentalRecord;

/**
 *
 * @author Vinay
 */
public class RentalRepositoryController implements Initializable{
    @FXML private TableView tableView;
@FXML 
private TableColumn<RentalRepository, String> recordID;
@FXML 
private TableColumn<RentalRepository, String> rentDate;
@FXML 
private TableColumn<RentalRepository, String> estimatedReturnDate;
@FXML 
private TableColumn<RentalRepository, String> actualReturnDate;
@FXML 
private TableColumn<RentalRepository, String> rentalFee;
@FXML 
private TableColumn<RentalRepository, String> lateFee;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert tableView != null : "fx:id=\"tableview\" was not injected: check your FXML file 'UserMaster.fxml'.";
     recordID.setCellValueFactory(
        new PropertyValueFactory<RentalRepository,String>("recordid")); 
     rentDate.setCellValueFactory(
        new PropertyValueFactory<RentalRepository,String>("rentdate"));   
     estimatedReturnDate.setCellValueFactory(
        new PropertyValueFactory<RentalRepository,String>("estimatedreturndate"));   
    actualReturnDate.setCellValueFactory(                
        new PropertyValueFactory<RentalRepository,String>("actualreturndate"));
    rentalFee.setCellValueFactory(
        new PropertyValueFactory<RentalRepository,String>("rentalfee"));        
    lateFee.setCellValueFactory(
        new PropertyValueFactory<RentalRepository,String>("latefee"));
    
   
}
    
}
