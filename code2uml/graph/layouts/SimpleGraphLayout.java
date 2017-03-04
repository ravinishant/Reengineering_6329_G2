/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts;

import java.util.Collection;
import java.util.Observable;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.graph.layouts.GraphLayout;

public class SimpleGraphLayout
extends Observable
implements GraphLayout {
    @Override
    public void layout(Graph graph) {
        for (NodeComponent node : graph.getNodes()) {
            node.setLocation(0, 0);
            this.setChanged();
            this.notifyObservers();
            this.clearChanged();
        }
    }
}

