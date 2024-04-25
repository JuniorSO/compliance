package org.example;

import javax.swing.*;

import static org.example.Main.regisFrame;
import static org.example.Regis.adminFrame;

public class Admin {
    public JPanel Admin;
    private JTextField fldLogin;
    private JTextField fldSenha;
    private JButton btnVoltar;
    private JButton btnEntrar;
    public static JFrame tableFrame;

    public Admin() {
        btnVoltar.addActionListener(e -> {
            if(regisFrame == null) {
                regisFrame = new JFrame("Admin");
                regisFrame.setContentPane(new Admin().Admin);
                regisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                regisFrame.pack();
            }
            regisFrame.setVisible(true);

            adminFrame.setVisible(false);
        });
        btnEntrar.addActionListener(e -> {
            if(fldSenha.getText().isEmpty() || fldLogin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os dois campos.");
            }
            else {
                boolean result = Main.getLogin(fldSenha.getText(), fldLogin.getText());
                if (result) {
                    fldLogin.setText("");
                    fldSenha.setText("");

                    if(tableFrame == null) {
                        tableFrame = new JFrame("Table");
                        tableFrame.setContentPane(new Table().Table);
                        tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        tableFrame.pack();
                    }
                    tableFrame.setVisible(true);

                    adminFrame.setVisible(false);
                }
            }
        });
    }
}
