/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import net.sourceforge.code2uml.graph.BasicNodeComponent;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.InfoNodeComponent;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.graph.NodeComponentFactory;

public class NodeComponentFactoryImpl
implements NodeComponentFactory {
    @Override
    public NodeComponent create(Graphics g, ConstructionHints hints) {
        String name = hints.getNodeName();
        if (name.equals("infoNodeComponent")) {
            return new InfoNodeComponent(g.getFontMetrics(hints.getFont()), hints.getBackColor(), true);
        }
        if (name.equals("basicNodeComponent")) {
            return new BasicNodeComponent(g.getFontMetrics(hints.getFont()), hints.getBackColor(), true);
        }
        return new BasicNodeComponent(g.getFontMetrics(hints.getFont()), hints.getBackColor(), true);
    }
}

