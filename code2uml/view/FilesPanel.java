/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ListModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sourceforge.code2uml.controller.Controller;
import net.sourceforge.code2uml.controller.ControllerFactory;

public class FilesPanel
extends JPanel
implements Observer {
    private Collection<String> selectedFiles = new ArrayList<String>();
    private Controller controller = ControllerFactory.getInstance();
    private JButton addButton;
    private JLabel jLabel1;
    private JProgressBar jProgressBar;
    private JButton nextButton;
    private JList pathsList;
    private JScrollPane pathsListPane;
    private JPanel progressBarPanel;
    private JButton removeButton;

    public FilesPanel() {
        this.initComponents();
        this.pathsList.setModel(new DefaultListModel());
        this.controller.addObserver(this);
        this.setBGThreadWorking(false);
    }

    public Collection<String> getSelectedFiles() {
        return this.selectedFiles;
    }

    @Override
    public void update(Observable o, Object arg) {
        Object[] tab;
        if (o == this.controller && arg instanceof Object[] && (Integer)(tab = (Object[])arg)[0] == 4) {
            this.setBGThreadWorking(false);
        }
    }

    private void setBGThreadWorking(final boolean b) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                FilesPanel.this.jProgressBar.setVisible(b);
                FilesPanel.this.addButton.setEnabled(!b);
                FilesPanel.this.removeButton.setEnabled(!b);
                FilesPanel.this.nextButton.setEnabled(!b);
            }
        });
    }

    private void initComponents() {
        this.pathsListPane = new JScrollPane();
        this.pathsList = new JList();
        this.jLabel1 = new JLabel();
        this.nextButton = new JButton();
        this.removeButton = new JButton();
        this.addButton = new JButton();
        this.progressBarPanel = new JPanel();
        this.jProgressBar = new JProgressBar();
        this.pathsList.setModel(new AbstractListModel(){
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
        this.pathsListPane.setViewportView(this.pathsList);
        this.jLabel1.setBackground(SystemColor.control);
        this.jLabel1.setText("Use 'Add' button to add .jar or .class files from which you want to create UML class diagram.");
        this.jLabel1.setVerticalAlignment(3);
        this.nextButton.setText("Next");
        this.nextButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FilesPanel.this.nextButtonActionPerformed(evt);
            }
        });
        this.removeButton.setText("Remove");
        this.removeButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FilesPanel.this.removeButtonActionPerformed(evt);
            }
        });
        this.addButton.setText("Add");
        this.addButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FilesPanel.this.addButtonActionPerformed(evt);
            }
        });
        this.jProgressBar.setStringPainted(true);
        GroupLayout progressBarPanelLayout = new GroupLayout(this.progressBarPanel);
        this.progressBarPanel.setLayout(progressBarPanelLayout);
        progressBarPanelLayout.setHorizontalGroup(progressBarPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jProgressBar, -1, 309, 32767));
        progressBarPanelLayout.setVerticalGroup(progressBarPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jProgressBar, -1, 25, 32767));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel1, GroupLayout.Alignment.LEADING, -1, -1, 32767).addComponent(this.pathsListPane, GroupLayout.Alignment.LEADING, -1, 576, 32767).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.addButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.removeButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.progressBarPanel, -1, -1, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.nextButton))).addContainerGap()));
        layout.linkSize(0, this.addButton, this.nextButton, this.removeButton);
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, 18, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.pathsListPane, -1, 349, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.progressBarPanel, -1, -1, 32767).addComponent(this.nextButton, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addComponent(this.removeButton, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addComponent(this.addButton, GroupLayout.Alignment.TRAILING, -1, -1, 32767)).addContainerGap()));
    }

    private void nextButtonActionPerformed(ActionEvent evt) {
        this.setBGThreadWorking(true);
        this.controller.retrieveNames(this.selectedFiles, this.jProgressBar);
    }

    private void removeButtonActionPerformed(ActionEvent evt) {
        DefaultListModel model = (DefaultListModel)this.pathsList.getModel();
        for (Object selected : this.pathsList.getSelectedValues()) {
            this.selectedFiles.remove(selected);
            model.removeElement(selected);
        }
    }

    private void addButtonActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filer = new FileNameExtensionFilter(".class and .jars", "class", "jar");
        fileChooser.setFileFilter(filer);
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(this);
        if (result == 0) {
            File[] selected;
            DefaultListModel model = (DefaultListModel)this.pathsList.getModel();
            for (File file : selected = fileChooser.getSelectedFiles()) {
                String path = file.getAbsolutePath();
                this.selectedFiles.add(path);
                if (model.contains(path)) continue;
                model.addElement(path);
            }
        }
    }

}

