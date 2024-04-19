package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Regis {
    public JPanel Regis;
    private JButton limparFormulárioButton;
    private JButton enviarFormulárioButton;
    private JButton entrarComoAdministradorButton;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextArea textArea1;
    private JButton selecionarArquivosButton;

    public Regis() {
        limparFormulárioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
            }
        });
        entrarComoAdministradorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
