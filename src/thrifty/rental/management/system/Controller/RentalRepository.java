/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thrifty.rental.management.system.Controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Vinay
 */
public class RentalRepository {
    
   //public SimpleIntegerProperty userId = new SimpleIntegerProperty();
   //public ObjectProperty userPhoto = new SimpleObjectProperty();  
   public SimpleStringProperty  recordID;
   public SimpleStringProperty rentDate;
   public SimpleStringProperty estimatedReturnDate;
   public SimpleStringProperty actualReturnDate;
   public SimpleStringProperty rentalFee;
   public SimpleStringProperty lateFee;
//
//   public Integer getUserId() {
//      return userId.get();
//   }
//
//   public Object getUserPhoto() {
//      return userPhoto.get();
//   }
public RentalRepository(String recordID,String rentDate,String estimatedReturnDate,String actualReturnDate,String rentalFee,String lateFee)
{
    this.recordID = new SimpleStringProperty(recordID);
    this.rentDate = new SimpleStringProperty(rentDate);
    this.estimatedReturnDate = new SimpleStringProperty(estimatedReturnDate);
    this.actualReturnDate = new SimpleStringProperty(actualReturnDate);
    this.rentalFee = new SimpleStringProperty(rentalFee);
    this.lateFee = new SimpleStringProperty(lateFee);
}
public RentalRepository(String recordID,String rentDate,String estimatedReturnDate)
{
    this.recordID = new SimpleStringProperty(recordID);
    this.rentDate = new SimpleStringProperty(rentDate);
    this.estimatedReturnDate = new SimpleStringProperty(estimatedReturnDate);
    this.actualReturnDate = new SimpleStringProperty("none");
    this.rentalFee = new SimpleStringProperty("none");
    this.lateFee = new SimpleStringProperty("none");

}
   public String getRecordID() {
      return recordID.get();
   }

   public String getRentDate() {
      return rentDate.get();
   }

   public String getEstimatedReturnDate() {
      return estimatedReturnDate.get();
   }

   public String getActualReturnDate() {
      return actualReturnDate.get();
   }

   public String getRentalFee() {
      return rentalFee.get();
   }

   public String getLateFee() {
      return lateFee.get();
   }
}
