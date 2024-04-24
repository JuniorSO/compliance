package org.example;

import javax.swing.*;
import java.util.Objects;

public class Regis {
    public JPanel Regis;
    private JButton btnLimparForm;
    private JButton btnEnviarForm;
    private JButton btnEntrarAdmin;
    private JComboBox<String> cmbAssunto;
    private JTextField fldAutor;
    private JTextArea areaDesc;
    private JButton btnSelecionarArquivo;
    private JLabel arqSelecLabel;
    public static JFrame adminFrame;

    public Regis() {
        String[] assuntos = {
                "Benefícios e Remuneração",
                "Cultura Organizacional",
                "Desenvolvimento Profissional",
                "Equilíbrio Entre Vida Pessoal e Profissional",
                "Questões de Gestão",
                "Recursos e Equipamentos",
                "Saúde e Segurança",
                "Outro"
        };
        populateAssuntoCombo(assuntos);

        btnLimparForm.addActionListener(e -> {
            fldAutor.setText("");
            cmbAssunto.setSelectedItem("Benefícios e Remuneração");
            areaDesc.setText("");
        });

        btnEntrarAdmin.addActionListener(e -> {
            if (adminFrame == null) {
                adminFrame = new JFrame("Admin");
                adminFrame.setContentPane(new Admin().Admin);
                adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                adminFrame.pack();
            }
            adminFrame.setVisible(true);

            Main.regisFrame.setVisible(false);
        });

        btnEnviarForm.addActionListener(e -> {
            if(areaDesc.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha a descrição.");
            }
            else {
                if(fldAutor.getText().isEmpty()) {
                    Main.insertDoc("Anônimo(a)", Objects.requireNonNull(cmbAssunto.getSelectedItem()).toString(), areaDesc.getText());
                } else {
                    Main.insertDoc(fldAutor.getText(), Objects.requireNonNull(cmbAssunto.getSelectedItem()).toString(), areaDesc.getText());
                }
            }
        });
    }

    public void populateAssuntoCombo(String[] arr) {
        for (String s : arr) {
            cmbAssunto.addItem(s);
        }
    }
}
