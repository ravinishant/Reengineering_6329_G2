/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponent;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponentComparator;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponentMerger;

class SimpleConnectedComponentMerger
implements ConnectedComponentMerger {
    @Override
    public void merge(Collection<ConnectedComponent> components) {
        ArrayList<ConnectedComponent> al = new ArrayList<ConnectedComponent>(components);
        Collections.sort(al, new ConnectedComponentComparator());
        int area = 0;
        for (ConnectedComponent comp : components) {
            area += comp.getWidth() * comp.getHeight();
        }
        double desiredWidth = Math.sqrt(area);
        double currentX = 0.0;
        double currentY = 0.0;
        double maxHeight = 0.0;
        for (ConnectedComponent comp2 : al) {
            if (currentX + (double)comp2.getWidth() > desiredWidth) {
                currentX = 0.0;
                currentY += maxHeight;
                maxHeight = 0.0;
            }
            comp2.setLocation(new Point((int)currentX, (int)currentY));
            currentX += (double)comp2.getWidth();
            if ((double)comp2.getHeight() <= maxHeight) continue;
            maxHeight = comp2.getHeight();
        }
    }
}

