package GUI;

import javax.swing.*;
import java.awt.*;

public class GuiComponents {

    // Creates and returns a button component
    public JButton createButton(JFrame frame, String name){
        JButton button = new JButton(name);

        frame.add(button); // adds the given frame to the button
        button.setBackground(Color.darkGray);
        button.setForeground(Color.white);
        return button;
    }

    // Creates and returns a label component
    public JLabel createLabel(JFrame frame, String name){
        JLabel label = new JLabel(name);
        label.setFont(new Font("Arial",Font.PLAIN,14));
        frame.add(label);
        return label;
    }

    // Creates and returns a text field component
    public JTextField createTextField(JFrame frame){
        JTextField textField = new JTextField();
        frame.add(textField);
        return textField;
    }

}
