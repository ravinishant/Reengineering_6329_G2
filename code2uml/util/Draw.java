/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Draw {
    private static final int arrowheadLength = 10;
    private static final int arrowheadWidth = 4;
    private static final int segmentLength = 8;
    private static final int diamondDiagonal = 5;

    public static void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2d = (Graphics2D)g;
        int dx = x1 - x2;
        int dy = y1 - y2;
        int length = (int)Math.sqrt(dx * dx + dy * dy);
        Draw.rotate(g2d, x1, y1, x2, y2);
        g2d.setColor(Color.BLACK);
        g2d.drawLine(x1, y1, x1, y1 - length + 10);
        Draw.drawArrowhead(g2d, x1, y1 - length);
    }

    public static void drawDashedArrow(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2d = (Graphics2D)g;
        int dx = x1 - x2;
        int dy = y1 - y2;
        int length = (int)Math.sqrt(dx * dx + dy * dy);
        Draw.rotate(g2d, x1, y1, x2, y2);
        g2d.setColor(Color.BLACK);
        for (int y = y1; y > y1 - length + 10 + 8; y -= 16) {
            g.drawLine(x1, y, x1, y - 8);
        }
        Draw.drawArrowhead(g2d, x1, y1 - length);
    }

    public static void drawDiamondEndLine(Graphics g, int x1, int y1, int x2, int y2, boolean filled) {
        Graphics2D g2d = (Graphics2D)g;
        int dx = x1 - x2;
        int dy = y1 - y2;
        int length = (int)Math.sqrt(dx * dx + dy * dy);
        Draw.rotate(g2d, x1, y1, x2, y2);
        g2d.setColor(Color.BLACK);
        int diamondBaseY = y1 - length + 20;
        g2d.drawLine(x1, y1, x1, diamondBaseY);
        int[] xPoints = new int[]{x1, x1 - 5, x1, x1 + 5};
        int[] yPoints = new int[]{diamondBaseY, diamondBaseY - 10, y1 - length, diamondBaseY - 10};
        g2d.drawPolygon(xPoints, yPoints, 4);
        if (filled) {
            g2d.fillPolygon(xPoints, yPoints, 4);
        }
    }

    private static void drawArrowhead(Graphics g, int x, int y) {
        g.drawLine(x, y, x - 4, y + 10);
        g.drawLine(x - 4, y + 10, x + 4, y + 10);
        g.drawLine(x + 4, y + 10, x, y);
    }

    private static void rotate(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        double beta;
        double alpha;
        double dx = Math.abs(x1 - x2);
        double dy = Math.abs(y1 - y2);
        if (dx == 0.0 || dy == 0.0) {
            alpha = 0.0;
            beta = 0.0;
        } else {
            alpha = Math.atan(dy / dx);
            beta = Math.atan(dx / dy);
        }
        double rotation = 0.0;
        rotation = x2 < x1 ? (y2 < y1 ? 4.71238898038469 + alpha : 3.141592653589793 + beta) : (y2 < y1 ? beta : 1.5707963267948966 + alpha);
        int length = (int)Math.sqrt(dx * dx + dy * dy);
        g2d.rotate(rotation, x1, y1);
    }
}

