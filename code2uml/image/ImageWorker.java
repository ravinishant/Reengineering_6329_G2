/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.image;

import java.awt.Component;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingWorker;
import net.sourceforge.code2uml.image.ImageSaver;
import net.sourceforge.code2uml.image.ImageSaverFactory;
import net.sourceforge.code2uml.image.ImageSaverFactoryImpl;

public class ImageWorker
extends SwingWorker
implements Observer {
    private ImageSaverFactory factory = new ImageSaverFactoryImpl();
    private Component comp;
    private String filePath;

    public ImageWorker(Component comp, String filePath) {
        this.comp = comp;
        this.filePath = filePath;
    }

    protected Object doInBackground() throws Exception {
        ImageSaver saver = this.factory.getImageWorker(this.filePath.substring(this.filePath.lastIndexOf(46) + 1));
        saver.addObserver(this);
        try {
            saver.saveImage(this.comp, this.filePath);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ImageSaver) {
            this.firePropertyChange("progress", null, arg);
        }
    }
}

