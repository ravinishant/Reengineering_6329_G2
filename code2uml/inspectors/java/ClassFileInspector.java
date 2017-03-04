/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors.java;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.sourceforge.code2uml.inspectors.FileInspector;
import net.sourceforge.code2uml.inspectors.java.ClassFileReader;
import net.sourceforge.code2uml.unitdata.UnitInfo;

public class ClassFileInspector
implements FileInspector {
    private static ConcurrentMap<String, String> cache = new ConcurrentHashMap<String, String>();

    @Override
    public Collection<UnitInfo> inspect(String filePath) {
        ArrayList<UnitInfo> al = null;
        UnitInfo unit = this.processClassFile(filePath, UnitInfo.class);
        if (unit != null) {
            al = new ArrayList<UnitInfo>(1);
            al.add(unit);
        }
        return al;
    }

    @Override
    public Collection<UnitInfo> inspect(String filePath, Collection<String> namesFilter) {
        if (cache.containsKey(filePath) && !namesFilter.contains(cache.get(filePath))) {
            return null;
        }
        Collection<UnitInfo> units = this.inspect(filePath);
        if (units == null) {
            return null;
        }
        if (namesFilter.contains(units.iterator().next().getName())) {
            return units;
        }
        return null;
    }

    @Override
    public Collection<String> glance(String filePath) {
        String name = cache.get(filePath);
        ArrayList<String> result = null;
        if (name == null) {
            name = this.processClassFile(filePath, String.class);
        }
        if (name != null) {
            result = new ArrayList<String>(1);
            result.add(name);
        }
        return result;
    }

    private void updateCache(String filePath, String qualifiedName) {
        cache.putIfAbsent(filePath, qualifiedName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private <T> T processClassFile(String filePath, Class<T> resultType) {
        FilterInputStream in = null;
        try {
            in = new DataInputStream(new FileInputStream(filePath));
            ClassFileReader reader = new ClassFileReader();
            if (resultType.equals(String.class)) {
                String name = reader.readUnitName((DataInput)((Object)in));
                this.updateCache(filePath, name);
                String string = name;
                return (T)string;
            }
            if (resultType.equals(UnitInfo.class)) {
                UnitInfo unit = reader.read((DataInput)((Object)in));
                this.updateCache(filePath, unit.getName());
                UnitInfo unitInfo = unit;
                return (T)unitInfo;
            }
            T unit = null;
            return unit;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            T unit = null;
            return unit;
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

