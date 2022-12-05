package Admin;

import Databases.AdminDatabase;
import Databases.Database;
import GUI.GuiComponents;
import GUI.Helper;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class EditTeachers {
    private final GuiComponents component = new GuiComponents();
    Helper func = new Helper();
    AdminDatabase dbAdmin = new AdminDatabase();
    private final JFrame frame = func.createFrame(500,500);
    private final JFrame prevFrame;
    private final JFrame prevFrame1;
    Connection connection;

    JLabel errLabel;

    EditTeachers(JFrame prevFrame, JFrame prevFrame1, String moduleCode, String[] teachersArr, String teachersString){
        Database db = new Database();
        connection = db.createConnection();
        this.prevFrame = prevFrame;
        this.prevFrame1 = prevFrame1;


        JLabel errLabel = component.createLabel(frame, "");
        errLabel.setBounds(60, 320, 400, 40);
        this.errLabel = errLabel;

        removeTeacher(teachersArr, teachersString, moduleCode);
        addTeacher(teachersString, moduleCode, teachersArr);
        backButton(prevFrame);
    }

    void removeTeacher(String[] teachersArr, String teachersString, String moduleCode){
        JLabel removeLabel = component.createLabel(frame, "Choose a teacher to remove");
        removeLabel.setBounds(60, 40, 300, 40);
        func.setFontSize(removeLabel,15);

        if(teachersArr.length == 0) {
            JLabel label = component.createLabel(frame, "No teachers found");
            label.setBounds(60, 90, 200, 40);
        }else{
            JComboBox<String> cb = new JComboBox<>(teachersArr);
            cb.setBounds(60, 90, 160, 30);
            frame.add(cb);
            frame.setSize(500, 501);

            JButton removeButton = component.createButton(frame, "Remove");
            removeButton.setBounds(260, 90, 100, 30);

            removeButton.addActionListener(e -> {
                String selectedTeacher = cb.getItemAt(cb.getSelectedIndex());

                dbAdmin.removeTeacherFromModule(connection,selectedTeacher, teachersString, moduleCode);
                dbAdmin.removeModuleFromTeacher(connection, selectedTeacher, moduleCode);
                new Courses();
                prevFrame.setVisible(false);
                prevFrame1.setVisible(false);
                frame.setVisible(false);
            });
        }
    }


    void addTeacher(String teachersString, String moduleCode, String[] teachersArr){

        JLabel orLabel = component.createLabel(frame, "OR");
        orLabel.setBounds(60, 160, 200, 40 );

        JLabel addTeacherLabel = component.createLabel(frame, "Add a new teacher to this module");
        addTeacherLabel.setBounds(60, 220, 300, 40 );
        func.setFontSize(addTeacherLabel,15);

        JTextField addTeacherField = component.createTextField(frame);
        addTeacherField.setBounds(60, 270, 150, 30);

        JButton addTeacherButton = component.createButton(frame, "Add");
        addTeacherButton.setBounds(260, 270, 100, 30);

        addTeacherButton.addActionListener(e -> {
            String teacherName = addTeacherField.getText();
            String moduleCodes= dbAdmin.getModuleCodesFromTeacher(connection, teacherName);

            ArrayList<String> allTeachers = getAllTeachers();


            if(allTeachers == null || !allTeachers.contains(teacherName)){
                JOptionPane.showMessageDialog(frame, "Teacher name not found !");
                return;
            }

            if(moduleCodes == null){
                moduleCodes = "";
            }

            String[] modulesArr = func.splitModuleCode(moduleCodes);


            if(teachersArr.length >= 4){
                errLabel.setText("This teacher cannot be added to this module");
            }else if(modulesArr.length >= 4){
                errLabel.setText("This teacher cannot be added to this module");
            }
            else{
                if(teachersString == null){
                    dbAdmin.addTeacherIntoModule(connection, teacherName, teachersString, moduleCode);
                    dbAdmin.addModuleIntoTeacher(connection,teacherName, moduleCodes, moduleCode);
                    new Courses();
                    frame.setVisible(false);
                    prevFrame.setVisible(false);
                    prevFrame1.setVisible(false);
                    return;
                }

                if(teachersString.toLowerCase().contains(teacherName.toLowerCase()) && moduleCodes.contains(moduleCode)){
                    errLabel.setText("The teacher already exists");
                    return;
                }

                dbAdmin.addTeacherIntoModule(connection, teacherName, teachersString, moduleCode);
                dbAdmin.addModuleIntoTeacher(connection,teacherName , moduleCodes, moduleCode);
                new Courses();
                frame.setVisible(false);
            }
        });
    }

    private ArrayList<String> getAllTeachers(){

        try{
            ArrayList<String> allTeachers = new ArrayList<>();
            Statement statement = connection.createStatement();
            String getTeachersQuery = "SELECT * FROM TEACHER";

            ResultSet rs = statement.executeQuery(getTeachersQuery);

            while(rs.next()){
                allTeachers.add(rs.getString("name"));
            }
            return allTeachers;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    private void backButton(JFrame prevFrame){
        JButton backButton = component.createButton(frame, "Back");
        backButton.setBounds(190, 400, 80, 40);

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.setVisible(false);
        });

    }

}
