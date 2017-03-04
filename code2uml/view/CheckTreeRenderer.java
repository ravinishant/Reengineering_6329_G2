/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import net.sourceforge.code2uml.view.CheckTreeNode;

public class CheckTreeRenderer
extends JPanel
implements TreeCellRenderer {
    private JLabel label = new JLabel();
    private JCheckBox checkBox = new JCheckBox();
    private Icon leafIcon;
    private Icon nodeClosedIcon;
    private Icon nodeOpenIcon;

    public CheckTreeRenderer() {
        URL nodeOpenIconUrl;
        URL nodeClosedIconUrl;
        this.setLayout(new BoxLayout(this, 0));
        this.add(this.checkBox);
        this.add(this.label);
        this.checkBox.setOpaque(false);
        this.setOpaque(false);
        URL leafIconUrl = ClassLoader.getSystemResource("net/sourceforge/code2uml/icons/leaf.png");
        if (leafIconUrl != null) {
            this.leafIcon = new ImageIcon(leafIconUrl);
        }
        if ((nodeClosedIconUrl = ClassLoader.getSystemResource("net/sourceforge/code2uml/icons/node-closed.png")) != null) {
            this.nodeClosedIcon = new ImageIcon(nodeClosedIconUrl);
        }
        if ((nodeOpenIconUrl = ClassLoader.getSystemResource("net/sourceforge/code2uml/icons/node-open.png")) != null) {
            this.nodeOpenIcon = new ImageIcon(nodeOpenIconUrl);
        }
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        CheckTreeNode node = (CheckTreeNode)value;
        this.checkBox.setSelected(node.isSelected());
        this.label.setText(node.toString());
        if (leaf) {
            this.label.setIcon(this.leafIcon);
        } else if (expanded) {
            this.label.setIcon(this.nodeOpenIcon);
        } else {
            this.label.setIcon(this.nodeClosedIcon);
        }
        this.setSize(this.checkBox.getWidth() + this.label.getWidth(), Math.max(this.checkBox.getHeight(), this.label.getHeight()));
        return this;
    }
}

