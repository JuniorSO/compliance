package org.example;

import javax.swing.*;

public class Admin {
    public JPanel Admin;
    private JTextField fldLogin;
    private JTextField fldSenha;
    private JButton btnVoltar;
    private JButton btnEntrar;

    public Admin() {
        btnVoltar.addActionListener(e -> {
            if(Main.regisFrame == null) {
                Main.regisFrame = new JFrame("Admin");
                Main.regisFrame.setContentPane(new Admin().Admin);
                Main.regisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Main.regisFrame.pack();
            }
            Main.regisFrame.setVisible(true);

            Regis.adminFrame.setVisible(false);
        });
        btnEntrar.addActionListener(e -> {
            if(fldSenha.getText().isEmpty() || fldLogin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os dois campos.");
            }
            else {

            }
        });
    }
}
