/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import net.sourceforge.code2uml.view.CheckTreeNode;
import net.sourceforge.code2uml.view.CheckTreeRenderer;

public class PackageCheckTree
extends JTree {
    public PackageCheckTree() {
        this.setModel(null);
        this.setCellRenderer(new CheckTreeRenderer());
        this.setRootVisible(false);
        this.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                TreePath path = PackageCheckTree.this.getPathForLocation(e.getX(), e.getY());
                if (path != null) {
                    CheckTreeNode node;
                    PackageCheckTree.this.setTreeSelected(node, !(node = (CheckTreeNode)path.getLastPathComponent()).isSelected());
                    PackageCheckTree.this.repaint();
                }
            }
        });
    }

    public Map<String, CheckTreeNode> createModel(Collection<String> names) {
        ArrayList<String> sortedNames = new ArrayList<String>(names);
        Collections.sort(sortedNames);
        HashMap<String, CheckTreeNode> result = new HashMap<String, CheckTreeNode>();
        CheckTreeNode root = new CheckTreeNode("root", false);
        DefaultTreeModel model = new DefaultTreeModel(root);
        for (String name : sortedNames) {
            StringTokenizer tokenizer = new StringTokenizer(name, ".");
            CheckTreeNode current = root;
            while (tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();
                boolean exists = false;
                for (int i = 0; i < current.getChildCount(); ++i) {
                    CheckTreeNode child = (CheckTreeNode)current.getChildAt(i);
                    if (!child.getUserObject().toString().equals(str)) continue;
                    current = child;
                    exists = true;
                    break;
                }
                if (exists) continue;
                CheckTreeNode newNode = new CheckTreeNode(str, !str.contains("$"));
                current.add(newNode);
                current = newNode;
            }
            result.put(name, current);
        }
        this.setModel(model);
        return result;
    }

    private void setTreeSelected(CheckTreeNode root, boolean selected) {
        root.setSelected(selected);
        if (!root.isLeaf()) {
            for (int i = 0; i < root.getChildCount(); ++i) {
                CheckTreeNode node = (CheckTreeNode)root.getChildAt(i);
                if (selected && node.getUserObject().toString().contains("$")) continue;
                this.setTreeSelected(node, selected);
            }
        }
    }

}

