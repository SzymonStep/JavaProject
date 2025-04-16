// insertTable.java

import java.sql.*; // Import SQL classes for database connection and operations
import java.util.*; // Import utilities like HashMap
import java.sql.Date; // Import Date class for handling SQL date types
import java.time.LocalDate; // Import LocalDate for handling current date
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class insertTable {

    // Method to calculate estimated delivery time (3 days later from current time)
    public static Timestamp getEstimatedDeliveryTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deliveryTime = now.plusDays(3);
        return Timestamp.valueOf(deliveryTime); // Return the timestamp for 3 days later
    }

    // Method to get the current time
    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now(); // Returns current time
    }

    // Database connection details
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/PharmacySales?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USERNAME = "root"; // Change as needed
    private static final String PASSWORD = "password"; // Change as needed

    // General method to insert a record into a given table dynamically
    public static int insertRecord(String tableName, Map<String, Object> data) {
        if (data.isEmpty()) {
            System.out.println("No data provided to insert.");
            return 0; // Return 0 if no data is provided
        }

        // Construct SQL query dynamically using table name and data
        String columns = String.join(", ", data.keySet());
        String placeholders = String.join(", ", Collections.nCopies(data.size(), "?"));
        String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try (
                // Open database connection and prepare the SQL query
                Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                PreparedStatement pstat = connection.prepareStatement(query)
        ) {
            // Set values dynamically for PreparedStatement
            int index = 1;
            for (Object value : data.values()) {
                pstat.setObject(index++, value);
            }

            // Execute insert operation and return the number of rows affected
            int rowsAffected = pstat.executeUpdate();
            System.out.println(rowsAffected + " record(s) successfully added to " + tableName);
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Return 0 in case of an error
        }
    }

    // Insert a customer record into the Customer table
    public static void insertCustomer(String firstName, String lastName, String address, String email, String phoneNumber) {
        Map<String, Object> customerData = new HashMap<>();
        customerData.put("firstName", firstName);
        customerData.put("lastName", lastName);
        customerData.put("address", address);
        customerData.put("email", email);
        customerData.put("phoneNumber", phoneNumber);

        insertRecord("Customer", customerData); // Call the generic insert method
    }

    // Insert a staff record into the Staff table
    public static void insertStaff(String firstName, String lastName, String staffRole, String email, int phoneNumber) {
        Map<String, Object> staffData = new HashMap<>();
        staffData.put("firstName", firstName);
        staffData.put("lastName", lastName);
        staffData.put("staffRole", staffRole);
        staffData.put("email", email);
        staffData.put("phoneNumber", phoneNumber);

        insertRecord("Staff", staffData); // Call the generic insert method
    }

    // Insert an equipment record into the Equipment table
    public static void insertEquipmentData(String equipmentName, String equipmentType, String equipmentSpecifications, int quantityAvailable, int equipmentPrice) {
        Map<String, Object> equipmentData = new HashMap<>();
        equipmentData.put("equipmentName", equipmentName);
        equipmentData.put("equipmentType", equipmentType);
        equipmentData.put("equipmentSpecifications", equipmentSpecifications);
        equipmentData.put("quantityAvailable", quantityAvailable);
        equipmentData.put("equipmentPrice", equipmentPrice);

        insertRecord("Equipment", equipmentData); // Call the generic insert method
    }

    // Insert an order record into the Orders table
    public static void insertOrdersData(int customerId, int totalAmount, String orderStatus) {
        Map<String, Object> ordersData = new HashMap<>();
        ordersData.put("customerId", 1); // Fixed customerId as 1 (it should be passed as an argument in a real scenario)
        ordersData.put("orderDate", Date.valueOf(LocalDate.now())); // Current date
        ordersData.put("totalAmount", totalAmount);
        ordersData.put("orderStatus", orderStatus);

        insertRecord("Orders", ordersData); // Call the generic insert method
    }

    // Insert an order return record into the OrderReturns table
    public static void insertOrderReturnsData(int orderId, int equipmentId, String reason, String orderReturnStatus, Boolean replacementRequested) {
        Map<String, Object> orderReturnsData = new HashMap<>();
        orderReturnsData.put("orderId", orderId);
        orderReturnsData.put("equipmentId", equipmentId);
        orderReturnsData.put("reason", reason);
        orderReturnsData.put("orderReturnStatus", orderReturnStatus);
        orderReturnsData.put("replacementRequested", replacementRequested);

        insertRecord("OrderReturns", orderReturnsData); // Call the generic insert method
    }

    // Insert an order and equipment record into the OrderAndEquipment table
    public static void insertOrderAndEquipment(int orderId, int equipmentId) {
        Map<String, Object> orderAndEquipmentData = new HashMap<>();
        orderAndEquipmentData.put("orderId", orderId);
        orderAndEquipmentData.put("equipmentId", equipmentId);

        insertRecord("OrderAndEquipment", orderAndEquipmentData); // Call the generic insert method
    }

    // Insert customer feedback into the CustomerFeedback table
    public static void insertCustomerFeedback(int customerId, int orderId, String comments, int rating) {
        Map<String, Object> customerFeedbackData = new HashMap<>();
        customerFeedbackData.put("customerId", customerId);
        customerFeedbackData.put("orderId", orderId);
        customerFeedbackData.put("comments", comments);
        customerFeedbackData.put("rating", rating);

        insertRecord("CustomerFeedback", customerFeedbackData); // Call the generic insert method
    }

    // Insert a delivery record into the Delivery table
    public static void insertDelivery(int orderId, Timestamp estimatedDeliveryTime, String trackingStatus) {
        Map<String, Object> deliveryData = new HashMap<>();
        deliveryData.put("orderid", orderId);
        deliveryData.put("estimatedDeliveryTime", getEstimatedDeliveryTime()); // Get the estimated delivery time (3 days later)
        deliveryData.put("trackingStatus", trackingStatus);

        insertRecord("Delivery", deliveryData); // Call the generic insert method
    }

    // Insert a report record into the Reports table
    public static void insertReports(String reportType, String reportData) {
        Map<String, Object> reportsData = new HashMap<>();
        reportsData.put("reportType", reportType);
        reportsData.put("reportData", reportData);
        reportsData.put("reportDate", getCurrentTime()); // Use the current time for report date

        insertRecord("Reports", reportsData); // Call the generic insert method
    }

    // Insert a faulty item record into the FaultyItems table
    public static void insertFaultyItems(int equipmentId, int batchNumber, String faultDescription) {
        Map<String, Object> faultyItemData = new HashMap<>();
        faultyItemData.put("equipmentId", equipmentId);
        faultyItemData.put("batchNumber", batchNumber);
        faultyItemData.put("faultDescription", faultDescription);
        faultyItemData.put("reportedDate", getCurrentTime()); // Use the current time for the reported date

        insertRecord("FaultyItems", faultyItemData); // Call the generic insert method
    }

    // Main method to insert all predefined records into respective tables
    public static void main(String[] args) {
        // Sample insertions
        insertOrderAndEquipment(1, 1);
        insertCustomerFeedback(1, 1, "comments123", 5);
        insertDelivery(1, getEstimatedDeliveryTime(), "Processing");
        insertReports("Faulty Item", "Defective Items");
        insertFaultyItems(1, 1, "Defective Items");
    }
}
