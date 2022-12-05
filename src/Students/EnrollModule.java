package Students;

import Databases.Database;
import Databases.DatabaseStudent;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;


public class EnrollModule {
    private final GuiComponents components = new GuiComponents();
    Helper func = new Helper();
    DatabaseStudent dbStudent = new DatabaseStudent();

    private final JFrame frame = func.createFrame(500, 500);

    EnrollModule(JFrame prevFrame, String selectedCourse, ArrayList<String> studentInfo){
        String level = studentInfo.get(3);
        System.out.println(level);

        headingLabel(selectedCourse, Objects.equals(level, "0") ? "4" : level);
        modules(studentInfo);
        backButton(prevFrame);
    }

    void headingLabel(String courseName, String level){
        String headingText = "Available Modules For " + courseName + " Level " + level ;
        JLabel label = components.createLabel(frame,headingText);
        label.setBounds(50, 25, 400, 40);
        label.setFont(new Font("Arial", Font.BOLD, 15));
    }

    void modules(ArrayList<String> studentInfo){
        Database db = new Database();
        Connection connection = db.createConnection();

        String id = studentInfo.get(4);
        String password = studentInfo.get(6);

        ArrayList<String> newStudentInfo = db.getUniqueStudent(connection, Integer.parseInt(id), password);

        String courseName = newStudentInfo.get(1);
        String level = newStudentInfo.get(3);
        String semester = newStudentInfo.get(5);

        // Creating result sets from the database with specified parameters
        ResultSet rs = db.getModules(connection, level, semester, courseName);
        ResultSet rs1 = db.getModules(connection, level, semester, courseName);
        ResultSet rs2 = db.getModules(connection, level, semester, courseName);

        if(rs == null){
            JLabel errLabel = components.createLabel(frame, "No modules found...");
            errLabel.setBounds(60, 350, 200, 40);
        }else{
            // module codes and module names from the result set
            ArrayList<String> moduleCodes = dbStudent.getModuleCodes(rs);
            ArrayList<String> moduleNames = dbStudent.getModuleNames(rs1);
            ArrayList<String> moduleTeachers = dbStudent.getModuleTeachers(rs2);

            int moduleLength =  moduleCodes.size();

            if(moduleLength == 0){
                JLabel errLabel = components.createLabel(frame, "No modules found...");
                errLabel.setBounds(60, 100, 200, 40);
            }else{
                if(Objects.equals(level, "6")){
                    JLabel optionalLabel = components.createLabel(frame, "You have 1 optional module to enroll in");
                    optionalLabel.setBounds(60, 70, 300, 40);
                }

                for(int i = 0; i < moduleLength; i++){
                    JButton button = components.createButton(frame, moduleCodes.get(i));
                    button.setBounds(60, 110 + (i * 70), 100, 40);

                    String moduleName = moduleNames.get(i);
                    String moduleCode = moduleCodes.get(i);
                    String moduleTeacher = moduleTeachers.get(i);

                    button.addActionListener(e -> {
                        new StudentModule(frame, studentInfo, moduleName,moduleCode, moduleTeacher);
                        frame.dispose();
                    });
                }
            }
        }
    }

    private void backButton(JFrame prevFrame){
        JButton backButton = components.createButton(frame, "Back");
        backButton.setBounds(200, 350,80, 35 );

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.dispose();
        });
    }
}
