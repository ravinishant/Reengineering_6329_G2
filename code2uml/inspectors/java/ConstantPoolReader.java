/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors.java;

import java.io.DataInput;
import java.io.IOException;

public class ConstantPoolReader {
    private static final int CONSTANT_Class = 7;
    private static final int CONSTANT_Fieldref = 9;
    private static final int CONSTANT_Methodref = 10;
    private static final int CONSTANT_InterfaceMethodref = 11;
    private static final int CONSTANT_String = 8;
    private static final int CONSTANT_Integer = 3;
    private static final int CONSTANT_Float = 4;
    private static final int CONSTANT_Long = 5;
    private static final int CONSTANT_Double = 6;
    private static final int CONSTANT_NameAndType = 12;
    private static final int CONSTANT_Utf8 = 1;

    public Object[] read(DataInput in, int constantPoolCount) throws IOException {
        Object[] result = new Object[constantPoolCount];
        block4 : for (int i = 1; i < constantPoolCount; ++i) {
            int tag = in.readUnsignedByte();
            switch (tag) {
                case 1: {
                    result[i] = in.readUTF();
                    continue block4;
                }
                case 7: {
                    result[i] = in.readUnsignedShort();
                    continue block4;
                }
                default: {
                    if (!this.readTrash(in, tag)) continue block4;
                    ++i;
                }
            }
        }
        return result;
    }

    private boolean readTrash(DataInput in, int tag) throws IOException {
        switch (tag) {
            case 3: 
            case 4: 
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                in.readInt();
                break;
            }
            case 7: 
            case 8: {
                in.readShort();
                break;
            }
            case 5: 
            case 6: {
                in.readLong();
                return true;
            }
            case 1: {
                in.readUTF();
                break;
            }
        }
        return false;
    }
}

