/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseDragListener
implements MouseListener,
MouseMotionListener {
    private Component c;
    private int clickX;
    private int clickY;

    public MouseDragListener(Component c) {
        this.c = c;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!this.c.isEnabled()) {
            return;
        }
        int oldX = this.c.getX();
        int oldY = this.c.getY();
        this.c.setLocation(Math.max(0, Math.min(this.c.getParent().getWidth() - this.c.getWidth(), this.c.getX() + e.getX() - this.clickX)), Math.max(0, Math.min(this.c.getParent().getHeight() - this.c.getHeight(), this.c.getY() + e.getY() - this.clickY)));
        this.c.getParent().setComponentZOrder(this.c, 0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.c.removeMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1 && this.c.isEnabled()) {
            this.clickX = e.getX();
            this.clickY = e.getY();
            this.c.addMouseMotionListener(this);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}

