/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

import net.sourceforge.code2uml.unitdata.AccessType;

public interface FieldInfo {
    public AccessType getAccessType();

    public String getTypeName();

    public String getName();

    public boolean isStatic();

    public boolean isFinal();
}

