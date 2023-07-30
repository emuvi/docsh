package br.com.pointel.docsh.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import br.com.pointel.docsh.lib.Found;

public class Founds extends JFrame {

    private final JPanel panelRoot = new JPanel(new BorderLayout(9, 9));
    private final DefaultListModel<Found> modelFounds = new DefaultListModel<>();
    private final JList<Found> fieldFounds = new JList<>(modelFounds);

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

        pack();
    }
    
}
