/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BMPOutputStream {
    private DataOutputStream out;

    public BMPOutputStream(OutputStream os) {
        this.out = new DataOutputStream(os);
    }

    public void writeFileHeader(int size, int offset) throws IOException {
        this.writeShort(19778);
        this.writeInt(size);
        this.writeShort(0);
        this.writeShort(0);
        this.writeInt(offset);
    }

    public void writeBitmapInfoHeader(int size, int width, int height, short planes, short bpp, int compression, int dataSize, int ppmh, int ppmv, int colors, int importantColors) throws IOException {
        this.writeInt(size);
        this.writeInt(width);
        this.writeInt(height);
        this.writeShort(planes);
        this.writeShort(bpp);
        this.writeInt(compression);
        this.writeInt(dataSize);
        this.writeInt(ppmh);
        this.writeInt(ppmv);
        this.writeInt(colors);
        this.writeInt(importantColors);
    }

    public void writeImage(BufferedImage image) throws IOException {
        int[] tab = new int[3];
        int zerosPadding = 0;
        int bytesPerRow = 3 * image.getWidth();
        if (bytesPerRow % 4 != 0) {
            zerosPadding = 4 - bytesPerRow % 4;
        }
        for (int y = image.getHeight() - 1; y >= 0; --y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                tab = image.getRaster().getPixel(x, y, tab);
                this.writeByte(tab[2]);
                this.writeByte(tab[1]);
                this.writeByte(tab[0]);
            }
            for (int j = 0; j < zerosPadding; ++j) {
                this.writeByte(0);
            }
        }
    }

    private void writeByte(int v) throws IOException {
        this.out.writeByte(v);
    }

    private void writeInt(int v) throws IOException {
        this.out.writeInt(Integer.reverseBytes(v));
    }

    private void writeShort(int v) throws IOException {
        this.out.writeShort(Short.reverseBytes((short)v));
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void close() throws IOException {
        this.out.close();
    }
}

