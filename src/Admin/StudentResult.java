package Admin;
import Databases.Database;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;


public class StudentResult {
    GuiComponents component = new GuiComponents();
    Helper func = new Helper();
    JFrame frame = func.createFrame(600, 400);
    private String id;
    private String moduleCode;
    private String marks;

    private String stName;
    private String stLevel;
    private String stSemester;
    private final JLabel headingLabel;
    private final JLabel headingLabel1;
    private final JLabel moduleCodeLabel;
    private final JLabel moduleCodeLabel1;
    private final JLabel moduleCodeLabel2;
    private final JLabel marksLabel;
    private final JLabel marksLabel1;
    private final JLabel marksLabel2;
    private final JLabel nameLabel;
    private final JLabel levLabel;
    private final JLabel semLabel;
    private final JLabel decisionLabel;

    StudentResult(JFrame prevFrame){
        headingLabel = component.createLabel(frame, " ");
        headingLabel.setBounds(350, 100, 100, 40);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 15));

        headingLabel1 = component.createLabel(frame, "");
        headingLabel1.setBounds(500, 100, 100, 40);
        headingLabel1.setFont(new Font("Arial", Font.BOLD, 15));

         moduleCodeLabel = component.createLabel(frame, "");
         moduleCodeLabel1 = component.createLabel(frame, "");
         moduleCodeLabel2 = component.createLabel(frame, "");

         marksLabel = component.createLabel(frame, "");
         marksLabel1 = component.createLabel(frame, "");
         marksLabel2 = component.createLabel(frame, "");

         nameLabel = component.createLabel(frame,"");
        nameLabel.setBounds(110, 100, 300, 40);

         levLabel = component.createLabel(frame,"");
        levLabel.setBounds(110, 120, 300, 40);

         semLabel = component.createLabel(frame,"");
        semLabel.setBounds(110, 140, 300, 40);

         decisionLabel = component.createLabel(frame, "");
        decisionLabel.setBounds(140, 300, 400, 40);

        viewResults();
    }

    void viewResults(){
        JLabel viewLabel = component.createLabel(frame, "University ID: ");
        viewLabel.setBounds(5,35,200, 40);
        func.setFontSize(viewLabel, 15);

        JTextField idField = component.createTextField(frame);
        idField.setBounds(110, 40, 150, 30);
       // idField.setFont(new Font());

        JButton viewButton = component.createButton(frame, "View Result");
        viewButton.setBounds(270, 40, 100, 30);

        viewButton.addActionListener(e -> {
            this.id = idField.getText();

            if(Objects.equals(id, "")){
                JOptionPane.showMessageDialog(frame, "Id cannot be empty");
                return;
            }

            try {
                ArrayList<String[]> results = getResults(id);

                JPanel errPanel = new JPanel();
                JLabel errLabel = component.createLabel(frame, "");

                frame.add(errPanel);
                errPanel.setBounds(0,0, 700, 30);
                errPanel.setBackground(Color.gray);

                errPanel.remove(errLabel);

                headingLabel.setText("Module");

                headingLabel1.setText("Marks");

                if(results == null){
                    errLabel.setText("Student not found");
                    return;
                }

                if (results.size() >= 1) {
                    String moduleCode = results.get(0)[1];
                    String marks = results.get(0)[2];

                    moduleCodeLabel.setBounds(350, 130 , 150, 40);
                    moduleCodeLabel.setText(moduleCode);

                    marksLabel.setBounds(500, 130, 150, 40);
                    marksLabel.setText(marks);
                }
                if (results.size() >= 2) {
                    String moduleCode = results.get(1)[1];
                    String marks = results.get(1)[2];

                    moduleCodeLabel1.setBounds(350, 170 , 150, 40);
                    moduleCodeLabel1.setText(moduleCode);

                    marksLabel1.setBounds(500, 170, 150, 40);
                    marksLabel1.setText(marks);
                }

                if (results.size() >= 3) {
                    String moduleCode = results.get(2)[1];
                    String marks = results.get(2)[2];

                    moduleCodeLabel2.setBounds(350, 210, 150, 40);
                    moduleCodeLabel2.setText(moduleCode);

                    marksLabel2.setBounds(500, 210, 150, 40);
                    marksLabel2.setText(marks);
                }

                try{
                    ArrayList<String[]> studentInfo = getStudentInfo(id);

                    if(studentInfo == null || studentInfo.size() == 0){
                        JOptionPane.showMessageDialog(frame, "Student not found!");
                        return;
                    }

                    stName = studentInfo.get(0)[0];
                    stLevel = studentInfo.get(0)[1];
                    stSemester = studentInfo.get(0)[2];

                    nameLabel.setText("Name:   " + stName);

                    levLabel.setText("Level:   " + stLevel);

                    semLabel.setText("Semester:   " + stSemester);


                } catch (Exception error) {
                    error.printStackTrace();
                }
                decision();


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    void decision(){
        boolean decision = true;

        try{
            ArrayList<String[]> results = getResults(id);

            for(int i = 0; i < results.size(); i++){
                String marks = results.get(i)[2];
                if(Integer.parseInt(marks) < 40){
                    decision = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        if(decision){
            decisionLabel.setText(" This student can progress to the next level !");
        }else{
            decisionLabel.setText(" This student cannot progress to the next level !");
        }
    }
    private ArrayList<String[]> getStudentInfo(String id) throws Exception{

        if(Objects.equals(id, "")){
            System.out.println("Invalid Input. Please check the field again");
            return null;
        }

        Database db = new Database();
        Connection connection = db.createConnection();

        Statement statement = connection.createStatement();
        String selectStudentsQuery = "SELECT * FROM students WHERE ID = '" + id + "'";

        ResultSet rs = statement.executeQuery(selectStudentsQuery);

        ArrayList<String[]> stInfo = new ArrayList<>();

        while(rs.next()){

            String[] info = new String[3];

            info[0] = rs.getString("name");
            info[1] = rs.getString("level");
            info[2] = rs.getString("semester");

            stInfo.add(info);
        }
        return stInfo;

    }

    ArrayList<String[]> getResults(String id) throws Exception{
        if(Objects.equals(id, "")){
            JOptionPane.showMessageDialog(frame, "Please, Check your Inputs !");
            return null;
        }

        Database db = new Database();
        Connection connection = db.createConnection();

        Statement statement = connection.createStatement();
        String selectStudentsQuery = "SELECT * FROM students_marks WHERE ID = '" + id + "'";

        ResultSet rs = statement.executeQuery(selectStudentsQuery);

        ArrayList<String[]> results = new ArrayList<>();

        while(rs.next()){

            String[] result = new String[3];

            result[0] = rs.getString("id");
            result[1] = rs.getString("module_code");
            result[2] = rs.getString("marks");

            results.add(result);
        }
        return results;
    }


}
