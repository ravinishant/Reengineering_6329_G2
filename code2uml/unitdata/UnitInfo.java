/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

import java.util.Collection;
import net.sourceforge.code2uml.unitdata.FieldInfo;
import net.sourceforge.code2uml.unitdata.MethodInfo;

public interface UnitInfo {
    public String getSimpleName();

    public String getName();

    public Collection<MethodInfo> getMethods();

    public Collection<FieldInfo> getFields();

    public Collection<String> getEnumValues();

    public Collection<String> getSupertypes();

    public boolean isPublic();

    public boolean isClass();

    public boolean isInterface();

    public boolean isEnum();

    public boolean isAbstract();

    public boolean isPartial();

    public void merge(UnitInfo var1) throws IllegalArgumentException, UnsupportedOperationException;
}

