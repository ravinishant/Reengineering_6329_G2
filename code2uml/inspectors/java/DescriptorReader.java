/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors.java;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class DescriptorReader {
    public String readFieldDescriptor(CharBuffer buffer) {
        return this.readFieldType(buffer);
    }

    protected String readComponentType(CharBuffer buffer) {
        return this.readFieldType(buffer);
    }

    protected String readBaseType(CharBuffer buffer) {
        char c = buffer.get();
        switch (c) {
            case 'B': {
                return "byte";
            }
            case 'C': {
                return "char";
            }
            case 'D': {
                return "double";
            }
            case 'F': {
                return "float";
            }
            case 'I': {
                return "int";
            }
            case 'J': {
                return "long";
            }
            case 'S': {
                return "String";
            }
            case 'Z': {
                return "boolean";
            }
        }
        return "?";
    }

    protected String readArrayType(CharBuffer buffer) {
        if (buffer.get() != '[') {
            return "?";
        }
        return this.readComponentType(buffer) + "[]";
    }

    protected String readObjectType(CharBuffer buffer) {
        if (buffer.get() != 'L') {
            return "?";
        }
        int i = 0;
        while (buffer.charAt(i) != ';') {
            ++i;
        }
        char[] tab = new char[i];
        buffer.get(tab);
        buffer.get();
        String str = new String(tab);
        return str.replace('/', '.');
    }

    protected String readFieldType(CharBuffer buffer) {
        char c = buffer.charAt(0);
        switch (c) {
            case 'L': {
                return this.readObjectType(buffer);
            }
            case '[': {
                return this.readArrayType(buffer);
            }
        }
        return this.readBaseType(buffer);
    }

    protected String readVoidDescriptor(CharBuffer buffer) {
        buffer.get();
        return "void";
    }

    protected String readReturnDescriptor(CharBuffer buffer) {
        char c = buffer.charAt(0);
        switch (c) {
            case 'V': {
                return this.readVoidDescriptor(buffer);
            }
        }
        return this.readFieldType(buffer);
    }

    protected String readParameterDescriptor(CharBuffer buffer) {
        return this.readFieldType(buffer);
    }

    public List<String> readMethodDescriptor(CharBuffer buffer) {
        ArrayList<String> result = new ArrayList<String>();
        if (buffer.get() != '(') {
            return result;
        }
        int i = 0;
        while (buffer.charAt(i) != ')') {
            ++i;
        }
        char[] tab = new char[i];
        buffer.get(tab);
        buffer.get();
        result.add(this.readReturnDescriptor(buffer));
        CharBuffer parameters = CharBuffer.wrap(tab);
        while (parameters.hasRemaining()) {
            result.add(this.readParameterDescriptor(parameters));
        }
        return result;
    }
}

