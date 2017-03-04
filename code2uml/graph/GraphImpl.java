/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.util.Collection;
import java.util.HashSet;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.NodeComponent;

public class GraphImpl
implements Graph {
    private Collection<NodeComponent> nodes = new HashSet<NodeComponent>();

    @Override
    public Collection<NodeComponent> getNodes() {
        return this.nodes;
    }

    @Override
    public int getWidth() {
        int result = 0;
        for (NodeComponent node : this.nodes) {
            int x = node.getX() + node.getWidth();
            if (x <= result) continue;
            result = x;
        }
        return result;
    }

    @Override
    public int getHeight() {
        int result = 0;
        for (NodeComponent node : this.nodes) {
            int y = node.getY() + node.getHeight();
            if (y <= result) continue;
            result = y;
        }
        return result;
    }
}

