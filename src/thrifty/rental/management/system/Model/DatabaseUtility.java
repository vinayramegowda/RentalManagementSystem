package thrifty.rental.management.system.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import javafx.scene.control.Alert;
import thrifty.rental.management.system.Controller.RentalRepository;

import thrifty.rental.management.system.Controller.VehicleRepository;

public class DatabaseUtility {

    String onClickedValue = "";

    public void OnClickedValue(String vehicleID) {
        onClickedValue = vehicleID;

    }

    public static Connection ConnenctionManager() {
        Connection con = null;
        Boolean flag = Boolean.FALSE;
        try {
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/rentalDB", "SA", "");
            if (con != null) {
                flag = Boolean.TRUE;

            } else {
                flag = Boolean.FALSE;
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        Connection result = (flag) ? con : null;
        return result;
    }

    public static List<Vehicle> ReadAllVehicles() {
        List<Vehicle> vehicleArrayList = new ArrayList<>();
        
        String dateSplit[];
        DateTime lastMaintenanceDate = null;
        Connection connection = ConnenctionManager();
         
        try {
            // Create and execute statement
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"PUBLIC\".\"VEHICLES_TBL\"");
            int rentStatus = 0;
           
            while (rs.next()) {
                
                String vehicleID = rs.getString("vehicleid");
                int year = Integer.parseInt(rs.getString("year"));
                String make = rs.getString("make");
                String model = rs.getString("model");
                int numberOfSeats = rs.getInt("numberofseats");

                String rentalStatus = rs.getString("rentalstatus");
                switch (rentalStatus.toLowerCase()) {
                    case "available":
                        rentStatus = 0;
                        break;
                    case "rented":
                        rentStatus = 1;
                        break;
                    case "maintenance":
                        rentStatus = 2;
                        break;
                }
                String maintenanceDate = rs.getString("maintenancedate");
                String vehicleType = rs.getString("vehicletype");
                if (maintenanceDate != null) {
                    dateSplit = maintenanceDate.split("/");
                    lastMaintenanceDate = new DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
                }
                if (vehicleType.toLowerCase().equalsIgnoreCase("car")) {
                    vehicleArrayList.add(new Vehicle(vehicleID, year, make, model, rentStatus, new VehicleType(numberOfSeats), vehicleType));
                } else {
                    vehicleArrayList.add(new Vehicle(vehicleID, year, make, model, rentStatus, new VehicleType(numberOfSeats, lastMaintenanceDate), vehicleType));
                }

            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                // Close connection
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return vehicleArrayList;
    }

    public static List<RentalRecord> ReadVehicleRentalRecord(String vehicleID) {
        //ObservableList<RentalRecord> data = FXCollections.observableArrayList();
        List<RentalRecord> rentalArrayList = new ArrayList<>();
        Double rentalFee, lateFee = 0.0;
        String dateSplit[];
        DateTime rentDate = null;
        DateTime actualReturnDate = null;
        DateTime estimatedReturnDate = null;
        Connection connection = ConnenctionManager();
        
        try {
            // Create and execute statement
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"PUBLIC\".\"RENTALRECORD_TBL\"");
            int rentStatus = 0;
            // Loop through the data and print all artist names
            while (rs.next()) {
                String recordID = rs.getString("recordid");
                if (recordID.substring(0, recordID.indexOf("_", recordID.indexOf("_") + 1)).equalsIgnoreCase(vehicleID)) {
                    String srentDate = rs.getString("rentdate");
                    if (srentDate != null) {
                        dateSplit = srentDate.split("/");
                        rentDate = new DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
                    }
                    String sestimatedReturnDate = rs.getString("estimatedreturndate");
                    if (sestimatedReturnDate != null) {
                        dateSplit = sestimatedReturnDate.split("/");
                        estimatedReturnDate = new DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
                    }
                    String sactualReturnDate = rs.getString("actualreturndate");
                    if ((rs.getString("actualreturndate").equalsIgnoreCase("none"))) {
                        actualReturnDate = null;
                    } else {
                        dateSplit = sactualReturnDate.split("/");
                        actualReturnDate = new DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
                    }
                    if (rs.getString("rentalfee").equalsIgnoreCase("none")) {
                        rentalFee = 0.0;
                    } else {
                        rentalFee = Double.parseDouble(rs.getString("rentalfee"));
                    }
                    if (rs.getString("latefee").equalsIgnoreCase("none")) {
                        lateFee = 0.0;
                    } else {
                        lateFee = Double.parseDouble(rs.getString("latefee"));
                    }
                    rentalArrayList.add(new RentalRecord(recordID, rentDate, estimatedReturnDate, actualReturnDate, rentalFee, lateFee));
                }

            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                // Close connection
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return rentalArrayList;
    }

    public static void InsertVehicles(VehicleRepository vehicleDetails) {
        Connection connection = ConnenctionManager();
        StringBuilder query = new StringBuilder();
        //Vehicle vehiclesList[]=new Vehicle[100]; 
        int result = 0;
        try {

            Statement stmt = connection.createStatement();
            if (vehicleDetails.getLastMaintenanceDate() == null) {
                result = stmt.executeUpdate("INSERT INTO \"PUBLIC\".\"VEHICLES_TBL\"( \"VEHICLEID\", \"YEAR\", \"MAKE\", \"MODEL\", \"NUMBEROFSEATS\", \"VEHICLETYPE\", \"RENTALSTATUS\", \"MAINTENANCESTATUS\", \"MAINTENANCEDATE\", \"COMPLETIONDATE\" )VALUES ('" + vehicleDetails.getVehicleID() + "','" + vehicleDetails.getYear() + "','" + "" + vehicleDetails.getMake() + "','" + vehicleDetails.getModel() + "','" + vehicleDetails.getNumberOfSeats() + "','" + vehicleDetails.getVehicleType() + "','" + vehicleDetails.getStatus() + "','" + "available'" + "," + null + "," + null + ")");
            } else {
                result = stmt.executeUpdate("INSERT INTO \"PUBLIC\".\"VEHICLES_TBL\"( \"VEHICLEID\", \"YEAR\", \"MAKE\", \"MODEL\", \"NUMBEROFSEATS\", \"VEHICLETYPE\", \"RENTALSTATUS\", \"MAINTENANCESTATUS\", \"MAINTENANCEDATE\", \"COMPLETIONDATE\" )VALUES ('" + vehicleDetails.getVehicleID() + "','" + vehicleDetails.getYear() + "','" + "" + vehicleDetails.getMake() + "','" + vehicleDetails.getModel() + "','" + vehicleDetails.getNumberOfSeats() + "','" + vehicleDetails.getVehicleType() + "','" + vehicleDetails.getStatus() + "','" + "available'" + ",'" + vehicleDetails.getLastMaintenanceDate() + "'," + null + ")");
            }

            System.out.print(result + " rows effected");

            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setContentText("Vehicle Sucessfully added!!");
            alert.showAndWait();
            stmt.close();

        } catch (Exception e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vehicle ID Already Present");
            alert.setContentText("Please fill new Vehicle ID as it is already present");
            alert.showAndWait();
            //e.printStackTrace(System.out);
        } finally {
            try {
                // Close connection
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void UpdateVehicles(VehicleRepository vehicleDetails) {
        List<RentalRecord> rentalArrayList = new ArrayList<>();
        Connection connection = ConnenctionManager();
        int result = 0;
        try {
            // Create and execute statement
            Statement stmt = connection.createStatement();
            System.out.println("UPDATE \"PUBLIC\".\"VEHICLES_TBL\" SET RENTALSTATUS='Rented' WHERE VEHICLEID='" + vehicleDetails.getVehicleID() + "'");
            result = stmt.executeUpdate("UPDATE \"PUBLIC\".\"VEHICLES_TBL\" SET RENTALSTATUS='" + vehicleDetails.getRentStatus() + "' WHERE VEHICLEID='" + vehicleDetails.getVehicleID() + "'");
            System.out.print(result + " rows effected");
            stmt.close();

        } catch (Exception e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vehicle ID Already Present");
            alert.setContentText("Please fill new Vehicle ID as it is already present");
            alert.showAndWait();
            e.printStackTrace(System.out);
        } finally {
            try {
                // Close connection
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void InsertRentalRecords(RentalRepository rentalRecord) {

        Connection connection = ConnenctionManager();
         
        int result = 0;
        try {
            // Create and execute statement
            Statement stmt = connection.createStatement();
            
            if (rentalRecord.getActualReturnDate().isEmpty() || rentalRecord.getRentalFee().isEmpty() || rentalRecord.getLateFee().isEmpty()) {
                result = stmt.executeUpdate("INSERT INTO \"PUBLIC\".\"RENTALRECORD_TBL\"VALUES ('" + rentalRecord.getRecordID() + "','" + rentalRecord.getRentDate() + "','" + "" + rentalRecord.getEstimatedReturnDate() + "','" + "none" + "','" + "none" + "','" + "none" + "')");
            } else {
                result = stmt.executeUpdate("INSERT INTO \"PUBLIC\".\"RENTALRECORD_TBL\"VALUES ('" + rentalRecord.getRecordID() + "','" + rentalRecord.getRentDate() + "','" + "" + rentalRecord.getEstimatedReturnDate() + "','" + rentalRecord.getActualReturnDate() + "','" + rentalRecord.getRentalFee() + "','" + rentalRecord.getLateFee() + "')");
            }
            
            System.out.print(result + " rows effected");
            stmt.close();

        } catch (Exception e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("There was some error while inserting the rental record");
            alert.showAndWait();
            e.printStackTrace(System.out);
        } finally {
            try {
                // Close connection
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void UpdateRentalRecord(RentalRepository vehicleDetails) {
        
        Connection connection = ConnenctionManager();
       
        int result = 0;
        try {
            // Create and execute statement
            Statement stmt = connection.createStatement();
            System.out.println("UPDATE \"PUBLIC\".\"VEHICLES_TBL\" SET RENTALSTATUS='Rented' WHERE VEHICLEID='");
            
            result = stmt.executeUpdate("UPDATE \"PUBLIC\".\"RENTALRECORD_TBL\" SET ACTUALRETURNDATE='" + vehicleDetails.getActualReturnDate() + "', RENTALFEE='" + vehicleDetails.getRentalFee() + "', LATEFEE='" + vehicleDetails.getLateFee() + "' WHERE RECORDID='" + vehicleDetails.getRecordID() + "'");
            
            System.out.print(result + " rows effected");
            stmt.close();

        } catch (Exception e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vehicle ID Already Present");
            alert.setContentText("Please fill new Vehicle ID as it is already present");
            alert.showAndWait();
            e.printStackTrace(System.out);
        } finally {
            try {
                // Close connection
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
