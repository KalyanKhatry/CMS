package Admin;

import Databases.AdminDatabase;
import Databases.Database;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.sql.Connection;

public class AddNewModule {
    AdminDatabase dbAdmin = new AdminDatabase();
    private final GuiComponents component = new GuiComponents();
    Helper func = new Helper();

    private final JFrame frame = func.createFrame(700, 600);
    JTextField addModuleField;
    JTextField addModuleCodeField;
    JComboBox<String> cb;
    JComboBox<String> cb1;

    AddNewModule(JFrame prevFrame, String courseName){
        Database db = new Database();
        Connection connection = db.createConnection();

        heading(courseName);
        addModuleName();
        addModuleCode();
        addModuleLevel();
        addModuleSemester();
        addButton(connection, courseName);
        backButton(prevFrame);
    }

    void heading(String courseName){
        JLabel headingLabel = component.createLabel(frame, "" + courseName);
        headingLabel.setBounds(100, 20, 500, 40);
        func.setFontSize(headingLabel,16);


        JLabel headingLabel1 = component.createLabel(frame, "Add a new Module to this course");
        headingLabel1.setBounds(100, 50, 500, 40);

    }

    void addModuleName(){
        JLabel addModuleLabel = component.createLabel(frame, "Module Name");
        addModuleLabel.setBounds(100, 100, 200, 40);

        addModuleField = component.createTextField(frame);
        addModuleField.setBounds(100, 140, 200, 35);
    }

    void addModuleCode(){
        JLabel addModuleLabel = component.createLabel(frame, "Module Code");
        addModuleLabel.setBounds(100, 190, 200, 40);

        addModuleCodeField = component.createTextField(frame);
        addModuleCodeField.setBounds(100, 230, 200, 35);

    }

    void addModuleLevel(){
        JLabel addLevelLabel = component.createLabel(frame, "Level");
        addLevelLabel.setBounds(100, 280, 200, 40);

        String[] levels = {"4", "5", "6"};

        cb = new JComboBox<String>(levels);

        cb.setBounds(100, 320,90,20);
        frame.add(cb);
        frame.setSize(701, 601);
    }

    void addModuleSemester(){
        JLabel addSemesterLabel = component.createLabel(frame, "Semester");
        addSemesterLabel.setBounds(100, 360, 200, 40);

        String[] levels = {"1", "2"};

        cb1 = new JComboBox<String>(levels);
        cb1.setBounds(100, 400,90,20);
        frame.add(cb1);
        frame.setSize(702, 602);
    }

    void addButton(Connection connection, String courseName){

        JButton addButton = component.createButton(frame, "Add");
        addButton.setBounds(220, 480, 100, 40);

        addButton.addActionListener(e -> {
            String moduleName = addModuleField.getText();
            String moduleCode = addModuleCodeField.getText();
            String moduleLevel =  cb.getItemAt(cb.getSelectedIndex());
            String moduleSemester = cb1.getItemAt(cb1.getSelectedIndex());

            // Check for the limit of the modules
            boolean levelLimit = dbAdmin.checkModuleLimit(connection, moduleLevel, moduleSemester, courseName);

            if(levelLimit){
                // Adds the module to the database and returns true or false depending upon the condition
                boolean isAdded = dbAdmin.addModule(connection, courseName, moduleName, moduleCode, moduleLevel, moduleSemester);

                JLabel addModuleLabel = component.createLabel(frame, "");
                addModuleLabel.setBounds(220,520, 200, 40);

                if(isAdded){
                    new Courses();
                    frame.setVisible(false);

                }else{
                    addModuleLabel.setText("Course could not be added.");
                }
            }else{
                JOptionPane.showMessageDialog(frame, "Module Limit reached!");
            }


        });
    }

    private void backButton(JFrame prevFrame){
        JButton backButton = component.createButton(frame, "Back");
        backButton.setBounds(340, 480, 100, 40);

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.setVisible(false);
        });

    }

}
