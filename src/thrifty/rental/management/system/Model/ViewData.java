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
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewData {
   
   public static void main(String[] args) {
      Connection con = null;
      Statement stmt = null;
      ResultSet result = null;
      
      try {
         Class.forName("org.hsqldb.jdbc.JDBCDriver");
         con = DriverManager.getConnection("jdbc:hsqldb:file:hsqldb/ThriftyDatabase", "SA", "");
         stmt = con.createStatement();
         result = stmt.executeQuery(
            "SELECT * FROM public.Vehicles_tbl");
         
         while(result.next()){
               result.getString("Make");
         }
      } catch (Exception e) {
         e.printStackTrace(System.out);
      }
   }
}