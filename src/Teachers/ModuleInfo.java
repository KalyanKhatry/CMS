package Teachers;

import Databases.Database;
import Databases.DatabaseTeacher;
import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class ModuleInfo implements ActionListener {

    Helper func = new Helper();
    GuiComponents component = new GuiComponents();
    DatabaseTeacher dbTeacher = new DatabaseTeacher();
    private final Connection connection;
    private final JFrame frame = func.createFrame(600, 400);
    private final String moduleCode;

    ModuleInfo(JFrame prevFrame, String moduleCode){
        Database db = new Database();
        this.connection = db.createConnection();
        this.moduleCode= moduleCode;
        heading();
        addMarks();
        backButton(prevFrame);
    }

    void heading(){
        String moduleName = dbTeacher.getModuleName(connection, moduleCode);
        System.out.println(moduleName);
        JLabel label = component.createLabel(frame, moduleName);
        label.setBounds(70, 30, 450, 40);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    void addMarks(){

        ArrayList<String> studentsArray =  dbTeacher.getStudents(connection, moduleCode);

//        if(studentsArray.size() != 0){
        JLabel addMarksLabel = component.createLabel(frame, "Add marks of students: ");
        addMarksLabel.setBounds(420,190, 200,40);

        JButton addMarksButton = component.createButton(frame, "Add");
        addMarksButton.setBounds(420,220,100,40 );

        addMarksButton.addActionListener(this);



        JLabel label = component.createLabel(frame, "Students");
        label.setBounds(80, 90, 100, 40);
        func.setFontSize(label,15);

        if(studentsArray.size() == 0){
            JLabel studentLabel = component.createLabel(frame,"No students are enrolled in this module");
            studentLabel.setBounds(80, 270 , 300, 40);
            return;
        }

        for(int i = 0; i < studentsArray.size(); i++){
            String studentName = (i + 1) + ":  " + studentsArray.get(i);
            JLabel studentLabel = component.createLabel(frame,studentName);
            studentLabel.setBounds(80, 110 + (i * 30), 250, 40);
        }
    }

        private void backButton(JFrame prevFrame){
        JButton backButton = component.createButton(frame, "Back");
        backButton.setBounds(420, 270,100, 40 );

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.setVisible(false);
        });
    }
    @Override
    public void actionPerformed(ActionEvent e){
        new StudentMarks(frame, moduleCode);
        frame.dispose();
    }

}
