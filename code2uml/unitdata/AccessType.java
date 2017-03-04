/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

public enum AccessType {
    PUBLIC,
    PACKAGE,
    PROTECTED,
    PRIVATE;
    

    private AccessType() {
    }

    public char toChar() {
        switch (this) {
            case PRIVATE: {
                return '-';
            }
            case PROTECTED: {
                return '#';
            }
            case PACKAGE: {
                return '~';
            }
            case PUBLIC: {
                return '+';
            }
        }
        return '?';
    }

    public String toString() {
        switch (this) {
            case PRIVATE: {
                return "private";
            }
            case PROTECTED: {
                return "protected";
            }
            case PACKAGE: {
                return "";
            }
            case PUBLIC: {
                return "public";
            }
        }
        return "?";
    }

}

