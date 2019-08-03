/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thrifty.rental.management.system.Controller;
//package thrifty.rental.management.system.Model.VehicleType;

import thrifty.rental.management.system.View.VehicleListViewCell;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import thrifty.rental.management.system.Model.DatabaseUtility;
import thrifty.rental.management.system.Model.DateTime;
import thrifty.rental.management.system.Model.Vehicle;
import thrifty.rental.management.system.Model.VehicleType;

/**
 *
 * @author Vinay
 */
public class FXMLDocumentController implements Initializable {

//  @FXML
//  private List<Vehicle> vehicles;
    @FXML
    private ListView listView;

    @FXML
    private ComboBox vehicleType;

    @FXML
    private ComboBox status;

    @FXML
    private ComboBox getnumberOfSeats;

    @FXML
    private ComboBox vehicleMake;
    private String onClickedVehicleID;
    protected ListProperty<Vehicle> listProperty = new SimpleListProperty<>();
    private ObservableList<Vehicle> vehicleObservableList = FXCollections.observableArrayList();
    protected List<Vehicle> vehicleArrayList = new ArrayList<>();
    protected List<Vehicle> vehicleTypeArrayList = new ArrayList<>();
    protected List<Vehicle> statusArrayList = new ArrayList<>();
    protected List<Vehicle> seatsArrayList = new ArrayList<>();

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        VehicleListViewCell.updateImage();
    }

    //to re fresh the data in the list in the main window
    @FXML
    private void onRefresh(MouseEvent event) throws Exception {
        vehicleObservableList.clear();
        vehicleArrayList = DatabaseUtility.ReadAllVehicles();
        vehicleObservableList = FXCollections.observableArrayList(vehicleArrayList);
        listView.itemsProperty().bind(listProperty);
        listProperty.set(vehicleObservableList);
        vehicleType.selectionModelProperty().setValue(null);
        
    }

    //To open new window when add vehicle is pressed on the main window
    @FXML
    private void handleHyperlinkAction(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/thrifty/rental/management/system/View/AddVehicle.fxml"));

        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Add Vehicle details");
        stage.setScene(scene);
        stage.show();
    }
    Vehicle selectedVehicle;

    //to handle List load when pressed
    @FXML
    public void handleListAction(MouseEvent event) throws IOException {
        this.selectedVehicle = (Vehicle) listView.getSelectionModel().selectedItemProperty().getValue();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/thrifty/rental/management/system/View/VehicleDetails.fxml"));
        vehicleArrayList = DatabaseUtility.ReadAllVehicles();

        Parent root = (Parent) loader.load();
        listView.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(vehicleArrayList));
        VehicleDetailsController controller = loader.<VehicleDetailsController>getController();
        controller.OnClickedValue(selectedVehicle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        System.out.println(selectedVehicle.getVehicleId());
        stage.setTitle("Vehicle details");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        vehicleArrayList = DatabaseUtility.ReadAllVehicles();
        vehicleObservableList = FXCollections.observableArrayList(vehicleArrayList);
        listView.itemsProperty().bind(listProperty);
        listProperty.set(vehicleObservableList);
        listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
            @Override
            public ListCell<Vehicle> call(ListView<Vehicle> vehicleListView) {
                return new VehicleListViewCell();
            }
        });
        List<String> listVehicletype = new ArrayList<String>();
        listVehicletype.add("Car");
        listVehicletype.add("Van");
        ObservableList obList = FXCollections.observableList(listVehicletype);
        vehicleType.requestFocus();
        vehicleType.getItems().clear();
        vehicleType.setItems(obList);

        List<String> listStatustype = new ArrayList<String>();
      
        
        listStatustype.add("Available");
        listStatustype.add("Rented");
        listStatustype.add("UnderMaintenance");
        ObservableList statusobList = FXCollections.observableList(listStatustype);
        status.requestFocus();
        status.getItems().clear();
        status.setItems(statusobList);
        
        List<String> listmaketype = new ArrayList<String>();
        List<String> nlistmaketype = new ArrayList<String>();
        for(Vehicle vehicle:vehicleArrayList)
        {
            listmaketype.add(vehicle.getMake());
        }
        nlistmaketype=listmaketype.stream().distinct().collect(Collectors.toList());
//        listmaketype.add("Honda");
//        listmaketype.add("Lamborghini");
//        listStatustype.add("Lexus");
//        listStatustype.add("Mustang");
//        listStatustype.add("BMW");
//        listStatustype.add("Audi");
        ObservableList makeobList = FXCollections.observableList(nlistmaketype);
        vehicleMake.requestFocus();
        vehicleMake.getItems().clear();
        vehicleMake.setItems(makeobList);
        
        List<String> listSeatstype = new ArrayList<String>();
        listSeatstype.add("4");
        listSeatstype.add("7");
        listSeatstype.add("15");
        ObservableList seatsobList = FXCollections.observableList(listSeatstype);
        getnumberOfSeats.requestFocus();
        getnumberOfSeats.getItems().clear();
        getnumberOfSeats.setItems(seatsobList);
        //change list on change of vehicle type
        vehicleType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue value, String old, String nn) {

                if (vehicleType.getValue().toString().toLowerCase().equalsIgnoreCase("car")) {
                    vehicleTypeArrayList.clear();
                    vehicleObservableList = FXCollections.observableArrayList(vehicleArrayList);
                    for (Vehicle vehicle : vehicleArrayList) {
                        if (vehicle.getVehicleClassification().equalsIgnoreCase("car")) {
                            vehicleTypeArrayList.add(vehicle);
                        }
                    }
                    vehicleObservableList = FXCollections.observableArrayList(vehicleTypeArrayList);
                    listView.itemsProperty().bind(listProperty);
                    listProperty.set(vehicleObservableList);
                    listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
                        @Override
                        public ListCell<Vehicle> call(ListView<Vehicle> vehicleListView) {
                            return new VehicleListViewCell();
                        }
                    });
                }
                if (vehicleType.getValue().toString().toLowerCase().equalsIgnoreCase("van")) {
                    vehicleTypeArrayList.clear();
                    vehicleObservableList = FXCollections.observableArrayList(vehicleArrayList);
                    for (Vehicle vehicle : vehicleArrayList) {
                        if (vehicle.getVehicleClassification().equalsIgnoreCase("van")) {
                            vehicleTypeArrayList.add(vehicle);
                        }
                    }
                    vehicleObservableList = FXCollections.observableArrayList(vehicleTypeArrayList);
                    listView.itemsProperty().bind(listProperty);
                    listProperty.set(vehicleObservableList);
                    listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
                        @Override
                        public ListCell<Vehicle> call(ListView<Vehicle> vehicleListView) {
                            return new VehicleListViewCell();
                        }
                    });
                }
            }
        });
        
        //change on change of number of seats
         getnumberOfSeats.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue value, String old, String nn) {

                if (getnumberOfSeats.getValue().toString().toLowerCase().equalsIgnoreCase("4")) {
                    seatsArrayList.clear();
                    vehicleObservableList = FXCollections.observableArrayList(vehicleArrayList);
                    
                    for (Vehicle vehicle : vehicleArrayList) {
                        String a=""+vehicle.getVehicleType().getSeats("car");
                        if (a.toString().equalsIgnoreCase("4")) {
                            seatsArrayList.add(vehicle);
                        }
                    }
                    vehicleObservableList = FXCollections.observableArrayList(seatsArrayList);
                    listView.itemsProperty().bind(listProperty);
                    listProperty.set(vehicleObservableList);
                    listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
                        @Override
                        public ListCell<Vehicle> call(ListView<Vehicle> vehicleListView) {
                            return new VehicleListViewCell();
                        }
                    });
                }
                 if (getnumberOfSeats.getValue().toString().toLowerCase().equalsIgnoreCase("7")) {
                    seatsArrayList.clear();
                    vehicleObservableList = FXCollections.observableArrayList(vehicleArrayList);
                    
                    for (Vehicle vehicle : vehicleArrayList) {
                        String a=""+vehicle.getVehicleType().getSeats("car");
                        if (a.toString().equalsIgnoreCase("7")) {
                            seatsArrayList.add(vehicle);
                        }
                    }
                    vehicleObservableList = FXCollections.observableArrayList(seatsArrayList);
                    listView.itemsProperty().bind(listProperty);
                    listProperty.set(vehicleObservableList);
                    listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
                        @Override
                        public ListCell<Vehicle> call(ListView<Vehicle> vehicleListView) {
                            return new VehicleListViewCell();
                        }
                    });
                }
               if (getnumberOfSeats.getValue().toString().toLowerCase().equalsIgnoreCase("15")) {
                    seatsArrayList.clear();
                    vehicleObservableList = FXCollections.observableArrayList(vehicleArrayList);
                    
                    for (Vehicle vehicle : vehicleArrayList) {
                        String a=""+vehicle.getVehicleType().getVanSeats();
                        if (a.toString().equalsIgnoreCase("15")) {
                            seatsArrayList.add(vehicle);
                        }
                    }
                    vehicleObservableList = FXCollections.observableArrayList(seatsArrayList);
                    listView.itemsProperty().bind(listProperty);
                    listProperty.set(vehicleObservableList);
                    listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
                        @Override
                        public ListCell<Vehicle> call(ListView<Vehicle> vehicleListView) {
                            return new VehicleListViewCell();
                        }
                    });
                }
            }
        });
        
        
        
   //change on change of status change
        status.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue value, String old, String nn) {

                if (status.getValue().toString().toLowerCase().equalsIgnoreCase("available")) {
                    statusArrayList.clear();
                    vehicleObservableList = FXCollections.observableArrayList(vehicleArrayList);
                    for (Vehicle vehicle : vehicleArrayList) {
                        if (vehicle.getStatus() == 0) {
                            statusArrayList.add(vehicle);

                        }
                    }
                    vehicleObservableList = FXCollections.observableArrayList(statusArrayList);
                    listView.itemsProperty().bind(listProperty);
                    listProperty.set(vehicleObservableList);
                    listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
                        @Override
                        public ListCell<Vehicle> call(ListView<Vehicle> vehicleListView) {
                            return new VehicleListViewCell();

                        }
                    });
                }

                if (status.getValue().toString().toLowerCase().equalsIgnoreCase("Rented")) {
                    statusArrayList.clear();
                    vehicleObservableList = FXCollections.observableArrayList(vehicleArrayList);
                    for (Vehicle vehicle : vehicleArrayList) {
                        if (vehicle.getStatus() == 1) {

                            statusArrayList.add(vehicle);

                        }
                    }
                    vehicleObservableList = FXCollections.observableArrayList(statusArrayList);
                    listView.itemsProperty().bind(listProperty);
                    listProperty.set(vehicleObservableList);
                    listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
                        @Override
                        public ListCell<Vehicle> call(ListView<Vehicle> vehicleListView) {
                            return new VehicleListViewCell();
                        }
                    });

                }
                if (status.getValue().toString().toLowerCase().equalsIgnoreCase("undermaintenance")) {
                    
                    statusArrayList.clear();
                    vehicleObservableList = FXCollections.observableArrayList(vehicleArrayList);
                    for (Vehicle vehicle : vehicleArrayList) {
                        if (vehicle.getStatus() == 2) {

                            statusArrayList.add(vehicle);

                        }
                    }
                    vehicleObservableList = FXCollections.observableArrayList(statusArrayList);
                    listView.itemsProperty().bind(listProperty);
                    listProperty.set(vehicleObservableList);
                    listView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
                        @Override
                        public ListCell<Vehicle> call(ListView<Vehicle> vehicleListView) {
                            return new VehicleListViewCell();
                        }
                    });
                }
            }

        });
    }

}
