/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Observer;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.unitdata.UnitInfo;

public interface GraphConstructor {
    public Graph construct(Collection<UnitInfo> var1, Graphics var2, ConstructionHints var3);

    public void addObserver(Observer var1);
}

