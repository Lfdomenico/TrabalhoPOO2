package view;

import javax.swing.ImageIcon;
import model.Acorde;
import repository.AcordeRepositorio;
import repository.AcordeRepositorioMySQL;

public class EMNotaView extends javax.swing.JPanel {


    public EMNotaView() {
        initComponents();
    }
    private final AcordeRepositorio acordeRepositorio = new AcordeRepositorioMySQL();

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardAcorde = new javax.swing.JPanel();
        btnBuscarAcorde = new javax.swing.JButton();
        lblImagemAcorde = new javax.swing.JLabel();
        chkFavorito = new javax.swing.JCheckBox();
        txtAcorde = new javax.swing.JLabel();
        btnInverter = new javax.swing.JButton();
        btnAnotacoes = new javax.swing.JButton();
        btnReproduzir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        btnBuscarAcorde.setText("Buscar");
        btnBuscarAcorde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAcordeActionPerformed(evt);
            }
        });

        lblImagemAcorde.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/Acorde.png"))); // NOI18N

        chkFavorito.setText("★");
        chkFavorito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFavoritoActionPerformed(evt);
            }
        });

        txtAcorde.setText("Acorde");

        btnInverter.setText("Inverter");

        btnAnotacoes.setText("Anotações");
        btnAnotacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnotacoesActionPerformed(evt);
            }
        });

        btnReproduzir.setText("Reproduzir");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout cardAcordeLayout = new javax.swing.GroupLayout(cardAcorde);
        cardAcorde.setLayout(cardAcordeLayout);
        cardAcordeLayout.setHorizontalGroup(
            cardAcordeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardAcordeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardAcordeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardAcordeLayout.createSequentialGroup()
                        .addComponent(lblImagemAcorde)
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cardAcordeLayout.createSequentialGroup()
                        .addComponent(txtAcorde)
                        .addGap(59, 59, 59)
                        .addGroup(cardAcordeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cardAcordeLayout.createSequentialGroup()
                                .addComponent(btnInverter)
                                .addGap(50, 50, 50)
                                .addComponent(btnAnotacoes)
                                .addGap(50, 50, 50)
                                .addComponent(btnReproduzir))
                            .addGroup(cardAcordeLayout.createSequentialGroup()
                                .addComponent(btnBuscarAcorde, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkFavorito)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        cardAcordeLayout.setVerticalGroup(
            cardAcordeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardAcordeLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(cardAcordeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarAcorde)
                    .addComponent(txtAcorde)
                    .addComponent(chkFavorito))
                .addGap(38, 38, 38)
                .addGroup(cardAcordeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(lblImagemAcorde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(cardAcordeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnotacoes)
                    .addComponent(btnReproduzir)
                    .addComponent(btnInverter))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cardAcorde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(cardAcorde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarAcordeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAcordeActionPerformed
    String nomeAcorde = txtAcorde.getText();

    Acorde acorde = acordeRepositorio.buscarPorNome(nomeAcorde);

    if (acorde != null) {
        lblImagemAcorde.setIcon(new ImageIcon(acorde.getCaminhoImagem())); 
        txtAcorde.setText(acorde.getNome() + " - " + acorde.getNotas()); 
    } else {
        lblImagemAcorde.setIcon(null);
        txtAcorde.setText("Acorde não encontrado!");
    }
    }//GEN-LAST:event_btnBuscarAcordeActionPerformed

    private void chkFavoritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFavoritoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkFavoritoActionPerformed

    private void btnAnotacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnotacoesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnotacoesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnotacoes;
    private javax.swing.JButton btnBuscarAcorde;
    private javax.swing.JButton btnInverter;
    private javax.swing.JButton btnReproduzir;
    private javax.swing.JPanel cardAcorde;
    private javax.swing.JCheckBox chkFavorito;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImagemAcorde;
    private javax.swing.JLabel txtAcorde;
    // End of variables declaration//GEN-END:variables
}
