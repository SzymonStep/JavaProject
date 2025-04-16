import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Customer() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // Left side: operations
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setPreferredSize(new Dimension(300, 0));

        JButton btnAdd = createOperationButton("Add Customer", "add.png");
        btnAdd.addActionListener(e -> addCustomer());
        JButton btnUpdate = createOperationButton("Update Customer", "update.png");
        btnUpdate.addActionListener(e -> updateCustomer());
        JButton btnDelete = createOperationButton("Delete Customer", "delete.png");
        btnDelete.addActionListener(e -> deleteCustomer());

        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnAdd);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnUpdate);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(btnDelete);
        leftPanel.add(Box.createVerticalGlue());
        add(leftPanel, BorderLayout.WEST);

        // Right side: table
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.LIGHT_GRAY);

        JLabel detailsLabel = new JLabel("Customer Details", SwingConstants.LEFT);
        detailsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        detailsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(detailsLabel, BorderLayout.NORTH);

        customerTable = new JTable();
        refreshCustomerTable();

        JScrollPane tableScrollPane = new JScrollPane(customerTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 400));
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);
    }

    private JButton createOperationButton(String text, String resourcePath) {
        ImageIcon icon = scaleIcon(resourcePath);
        JButton button = new JButton(text, icon);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(280, 50));
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setIconTextGap(10);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private ImageIcon scaleIcon(String resourcePath) {
        java.net.URL imgURL = getClass().getResource(resourcePath);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } else {
            System.err.println("Couldn't find file: " + resourcePath);
            return new ImageIcon();
        }
    }

    private void refreshCustomerTable() {
        List<Map<String, String>> data = GenericDatabaseManager.fetchData("customer");
        if (data.isEmpty()) {
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
                for (int j = 0; j < columnNames.length; j++) {
                    tableData[i][j] = row.get(columnNames[j]);
                }
            }
            customerTable.setModel(new DefaultTableModel(tableData, columnNames));
        }
    }

    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    private void addCustomer() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField firstNameField = new JTextField();
        JTextField lastNameField  = new JTextField();
        JTextField addressField   = new JTextField();
        JTextField emailField     = new JTextField();
        JTextField phoneField     = new JTextField();

        panel.add(new JLabel("First Name:"));   panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));    panel.add(lastNameField);
        panel.add(new JLabel("Address:"));      panel.add(addressField);
        panel.add(new JLabel("Email:"));        panel.add(emailField);
        panel.add(new JLabel("Phone Number:")); panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Add Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            Map<String, Object> customerData = new HashMap<>();
            customerData.put("customerFirstName",   firstNameField.getText());
            customerData.put("customerSurname",     lastNameField.getText());
            customerData.put("customerAddress",     addressField.getText());
            customerData.put("customerEmail",       emailField.getText());
            customerData.put("customerPhoneNumber", phoneField.getText());
            // Add date added (immutable)
            customerData.put("customerDateAdded",   getCurrentDateTime());

            int rows = Operations.addCustomer(customerData);
            JOptionPane.showMessageDialog(
                    this,
                    rows > 0 ? "Customer added successfully." : "Error adding customer."
            );
            refreshCustomerTable();
        }
    }

    private void updateCustomer() {
        List<Map<String, String>> data = GenericDatabaseManager.fetchData("customer");
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No customers to update.");
            return;
        }

        // Dropdown + fields on same panel
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("--Select Customer--");
        data.stream()
                .map(r -> r.get("customerFirstName") + " " + r.get("customerSurname"))
                .forEach(combo::addItem);

        // Prepare fields
        Map<String, JTextField> fieldMap = new LinkedHashMap<>();
        List<String> cols = new ArrayList<>(data.get(0).keySet());
        cols.remove("idCustomer");
        Collections.sort(cols);
        cols.add(0, "idCustomer");
        // Ensure dateAdded treated as read-only
        if (cols.contains("customerDateAdded")) {
            // leave in sorted order
        }

        JPanel fieldsPanel = new JPanel(new GridLayout(cols.size(), 2, 10, 10));
        for (String col : cols) {
            String label = col.equals("idCustomer")
                    ? "Customer ID"
                    : Arrays.stream(col.replaceFirst("^customer", "")
                            .split("(?=[A-Z])"))
                    .map(s -> s.substring(0,1).toUpperCase() + s.substring(1))
                    .collect(Collectors.joining(" "));
            JTextField tf = new JTextField();
            // disable id and dateAdded
            if (col.equals("idCustomer") || col.equals("customerDateAdded")) {
                tf.setEnabled(false);
            }
            fieldsPanel.add(new JLabel(label + ":"));
            fieldsPanel.add(tf);
            fieldMap.put(col, tf);
        }

        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && combo.getSelectedIndex() > 0) {
                    Map<String, String> chosen = data.get(combo.getSelectedIndex() - 1);
                    fieldMap.forEach((col, tf) -> {
                        tf.setText(chosen.get(col));
                        boolean editable = !(col.equals("idCustomer") || col.equals("customerDateAdded"));
                        tf.setEnabled(editable);
                    });
                } else if (combo.getSelectedIndex() == 0) {
                    fieldMap.forEach((col, tf) -> {
                        tf.setText("");
                        tf.setEnabled(false);
                    });
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(combo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(fieldsPanel);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Update Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION && combo.getSelectedIndex() > 0) {
            int idCustomer = Integer.parseInt(fieldMap.get("idCustomer").getText());
            String fName = fieldMap.get("customerFirstName").getText();
            String surname = fieldMap.get("customerSurname").getText();
            String address = fieldMap.get("customerAddress").getText();
            String email = fieldMap.get("customerEmail").getText();
            String phone = fieldMap.get("customerPhoneNumber").getText();

            int rows = Operations.updateCustomer(
                    idCustomer, fName, surname, address, email, phone
            );
            JOptionPane.showMessageDialog(
                    this,
                    rows > 0 ? "Customer updated successfully." : "Error updating customer."
            );
            refreshCustomerTable();
        }
    }

    private void deleteCustomer() {
        List<Map<String, String>> data = GenericDatabaseManager.fetchData("customer");
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No customers to delete.");
            return;
        }

        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("--Select Customer--");
        data.stream()
                .map(r -> r.get("customerFirstName") + " " + r.get("customerSurname"))
                .forEach(combo::addItem);

        Map<String, JTextField> fieldMap = new LinkedHashMap<>();
        List<String> colsDel = new ArrayList<>(data.get(0).keySet());
        colsDel.remove("idCustomer");
        Collections.sort(colsDel);
        colsDel.add(0, "idCustomer");

        JPanel fieldsPanel = new JPanel(new GridLayout(colsDel.size(), 2, 10, 10));
        for (String col : colsDel) {
            String label = col.equals("idCustomer")
                    ? "Customer ID"
                    : Arrays.stream(col.replaceFirst("^customer", "")
                            .split("(?=[A-Z])"))
                    .map(s -> s.substring(0,1).toUpperCase() + s.substring(1))
                    .collect(Collectors.joining(" "));
            JTextField tf = new JTextField();
            tf.setEnabled(false);
            fieldsPanel.add(new JLabel(label + ":"));
            fieldsPanel.add(tf);
            fieldMap.put(col, tf);
        }

        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && combo.getSelectedIndex() > 0) {
                    Map<String, String> chosen = data.get(combo.getSelectedIndex() - 1);
                    fieldMap.forEach((col, tf) -> tf.setText(chosen.get(col)));
                } else if (combo.getSelectedIndex() == 0) {
                    fieldMap.forEach((col, tf) -> tf.setText(""));
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(combo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(fieldsPanel);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Confirm Delete Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION && combo.getSelectedIndex() > 0) {
            int idCustomer = Integer.parseInt(fieldMap.get("idCustomer").getText());
            int rows = Operations.deleteCustomer(idCustomer);
            JOptionPane.showMessageDialog(
                    this,
                    rows > 0 ? "Customer deleted successfully." : "Error deleting customer."
            );
            refreshCustomerTable();
        }
    }
}
