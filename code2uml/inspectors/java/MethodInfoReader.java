/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors.java;

import java.io.DataInput;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import net.sourceforge.code2uml.inspectors.java.AttributeInfoReader;
import net.sourceforge.code2uml.inspectors.java.DescriptorReader;
import net.sourceforge.code2uml.inspectors.java.SignatureReader;
import net.sourceforge.code2uml.unitdata.AccessType;
import net.sourceforge.code2uml.unitdata.MethodInfo;
import net.sourceforge.code2uml.unitdata.MethodInfoImpl;

class MethodInfoReader {
    private static final int ACC_PUBLIC = 1;
    private static final int ACC_PRIVATE = 2;
    private static final int ACC_PROTECTED = 4;
    private static final int ACC_STATIC = 8;
    private static final int ACC_FINAL = 16;
    private static final int ACC_SYNCHRONIZED = 32;
    private static final int ACC_BRIDGE = 64;
    private static final int ACC_VARARGS = 128;
    private static final int ACC_NATIVE = 256;
    private static final int ACC_ABSTRACT = 1024;
    private static final int ACC_STRICT = 2048;
    private static final int ACC_SYNTHETIC = 4096;
    private DescriptorReader descriptorReader = new DescriptorReader();
    private SignatureReader signatureReader = new SignatureReader();
    private AttributeInfoReader attributeReader = new AttributeInfoReader();

    public MethodInfo readMethod(DataInput in, Object[] pool) throws IOException {
        int temp = in.readUnsignedShort();
        if ((temp & 4096) != 0) {
            this.ignoreMethod(in);
            return null;
        }
        MethodInfoImpl method = new MethodInfoImpl();
        if ((temp & 1) != 0) {
            method.setAccessType(AccessType.PUBLIC);
        } else if ((temp & 2) != 0) {
            method.setAccessType(AccessType.PRIVATE);
        } else if ((temp & 4) != 0) {
            method.setAccessType(AccessType.PROTECTED);
        } else {
            method.setAccessType(AccessType.PACKAGE);
        }
        method.setStatic((temp & 8) != 0);
        method.setAbstract((temp & 1024) != 0);
        temp = in.readUnsignedShort();
        method.setName((String)pool[temp]);
        temp = in.readUnsignedShort();
        String methodDescriptor = (String)pool[temp];
        List<String> methodInfo = this.descriptorReader.readMethodDescriptor(CharBuffer.wrap(methodDescriptor));
        method.setReturnTypeName(methodInfo.remove(0));
        method.setArguments(methodInfo);
        int signatureIdx = this.attributeReader.readSignatureIndex(in, pool);
        if (signatureIdx != -1) {
            CharBuffer charBuffer = CharBuffer.wrap((CharSequence)pool[signatureIdx]);
            List<String> list = this.signatureReader.readMethodTypeSignature(charBuffer);
            method.setReturnTypeName(list.remove(0));
            method.setArguments(list);
        }
        return method.getName().contains("<") ? null : method;
    }

    private void ignoreMethod(DataInput in) throws IOException {
        in.readInt();
        this.attributeReader.ignoreAttributes(in);
    }
}

