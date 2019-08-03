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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType; 
import thrifty.rental.management.system.Model.Car;
import thrifty.rental.management.system.Model.DateTime;
import thrifty.rental.management.system.Model.ThriftyRentSystem;
import thrifty.rental.management.system.Model.Van;
import thrifty.rental.management.system.Model.Vehicle;
import thrifty.rental.management.system.Model.VehicleType;

/**
 * FXML Controller class
 *
 * @author Vinay
 */
public class AddVehicleController implements Initializable {
       @FXML
    private ChoiceBox<String> vehicleType;

    @FXML
    private TextField vehicleID;

    @FXML
    private TextField year;

    @FXML
    private TextField make;

    @FXML
    private TextField model;

    @FXML
    private TextField numberOfSeats;

    @FXML
    private DatePicker lastMaintenanceDate;

    @FXML
    private Button addVehicle;
    @FXML
    private Label error;
    @FXML
    private ComboBox comboBox;
    @FXML
    private ComboBox comboBoxYear;
      @FXML
    private Label numsts;

    @FXML
    private Label lstmn;
    @FXML
public void onAddVehicleClick(MouseEvent event)throws IOException
{  Vehicle newVehicle;
        DateTime dateValue;
        String dateSplit[];
        if(model.getText().isEmpty())
        {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
           alert.setTitle("Input fields empty");
           alert.setContentText("Please fill all input fields");
           alert.showAndWait();
        }
        if(comboBox.getValue().toString().equalsIgnoreCase("car")){
  if (!(numberOfSeats.getText().equalsIgnoreCase("4")||numberOfSeats.getText().equalsIgnoreCase("7")))
  {
       javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
           alert.setTitle("Input was wrong");
           alert.setContentText("Please fill either 4 or 7 as seats");
           alert.showAndWait();
  }}
       if ((comboBoxYear.getSelectionModel().isEmpty()))
  {
       javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
           alert.setTitle("Input field empty");
           alert.setContentText("Please select a year");
           alert.showAndWait();
  }
   if ((comboBox.getSelectionModel().isEmpty()))
  {
       javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
           alert.setTitle("Input field empty");
           alert.setContentText("Please select a vehicle type");
           alert.showAndWait();
  }
  if ((make.getText().isEmpty()))
  {
       javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
           alert.setTitle("Input field empty");
           alert.setContentText("Please select a vehicle type");
           alert.showAndWait();
  }
  if ((model.getText().isEmpty()))
  {
       javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
           alert.setTitle("Input field empty");
           alert.setContentText("Please select a vehicle type");
           alert.showAndWait();
  }
//  else if(vehicleType.getValue()==null)
//          {
//                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
//                alert.setTitle("Input fields empty");
//                alert.setContentText("Please fill vehicle Type");
//                alert.showAndWait();
//          }
  if(!(vehicleID.getText().toLowerCase().contains("c_")||vehicleID.getText().toLowerCase().contains("v_")))
          {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
                alert.setTitle("Input field error");
                alert.setContentText("Please fill correct vehicleID");
                alert.showAndWait();
          }
  else{
    ThriftyRentSystem rentSystem=new ThriftyRentSystem();
        
              if(comboBox.getValue().toString().toLowerCase().equalsIgnoreCase("car"))
        {
            newVehicle=(Vehicle)new Car(vehicleID.getText(),Integer.parseInt(comboBoxYear.getValue().toString()),make.getText(),model.getText(),0,new VehicleType(Integer.parseInt(numberOfSeats.getText())),comboBox.getValue().toString());
        }
       else
        {
            
            dateSplit= lastMaintenanceDate.getValue().toString().split("-");
           dateValue= new DateTime(Integer.parseInt(dateSplit[2]),Integer.parseInt(dateSplit[1]),Integer.parseInt(dateSplit[0]));
            newVehicle=new Van(vehicleID.getText(),Integer.parseInt(comboBoxYear.getValue().toString()),make.getText(),model.getText(),0,new VehicleType(15,dateValue),comboBox.getValue().toString());
        }
            rentSystem.add(newVehicle);
  }
}
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        error.setVisible(false);
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue value, String old, String nn) {
                error.setVisible(true);
                if(comboBox.getValue().toString().toLowerCase().equalsIgnoreCase("car"))
      {
          vehicleID.setText("C_");
          lastMaintenanceDate.setVisible(false);
          lstmn.setVisible(false);
          numberOfSeats.setVisible(true);
          numsts.setVisible(true);
      }
      else
      {
          vehicleID.setText("V_");
          lastMaintenanceDate.setVisible(true);
          lstmn.setVisible(true);
          numberOfSeats.setVisible(false);
          numsts.setVisible(false);
      }
            }    
        });
        List<String> listVehicletype = new ArrayList<String>();
        listVehicletype.add("Car");
        listVehicletype.add("Van");
        ObservableList obList = FXCollections.observableList(listVehicletype);
        comboBox.requestFocus();
        comboBox.getItems().clear();
        comboBox.setItems(obList);
        List<String> listYear = new ArrayList<String>();
        for(int i=1990;i<=2019;i++)
            listYear.add(""+i);
        
        
        ObservableList obyearList = FXCollections.observableList(listYear);
        comboBoxYear.getItems().clear();
        comboBoxYear.setItems(obyearList);
        
      
vehicleID.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        if (!newValue) { //when focus lost
            if(!(vehicleID.getText().toLowerCase().contains("c_")||vehicleID.getText().toLowerCase().contains("v_"))){
                //when it not matches the pattern (1.0 - 6.0)
                //set the textField empty
                error.setVisible(true);
                error.setText("Please enter a valid vehicleID");
                vehicleID.setText("");
            }
            else
            error.setText("");
            
        }

    });

numberOfSeats.focusedProperty().addListener((arg0, oldValue, newValue) -> {
        if (!newValue) { //when focus lost
            if(!(numberOfSeats.getText().equals("4")||numberOfSeats.getText().equals("7"))){
                //when it not matches the pattern (1.0 - 6.0)
                //set the textField empty
                error.setVisible(true);
                error.setText("There are cars with only 4 or 7 seats");
                numberOfSeats.setText("");
            }
            else
            error.setText("");
        }

    });

   // addVehicle.setOnAction(action -> {onAddVehicleClick(MouseEvent e)});
        
    }    
    
}
