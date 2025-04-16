// Operations.java

import java.sql.*;
import java.util.*;

public class Operations {
    private static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/PharmacySales?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    // ---------------- Generic Insert Method ----------------
    public static int insertRecord(String tableName, Map<String, Object> data) {
        int rowsAffected = 0;
        if (data.isEmpty()) {
            System.out.println("No data provided to insert.");
            return 0;
        }
        String columns      = String.join(", ", data.keySet());
        String placeholders = String.join(", ", Collections.nCopies(data.size(), "?"));
        String query        = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            int index = 1;
            for (Object value : data.values()) {
                pstat.setObject(index++, value);
            }
            rowsAffected = pstat.executeUpdate();
            System.out.println(rowsAffected + " record(s) successfully added to " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // ---------------- Add Methods ----------------
    public static int addCustomer(Map<String, Object> customerData) {
        return insertRecord("customer", customerData);
    }
    public static int addStock(Map<String, Object> stockData) {
        return insertRecord("Stock", stockData);
    }
    public static int addFaultyItem(Map<String, Object> faultyItemData) {
        return insertRecord("FaultyItems", faultyItemData);
    }
    public static int addOrder(Map<String, Object> orderData) {
        return insertRecord("Orders", orderData);
    }
    public static int addEquipment(Map<String, Object> equipmentData) {
        return insertRecord("Equipment", equipmentData);
    }
    public static int addStaff(Map<String, Object> staffData) {
        return insertRecord("Staff", staffData);
    }

    // ---------------- Update Methods ----------------
    public static int updateCustomer(int idCustomer,
                                     String customerFirstName,
                                     String customerSurname,
                                     String customerAddress,
                                     String customerEmail,
                                     String customerPhoneNumber) {
        int rowsAffected = 0;
        String query =
                "UPDATE customer "
                        + "SET customerFirstName=?, customerSurname=?, customerAddress=?, customerEmail=?, customerPhoneNumber=? "
                        + "WHERE idCustomer=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setString(1, customerFirstName);
            pstat.setString(2, customerSurname);
            pstat.setString(3, customerAddress);
            pstat.setString(4, customerEmail);
            pstat.setString(5, customerPhoneNumber);
            pstat.setInt(6, idCustomer);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int updateStock(int stockId, String prodName, int quantity, int price) {
        int rowsAffected = 0;
        String query = "UPDATE Stock SET prodName=?, quantity=?, price=? WHERE stockId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setString(1, prodName);
            pstat.setInt(2, quantity);
            pstat.setInt(3, price);
            pstat.setInt(4, stockId);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // ... (other update methods unchanged)

    // ---------------- Delete Methods ----------------
    public static int deleteCustomer(int idCustomer) {
        int rowsAffected = 0;
        String query = "DELETE FROM customer WHERE idCustomer=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setInt(1, idCustomer);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int deleteStock(int stockId) {
        int rowsAffected = 0;
        String query = "DELETE FROM Stock WHERE stockId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setInt(1, stockId);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // ... (other delete methods unchanged)
}
