/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts;

import java.util.Observer;
import net.sourceforge.code2uml.graph.Graph;

public interface GraphLayout {
    public void layout(Graph var1);

    public void addObserver(Observer var1);
}

