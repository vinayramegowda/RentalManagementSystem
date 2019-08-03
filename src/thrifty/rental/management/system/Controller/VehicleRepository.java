/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thrifty.rental.management.system.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author Vinay
 */
public class VehicleRepository {
    private final StringProperty vehicleID;
    private final StringProperty year;
    private final StringProperty make;
    private final StringProperty model;
    private final StringProperty rentStatus;
    private final StringProperty vehicleType;
    private final StringProperty numberOfSeats;
    private final StringProperty lastMaintenanceDate;
    private final StringProperty maintenanceStatus;
    private final StringProperty completionDate;
    public VehicleRepository(String VehicleId,String Year,String Make,String Model,String status,String vehicleType,String numberOfSeats,String lastmaintenanceDate,String completionDate,String maintenanceStatus)
    {
        this.vehicleID=new SimpleStringProperty(VehicleId);
        this.year=new SimpleStringProperty(Year);
        this.make=new SimpleStringProperty(Make);
        this.model=new SimpleStringProperty(Model);
        this.rentStatus=new SimpleStringProperty(status);
        this.vehicleType=new SimpleStringProperty(vehicleType);
        this.numberOfSeats=new SimpleStringProperty(numberOfSeats);
        this.lastMaintenanceDate=new SimpleStringProperty(lastmaintenanceDate);
        this.maintenanceStatus=new SimpleStringProperty(maintenanceStatus);
        this.completionDate=new SimpleStringProperty(completionDate);
    }
    public String getVehicleID()
	  {
	    return (String)this.vehicleID.get();
	  }
    public String getMaintenanceStatus()
	  {
	    return (String)this.maintenanceStatus.get();
	  }
    public String getCompletionDate()
	  {
	    return (String)this.completionDate.get();
	  }
    public String getYear()
	  {
	    return (String)this.year.get();
	  }
    public String getMake()
	  {
	    return (String)this.make.get();
	  }
    public String getModel()
	  {
	    return (String)this.model.get();
	  }
    public String getStatus()
	  {
	    return (String)this.rentStatus.get();
	  }
     public String getRentStatus()
	  {
	    return (String)this.rentStatus.get();
	  }
      public String getVehicleType()
	  {
	    return (String)this.vehicleType.get();
	  }
       public String getNumberOfSeats()
	  {
	    return (String)this.numberOfSeats.get();
	  }
        public String getLastMaintenanceDate()
	  {
	    return (String)this.lastMaintenanceDate.get();
	  }
        public void setVehicleID(String value)
	  {
	    this.vehicleID.set(value);
	  }
        public void setMaintenanceDate(String value)
	  {
	    this.vehicleID.set(value);
	  }
        public void setCompletionDate(String value)
	  {
	    this.vehicleID.set(value);
	  }
        public void setYear(String value)
	  {
	    this.year.set(value);
	  }
     public void setMake(String value)
	  {
	    this.make.set(value);
	  }
      public void setModel(String value)
	  {
	    this.model.set(value);
	  }
      public void setRentStatus(String value)
	  {
	    this.rentStatus.set(value);
	  }
      public void setVehicleType(String value)
	  {
	    this.vehicleType.set(value);
	  }
      public void setNumberOfSeats(String value)
	  {
	    this.numberOfSeats.set(value);
	  }
       public void setLastMaintenanceDate(String value)
	  {
	    this.lastMaintenanceDate.set(value);
	  }
        public StringProperty vehicleIDProperty()
	  {
	    return this.vehicleID;
	  }
        public StringProperty yearProperty()
	  {
	    return this.year;
	  }
        public StringProperty makeProperty()
	  {
	    return this.make;
	  }
        public StringProperty modelProperty()
	  {
	    return this.model;
	  }
         public StringProperty rentStatusProperty()
	  {
	    return this.rentStatus;
	  }
         public StringProperty vehicleTypeProperty()
	  {
	    return this.vehicleType;
	  }
          public StringProperty numberOfSeatsProperty()
	  {
	    return this.numberOfSeats;
	  }
            public StringProperty lastMaintenanceDateProperty()
	  {
	    return this.lastMaintenanceDate;
	  }
}

