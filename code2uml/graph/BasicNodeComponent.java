/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.util.TextSection;

public class BasicNodeComponent
extends NodeComponent {
    private static final int sectionCount = 4;
    private TextSection[] section = new TextSection[4];
    private FontMetrics fm;
    private int maxStrWidth = 0;

    public BasicNodeComponent(FontMetrics fm, Color backColor, boolean draggable) {
        super(backColor, draggable);
        this.fm = fm;
        for (int i = 0; i < 4; ++i) {
            this.section[i] = new TextSection(fm.getFont(), fm.getHeight(), fm.getAscent(), 5);
        }
        this.setSize(0, fm.getHeight());
    }

    protected void updateSize(String str) {
        int w = this.fm.stringWidth(str);
        if (w > this.maxStrWidth) {
            this.maxStrWidth = w;
        }
        this.setSize(this.maxStrWidth + 10, this.getHeight() + this.fm.getHeight());
    }

    @Override
    public void addToName(String str) {
        this.updateSize(str);
        this.section[0].addString(str);
    }

    @Override
    public void addToEnum(String str) {
        this.updateSize(str);
        this.section[1].addString(str);
    }

    @Override
    public void addToField(String str) {
        this.updateSize(str);
        this.section[2].addString(str);
    }

    @Override
    public void addToMethod(String str) {
        this.updateSize(str);
        this.section[3].addString(str);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.backColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        int h = this.fm.getHeight() / 2;
        for (int i = 0; i < 4; ++i) {
            if (i != 0 && this.section[i].getHeight() > 0) {
                g.drawLine(0, h, this.getWidth(), h);
            }
            if (i == 0) {
                this.section[i].drawCenteredAt(g, 0, h, this.getWidth(), this.fm);
            } else {
                this.section[i].drawAt(g, 0, h);
            }
            h += this.section[i].getHeight();
            if (i != 0) continue;
            h += this.fm.getHeight() / 2;
        }
        g.dispose();
    }
}

