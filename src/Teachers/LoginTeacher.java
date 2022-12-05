package Teachers;

import Databases.Database;
import Databases.DatabaseStudent;
import GUI.GuiComponents;
import GUI.Helper;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class LoginTeacher {
    Helper func = new Helper();
    DatabaseStudent dbTeacher = new DatabaseStudent();

    GuiComponents component = new GuiComponents();
    JFrame frame = func.createFrame(500, 400);

    public LoginTeacher(JFrame prevFrame, Database db, Connection connection){

        JLabel errorLabel = component.createLabel(frame, "");
        errorLabel.setBounds(120, 300, 300, 20);

        JLabel label = component.createLabel(frame, "Login as Teacher");
        label.setBounds(180, 20, 200, 30);
        func.setFontSize(label,18);


        JLabel idLabel = component.createLabel(frame, "Teacher Id ");
        idLabel.setBounds(100, 80, 120, 35);
        func.setFontSize(label,16);

        JTextField textFieldId = component.createTextField(frame);
        textFieldId.setBounds(200,80, 120, 35 );


        JLabel passwordLabel = component.createLabel(frame, "Password ");
        passwordLabel.setBounds(100, 130, 120, 35);
        func.setFontSize(label,16);


        JPasswordField textFieldPass = new JPasswordField();
        frame.add(textFieldPass);
        textFieldPass.setBounds(200, 130,120, 35 );


        // Login button
        JButton loginButton = component.createButton(frame, "Login");
        loginButton.setBounds(250, 220, 100, 40);

        // Button to return to previous frame
        backButton(prevFrame);

        // Using action listener to get data
        loginButton.addActionListener(e -> {
            String instructorId = textFieldId.getText();
            String password = textFieldPass.getText();

            if(Objects.equals(instructorId, "") || Objects.equals(password, "")){
                errorLabel.setText("The fields cannot be empty");
                return;
            }

            // Checking if the provided input "id" is an integer or not
            try {
                Integer.parseInt(instructorId);
            } catch (NumberFormatException err) {
                err.printStackTrace();
                errorLabel.setText("Input error. Check your fields again");
                return;
            }


            if(connection != null){
                try{
                    ResultSet rs = db.getUniqueTeacher(connection, instructorId, password);
                    ArrayList<String> teacherInfo = dbTeacher.getUniqueTeacher(rs);

                    // Creating new panel after login
                    if(teacherInfo.size() != 0){
                        new ModuleAssigned(frame,teacherInfo);
                        frame.setVisible(false);
                    }else{
                        errorLabel.setText("The login detail does not match");
                    }

                }catch(NoSuchElementException err){
                    err.printStackTrace();
                    errorLabel.setText("The login detail does not match");
                }
            }
        });
    }

    private void backButton(JFrame prevFrame){
        JButton backButton = component.createButton(frame, "Back");
        backButton.setBounds(120, 220,100, 40 );

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.setVisible(false);
        });
    }
}
