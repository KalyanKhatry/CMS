package Admin;

import Databases.AdminDatabase;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

public class LoginAdmin {
    Helper func = new Helper();
    AdminDatabase dbAdmin = new AdminDatabase();

    private GuiComponents component = new GuiComponents();
    private JFrame frame = func.createFrame(500, 400);

    public LoginAdmin(JFrame prevFrame, Connection connection){


        JLabel errorLabel = component.createLabel(frame, "");
        errorLabel.setBounds(120, 300, 300, 20);

        JLabel label = component.createLabel(frame, "Login as Administrator");
        label.setBounds(180, 20, 200, 30);
        func.setFontSize(label,18);


        JLabel idLabel = component.createLabel(frame, "Admin Id ");
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

        // Using Input to login
        JButton loginButton = component.createButton(frame, "Login");
        loginButton.setBounds(250, 220, 100, 40);


        // Returning to previous frame
        backButton(prevFrame);

        // Using action listener to get data

        loginButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String id = textFieldId.getText();
                        String password = textFieldPass.getText();

                        try {
                            Integer.parseInt(id);
                        } catch (NumberFormatException err) {
                            JOptionPane.showMessageDialog(frame, "Input Error !");
                            return;
                        }


                        if (connection != null) {
                            try {
                                boolean isAuthenticated = dbAdmin.authenticateAdmin(connection, id, password);

                                // Create new panel after login
                                if (isAuthenticated) {
                                    System.out.println("Admin");
                                    new Courses();
                                    frame.dispose();
                                }

                            } catch (NoSuchElementException err) {
                                JOptionPane.showMessageDialog(frame, "Login details does not match !");
                            }
                        }
                    }


                });
    }

    private void backButton(JFrame prevFrame){
        JButton backButton = component.createButton(frame, "Back");
        backButton.setBounds(120, 220,100, 40 );

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.dispose();
        });
    }
}
