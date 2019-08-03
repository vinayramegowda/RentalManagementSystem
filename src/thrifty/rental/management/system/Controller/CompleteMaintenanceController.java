/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thrifty.rental.management.system.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import thrifty.rental.management.system.Model.ThriftyRentSystem;
import thrifty.rental.management.system.Model.Vehicle;

/**
 * FXML Controller class
 *
 * @author Vinay
 */
public class CompleteMaintenanceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private DatePicker maintenanceDate;

    @FXML
    private Button completeMaintenance;
    Vehicle vDetails;
    public void onCompleteButton(MouseEvent event) throws IOException{
          if (maintenanceDate.getValue() == null) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input field blank");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
        }
          else
          {
              ThriftyRentSystem rentalSystem=new ThriftyRentSystem();
              rentalSystem.completeMaintenance(vDetails,maintenanceDate.getValue().toString());
              
                
              
       
          }
    }
     public void getVehicleDetails(Vehicle vehicleDetails) throws IOException{
         this.vDetails=vehicleDetails;
         
         
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
