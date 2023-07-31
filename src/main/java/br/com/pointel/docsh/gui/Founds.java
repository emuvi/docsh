package br.com.pointel.docsh.gui;

import java.awt.BorderLayout;
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

public class Founds extends JFrame {

    private final JPanel panelRoot = new JPanel(new BorderLayout());
    private final DefaultListModel<Found> modelFounds = new DefaultListModel<>();
    private final JList<Found> fieldFounds = new JList<>(modelFounds);
    private final JScrollPane scrollFounds = new JScrollPane(fieldFounds);
    private final DefaultListModel<Scored> modelScored = new DefaultListModel<>();
    private final JList<Scored> fieldScored = new JList<>(modelScored);
    private final JScrollPane scrollScored = new JScrollPane(fieldScored);
    private final JSplitPane panelTop = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollFounds, scrollScored); 
    private final JTextArea fieldText = new JTextArea();
    private final JScrollPane scrollText = new JScrollPane(fieldText);
    private final JSplitPane panelCenter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelTop, scrollText);

    private final Border borderSpace = BorderFactory.createEmptyBorder(9, 9, 9, 9);
    private final Border borderFields = BorderFactory.createLoweredBevelBorder();


    public Founds(List<Found> founds) {
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
        scrollText.setBorder(borderFields);

        panelRoot.add(panelCenter, BorderLayout.CENTER);
        
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fieldFounds.addListSelectionListener(e -> selectedFound(e));
        fieldScored.addListSelectionListener(e -> selectedScored(e));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var width = getWidth();
                panelTop.setDividerLocation(width / 2);
                var height = getHeight();
                panelCenter.setDividerLocation(height / 4);
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
        System.out.println("Selected Scored");
    }

}
