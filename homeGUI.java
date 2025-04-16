
import javax.swing.*;
import java.awt.*;

public class homeGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public homeGUI() {
        super("Pharmacy Sales System - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Global Navigation Bar
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setPreferredSize(new Dimension(0, 60));
        navBar.setBackground(Color.BLACK);

        JPanel navButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navButtonsPanel.setBackground(Color.BLACK);

        JButton btnHome = new JButton("Home");
        styleNavButton(btnHome);
        btnHome.addActionListener(e -> cardLayout.show(cardPanel, "home"));
        navButtonsPanel.add(btnHome);

        JButton btnCustomer = new JButton("Customer");
        styleNavButton(btnCustomer);
        btnCustomer.addActionListener(e -> cardLayout.show(cardPanel, "customer"));
        navButtonsPanel.add(btnCustomer);

        JButton btnOrders = new JButton("Orders");
        styleNavButton(btnOrders);
        btnOrders.addActionListener(e -> cardLayout.show(cardPanel, "orders"));
        navButtonsPanel.add(btnOrders);

        JButton btnEquipment = new JButton("Equipment");
        styleNavButton(btnEquipment);
        btnEquipment.addActionListener(e -> cardLayout.show(cardPanel, "equipment"));
        navButtonsPanel.add(btnEquipment);

        navBar.add(navButtonsPanel, BorderLayout.WEST);

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        logoutPanel.setBackground(Color.BLACK);
        JButton logoutButton = new JButton("Logout");
        styleNavButton(logoutButton);
        logoutButton.addActionListener(e -> {
            dispose();
            new loginPage().setVisible(true);
        });
        logoutPanel.add(logoutButton);
        navBar.add(logoutPanel, BorderLayout.EAST);

        add(navBar, BorderLayout.NORTH);

        // Card Panel setup
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Home card
        JPanel homeCard = new JPanel(new BorderLayout());
        homeCard.setBackground(Color.LIGHT_GRAY);
        JLabel welcomeLabel = new JLabel("Welcome to Pharmacy Sales System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        homeCard.add(welcomeLabel, BorderLayout.CENTER);
        cardPanel.add(homeCard, "home");

        // Customer card
        Customer customerPanel = new Customer();
        cardPanel.add(customerPanel, "customer");

        // Orders card
        Orders ordersPanel = new Orders();
        cardPanel.add(ordersPanel, "orders");

        // Equipment card
        Equipment equipmentPanel = new Equipment();
        cardPanel.add(equipmentPanel, "equipment");

        add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "home");
    }

    private void styleNavButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new homeGUI().setVisible(true));
    }
}
