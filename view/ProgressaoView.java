package view;

import controller.ControladorProgressao;
import model.Progressao;

public class ProgressaoView extends javax.swing.JPanel {
    
    private final ControladorProgressao controladorProgressao = new ControladorProgressao();


    public ProgressaoView() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExcluir = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtProgressao = new javax.swing.JTextArea();
        btnImportar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        btnReproduzirSom = new javax.swing.JButton();
        btnCriar = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));

        btnExcluir.setText("Excluir");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");

        txtProgressao.setColumns(20);
        txtProgressao.setRows(5);
        jScrollPane1.setViewportView(txtProgressao);

        btnImportar.setText("Importar");

        btnExportar.setText("Exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        btnReproduzirSom.setText("Reproduzir som");

        btnCriar.setText("Criar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(129, 129, 129))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnImportar)
                        .addGap(50, 50, 50)
                        .addComponent(btnExportar)
                        .addGap(50, 50, 50)
                        .addComponent(btnReproduzirSom))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSalvar, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnExcluir)
                                    .addComponent(btnCriar))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImportar)
                    .addComponent(btnExportar)
                    .addComponent(btnReproduzirSom))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCriar)
                        .addGap(30, 30, 30)
                        .addComponent(btnExcluir)
                        .addGap(30, 30, 30)
                        .addComponent(btnBuscar)
                        .addGap(30, 30, 30)
                        .addComponent(btnSalvar)
                        .addGap(0, 277, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String nomeBusca = txtProgressao.getText();
        Progressao encontrada = controladorProgressao.txtProgressao(nomeBusca);

        if (encontrada != null) {
            txtProgressao.setText("Encontrada: " + encontrada.getNome());
        } else {
            txtProgressao.setText("Nenhuma progressão encontrada.");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCriar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnImportar;
    private javax.swing.JButton btnReproduzirSom;
    private javax.swing.JButton btnSalvar;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtProgressao;
    // End of variables declaration//GEN-END:variables
}
