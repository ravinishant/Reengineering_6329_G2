/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors.java;

import java.io.DataInput;
import java.io.IOException;
import net.sourceforge.code2uml.inspectors.java.ConstantPoolReader;
import net.sourceforge.code2uml.inspectors.java.FieldInfoReader;
import net.sourceforge.code2uml.inspectors.java.MethodInfoReader;
import net.sourceforge.code2uml.unitdata.FieldInfo;
import net.sourceforge.code2uml.unitdata.MethodInfo;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.unitdata.UnitInfoImpl;

class ClassFileReader {
    private static final int ACC_PUBLIC = 1;
    private static final int ACC_FINAL = 16;
    private static final int ACC_SUPER = 32;
    private static final int ACC_INTERFACE = 512;
    private static final int ACC_ABSTRACT = 1024;
    private static final int ACC_SYNTHETIC = 4096;
    private static final int ACC_ANNOTATION = 8192;
    private static final int ACC_ENUM = 16384;
    private FieldInfoReader fieldReader = new FieldInfoReader();
    private MethodInfoReader methodReader = new MethodInfoReader();

    public UnitInfo read(DataInput in) throws IOException {
        int i;
        UnitInfoImpl unit = new UnitInfoImpl();
        if (in.readInt() != -889275714) {
            return null;
        }
        in.readInt();
        int data = in.readUnsignedShort();
        Object[] pool = new ConstantPoolReader().read(in, data);
        data = in.readUnsignedShort();
        if ((data & 512) != 0) {
            unit.setIsInterface(true);
        } else if ((data & 16384) != 0) {
            unit.setIsEnum(true);
        } else {
            unit.setIsClass(true);
            if ((data & 1024) != 0) {
                unit.setIsAbstract(true);
            }
        }
        if ((data & 1) != 0) {
            unit.setIsPublic(true);
        }
        data = in.readUnsignedShort();
        String name = (String)pool[(Integer)pool[data]];
        unit.setName(name.replace('/', '.'));
        unit.setSimpleName(unit.getName().substring(unit.getName().lastIndexOf(46) + 1));
        data = in.readUnsignedShort();
        if (data > 0) {
            name = (String)pool[(Integer)pool[data]];
            unit.addSupertype(name.replace('/', '.'));
        }
        data = in.readUnsignedShort();
        for (i = 0; i < data; ++i) {
            int idx = in.readUnsignedShort();
            name = (String)pool[(Integer)pool[idx]];
            unit.addSupertype(name.replace('/', '.'));
        }
        data = in.readUnsignedShort();
        for (i = 0; i < data; ++i) {
            FieldInfo field = this.fieldReader.read(in, pool);
            if (field == null) continue;
            if (field.getTypeName().equals("enum")) {
                unit.addEnumValue(field.getName());
                continue;
            }
            unit.addField(field);
        }
        data = in.readUnsignedShort();
        for (i = 0; i < data; ++i) {
            MethodInfo method = this.methodReader.readMethod(in, pool);
            if (method == null) continue;
            unit.addMethod(method);
        }
        return unit;
    }

    public String readUnitName(DataInput in) throws IOException {
        ConstantPoolReader reader = new ConstantPoolReader();
        if (in.readInt() != -889275714) {
            return null;
        }
        in.readInt();
        int data = in.readUnsignedShort();
        Object[] pool = new ConstantPoolReader().read(in, data);
        in.readUnsignedShort();
        data = in.readUnsignedShort();
        return ((String)pool[(Integer)pool[data]]).replace('/', '.');
    }
}

