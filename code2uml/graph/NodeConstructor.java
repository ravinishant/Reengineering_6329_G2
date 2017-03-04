/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Graphics;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.unitdata.UnitInfo;

public interface NodeConstructor {
    public NodeComponent construct(UnitInfo var1, Graphics var2, ConstructionHints var3);
}

