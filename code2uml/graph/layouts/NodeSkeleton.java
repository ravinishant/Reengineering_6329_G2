/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts;

import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.util.Vector2D;

public class NodeSkeleton {
    private double x;
    private double y;
    private int width;
    private int height;
    private NodeComponent node;

    public NodeSkeleton() {
    }

    public NodeSkeleton(NodeComponent node) {
        this.x = node.getX();
        this.y = node.getY();
        this.width = node.getWidth();
        this.height = node.getHeight();
        this.setNode(node);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
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

    public NodeComponent getNode() {
        return this.node;
    }

    public void setNode(NodeComponent node) {
        this.node = node;
    }

    public void offset(Vector2D v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    public Vector2D getMiddle() {
        return new Vector2D(this.x + (double)(this.width / 2), this.y + (double)(this.height / 2));
    }

    public Vector2D getTopMiddle() {
        return new Vector2D(this.x + (double)(this.width / 2), this.y);
    }

    public Vector2D getBottomMiddle() {
        return new Vector2D(this.x + (double)(this.width / 2), this.y + (double)this.height);
    }

    public Vector2D getLeftMiddle() {
        return new Vector2D(this.x, this.y + (double)(this.height / 2));
    }

    public Vector2D getRightMiddle() {
        return new Vector2D(this.x + (double)this.width, this.y + (double)(this.height / 2));
    }

    public Vector2D getLocation() {
        return new Vector2D(this.x, this.y);
    }

    public void updateNodeComponent() {
        if (this.node != null) {
            this.node.setLocation((int)this.x, (int)this.y);
            this.node.setSize(this.width, this.height);
        }
    }
}

