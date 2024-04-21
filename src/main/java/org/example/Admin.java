package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin {
    public JPanel Admin;
    private JTextField textField1;
    private JTextField textField2;
    private JButton voltarButton;
    private JButton entrarButton;

    public Admin() {
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Main.regisFrame == null) {
                    Main.regisFrame = new JFrame("Admin");
                    Main.regisFrame.setContentPane(new Admin().Admin);
                    Main.regisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    Main.regisFrame.pack();
                    Main.regisFrame.setVisible(true);
                }
                else {
                    Main.regisFrame.setVisible(true);
                }
                Regis.adminFrame.setVisible(false);
            }
        });
    }
}
