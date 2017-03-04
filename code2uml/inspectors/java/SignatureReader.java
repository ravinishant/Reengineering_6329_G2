/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors.java;

import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import net.sourceforge.code2uml.inspectors.java.DescriptorReader;

public class SignatureReader {
    private DescriptorReader descriptorReader = new DescriptorReader();
    private static final Set<Character> identifierTerminatingChar = new HashSet<Character>();

    public String readFieldTypeSignature(CharBuffer buffer) {
        switch (buffer.charAt(0)) {
            case 'L': {
                return this.readClassTypeSignature(buffer);
            }
            case '[': {
                return this.readArrayTypeSignature(buffer);
            }
            case 'T': {
                return this.readTypeVariableSignature(buffer);
            }
        }
        return "?";
    }

    protected String readTypeSignature(CharBuffer buffer) {
        char c = buffer.charAt(0);
        if (c == 'L' || c == '[' || c == 'T') {
            return this.readFieldTypeSignature(buffer);
        }
        return this.descriptorReader.readBaseType(buffer);
    }

    protected String readArrayTypeSignature(CharBuffer buffer) {
        if (buffer.get() != '[') {
            return "?";
        }
        return this.readTypeSignature(buffer) + "[]";
    }

    protected String readWildcardIndicator(CharBuffer buffer) {
        char c = buffer.get();
        if (c == '+') {
            return "? extends ";
        }
        if (c == '-') {
            return "? super ";
        }
        return "?";
    }

    protected String readTypeArgument(CharBuffer buffer) {
        String result = "";
        char c = buffer.charAt(0);
        if (c == '*') {
            buffer.get();
            return "?";
        }
        if (c == '+' || c == '-') {
            result = this.readWildcardIndicator(buffer);
        }
        return result + this.readFieldTypeSignature(buffer);
    }

    protected String readTypeArguments(CharBuffer buffer) {
        if (buffer.get() != '<') {
            return "?";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('<');
        builder.append(this.readTypeArgument(buffer));
        while (buffer.charAt(0) != '>') {
            builder.append(", ");
            builder.append(this.readTypeArgument(buffer));
        }
        buffer.get();
        builder.append('>');
        return builder.toString();
    }

    protected String readIdentifier(CharBuffer buffer) {
        int i = 0;
        while (!identifierTerminatingChar.contains(Character.valueOf(buffer.charAt(i)))) {
            ++i;
        }
        char[] tab = new char[i];
        buffer.get(tab);
        return new String(tab);
    }

    protected String readTypeVariableSignature(CharBuffer buffer) {
        if (buffer.get() != 'T') {
            return "?";
        }
        String result = this.readIdentifier(buffer);
        buffer.get();
        return result;
    }

    protected String readSimpleClassTypeSignature(CharBuffer buffer) {
        String result = this.readIdentifier(buffer);
        if (buffer.charAt(0) == '<') {
            result = result + this.readTypeArguments(buffer);
        }
        return result;
    }

    protected String readClassTypeSignatureSuffix(CharBuffer buffer) {
        if (buffer.get() != '.') {
            return "?";
        }
        return "$" + this.readSimpleClassTypeSignature(buffer);
    }

    protected String readClassTypeSignature(CharBuffer buffer) {
        if (buffer.get() != 'L') {
            return "?";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(this.readIdentifier(buffer));
        while (buffer.charAt(0) == '/') {
            buffer.get();
            builder.append('.');
            builder.append(this.readIdentifier(buffer));
        }
        switch (buffer.charAt(0)) {
            case '<': {
                builder.append(this.readTypeArguments(buffer));
                while (buffer.charAt(0) == '.') {
                    builder.append(this.readClassTypeSignatureSuffix(buffer));
                }
                buffer.get();
                return builder.toString();
            }
            case '.': {
                do {
                    builder.append(this.readClassTypeSignatureSuffix(buffer));
                } while (buffer.charAt(0) == '.');
                buffer.get();
                return builder.toString();
            }
            case ';': {
                buffer.get();
                return builder.toString();
            }
        }
        return "?";
    }

    protected String readSuperinterfaceSignature(CharBuffer buffer) {
        return this.readClassTypeSignature(buffer);
    }

    protected String readSuperclassSignature(CharBuffer buffer) {
        return this.readClassTypeSignature(buffer);
    }

    protected String readInterfaceBound(CharBuffer buffer) {
        if (buffer.get() != ':') {
            return "?";
        }
        return this.readFieldTypeSignature(buffer);
    }

    protected String readClassBound(CharBuffer buffer) {
        if (buffer.get() != ':') {
            return "?";
        }
        return this.readFieldTypeSignature(buffer);
    }

    protected String readFormalTypeParameter(CharBuffer buffer) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.readIdentifier(buffer));
        builder.append(' ');
        builder.append(this.readClassBound(buffer));
        while (buffer.charAt(0) == ':') {
            builder.append(' ');
            builder.append(this.readInterfaceBound(buffer));
        }
        return builder.toString();
    }

    protected String readFormalTypeParameters(CharBuffer buffer) {
        if (buffer.get() != '<') {
            return "?";
        }
        StringBuilder builder = new StringBuilder(60);
        builder.append(this.readFormalTypeParameter(buffer));
        while (buffer.charAt(0) != '>') {
            builder.append(", ");
            builder.append(this.readFormalTypeParameter(buffer));
        }
        buffer.get();
        builder.append('>');
        return builder.toString();
    }

    public String readClassSignature(CharBuffer buffer) {
        StringBuilder builder = new StringBuilder();
        if (buffer.charAt(0) == '<') {
            builder.append(this.readFormalTypeParameters(buffer));
            builder.append(' ');
        }
        builder.append(this.readSuperclassSignature(buffer));
        while (buffer.charAt(0) == 'L') {
            builder.append(' ');
            builder.append(this.readSuperinterfaceSignature(buffer));
        }
        return builder.toString();
    }

    protected String readReturnType(CharBuffer buffer) {
        if (buffer.charAt(0) == 'V') {
            return this.descriptorReader.readVoidDescriptor(buffer);
        }
        return this.readTypeSignature(buffer);
    }

    protected String readThrowsSignature(CharBuffer buffer) {
        if (buffer.get() != '^') {
            return "?";
        }
        if (buffer.charAt(0) == 'L') {
            return this.readClassTypeSignature(buffer);
        }
        return this.readTypeVariableSignature(buffer);
    }

    public List<String> readMethodTypeSignature(CharBuffer buffer) {
        LinkedList<String> result = new LinkedList<String>();
        if (buffer.charAt(0) == '<') {
            this.readFormalTypeParameters(buffer);
        }
        if (buffer.get() != '(') {
            return result;
        }
        while (buffer.charAt(0) != ')') {
            result.addLast(this.readTypeSignature(buffer));
        }
        buffer.get();
        result.addFirst(this.readReturnType(buffer));
        while (buffer.hasRemaining() && buffer.charAt(0) == '^') {
            this.readThrowsSignature(buffer);
        }
        return result;
    }

    static {
        identifierTerminatingChar.add(Character.valueOf(';'));
        identifierTerminatingChar.add(Character.valueOf('<'));
        identifierTerminatingChar.add(Character.valueOf('.'));
        identifierTerminatingChar.add(Character.valueOf('/'));
        identifierTerminatingChar.add(Character.valueOf(':'));
    }
}

