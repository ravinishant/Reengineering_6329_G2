/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts;

import java.util.Collection;
import java.util.Observable;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponent;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponentFactory;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponentMerger;
import net.sourceforge.code2uml.graph.layouts.GraphLayout;
import net.sourceforge.code2uml.graph.layouts.SimpleConnectedComponentMerger;

public abstract class SplittingGraphLayout
extends Observable
implements GraphLayout {
    private static final int padding = 30;
    private ConnectedComponentFactory factory = new ConnectedComponentFactory(30);
    private ConnectedComponentMerger merger = new SimpleConnectedComponentMerger();

    @Override
    public void layout(Graph graph) {
        Collection<ConnectedComponent> components = this.factory.createFromGraph(graph);
        this.layout(components);
        this.merger.merge(components);
    }

    protected abstract void layout(Collection<ConnectedComponent> var1);
}

