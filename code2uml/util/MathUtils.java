/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.util;

import net.sourceforge.code2uml.util.Vector2D;

public class MathUtils {
    public static double dist(Vector2D v1, Vector2D v2) {
        return Math.sqrt((v1.getX() - v2.getX()) * (v1.getX() - v2.getX()) + (v1.getY() - v2.getY()) * (v1.getY() - v2.getY()));
    }

    public static Vector2D createUnitVector(Vector2D v1, Vector2D v2) {
        double length = Math.max(1.0E-4, MathUtils.dist(v1, v2));
        Vector2D result = new Vector2D(v2.getX() - v1.getX(), v2.getY() - v1.getY());
        result.setX(result.getX() / length);
        result.setY(result.getY() / length);
        return result;
    }
}

