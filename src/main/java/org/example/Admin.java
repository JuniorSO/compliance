package org.example;

import javax.swing.*;

import static org.example.Main.getLogin;
import static org.example.Main.getRegisFrame;
import static org.example.Regis.getAdminFrame;

public class Admin {
    private JPanel Admin;
    private JTextField fldLogin;
    private JTextField fldSenha;
    private JButton btnVoltar;
    private JButton btnEntrar;
    private static JFrame tableFrame;

    public Admin() {
        btnVoltar.addActionListener(e -> {
            getRegisFrame().setVisible(true);

            getAdminFrame().setVisible(false);
            fldLogin.setText("");
            fldSenha.setText("");
        });
        btnEntrar.addActionListener(e -> {
            if(fldSenha.getText().isEmpty() || fldLogin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os dois campos.");
            }
            else {
                boolean result = getLogin(fldSenha.getText(), fldLogin.getText());
                fldLogin.setText("");
                fldSenha.setText("");

                if (result) {

                    if(tableFrame == null) {
                        tableFrame = new JFrame("Tabela de Registros");
                        Table tabela = new Table();
                        tableFrame.setContentPane(tabela.getTablePanel());
                        tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        tableFrame.pack();
                    }
                    tableFrame.setVisible(true);

                    getAdminFrame().setVisible(false);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Login ou senha incorretos.");
                }
            }
        });
    }

    public JPanel getAdminPanel() {
        return Admin;
    }

    public static JFrame getTableFrame() {
        return tableFrame;
    }

    public static void setTableFrameNull() {
        tableFrame = null;
    }
}
