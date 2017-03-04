/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts;

import java.util.Comparator;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponent;

class ConnectedComponentComparator
implements Comparator<ConnectedComponent> {
    @Override
    public int compare(ConnectedComponent o1, ConnectedComponent o2) {
        if (o1.getWidth() == o2.getWidth()) {
            if (o1.getHeight() == o2.getHeight()) {
                return 0;
            }
            return o1.getHeight() > o2.getHeight() ? 1 : -1;
        }
        return o1.getWidth() > o2.getWidth() ? 1 : -1;
    }
}

