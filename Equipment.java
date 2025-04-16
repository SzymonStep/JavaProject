

import javax.swing.*;
import java.awt.*;

/**
 * The Equipment panel provides buttons for performing operations on Equipment,
 * such as Add, Update, Delete, and View. This panel is meant to be displayed
 * in the main window.
 */
public class Equipment extends JPanel {

    public Equipment() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setBackground(Color.LIGHT_GRAY);

        JButton btnAdd = new JButton("Add Equipment");
        btnAdd.addActionListener(e-> {
            // Replace with Add Equipment logic
            JOptionPane.showMessageDialog(this, "Add Equipment clicked (demo)");
        });

        JButton btnUpdate = new JButton("Update Equipment");
        btnUpdate.addActionListener(e-> {
            // Replace with Update Equipment logic
            JOptionPane.showMessageDialog(this, "Update Equipment clicked (demo)");
        });

        JButton btnDelete = new JButton("Delete Equipment");
        btnDelete.addActionListener(e -> {
            // Replace with Delete Equipment logic
            JOptionPane.showMessageDialog(this, "Delete Equipment clicked (demo)");
        });

        JButton btnView = new JButton("View Equipment");
        btnView.addActionListener(e -> {
            // Replace with View Equipment logic
            JOptionPane.showMessageDialog(this, "View Equipment clicked (demo)");
        });

        add(btnAdd);
        add(btnUpdate);
        add(btnDelete);
        add(btnView);
    }
}

