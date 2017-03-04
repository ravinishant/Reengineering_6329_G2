/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Graphics;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.graph.NodeComponentFactory;
import net.sourceforge.code2uml.graph.NodeComponentFactoryImpl;
import net.sourceforge.code2uml.graph.NodeConstructor;
import net.sourceforge.code2uml.unitdata.AccessType;
import net.sourceforge.code2uml.unitdata.FieldInfo;
import net.sourceforge.code2uml.unitdata.MethodInfo;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.util.GenericNameConverter;

class NodeConstructorImpl
implements NodeConstructor {
    private NodeComponentFactory factory = new NodeComponentFactoryImpl();
    private GenericNameConverter converter = new GenericNameConverter();
    private static final char SPACE = ' ';
    private static final char DOT = '.';

    @Override
    public NodeComponent construct(UnitInfo unit, Graphics g, ConstructionHints hints) {
        StringBuilder builder;
        HashSet<AccessType> accessSet = new HashSet<AccessType>();
        if (hints.isPrivateVisible()) {
            accessSet.add(AccessType.PRIVATE);
        }
        if (hints.isProtectedVisible()) {
            accessSet.add(AccessType.PROTECTED);
        }
        if (hints.isPackageVisible()) {
            accessSet.add(AccessType.PACKAGE);
        }
        if (hints.isPublicVisible()) {
            accessSet.add(AccessType.PUBLIC);
        }
        NodeComponent node = this.factory.create(g, hints);
        node.setUnitName(unit.getName());
        if (unit.isInterface()) {
            node.addToName("<<interface>>");
        } else if (unit.isEnum()) {
            node.addToName("<<enum>>");
        }
        node.addToName(unit.getSimpleName());
        if (hints.isFieldsVisible()) {
            TreeSet<String> fields = new TreeSet<String>();
            for (FieldInfo field2 : unit.getFields()) {
                if (!accessSet.contains((Object)field2.getAccessType()) || field2.isStatic() && !hints.isStaticVisible() || field2.isFinal() && !hints.isFinalVisible()) continue;
                builder = new StringBuilder();
                builder.append(field2.getAccessType().toChar());
                builder.append(' ');
                if (field2.isStatic()) {
                    builder.append("static ");
                }
                if (field2.isFinal()) {
                    builder.append("final ");
                }
                builder.append(this.converter.unqualify(field2.getTypeName()));
                builder.append(' ');
                builder.append(field2.getName());
                fields.add(builder.toString());
            }
            for (String field : fields) {
                node.addToField(field);
            }
        }
        if (unit.isEnum() && hints.isEnumsVisible()) {
            TreeSet<String> enums = new TreeSet<String>();
            enums.addAll(unit.getEnumValues());
            for (String enumValue : enums) {
                node.addToEnum(enumValue);
            }
        }
        if (hints.isMethodsVisible()) {
            TreeSet<String> methods = new TreeSet<String>();
            for (MethodInfo method2 : unit.getMethods()) {
                if (!accessSet.contains((Object)method2.getAccessType()) || method2.isStatic() && !hints.isStaticVisible()) continue;
                builder = new StringBuilder();
                builder.append(method2.getAccessType().toChar());
                builder.append(' ');
                if (method2.isStatic()) {
                    builder.append("static ");
                }
                builder.append(this.converter.unqualify(method2.getReturnTypeName()));
                builder.append(' ');
                builder.append(method2.getName());
                builder.append("(");
                if (hints.isArgumentsVisible() && method2.getArguments() != null) {
                    String str;
                    Iterator<String> it = method2.getArguments().iterator();
                    if (it.hasNext()) {
                        str = it.next();
                        builder.append(this.converter.unqualify(str));
                    }
                    while (it.hasNext()) {
                        str = it.next();
                        builder.append(", ");
                        builder.append(this.converter.unqualify(str));
                    }
                }
                builder.append(")");
                methods.add(builder.toString());
            }
            for (String method : methods) {
                node.addToMethod(method);
            }
        }
        return node;
    }
}

