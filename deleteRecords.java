// deleteRecords.java

import java.sql.*; // Import SQL classes for database operations

public class deleteRecords {
    // Database credentials for connecting to MySQL database
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/PharmacySales?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USERNAME = "root"; // Change as needed
    private static final String PASSWORD = "password"; // Change as needed

    /**
     * Deletes a record from a table using a single primary key.
     *
     * @param tableName The name of the table from which the record should be deleted.
     * @param primaryKey The column name of the primary key.
     * @param primaryKeyValue The value of the primary key of the record to be deleted.
     */
    public static void deleteRecord(String tableName, String primaryKey, int primaryKeyValue) {
        // SQL query to delete a record from a table using the primary key value.
        String query = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = ?";

        try (
                // Create a database connection and prepare the DELETE statement
                Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                PreparedStatement pstat = connection.prepareStatement(query)
        ) {
            // Set the primary key value in the query
            pstat.setInt(1, primaryKeyValue);

            // Execute the query and get the number of rows affected
            int rowsAffected = pstat.executeUpdate();

            // If rows are affected, print success message
            if (rowsAffected > 0) {
                System.out.println("Record deleted successfully from " + tableName + ".");
            } else {
                System.out.println("No matching record found in " + tableName + ".");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            // Catch violation of foreign key constraints (cannot delete a record that is referenced)
            System.out.println("Cannot delete record: Foreign key constraint violation.");
        } catch (SQLException e) {
            // Catch any other SQL exceptions and print the stack trace for debugging
            e.printStackTrace();
        }
    }

    /**
     * Deletes a record from OrderAndEquipment table, which has a composite primary key.
     * This method is specific for tables with composite keys (orderId, equipmentId).
     * @param tableName The name of the table from which the record should be deleted.
     * @param orderId The orderId part of the composite primary key.
     * @param equipmentId The equipmentId part of the composite primary key.
     */
    public static void deleteCompositeRecord(String tableName, int orderId, int equipmentId) {
        // SQL query to delete a record using both parts of the composite primary key
        String query = "DELETE FROM " + tableName + " WHERE orderId = ? AND equipmentId = ?";

        try (
                // Create a database connection and prepare the DELETE statement
                Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                PreparedStatement pstat = connection.prepareStatement(query)
        ) {
            // Set both parts of the composite key in the query
            pstat.setInt(1, orderId);
            pstat.setInt(2, equipmentId);

            // Execute the query and get the number of rows affected
            int rowsAffected = pstat.executeUpdate();

            // If rows are affected, print success message
            if (rowsAffected > 0) {
                System.out.println("Record deleted successfully from " + tableName + ".");
            } else {
                System.out.println("No matching record found in " + tableName + ".");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Cannot delete record: Foreign key constraint violation.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
