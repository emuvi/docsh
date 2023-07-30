package br.com.pointel.docsh.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import br.com.pointel.docsh.lib.Found;
import br.com.pointel.docsh.lib.Scored;

public class Founds extends JFrame {

    private final JPanel panelRoot = new JPanel(new GridBagLayout());
    private final DefaultListModel<Found> modelFounds = new DefaultListModel<>();
    private final JList<Found> fieldFounds = new JList<>(modelFounds);
    private final DefaultListModel<Scored> modelScored = new DefaultListModel<>();
    private final JList<Scored> fieldScored = new JList<>(modelScored);
    private final JTextArea fieldText = new JTextArea(18, 27);

    private final Border border = BorderFactory.createEmptyBorder(9, 9, 9, 9);

    public Founds(List<Found> founds) {
        this.modelFounds.addAll(founds);
        initComponents();
    }

    private void initComponents() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Docsh - Founds");
        setContentPane(panelRoot);

        panelRoot.setBorder(border);
        var layout = new GridBagConstraints();
        layout.fill = GridBagConstraints.BOTH;
        layout.weightx = 1;
        layout.weighty = 1;
        layout.gridx = 0;
        layout.gridy = 0;
        panelRoot.add(fieldFounds, layout);
        layout.gridy++;
        panelRoot.add(fieldScored, layout);
        layout.gridy++;
        layout.weighty = 3;
        panelRoot.add(fieldText, layout);

        pack();
    }
    
}
