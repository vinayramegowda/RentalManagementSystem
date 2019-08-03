/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thrifty.rental.management.system.Model;

/**
 *
 * @author Vinay
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertData {
    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        int result = 0;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/rentalDB;ifexists=true", "SA", "");
            stmt = con.createStatement();
            result = stmt.executeUpdate("INSERT INTO \"PUBLIC\".\"VEHICLES_TBL\"( \"VEHICLEID\", \"YEAR\", \"MAKE\", \"MODEL\", \"NUMBEROFSEATS\", \"VEHICLETYPE\", \"RENTALSTATUS\", \"MAINTENANCESTATUS\", \"MAINTENANCEDATE\", \"COMPLETIONDATE\" )VALUES ('C_15','2004','Honda', 'CRV', '4' ,'car', 'available', 'available',NULL,NULL)");
            //result = stmt.executeUpdate("INSERT INTO vehicle_tbl VALUES('C_11','2004','Honda', 'CRV', '4' ,'car', 'available', 'available',NULL,NULL)");
            con.commit();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        System.out.println(result + " rows effected");
        System.out.println("Rows inserted successfully");
    }
}