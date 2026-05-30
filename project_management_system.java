import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

class Project {
    String name;
    String manager;
    String status;

    Project(String name, String manager, String status) {
        this.name = name;
        this.manager = manager;
        this.status = status;
    }
}

public class ProjectManagementSystem {
    JFrame frame;
    JTextField nameField, managerField;
    JComboBox<String> statusBox;
    JTable table;
    DefaultTableModel model;

    ArrayList<Project> projects = new ArrayList<>();

    ProjectManagementSystem() {
        frame = new JFrame("Project Management System");
        frame.setSize(600, 400);
        frame.setLayout(null);

        // Labels
        JLabel nameLabel = new JLabel("Project Name:");
        nameLabel.setBounds(20, 20, 100, 25);
        frame.add(nameLabel);

        JLabel managerLabel = new JLabel("Manager:");
        managerLabel.setBounds(20, 60, 100, 25);
        frame.add(managerLabel);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(20, 100, 100, 25);
        frame.add(statusLabel);

        // Inputs
        nameField = new JTextField();
        nameField.setBounds(130, 20, 150, 25);
        frame.add(nameField);

        managerField = new JTextField();
        managerField.setBounds(130, 60, 150, 25);
        frame.add(managerField);

        String[] status = {"Ongoing", "Completed", "Pending"};
        statusBox = new JComboBox<>(status);
        statusBox.setBounds(130, 100, 150, 25);
        frame.add(statusBox);

        // Buttons
        JButton addBtn = new JButton("Add");
        addBtn.setBounds(320, 20, 100, 30);
        frame.add(addBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(320, 60, 100, 30);
        frame.add(deleteBtn);

        // Table
        model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Manager");
        model.addColumn("Status");

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 150, 540, 180);
        frame.add(sp);

        // Add Button Action
        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            String manager = managerField.getText();
            String statusVal = (String) statusBox.getSelectedItem();

            projects.add(new Project(name, manager, statusVal));
            model.addRow(new Object[]{name, manager, statusVal});

            nameField.setText("");
            managerField.setText("");
        });

        // Delete Button Action
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                projects.remove(row);
                model.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(frame, "Select a row first!");
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ProjectManagementSystem();
    }
}