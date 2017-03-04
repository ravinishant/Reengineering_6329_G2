/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts.force;

import net.sourceforge.code2uml.graph.EdgeComponent;
import net.sourceforge.code2uml.graph.EdgeType;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.graph.layouts.NodeSkeleton;
import net.sourceforge.code2uml.util.MathUtils;
import net.sourceforge.code2uml.util.Vector2D;

class HookForce {
    private static final double hookFactor = 0.1;
    private static final double desiredHookDist = 30.0;
    private static final double maxForce = 1000.0;
    private static final double minDist = 1.0E-4;

    public Vector2D calculateHook(NodeSkeleton node1, NodeSkeleton node2, EdgeComponent edge) {
        Vector2D fromPoint;
        NodeSkeleton toSkeleton;
        NodeSkeleton fromSkeleton;
        Vector2D toPoint;
        Vector2D point1;
        Vector2D point2;
        if (node1.getNode() == edge.getFrom()) {
            fromSkeleton = node1;
            toSkeleton = node2;
        } else {
            fromSkeleton = node2;
            toSkeleton = node1;
        }
        if (edge.getType().equals((Object)EdgeType.REALIZATION) || edge.getType().equals((Object)EdgeType.GENERALIZATION)) {
            fromPoint = fromSkeleton.getTopMiddle();
            toPoint = toSkeleton.getBottomMiddle();
        } else if (fromSkeleton.getMiddle().getX() > toSkeleton.getMiddle().getX()) {
            fromPoint = fromSkeleton.getLeftMiddle();
            toPoint = toSkeleton.getRightMiddle();
        } else {
            fromPoint = fromSkeleton.getRightMiddle();
            toPoint = toSkeleton.getLeftMiddle();
        }
        if (node1.getNode() == edge.getFrom()) {
            point1 = fromPoint;
            point2 = toPoint;
        } else {
            point2 = fromPoint;
            point1 = toPoint;
        }
        double dist = Math.max(1.0E-4, MathUtils.dist(fromPoint, toPoint));
        double force = -0.1 * (dist - 30.0);
        if (Math.abs(force) > 1000.0) {
            force = Math.signum(force) * 1000.0;
        }
        Vector2D result = MathUtils.createUnitVector(point2, point1);
        result.setX(result.getX() * force);
        result.setY(result.getY() * force);
        return result;
    }
}

