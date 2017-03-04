/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import net.sourceforge.code2uml.view.CheckTreeNode;
import net.sourceforge.code2uml.view.CheckTreeRenderer;

public class CheckTree
extends JTree {
    public CheckTree() {
        this.setModel(null);
        this.setCellRenderer(new CheckTreeRenderer());
        this.setRootVisible(false);
        this.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                TreePath path = CheckTree.this.getPathForLocation(e.getX(), e.getY());
                if (path != null) {
                    CheckTreeNode node;
                    CheckTree.this.setTreeSelected(node, !(node = (CheckTreeNode)path.getLastPathComponent()).isSelected());
                    CheckTree.this.repaint();
                }
            }
        });
    }

    private void setTreeSelected(CheckTreeNode root, boolean selected) {
        root.setSelected(selected);
        if (!root.isLeaf()) {
            for (int i = 0; i < root.getChildCount(); ++i) {
                this.setTreeSelected((CheckTreeNode)root.getChildAt(i), selected);
            }
        }
    }

}

