/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts.force;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import net.sourceforge.code2uml.graph.EdgeComponent;
import net.sourceforge.code2uml.graph.EdgeType;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.graph.layouts.ConnectedComponent;
import net.sourceforge.code2uml.graph.layouts.NodeSkeleton;
import net.sourceforge.code2uml.graph.layouts.force.CoulombForce;
import net.sourceforge.code2uml.graph.layouts.force.HookForce;
import net.sourceforge.code2uml.util.Vector2D;

class ConnectedComponentLayout {
    private double desiredDist = 60.0;
    private CoulombForce coulombForce = new CoulombForce();
    private HookForce hookForce = new HookForce();

    private void visit(NodeComponent node, NodeComponent from, EdgeComponent edge, int n) {
        if (node.getX() != 0 || node.getY() != 0) {
            return;
        }
        if (from != null) {
            double x = 0.0;
            double y = 0.0;
            switch (edge.getType()) {
                case GENERALIZATION: {
                    y = - this.desiredDist;
                    break;
                }
                case REALIZATION: {
                    y = - this.desiredDist;
                    break;
                }
                case COMPOSITION: {
                    x = this.desiredDist;
                    break;
                }
                case AGGREGATION: {
                    x = this.desiredDist;
                }
            }
            if (edge.getFrom() == node) {
                x = - x;
                y = - y;
            }
            for (int i = 0; i < n; ++i) {
                x *= 0.9;
                y *= 0.9;
            }
            node.setLocation(from.getX() + (int)x, from.getY() + (int)y);
        } else {
            node.setLocation(1000, 1000);
        }
        for (EdgeComponent e2 : node.getOutEdges()) {
            this.visit(e2.getTo(), node, e2, n + 1);
        }
        for (EdgeComponent e2 : node.getInEdges()) {
            this.visit(e2.getFrom(), node, e2, n + 1);
        }
    }

    private void prelayout(ConnectedComponent component) {
        this.visit(component.getNodes().iterator().next(), null, null, 0);
    }

    public void layout(ConnectedComponent component) {
        double padding = component.getPadding();
        this.prelayout(component);
        int size = component.getNodes().size();
        HashMap<NodeComponent, NodeSkeleton> skeletons = new HashMap<NodeComponent, NodeSkeleton>(size);
        HashMap<NodeSkeleton, Vector2D> newLocations = new HashMap<NodeSkeleton, Vector2D>(size);
        for (NodeComponent node : component.getNodes()) {
            skeletons.put(node, new NodeSkeleton(node));
        }
        int i = 0;
        boolean done = false;
        while (!done) {
            for (NodeComponent node2 : component.getNodes()) {
                NodeSkeleton currentSkeleton = (NodeSkeleton)skeletons.get(node2);
                Vector2D force = new Vector2D();
                for (NodeComponent otherNode : component.getNodes()) {
                    if (node2 == otherNode) continue;
                    force.add(this.coulombForce.calculateCoulomb(currentSkeleton, (NodeSkeleton)skeletons.get(otherNode)));
                }
                for (EdgeComponent edge2 : node2.getOutEdges()) {
                    force.add(this.hookForce.calculateHook(currentSkeleton, (NodeSkeleton)skeletons.get(edge2.getTo()), edge2));
                }
                for (EdgeComponent edge2 : node2.getInEdges()) {
                    force.add(this.hookForce.calculateHook(currentSkeleton, (NodeSkeleton)skeletons.get(edge2.getFrom()), edge2));
                }
                newLocations.put(currentSkeleton, currentSkeleton.getLocation());
                ((Vector2D)newLocations.get(currentSkeleton)).add(force);
                Vector2D newLocation = (Vector2D)newLocations.get(currentSkeleton);
                if (newLocation.getX() < padding) {
                    newLocation.setX(padding);
                }
                if (newLocation.getY() >= padding) continue;
                newLocation.setY(padding);
            }
            for (NodeSkeleton skeleton : newLocations.keySet()) {
                Vector2D v = (Vector2D)newLocations.get(skeleton);
                skeleton.setX(v.getX());
                skeleton.setY(v.getY());
            }
            done = ++i > 20;
        }
        for (NodeComponent node2 : component.getNodes()) {
            NodeSkeleton skeleton = (NodeSkeleton)skeletons.get(node2);
            skeleton.updateNodeComponent();
        }
        this.postlayout(component);
    }

    private void postlayout(ConnectedComponent component) {
        component.packNodes();
        component.updateSize();
    }

}

