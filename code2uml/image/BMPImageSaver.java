/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.image;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Observable;
import net.sourceforge.code2uml.image.BMPOutputStream;
import net.sourceforge.code2uml.image.ImageSaver;
import net.sourceforge.code2uml.util.ProgressData;

public class BMPImageSaver
extends Observable
implements ImageSaver {
    private static final int imagePixels = 10000;

    private int calculateBMPSize(Component comp) {
        int bytesPerRow = comp.getWidth() * 3;
        int zerosPadding = 0;
        if (bytesPerRow % 4 != 0) {
            zerosPadding = 4 - bytesPerRow % 4;
        }
        return 54 + (bytesPerRow += zerosPadding) * comp.getHeight();
    }

    @Override
    public void saveImage(Component comp, String filePath) throws IOException {
        BMPOutputStream out = new BMPOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
        int size = this.calculateBMPSize(comp);
        out.writeFileHeader(size, 54);
        out.writeBitmapInfoHeader(40, comp.getWidth(), comp.getHeight(), 1, 24, 0, 0, 0, 0, 0, 0);
        int imageHeight = Math.min(Math.max(10000 / comp.getWidth(), 1), comp.getHeight());
        int numberOfParts = (int)Math.ceil((double)comp.getHeight() / (double)imageHeight);
        int partsSavedSoFar = 0;
        int lastImageHeight = comp.getHeight() % imageHeight;
        BufferedImage image = new BufferedImage(comp.getWidth(), imageHeight, 5);
        BufferedImage lastImage = lastImageHeight == 0 ? image : new BufferedImage(comp.getWidth(), lastImageHeight, 5);
        for (int i = comp.getHeight() - image.getHeight(); i >= lastImage.getHeight(); i -= image.getHeight()) {
            Graphics g = image.getGraphics();
            g.translate(0, - i);
            comp.paint(g);
            out.writeImage(image);
            g.dispose();
            double progress = 100.0 * (double)(++partsSavedSoFar) / (double)numberOfParts;
            this.setChanged();
            this.notifyObservers(new ProgressData(progress));
            this.clearChanged();
        }
        Graphics2D g = lastImage.createGraphics();
        comp.paint(g);
        out.writeImage(lastImage);
        this.setChanged();
        this.notifyObservers(new ProgressData(100.0));
        this.clearChanged();
        g.dispose();
        out.close();
    }
}

