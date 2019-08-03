/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thrifty.rental.management.system.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import thrifty.rental.management.system.Model.DatabaseUtility;
import thrifty.rental.management.system.Model.RentalRecord;
import thrifty.rental.management.system.Model.ThriftyRentSystem;
import thrifty.rental.management.system.Model.Vehicle;

/**
 * FXML Controller class
 *
 * @author Vinay
 */
public class ReturnVehicleController implements Initializable {
 @FXML
    private DatePicker returnDate;
Vehicle vDetails;
    @FXML
    private Button retunVehicle;
    private List<Vehicle> vehicleDetailsList=new ArrayList<>();
    private List<RentalRecord> rentalRecordList=new ArrayList<>();
    
    /**
     * Initializes the controller class.
     */
    public void onReturnButtton(MouseEvent event) throws IOException{
          if (returnDate.getValue() == null) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input field blank");
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
        }
          else
          {
              ThriftyRentSystem rentalSystem=new ThriftyRentSystem();
              
         rentalSystem.returnCar(vDetails,returnDate.getValue().toString());
//              FXMLLoader loader = new FXMLLoader(getClass().getResource("VehicleDetails.fxml"));
//              Parent root = (Parent)loader.load();
//    VehicleDetailsController controller = loader.<VehicleDetailsController>getController();
    //controller.OnClickedValue(vehicleDetails);
              
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
    

