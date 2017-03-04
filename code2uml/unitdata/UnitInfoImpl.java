/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

import java.util.Collection;
import java.util.HashSet;
import net.sourceforge.code2uml.unitdata.FieldInfo;
import net.sourceforge.code2uml.unitdata.MethodInfo;
import net.sourceforge.code2uml.unitdata.UnitInfo;

public class UnitInfoImpl
implements UnitInfo {
    private String simpleName;
    private String name;
    private Collection<MethodInfo> methods = new HashSet<MethodInfo>();
    private Collection<FieldInfo> fields = new HashSet<FieldInfo>();
    private Collection<String> enumValues = new HashSet<String>();
    private Collection<String> superTypes = new HashSet<String>();
    private boolean isPublic;
    private boolean isClass;
    private boolean isInterface;
    private boolean isEnum;
    private boolean isAbstract;
    private boolean isPartial;

    @Override
    public String getSimpleName() {
        return this.simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<MethodInfo> getMethods() {
        return this.methods;
    }

    public void addMethod(MethodInfo method) {
        this.methods.add(method);
    }

    @Override
    public Collection<FieldInfo> getFields() {
        return this.fields;
    }

    public void addField(FieldInfo field) {
        this.fields.add(field);
    }

    @Override
    public Collection<String> getEnumValues() {
        return this.enumValues;
    }

    public void addEnumValue(String enumValue) {
        this.enumValues.add(enumValue);
    }

    @Override
    public Collection<String> getSupertypes() {
        return this.superTypes;
    }

    public void addSupertype(String supertypeName) {
        this.superTypes.add(supertypeName);
    }

    @Override
    public boolean isPublic() {
        return this.isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public boolean isClass() {
        return this.isClass;
    }

    public void setIsClass(boolean isClass) {
        this.isClass = isClass;
    }

    @Override
    public boolean isInterface() {
        return this.isInterface;
    }

    public void setIsInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    @Override
    public boolean isEnum() {
        return this.isEnum;
    }

    public void setIsEnum(boolean isEnum) {
        this.isEnum = isEnum;
    }

    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    public void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    @Override
    public boolean isPartial() {
        return this.isPartial;
    }

    public void setIsPartial(boolean isPartial) {
        this.isPartial = isPartial;
    }

    @Override
    public void merge(UnitInfo arg) throws IllegalArgumentException, UnsupportedOperationException {
        if (!this.isPartial()) {
            throw new UnsupportedOperationException();
        }
        if (!arg.isPartial() || !this.name.equals(arg.getName())) {
            throw new IllegalArgumentException();
        }
        this.methods.addAll(arg.getMethods());
        this.fields.addAll(arg.getFields());
        this.enumValues.addAll(arg.getEnumValues());
    }
}

