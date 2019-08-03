package thrifty.rental.management.system.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import thrifty.rental.management.system.Model.ThriftyRentSystem;
import thrifty.rental.management.system.Model.Vehicle;

public class DatePickerFXMLController
        implements Initializable {

    @FXML
    private TextField days;
    @FXML
    private Button readDate;
    @FXML
    private DatePicker datePick;
    @FXML
    private TextField customerID;
    LocalDate value;
    Vehicle vehicleDetails;

    public DatePickerFXMLController() {
    }

    public void OnDatePickedClicked(Vehicle vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    @FXML
    public void onRentButtonClick(MouseEvent event)
            throws IOException {
        ThriftyRentSystem rentSystem = new ThriftyRentSystem();
        if ((datePick.getValue() == null) && (days.getText().isEmpty()) && (customerID.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input field blank");
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else if ((datePick.getValue() == null) || (days.getText().isEmpty()) || (customerID.getText().isEmpty())) {

            if (datePick.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input field blank");
                alert.setContentText("Please select the date to be rented");
                alert.showAndWait();
            } else if (days.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input field blank");
                alert.setContentText("Please fill customer ID field");
                alert.showAndWait();
            } else if (customerID.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input field blank");
                alert.setContentText("Please fill customer ID");
                alert.showAndWait();
            }
        } else {
            rentSystem.rent(vehicleDetails, days.getText(), customerID.getText(), ((LocalDate) datePick.getValue()).toString());
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
    }
}
