package Admin;

import Databases.AdminDatabase;
import Databases.Database;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Objects;
import java.util.Set;

public class Courses implements ActionListener {
    AdminDatabase dbAdmin = new AdminDatabase();
    private final GuiComponents component = new GuiComponents();
    Helper func = new Helper();

    private final JFrame frame = func.createFrame(500, 550);

    Courses(){
        allCourses();
        addNewCourse();
        viewResults();
    }

    void allCourses(){
        Database db = new Database();
        Connection connection = db.createConnection();

        Set<String> allCourses = dbAdmin.getAllCourses(connection);

        // Iterating through the list of courses
        int i = 0;
        for(String courseName : allCourses){
            JButton courseButton = component.createButton(frame, courseName);
            courseButton.setBounds(170, 150 + (i * 60), 150, 40 );

            courseButton.addActionListener(e -> {
                new Modules(frame, courseName);
                frame.setVisible(false);
            });
            i++;
        }
    }

    void addNewCourse(){
        Database db = new Database();
        Connection connection = db.createConnection();

        JLabel addCourseLabel = component.createLabel(frame, "Add a new course :");
        addCourseLabel.setBounds(10, 30, 200, 40);

        JTextField addCourseField = component.createTextField(frame);
        addCourseField.setBounds(145, 35, 150, 30);

        JButton addCourseButton = component.createButton(frame, "Add");
        addCourseButton.setBounds(300,34,100, 30 );

        JLabel addCourseTitle = component.createLabel(frame, "Available Courses");
        addCourseTitle.setBounds(150, 100, 200, 40);
        func.setFontSize(addCourseTitle,23);


        addCourseButton.addActionListener(e -> {
            String courseName = addCourseField.getText();

            if(Objects.equals(courseName, "")){
                JOptionPane.showMessageDialog(frame, "Course name cannot be empty !");
            }else{
                new AddNewModule(frame, courseName);
                frame.setVisible(false);
            }

        });
    }

    void viewResults(){
        JLabel viewResult = component.createLabel(frame, "View Student Result :");
        viewResult.setBounds(10, 450, 200, 40);

        JButton viewResultButton = component.createButton(frame, "View results");
        viewResultButton.setBounds(150, 455, 180, 30);

            viewResultButton.addActionListener(this);

    }

    public static void main(String[] args) {

        new Courses();
    }
    public void actionPerformed(ActionEvent e){
        new StudentResult(frame);
    }

}