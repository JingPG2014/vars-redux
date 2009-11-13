/*
 * VCRSelectionDialog.java
 *
 * Created on May 11, 2007, 1:19 PM
 */

package org.mbari.vars.annotation.ui.dialogs;

import org.mbari.util.Dispatcher;
import org.mbari.vars.annotation.ui.dispatchers.PredefinedDispatcher;
import org.mbari.vcr.IVCR;
import org.mbari.vcr.VCRAdapter;
import org.mbari.vcr.ui.VCRSelectionPanel;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author  brian
 */
public class VCRSelectionDialog extends javax.swing.JDialog {
    
    
    private static final Logger log = LoggerFactory.getLogger(VCRSelectionDialog.class);
    
    /** Creates new form VCRSelectionDialog */
    public VCRSelectionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("VARS - Connect to VCR");
        initComponents();
        setLocationRelativeTo(parent);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        vcrSelectionPanel = new VCRSelectionPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        org.jdesktop.layout.GroupLayout vcrSelectionPanelLayout = new org.jdesktop.layout.GroupLayout(vcrSelectionPanel);
        vcrSelectionPanel.setLayout(vcrSelectionPanelLayout);
        vcrSelectionPanelLayout.setHorizontalGroup(
            vcrSelectionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 387, Short.MAX_VALUE)
        );
        vcrSelectionPanelLayout.setVerticalGroup(
            vcrSelectionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 208, Short.MAX_VALUE)
        );

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(okButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancelButton))
                    .add(vcrSelectionPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(vcrSelectionPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton)
                    .add(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
    setVisible(false);
}//GEN-LAST:event_cancelButtonActionPerformed

private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
    log.debug("Opening a dialog to select the VCR");
    setVisible(false);
    VCRSelectionPanel panel = (VCRSelectionPanel) vcrSelectionPanel;
    Dispatcher.getDispatcher(IVCR.class).setValueObject(panel.getVcr());
    log.debug("Connection to VCR is now established");
}//GEN-LAST:event_okButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VCRSelectionDialog dialog = new VCRSelectionDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    @Override
    public void setVisible(boolean b) {
        if (b) {
            IVCR vcr = (IVCR) PredefinedDispatcher.VCR.getDispatcher().getValueObject();
            if (vcr == null) {
                vcr = new VCRAdapter();
            }
            
            ((VCRSelectionPanel) vcrSelectionPanel).setVcr(vcr);
        }

        super.setVisible(b);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel vcrSelectionPanel;
    // End of variables declaration//GEN-END:variables
    
}
