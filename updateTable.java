// updateTable.java

import java.sql.*; // Import SQL classes for database operations
import java.util.*; // Import utilities like HashMap for handling key-value pairs
import java.sql.Date; // Import SQL Date class to handle SQL Date objects
import java.time.LocalDate; // Import LocalDate for handling the current date in a more readable format

public class updateTable {

    // Database connection details for accessing the database
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/PharmacySales?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USERNAME = "root"; // Database username (modify as needed)
    private static final String PASSWORD = "password"; // Database password (modify as needed)

    /**
     * Updates a record in the given table.
     *
     * @param tableName The name of the table to update.
     * @param data A map containing column names and their corresponding values.
     * @param primaryKeyColumn The primary key column of the table.
     * @param primaryKeyValue The value of the primary key for the record to update.
     * @return The number of records updated.
     */
    public static int updateRecord(String tableName, Map<String, Object> data, String primaryKeyColumn, int primaryKeyValue) {
        // Check if data is provided, return 0 if data is empty
        if (data.isEmpty()) {
            System.out.println("No data provided to update.");
            return 0;
        }

        // Dynamically build the SET clause for the SQL query (e.g., "firstName = ?, lastName = ?, ...")
        StringBuilder setClauseBuilder = new StringBuilder();
        for (String key : data.keySet()) {
            if (setClauseBuilder.length() > 0) {
                setClauseBuilder.append(", ");
            }
            setClauseBuilder.append(key).append(" = ?");
        }
        String setClause = setClauseBuilder.toString();

        // Construct the full SQL UPDATE query
        String query = "UPDATE " + tableName + " SET " + setClause + " WHERE " + primaryKeyColumn + " = ?";

        try (
                // Establish a connection and prepare the statement for executing the query
                Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                PreparedStatement pstat = connection.prepareStatement(query)
        ) {
            // Dynamically set the values for the columns to be updated
            int index = 1;
            for (Object value : data.values()) {
                pstat.setObject(index++, value); // Set the value in the prepared statement
            }
            // Set the primary key value for the WHERE condition
            pstat.setObject(index, primaryKeyValue);

            // Execute the update operation and get the number of affected rows
            int rowsAffected = pstat.executeUpdate();
            System.out.println(rowsAffected + " record(s) successfully updated in " + tableName);
            return rowsAffected; // Return the number of rows updated

        } catch (SQLException e) {
            // Handle SQL exceptions (e.g., connection issues or query errors)
            e.printStackTrace();
            return 0; // Return 0 if there is an error
        }
    }

    /** Method to update a customer record in the Customer table */
    public static void updateCustomer(int customerId, String firstName, String lastName, String address, String email, String phoneNumber) {
        // Create a map to hold the updated data
        Map<String, Object> customerData = new HashMap<>();
        customerData.put("firstName", firstName);
        customerData.put("lastName", lastName);
        customerData.put("address", address);
        customerData.put("email", email);
        customerData.put("phoneNumber", phoneNumber);

        // Call updateRecord method to update the customer in the database
        updateRecord("Customer", customerData, "customerId", customerId);
    }

    /** Method to update a staff record in the Staff table */
    public static void updateStaff(int staffId, String firstName, String lastName, String staffRole, String email, int phoneNumber) {
        // Create a map to hold the updated data
        Map<String, Object> staffData = new HashMap<>();
        staffData.put("firstName", firstName);
        staffData.put("lastName", lastName);
        staffData.put("staffRole", staffRole);
        staffData.put("email", email);
        staffData.put("phoneNumber", phoneNumber);

        // Call updateRecord method to update the staff in the database
        updateRecord("Staff", staffData, "staffId", staffId);
    }

    /** Method to update an equipment record in the Equipment table */
    public static void updateEquipment(int equipmentId, String equipmentName, String equipmentType, String equipmentSpecifications, int quantityAvailable, int equipmentPrice) {
        // Create a map to hold the updated data
        Map<String, Object> equipmentData = new HashMap<>();
        equipmentData.put("equipmentName", equipmentName);
        equipmentData.put("equipmentType", equipmentType);
        equipmentData.put("equipmentSpecifications", equipmentSpecifications);
        equipmentData.put("quantityAvailable", quantityAvailable);
        equipmentData.put("equipmentPrice", equipmentPrice);

        // Call updateRecord method to update the equipment in the database
        updateRecord("Equipment", equipmentData, "equipmentId", equipmentId);
    }

    /** Method to update an order record in the Orders table */
    public static void updateOrders(int orderId, int customerId, double totalAmount, String orderStatus) {
        // Create a map to hold the updated order data
        Map<String, Object> ordersData = new HashMap<>();
        ordersData.put("customerId", customerId);  // Fix: use customerId instead of orderId
        ordersData.put("orderDate", Date.valueOf(LocalDate.now())); // Set the current date for the orderDate
        ordersData.put("totalAmount", totalAmount);
        ordersData.put("orderStatus", orderStatus);

        // Call updateRecord method to update the order in the database
        updateRecord("Orders", ordersData, "orderId", orderId);
    }

    /** Method to update an order return record in the OrderReturns table */
    public static void updateOrderReturns(int returnId, int orderId, int equipmentId, String reason, String orderReturnStatus, Boolean replacementRequested) {
        // Create a map to hold the updated data for the order return
        Map<String, Object> orderReturnsData = new HashMap<>();
        orderReturnsData.put("orderId", orderId);
        orderReturnsData.put("equipmentId", equipmentId);
        orderReturnsData.put("reason", reason);
        orderReturnsData.put("orderReturnStatus", orderReturnStatus);
        orderReturnsData.put("replacementRequested", replacementRequested);

        // Call updateRecord method to update the order return in the database
        updateRecord("OrderReturns", orderReturnsData, "returnId", returnId);
    }

    /** Method to update a record in the composite key table OrderAndEquipment */
    public static void updateOrderAndEquipment(int orderId, int equipmentId, int newOrderId, int newEquipmentId) {
        // Update a record with composite primary keys, both orderId and equipmentId are required
        String query = "UPDATE OrderAndEquipment SET orderId = ?, equipmentId = ? WHERE orderId = ? AND equipmentId = ?";
        try (
                Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                PreparedStatement pstat = connection.prepareStatement(query)
        ) {
            // Set the new values for orderId and equipmentId
            pstat.setInt(1, newOrderId);
            pstat.setInt(2, newEquipmentId);
            pstat.setInt(3, orderId);
            pstat.setInt(4, equipmentId);

            // Execute the update operation
            int rowsAffected = pstat.executeUpdate();
            System.out.println(rowsAffected + " record(s) successfully updated in OrderAndEquipment");
        } catch (SQLException e) {
            // Handle any exceptions during the update operation
            e.printStackTrace();
        }
    }

    /** Method to update a customer feedback record in the CustomerFeedback table */
    public static void updateCustomerFeedback(int feedbackId, int customerId, int orderId, String comments, int rating) {
        // Create a map to hold the updated customer feedback data
        Map<String, Object> customerFeedbackData = new HashMap<>();
        customerFeedbackData.put("customerId", customerId);
        customerFeedbackData.put("orderId", orderId);
        customerFeedbackData.put("comments", comments);
        customerFeedbackData.put("rating", rating);

        // Call updateRecord method to update the customer feedback in the database
        updateRecord("CustomerFeedback", customerFeedbackData, "feedbackId", feedbackId);
    }

    /** Method to update a delivery record in the Delivery table */
    public static void updateDelivery(int deliveryId, int orderId, Timestamp estimatedDeliveryTime, String trackingStatus) {
        // Create a map to hold the updated delivery data
        Map<String, Object> deliveryData = new HashMap<>();
        deliveryData.put("orderId", orderId);
        deliveryData.put("estimatedDeliveryTime", estimatedDeliveryTime);
        deliveryData.put("trackingStatus", trackingStatus);

        // Call updateRecord method to update the delivery in the database
        updateRecord("Delivery", deliveryData, "deliveryId", deliveryId);
    }

    /** Method to update a report record in the Reports table */
    public static void updateReports(int reportId, String reportType, String reportData) {
        // Create a map to hold the updated report data
        Map<String, Object> reportsData = new HashMap<>();
        reportsData.put("reportType", reportType);
        reportsData.put("reportData", reportData);

        // Call updateRecord method to update the report in the database
        updateRecord("Reports", reportsData, "reportId", reportId);
    }

    /** Method to update a faulty item record in the FaultyItems table */
    public static void updateFaultyItems(int faultId, int equipmentId, int batchNumber, String faultDescription) {
        // Create a map to hold the updated faulty item data
        Map<String, Object> faultyItemData = new HashMap<>();
        faultyItemData.put("equipmentId", equipmentId);
        faultyItemData.put("batchNumber", batchNumber);
        faultyItemData.put("faultDescription", faultDescription);

        // Call updateRecord method to update the faulty item in the database
        updateRecord("FaultyItems", faultyItemData, "faultId", faultId);
    }

    /** Main method to update predefined records */
    public static void main(String[] args) {
        // Example calls with sample data for updating records
        updateCustomer(1, "John", "Doe", "123 Main St", "john.doe@example.com", "1234567890");
        updateStaff(1, "Alice", "Smith", "Manager", "alice.smith@example.com", 987654321);
        updateEquipment(1, "Printer", "Electronics", "Laser printer", 10, 250);
        updateOrders(1, 1, 150.00, "Shipped");
        updateOrderReturns(10, 1, 1, "Defective.", "Returned", true);

        // Update a composite key record in OrderAndEquipment
        updateOrderAndEquipment(2, 2, 5, 6);
        updateCustomerFeedback(7, 1, 1, "Updated comment.", 4);

        // Example of updating delivery time with a future timestamp
        Timestamp newDeliveryTime = new Timestamp(System.currentTimeMillis() + 86400000); // 1 day later
        updateDelivery(3, 1, newDeliveryTime, "In Transit.");
        updateReports(1, "Update Report", "Updated report data");
        updateFaultyItems(4, 1, 101, "Updated fault description.");
    }
}
