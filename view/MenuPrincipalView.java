package view;

import java.awt.CardLayout;
import javax.swing.*;

public class MenuPrincipalView extends JFrame {

    private final EMAcordeView cardAcordePanel = new EMAcordeView();
    private final ProgressaoView progressaoPanel = new ProgressaoView();
    private final MusicaView musicaPanel = new MusicaView();
    private final TecladoView tecladoPanel = new TecladoView();
    private final EMCampohView cardCampoHPanel = new EMCampohView();
    private final EMNotaView cardNotaPanel = new EMNotaView();
    private final EMEscalaView cardEscalaPanel = new EMEscalaView();


    public MenuPrincipalView() {
    initComponents();
    
    this.getContentPane().add(painelCentro, java.awt.BorderLayout.CENTER);

    painelCentro.add(cardAcordePanel, "cardAcorde");
    painelCentro.add(progressaoPanel, "cardProgressao");
    painelCentro.add(musicaPanel, "cardMusica");
    painelCentro.add(tecladoPanel, "cardTeclado");
    painelCentro.add(cardCampoHPanel, "cardCampoH");
    painelCentro.add(cardNotaPanel, "cardNota");
    painelCentro.add(cardEscalaPanel, "cardEscala");

    CardLayout cl = (CardLayout) painelCentro.getLayout();
    cl.show(painelCentro, "cardProgressao"); // Ajuste se quiser que outro painel apareça primeiro

    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    pack();
}

 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        painelCentro = new javax.swing.JPanel();
        cardMenuPrincipal = new javax.swing.JPanel();
        cardProgressao = new view.ProgressaoView();
        jMenuBar1 = new javax.swing.JMenuBar();
        Elemento = new javax.swing.JMenu();
        Acorde = new javax.swing.JMenuItem();
        Escala = new javax.swing.JMenuItem();
        Nota = new javax.swing.JMenuItem();
        CampoH = new javax.swing.JMenuItem();
        Musica = new javax.swing.JMenu();
        menuProgressao = new javax.swing.JMenu();
        Teclado = new javax.swing.JMenu();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        painelCentro.setLayout(new java.awt.CardLayout());

        cardMenuPrincipal.setMaximumSize(new java.awt.Dimension(10000, 10000));

        javax.swing.GroupLayout cardMenuPrincipalLayout = new javax.swing.GroupLayout(cardMenuPrincipal);
        cardMenuPrincipal.setLayout(cardMenuPrincipalLayout);
        cardMenuPrincipalLayout.setHorizontalGroup(
            cardMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        cardMenuPrincipalLayout.setVerticalGroup(
            cardMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 478, Short.MAX_VALUE)
        );

        painelCentro.add(cardMenuPrincipal, "cardMenuPrincipal");

        javax.swing.GroupLayout cardProgressaoLayout = new javax.swing.GroupLayout(cardProgressao);
        cardProgressao.setLayout(cardProgressaoLayout);
        cardProgressaoLayout.setHorizontalGroup(
            cardProgressaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        cardProgressaoLayout.setVerticalGroup(
            cardProgressaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        painelCentro.add(cardProgressao, "card3");

        Elemento.setText("Elemento Musical");

        Acorde.setText("Acorde");
        Acorde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcordeActionPerformed(evt);
            }
        });
        Elemento.add(Acorde);

        Escala.setText("Escala");
        Escala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EscalaActionPerformed(evt);
            }
        });
        Elemento.add(Escala);

        Nota.setText("Nota");
        Nota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NotaActionPerformed(evt);
            }
        });
        Elemento.add(Nota);

        CampoH.setText("Campo Harmonico");
        CampoH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoHActionPerformed(evt);
            }
        });
        Elemento.add(CampoH);

        jMenuBar1.add(Elemento);

        Musica.setText("Musica");
        Musica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MusicaActionPerformed(evt);
            }
        });
        jMenuBar1.add(Musica);

        menuProgressao.setText("Progressao");
        menuProgressao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProgressaoActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuProgressao);

        Teclado.setText("Teclado");
        Teclado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TecladoActionPerformed(evt);
            }
        });
        jMenuBar1.add(Teclado);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCentro, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelCentro, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AcordeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcordeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AcordeActionPerformed

    private void menuProgressaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProgressaoActionPerformed
        CardLayout cl = (CardLayout) painelCentro.getLayout();
        cl.show(painelCentro, "cardProgressao");
    }//GEN-LAST:event_menuProgressaoActionPerformed

    private void MusicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MusicaActionPerformed
        CardLayout cl = (CardLayout) painelCentro.getLayout();
        cl.show(painelCentro, "cardMusica");
    }//GEN-LAST:event_MusicaActionPerformed

    private void TecladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TecladoActionPerformed
        CardLayout cl = (CardLayout) painelCentro.getLayout();
        cl.show(painelCentro, "cardTeclado");
    }//GEN-LAST:event_TecladoActionPerformed

    private void EscalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EscalaActionPerformed
        CardLayout cl = (CardLayout) painelCentro.getLayout();
        cl.show(painelCentro, "cardEscala");
    }//GEN-LAST:event_EscalaActionPerformed

    private void NotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NotaActionPerformed
        CardLayout cl = (CardLayout) painelCentro.getLayout();
        cl.show(painelCentro, "cardNota");
    }//GEN-LAST:event_NotaActionPerformed

    private void CampoHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoHActionPerformed
        CardLayout cl = (CardLayout) painelCentro.getLayout();
        cl.show(painelCentro, "cardCampoH");
    }//GEN-LAST:event_CampoHActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Acorde;
    private javax.swing.JMenuItem CampoH;
    private javax.swing.JMenu Elemento;
    private javax.swing.JMenuItem Escala;
    private javax.swing.JMenu Musica;
    private javax.swing.JMenuItem Nota;
    private javax.swing.JMenu Teclado;
    private javax.swing.JPanel cardMenuPrincipal;
    private javax.swing.JPanel cardProgressao;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenu menuProgressao;
    private javax.swing.JPanel painelCentro;
    // End of variables declaration//GEN-END:variables
}
