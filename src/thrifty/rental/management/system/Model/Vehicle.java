package thrifty.rental.management.system.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import thrifty.rental.management.system.Controller.RentalRepository;
import javafx.scene.image.Image;
/**
 * Class to save all the vehicle details This class contains all the vehicle
 * attributes required for this application This is the data access layer and
 * main logic for this application is written here
 */
public class Vehicle {
    protected String path;
    protected Image image;
    protected String Vehicle_id;
    protected int Year;
    protected String Make;
    protected String Model;
    protected int vehicleStatus;
    protected VehicleType vehicleType;
    protected String vehicleClassification;
    protected RentalRecord records[] = new RentalRecord[20];

    // Constructor to accept the details of a vehicle
    public Vehicle(String VehicleId, int Year, String Make, String Model, int status, VehicleType vehicleType) {
        this.Vehicle_id = VehicleId;
        this.Year = Year;
        this.Make = Make;
        this.Model = Model;
        this.vehicleStatus = status;
        this.vehicleType = vehicleType;
    }

    public Vehicle(String VehicleId, int Year, String Make, String Model, int status, VehicleType vehicleType, String vehicleClass) {
        this.Vehicle_id = VehicleId;
        this.Year = Year;
        this.Make = Make;
        this.Model = Model;
        this.vehicleStatus = status;
        this.vehicleType = vehicleType;
        this.vehicleClassification = vehicleClass;
    }

    /**
     * Method to get vehicle ID
     */
    public int getYear() {
        return this.Year;
    }

    public String getVehicleClassification() {
        return this.vehicleClassification;
    }

    public String getMake() {
        return this.Make;
    }

    public String getModel() {
        return this.Model;
    }

    public int getStatus() {
        return this.vehicleStatus;
    }

    public String getVehicleId() {
        return this.Vehicle_id;
    }

    public VehicleType getVehicleType() {
        return this.vehicleType;
    }
    List<RentalRecord> rentalRecordArrayList = new ArrayList<>();
    Iterator rentalRecordIterator;

    public void LoadAllRentalRecords() {
        RentalRecord record;

        rentalRecordArrayList = DatabaseUtility.ReadVehicleRentalRecord(this.getVehicleId());
        if (!(rentalRecordArrayList.isEmpty())) {
            for (int j = 0; j < rentalRecordArrayList.size(); j++) {
                if (rentalRecordArrayList.get(j).getActualReturnDate() == null) {
                    record = new RentalRecord(rentalRecordArrayList.get(j).getRentId(), rentalRecordArrayList.get(j).getRentDate(), rentalRecordArrayList.get(j).getEstimatedReturnDate());
                    this.records[j] = record;
                } else {
                    record = new RentalRecord(rentalRecordArrayList.get(j).getRentId(), rentalRecordArrayList.get(j).getRentDate(), rentalRecordArrayList.get(j).getEstimatedReturnDate(), rentalRecordArrayList.get(j).getActualReturnDate(), rentalRecordArrayList.get(j).getRentalFee(), rentalRecordArrayList.get(j).getLateFee());
                    this.records[j] = record;
                }
            }
        }
    }

    RentalRepository repository;

    public RentalRepository getRepository() {
        return repository;
    }

    /**
     * Used to rent either available car or available van
     *
     * @param customerId,rentDate,numOfRentDay accepts customeID, date of rent,
     * no of renting days
     * @return True or false as to vehicle is successfully rented or not
     */
    public RentalRepository rent(String customerId, DateTime rentDate, int numOfRentDay) {

        DateTime retDate;
        LoadAllRentalRecords();
        String typeOfVehicle;
        if (this.Vehicle_id.contains("C_")) {
            typeOfVehicle = "car";
        } else {
            typeOfVehicle = "van";
        }
        if (this.vehicleStatus != 0 || numOfRentDay < this.vehicleType.canBeRentedForMinimumDays(rentDate, typeOfVehicle) || numOfRentDay >= 14 || numOfRentDay < 2) {
            return null;
        } else if (typeOfVehicle.equals("van")) {
            if (this.vehicleStatus != 0 || this.vehicleType.IsUnderMaintenance(rentDate, typeOfVehicle, numOfRentDay) || numOfRentDay == 0) {
                return null;
            } else {
                String rentId = this.Vehicle_id + "_" + customerId + "_" + rentDate.getEightDigitDate();
                this.records[this.getLastElementIndex() + 1] = new RentalRecord(rentId, rentDate, new DateTime(rentDate, numOfRentDay));
                this.vehicleStatus = 1;
                retDate = new DateTime(rentDate, numOfRentDay);
                repository = new RentalRepository(rentId, rentDate.toString(), retDate.toString());
                return repository;
            }
        } else {
            String rentId = this.Vehicle_id + "_" + customerId + "_" + rentDate.getEightDigitDate();
            this.records[this.getLastElementIndex() + 1] = new RentalRecord(rentId, rentDate, new DateTime(rentDate, numOfRentDay));
            this.vehicleStatus = 1;
            retDate = new DateTime(rentDate, numOfRentDay);
            repository = new RentalRepository(rentId, rentDate.toString(), retDate.toString());
            return repository;
        }
    }

    /**
     * sets the vehicle status to available after maintenance
     *
     * @return Returns true if returned else false
     */
    public boolean performMaintenance() {
        if (this.vehicleStatus == 1 || this.vehicleStatus == 2) {
            return false;
        }
        this.vehicleStatus = 2;
        return true;
    }

    /**
     * Method used to get details of vehicle
     */
    public String toString() {
        String repository = "";
        if (this.Vehicle_id.contains("V_")) {
            repository = this.Vehicle_id + ":" + String.valueOf(this.Year) + ":" + this.Make + ":" + this.Model + ":15" + ":";
        }
        if (this.Vehicle_id.contains("C_")) {
            repository = this.Vehicle_id + ":" + String.valueOf(this.Year) + ":" + this.Make + ":" + this.Model + ":" + String.valueOf(this.vehicleType.getSeats("car")) + ":";
        }
        switch (this.vehicleStatus) {
            case 0:
                repository += "Available";
                break;
            case 1:
                repository += "Rented";
                break;
            case 2:
                repository += "Maintenance";
                break;
        }
        return repository;
    }

    /**
     * Method used to get details of car or van with their rental history
     */
    public String getDetails() {
        String data = "Vehicle ID:\t" + this.Vehicle_id + "\n Year:\t " + String.valueOf(this.Year) + "\n Make:\t" + this.Make + "\n Model:\t" + this.Model + "\n Number of Seats:\t";
        if (this.Vehicle_id.contains("C_")) {
            data += String.valueOf(this.vehicleType.getSeats("car")) + "\n Status:\t";
        } else {
            data += String.valueOf(this.vehicleType.getSeats("van")) + "\n Status:\t";
        }
        switch (this.vehicleStatus) {
            case 0:
                data += "Available";
                break;
            case 1:
                data += "Rented";
                break;
            case 2:
                data += "Maintenance";
                break;
        }
        return data;

    }

    /**
     * Method used to get last element index
     */
    public int getLastElementIndex() {
        int index = 0;
        for (index = 0; this.records[index] != null; index++);
        return index - 1;
    }
    
    public Image getImage()
    {
        return new Image(path, false);
    }
}
