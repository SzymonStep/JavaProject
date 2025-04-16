// GenericDatabaseManager.java

import java.sql.*;
import java.util.*;

public class GenericDatabaseManager {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/PharmacySales?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USERNAME = "root"; // Change as needed
    private static final String PASSWORD = "password"; // Change as needed

    // Generic Method to Fetch Data from Any Table
    public static List<Map<String, String>> fetchData(String tableName) {
        List<Map<String, String>> records = new ArrayList<>();
        String query = "SELECT * FROM " + tableName; // Dynamic Table Selection

        try (
                Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                PreparedStatement pstat = connection.prepareStatement(query);
                ResultSet resultSet = pstat.executeQuery()
        ) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Fetch rows dynamically
            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), resultSet.getString(i)); // Store column values dynamically
                }
                records.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records; // Return List of Records
    }

    public static void main(String[] args) {
        // Fetch Customer Data
        List<Map<String, String>> customers = fetchData("Customer");
        System.out.println("Customers:");
        displayRecords(customers);

        // Fetch Staff Data
        List<Map<String, String>> staff = fetchData("Staff");
        System.out.println("\nStaff:");
        displayRecords(staff);
    }

    // Method to Display Records
    public static void displayRecords(List<Map<String, String>> records) {
        if (records.isEmpty()) {
            System.out.println("No data found.");
            return;
        }

        // Print Column Headers
        Set<String> columnNames = records.get(0).keySet();
        for (String column : columnNames) {
            System.out.print(column + "\t");
        }
        System.out.println("\n---------------------------------------------------------------------------");

        // Print Row Data
        for (Map<String, String> record : records) {
            for (String column : columnNames) {
                System.out.print(record.get(column) + "\t");
            }
            System.out.println();
        }
    }
}
