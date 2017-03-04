/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.unitdata;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingWorker;
import net.sourceforge.code2uml.unitdata.UnitsRetriever;
import net.sourceforge.code2uml.unitdata.UnitsRetrieverImpl;

public class NamesWorker
extends SwingWorker<Collection<String>, Object>
implements Observer {
    private UnitsRetriever retriever;
    private Collection<String> filePaths;

    public NamesWorker(Collection<String> filePaths) {
        this.filePaths = filePaths;
        this.retriever = new UnitsRetrieverImpl();
        this.retriever.addObserver(this);
    }

    @Override
    protected Collection<String> doInBackground() {
        return this.retriever.retrieveNames(this.filePaths);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.firePropertyChange("progress", null, arg);
    }
}

