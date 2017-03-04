/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.image;

import java.util.HashMap;
import java.util.Map;
import net.sourceforge.code2uml.image.BMPImageSaver;
import net.sourceforge.code2uml.image.ImageSaver;
import net.sourceforge.code2uml.image.ImageSaverFactory;

public class ImageSaverFactoryImpl
implements ImageSaverFactory {
    private Map<String, ImageSaver> savers = new HashMap<String, ImageSaver>();

    public ImageSaverFactoryImpl() {
        this.savers.put("bmp", new BMPImageSaver());
    }

    @Override
    public ImageSaver getImageWorker(String fileExtension) {
        return this.savers.get(fileExtension);
    }
}

