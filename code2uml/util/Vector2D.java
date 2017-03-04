/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.util;

import java.awt.Point;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D() {
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Point p) {
        this.setX(p.getX());
        this.setY(p.getY());
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

    public void add(Vector2D v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    public Point toPoint() {
        return new Point((int)this.x, (int)this.y);
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + "]";
    }
}

