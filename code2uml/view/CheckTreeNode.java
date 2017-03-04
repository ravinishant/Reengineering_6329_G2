/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.view;

import javax.swing.tree.DefaultMutableTreeNode;

public class CheckTreeNode
extends DefaultMutableTreeNode {
    private boolean selected;

    public CheckTreeNode() {
    }

    public CheckTreeNode(Object userObject, boolean selected) {
        super(userObject);
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

