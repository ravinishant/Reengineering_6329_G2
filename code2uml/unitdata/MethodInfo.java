/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

import java.util.List;
import net.sourceforge.code2uml.unitdata.AccessType;

public interface MethodInfo {
    public AccessType getAccessType();

    public boolean isStatic();

    public boolean isAbstract();

    public String getReturnTypeName();

    public String getName();

    public List<String> getArguments();
}

