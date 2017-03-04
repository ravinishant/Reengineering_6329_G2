/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Graphics;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.NodeComponent;

public interface NodeComponentFactory {
    public NodeComponent create(Graphics var1, ConstructionHints var2);
}

