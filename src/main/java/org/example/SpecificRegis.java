package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

import static org.example.Admin.getTableFrame;
import static org.example.Main.deleteDoc;
import static org.example.Main.getSpecificDoc;
import static org.example.Table.getSpecificRegisFrame;

public class SpecificRegis {
    private JPanel SpecificRegis;
    private JTextArea areaDesc;
    private JButton btnVoltar;
    private JButton btnDeletarRegistro;
    private JButton btnAbrirMidia;
    private JLabel lblAutor;
    private JLabel lblAssunto;
    private JLabel lblData;

    public SpecificRegis(Document doc, DefaultTableModel model, JTable tblRegis) {
        lblAutor.setText(doc.get("autor").toString());
        lblAssunto.setText(doc.get("assunto").toString());
        areaDesc.setText(doc.get("descricao").toString());
        lblData.setText(doc.get("data").toString());
        if(doc.get("midia") != null) {
            btnAbrirMidia.setVisible(true);
        }

        btnVoltar.addActionListener(e -> retornarTabela());

        btnAbrirMidia.addActionListener(e -> {
            try {
                //noinspection deprecation
                Desktop.getDesktop().browse(new URL(doc.get("midia").toString()).toURI());
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível exibir a mídia.");
            }
        });

        btnDeletarRegistro.addActionListener(e -> {
            String link;
            String tipo;
            ObjectId id = (ObjectId) doc.get("_id");

            try {
                getSpecificDoc(id);

                if(doc.get("midia") != null) {
                    link = doc.get("midia").toString();
                    tipo = doc.get("tipo_midia").toString();
                }
                else {
                    link = null;
                    tipo = null;
                }

                try {
                    deleteDoc(id, link, tipo);
                    model.removeRow(tblRegis.getSelectedRow());
                    JOptionPane.showMessageDialog(null, "Registro excluído.");
                    retornarTabela();
                }
                catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Registro já excluído.");
                    model.removeRow(tblRegis.getSelectedRow());
                    retornarTabela();
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir registro.");
                }
            }
            catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Registro já excluído.");
                model.removeRow(tblRegis.getSelectedRow());
                retornarTabela();
            }
        });
    }

    public void retornarTabela() {
        getTableFrame().setVisible(true);

        getSpecificRegisFrame().dispose();
    }

    public JPanel getSpecificRegisPanel() {
        return SpecificRegis;
    }
}
