/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.accessibility.AccessibleContext;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.sourceforge.code2uml.view.FilesPanel;
import net.sourceforge.code2uml.view.PreviewPanel;
import net.sourceforge.code2uml.view.SettingsPanel;
import net.sourceforge.code2uml.view.UnitsPanel;

public class MainFrame
extends JFrame {
    private JMenuItem exitMenuItem;
    private JMenu fileMenu;
    private FilesPanel filesPanel1;
    private JMenuBar jMenuBar;
    private JPanel jPanel1;
    private PreviewPanel previewPanel1;
    private SettingsPanel settingsPanel1;
    private JTabbedPane stepsTabbedPane;
    private UnitsPanel unitsPanel1;

    public MainFrame() {
        this.initComponents();
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.stepsTabbedPane = new JTabbedPane();
        this.filesPanel1 = new FilesPanel();
        this.unitsPanel1 = new UnitsPanel();
        this.settingsPanel1 = new SettingsPanel();
        this.previewPanel1 = new PreviewPanel();
        this.jMenuBar = new JMenuBar();
        this.fileMenu = new JMenu();
        this.exitMenuItem = new JMenuItem();
        this.setDefaultCloseOperation(0);
        this.setTitle("Code 2 UML");
        this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent evt) {
                MainFrame.this.formWindowClosing(evt);
            }
        });
        this.stepsTabbedPane.setEnabled(false);
        this.stepsTabbedPane.addTab("Select Files", this.filesPanel1);
        this.stepsTabbedPane.addTab("Select Units", this.unitsPanel1);
        this.stepsTabbedPane.addTab("Settings", this.settingsPanel1);
        this.stepsTabbedPane.addTab("Diagram", this.previewPanel1);
        this.stepsTabbedPane.getAccessibleContext().setAccessibleName("stepsTabbedPane");
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.stepsTabbedPane).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.stepsTabbedPane, -1, 498, 32767).addContainerGap()));
        this.fileMenu.setText("Menu");
        this.exitMenuItem.setText("Exit");
        this.exitMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainFrame.this.exitMenuItemActionPerformed(evt);
            }
        });
        this.fileMenu.add(this.exitMenuItem);
        this.jMenuBar.add(this.fileMenu);
        this.setJMenuBar(this.jMenuBar);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -1, -1, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -1, -1, 32767));
        this.pack();
    }

    private void formWindowClosing(WindowEvent evt) {
        this.shutdown();
    }

    private void shutdown() {
        this.dispose();
    }

    private void exitMenuItemActionPerformed(ActionEvent evt) {
        this.shutdown();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (InstantiationException ex) {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                new MainFramefdfdsfdsfsd().setVisible(true);
            }
        });
    }

}

