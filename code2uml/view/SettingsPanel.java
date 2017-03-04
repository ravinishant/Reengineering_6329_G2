/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.border.Border;
import net.sourceforge.code2uml.controller.Controller;
import net.sourceforge.code2uml.controller.ControllerFactory;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.EdgeType;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.view.FontDialog;
import net.sourceforge.code2uml.view.UnitsPanel;

public class SettingsPanel
extends JPanel
implements Observer {
    private Controller controller = ControllerFactory.getInstance();
    private static final String settingsFileName = ".code2uml.settings";
    private Collection<UnitInfo> units;
    private JCheckBox argumentsCheckBox;
    private JButton colorButton;
    private ButtonGroup drawModeButtonGroup;
    private JCheckBox enumsCheckBox;
    private JRadioButton expandRadioButton;
    private JCheckBox fieldsCheckBox;
    private JCheckBox finalCheckBox;
    private JButton fontButton;
    private JRadioButton fullRadioButton;
    private JCheckBox generalizationChackBox;
    private JCheckBox hasACheckBox;
    private JComboBox hasATypeComboBox;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JProgressBar jProgressBar;
    private JCheckBox methodsCheckBox;
    private JButton nextButton;
    private JCheckBox packageCheckBox;
    private JLabel previewLabel;
    private JButton previousButton;
    private JCheckBox privateCheckBox;
    private JPanel progressBarPanel;
    private JCheckBox protectedCheckBox;
    private JCheckBox publicCheckBox;
    private JCheckBox publicUnitsCheckBox;
    private JCheckBox realizationCheckBox;
    private JCheckBox staticCheckBox;

    public SettingsPanel() {
        this.initComponents();
        this.controller.addObserver(this);
        this.setBGThreadWorking(false);
        this.loadSettings();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == this.controller && arg instanceof Object[]) {
            Object[] tab = (Object[])arg;
            if ((Integer)tab[0] == 2) {
                this.setBGThreadWorking(false);
            }
            if ((Integer)tab[0] == 1) {
                this.setAsSelected();
                this.units = (Collection)tab[1];
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadSettings() {
        File file = new File(".code2uml.settings");
        if (file.exists()) {
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new FileInputStream(file));
                Object obj = in.readObject();
                if (obj instanceof ConstructionHints) {
                    this.initFromHints((ConstructionHints)obj);
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private void setAsSelected() {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                JTabbedPane parent = (JTabbedPane)SettingsPanel.this.getParent();
                parent.setSelectedComponent(SettingsPanel.this.self());
            }
        });
    }

    private SettingsPanel self() {
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveSettings(ConstructionHints hints) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(".code2uml.settings"));
            out.writeObject(hints);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void initFromHints(ConstructionHints hints) {
        this.privateCheckBox.setSelected(hints.isPrivateVisible());
        this.protectedCheckBox.setSelected(hints.isProtectedVisible());
        this.packageCheckBox.setSelected(hints.isPackageVisible());
        this.publicCheckBox.setSelected(hints.isPublicVisible());
        this.staticCheckBox.setSelected(hints.isStaticVisible());
        this.finalCheckBox.setSelected(hints.isFinalVisible());
        this.fieldsCheckBox.setSelected(hints.isFieldsVisible());
        this.methodsCheckBox.setSelected(hints.isMethodsVisible());
        this.argumentsCheckBox.setSelected(hints.isArgumentsVisible());
        this.enumsCheckBox.setSelected(hints.isEnumsVisible());
        this.publicUnitsCheckBox.setSelected(!hints.isNonpublicUnitsVisible());
        this.generalizationChackBox.setSelected(hints.isGeneralizationDrawn());
        this.realizationCheckBox.setSelected(hints.isRealizationDrawn());
        this.hasACheckBox.setSelected(hints.isHasADrawn());
        this.hasATypeComboBox.setSelectedItem(hints.getHasAType().toString().toLowerCase() + "s");
        this.previewLabel.setFont(hints.getFont());
        this.previewLabel.setBackground(hints.getBackColor());
        if (hints.getNodeName().equals("infoNodeComponent")) {
            this.expandRadioButton.setSelected(true);
        } else {
            this.fullRadioButton.setSelected(true);
        }
    }

    private ConstructionHints getHints() {
        ConstructionHints hints = new ConstructionHints();
        hints.setArgumentsVisible(this.argumentsCheckBox.isSelected());
        hints.setBackColor(this.previewLabel.getBackground());
        hints.setEnumsVisible(this.enumsCheckBox.isSelected());
        hints.setFieldsVisible(this.fieldsCheckBox.isSelected());
        hints.setFinalVisible(this.finalCheckBox.isSelected());
        hints.setNonpublicUnitsVisible(!this.publicUnitsCheckBox.isSelected());
        hints.setFont(this.previewLabel.getFont());
        hints.setMethodsVisible(this.methodsCheckBox.isSelected());
        hints.setPackageVisible(this.packageCheckBox.isSelected());
        hints.setPrivateVisible(this.privateCheckBox.isSelected());
        hints.setProtectedVisible(this.protectedCheckBox.isSelected());
        hints.setPublicVisible(this.publicCheckBox.isSelected());
        hints.setStaticVisible(this.staticCheckBox.isSelected());
        hints.setRealizationDrawn(this.realizationCheckBox.isSelected());
        hints.setGeneralizationDrawn(this.generalizationChackBox.isSelected());
        if (this.hasACheckBox.isSelected()) {
            hints.setHasADrawn(true);
            if (this.hasATypeComboBox.getSelectedItem().equals("aggregations")) {
                hints.setHasAType(EdgeType.AGGREGATION);
            } else {
                hints.setHasAType(EdgeType.COMPOSITION);
            }
        }
        if (this.expandRadioButton.isSelected()) {
            hints.setNodeName("infoNodeComponent");
        } else if (this.fullRadioButton.isSelected()) {
            hints.setNodeName("basicNodeComponent");
        }
        return hints;
    }

    private void setBGThreadWorking(final boolean b) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                SettingsPanel.this.jProgressBar.setVisible(b);
                SettingsPanel.this.previousButton.setEnabled(!b);
                SettingsPanel.this.fontButton.setEnabled(!b);
                SettingsPanel.this.colorButton.setEnabled(!b);
                SettingsPanel.this.nextButton.setEnabled(!b);
            }
        });
    }

    private void initComponents() {
        this.drawModeButtonGroup = new ButtonGroup();
        this.jLabel1 = new JLabel();
        this.jPanel1 = new JPanel();
        this.nextButton = new JButton();
        this.previousButton = new JButton();
        this.progressBarPanel = new JPanel();
        this.jProgressBar = new JProgressBar();
        this.expandRadioButton = new JRadioButton();
        this.fullRadioButton = new JRadioButton();
        this.jLabel2 = new JLabel();
        this.previewLabel = new JLabel();
        this.colorButton = new JButton();
        this.fontButton = new JButton();
        this.generalizationChackBox = new JCheckBox();
        this.realizationCheckBox = new JCheckBox();
        this.hasACheckBox = new JCheckBox();
        this.jLabel3 = new JLabel();
        this.hasATypeComboBox = new JComboBox();
        this.argumentsCheckBox = new JCheckBox();
        this.methodsCheckBox = new JCheckBox();
        this.enumsCheckBox = new JCheckBox();
        this.fieldsCheckBox = new JCheckBox();
        this.privateCheckBox = new JCheckBox();
        this.protectedCheckBox = new JCheckBox();
        this.packageCheckBox = new JCheckBox();
        this.publicCheckBox = new JCheckBox();
        this.finalCheckBox = new JCheckBox();
        this.staticCheckBox = new JCheckBox();
        this.publicUnitsCheckBox = new JCheckBox();
        this.jLabel1.setText("Choose what and how should be shown on the diagram.");
        this.nextButton.setText("Next");
        this.nextButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SettingsPanel.this.nextButtonActionPerformed(evt);
            }
        });
        this.previousButton.setText("Previous");
        this.previousButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SettingsPanel.this.previousButtonActionPerformed(evt);
            }
        });
        GroupLayout progressBarPanelLayout = new GroupLayout(this.progressBarPanel);
        this.progressBarPanel.setLayout(progressBarPanelLayout);
        progressBarPanelLayout.setHorizontalGroup(progressBarPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jProgressBar, GroupLayout.Alignment.TRAILING, -1, 210, 32767));
        progressBarPanelLayout.setVerticalGroup(progressBarPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jProgressBar, GroupLayout.Alignment.TRAILING, -1, 25, 32767));
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.previousButton, -2, 95, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.progressBarPanel, -1, -1, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.nextButton)));
        jPanel1Layout.linkSize(0, this.nextButton, this.previousButton);
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.progressBarPanel, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addComponent(this.nextButton, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addComponent(this.previousButton, GroupLayout.Alignment.TRAILING, -1, -1, 32767))));
        jPanel1Layout.linkSize(1, this.nextButton, this.previousButton);
        this.drawModeButtonGroup.add(this.expandRadioButton);
        this.expandRadioButton.setSelected(true);
        this.expandRadioButton.setText("expandable - only names, expands to full");
        this.expandRadioButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.expandRadioButton.setMargin(new Insets(0, 0, 0, 0));
        this.drawModeButtonGroup.add(this.fullRadioButton);
        this.fullRadioButton.setText("full - always all information");
        this.fullRadioButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.fullRadioButton.setMargin(new Insets(0, 0, 0, 0));
        this.jLabel2.setText("Elements draw mode:");
        this.previewLabel.setBackground(new Color(255, 255, 255));
        this.previewLabel.setText("ABCDEF abcdef");
        this.previewLabel.setOpaque(true);
        this.colorButton.setText("Back Color");
        this.colorButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SettingsPanel.this.colorButtonActionPerformed(evt);
            }
        });
        this.fontButton.setText("Font");
        this.fontButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SettingsPanel.this.fontButtonActionPerformed(evt);
            }
        });
        this.generalizationChackBox.setSelected(true);
        this.generalizationChackBox.setText("show generalization relationships");
        this.generalizationChackBox.setToolTipText("Show class inheritance.");
        this.generalizationChackBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.generalizationChackBox.setMargin(new Insets(0, 0, 0, 0));
        this.realizationCheckBox.setSelected(true);
        this.realizationCheckBox.setText("show realization relationships");
        this.realizationCheckBox.setToolTipText("Show interface implementation.");
        this.realizationCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.realizationCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.hasACheckBox.setSelected(true);
        this.hasACheckBox.setText("show \"has a\" relationships");
        this.hasACheckBox.setToolTipText("Show \"has a\" relationships.");
        this.hasACheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.hasACheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.hasACheckBox.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                SettingsPanel.this.hasACheckBoxItemStateChanged(evt);
            }
        });
        this.jLabel3.setText("...as:");
        this.hasATypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[]{"aggregations", "compositions"}));
        this.argumentsCheckBox.setSelected(true);
        this.argumentsCheckBox.setText("show methods' arguments");
        this.argumentsCheckBox.setToolTipText("Show types of arguments of methods.");
        this.argumentsCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.argumentsCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.methodsCheckBox.setSelected(true);
        this.methodsCheckBox.setText("show methods");
        this.methodsCheckBox.setToolTipText("Show methods of classes/interfaces/enums.");
        this.methodsCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.methodsCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.enumsCheckBox.setSelected(true);
        this.enumsCheckBox.setText("show enum values");
        this.enumsCheckBox.setToolTipText("Show enum values in enum types.");
        this.enumsCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.enumsCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.fieldsCheckBox.setSelected(true);
        this.fieldsCheckBox.setText("show fields");
        this.fieldsCheckBox.setToolTipText("Show fields of classes/interfaces/enums.");
        this.fieldsCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.fieldsCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.privateCheckBox.setSelected(true);
        this.privateCheckBox.setText("show private members");
        this.privateCheckBox.setToolTipText("Show private fields/methods of classes/interfaces/enums.");
        this.privateCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.privateCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.protectedCheckBox.setSelected(true);
        this.protectedCheckBox.setText("show protected members");
        this.protectedCheckBox.setToolTipText("Show protected fields/methods of classes/interfaces/enums.");
        this.protectedCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.protectedCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.packageCheckBox.setSelected(true);
        this.packageCheckBox.setText("show package members");
        this.packageCheckBox.setToolTipText("Show package fields/methods of classes/interfaces/enums.");
        this.packageCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.packageCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.publicCheckBox.setSelected(true);
        this.publicCheckBox.setText("show public members");
        this.publicCheckBox.setToolTipText("Show public fields/methods of classes/interfaces/enums.");
        this.publicCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.publicCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.finalCheckBox.setSelected(true);
        this.finalCheckBox.setText("show final fields");
        this.finalCheckBox.setToolTipText("Show fields which values cannot change after initialization.");
        this.finalCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.finalCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.staticCheckBox.setSelected(true);
        this.staticCheckBox.setText("show static members");
        this.staticCheckBox.setToolTipText("Show static fields and methods.");
        this.staticCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.staticCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.publicUnitsCheckBox.setText("show public units only");
        this.publicUnitsCheckBox.setToolTipText("Show only those classes/interfaces/enums which are visible outside packages they are defined in.");
        this.publicUnitsCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.publicUnitsCheckBox.setMargin(new Insets(0, 0, 0, 0));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jPanel1, GroupLayout.Alignment.LEADING, -1, -1, 32767).addComponent(this.jLabel1, -1, 412, 32767)).addContainerGap()).addGroup(layout.createSequentialGroup().addComponent(this.finalCheckBox).addContainerGap(302, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.generalizationChackBox).addContainerGap(194, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.realizationCheckBox).addContainerGap(217, 32767)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.previewLabel, -1, 299, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.colorButton).addComponent(this.fontButton)).addContainerGap()).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 252, -2)).addComponent(this.expandRadioButton, -1, 386, 32767).addComponent(this.fullRadioButton, -1, 386, 32767)).addGap(38, 38, 38)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.hasACheckBox).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3)).addComponent(this.argumentsCheckBox).addComponent(this.methodsCheckBox).addComponent(this.enumsCheckBox).addComponent(this.fieldsCheckBox).addComponent(this.staticCheckBox)).addGap(14, 14, 14).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.publicUnitsCheckBox).addComponent(this.privateCheckBox).addComponent(this.protectedCheckBox).addComponent(this.packageCheckBox).addComponent(this.publicCheckBox).addComponent(this.hasATypeComboBox, -2, -1, -2)).addContainerGap(-1, 32767)))));
        layout.linkSize(0, this.colorButton, this.fontButton);
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, 23, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.staticCheckBox).addComponent(this.publicUnitsCheckBox)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.finalCheckBox).addGap(25, 25, 25).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.fieldsCheckBox).addComponent(this.privateCheckBox)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.enumsCheckBox).addComponent(this.protectedCheckBox)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.methodsCheckBox).addComponent(this.packageCheckBox)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.argumentsCheckBox).addComponent(this.publicCheckBox)).addGap(26, 26, 26).addComponent(this.generalizationChackBox).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.realizationCheckBox).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hasACheckBox).addComponent(this.hasATypeComboBox, -2, -1, -2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.colorButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.fontButton)).addComponent(this.previewLabel, -2, 56, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.fullRadioButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.expandRadioButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jPanel1, -2, -1, -2).addContainerGap()));
    }

    private void hasACheckBoxItemStateChanged(ItemEvent evt) {
        boolean selected = this.hasACheckBox.isSelected();
        this.jLabel3.setVisible(selected);
        this.hasATypeComboBox.setVisible(selected);
    }

    private void fontButtonActionPerformed(ActionEvent evt) {
        Container parent = this.getParent();
        while (!(parent instanceof Frame)) {
            parent = parent.getParent();
        }
        FontDialog dialog = new FontDialog((Frame)parent, true);
        dialog.setVisible(true);
        if (dialog.isFontChosen()) {
            this.previewLabel.setFont(dialog.getSelectedFont());
        }
    }

    private void nextButtonActionPerformed(ActionEvent evt) {
        this.setBGThreadWorking(true);
        ConstructionHints hints = this.getHints();
        this.saveSettings(hints);
        JTabbedPane parent = (JTabbedPane)this.getParent();
        UnitsPanel unitsPanel = (UnitsPanel)parent.getComponentAt(parent.getSelectedIndex() - 1);
        this.controller.processUnits(this.units, this.jProgressBar, hints, this.getGraphics());
    }

    private void previousButtonActionPerformed(ActionEvent evt) {
        JTabbedPane parent = (JTabbedPane)this.getParent();
        parent.setSelectedIndex(parent.getSelectedIndex() - 1);
    }

    private void colorButtonActionPerformed(ActionEvent evt) {
        JColorChooser chooser = new JColorChooser();
        Color color = JColorChooser.showDialog(this, "select back color", this.jLabel2.getBackground());
        if (color != null) {
            this.previewLabel.setBackground(color);
        }
    }

}

