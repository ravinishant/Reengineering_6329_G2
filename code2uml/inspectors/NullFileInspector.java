/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors;

import java.util.Collection;
import net.sourceforge.code2uml.inspectors.FileInspector;
import net.sourceforge.code2uml.unitdata.UnitInfo;

public class NullFileInspector
implements FileInspector {
    @Override
    public Collection<UnitInfo> inspect(String filePath) {
        return null;
    }

    @Override
    public Collection<String> glance(String filePath) {
        return null;
    }

    @Override
    public Collection<UnitInfo> inspect(String filePath, Collection<String> namesFilter) {
        return null;
    }
}

