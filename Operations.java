// Operations.java

import java.sql.*;
import java.util.*;

public class Operations {
    // Database connection details (update as needed)
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/PharmacySales?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    // ---------------- Generic Insert Method ----------------
    public static int insertRecord(String tableName, Map<String, Object> data) {
        int rowsAffected = 0;
        if (data.isEmpty()) {
            System.out.println("No data provided to insert.");
            return 0;
        }
        String columns = String.join(", ", data.keySet());
        String placeholders = String.join(", ", Collections.nCopies(data.size(), "?"));
        String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

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
        return insertRecord("Customer", customerData);
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
    public static int updateCustomer(int customerId, String firstName, String lastName, String address, String email, String phoneNumber) {
        int rowsAffected = 0;
        String query = "UPDATE Customer SET firstName=?, lastName=?, address=?, email=?, phoneNumber=? WHERE customerId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setString(1, firstName);
            pstat.setString(2, lastName);
            pstat.setString(3, address);
            pstat.setString(4, email);
            pstat.setString(5, phoneNumber);
            pstat.setInt(6, customerId);
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

    public static int updateFaultyItem(int itemId, String itemName, String description, int quantity, String reportedDate) {
        int rowsAffected = 0;
        String query = "UPDATE FaultyItems SET itemName=?, description=?, quantity=?, reportedDate=? WHERE itemId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setString(1, itemName);
            pstat.setString(2, description);
            pstat.setInt(3, quantity);
            pstat.setString(4, reportedDate);
            pstat.setInt(5, itemId);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int updateOrder(int orderId, int customerId, String orderDate, double totalAmount, String status) {
        int rowsAffected = 0;
        String query = "UPDATE Orders SET customerId=?, orderDate=?, totalAmount=?, status=? WHERE orderId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setInt(1, customerId);
            pstat.setString(2, orderDate);
            pstat.setDouble(3, totalAmount);
            pstat.setString(4, status);
            pstat.setInt(5, orderId);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int updateEquipment(int equipmentId, String equipmentName, String equipmentType, String purchaseDate, String status) {
        int rowsAffected = 0;
        String query = "UPDATE Equipment SET equipmentName=?, equipmentType=?, purchaseDate=?, status=? WHERE equipmentId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setString(1, equipmentName);
            pstat.setString(2, equipmentType);
            pstat.setString(3, purchaseDate);
            pstat.setString(4, status);
            pstat.setInt(5, equipmentId);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int updateStaff(int staffId, String firstName, String lastName, String role, String email, String phoneNumber) {
        int rowsAffected = 0;
        String query = "UPDATE Staff SET firstName=?, lastName=?, role=?, email=?, phoneNumber=? WHERE staffId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setString(1, firstName);
            pstat.setString(2, lastName);
            pstat.setString(3, role);
            pstat.setString(4, email);
            pstat.setString(5, phoneNumber);
            pstat.setInt(6, staffId);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // ---------------- Delete Methods ----------------
    public static int deleteCustomer(int customerId) {
        int rowsAffected = 0;
        String query = "DELETE FROM Customer WHERE customerId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setInt(1, customerId);
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

    public static int deleteFaultyItem(int itemId) {
        int rowsAffected = 0;
        String query = "DELETE FROM FaultyItems WHERE itemId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setInt(1, itemId);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int deleteOrder(int orderId) {
        int rowsAffected = 0;
        String query = "DELETE FROM Orders WHERE orderId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setInt(1, orderId);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int deleteEquipment(int equipmentId) {
        int rowsAffected = 0;
        String query = "DELETE FROM Equipment WHERE equipmentId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setInt(1, equipmentId);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static int deleteStaff(int staffId) {
        int rowsAffected = 0;
        String query = "DELETE FROM Staff WHERE staffId=?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstat = connection.prepareStatement(query)) {
            pstat.setInt(1, staffId);
            rowsAffected = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
