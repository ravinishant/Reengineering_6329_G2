/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingWorker;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.unitdata.UnitsRetriever;
import net.sourceforge.code2uml.unitdata.UnitsRetrieverImpl;

public class UnitsWorker
extends SwingWorker<Collection<UnitInfo>, Object>
implements Observer {
    private Collection<String> filePaths;
    private Collection<String> namesFilter;
    private UnitsRetriever retriever;

    public UnitsWorker(Collection<String> filePaths) {
        this.filePaths = filePaths;
        this.retriever = new UnitsRetrieverImpl();
        this.retriever.addObserver(this);
    }

    public UnitsWorker(Collection<String> filePaths, Collection<String> namesFilter) {
        this(filePaths);
        this.namesFilter = namesFilter;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(this.retriever)) {
            this.firePropertyChange("progress", null, arg);
        }
    }

    @Override
    protected Collection<UnitInfo> doInBackground() {
        return this.retriever.retrieve(this.filePaths, this.namesFilter);
    }
}

