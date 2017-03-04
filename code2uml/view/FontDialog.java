/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FontDialog
extends JDialog {
    private static final int defaultSize = 10;
    private static final int defaultStyle = 0;
    private boolean fontChosen = false;
    private JPanel buttonsPanel;
    private JButton cancelButton;
    private JLabel familyLabel;
    private JList familyList;
    private JScrollPane familyScrollPane;
    private JPanel listsPanel;
    private JButton okButton;
    private JLabel previewLabel;
    private JPanel previewPanel;
    private JLabel sizeLabel;
    private JList sizeList;
    private JScrollPane sizeScrollPane;
    private JLabel styleLabel;
    private JList styleList;
    private JScrollPane styleScrollPane;
    private JLabel txtLabel;

    public FontDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.initFonts();
        this.initStyles();
        this.initSizes();
    }

    public Font getSelectedFont() {
        return this.previewLabel.getFont();
    }

    public boolean isFontChosen() {
        return this.fontChosen;
    }

    private void initFonts() {
        String[] families = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        DefaultListModel<String> model = new DefaultListModel<String>();
        for (String family : families) {
            model.addElement(family);
        }
        this.familyList.setModel(model);
    }

    private void initStyles() {
        DefaultListModel<String> model = new DefaultListModel<String>();
        model.addElement("plain");
        model.addElement("bold");
        model.addElement("italic");
        model.addElement("bold italic");
        this.styleList.setModel(model);
    }

    private void initSizes() {
        Integer[] sizes = new Integer[]{8, 10, 11, 12, 13, 14, 16, 18, 20};
        DefaultListModel<Integer> model = new DefaultListModel<Integer>();
        for (Integer size : sizes) {
            model.addElement(size);
        }
        this.sizeList.setModel(model);
    }

    private int getSelectedStyle() {
        int idx = this.styleList.getSelectedIndex();
        switch (idx) {
            case 0: {
                return 0;
            }
            case 1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            case 3: {
                return 3;
            }
        }
        return 0;
    }

    private int getSelectedSize() {
        Object selected = this.sizeList.getSelectedValue();
        return selected == null ? 10 : (Integer)selected;
    }

    private void initComponents() {
        this.buttonsPanel = new JPanel();
        this.okButton = new JButton();
        this.cancelButton = new JButton();
        this.previewPanel = new JPanel();
        this.txtLabel = new JLabel();
        this.previewLabel = new JLabel();
        this.listsPanel = new JPanel();
        this.sizeScrollPane = new JScrollPane();
        this.sizeList = new JList();
        this.sizeLabel = new JLabel();
        this.styleScrollPane = new JScrollPane();
        this.styleList = new JList();
        this.styleLabel = new JLabel();
        this.familyScrollPane = new JScrollPane();
        this.familyList = new JList();
        this.familyLabel = new JLabel();
        this.setDefaultCloseOperation(2);
        this.okButton.setText("OK");
        this.okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FontDialog.this.okButtonActionPerformed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FontDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        GroupLayout buttonsPanelLayout = new GroupLayout(this.buttonsPanel);
        this.buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup().addComponent(this.cancelButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, 32767).addComponent(this.okButton)));
        buttonsPanelLayout.linkSize(0, this.cancelButton, this.okButton);
        buttonsPanelLayout.setVerticalGroup(buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.okButton).addComponent(this.cancelButton)));
        this.previewPanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
        this.txtLabel.setText("Preview:");
        this.previewLabel.setBackground(new Color(255, 255, 255));
        this.previewLabel.setText("Font font = new Font(family, style, size);");
        this.previewLabel.setOpaque(true);
        GroupLayout previewPanelLayout = new GroupLayout(this.previewPanel);
        this.previewPanel.setLayout(previewPanelLayout);
        previewPanelLayout.setHorizontalGroup(previewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(previewPanelLayout.createSequentialGroup().addContainerGap().addGroup(previewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.previewLabel, -1, 417, 32767).addComponent(this.txtLabel)).addContainerGap()));
        previewPanelLayout.setVerticalGroup(previewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(previewPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.txtLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.previewLabel, -1, 29, 32767).addContainerGap()));
        this.listsPanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaptionBorder));
        this.sizeList.setModel(new AbstractListModel(){
            String[] strings;

            @Override
            public int getSize() {
                return this.strings.length;
            }

            @Override
            public Object getElementAt(int i) {
                return this.strings[i];
            }
        });
        this.sizeList.setSelectionMode(0);
        this.sizeList.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                FontDialog.this.sizeListValueChanged(evt);
            }
        });
        this.sizeScrollPane.setViewportView(this.sizeList);
        this.sizeLabel.setText("Size:");
        this.styleList.setModel(new AbstractListModel(){
            String[] strings;

            @Override
            public int getSize() {
                return this.strings.length;
            }

            @Override
            public Object getElementAt(int i) {
                return this.strings[i];
            }
        });
        this.styleList.setSelectionMode(0);
        this.styleList.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                FontDialog.this.styleListValueChanged(evt);
            }
        });
        this.styleScrollPane.setViewportView(this.styleList);
        this.styleLabel.setText("Style:");
        this.familyList.setModel(new AbstractListModel(){
            String[] strings;

            @Override
            public int getSize() {
                return this.strings.length;
            }

            @Override
            public Object getElementAt(int i) {
                return this.strings[i];
            }
        });
        this.familyList.setSelectionMode(0);
        this.familyList.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                FontDialog.this.familyListValueChanged(evt);
            }
        });
        this.familyScrollPane.setViewportView(this.familyList);
        this.familyLabel.setText("Family:");
        GroupLayout listsPanelLayout = new GroupLayout(this.listsPanel);
        this.listsPanel.setLayout(listsPanelLayout);
        listsPanelLayout.setHorizontalGroup(listsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, listsPanelLayout.createSequentialGroup().addContainerGap().addGroup(listsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.familyLabel).addComponent(this.familyScrollPane, -1, 201, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(listsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.styleLabel, GroupLayout.Alignment.LEADING).addComponent(this.styleScrollPane, -2, 131, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(listsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.sizeLabel).addComponent(this.sizeScrollPane, -2, 61, -2)).addContainerGap()));
        listsPanelLayout.setVerticalGroup(listsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, listsPanelLayout.createSequentialGroup().addContainerGap().addGroup(listsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.sizeLabel).addComponent(this.familyLabel).addComponent(this.styleLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(listsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.styleScrollPane, -1, 208, 32767).addComponent(this.familyScrollPane, -1, 208, 32767).addComponent(this.sizeScrollPane, -1, 208, 32767)).addContainerGap()));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.listsPanel, GroupLayout.Alignment.LEADING, -1, -1, 32767).addComponent(this.previewPanel, GroupLayout.Alignment.LEADING, -1, -1, 32767).addComponent(this.buttonsPanel, -2, -1, -2)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.listsPanel, -1, -1, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.previewPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.buttonsPanel, -2, -1, -2).addContainerGap()));
        this.pack();
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.setVisible(false);
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        this.fontChosen = true;
        this.setVisible(false);
    }

    private void familyListValueChanged(ListSelectionEvent evt) {
        Object selected = this.familyList.getSelectedValue();
        if (selected == null) {
            return;
        }
        String name = selected.toString();
        this.previewLabel.setFont(new Font(name, this.getSelectedStyle(), this.getSelectedSize()));
    }

    private void styleListValueChanged(ListSelectionEvent evt) {
        this.previewLabel.setFont(this.previewLabel.getFont().deriveFont(this.getSelectedStyle()));
    }

    private void sizeListValueChanged(ListSelectionEvent evt) {
        this.previewLabel.setFont(this.previewLabel.getFont().deriveFont((float)this.getSelectedSize()));
    }

}

