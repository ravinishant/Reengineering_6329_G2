/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import net.sourceforge.code2uml.graph.NodeComponent;

public class ConnectedComponent {
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private int padding = 0;
    private Set<NodeComponent> nodes = new HashSet<NodeComponent>();

    public ConnectedComponent() {
    }

    public ConnectedComponent(int padding) {
        this.padding = padding;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.setLocation(new Point(x, this.getY()));
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.setLocation(new Point(this.getX(), y));
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPadding() {
        return this.padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public Set<NodeComponent> getNodes() {
        return this.nodes;
    }

    public void addNode(NodeComponent node) {
        this.nodes.add(node);
    }

    public void setLocation(Point p) {
        int oldX = this.x;
        int oldY = this.y;
        this.x = (int)p.getX();
        this.y = (int)p.getY();
        int dx = this.x - oldX;
        int dy = this.y - oldY;
        for (NodeComponent node : this.nodes) {
            node.setLocation(node.getX() + dx, node.getY() + dy);
        }
    }

    public void packNodes() {
        int minY = Integer.MAX_VALUE;
        int minX = Integer.MAX_VALUE;
        for (NodeComponent node2 : this.nodes) {
            int x = node2.getX();
            int y = node2.getY();
            if (x < minX) {
                minX = x;
            }
            if (y >= minY) continue;
            minY = y;
        }
        for (NodeComponent node2 : this.nodes) {
            node2.setLocation(node2.getX() - minX + this.padding, node2.getY() - minY + this.padding);
        }
    }

    public void updateSize() {
        int minY = Integer.MAX_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = 0;
        int maxX = 0;
        for (NodeComponent node : this.nodes) {
            int x = node.getX();
            int y = node.getY();
            if (x < minX) {
                minX = x;
            }
            if (x + node.getWidth() > maxX) {
                maxX = x + node.getWidth();
            }
            if (y < minY) {
                minY = y;
            }
            if (y + node.getHeight() <= maxY) continue;
            maxY = y + node.getHeight();
        }
        this.width = maxX - minX + 2 * this.padding;
        this.height = maxY - minY + 2 * this.padding;
    }
}

