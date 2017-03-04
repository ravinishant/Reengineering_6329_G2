/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors.java;

import java.io.DataInput;
import java.io.IOException;
import java.nio.CharBuffer;
import net.sourceforge.code2uml.inspectors.java.AttributeInfoReader;
import net.sourceforge.code2uml.inspectors.java.DescriptorReader;
import net.sourceforge.code2uml.inspectors.java.SignatureReader;
import net.sourceforge.code2uml.unitdata.AccessType;
import net.sourceforge.code2uml.unitdata.FieldInfo;
import net.sourceforge.code2uml.unitdata.FieldInfoImpl;

class FieldInfoReader {
    private static final int ACC_PUBLIC = 1;
    private static final int ACC_PRIVATE = 2;
    private static final int ACC_PROTECTED = 4;
    private static final int ACC_STATIC = 8;
    private static final int ACC_FINAL = 16;
    private static final int ACC_VOLATILE = 64;
    private static final int ACC_TRANSIENT = 128;
    private static final int ACC_SYNTHETIC = 4096;
    private static final int ACC_ENUM = 16384;
    private DescriptorReader descriptorReader = new DescriptorReader();
    private SignatureReader signatureReader = new SignatureReader();
    private AttributeInfoReader attributeReader = new AttributeInfoReader();

    public FieldInfo read(DataInput in, Object[] pool) throws IOException {
        int temp = in.readUnsignedShort();
        if ((temp & 4096) != 0) {
            this.ignoreField(in);
            return null;
        }
        FieldInfoImpl field = new FieldInfoImpl();
        if ((temp & 16384) != 0) {
            temp = in.readUnsignedShort();
            field.setTypeName("enum");
            field.setName((String)pool[temp]);
            in.readUnsignedShort();
            this.attributeReader.ignoreAttributes(in);
        } else {
            if ((temp & 1) != 0) {
                field.setAccessType(AccessType.PUBLIC);
            } else if ((temp & 2) != 0) {
                field.setAccessType(AccessType.PRIVATE);
            } else if ((temp & 4) != 0) {
                field.setAccessType(AccessType.PROTECTED);
            } else {
                field.setAccessType(AccessType.PACKAGE);
            }
            field.setStatic((temp & 8) != 0);
            field.setIsFinal((temp & 16) != 0);
            temp = in.readUnsignedShort();
            field.setName((String)pool[temp]);
            temp = in.readUnsignedShort();
            field.setTypeName(this.descriptorReader.readFieldDescriptor(CharBuffer.wrap((CharSequence)pool[temp])));
            int signatureIdx = this.attributeReader.readSignatureIndex(in, pool);
            if (signatureIdx != -1) {
                CharBuffer charBuffer = CharBuffer.wrap((CharSequence)pool[signatureIdx]);
                field.setTypeName(this.signatureReader.readFieldTypeSignature(charBuffer));
            }
        }
        return field;
    }

    private void ignoreField(DataInput in) throws IOException {
        in.readInt();
        this.attributeReader.ignoreAttributes(in);
    }
}

