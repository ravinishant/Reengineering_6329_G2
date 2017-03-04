/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JComponent;
import net.sourceforge.code2uml.graph.EdgeType;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.util.Draw;

public class EdgeComponent
extends JComponent
implements ComponentListener {
    private static final int minSize = 8;
    private NodeComponent from;
    private NodeComponent to;
    private EdgeType type;

    public NodeComponent getFrom() {
        return this.from;
    }

    public void setFrom(NodeComponent from) {
        if (this.from != null) {
            this.from.removeComponentListener(this);
        }
        this.from = from;
        from.addComponentListener(this);
    }

    public NodeComponent getTo() {
        return this.to;
    }

    public void setTo(NodeComponent to) {
        if (this.to != null) {
            this.to.removeComponentListener(this);
        }
        this.to = to;
        this.to.addComponentListener(this);
    }

    public EdgeType getType() {
        return this.type;
    }

    public void setType(EdgeType type) {
        this.type = type;
    }

    @Override
    public void paintComponent(Graphics g) {
        Point[] p = this.selectPoints();
        int x1 = (int)p[0].getX() - this.getX();
        int x2 = (int)p[1].getX() - this.getX();
        int y1 = (int)p[0].getY() - this.getY();
        int y2 = (int)p[1].getY() - this.getY();
        switch (this.getType()) {
            case GENERALIZATION: {
                Draw.drawArrow(g, x1, y1, x2, y2);
                break;
            }
            case REALIZATION: {
                Draw.drawDashedArrow(g, x1, y1, x2, y2);
                break;
            }
            case AGGREGATION: {
                Draw.drawDiamondEndLine(g, x2, y2, x1, y1, false);
                break;
            }
            case COMPOSITION: {
                Draw.drawDiamondEndLine(g, x2, y2, x1, y1, true);
                break;
            }
            default: {
                g.drawLine(x1, y1, x2, y2);
            }
        }
        g.dispose();
    }

    private Point[] selectPoints() {
        if (this.type.equals((Object)EdgeType.REALIZATION) || this.type.equals((Object)EdgeType.GENERALIZATION)) {
            return new Point[]{this.from.getTopMiddle(), this.to.getBottomMiddle()};
        }
        if (this.from.getMiddle().getX() > this.to.getMiddle().getX()) {
            return new Point[]{this.from.getLeftMiddle(), this.to.getRightMiddle()};
        }
        return new Point[]{this.from.getRightMiddle(), this.to.getLeftMiddle()};
    }

    private void update() {
        Point[] p = this.selectPoints();
        int newX = (int)Math.min(p[0].getX(), p[1].getX()) - 4;
        int newWidth = (int)Math.abs(p[0].getX() - p[1].getX()) + 8;
        int newY = (int)Math.min(p[0].getY(), p[1].getY()) - 4;
        int newHeight = (int)Math.abs(p[0].getY() - p[1].getY()) + 8;
        this.setLocation(newX, newY);
        this.setSize(newWidth, newHeight);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        this.update();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        this.update();
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

}

