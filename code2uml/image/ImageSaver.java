/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.image;

import java.awt.Component;
import java.io.IOException;
import java.util.Observer;

public interface ImageSaver {
    public void saveImage(Component var1, String var2) throws IOException;

    public void addObserver(Observer var1);
}

