/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.util;

import java.util.ArrayList;
import java.util.Collection;

public class GenericNameConverter {
    public Collection<String> getTypeNames(String typeName) {
        String str;
        int endIdx;
        ArrayList<String> result = new ArrayList<String>();
        int startIdx = 0;
        for (endIdx = 0; endIdx < typeName.length(); ++endIdx) {
            switch (typeName.charAt(endIdx)) {
                case ' ': 
                case ',': 
                case '<': 
                case '>': {
                    str = typeName.substring(startIdx, endIdx);
                    if (!(str.equals("super") || str.equals("extends") || str.equals("?"))) {
                        result.add(str);
                    }
                    startIdx = endIdx + 1;
                    break;
                }
            }
        }
        str = typeName.substring(startIdx, endIdx);
        if (!(str.equals("super") || str.equals("extends") || str.equals("?"))) {
            result.add(str);
        }
        return result;
    }

    public String unqualify(String typeName) {
        StringBuilder builder = new StringBuilder();
        int startIdx = 0;
        block4 : for (int endIdx = 0; endIdx < typeName.length(); ++endIdx) {
            switch (typeName.charAt(endIdx)) {
                case '.': {
                    startIdx = endIdx + 1;
                    continue block4;
                }
                case ' ': 
                case ',': 
                case '<': 
                case '>': {
                    builder.append(typeName.substring(startIdx, endIdx + 1));
                    startIdx = endIdx + 1;
                    break;
                }
            }
        }
        builder.append(typeName.substring(startIdx));
        return builder.toString();
    }
}

