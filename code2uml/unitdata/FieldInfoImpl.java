/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

import net.sourceforge.code2uml.unitdata.AccessType;
import net.sourceforge.code2uml.unitdata.FieldInfo;

public class FieldInfoImpl
implements FieldInfo {
    private AccessType accessType;
    private String typeName;
    private String name;
    private boolean staticField;
    private boolean finalField;

    @Override
    public AccessType getAccessType() {
        return this.accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isStatic() {
        return this.staticField;
    }

    public void setStatic(boolean staticField) {
        this.staticField = staticField;
    }

    @Override
    public boolean isFinal() {
        return this.finalField;
    }

    public void setIsFinal(boolean isFinal) {
        this.finalField = isFinal;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.accessType.toString());
        if (this.staticField) {
            builder.append(" static ");
        } else {
            builder.append(" ");
        }
        builder.append(this.typeName);
        builder.append(" ");
        builder.append(this.name);
        return builder.toString().trim();
    }
}

