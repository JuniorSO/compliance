package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.example.Admin.getTableFrame;
import static org.example.Main.*;

public class Table {
    private JPanel Table;
    private JButton btnVoltar;
    private JTable tblRegis;
    private JButton btnVisualizarDetalhes;
    private JButton btnDeletarRegistro;
    private JButton btnRecarregar;
    private DefaultTableModel model;
    private static JFrame specificRegisFrame;

    public Table() {
        populateTable();

        btnVoltar.addActionListener(e -> {
            getRegisFrame().setVisible(true);

            getTableFrame().setVisible(false);
        });

        btnDeletarRegistro.addActionListener(e -> {
            ObjectId id = new ObjectId(tblRegis.getValueAt(tblRegis.getSelectedRow(), 0).toString());
            String link;
            String tipo;
            Document doc;

            try {
                doc = getSpecificDoc(id);

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
                }
                catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Registro já excluído.");
                    model.removeRow(tblRegis.getSelectedRow());
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir registro.");
                }
            }
            catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Registro já excluído.");
                model.removeRow(tblRegis.getSelectedRow());
            }
        });

        btnVisualizarDetalhes.addActionListener(e -> {
            ObjectId id = new ObjectId(tblRegis.getValueAt(tblRegis.getSelectedRow(), 0).toString());

            try {
                Document doc = getSpecificDoc(id);
                SpecificRegis specificRegis;

                specificRegisFrame = new JFrame("Registro " + id);
                specificRegis = new SpecificRegis(doc, model, tblRegis);
                specificRegisFrame.setContentPane(specificRegis.getSpecificRegisPanel());
                specificRegisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                specificRegisFrame.pack();

                specificRegisFrame.setVisible(true);

                getTableFrame().setVisible(false);
            }
            catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Registro não existe.");
                model.removeRow(tblRegis.getSelectedRow());
            }
        });

        btnRecarregar.addActionListener(e -> populateTable());
    }

    public void populateTable() {
        String[] columns = new String[] {"ID", "Autor", "Assunto", "Data", "Resumo"};
        String[][] data = new String[(int) countDocs()][5];
        int i = 0;

        for (Document doc : getDocs()) {
            data[i][0] = doc.get("_id").toString();
            data[i][1] = doc.get("autor").toString();
            data[i][2] = doc.get("assunto").toString();
            data[i][3] = doc.get("data").toString();
            data[i][4] = doc.get("descricao").toString();
            i++;
        }

        model = new DefaultTableModel(data, columns);
        tblRegis.setModel(model);

        tblRegis.getColumnModel().getColumn(0).setMaxWidth(-1);
        tblRegis.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblRegis.getColumnModel().getColumn(1).setMaxWidth(100);
        tblRegis.getColumnModel().getColumn(2).setPreferredWidth(250);
        tblRegis.getColumnModel().getColumn(2).setMaxWidth(250);
        tblRegis.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblRegis.getColumnModel().getColumn(3).setMaxWidth(100);
        tblRegis.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    public JPanel getTablePanel() {
        return Table;
    }

    public static JFrame getSpecificRegisFrame() {
        return specificRegisFrame;
    }
}
