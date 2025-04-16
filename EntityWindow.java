

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class EntityWindow extends JFrame {
    public EntityWindow(String tableName, String displayName) {
        super(displayName + " - Pharmacy Sales System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Bar: Black background with navigation buttons
        JPanel topBar = new JPanel(null);
        topBar.setPreferredSize(new Dimension(0, 60));
        topBar.setBackground(Color.BLACK);

        // Create navigation buttons (using helper method for styling)
        JButton btnCustomer = new JButton("Customer");
        styleButton(btnCustomer, 20, Color.WHITE);
        btnCustomer.addActionListener(e -> new EntityWindow("Customer", "Customer").setVisible(true));

        JButton btnOrders = new JButton("Orders");
        styleButton(btnOrders, 160, Color.MAGENTA); // purple border to indicate selection
        btnOrders.addActionListener(e -> new EntityWindow("Orders", "Orders").setVisible(true));

        JButton btnEquipment = new JButton("Equipment");
        styleButton(btnEquipment, 300, Color.WHITE);
        btnEquipment.addActionListener(e -> new EntityWindow("Equipment", "Equipment").setVisible(true));

        // (Add more buttons as needed following the same approach)
        topBar.add(btnCustomer);
        topBar.add(btnOrders);
        topBar.add(btnEquipment);

        add(topBar, BorderLayout.NORTH);

        // Center Panel: Display the data from the table on a light-gray background
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);
        List<Map<String, String>> data = GenericDatabaseManager.fetchData(tableName);
        if (data.isEmpty()) {
            JLabel noDataLabel = new JLabel("No data available for " + tableName, SwingConstants.CENTER);
            noDataLabel.setFont(new Font("Arial", Font.ITALIC, 24));
            centerPanel.add(noDataLabel, BorderLayout.CENTER);
        } else {
            String[] columnNames = data.getFirst().keySet().toArray(new String[0]);
            Object[][] tableData = new Object[data.size()][columnNames.length];
            for (int i = 0; i < data.size(); i++) {
                int j = 0;
                for (String col : columnNames) {
                    tableData[i][j++] = data.get(i).get(col);
                }
            }
            JTable table = new JTable(tableData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(700, 300));
            centerPanel.add(scrollPane, BorderLayout.CENTER);
        }
        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel: Logout button on the bottom-left
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(120, 40));
        logoutButton.addActionListener(e -> {
            dispose();
            new loginPage().setVisible(true);
        });
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Helper method to style buttons consistently
    private void styleButton(JButton btn, int x,
                             Color borderColor) {
        btn.setBounds(x, 10, 120, 40);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(borderColor, 2));
    }
}
