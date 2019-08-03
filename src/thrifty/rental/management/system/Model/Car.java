package thrifty.rental.management.system.Model;

import thrifty.rental.management.system.Controller.RentalRepository;

/**
 * Class to save all the car details
 * This class contains all the calculations and details of the car
 * It extends Vehicle so it has all the features of a vehicle
 */
public class Car extends  Vehicle {


    private double rentRate=78;
    private double lateFee;
    private int seats=0;

    public Car(String VehicleId,int Year,String Make,String Model,int status,VehicleType vehicleType)
    {
        super(VehicleId,Year,Make,Model,status,vehicleType);
        seats=vehicleType.getCarSeats();
        if(seats==7)
            rentRate=113;
    }
public Car(String VehicleId,int Year,String Make,String Model,int status,VehicleType vehicleType,String vehicleClass)
    {
        super(VehicleId,Year,Make,Model,status,vehicleType,vehicleClass);
        seats=vehicleType.getCarSeats();
        if(seats==7)
            rentRate=113;
    }
    /**
     * Used to add either cars or vans to the list
     * @param endDate,startDate accepts start date and end date
     * @return lateFee the late fee
     */
    public double getLateFee(DateTime endDate,DateTime startDate)
    {

        if(DateTime.diffDays(endDate,startDate)>0)
            this.lateFee= 1.25 * this.rentRate * DateTime.diffDays(endDate,startDate);
        else
            this.lateFee=0.0;
        return this.lateFee;
    }
public  void LoadAllRentalRecords()
    {
        RentalRecord record;
    
        rentalRecordArrayList=  DatabaseUtility.ReadVehicleRentalRecord(this.getVehicleId());
                 if(!(rentalRecordArrayList.isEmpty())){
                        for (int j = 0; j < rentalRecordArrayList.size(); j++) {
                                if(rentalRecordArrayList.get(j).getActualReturnDate()==null){
                                record= new RentalRecord(rentalRecordArrayList.get(j).getRentId(),rentalRecordArrayList.get(j).getRentDate(),rentalRecordArrayList.get(j).getEstimatedReturnDate()); 
                                this.records[j] = record;
                                }
                                else{
                                  record= new RentalRecord(rentalRecordArrayList.get(j).getRentId(),rentalRecordArrayList.get(j).getRentDate(),rentalRecordArrayList.get(j).getEstimatedReturnDate(),rentalRecordArrayList.get(j).getActualReturnDate(),rentalRecordArrayList.get(j).getRentalFee(),rentalRecordArrayList.get(j).getLateFee());
                                  this.records[j]=record;
                                }
                        } 
                       
                    } 
    }
    /**
     * Used to add either cars or vans to the list
     * @param returnDate accepts the date as to when it has to be returned
     * @return Returns true if returned else false with appropriate messages
     */
    public  RentalRepository returnVehicle(DateTime returnDate)
    {
        LoadAllRentalRecords();
        String vehicleType;
        if(this.Vehicle_id.contains("C_"))
            vehicleType="car";
        else
            vehicleType="van";
        if(this.vehicleStatus!=0)
        {
        DateTime estdate= this.records[this.getLastElementIndex()].getEstimatedReturnDate();
        DateTime rentDate= this.records[this.getLastElementIndex()].getRentDate();
        if(vehicleType.equals("car") && DateTime.diffDays(returnDate,estdate)<0 && DateTime.diffDays(returnDate,rentDate)<this.vehicleType.canBeRentedForMinimumDays(rentDate,vehicleType)){
            return null;
        }

        else{

            this.records[this.getLastElementIndex()].setData(returnDate,this.rentRate * DateTime.diffDays(returnDate,rentDate),this.getLateFee(returnDate,estdate));
            this.vehicleStatus=0;
            repository=new RentalRepository(this.records[this.getLastElementIndex()].getRentId(),rentDate.toString(),estdate.toString(),returnDate.toString(),""+this.rentRate * DateTime.diffDays(returnDate,rentDate),""+this.getLateFee(returnDate,estdate));
            return repository;
        }}
        else {
            return null;
        }
    }

    /**
     * sets the vehicle status to available after maintenance
     * @return Returns true if returned else false
     */
    public boolean completeMaintenance()
    {
        if(super.vehicleStatus!=2)
            return false;
        this.vehicleStatus=0;
        return true;
    }


    /**
     * Method used to get details of car with their rental history
     * Prints the rental record of car
     */
    public String getDetails()
    {
        String details =super.getDetails();
        if(this.records[0]==null){
            details+="\nRENTAL RECORD:\tempty";
		}
        else{
            details+="\nRENTAL RECORD\n";
			int count=0;
			for(int index=0;this.records[index]!=null;index++)
				count++;
            for(int index=count-1;index>=0;index--){
                details+=this.records[index].getDetails()+"                     \n";
                for(int innerIndex=0;innerIndex<10;innerIndex++)
                    details+="-";
                details+="                     \n";
            }
        }
        return details;
    }
	
}