package Admin;

import Databases.AdminDatabase;
import Databases.Database;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;


public class Modules {
    private GuiComponents component = new GuiComponents();
    Helper func = new Helper();
    AdminDatabase dbAdmin = new AdminDatabase();

    private JFrame frame = func.createFrame(600,600);
    String courseName;
    boolean isCancelled = false;
    JLabel errorLabel;

    Modules(JFrame prevFrame, String courseName){
        Database db = new Database();
        Connection connection = db.createConnection();

        this.courseName = courseName;

        this.isCancelled = dbAdmin.checkIfCancelled(connection, courseName);

        // label for error
        JLabel errorLabel = component.createLabel(frame, "");
        errorLabel.setBounds(300, 350, 300, 40);
        this.errorLabel = errorLabel;

        // FUNCTIONS
        heading();
        showModules(connection);
        addNewModule();
        deleteCourse(connection);
        cancelCourse(connection);
        editCourse(prevFrame, connection);
        backButton(prevFrame);
    }

    void heading(){

        if(isCancelled){
            JLabel headingLabel = component.createLabel(frame, "This course has been cancelled.");
            headingLabel.setBounds(60, 20, 400, 40);
            return;
        }

        JLabel headingLabel = component.createLabel(frame, "Available modules for " + courseName);
        headingLabel.setBounds(60, 20, 400, 40);
        func.setFontSize(headingLabel,15);

    }

    void showModules(Connection connection){
        ArrayList<String> modules = dbAdmin.getModuleCodes(connection, courseName);

        System.out.println("Course Name:" + courseName);
        System.out.println("Module Codes: " + modules);

        if(isCancelled){
            return;
        }

        for(int i = 0; i < modules.size() ; i++){
            String moduleCode = modules.get(i);
            JButton moduleButton = component.createButton(frame, moduleCode);
            moduleButton.setBounds(60, 70 + (i * 60), 100, 40);

            moduleButton.addActionListener(e -> {
                new ModuleInfo(frame, moduleCode);
            });
        }
    }

    void addNewModule(){
        JButton addButton = component.createButton(frame,"Add Module");
        addButton.setBounds(40, 400, 150, 40);

        addButton.addActionListener(e -> {
            new AddNewModule(frame, courseName);
        });
    }

    void deleteCourse(Connection connection){

        JButton deleteButton = component.createButton(frame, "Delete Course");
        deleteButton.setBounds(220, 400, 150, 40);

        deleteButton.addActionListener(e -> {
            ArrayList<String> modules = dbAdmin.getModuleCodes(connection, courseName);

            dbAdmin.deleteCourse(connection,courseName);
            dbAdmin.deleteCourseFromTeachers(connection, modules);
            dbAdmin.deleteCourseFromStudents(connection,courseName, modules);
            new Courses();
            frame.setVisible(false);
        });
    }

    void cancelCourse(Connection connection) {
        if (!isCancelled) {
            JButton cancelButton = component.createButton(frame, "Temporary Delete");
            cancelButton.setBounds(400, 400, 150, 40);

            cancelButton.addActionListener(e -> {
                dbAdmin.cancelCourse(connection, true, courseName);
                new Courses();
                frame.setVisible(false);
            });
        } else {
            JButton cancelButton = component.createButton(frame, "Regenerate Course");
            cancelButton.setBounds(400, 400, 180, 40);

            cancelButton.addActionListener(e -> {
                dbAdmin.cancelCourse(connection, false, courseName);
                new Courses();
                frame.setVisible(false);

            });
        }
    }

    void editCourse(JFrame prevFrame, Connection connection){
        JLabel changeLabel = component.createLabel(frame, "Change course name");
        changeLabel.setBounds(330,60,200, 40 );

        JTextField changeField = component.createTextField(frame);
        changeField.setBounds(330, 90, 150, 30);

        JButton changeCourseButton = component.createButton(frame, "Change");
        changeCourseButton.setBounds(480, 90, 100, 30);

        changeCourseButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newCourse = changeField.getText();
                        boolean integer = false;
                        try{
                            Integer.parseInt(newCourse);
                            integer= true;
                        } catch (NumberFormatException numberFormatException) {
                            integer =false;
                        }
                        if(integer){
                            errorLabel.setText("Check your inputs again");
                            return;
                        }

                        if(Objects.equals(newCourse, "")){
                            errorLabel.setText("Name cannot be empty");
                            return;
                        }

                        dbAdmin.changeCourseName(connection, courseName, newCourse);
                        new Courses();
                        frame.setVisible(false);
                    }
                });
    }

    private void backButton(JFrame prevFrame){
        JButton backButton = component.createButton(frame, "Back");
        backButton.setBounds(250, 500, 100, 40);

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.setVisible(false);
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        new Modules(frame, "Computer Science");
    }
}
