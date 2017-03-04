/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

import java.util.Collection;
import java.util.Observer;
import net.sourceforge.code2uml.unitdata.UnitInfo;

public interface UnitsRetriever {
    public Collection<UnitInfo> retrieve(Collection<String> var1);

    public Collection<UnitInfo> retrieve(Collection<String> var1, Collection<String> var2);

    public Collection<String> retrieveNames(Collection<String> var1);

    public void addObserver(Observer var1);
}

