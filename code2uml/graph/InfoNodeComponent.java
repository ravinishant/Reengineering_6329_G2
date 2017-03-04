/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import net.sourceforge.code2uml.graph.BasicNodeComponent;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.util.MouseDragListener;
import net.sourceforge.code2uml.util.TextSection;

public class InfoNodeComponent
extends NodeComponent {
    private FontMetrics fm;
    private BasicNodeComponent info;
    private TextSection nameSection;
    private int maxStrWidth = 0;

    public InfoNodeComponent(FontMetrics fm, Color backColor, boolean draggable) {
        super(backColor, draggable);
        this.fm = fm;
        this.info = new BasicNodeComponent(fm, backColor, false);
        this.nameSection = new TextSection(fm.getFont(), fm.getHeight(), fm.getAscent(), 5);
        this.setSize(0, fm.getHeight());
        this.info.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 3 && InfoNodeComponent.this.info.isEnabled()) {
                    Container parent = InfoNodeComponent.this.info.getParent();
                    parent.remove(InfoNodeComponent.this.info);
                    parent.repaint();
                }
            }
        });
        this.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 3 && InfoNodeComponent.this.isEnabled()) {
                    Container parent = InfoNodeComponent.this.getParent();
                    InfoNodeComponent.this.info.setLocation(Math.max(0, Math.min(parent.getWidth() - InfoNodeComponent.this.info.getWidth(), InfoNodeComponent.this.getX() + (InfoNodeComponent.this.getWidth() - InfoNodeComponent.this.info.getWidth()) / 2)), Math.max(0, Math.min(parent.getHeight() - InfoNodeComponent.this.info.getHeight(), InfoNodeComponent.this.getY() + (InfoNodeComponent.this.getHeight() - InfoNodeComponent.this.info.getHeight()) / 2)));
                    parent.add((Component)InfoNodeComponent.this.info, 0);
                    parent.repaint();
                    Dimension dim = new Dimension(Math.max(InfoNodeComponent.this.info.getWidth(), parent.getWidth()), Math.max(InfoNodeComponent.this.info.getHeight(), parent.getHeight()));
                    if (!parent.getSize().equals(dim)) {
                        parent.setSize(dim);
                    }
                    for (MouseMotionListener m : InfoNodeComponent.this.getMouseMotionListeners()) {
                        if (!(m instanceof MouseDragListener)) continue;
                        InfoNodeComponent.this.removeMouseMotionListener(m);
                    }
                }
            }
        });
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
        this.nameSection.addString(str);
        this.info.addToName(str);
    }

    @Override
    public void addToEnum(String str) {
        this.info.addToEnum(str);
    }

    @Override
    public void addToField(String str) {
        this.info.addToField(str);
    }

    @Override
    public void addToMethod(String str) {
        this.info.addToMethod(str);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.backColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        this.nameSection.drawCenteredAt(g, 0, this.fm.getHeight() / 2, this.getWidth(), this.fm);
        g.dispose();
    }

}

