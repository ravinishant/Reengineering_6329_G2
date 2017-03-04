/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import net.sourceforge.code2uml.inspectors.FileInspector;
import net.sourceforge.code2uml.inspectors.InspectorsFactory;
import net.sourceforge.code2uml.inspectors.InspectorsFactoryImpl;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.unitdata.UnitsRetriever;
import net.sourceforge.code2uml.util.ProgressData;

class UnitsRetrieverImpl
extends Observable
implements UnitsRetriever,
Observer {
    private InspectorsFactory factory = new InspectorsFactoryImpl();
    private double progress = 0.0;
    private int readCount = 0;

    @Override
    public Collection<UnitInfo> retrieve(Collection<String> filePaths) {
        return this.retrieve(filePaths, null);
    }

    @Override
    public Collection<UnitInfo> retrieve(Collection<String> filePaths, Collection<String> namesFilter) {
        HashMap<String, UnitInfo> resultMap = new HashMap<String, UnitInfo>();
        this.progress = 0.0;
        this.readCount = 0;
        for (String filePath : filePaths) {
            Collection<UnitInfo> units;
            int idx;
            FileInspector inspector = this.factory.getInspector(filePath.substring((idx = filePath.lastIndexOf(46)) + 1));
            if (inspector instanceof Observable) {
                ((Observable)((Object)inspector)).addObserver(this);
            }
            if ((units = inspector.inspect(filePath, namesFilter)) != null) {
                for (UnitInfo unit : units) {
                    boolean includeInResult = namesFilter != null && namesFilter.contains(unit.getName());
                    if (!includeInResult) continue;
                    UnitInfo existing = (UnitInfo)resultMap.get(unit.getName());
                    if (existing == null) {
                        resultMap.put(unit.getName(), unit);
                        continue;
                    }
                    if (!existing.isPartial() || !unit.isPartial()) continue;
                    existing.merge(unit);
                }
            }
            this.progress += 100.0 / (double)filePaths.size();
            this.readCount = resultMap.size();
            this.setChanged();
            this.notifyObservers(new ProgressData(this.progress, "found " + this.readCount + " units"));
            this.clearChanged();
        }
        return resultMap.values();
    }

    @Override
    public Collection<String> retrieveNames(Collection<String> filePaths) {
        this.progress = 0.0;
        HashSet<String> result = new HashSet<String>();
        for (String filePath : filePaths) {
            Collection<String> names;
            FileInspector inspector = this.factory.getInspector(filePath.substring(filePath.lastIndexOf(46) + 1));
            if (inspector instanceof Observable) {
                ((Observable)((Object)inspector)).addObserver(this);
            }
            if ((names = inspector.glance(filePath)) != null) {
                for (String name : names) {
                    result.add(name);
                }
            }
            this.progress += 100.0 / (double)filePaths.size();
            this.readCount = result.size();
            this.setChanged();
            this.notifyObservers(new ProgressData(this.progress, "found " + this.readCount + " units"));
            this.clearChanged();
        }
        return result;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof FileInspector) {
            int count = (Integer)arg;
            this.setChanged();
            this.notifyObservers(new ProgressData(this.progress, "found " + (this.readCount + count) + " units"));
            this.clearChanged();
        }
    }
}

