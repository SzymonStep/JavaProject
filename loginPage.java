import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class loginPage extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JLabel errorLabel; // Label to display validation errors

    public loginPage() {
        setTitle("Login Page");
        setSize(500, 400); // Adjusted height to fit error message
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Prevent resizing

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240)); // Light background
        GridBagConstraints gridCons = new GridBagConstraints();
        gridCons.insets = new Insets(10, 10, 10, 10);
        gridCons.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 16);
        Color teal = new Color(0, 128, 128);

        // Username Label (above the text field)
        gridCons.gridx = 0;
        gridCons.gridy = 0;
        gridCons.anchor = GridBagConstraints.WEST;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        panel.add(usernameLabel, gridCons);

        // Username Text Field
        gridCons.gridx = 0;
        gridCons.gridy = 1;
        usernameField = new JTextField(20);
        usernameField.setFont(fieldFont);
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setBorder(BorderFactory.createLineBorder(teal, 2));
        panel.add(usernameField, gridCons);

        // Password Label (above the text field)
        gridCons.gridx = 0;
        gridCons.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        panel.add(passwordLabel, gridCons);

        // Password Text Field
        gridCons.gridx = 0;
        gridCons.gridy = 3;
        passwordField = new JPasswordField(20);
        passwordField.setFont(fieldFont);
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBorder(BorderFactory.createLineBorder(teal, 2));
        panel.add(passwordField, gridCons);

        // Login Button with teal background
        gridCons.gridx = 0;
        gridCons.gridy = 4;
        gridCons.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setBackground(teal);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 40));
        panel.add(loginButton, gridCons);

        // Error Label (initially empty)
        gridCons.gridx = 0;
        gridCons.gridy = 5;
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel, gridCons);

        add(panel);

        // Set loginButton as the default button so that pressing Enter triggers its action.
        getRootPane().setDefaultButton(loginButton);

        // Action listener for the login button that performs validation
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve the entered username and password
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Simple validation against predefined credentials
                if (username.equals("admin") && password.equals("admin123")) {
                    errorLabel.setText(""); // Clear error message if any
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // Assumes GUITest.java defines a class GUITest that extends JFrame
                            new homeGUI().setVisible(true);
                        }
                    });
                } else {
                    // Display error message if credentials don't match
                    errorLabel.setText("Username or password are incorrect");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new loginPage().setVisible(true);
            }
        });
    }
}
