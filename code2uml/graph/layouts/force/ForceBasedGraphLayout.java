/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts.force;

import java.util.Collection;
import java.util.Set;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponent;
import net.sourceforge.code2uml.graph.layouts.SplittingGraphLayout;
import net.sourceforge.code2uml.graph.layouts.force.ConnectedComponentLayout;
import net.sourceforge.code2uml.util.ProgressData;

public class ForceBasedGraphLayout
extends SplittingGraphLayout {
    private ConnectedComponentLayout layout = new ConnectedComponentLayout();

    @Override
    protected void layout(Collection<ConnectedComponent> components) {
        int nodesCount = 0;
        int laidoutCount = 0;
        for (ConnectedComponent comp2 : components) {
            nodesCount += comp2.getNodes().size();
        }
        for (ConnectedComponent comp2 : components) {
            this.layout.layout(comp2);
            this.setChanged();
            this.notifyObservers(new ProgressData(100.0 * (double)(laidoutCount += comp2.getNodes().size()) / (double)nodesCount));
            this.clearChanged();
        }
    }
}

