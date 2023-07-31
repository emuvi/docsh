package br.com.pointel.docsh.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import br.com.pointel.docsh.lib.Scored;
import br.com.pointel.docsh.lib.WordMap;

public class GuiFounds extends JFrame {

    private final JPanel panelRoot = new JPanel(new BorderLayout());
    private final DefaultListModel<Found> modelFounds = new DefaultListModel<>();
    private final JList<Found> fieldFounds = new JList<>(modelFounds);
    private final JScrollPane scrollFounds = new JScrollPane(fieldFounds);
    
    private final DefaultListModel<Scored> modelScored = new DefaultListModel<>();
    private final JList<Scored> fieldScored = new JList<>(modelScored);
    private final JScrollPane scrollScored = new JScrollPane(fieldScored);
    private final DefaultListModel<WordMap> modelMapped = new DefaultListModel<>();
    private final JList<WordMap> fieldMapped = new JList<>(modelMapped);
    private final JScrollPane scrollMapped = new JScrollPane(fieldMapped);
    private final JSplitPane panelRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollScored, scrollMapped);
    private final JSplitPane panelTop = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollFounds, panelRight); 
    private final JTextArea fieldText = new JTextArea();
    private final JScrollPane scrollText = new JScrollPane(fieldText);
    private final JSplitPane panelCenter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelTop, scrollText);

    private final Border borderSpace = BorderFactory.createEmptyBorder(9, 9, 9, 9);
    private final Border borderFields = BorderFactory.createLoweredBevelBorder();


    public GuiFounds(List<Found> founds) {
        this.modelFounds.addAll(founds);
        initComponents();
    }

    private void initComponents() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Docsh - Founds");
        setContentPane(panelRoot);

        panelRoot.setBorder(borderSpace);
        scrollFounds.setBorder(borderFields);
        scrollScored.setBorder(borderFields);
        scrollMapped.setBorder(borderFields);
        scrollText.setBorder(borderFields);

        panelRoot.add(panelCenter, BorderLayout.CENTER);

        var font = new Font(Font.MONOSPACED, 0, 18);
        fieldFounds.setFont(font);
        fieldScored.setFont(font);
        fieldMapped.setFont(font);
        fieldText.setFont(font);
        
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fieldFounds.addListSelectionListener(e -> selectedFound(e));
        fieldScored.addListSelectionListener(e -> selectedScored(e));

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
        }
    }

    private void selectedScored(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        modelMapped.removeAllElements();
        var scored = fieldScored.getSelectedValue();
        for (var point : scored.points) {
            modelMapped.addElement(point);
        }
    }

}
