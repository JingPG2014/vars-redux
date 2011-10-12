/*
 * Copyright 2005 MBARI
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 2.1
 * (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.gnu.org/copyleft/lesser.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/*
Generated by Together
 */
package vars.annotation.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * <p>The about dialog</p>
 *
 * @author <a href="http://www.mbari.org">MBARI</a>
 * @version $Id: AboutDialog.java 440 2006-11-22 19:34:10Z hohonuuli $
 */
public class AboutDialog extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 5863660176510794934L;


    private final FlowLayout btnPaneLayout = new FlowLayout();


    private final JPanel btnPanel = new JPanel();


    private final JTextArea commentField = new JTextArea();


    private final String comments =
        " Authors:\n\tBrian Schlining\n\tAndrew Chase\n\tKevin Gomes\n\tRich Schramm\n\tMike McCann";


    private final JPanel contentPane = new JPanel();


    private final GridBagLayout contentPaneLayout = new GridBagLayout();


    private final JLabel copLabel = new JLabel();

    private final String copyright = "Copyright (c) 2006";

    private final BorderLayout formLayout = new BorderLayout();

    private final JLabel image = new JLabel();

    private final JButton okButton = new JButton();

    private final JLabel prodLabel = new JLabel();

    private final String product = "Video Annotation and Reference System";

    private final String title = "VARS - About";

    private final JLabel verLabel = new JLabel();

    private String version;

    /**
     * Constructor for the AboutDialog object
     */
    public AboutDialog() {
        super();
        setModal(true);
        initGUI();
        pack();
    }

    /**
     * Creates new About Dialog
     *
     * @param  parent The parent frame
     * @param  modal ture = modal, false = non-modal
     */
    public AboutDialog(final Frame parent, final boolean modal) {
        super(parent, modal);
        initGUI();
        pack();
    }

    /**
     * Closes the dialog
     *
     * @param  evt The WindowEvent
     */
    private void closeDialog(final WindowEvent evt) {
        setVisible(false);
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the dialog.
     */
    private void initGUI() {
        ResourceBundle bundle = ResourceBundle.getBundle("varssettings");
        version = bundle.getString("version");

        addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(final WindowEvent evt) {
                closeDialog(evt);
            }
        });
        getContentPane().setLayout(formLayout);
        contentPane.setLayout(contentPaneLayout);
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        prodLabel.setText(product);
        contentPane.add(prodLabel,
                        new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,
                            GridBagConstraints.REMAINDER, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
                            GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
        verLabel.setText(version);
        contentPane.add(verLabel,
                        new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,
                            GridBagConstraints.REMAINDER, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
                            GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
        copLabel.setText(copyright);
        contentPane.add(copLabel,
                        new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,
                            GridBagConstraints.REMAINDER, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
                            GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
        commentField.setBackground(getBackground());
        commentField.setForeground(copLabel.getForeground());
        commentField.setFont(copLabel.getFont());
        commentField.setText(comments);
        commentField.setEditable(false);
        contentPane.add(commentField,
                        new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,
                            GridBagConstraints.REMAINDER, 3, 0.0, 1.0, GridBagConstraints.NORTHWEST,
                            GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
        image.setIcon(new ImageIcon(getClass().getResource("/images/vars/annotation/mbarilogo-web-400.jpg")));
        getContentPane().add(image, BorderLayout.WEST);
        getContentPane().add(contentPane, BorderLayout.CENTER);
        btnPanel.setLayout(btnPaneLayout);
        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        btnPanel.add(okButton);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        setTitle(title);
        setResizable(false);
    }
}
