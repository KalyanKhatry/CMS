package Teachers;

import GUI.GuiComponents;
import GUI.Helper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ModuleAssigned {
    private Helper func = new Helper();
    private GuiComponents component = new GuiComponents();

    JFrame frame = func.createFrame(500, 450);

    ModuleAssigned(JFrame prevFrame, ArrayList<String> teacherInfo) {
        String teacherName = teacherInfo.get(0);
        String moduleCodes = teacherInfo.get(2);

        welcomeTeacher(teacherName, moduleCodes);
        assignedModules(moduleCodes);
        backButton(prevFrame);
    }

    void welcomeTeacher(String teacherName, String moduleCodes) {

        String welcomeString = "Welcome " + teacherName + "!";
        JLabel headingLabel = component.createLabel(frame, welcomeString);
        headingLabel.setBounds(150, 30, 350, 30);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));

    }

    void assignedModules(String moduleCodes) {
        if (moduleCodes == null) {
            moduleCodes = "";
        }
        String[] moduleCodesArr = func.splitModuleCode(moduleCodes);

        JLabel label;
        if (moduleCodesArr.length == 0) {
            label = component.createLabel(frame, "You are not assigned any modules !");
        } else {
            label = component.createLabel(frame, "These modules are assigned to you !");
        }
        label.setBounds(130, 50, 400, 100);
        func.setFontSize(label,15);

        for (int i = 0; i < moduleCodesArr.length; i++) {
            String moduleCode = moduleCodesArr[i];
            JButton button = component.createButton(frame, moduleCode);
            button.setBounds(180, 120 + (i * 50), 100, 60);

            button.addActionListener(e -> {
                new ModuleInfo(frame, moduleCode);
                frame.setVisible(false);
            });
        }
    }

    private void backButton(JFrame prevFrame) {
        JButton backButton = component.createButton(frame, "Back");
        backButton.setBounds(370, 350, 100, 40);

        backButton.addActionListener(e -> {
            prevFrame.setVisible(true);
            frame.dispose();
        });
    }
}