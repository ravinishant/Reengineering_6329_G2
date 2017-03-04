/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors;

import java.util.Collection;
import net.sourceforge.code2uml.unitdata.UnitInfo;

public interface FileInspector {
    public Collection<UnitInfo> inspect(String var1);

    public Collection<UnitInfo> inspect(String var1, Collection<String> var2);

    public Collection<String> glance(String var1);
}

