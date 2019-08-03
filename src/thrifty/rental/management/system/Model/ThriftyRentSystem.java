package thrifty.rental.management.system.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import thrifty.rental.management.system.Controller.RentalRepository;
import thrifty.rental.management.system.Controller.VehicleRepository;

/**
 * This the main class used to get details from the user and display the menu
 * driven options This class acts as the business layer in our applications
 */
public class ThriftyRentSystem {
    //cars and vans are two collections used to store cars of type Cars and vans of type Vans respectively

    private Car cars[] = new Car[50];
    private Van vans[] = new Van[50];

    public static DateFormat format = new SimpleDateFormat("dd/MM/yyyy"); //Basic format expected from the User

    /**
     * This the method called from main method this contains the menu driven
     * interface to communicate with the user
     */
    public void run() {

    }

    Vehicle vehicleDetails;
    List<Vehicle> vehicleArrayList = new ArrayList<>();
    List<RentalRecord> rentalRecordArrayList = new ArrayList<>();
    Iterator vehicleIterator;
    Iterator rentalRecordIterator;

    /**
     * Used to add either cars or vans to the list
     *
     * @param //Scanner variable
     * @return adds either car or van if the details are correct
     */
    public void add(Vehicle vehicle) {
      
        int i = 0;
        VehicleRepository repository;
        String vehicleID = "";
        int seats = 0;
        String maintenanceDate = null;
        String vehicleType = "";

        if (vehicle.getVehicleId().toLowerCase().contains("c_")) {
            vehicleType = "car";
        } else {
            vehicleType = "van";
        }

        System.out.print("Year: ");
        int year = vehicle.getYear();

        vehicleID = vehicle.getVehicleId();
        String make = vehicle.getMake();
        String model = vehicle.getModel();
        vehicleArrayList = DatabaseUtility.ReadAllVehicles();

        if (vehicle.getVehicleId().toLowerCase().contains("c_")) {
            seats = vehicle.vehicleType.getCarSeats();
            Vehicle newVehicle = new Car(vehicleID, year, make, model, 0, new VehicleType(seats));
            this.cars[i] = (Car) newVehicle;
            System.out.println(newVehicle.toString());

            repository = new VehicleRepository(vehicleID, "" + year, make, model, "available", vehicleType, "" + seats, null, null, "available");

        } else {

            seats = 15;
            Vehicle newVehicle = new Van(vehicleID, year, make, model, 0, new VehicleType(seats, vehicle.vehicleType.getLastMaintenance()), vehicleType);
            this.vans[i] = (Van) newVehicle;
            System.out.println(newVehicle.toString());
            maintenanceDate = vehicle.vehicleType.getLastMaintenance().toString();
            repository = new VehicleRepository(vehicleID, "" + year, make, model, "available", vehicleType, "" + seats, maintenanceDate, null, "available");

        }
        if (vehicleArrayList.equals(vehicle)) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setContentText("The car with ID : " + vehicle.getVehicleId() + " is already either rented or under maintenance, please choose another car.");
            alert.showAndWait();
        } else {
            DatabaseUtility.InsertVehicles(repository);
        }
    }

    public void LoadDataToArray() {
        vehicleArrayList = DatabaseUtility.ReadAllVehicles();

        int carsIndex = 0, vansIndex = 0;
        // ArrayList to Array Conversion 
        for (int j = 0; j < vehicleArrayList.size(); j++) {

            if (vehicleArrayList.get(j).getVehicleClassification().equalsIgnoreCase("van")) {

                Vehicle newvehicle = new Van(vehicleArrayList.get(j).getVehicleId(), vehicleArrayList.get(j).getYear(), vehicleArrayList.get(j).getMake(), vehicleArrayList.get(j).getModel(), vehicleArrayList.get(j).getStatus(), new VehicleType(vehicleArrayList.get(j).getVehicleType().getCarSeats(), vehicleArrayList.get(j).getVehicleType().getLastMaintenance()), vehicleArrayList.get(j).getVehicleClassification());

                this.vans[vansIndex] = (Van) newvehicle;
                vansIndex++;
            } else {
                Vehicle newvehicle = new Car(vehicleArrayList.get(j).getVehicleId(), vehicleArrayList.get(j).getYear(), vehicleArrayList.get(j).getMake(), vehicleArrayList.get(j).getModel(), vehicleArrayList.get(j).getStatus(), new VehicleType(vehicleArrayList.get(j).getVehicleType().getCarSeats()));
                this.cars[carsIndex] = (Car) newvehicle;
                carsIndex++;
            }
        }

    }

    /**
     * Used to rent either available car or available van
     *
     * @param //Scanner variable
     * @return Rents a car or van if the details are correct
     */
    String dateSplit[];
    DateTime dateValue;
    RentalRepository repository;
    VehicleRepository vehicleRepository;

    public void rent(Vehicle details, String days, String customerID, String dateOfRent) {
        boolean isRented = false;

        String vehicleType = "";
        if (details.getVehicleId().toLowerCase().contains("c_")) {
            vehicleType = "car";
        } else {
            vehicleType = "van";
        }

        String id = details.getVehicleId();

        LoadDataToArray();
        if (this.cars[0] != null && id.contains("C_")) {
            boolean flag = false;
            for (int i = 0; this.cars[i] != null; i++) {
                if ((this.cars[i].getVehicleId()).equals(id)) {
                    if (this.cars[i].vehicleStatus != 0) {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("ERROR");
                        alert.setContentText("The car with ID : " + id + " is already either rented or under maintenance, please choose another car.");
                        alert.showAndWait();

                    }

                    vehicleType = "car";
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setContentText("ID is incorrect, please try again!");
                alert.showAndWait();

            }
        }
        if (this.vans[0] != null && id.contains("V_")) {
            boolean flag = false;
            for (int i = 0; this.vans[i] != null; i++) {
                if ((this.vans[i].getVehicleId()).equals(id)) {
                    if (this.vans[i].vehicleStatus != 0) {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("ERROR");
                        alert.setContentText("The van with ID : " + id + " is already either rented or under maintenance. \nPlease choose another van.");
                        alert.showAndWait();
//						
                    }
                    vehicleType = "van";
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setContentText("ID is incorrect, please try again!");
                alert.showAndWait();
            }
        }
        if (!(id.contains("V_") || id.contains("C_"))) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setContentText("Please Enter a Valid ID either starting from 'V_' or 'C_'.");
            alert.showAndWait();
        }

        String cusId = customerID;

        String date = dateOfRent;
        format.setLenient(false);
        while (date.trim().length() != ((SimpleDateFormat) format).toPattern().length()) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setContentText("Please enter a valid date in the format dd/mm/yyyy: ");
            alert.showAndWait();
        }
        dateSplit = dateOfRent.toString().split("-");
        dateValue = new DateTime(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]));
        //System.out.print("How many days?: ");
        String vehicleStatus = "";
        int numberOfDays = Integer.parseInt(days);
        if (vehicleType.equals("car")) {
            for (int i = 0; this.cars[0] != null; i++) {
                if ((this.cars[i].getVehicleId()).equals(id)) {
                    repository = this.cars[i].rent(cusId, dateValue, numberOfDays);
                    if (repository != null) {
                        switch (this.cars[i].getStatus()) {
                            case 0:
                                vehicleStatus += "Available";
                                break;
                            case 1:
                                vehicleStatus += "Rented";
                                break;
                            case 2:
                                vehicleStatus += "Maintenance";
                                break;
                        }
                        vehicleRepository = new VehicleRepository(this.cars[i].getVehicleId(), "" + this.cars[i].getYear(), this.cars[i].getMake(), this.cars[i].getModel(), vehicleStatus, vehicleType, "" + this.cars[i].getVehicleType().getCarSeats(), null, null, "available");
                        DatabaseUtility.UpdateVehicles(vehicleRepository);
                        DatabaseUtility.InsertRentalRecords(repository);
                        isRented = true;
                        break;
                    } else {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("ERROR");
                        alert.setContentText("Vehicle " + id + " could not be rented.");
                        alert.showAndWait();

                    }
                }
            }

            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Vehicle " + id + " is now rented by customer " + cusId);
            alert.showAndWait();
        }

        if (vehicleType.equals("van")) {
            for (int i = 0; this.vans[i] != null; i++) {
                if ((this.vans[i].getVehicleId()).equals(id)) {
                    repository = this.vans[i].rent(cusId, dateValue, numberOfDays);

                    if (repository != null) {
                        switch (this.vans[i].getStatus()) {
                            case 0:
                                vehicleStatus += "Available";
                                break;
                            case 1:
                                vehicleStatus += "Rented";
                                break;
                            case 2:
                                vehicleStatus += "Maintenance";
                                break;
                        }
                        String maintenanceDate = this.vans[i].vehicleType.getLastMaintenance().toString();

                        vehicleRepository = new VehicleRepository(this.vans[i].getVehicleId(), "" + this.vans[i].getYear(), this.vans[i].getMake(), this.vans[i].getModel(), vehicleStatus, vehicleType, "" + this.vans[i].getVehicleType().getSeats(vehicleType), maintenanceDate, null, "available");
                        DatabaseUtility.UpdateVehicles(vehicleRepository);

                        DatabaseUtility.InsertRentalRecords(repository);
                        isRented = true;
                        break;

                    } else {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("ERROR");
                        alert.setContentText("Vehicle " + id + " could not be rented");
                        alert.showAndWait();
                    }
                }
            }
        }
        if (isRented) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setContentText("Vehicle " + id + " is now rented by customer " + cusId);
            alert.showAndWait();
        }
    }

    /**
     * Used to return a rented car or van
     *
     * @param //Scanner variable
     * @return prints the details of the car along with rental fee and charges
     * if it is returned late
     */
    public void returnCar(Vehicle vehicleDetails, String returnDate) {
        RentalRepository rentRepository;
        Scanner sc = null;
        String vehicleStatus = "";
        boolean isRented = false;
        String id = vehicleDetails.getVehicleId();
        LoadDataToArray();
        rentalRecordArrayList = DatabaseUtility.ReadVehicleRentalRecord(id);
        String vehicleType = "";

        if (this.cars[0] != null && id.contains("C_")) {
            vehicleType = "car";

            boolean flag = false;
            for (int i = 0; this.cars[i] != null; i++) {

                if ((this.cars[i].getVehicleId()).equals(id)) {

                    String date = returnDate;
                    String dates[] = date.split("-");
                    DateTime dateValue = new DateTime(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0]));
                    rentRepository = this.cars[i].returnVehicle(dateValue);

                    if (rentRepository != null) {
                        switch (this.cars[i].getStatus()) {
                            case 0:
                                vehicleStatus += "Available";
                                break;
                            case 1:
                                vehicleStatus += "Rented";
                                break;
                            case 2:
                                vehicleStatus += "Maintenance";
                                break;
                        }
                        vehicleRepository = new VehicleRepository(this.cars[i].getVehicleId(), "" + this.cars[i].getYear(), this.cars[i].getMake(), this.cars[i].getModel(), vehicleStatus, vehicleType, "" + this.cars[i].getVehicleType().getCarSeats(), null, null, "available");
                        DatabaseUtility.UpdateVehicles(vehicleRepository);
                        DatabaseUtility.UpdateRentalRecord(rentRepository);
                        isRented = true;
                        //System.out.println(this.cars[i].records[this.cars[i].getLastElementIndex()].getDetails());
                    } else {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert");
                        alert.setContentText("Vehicle cannot be returned as it may have been never rented");
                        alert.showAndWait();

                    }
                    flag = true;
                    break;
                }

            }
            if (!flag) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setContentText("IncorrectID");
                alert.showAndWait();
            }
        }
        if (this.vans[0] != null && id.contains("V_")) {
            vehicleType = "van";
            boolean flag = false;
            for (int i = 0; this.vans[i] != null; i++) {
                if ((this.vans[i].getVehicleId()).equals(id)) {

                    String date = returnDate;
                    String dates[] = date.toString().split("-");
                    DateTime dateValue = new DateTime(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0]));
                    rentRepository = this.vans[i].returnVehicle(dateValue);
                    if (rentRepository != null) {
                        switch (this.vans[i].getStatus()) {
                            case 0:
                                vehicleStatus += "Available";
                                break;
                            case 1:
                                vehicleStatus += "Rented";
                                break;
                            case 2:
                                vehicleStatus += "Maintenance";
                                break;
                        }
                        String maintenanceDate = this.vans[i].vehicleType.getLastMaintenance().toString();
                        vehicleRepository = new VehicleRepository(this.vans[i].getVehicleId(), "" + this.vans[i].getYear(), this.vans[i].getMake(), this.vans[i].getModel(), vehicleStatus, vehicleType, "" + this.vans[i].getVehicleType().getSeats(vehicleType), maintenanceDate, null, "available");
                        DatabaseUtility.UpdateVehicles(vehicleRepository);
                        DatabaseUtility.UpdateRentalRecord(rentRepository);
                        isRented = true;

                    } else {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert");
                        alert.setContentText("Vehicle cannot be returned as it may have been never rented");
                        alert.showAndWait();
                    }
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setContentText("IncorrectID");
                alert.showAndWait();
            }
        }
        if (isRented) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setContentText("Vehicle sucessfully Returned");
            alert.showAndWait();
        }
    }

    /**
     * Method used to set either car or van to maintenance
     *
     * @param //Scanner variable
     * @return prints appropriate message if sent for maintenance
     */
    public void vehicleMaintenance(Vehicle vehicleDetails) {
        Scanner sc = null;
        String vehicleStatus = "", vehicleType = "";
        String id = vehicleDetails.getVehicleId();
        LoadDataToArray();
        rentalRecordArrayList = DatabaseUtility.ReadVehicleRentalRecord(id);
        if (this.cars[0] != null && id.contains("C_")) {
            vehicleType = "car";
            boolean flag = false;
            for (int i = 0; this.cars[i] != null; i++) {
                if ((this.cars[i].getVehicleId()).equals(id)) {
                    if (this.cars[i].performMaintenance()) {
                        switch (this.cars[i].getStatus()) {
                            case 0:
                                vehicleStatus += "Available";
                                break;
                            case 1:
                                vehicleStatus += "Rented";
                                break;
                            case 2:
                                vehicleStatus += "Maintenance";
                                break;
                        }
                        vehicleRepository = new VehicleRepository(this.cars[i].getVehicleId(), "" + this.cars[i].getYear(), this.cars[i].getMake(), this.cars[i].getModel(), vehicleStatus, vehicleType, "" + this.cars[i].getVehicleType().getCarSeats(), null, null, "available");
                        DatabaseUtility.UpdateVehicles(vehicleRepository);
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert");
                        alert.setContentText("Vehicle sucessfully sent for maintenance!!");
                        alert.showAndWait();

                    } else {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert");
                        alert.setContentText("Vehicle could not be sent for maintenance!!");
                        alert.showAndWait();

                    }
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setContentText("Vehicle could not be sent for maintenance!!");
                alert.showAndWait();
            }
        }
        if (this.vans[0] != null && id.contains("V_")) {
            boolean flag = false;
            vehicleType = "van";
            for (int i = 0; this.vans[i] != null; i++) {
                if ((this.vans[i].getVehicleId()).equals(id)) {
                    if (this.vans[i].performMaintenance()) {
                        switch (this.vans[i].getStatus()) {
                            case 0:
                                vehicleStatus += "Available";
                                break;
                            case 1:
                                vehicleStatus += "Rented";
                                break;
                            case 2:
                                vehicleStatus += "Maintenance";
                                break;
                        }
                        String maintenanceDate = this.vans[i].vehicleType.getLastMaintenance().toString();
                        vehicleRepository = new VehicleRepository(this.vans[i].getVehicleId(), "" + this.vans[i].getYear(), this.vans[i].getMake(), this.vans[i].getModel(), vehicleStatus, vehicleType, "" + this.vans[i].getVehicleType().getSeats(vehicleType), maintenanceDate, null, "available");
                        DatabaseUtility.UpdateVehicles(vehicleRepository);
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert!!");
                        alert.setContentText("Vehicle sucessfully sent for maintenance!!");
                        alert.showAndWait();

                    } else {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("ERROR!!");
                        alert.setContentText("Vehicle could not be sent for maintenance!!");
                        alert.showAndWait();

                    }
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR!!");
                alert.setContentText("Vehicle could not be sent for maintenance!!");
                alert.showAndWait();
            }
        }
    }

    /**
     * Method used to complete maintenance of either car or van
     *
     * @param //Scanner variable
     * @return prints appropriate message after completing maintenance
     */
    public void completeMaintenance(Vehicle vehicleDetails, String maintenanceDatee) {
        Scanner sc = null;
        String vehicleStatus = "", vehicleType = "";
        String id = vehicleDetails.getVehicleId();
        LoadDataToArray();
        rentalRecordArrayList = DatabaseUtility.ReadVehicleRentalRecord(id);

        if (this.cars[0] != null && id.contains("C_")) {
            vehicleType = "car";
            boolean flag = false;
            for (int i = 0; this.cars[i] != null; i++) {
                if ((this.cars[i].getVehicleId()).equals(id)) {

                    String date = maintenanceDatee;
                    if (this.cars[i].completeMaintenance()) {
                        switch (this.cars[i].getStatus()) {
                            case 0:
                                vehicleStatus += "Available";
                                break;
                            case 1:
                                vehicleStatus += "Rented";
                                break;
                            case 2:
                                vehicleStatus += "Maintenance";
                                break;
                        }
                        vehicleRepository = new VehicleRepository(this.cars[i].getVehicleId(), "" + this.cars[i].getYear(), this.cars[i].getMake(), this.cars[i].getModel(), vehicleStatus, vehicleType, "" + this.cars[i].getVehicleType().getCarSeats(), null, null, "available");
                        DatabaseUtility.UpdateVehicles(vehicleRepository);
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert!!");
                        alert.setContentText("Vehicle is serviced and is now available for rent!");
                        alert.showAndWait();
                    } else {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("ERROR!!");
                        alert.setContentText("Vehicle could not complete maintenance");
                        alert.showAndWait();
                        return;
                    }
                    flag = true;
                    break;
                }
            }

        }
        if (this.vans[0] != null && id.contains("V_")) {
            boolean flag = false;
            vehicleType = "van";
            for (int i = 0; this.vans[i] != null; i++) {
                if ((this.vans[i].getVehicleId()).equals(id)) {

                    String date = maintenanceDatee;
                    String dates[] = date.split("-");
                    DateTime dateValue = new DateTime(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0]));
                    if (this.vans[i].completeMaintenance(dateValue)) {
                        switch (this.vans[i].getStatus()) {
                            case 0:
                                vehicleStatus += "Available";
                                break;
                            case 1:
                                vehicleStatus += "Rented";
                                break;
                            case 2:
                                vehicleStatus += "Maintenance";
                                break;
                        }
                        String maintenanceDate = this.vans[i].vehicleType.getLastMaintenance().toString();
                        vehicleRepository = new VehicleRepository(this.vans[i].getVehicleId(), "" + this.vans[i].getYear(), this.vans[i].getMake(), this.vans[i].getModel(), vehicleStatus, vehicleType, "" + this.vans[i].getVehicleType().getSeats(vehicleType), maintenanceDate, null, "available");
                        DatabaseUtility.UpdateVehicles(vehicleRepository);
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert!!");
                        alert.setContentText("Vehicle has now finished maintenance and is available for rent!");
                        alert.showAndWait();
                    } else {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("ERROR!!");
                        alert.setContentText("Vehicle could not complete its maintenance!");
                        alert.showAndWait();

                    }
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Alert!!");
                        alert.setContentText("ID is incorrect");
                        alert.showAndWait();
            }
        }
    }

    /**
     * Method used to get details of car or van with their rental history
     */
    private void getDetails() {
        if (cars[0] != null && vans[0] != null) {
            System.out.println("There are no cars or vans to display, please enter some vehicles and try again");
            return;
        }
        if (cars[0] != null) {
            System.out.println("***********Cars**********");
            for (int i = 0; this.cars[i] != null; i++) {
                System.out.println(this.cars[i].getDetails());
            }
        }
        if (vans[0] != null) {
            System.out.println("***********Vans**********");
            for (int i = 0; this.vans[i] != null; i++) {
                System.out.println(this.vans[i].getDetails());
            }
        }
    }
}
