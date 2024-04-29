package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Objects;

import static org.example.Main.getRegisFrame;
import static org.example.Main.insertDoc;

public class Regis {
    private JPanel Regis;
    private JButton btnLimparForm;
    private JButton btnEnviarForm;
    private JButton btnEntrarAdmin;
    private JComboBox<String> cmbAssunto;
    private JTextField fldAutor;
    private JTextArea areaDesc;
    private JButton btnSelecionarArquivo;
    private JButton btnRemoverArquivo;
    private JLabel lblArqSelec;
    private static JFrame adminFrame;
    private final JFileChooser chooser = new JFileChooser();

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

        btnLimparForm.addActionListener(e -> limparForm());

        btnEntrarAdmin.addActionListener(e -> {
            if (adminFrame == null) {
                adminFrame = new JFrame("Entrar como Administrador");
                adminFrame.setContentPane(new Admin().getAdminPanel());
                adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                adminFrame.pack();
            }
            adminFrame.setVisible(true);

            getRegisFrame().setVisible(false);
        });

        btnEnviarForm.addActionListener(e -> {
            if(areaDesc.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha a descrição.");
            }
            else {
                if(fldAutor.getText().isEmpty()) {
                    if(chooser.getSelectedFile() != null) {
                        insertDoc("Anônimo(a)", Objects.requireNonNull(cmbAssunto.getSelectedItem()).toString(), areaDesc.getText(), chooser.getSelectedFile());
                    }
                    else {
                        insertDoc("Anônimo(a)", Objects.requireNonNull(cmbAssunto.getSelectedItem()).toString(), areaDesc.getText(), null);
                    }

                }
                else {
                    if(chooser.getSelectedFile() != null) {
                        insertDoc(fldAutor.getText(), Objects.requireNonNull(cmbAssunto.getSelectedItem()).toString(), areaDesc.getText(), chooser.getSelectedFile());
                    }
                    else {
                        insertDoc(fldAutor.getText(), Objects.requireNonNull(cmbAssunto.getSelectedItem()).toString(), areaDesc.getText(), null);
                    }
                }

                limparForm();
            }
        });

        btnSelecionarArquivo.addActionListener(e ->  {
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens PNG, JPG ou Vídeos MP4", "png", "jpg", "mp4");
            chooser.addChoosableFileFilter(filter);
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                lblArqSelec.setText("Arquivo Selecionado: " + chooser.getSelectedFile().getName());
            }
        });

        btnRemoverArquivo.addActionListener(e -> {
            chooser.setSelectedFile(null);
            lblArqSelec.setText("Arquivo Selecionado:");
        });

    }

    public void populateAssuntoCombo(String[] arr) {
        for (String s : arr) {
            cmbAssunto.addItem(s);
        }
    }

    public void limparForm() {
        fldAutor.setText("");
        cmbAssunto.setSelectedItem("Benefícios e Remuneração");
        areaDesc.setText("");
        chooser.setSelectedFile(null);
        lblArqSelec.setText("Arquivo Selecionado:");
    }

    public JPanel getRegisPanel() {
        return Regis;
    }

    public static JFrame getAdminFrame() {
        return adminFrame;
    }
}
