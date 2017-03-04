/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.controller;

import java.awt.Component;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Observer;
import javax.swing.JProgressBar;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.unitdata.UnitInfo;

public interface Controller {
    public static final int UNITS_RESULT = 1;
    public static final int GRAPH_RESULT = 2;
    public static final int SAVE_RESULT = 3;
    public static final int NAMES_RESULT = 4;

    public void processFiles(Collection<String> var1, JProgressBar var2);

    public void processFiles(Collection<String> var1, Collection<String> var2, JProgressBar var3);

    public void processUnits(Collection<UnitInfo> var1, JProgressBar var2, ConstructionHints var3, Graphics var4);

    public void saveImage(Component var1, String var2, JProgressBar var3);

    public void retrieveNames(Collection<String> var1, JProgressBar var2);

    public void addObserver(Observer var1);
}

