// insertCustomer.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class insertCustomer {
    public static void main(String[] args) {
        // Database URL
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/PharmacySales?allowPublicKeyRetrieval=true&useSSL=false";
        Connection connection = null;
        PreparedStatement pstat = null;
        String firstname = "firstName";
        String lastname = "lastName";
        String address = "address";
        String email = "email@gmail.com";
        String phoneNumber = "0871234567";

        int i = 0;

        try {
            // Establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "password");

            // Create Prepared Statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO Customer (firstName, lastName, address, email, phoneNumber) VALUES (?,?,?,?,?)");
            pstat.setString(1, firstname);
            pstat.setString(2, lastname);
            pstat.setString(3, address);
            pstat.setString(4, email);
            pstat.setString(5, phoneNumber);

            // Insert data into table
            i = pstat.executeUpdate();
            System.out.println(i + " record successfully added to the table.");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (pstat != null) {
                    pstat.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    } // end main
} // end class
