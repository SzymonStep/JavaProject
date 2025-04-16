

import javax.swing.*;
import java.awt.*;

/**
 * The Orders panel provides buttons for operating on orders.
 * Unlike the other panels, it does not include an "Add Order" button.
 * (This is because, per your plan, the order operations will depend on the role:
 * customers, staff, or admin.)
 */
public class Orders extends JPanel {

    public Orders() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setBackground(Color.LIGHT_GRAY);

        // Do not include an "Add Order" option
        // Instead, include operations to Update, Delete, and View orders.
        JButton btnUpdate = new JButton("Update Order");
        btnUpdate.addActionListener(e-> {
            // Replace with Update Order logic
            JOptionPane.showMessageDialog(this, "Update Order clicked (demo)");
        });

        JButton btnDelete = new JButton("Delete Order");
        btnDelete.addActionListener(e -> {
            // Replace with Delete Order logic
            JOptionPane.showMessageDialog(this, "Delete Order clicked (demo)");
        });

        JButton btnView = new JButton("View Orders");
        btnView.addActionListener(e -> {
            // Replace with View Orders logic
            JOptionPane.showMessageDialog(this, "View Orders clicked (demo)");
        });

        add(btnUpdate);
        add(btnDelete);
        add(btnView);
    }
}

