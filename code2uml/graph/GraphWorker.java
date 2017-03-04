/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingWorker;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.GraphConstructor;
import net.sourceforge.code2uml.graph.GraphConstructorImpl;
import net.sourceforge.code2uml.unitdata.UnitInfo;

public class GraphWorker
extends SwingWorker<Graph, Object>
implements Observer {
    private GraphConstructor constructor = new GraphConstructorImpl();
    private Collection<UnitInfo> units;
    private Graphics g;
    private ConstructionHints hints;

    public GraphWorker(Collection<UnitInfo> units, Graphics g, ConstructionHints hints) {
        this.units = units;
        this.g = g;
        this.hints = hints;
    }

    @Override
    protected Graph doInBackground() throws Exception {
        this.constructor.addObserver(this);
        return this.constructor.construct(this.units, this.g, this.hints);
    }

    @Override
    public void update(Observable observable, Object object) {
        if (observable == this.constructor) {
            this.firePropertyChange("progress", null, object);
        }
    }
}

