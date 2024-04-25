package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.example.Admin.tableFrame;
import static org.example.Main.*;

public class Table {
    public JPanel Table;
    private JButton btnVoltar;
    private JTable tblRegis;
    private JButton btnVisualizarDetalhes;
    private JButton btnDeletarRegistro;

    public Table() {
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

        DefaultTableModel model = new DefaultTableModel(data, columns);
        tblRegis.setModel(model);

        tblRegis.getColumnModel().getColumn(0).setMaxWidth(-1);
        tblRegis.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblRegis.getColumnModel().getColumn(1).setMaxWidth(100);
        tblRegis.getColumnModel().getColumn(2).setPreferredWidth(250);
        tblRegis.getColumnModel().getColumn(2).setMaxWidth(250);
        tblRegis.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblRegis.getColumnModel().getColumn(3).setMaxWidth(100);
        tblRegis.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        btnVoltar.addActionListener(e -> {
            if(regisFrame == null) {
                regisFrame = new JFrame("Admin");
                regisFrame.setContentPane(new Admin().Admin);
                regisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                regisFrame.pack();
            }
            regisFrame.setVisible(true);

            tableFrame.setVisible(false);
        });

        btnDeletarRegistro.addActionListener(e -> {
            ObjectId id = new ObjectId(tblRegis.getValueAt(tblRegis.getSelectedRow(), 0).toString());
            model.removeRow(tblRegis.getSelectedRow());
            deleteDoc(id);
        });

        btnVisualizarDetalhes.addActionListener(e -> {

        });
    }
}
