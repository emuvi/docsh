package br.com.pointel.docsh.gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.FlatDarculaLaf;

public class Gui extends JFrame {

    private final JPanel panelRoot = new JPanel(new GridLayout(3, 2));
    private final JLabel labelPath = new JLabel("Path:", JLabel.RIGHT);
    private final JTextField fieldPath = new JTextField(18);
    private final JLabel labelSearch = new JLabel("Search:", JLabel.RIGHT);
    private final JTextField fieldSearch = new JTextField(18);
    private final JLabel labelStatus = new JLabel("", JLabel.RIGHT);
    private final JButton buttonStart = new JButton("Start");
    

    public Gui() {
        initComponents();
    }

    private void initComponents() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Docsh");
        setContentPane(panelRoot);
        panelRoot.add(labelPath);        
        panelRoot.add(fieldPath);
        panelRoot.add(labelSearch);
        panelRoot.add(fieldSearch);
        panelRoot.add(labelStatus);
        panelRoot.add(buttonStart);
        var border = BorderFactory.createEmptyBorder(9, 9, 9, 9);
        panelRoot.setBorder(border);
        labelPath.setBorder(border);
        labelSearch.setBorder(border);
        labelStatus.setBorder(border);
        pack();
    }

    public static void start(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(() -> {
            new Gui().setVisible(true);
        });
    }
    
}
