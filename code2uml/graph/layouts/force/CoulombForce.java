/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts.force;

import net.sourceforge.code2uml.graph.layouts.NodeSkeleton;
import net.sourceforge.code2uml.util.MathUtils;
import net.sourceforge.code2uml.util.Vector2D;

class CoulombForce {
    private double coulombFactor = 50.0;
    private static final double maxForce = 1000.0;
    private static final double minDist = 1.0E-4;

    public Vector2D calculateCoulomb(NodeSkeleton node1, NodeSkeleton node2) {
        double charge = (double)(node1.getWidth() + node1.getHeight() + node2.getWidth() + node2.getHeight()) / 4.0;
        double dist = Math.max(1.0E-4, MathUtils.dist(node1.getMiddle(), node2.getMiddle()));
        if (dist > 3.0 * charge) {
            return new Vector2D();
        }
        double force = this.coulombFactor * charge * charge / dist / dist;
        if (Math.abs(force) > 1000.0) {
            force = Math.signum(force) * 1000.0;
        }
        Vector2D result = MathUtils.createUnitVector(node2.getMiddle(), node1.getMiddle());
        result.setX(result.getX() * force);
        result.setY(result.getY() * force);
        return result;
    }
}

