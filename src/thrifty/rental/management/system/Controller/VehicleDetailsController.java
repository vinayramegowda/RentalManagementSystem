
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thrifty.rental.management.system.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import thrifty.rental.management.system.Model.DatabaseUtility;
import thrifty.rental.management.system.Model.DateTime;
import thrifty.rental.management.system.Model.RentalRecord;
import thrifty.rental.management.system.Model.Vehicle;
import thrifty.rental.management.system.Model.ThriftyRentSystem;

/**
 * FXML Controller class
 *
 * @author Vinay
 */
public class VehicleDetailsController implements Initializable {

    private Vehicle vehicleDetails;
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label make;

    @FXML
    private Label model;

    @FXML
    private Label year;

    @FXML
    private Label vehicleid;

    @FXML
    private Label classification;

    @FXML
    private Label isundermaintenance;

    @FXML
    private Label lastMaintenance;

    @FXML
    private Label rentStatus;

    @FXML
    private Label noOfSeats;
    private ObservableList<RentalRecord> data = FXCollections.observableArrayList();
    @FXML
    private TableView<RentalRecord> tableView;
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
    protected List<RentalRecord> rentalArrayList = new ArrayList<>();
    String onClickedValue = "";

    @FXML
    private Button rentVehicle;
    @FXML
    private Button refresh;
    @FXML
    private Button returnVehicle;

    @FXML
    private Button maintenance;
    @FXML
    private Button readDate;
    @FXML
    private DatePicker datePick;
    DateTime dateValue;
    @FXML
    private Button completemaintenace;
    String dateSplit[];

    public void OnDatePickedValue(LocalDate datePicked) {
        //dateValue=datePicked.toString();
        dateSplit = datePicked.toString().split("-");
        dateValue = new DateTime(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]));
        System.out.println("vvvv" + dateValue.toString());
    }

    public void OnClickedValue(Vehicle vehicleDetails) throws IOException {
        this.vehicleDetails = vehicleDetails;
        this.onClickedValue = vehicleDetails.getVehicleId();
        vehicleid.setText(vehicleDetails.getVehicleId());
        make.setText(vehicleDetails.getMake());
        model.setText(vehicleDetails.getModel());
        year.setText("" + vehicleDetails.getYear());
        classification.setText(vehicleDetails.getVehicleClassification());
        isundermaintenance.setText("");

        switch (vehicleDetails.getStatus()) {
            case 0:
                rentStatus.setText("Available");
                break;
            case 1:
                rentStatus.setText("Rented");
                break;
            case 2:
                rentStatus.setText("Maintenance");
                break;
        }
        lastMaintenance.setText("" + vehicleDetails.getVehicleType().getLastMaintenance());
        noOfSeats.setText("" + vehicleDetails.getVehicleType().getSeats(vehicleDetails.getVehicleClassification().toLowerCase()));

    }

    @FXML
    public void OnReturnButtonClick(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/thrifty/rental/management/system/View/ReturnVehicle.fxml"));
        Parent root = (Parent) loader.load();
        ReturnVehicleController returnVehicleContoller = loader.<ReturnVehicleController>getController();
        returnVehicleContoller.getVehicleDetails(this.vehicleDetails);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Return Vehicle");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void OnCompleteButtonClick(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/thrifty/rental/management/system/View/CompleteMaintenance.fxml"));
        Parent root = (Parent) loader.load();
        CompleteMaintenanceController returnVehicleContoller = loader.<CompleteMaintenanceController>getController();
        returnVehicleContoller.getVehicleDetails(this.vehicleDetails);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Return Vehicle");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void OnMaintenanceButtonClick(MouseEvent event) throws IOException {
        ThriftyRentSystem rentSyatem = new ThriftyRentSystem();
        rentSyatem.vehicleMaintenance(this.vehicleDetails);
    }

    @FXML
    public void OnRefreshButtonClick(MouseEvent event) throws IOException {
        data.clear();
        data = FXCollections.observableList(DatabaseUtility.ReadVehicleRentalRecord(onClickedValue));
        tableView.setItems(data);
    }

    public void setStudentData(ObservableList<RentalRecord> data) {
        this.data = data;
    }

    @FXML
    private void handleDatePickAction(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/thrifty/rental/management/system/View/DatePickerFXML.fxml"));

        Parent root = (Parent) loader.load();
        DatePickerFXMLController controller = loader.<DatePickerFXMLController>getController();
        controller.OnDatePickedClicked(vehicleDetails);

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("Pick your date");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableList(DatabaseUtility.ReadVehicleRentalRecord(onClickedValue));
        Platform.runLater(() -> {

            //do stuff
            // assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'UserMaster.fxml'.";
            recordID.setCellValueFactory(
                    new PropertyValueFactory<RentalRepository, String>("RentId"));
            rentDate.setCellValueFactory(
                    new PropertyValueFactory<RentalRepository, String>("RentDate"));
            estimatedReturnDate.setCellValueFactory(
                    new PropertyValueFactory<RentalRepository, String>("EstimatedReturnDate"));
            actualReturnDate.setCellValueFactory(
                    new PropertyValueFactory<RentalRepository, String>("ActualReturnDate"));
            rentalFee.setCellValueFactory(
                    new PropertyValueFactory<RentalRepository, String>("RentalFee"));
            lateFee.setCellValueFactory(
                    new PropertyValueFactory<RentalRepository, String>("LateFee"));
            data = FXCollections.observableList(DatabaseUtility.ReadVehicleRentalRecord(onClickedValue));

            tableView.setItems(data);

        });
    }

}
