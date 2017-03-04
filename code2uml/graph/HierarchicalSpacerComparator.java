/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.util.Comparator;
import net.sourceforge.code2uml.graph.NodeComponent;

public class HierarchicalSpacerComparator
implements Comparator<NodeComponent> {
    @Override
    public int compare(NodeComponent o1, NodeComponent o2) {
        if (o1.getX() == o2.getX() && o1.getY() == o2.getY()) {
            return 0;
        }
        if (o1.getX() < o2.getX() || o1.getX() == o2.getX() && o1.getY() < o2.getY()) {
            return -1;
        }
        return 1;
    }
}

