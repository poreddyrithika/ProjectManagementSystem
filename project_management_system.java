import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

//Class to store project data
class Project{
    String name;
    String manager;
    String status;
    Project(String n,String m,String s){
        name=n;
        manager=m;
        status=s;
    }
}

public class ProjectManagementSystem{
    JFrame frame;
    JTextField nameField,managerField;
    JComboBox<String> statusBox;
    JTable table;
    DefaultTableModel model;
    ArrayList<Project> projects=new ArrayList<>();
    ProjectManagementSystem(){
		
		//Frame setup
        frame=new JFrame("Project Management System");
        frame.setSize(600,400);
        frame.setLayout(null);
		
		//Labels
        JLabel nameLabel=new JLabel("Project Name:");
        nameLabel.setBounds(20,20,100,25);
        frame.add(nameLabel);
        JLabel managerLabel=new JLabel("Manager:");
        managerLabel.setBounds(20,60,100,25);
        frame.add(managerLabel);
        JLabel statusLabel=new JLabel("Status:");
        statusLabel.setBounds(20,100,100,25);
        frame.add(statusLabel);
		
		//Text fields
        nameField=new JTextField();
        nameField.setBounds(130,20,150,25);
        frame.add(nameField);
        managerField=new JTextField();
        managerField.setBounds(130,60,150,25);
        frame.add(managerField);
		
		//ComboBox(Dropdown)
        String[] status={"Select Status","Ongoing","Completed","Pending"};
        statusBox=new JComboBox<>(status);
        statusBox.setBounds(130,100,150,25);
        frame.add(statusBox);
		
		//Buttons
        JButton addBtn=new JButton("Add");
        addBtn.setBounds(320,20,100,30);
        frame.add(addBtn);
        JButton deleteBtn=new JButton("Delete");
        deleteBtn.setBounds(320,60,100,30);
        frame.add(deleteBtn);
		
        //Table
        model=new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Manager");
        model.addColumn("Status");
        table=new JTable(model);
        JScrollPane sp=new JScrollPane(table);
        sp.setBounds(20,150,540,180);
        frame.add(sp);

        // Load previous data
        loadFromFile();

        // Add Button Action
        addBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String name=nameField.getText().trim();
                String manager=managerField.getText().trim();
                String statusVal=(String)statusBox.getSelectedItem();

                // Validation
                if(name.equals("")||manager.equals("")||statusBox.getSelectedIndex()==0){
                    JOptionPane.showMessageDialog(frame,"Please fill all fields!");
                    return;
                }

                // Add data
                Project p=new Project(name,manager,statusVal);
                projects.add(p);
                model.addRow(new Object[]{name,manager,statusVal});

                // Save to file
                saveToFile();

                // Clear fields
                nameField.setText("");
                managerField.setText("");
                statusBox.setSelectedIndex(0);
            }
        });

        // Delete Button Action
        deleteBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int row=table.getSelectedRow();
                if(row>=0){
                    projects.remove(row);
                    model.removeRow(row);

                    saveToFile();
                } else {
                    JOptionPane.showMessageDialog(frame, "Select a projct row to delete!");
                }
            }
        });

        // Frame visibility
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Method to save data into file
    void saveToFile(){
        try{
            PrintWriter pw=new PrintWriter(new FileWriter("projects.txt"));
            for(Project p:projects){
                pw.println(p.name+","+p.manager+","+p.status);
            }
            pw.close();
        } catch(Exception e){
            System.out.println("Error saving file");
        }
    }

    //Method to load data from file
    void loadFromFile(){
        try {
            BufferedReader br=new BufferedReader(new FileReader("projects.txt"));
            String line;
            while((line=br.readLine())!=null){
                String data[]=line.split(",");
                Project p=new Project(data[0],data[1],data[2]);
                projects.add(p);
                model.addRow(data);
            }
            br.close();
        } catch(Exception e){
            //ignore if file not present
        }
    }
    public static void main(String[] args){
        new ProjectManagementSystem();
    }
}
