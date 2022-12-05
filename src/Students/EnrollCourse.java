package Students;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import Databases.AdminDatabase;
import Databases.Database;
import Databases.DatabaseStudent;
import GUI.GuiComponents;
import GUI.Helper;

public class EnrollCourse implements ActionListener {
    Helper func = new Helper();
    private final GuiComponents components = new GuiComponents();
    DatabaseStudent dbStudent = new DatabaseStudent();
    private final JFrame frame = func.createFrame(600, 450);
    JLabel errorLabel;
    JComboBox cb;
    String courseName;
    String studentId;
    ArrayList<String> studentInfo;

    public EnrollCourse(JFrame prevFrame, ArrayList<String> studentInfo) {
        String studentName = studentInfo.get(0);

        welcomeLabel(studentName);
        JComboBox cb = chooseCourse();
        this.cb = cb;
        enrollButton(studentInfo);
        backButton(prevFrame);

        JLabel errorLabel = components.createLabel(frame, "");
        errorLabel.setBounds(150, 240, 400, 50);
        this.errorLabel = errorLabel;
    }

    void welcomeLabel(String name) {
        String welcomeString = "Welcome " + name + " !";
        JLabel label = components.createLabel(frame, welcomeString);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setBounds(180, 20, 400, 40);
    }

    JComboBox chooseCourse() {

        JLabel label = components.createLabel(frame, "CHOOSE A COURSE TO ENROLL");
        label.setBounds(170, 80, 300, 40);

        Database db = new Database();
        Connection connection = db.createConnection();
        AdminDatabase dbAdmin = new AdminDatabase();

        Set<String> setCourses = dbAdmin.getAllCourses(connection);
        String[] courses = new String[setCourses.size()];

        // Adding every element in hashset to an array
        int i = 0;
        for (String ele : setCourses) {
            courses[i++] = ele;
        }

        JComboBox<String> cb = new JComboBox<String>(courses);
        cb.setBounds(200, 150, 160, 20);
        frame.add(cb);
        frame.setSize(601, 450);
        return cb;
    }

    void enrollButton( ArrayList<String> studentInfo) {
        JButton button = components.createButton(frame, "Enroll");
        button.setBounds(300, 220, 100, 40);

        courseName = studentInfo.get(1);
        studentId = studentInfo.get(4);
        this.studentInfo= studentInfo;
        button.addActionListener(this);
    }

    void backButton(JFrame prevFrame) {
        JButton backButton = components.createButton(frame, "Back");
        backButton.setBounds(150, 220, 100, 40);

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.dispose();
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedCourse = (String) cb.getItemAt(cb.getSelectedIndex());

        Database db = new Database();
        Connection connection = db.createConnection();

        int cancelled = db.checkIfCancelled(connection, selectedCourse);

        if (cancelled == 1) {
            errorLabel.setText("The course has been cancelled");
            return;
        }

        // Proceed only if the student isn't already enrolled in another course
        if (Objects.equals(courseName, selectedCourse)) {
            new EnrollModule(frame, selectedCourse, studentInfo);
            frame.dispose();

        } else if (courseName == null || courseName.equals("")) {
            dbStudent.updateStudentCourse(connection, studentId, selectedCourse);
            new EnrollModule(frame, selectedCourse, studentInfo);
            frame.dispose();

        } else {
            JOptionPane.showMessageDialog(frame, "You have already enrolled in another course !");
        }
    }

}

