/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.inspectors.java;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.sourceforge.code2uml.inspectors.FileInspector;
import net.sourceforge.code2uml.inspectors.java.ClassFileReader;
import net.sourceforge.code2uml.unitdata.UnitInfo;

public class JarFileInspector
extends Observable
implements FileInspector {
    private static ConcurrentMap<String, ConcurrentMap<String, String>> cache = new ConcurrentHashMap<String, ConcurrentMap<String, String>>();

    @Override
    public Collection<UnitInfo> inspect(String filePath) {
        return this.processJarFile(filePath, null, UnitInfo.class);
    }

    @Override
    public Collection<UnitInfo> inspect(String filePath, Collection<String> namesFilter) {
        return this.processJarFile(filePath, namesFilter, UnitInfo.class);
    }

    @Override
    public Collection<String> glance(String filePath) {
        return this.processJarFile(filePath, null, String.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private <T> Collection<T> processJarFile(String filePath, Collection<String> namesFilter, Class<T> resultType) {
        ZipInputStream in = null;
        ConcurrentMap<String, String> jarCache = cache.get(filePath);
        if (jarCache == null) {
            jarCache = new ConcurrentHashMap<String, String>();
            cache.put(filePath, jarCache);
        }
        try {
            LinkedList t;
            ZipEntry entry;
            in = new ZipInputStream(new FileInputStream(filePath));
            LinkedList result = new LinkedList();
            while ((entry = in.getNextEntry()) != null) {
                if (namesFilter != null && jarCache.containsKey(entry.getName()) && !namesFilter.contains(jarCache.get(entry.getName())) || (t = this.processZipEntry(in, entry, resultType)) == null) continue;
                result.add(t);
                if (!jarCache.containsKey(entry.getName())) {
                    if (t instanceof String) {
                        jarCache.putIfAbsent(entry.getName(), (String)((Object)t));
                    } else {
                        jarCache.putIfAbsent(entry.getName(), ((UnitInfo)((Object)t)).getName());
                    }
                }
                this.setChanged();
                this.notifyObservers(result.size());
                this.clearChanged();
            }
            t = result.isEmpty() ? null : result;
            return t;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            Collection<T> entry = null;
            return entry;
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

    private <T> T processZipEntry(ZipInputStream zipIn, ZipEntry entry, Class<T> resultType) {
        if (!entry.getName().endsWith(".class")) {
            return null;
        }
        int compression = entry.getMethod();
        if (compression != 0 && compression != 8) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            DataInputStream in = new DataInputStream(zipIn);
            ClassFileReader reader = new ClassFileReader();
            if (resultType.equals(String.class)) {
                return (T)reader.readUnitName(in);
            }
            if (resultType.equals(UnitInfo.class)) {
                return (T)reader.read(in);
            }
            return null;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

