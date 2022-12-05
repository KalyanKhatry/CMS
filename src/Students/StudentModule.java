package Students;

import Databases.Database;
import Databases.DatabaseStudent;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

public class StudentModule {
    private final GuiComponents components = new GuiComponents();
    private final Helper func = new Helper();
    DatabaseStudent dbStudent = new DatabaseStudent();

    JFrame frame = func.createFrame(600, 450);
    JFrame prevFrame;

    StudentModule(JFrame prevFrame, ArrayList<String> studentInfo, String moduleName, String moduleCode, String moduleTeachers){
        this.prevFrame = prevFrame;

        moduleHeading(moduleName);
        teachers(moduleTeachers);
        enrollModule(studentInfo, moduleCode);
        backButton();
    }

    void moduleHeading(String moduleName){
        JLabel headingLabel = components.createLabel(frame, moduleName);
        headingLabel.setBounds(80, 30, 400, 40);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
    }

    void teachers(String teachers){
        if(teachers == null || teachers.equals("")){
            JLabel teacherLabel = components.createLabel(frame, "No instructors assigned to this module: ");
            teacherLabel.setBounds(80, 90, 400, 40);
        }else{
            String[] teachersArr = teachers.split(",", 0);

            JLabel teacherLabel = components.createLabel(frame, "Instructors ");
            teacherLabel.setBounds(80, 90, 100, 40);
            teacherLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));

            for(int i = 0; i < teachersArr.length; i++){
                JLabel label = components.createLabel(frame, teachersArr[i]);
                label.setBounds(80, 120 + (i * 30), 130, 40);
            }
        }
    }

    void enrollModule(ArrayList<String> studentInfo, String newModuleCode){
        Database db = new Database();
        Connection connection = db.createConnection();

        String id = studentInfo.get(4);
        String password = studentInfo.get(6);

        // Get new moduleCodes since they can update when student is enrolled
        ArrayList<String> newStudentInfo = db.getUniqueStudent(connection, Integer.parseInt(id), password);
        String newModuleCodes = newStudentInfo.get(2);

        System.out.println("Module Code:" + newModuleCodes);
        if(newModuleCodes == null){
            newModuleCodes = "";
        }
        String[] modulesArr = func.splitModuleCode(newModuleCodes);

        JButton button = components.createButton(frame, "Enroll");
        button.setBounds(170, 250, 100, 35);

        String finalModuleCodes = newModuleCodes;
        button.addActionListener(e -> {
            JLabel enrollLabel = components.createLabel(frame, "");
            enrollLabel.setBounds(100, 320, 400, 35);

            if(modulesArr.length >= 4){
                enrollLabel.setText("Enrolled in 4 Modules in this course");
            }else{
                if(finalModuleCodes.contains(newModuleCode)){
                    JOptionPane.showMessageDialog(frame, "Already Enrolled!");
                }else{
                    dbStudent.updateStudentModules(connection, finalModuleCodes,newModuleCode, id );
                    frame.dispose();
                    prevFrame.setVisible(true);
                }
            }

        });
    }

    private void backButton(){
        JButton backButton = components.createButton(frame, "Back");
        backButton.setBounds(290, 250,100, 35 );
        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.dispose();
        });
    }
}
