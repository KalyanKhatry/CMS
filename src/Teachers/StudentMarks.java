package Teachers;

import Databases.Database;
import Databases.DatabaseTeacher;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class StudentMarks {
    Helper func = new Helper();
    GuiComponents component = new GuiComponents();
    DatabaseTeacher dbTeacher = new DatabaseTeacher();
    JFrame frame = func.createFrame(350, 400);

    private final String moduleCode;
    private JTextField studentIdField;
    private JTextField studentMarksField;
    private final Connection connection;

    private final ArrayList<String> studentId;
    private final JLabel errLabel;

    StudentMarks(JFrame prevFrame, String moduleCode){
        Database db = new Database();
        Connection connection = db.createConnection();

        ArrayList<String> studentId = dbTeacher.getStudentId(connection, moduleCode);

        this.moduleCode = moduleCode;
        this.connection = connection;
        this.studentId = studentId;

        JLabel errLabel = component.createLabel(frame, "");
        errLabel.setBounds(80, 330, 500, 40);
        this.errLabel = errLabel;

        addMarks();
        addButton();
        backButton(prevFrame);
    }

    private void addMarks(){
        JLabel stIdLabel = component.createLabel(frame, "University Id");
        stIdLabel.setBounds(90,20,280,40);
        func.setFontSize(stIdLabel,15);

        studentIdField = component.createTextField(frame);
        studentIdField.setBounds(90, 60, 140, 35);

        JLabel stMarksLabel = component.createLabel(frame, "Mark");
        stMarksLabel.setBounds(90,120,280,40);
        func.setFontSize(stMarksLabel,15);

        studentMarksField = component.createTextField(frame);
        studentMarksField.setBounds(90, 160, 100, 35);

    }

    private void addButton(){
        JButton addMarksButton = component.createButton(frame, "Add marks");
        addMarksButton.setBounds(80, 230,160, 40);

        addMarksButton.addActionListener(e -> {
            String marks = studentMarksField.getText();
            String id = studentIdField.getText();

            try {
                Integer.parseInt(marks);
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(frame, "Incorrect  Input !");
                return;
            }

            try {
                addMarksDb(marks, id);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Please, Check again !");
            }
        });
    }

    private void addMarksDb(String marks, String id) throws Exception{


        if(Objects.equals(marks, "") || Objects.equals(id, "")){
            JOptionPane.showMessageDialog(frame, "Please, Check your Input !");
            return;
        }

        if(!studentId.contains(id)){
            JOptionPane.showMessageDialog(frame, "Student is not enrolled in this module !");
            return;
        }

        // SELECTING STUDENT WITH GIVEN id AND module code TO SEE IF IT EXISTS

        errLabel.setText("Marks added to this student");

        Statement statement = connection.createStatement();
        String selectStudentsQuery = "SELECT * FROM students_marks WHERE ID = '" + id + "'" + "AND MODULE_CODE = '" + moduleCode + "'";

        ResultSet rs = statement.executeQuery(selectStudentsQuery);

        if(rs.next()){
            String updateStudentMarks = "UPDATE students_marks SET MARKS = ? WHERE ID = ? AND MODULE_CODE = ?";

            PreparedStatement statement1 = connection.prepareStatement(updateStudentMarks);
            statement1.setString(1, marks);
            statement1.setString(2, id);
            statement1.setString(3, moduleCode);
            statement1.executeUpdate();

        }
        else{
            String insertStudentMarks = "INSERT INTO students_marks(ID, MODULE_CODE, MARKS) VALUES (?, ?, ?)";

            PreparedStatement statement2 = connection.prepareStatement(insertStudentMarks);
            statement2.setString(1, id);
            statement2.setString(2, moduleCode);
            statement2.setString(3, marks);
            statement2.executeUpdate();
        }
    }

    private void backButton(JFrame prevFrame){
        JButton backButton = component.createButton(frame, "Back");
        backButton.setBounds(110, 280,100, 30 );

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.dispose();
        });
    }


}
