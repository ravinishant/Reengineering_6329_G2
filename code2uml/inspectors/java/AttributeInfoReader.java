/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors.java;

import java.io.DataInput;
import java.io.IOException;

public class AttributeInfoReader {
    public void ignoreAttributes(DataInput in) throws IOException {
        int attributesCount = in.readUnsignedShort();
        for (int i = 0; i < attributesCount; ++i) {
            in.readUnsignedShort();
            int max = in.readInt();
            for (int j = 0; j < max; ++j) {
                in.readByte();
            }
        }
    }

    public int readSignatureIndex(DataInput in, Object[] pool) throws IOException {
        int signatureIndex = -1;
        int attributesCount = in.readUnsignedShort();
        for (int i = 0; i < attributesCount; ++i) {
            boolean signature;
            int idx = in.readUnsignedShort();
            boolean bl = signature = pool[idx] != null && pool[idx] instanceof String && pool[idx].equals("Signature");
            if (signature) {
                in.readInt();
                signatureIndex = in.readUnsignedShort();
                continue;
            }
            int max = in.readInt();
            for (int j = 0; j < max; ++j) {
                in.readByte();
            }
        }
        return signatureIndex;
    }
}

