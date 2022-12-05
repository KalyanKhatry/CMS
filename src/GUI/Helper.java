package GUI;

import javax.swing.*;
import java.awt.*;

public class Helper {

    // Create a new frame
    public JFrame createFrame(int width, int height) {
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setResizable(false);
        return frame;
    }


    // Split module codes
    public String[] splitModuleCode(String moduleCode){
        String[] moduleCodeArr = moduleCode.split(",", 0);
        return moduleCodeArr;
    }

    // Split teachers
    public String[] splitTeachers(String teachers){
        String[] teachersArr = teachers.split(",", 0);
        return teachersArr;
    }

    public void setFontSize(JLabel label, int size){
        label.setFont(new Font("Arial", Font.PLAIN, size));
    }
}
