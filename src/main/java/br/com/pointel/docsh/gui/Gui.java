package br.com.pointel.docsh.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import com.formdev.flatlaf.FlatDarculaLaf;

import br.com.pointel.docsh.lib.Lib;

public class Gui extends JFrame implements ActionListener {

    private final JPanel panelRoot = new JPanel(new GridLayout(3, 2));
    private final JLabel labelPath = new JLabel("Path:", JLabel.RIGHT);
    private final JTextField fieldPath = new JTextField(18);
    private final JLabel labelSearch = new JLabel("Search:", JLabel.RIGHT);
    private final JTextField fieldSearch = new JTextField(18);
    private final JLabel labelStatus = new JLabel("", JLabel.RIGHT);
    private final JButton buttonStart = new JButton("Start");

    private final Border border = BorderFactory.createEmptyBorder(9, 9, 9, 9);

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

        panelRoot.setBorder(border);
        labelPath.setBorder(border);
        labelSearch.setBorder(border);
        labelStatus.setBorder(border);

        pack();

        buttonStart.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonStart.setEnabled(false);
        labelStatus.setText("Searching...");
        final var path = new File(fieldPath.getText());
        final var search = fieldSearch.getText();
        new Thread() {
            @Override
            public void run() {
                try {
                    final var founds = Lib.search(path, search);
                    if (founds.isEmpty()) {
                        SwingUtilities.invokeLater(() -> labelStatus.setText("None found."));
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            labelStatus.setText("Found " + founds.size());
                            new GuiFounds(search, founds).setVisible(true);
                        });
                    }
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> labelStatus.setText(e.getMessage()));
                } finally {
                    SwingUtilities.invokeLater(() -> buttonStart.setEnabled(true));
                }
            };
        }.start();
    }

}
