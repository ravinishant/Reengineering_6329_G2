/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts;

import java.util.Collection;
import java.util.Observable;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.graph.layouts.GraphLayout;

public class RectangularGraphLayout
extends Observable
implements GraphLayout {
    private final int padding = 30;

    @Override
    public void layout(Graph g) {
        int maxWidth = 0;
        int maxHeight = 0;
        for (NodeComponent node : g.getNodes()) {
            if (g.getWidth() > maxWidth) {
                maxWidth = g.getWidth();
            }
            if (g.getHeight() <= maxHeight) continue;
            maxHeight = g.getHeight();
        }
        int count = (int)Math.sqrt(g.getNodes().size());
        int x = 30;
        int y = 30;
        int idx = 0;
        for (NodeComponent node2 : g.getNodes()) {
            node2.setLocation(x, y);
            if (++idx >= count) {
                x = 30;
                y += maxHeight + 30;
                idx = 0;
            } else {
                x += maxWidth + 30;
            }
            this.setChanged();
            this.notifyObservers();
            this.clearChanged();
        }
    }
}

