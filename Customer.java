// Customer.java
// No package declaration if all files are in the same folder.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Customer extends JPanel {

    private final JTable customerTable;

    public Customer() {
        // Use a BorderLayout to separate left (operations) and right (data view)
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // ------------------------------------
        // Left Panel: Operation Buttons (Vertical)
        // ------------------------------------
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setPreferredSize(new Dimension(300, 0)); // Fixed width for the ops column

        // Create operation buttons (black background, white text, with icons)
        JButton btnAdd = createOperationButton("Add Customer", "add.png");
        btnAdd.addActionListener(e -> addCustomer());

        JButton btnUpdate = createOperationButton("Update Customer", "update.png");
        btnUpdate.addActionListener(e -> updateCustomer());

        JButton btnDelete = createOperationButton("Delete Customer", "delete.png");
        btnDelete.addActionListener(e -> deleteCustomer());

        // Add vertical spacing + buttons
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnAdd);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnUpdate);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnDelete);
        leftPanel.add(Box.createVerticalGlue());

        add(leftPanel, BorderLayout.WEST);

        // ------------------------------------
        // Right Panel: Customer Details Label and Data Table
        // ------------------------------------
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.LIGHT_GRAY);

        // Label at the top
        JLabel detailsLabel = new JLabel("Customer Details", SwingConstants.LEFT);
        detailsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        detailsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(detailsLabel, BorderLayout.NORTH);

        // Create the JTable to display customer data
        customerTable = new JTable();
        refreshCustomerTable();

        JScrollPane tableScrollPane = new JScrollPane(customerTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 400));
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);
    }

    /**
     * Helper method to create an operation button.
     * Loads and scales an icon, sets the button text, and applies a black background.
     */
    private JButton createOperationButton(String text, String resourcePath) {
        ImageIcon icon = scaleIcon(resourcePath, 32, 32);
        JButton button = new JButton(text, icon);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(280, 50));
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setIconTextGap(10);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        // If you want a borderless look, add: button.setBorderPainted(false);
        return button;
    }

    /**
     * Helper method to load and scale an image resource.
     * resourcePath should start with "/" if the images are at the root of the classpath.
     */
    private ImageIcon scaleIcon(String resourcePath, int width, int height) {
        java.net.URL imgURL = getClass().getResource(resourcePath);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } else {
            System.err.println("Couldn't find file: " + resourcePath);
            return new ImageIcon();
        }
    }

    /**
     * Refreshes the customer table by fetching data from the database.
     */
    private void refreshCustomerTable() {
        // 'Operations' references your static method calls; 'GenericDatabaseManager' fetches data
        List<Map<String, String>> data = GenericDatabaseManager.fetchData("Customer");
        if (data == null || data.isEmpty()) {
            customerTable.setModel(new DefaultTableModel(
                    new Object[][] {{"No customers found."}},
                    new String[] {"Message"}
            ));
        } else {
            Set<String> columns = data.get(0).keySet();
            String[] columnNames = columns.toArray(new String[0]);
            Object[][] tableData = new Object[data.size()][columnNames.length];
            for (int i = 0; i < data.size(); i++) {
                Map<String, String> row = data.get(i);
                int j = 0;
                for (String col : columnNames) {
                    tableData[i][j++] = row.get(col);
                }
            }
            customerTable.setModel(new DefaultTableModel(tableData, columnNames));
        }
    }

    // ----------------- Operation Functionality -----------------

    /**
     * Displays a form to add a new customer, and calls Operations.addCustomer method.
     */
    private void addCustomer() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();

        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Add Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            Map<String, Object> customerData = new HashMap<>();
            customerData.put("firstName", firstNameField.getText());
            customerData.put("lastName", lastNameField.getText());
            customerData.put("address", addressField.getText());
            customerData.put("email", emailField.getText());
            customerData.put("phoneNumber", phoneField.getText());

            int rows = Operations.addCustomer(customerData);
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Customer added successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Error adding customer.");
            }
            refreshCustomerTable();
        }
    }

    /**
     * Displays a form to update an existing customer.
     */
    private void updateCustomer() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        JTextField idField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();

        panel.add(new JLabel("Customer ID:"));
        panel.add(idField);
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Update Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            try {
                int customerId = Integer.parseInt(idField.getText());
                int rows = Operations.updateCustomer(
                        customerId,
                        firstNameField.getText(),
                        lastNameField.getText(),
                        addressField.getText(),
                        emailField.getText(),
                        phoneField.getText()
                );
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Customer updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error updating customer.");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Invalid Customer ID.");
            }
            refreshCustomerTable();
        }
    }

    /**
     * Prompts the user for a Customer ID and deletes that customer.
     */
    private void deleteCustomer() {
        String input = JOptionPane.showInputDialog(this, "Enter Customer ID to delete:");
        if (input != null) {
            try {
                int customerId = Integer.parseInt(input);
                int rows = Operations.deleteCustomer(customerId);
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Customer deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting customer or not found.");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Invalid Customer ID.");
            }
            refreshCustomerTable();
        }
    }
}
