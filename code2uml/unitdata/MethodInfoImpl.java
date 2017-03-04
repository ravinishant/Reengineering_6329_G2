/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

import java.util.Iterator;
import java.util.List;
import net.sourceforge.code2uml.unitdata.AccessType;
import net.sourceforge.code2uml.unitdata.MethodInfo;

public class MethodInfoImpl
implements MethodInfo {
    private AccessType accessType;
    private boolean staticMethod;
    private boolean abstractMethod;
    private String returnTypeName;
    private String name;
    private List<String> arguments;

    @Override
    public AccessType getAccessType() {
        return this.accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    @Override
    public boolean isStatic() {
        return this.staticMethod;
    }

    public void setStatic(boolean staticMethod) {
        this.staticMethod = staticMethod;
    }

    @Override
    public boolean isAbstract() {
        return this.abstractMethod;
    }

    public void setAbstract(boolean abstractMethod) {
        this.abstractMethod = abstractMethod;
    }

    @Override
    public String getReturnTypeName() {
        return this.returnTypeName;
    }

    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<String> getArguments() {
        return this.arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.accessType.toString());
        if (this.staticMethod) {
            builder.append(" static ");
        } else {
            builder.append(" ");
        }
        builder.append(this.returnTypeName);
        builder.append(" ");
        builder.append(this.name);
        builder.append("(");
        if (this.arguments != null) {
            Iterator<String> it = this.arguments.iterator();
            if (it.hasNext()) {
                builder.append(it.next());
            }
            while (it.hasNext()) {
                builder.append(", ");
                builder.append(it.next());
            }
        }
        builder.append(")");
        return builder.toString().trim();
    }
}

