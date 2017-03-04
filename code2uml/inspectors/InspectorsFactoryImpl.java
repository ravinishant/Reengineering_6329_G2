/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors;

import java.util.HashMap;
import java.util.Map;
import net.sourceforge.code2uml.inspectors.FileInspector;
import net.sourceforge.code2uml.inspectors.InspectorsFactory;
import net.sourceforge.code2uml.inspectors.NullFileInspector;
import net.sourceforge.code2uml.inspectors.java.ClassFileInspector;
import net.sourceforge.code2uml.inspectors.java.JarFileInspector;

public class InspectorsFactoryImpl
implements InspectorsFactory {
    Map<String, FileInspector> inspectors = new HashMap<String, FileInspector>();
    FileInspector nullInspector = new NullFileInspector();

    public InspectorsFactoryImpl() {
        this.inspectors.put("class", new ClassFileInspector());
        this.inspectors.put("jar", new JarFileInspector());
    }

    @Override
    public FileInspector getInspector(String fileExtension) {
        FileInspector inspector = this.inspectors.get(fileExtension);
        return inspector != null ? inspector : this.nullInspector;
    }
}

