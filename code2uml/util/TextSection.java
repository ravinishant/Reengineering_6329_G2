/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class TextSection {
    private Font font;
    private int lineHeight;
    private int ascent;
    private int padding;
    private Collection<String> strings = new LinkedList<String>();

    public TextSection(Font font, int lineHeight, int ascent, int padding) {
        this.font = font;
        this.lineHeight = lineHeight;
        this.ascent = ascent;
        this.padding = padding;
    }

    public int getLineHeight() {
        return this.lineHeight;
    }

    public int getHeight() {
        return this.lineHeight * this.strings.size();
    }

    public void addString(String str) {
        this.strings.add(str);
    }

    public void drawAt(Graphics g, int x, int y) {
        g.setFont(this.font);
        g.setColor(Color.BLACK);
        int count = 0;
        Iterator<String> it = this.strings.iterator();
        while (it.hasNext()) {
            g.drawString(it.next(), x + this.padding, y + count * this.lineHeight + this.ascent);
            ++count;
        }
    }

    public void drawCenteredAt(Graphics g, int x, int y, int width, FontMetrics fm) {
        g.setFont(this.font);
        g.setColor(Color.BLACK);
        int count = 0;
        for (String str : this.strings) {
            g.drawString(str, (width - fm.stringWidth(str)) / 2, y + count * this.lineHeight + this.ascent);
            ++count;
        }
    }
}

