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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import net.sourceforge.code2uml.controller.Controller;
import net.sourceforge.code2uml.controller.ControllerFactory;
import net.sourceforge.code2uml.view.CheckTreeNode;
import net.sourceforge.code2uml.view.FilesPanel;
import net.sourceforge.code2uml.view.PackageCheckTree;

public class UnitsPanel
extends JPanel
implements Observer {
    private Controller controller = ControllerFactory.getInstance();
    private Map<String, CheckTreeNode> unitNode = new HashMap<String, CheckTreeNode>();
    private PackageCheckTree checkTree;
    private JScrollPane checkTreePane;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JProgressBar jProgressBar1;
    private JButton nextButton;
    private JButton previousButton;
    private JPanel progressBarPanel;

    public UnitsPanel() {
        this.initComponents();
        this.controller.addObserver(this);
        this.setBGThreadWorking(false);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == this.controller && arg instanceof Object[]) {
            Object[] tab = (Object[])arg;
            int n = (Integer)tab[0];
            if (n == 4) {
                this.initCheckTree((Collection)tab[1]);
                this.setAsSelected();
            } else if (n == 1) {
                this.setBGThreadWorking(false);
            }
        }
    }

    public Collection<String> getSelectedNames() {
        ArrayList<String> result = new ArrayList<String>();
        for (String name : this.unitNode.keySet()) {
            if (!this.unitNode.get(name).isSelected()) continue;
            result.add(name);
        }
        return result;
    }

    private void setAsSelected() {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                JTabbedPane parent = (JTabbedPane)UnitsPanel.this.getParent();
                parent.setSelectedComponent(UnitsPanel.this.self());
            }
        });
    }

    private void initCheckTree(final Collection<String> names) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                UnitsPanel.this.unitNode = UnitsPanel.this.checkTree.createModel(names);
            }
        });
    }

    private void setBGThreadWorking(final boolean b) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                UnitsPanel.this.jProgressBar1.setVisible(b);
                UnitsPanel.this.nextButton.setEnabled(!b);
                UnitsPanel.this.previousButton.setEnabled(!b);
            }
        });
    }

    private UnitsPanel self() {
        return this;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.checkTreePane = new JScrollPane();
        this.checkTree = new PackageCheckTree();
        this.progressBarPanel = new JPanel();
        this.jProgressBar1 = new JProgressBar();
        this.previousButton = new JButton();
        this.nextButton = new JButton();
        this.jLabel1.setBackground(SystemColor.control);
        this.jLabel1.setText("These are classes, interfaces and enums found in given files.");
        this.jLabel2.setText("Choose those which should be included in the diagram.");
        this.checkTree.setShowsRootHandles(true);
        this.checkTreePane.setViewportView(this.checkTree);
        this.jProgressBar1.setStringPainted(true);
        GroupLayout progressBarPanelLayout = new GroupLayout(this.progressBarPanel);
        this.progressBarPanel.setLayout(progressBarPanelLayout);
        progressBarPanelLayout.setHorizontalGroup(progressBarPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jProgressBar1, -1, 286, 32767));
        progressBarPanelLayout.setVerticalGroup(progressBarPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jProgressBar1, -2, 25, -2));
        this.previousButton.setText("Previous");
        this.previousButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UnitsPanel.this.previousButtonActionPerformed(evt);
            }
        });
        this.nextButton.setText("Next");
        this.nextButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UnitsPanel.this.nextButtonActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.checkTreePane, -1, 448, 32767).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.previousButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.progressBarPanel, -1, -1, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.nextButton)).addComponent(this.jLabel1, -1, 448, 32767).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING, -1, 448, 32767)).addContainerGap()));
        layout.linkSize(0, this.nextButton, this.previousButton);
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.checkTreePane, -1, 285, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.progressBarPanel, -2, -1, -2).addComponent(this.previousButton).addComponent(this.nextButton)).addContainerGap()));
        layout.linkSize(1, this.nextButton, this.previousButton, this.progressBarPanel);
    }

    private void nextButtonActionPerformed(ActionEvent evt) {
        this.setBGThreadWorking(true);
        JTabbedPane parent = (JTabbedPane)this.getParent();
        FilesPanel filesPanel = (FilesPanel)parent.getComponentAt(parent.getSelectedIndex() - 1);
        this.controller.processFiles(filesPanel.getSelectedFiles(), this.getSelectedNames(), this.jProgressBar1);
    }

    private void previousButtonActionPerformed(ActionEvent evt) {
        JTabbedPane parent = (JTabbedPane)this.getParent();
        parent.setSelectedIndex(parent.getSelectedIndex() - 1);
    }

}

