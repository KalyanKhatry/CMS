package Admin;

import Databases.AdminDatabase;
import Databases.Database;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;

public class ModuleInfo {
    GuiComponents components = new GuiComponents();
    Helper func = new Helper();
    AdminDatabase dbAdmin = new AdminDatabase();

    JFrame frame = func.createFrame(650,400);
    JFrame prevFrame;

    ModuleInfo(JFrame prevFrame, String moduleCode){
        Database db = new Database();
        Connection connection = db.createConnection();
        this.prevFrame = prevFrame;

        ArrayList<String> moduleInfo = dbAdmin.getModuleInfo(connection, moduleCode);
        String moduleName = moduleInfo.get(0);
        String courseName = moduleInfo.get(1);
        String level = moduleInfo.get(2);
        String semester = moduleInfo.get(3);
        String teachers = moduleInfo.get(4);

        heading(moduleName, moduleCode);
        moduleInfo(courseName, level, semester, teachers, moduleCode);
        changeModuleInfo(prevFrame,moduleCode);
        deleteModule(connection, moduleCode);
        backButton(prevFrame);
    }

    void heading(String moduleName, String moduleCode){
        String headingString = "(" + moduleCode + ") " + moduleName;
        JLabel headingLabel = components.createLabel(frame, headingString);
        headingLabel.setBounds(60, 30, 600, 50);
        func.setFontSize(headingLabel,15);
    }

    void moduleInfo(String courseName, String level, String semester, String teachers, String moduleCode){

        // Course name of the module
        JLabel courseLabel = components.createLabel(frame, "Course: " + courseName);
        courseLabel.setBounds(60, 70, 200, 40);

        // Module level
        JLabel levelLabel = components.createLabel(frame, "Level: " + level);
        levelLabel.setBounds(60, 100, 100, 40);

        // Module semester
        JLabel semesterLabel = components.createLabel(frame, "Semester: " + semester);
        semesterLabel.setBounds(60, 130, 100, 40);

        // Teachers for the module
        JLabel teacherHeading = components.createLabel(frame, "Teachers");
        teacherHeading.setBounds(500, 70, 200, 40);
        func.setFontSize(teacherHeading,15);

        // Button to edit Teachers
        JButton editTeacherButton = components.createButton(frame, "Edit Teachers");
        editTeacherButton.setBounds(60, 300 , 150, 40);

        if(teachers == null || teachers.equals("")){
            JOptionPane.showMessageDialog(frame, "No teachers added to this module !");

            String[] emptyTeachers = new String [0];

            editTeacherButton.addActionListener(e -> {
                new EditTeachers(prevFrame, frame, moduleCode, emptyTeachers, teachers);
            });

        }else{
            String[] teachersArr = func.splitTeachers(teachers);
            for(int i = 0; i < teachersArr.length; i++){
                JLabel teacherLabel = components.createLabel(frame, teachersArr[i]);
                teacherLabel.setBounds(500, 100 + (i * 30) , 200, 40);
            }

            editTeacherButton.addActionListener(e -> {
                new EditTeachers(prevFrame, frame,moduleCode, teachersArr, teachers);
            });

        }
    }

    void deleteModule(Connection connection, String moduleCode){
        JButton deleteModuleButton = components.createButton(frame, "Delete Module");
        deleteModuleButton.setBounds(280,300, 150, 40);

        deleteModuleButton.addActionListener(e -> {
            dbAdmin.deleteModule(connection,moduleCode);
            dbAdmin.deleteModuleFromStudents(connection, moduleCode);
            new Courses();
            frame.setVisible(false);
            prevFrame.setVisible(false);
        });
    }

    void changeModuleInfo(JFrame prevFrame, String moduleCode){
        JLabel changeModuleLabel = components.createLabel(frame, "Change module name");
        changeModuleLabel.setBounds(60, 200, 200, 40);
        func.setFontSize(changeModuleLabel,13);

        JTextField changeModuleField = components.createTextField(frame);
        changeModuleField.setBounds(60, 230, 200, 30);

        JButton changeButton = components.createButton(frame, "Change");
        changeButton.setBounds(270, 230, 100, 30);

        changeButton.addActionListener(e -> {
            Database db = new Database();
            Connection connection = db.createConnection();

            String newModuleName = changeModuleField.getText();
            dbAdmin.changeModuleName(connection, newModuleName, moduleCode);
            prevFrame.setVisible(true);
            frame.setVisible(false);
        });
    }

    private void backButton(JFrame prevFrame){
        JButton backButton = components.createButton(frame, "Back");
        backButton.setBounds(500, 300,100, 40 );

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.dispose();
        });
    }
}
