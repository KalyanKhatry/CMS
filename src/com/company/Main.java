package com.company;
import Admin.LoginAdmin;
import Databases.Database;
import GUI.GuiComponents;
import GUI.Helper;
import Students.LoginStudent;
import Teachers.LoginTeacher;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main {

//    Main(){
//        labelLogoImage();
//    }
//
//    private JLabel labelLogoImage;
//    private JLabel labelLogoImage() {
//        ImageIcon img = new ImageIcon("C:/Users/HP/Desktop/CourseManagementSystem/src/icons/icon1.png");
//        Image icon = img.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
//        img = new ImageIcon(icon);
//        labelLogoImage = new JLabel();
//        labelLogoImage.setIcon(img);
//        return labelLogoImage;
//    }
    Helper func = new Helper();
    private final GuiComponents component = new GuiComponents();
    private final  JFrame frame = func.createFrame(400, 350);

    Database db = new Database();
    Connection connection = db.createConnection();

    public void studentPanel(){
        // Create student button
        JButton studentButton = component.createButton(frame, "Student");
        studentButton.setBounds(120, 50, 150, 40);

        // Adding an action listener to student button
        studentButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new LoginStudent(frame, db, connection);
                        frame.setVisible(false);
                    }
                });
    }

    public void teacherPanel(){
        // Create teacher button
        JButton instructorButton = component.createButton(frame, "Teacher");
        instructorButton.setBounds(120, 130, 150, 40);

        // Adding an action listener to instructor button
        instructorButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new LoginTeacher(frame, db, connection);
                        frame.setVisible(false);
                    }    }
        );
    }

    public void adminPanel(){
        // Create admin button
        JButton adminButton = component.createButton(frame, "Administrator");
        adminButton.setBounds(120, 210, 150, 40);

        // Adding an action listener to administrator button
        adminButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new LoginAdmin(frame, connection);
                        frame.setVisible(false);
                    }
                });
    }

    Main(){
        studentPanel();
        adminPanel();
        teacherPanel();
    }
    public static void main(String[] args){
        new Main();
    }
}
