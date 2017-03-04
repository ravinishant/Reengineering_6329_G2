/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sourceforge.code2uml.controller.Controller;
import net.sourceforge.code2uml.controller.ControllerFactory;
import net.sourceforge.code2uml.graph.EdgeComponent;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.NodeComponent;

public class PreviewPanel
extends JPanel
implements Observer {
    private final int graphPadding = 50;
    private Controller controller = ControllerFactory.getInstance();
    private Graph graph;
    private JPanel graphPanel;
    private JScrollPane graphScrollPane;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JButton previousButton;
    private JButton saveImageButton;
    private JProgressBar saveProgressBar;

    public PreviewPanel() {
        this.initComponents();
        this.controller.addObserver(this);
        this.setBGThreadWorking(false);
    }

    private void setBGThreadWorking(final boolean b) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                PreviewPanel.this.saveProgressBar.setVisible(b);
                PreviewPanel.this.previousButton.setEnabled(!b);
                PreviewPanel.this.saveImageButton.setEnabled(!b);
                PreviewPanel.this.graphPanel.setEnabled(!b);
                for (Component comp : PreviewPanel.this.graphPanel.getComponents()) {
                    comp.setEnabled(!b);
                }
            }
        });
    }

    private void setGraph(final Graph g) {
        this.graph = g;
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                PreviewPanel.this.graphPanel.removeAll();
                for (NodeComponent node : g.getNodes()) {
                    for (EdgeComponent edge : node.getOutEdges()) {
                        PreviewPanel.this.graphPanel.add(edge);
                    }
                    PreviewPanel.this.graphPanel.add(node);
                }
                PreviewPanel.this.graphPanel.setPreferredSize(null);
                PreviewPanel.this.graphPanel.setPreferredSize(new Dimension(PreviewPanel.this.graph.getWidth() + 50, PreviewPanel.this.graph.getHeight() + 50));
                JScrollBar vsb = PreviewPanel.this.graphScrollPane.getVerticalScrollBar();
                JScrollBar hsb = PreviewPanel.this.graphScrollPane.getHorizontalScrollBar();
                int maxIncrement = 100;
                int horizontalIncrement = Math.max(maxIncrement, (hsb.getMaximum() - hsb.getMinimum()) / 75);
                int verticalIncrement = Math.max(maxIncrement, (vsb.getMaximum() - vsb.getMinimum()) / 75);
                PreviewPanel.this.graphScrollPane.getVerticalScrollBar().setUnitIncrement(verticalIncrement);
                PreviewPanel.this.graphScrollPane.getHorizontalScrollBar().setUnitIncrement(horizontalIncrement);
                PreviewPanel.this.graphPanel.revalidate();
            }
        });
    }

    private void setAsSelected() {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                JTabbedPane parent = (JTabbedPane)PreviewPanel.this.getParent();
                parent.setSelectedComponent(PreviewPanel.this.self());
            }
        });
    }

    private PreviewPanel self() {
        return this;
    }

    private void initComponents() {
        this.previousButton = new JButton();
        this.graphScrollPane = new JScrollPane();
        this.graphPanel = new JPanel();
        this.jLabel1 = new JLabel();
        this.saveImageButton = new JButton();
        this.jPanel1 = new JPanel();
        this.saveProgressBar = new JProgressBar();
        this.previousButton.setText("Previous");
        this.previousButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PreviewPanel.this.previousButtonActionPerformed(evt);
            }
        });
        this.graphScrollPane.setHorizontalScrollBarPolicy(32);
        this.graphScrollPane.setVerticalScrollBarPolicy(22);
        GroupLayout graphPanelLayout = new GroupLayout(this.graphPanel);
        this.graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(graphPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 549, 32767));
        graphPanelLayout.setVerticalGroup(graphPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 461, 32767));
        this.graphScrollPane.setViewportView(this.graphPanel);
        this.jLabel1.setText("Drag nodes to desired positions and save the image.");
        this.saveImageButton.setText("Save Image");
        this.saveImageButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PreviewPanel.this.saveImageButtonActionPerformed(evt);
            }
        });
        this.saveProgressBar.setStringPainted(true);
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.saveProgressBar, GroupLayout.Alignment.TRAILING, -1, 358, 32767));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.saveProgressBar, GroupLayout.Alignment.TRAILING, -1, 25, 32767));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.graphScrollPane, GroupLayout.Alignment.LEADING, -1, 552, 32767).addComponent(this.jLabel1, GroupLayout.Alignment.LEADING, -1, 552, 32767).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.previousButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel1, -1, -1, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.saveImageButton))).addContainerGap()));
        layout.linkSize(0, this.previousButton, this.saveImageButton);
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, 15, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.graphScrollPane, -1, 464, 32767).addGap(9, 9, 9).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.previousButton).addComponent(this.saveImageButton)).addComponent(this.jPanel1, -2, -1, -2)).addContainerGap()));
        layout.linkSize(1, this.jPanel1, this.previousButton, this.saveImageButton);
    }

    private void saveImageButtonActionPerformed(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("bmp file", "bmp"));
        chooser.setDialogType(1);
        int result = chooser.showSaveDialog(this);
        if (result == 0) {
            FileNameExtensionFilter filter = (FileNameExtensionFilter)chooser.getFileFilter();
            String suffix = "." + filter.getExtensions()[0];
            String filePath = chooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(suffix)) {
                filePath = filePath + suffix;
            }
            this.setBGThreadWorking(true);
            this.controller.saveImage(this.graphPanel, filePath, this.saveProgressBar);
        }
    }

    private void previousButtonActionPerformed(ActionEvent evt) {
        JTabbedPane parent = (JTabbedPane)this.getParent();
        parent.setSelectedIndex(parent.getSelectedIndex() - 1);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == this.controller && arg instanceof Object[]) {
            Object[] tab = (Object[])arg;
            int n = (Integer)tab[0];
            if (n == 2) {
                this.setGraph((Graph)tab[1]);
                this.setAsSelected();
            }
        } else if (o == this.controller && arg instanceof Integer && (Integer)arg == 3) {
            this.setBGThreadWorking(false);
        }
    }

}

