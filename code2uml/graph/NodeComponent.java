/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.JComponent;
import net.sourceforge.code2uml.graph.EdgeComponent;
import net.sourceforge.code2uml.util.MouseDragListener;

public abstract class NodeComponent
extends JComponent {
    protected static final int padding = 5;
    private static final int pointPadding = 3;
    private String unitName;
    private Collection<EdgeComponent> edges = new LinkedList<EdgeComponent>();
    private Collection<EdgeComponent> incomingEdges = new LinkedList<EdgeComponent>();
    protected Color backColor;

    public NodeComponent(Color backColor, boolean draggable) {
        this.backColor = backColor;
        if (draggable) {
            this.addMouseListener(new MouseDragListener(this));
        }
    }

    public Point getMiddle() {
        return new Point(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
    }

    public Point getTopMiddle() {
        return new Point(this.getX() + this.getWidth() / 2, this.getY() - 3);
    }

    public Point getBottomMiddle() {
        return new Point(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() + 3);
    }

    public Point getLeftMiddle() {
        return new Point(this.getX() - 3, this.getY() + this.getHeight() / 2);
    }

    public Point getRightMiddle() {
        return new Point(this.getX() + this.getWidth() + 3, this.getY() + this.getHeight() / 2);
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String name) {
        this.unitName = name;
    }

    public Collection<EdgeComponent> getOutEdges() {
        return this.edges;
    }

    public Collection<EdgeComponent> getInEdges() {
        return this.incomingEdges;
    }

    public void addEdge(EdgeComponent edge) {
        this.edges.add(edge);
        edge.getTo().getInEdges().add(edge);
    }

    public abstract void addToName(String var1);

    public abstract void addToEnum(String var1);

    public abstract void addToField(String var1);

    public abstract void addToMethod(String var1);
}

