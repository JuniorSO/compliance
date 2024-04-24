package org.example;

import javax.swing.*;

public class Table {
    public JPanel Table;
    private JButton voltarRegisButton;

    public Table() {
        voltarRegisButton.addActionListener(e -> {
            if(Main.regisFrame == null) {
                Main.regisFrame = new JFrame("Admin");
                Main.regisFrame.setContentPane(new Admin().Admin);
                Main.regisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Main.regisFrame.pack();
            }
            Main.regisFrame.setVisible(true);

            Admin.tableFrame.setVisible(false);
        });
    }
}
