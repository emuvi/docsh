package br.com.pointel.docsh.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.Border;


import br.com.pointel.docsh.lib.Lib;
import br.com.pointel.docsh.lib.WizSwing;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import javax.swing.JSlider;

public class Gui extends JFrame implements ActionListener {

    public static final Font FONT = new Font(Font.MONOSPACED, 0, 15);

    private final JPanel panelRoot = new JPanel(new BorderLayout(3, 3));
    private final JPanel panelUpper = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3));
    private final JPanel panelLower = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 3));
    private final JTextField fieldPath = new JTextField(27);
    private final JButton buttonSelect = new JButton("&");
    private final JTextField fieldWords = new JTextField(18);
    private final JSlider fieldLimit = new JSlider(JSlider.HORIZONTAL, 0, 100, 72);
    private final JButton buttonSearch = new JButton("Search");

    private final Border border = BorderFactory.createEmptyBorder(4, 4, 4, 4);

    public Gui() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Docsh");
        setContentPane(panelRoot);
        setResizable(false);

        panelRoot.add(panelUpper, BorderLayout.NORTH);
        panelRoot.add(panelLower, BorderLayout.SOUTH);
        panelUpper.add(fieldPath);
        panelUpper.add(buttonSelect);
        panelUpper.add(fieldWords);
        panelLower.add(fieldLimit);
        panelLower.add(buttonSearch);

        panelRoot.setBorder(border);
        fieldPath.setFont(FONT);
        buttonSelect.setFont(FONT);
        fieldWords.setFont(FONT);
        buttonSearch.setFont(FONT);

        pack();

        getRootPane().setDefaultButton(buttonSearch);
        buttonSearch.addActionListener(this);
        buttonSelect.addActionListener((e) -> selectPath());
        
        WizSwing.initPositioner(this);
    }

    public static void start(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(() -> {
            new Gui().setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonSearch.setEnabled(false);
        final var origin = this;
        final var path = new File(fieldPath.getText());
        final var words = fieldWords.getText();
        final var tolerance = fieldLimit.getValue() / 100.00;
        new Thread() {
            @Override
            public void run() {
                try {
                    final var founds = Lib.search(path, words, tolerance);
                    if (founds.isEmpty()) {
                        SwingUtilities.invokeLater(
                                () -> JOptionPane.showMessageDialog(origin, "None found."));
                    } else {
                        SwingUtilities.invokeLater(
                                () -> new GuiFounds(origin, words, founds).setVisible(true));
                    }
                } catch (Exception e) {
                    SwingUtilities.invokeLater(
                            () -> JOptionPane.showMessageDialog(origin, e.getMessage()));
                } finally {
                    SwingUtilities.invokeLater(
                            () -> buttonSearch.setEnabled(true));
                }
            };
        }.start();
    }

    private void selectPath() {
        try {
            var chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                fieldPath.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

}
