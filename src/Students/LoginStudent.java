package Students;

import Databases.Database;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LoginStudent {
//       void Main(){
//        labelLogoImage();
//    }
//        private JLabel labelLogoImage;
//    private JLabel labelLogoImage() {
//        ImageIcon img = new ImageIcon("src/icons/logo1.png");
//        Image icon = img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
//        img = new ImageIcon(icon);
//        labelLogoImage = new JLabel();
//        labelLogoImage.setIcon(img);
//        return labelLogoImage;
//    }

    Helper func = new Helper();
    private final GuiComponents component = new GuiComponents();
    private final JFrame frame = func.createFrame(500, 400);


    public LoginStudent(JFrame prevFrame, Database db, Connection connection){

        JLabel errorLabel = component.createLabel(frame, "");
        errorLabel.setBounds(120, 300, 300, 20);

        JLabel label = component.createLabel(frame, "Login as Student");
        label.setBounds(180, 20, 200, 30);
        func.setFontSize(label,18);

        JLabel idLabel = component.createLabel(frame, "University Id ");
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

        // Using action listener to get data
        loginButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String id = textFieldId.getText();
                        String password = textFieldPass.getText();

                        if (Objects.equals(id, "") || Objects.equals(password, "")) {
                            JOptionPane.showMessageDialog(frame, "This field cannot be empty !");
                            return;
                        }

                        // Checking if the input "id" is an integer or not
                        int studentId;
                        try {
                            studentId = Integer.parseInt(id);
                        } catch (NumberFormatException err) {
                            JOptionPane.showMessageDialog(frame, "Input Error");
                            return;
                        }

                        if (connection != null) {
                            try {
                                ArrayList<String> studentInfo = db.getUniqueStudent(connection, studentId, password);
                                // Creating a new panel after login
                                if (studentInfo != null) {
                                    new EnrollCourse(frame, studentInfo);
                                    frame.dispose();
                                }

                            } catch (NoSuchElementException err) {
                                JOptionPane.showMessageDialog(frame, "Detail Incorrect !");
                            }

                        }
                    }
                });
        backButton(prevFrame);
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
