/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.util.Collection;
import net.sourceforge.code2uml.graph.NodeComponent;

public interface Graph {
    public Collection<NodeComponent> getNodes();

    public int getWidth();

    public int getHeight();
}

