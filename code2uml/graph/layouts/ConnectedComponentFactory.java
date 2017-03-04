/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import net.sourceforge.code2uml.graph.EdgeComponent;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponent;

public class ConnectedComponentFactory {
    private int padding = 0;

    public ConnectedComponentFactory() {
    }

    public ConnectedComponentFactory(int padding) {
        this.padding = padding;
    }

    private void addToComponent(ConnectedComponent component, NodeComponent node) {
        if (component.getNodes().contains(node)) {
            return;
        }
        component.addNode(node);
        for (EdgeComponent e2 : node.getInEdges()) {
            this.addToComponent(component, e2.getFrom());
        }
        for (EdgeComponent e2 : node.getOutEdges()) {
            this.addToComponent(component, e2.getTo());
        }
    }

    private ConnectedComponent createComponent(NodeComponent node) {
        ConnectedComponent result = new ConnectedComponent(this.padding);
        this.addToComponent(result, node);
        return result;
    }

    public Collection<ConnectedComponent> createFromGraph(Graph g) {
        ArrayList<NodeComponent> nodes = new ArrayList<NodeComponent>(g.getNodes());
        ArrayList<ConnectedComponent> components = new ArrayList<ConnectedComponent>();
        while (!nodes.isEmpty()) {
            ConnectedComponent component = this.createComponent(nodes.iterator().next());
            components.add(component);
            nodes.removeAll(component.getNodes());
        }
        return components;
    }
}

