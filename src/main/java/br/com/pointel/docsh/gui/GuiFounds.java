package br.com.pointel.docsh.gui;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;

import br.com.pointel.docsh.lib.Found;
import br.com.pointel.docsh.lib.Score;
import br.com.pointel.docsh.lib.Scored;
import br.com.pointel.docsh.lib.WizSwing;
import br.com.pointel.docsh.lib.WordMap;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextField;

public class GuiFounds extends JFrame {

    private final JPanel panelRoot = new JPanel(new BorderLayout(3, 3));
    private final DefaultListModel<Found> modelFounds = new DefaultListModel<>();
    private final JList<Found> fieldFounds = new JList<>(modelFounds);
    private final JScrollPane scrollFounds = new JScrollPane(fieldFounds);

    private final DefaultListModel<Scored> modelScored = new DefaultListModel<>();
    private final JList<Scored> fieldScored = new JList<>(modelScored);
    private final JScrollPane scrollScored = new JScrollPane(fieldScored);
    private final DefaultListModel<Score> modelMapped = new DefaultListModel<>();
    private final JList<Score> fieldMapped = new JList<>(modelMapped);
    private final JScrollPane scrollMapped = new JScrollPane(fieldMapped);
    private final JSplitPane panelRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollScored, scrollMapped);
    private final JSplitPane panelTop = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollFounds, panelRight);
    private final JTextArea fieldText = new JTextArea();
    private final JScrollPane scrollText = new JScrollPane(fieldText);
    private final JSplitPane panelCenter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelTop, scrollText);
    
    private final JPanel panelLower = new JPanel(new BorderLayout(3, 3));
    private final JButton buttonFolder = new JButton("Folder");
    private final JTextField fieldPath = new JTextField();
    private final JButton buttonOpen = new JButton("Open");

    private final Border borderSpace = BorderFactory.createEmptyBorder(9, 9, 9, 9);
    private final Border borderFields = BorderFactory.createLoweredBevelBorder();

    public GuiFounds(JFrame origin, String words, List<Found> founds) {
        super("Docsh - Founds");
        this.modelFounds.addAll(founds);
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(panelRoot);

        panelRoot.setBorder(borderSpace);
        scrollFounds.setBorder(borderFields);
        scrollScored.setBorder(borderFields);
        scrollMapped.setBorder(borderFields);
        scrollText.setBorder(borderFields);
        panelRoot.add(panelCenter, BorderLayout.CENTER);
        
        buttonFolder.addActionListener((e) -> openFolder());
        fieldPath.setEditable(false);
        buttonOpen.addActionListener((e) -> openFound());
        
        panelLower.add(buttonFolder, BorderLayout.WEST);
        panelLower.add(fieldPath, BorderLayout.CENTER);
        panelLower.add(buttonOpen, BorderLayout.EAST);
        panelRoot.add(panelLower, BorderLayout.SOUTH);

        fieldFounds.setFont(Gui.FONT);
        fieldScored.setFont(Gui.FONT);
        fieldMapped.setFont(Gui.FONT);
        fieldText.setFont(Gui.FONT);
        buttonFolder.setFont(Gui.FONT);
        fieldPath.setFont(Gui.FONT);
        buttonOpen.setFont(Gui.FONT);

        fieldText.setLineWrap(true);
        fieldText.setWrapStyleWord(true);
        fieldText.setEditable(false);

        pack();

        fieldFounds.addListSelectionListener(e -> selectedFound(e));
        fieldScored.addListSelectionListener(e -> selectedScored(e));
        fieldMapped.addListSelectionListener(e -> selectedMapped(e));
        
        WizSwing.initEscaper(this);
        WizSwing.initPositioner(this);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fieldFounds.requestFocus();
                fieldFounds.setSelectedIndex(0);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var width = getWidth();
                var height = getHeight();
                panelTop.setDividerLocation(width / 2);
                panelCenter.setDividerLocation(height / 3);
                panelRight.setDividerLocation(height / 6);
            }
        });
        
        var thisFrame = this;
        
        var shortCuts = new KeyAdapter() {
            private boolean isPrior(char c) {
                return c == '<' || c == ',';
            }

            private boolean isNext(char c) {
                return c == '>' || c == '.';
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isShiftDown()) {
                    if (isPrior(e.getKeyChar())) {
                        priorFound();
                    } else if (isNext(e.getKeyChar())) {
                        nextFound();
                    }
                } else if (e.isControlDown()) {
                    if (isPrior(e.getKeyChar())) {
                        priorScore();
                    } else if (isNext(e.getKeyChar())) {
                        nextScore();
                    }
                } else if (e.isAltDown()) {
                    if (isPrior(e.getKeyChar())) {
                        priorMapped();
                    } else if (isNext(e.getKeyChar())) {
                        nextMapped();
                    }
                } else {
                    if (e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) {
                        WizSwing.close(thisFrame);
                    }
                }
            }
        };

        fieldFounds.addKeyListener(shortCuts);
        fieldScored.addKeyListener(shortCuts);
        fieldMapped.addKeyListener(shortCuts);
        fieldText.addKeyListener(shortCuts);
    }

    private void selectedFound(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        modelScored.removeAllElements();
        fieldText.setText("");
        var found = fieldFounds.getSelectedValue();
        if (found != null) {
            for (var scored : found.scores) {
                modelScored.addElement(scored);
            }
            fieldText.setText(found.source);
            fieldPath.setText(found.file.getPath());
        }
        nextScore();
    }

    private void selectedScored(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        modelMapped.removeAllElements();
        var scored = fieldScored.getSelectedValue();
        if (scored != null) {
            for (var point : scored.points) {
                modelMapped.addElement(point);
            }
        }
        nextMapped();
    }

    private void selectedMapped(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        var score = fieldMapped.getSelectedValue();
        if (score != null) {
            fieldText.setSelectionStart(score.wordMap.start);
            fieldText.setSelectionEnd(score.wordMap.end);
            fieldText.requestFocus();
        }
    }
    
    private void priorFound() {
        var index = fieldFounds.getSelectedIndex();
        if (index > 0) {
            fieldFounds.setSelectedIndex(index - 1);
            fieldFounds.ensureIndexIsVisible(fieldFounds.getSelectedIndex());
        }
    }

    private void nextFound() {
        var index = fieldFounds.getSelectedIndex();
        if (index < fieldFounds.getModel().getSize() - 1) {
            fieldFounds.setSelectedIndex(index + 1);
            fieldFounds.ensureIndexIsVisible(fieldFounds.getSelectedIndex());
        }
    }

    private void priorScore() {
        var index = fieldScored.getSelectedIndex();
        if (index > 0) {
            fieldScored.setSelectedIndex(index - 1);
            fieldScored.ensureIndexIsVisible(fieldScored.getSelectedIndex());
        }
    }

    private void nextScore() {
        var index = fieldScored.getSelectedIndex();
        if (index < fieldScored.getModel().getSize() - 1) {
            fieldScored.setSelectedIndex(index + 1);
            fieldScored.ensureIndexIsVisible(fieldScored.getSelectedIndex());
        }
    }

    private void priorMapped() {
        var index = fieldMapped.getSelectedIndex();
        if (index > 0) {
            fieldMapped.setSelectedIndex(index - 1);
            fieldMapped.ensureIndexIsVisible(fieldMapped.getSelectedIndex());
        }
    }

    private void nextMapped() {
        var index = fieldMapped.getSelectedIndex();
        if (index < fieldMapped.getModel().getSize() - 1) {
            fieldMapped.setSelectedIndex(index + 1);
            fieldMapped.ensureIndexIsVisible(fieldMapped.getSelectedIndex());
        }
    }

    private void openFolder() {
        var found = fieldFounds.getSelectedValue();
        if (found != null) {
            try {
                Desktop.getDesktop().open(found.file.getParentFile());
            } catch (IOException ex) {
                WizSwing.showError(ex);
            }
        }
    }

    private void openFound() {
        var found = fieldFounds.getSelectedValue();
        if (found != null) {
            try {
                Desktop.getDesktop().open(found.file);
            } catch (IOException ex) {
                WizSwing.showError(ex);
            }
        }
    }

}
