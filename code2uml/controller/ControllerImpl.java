/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.controller;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import net.sourceforge.code2uml.controller.Controller;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.GraphWorker;
import net.sourceforge.code2uml.image.ImageWorker;
import net.sourceforge.code2uml.unitdata.NamesWorker;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.unitdata.UnitsWorker;
import net.sourceforge.code2uml.util.ProgressData;

class ControllerImpl
extends Observable
implements Controller {
    ControllerImpl() {
    }

    @Override
    public void processFiles(Collection<String> filePaths, JProgressBar progress) {
        this.processFiles(filePaths, null, progress);
    }

    @Override
    public void processFiles(Collection<String> filePaths, Collection<String> namesFilter, final JProgressBar progress) {
        final UnitsWorker worker = new UnitsWorker(filePaths, namesFilter);
        worker.addPropertyChangeListener(new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if (name.equals("progress") && progress != null) {
                    ProgressData data = (ProgressData)evt.getNewValue();
                    ControllerImpl.this.setProgress(progress, (int)data.getProgress(), data.getMessage());
                } else if (name.equals("state") && evt.getNewValue().equals((Object)SwingWorker.StateValue.DONE)) {
                    try {
                        Collection units = (Collection)worker.get();
                        ControllerImpl.this.setChanged();
                        ControllerImpl.this.notifyObservers(new Object[]{1, units});
                        ControllerImpl.this.clearChanged();
                    }
                    catch (ExecutionException ex) {
                        ex.printStackTrace();
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        worker.execute();
    }

    @Override
    public void processUnits(Collection<UnitInfo> units, final JProgressBar progress, ConstructionHints hints, Graphics g) {
        final GraphWorker worker = new GraphWorker(units, g, hints);
        worker.addPropertyChangeListener(new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if (name.equals("progress") && progress != null) {
                    ProgressData data = (ProgressData)evt.getNewValue();
                    ControllerImpl.this.setProgress(progress, (int)data.getProgress(), data.getMessage());
                } else if (name.equals("state") && evt.getNewValue().equals((Object)SwingWorker.StateValue.DONE)) {
                    try {
                        Graph g = (Graph)worker.get();
                        ControllerImpl.this.setChanged();
                        ControllerImpl.this.notifyObservers(new Object[]{2, g});
                        ControllerImpl.this.clearChanged();
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    catch (ExecutionException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        worker.execute();
    }

    @Override
    public void saveImage(Component comp, String filePath, final JProgressBar progress) {
        ImageWorker worker = new ImageWorker(comp, filePath);
        worker.addPropertyChangeListener(new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if (name.equals("progress")) {
                    ProgressData data = (ProgressData)evt.getNewValue();
                    ControllerImpl.this.setProgress(progress, (int)data.getProgress(), "saving...");
                } else if (name.equals("state") && evt.getNewValue() == SwingWorker.StateValue.DONE) {
                    ControllerImpl.this.setChanged();
                    ControllerImpl.this.notifyObservers(3);
                    ControllerImpl.this.clearChanged();
                }
            }
        });
        worker.execute();
    }

    @Override
    public void retrieveNames(Collection<String> filePaths, final JProgressBar progress) {
        final NamesWorker worker = new NamesWorker(filePaths);
        worker.addPropertyChangeListener(new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if (name.equals("progress")) {
                    ProgressData progressData = (ProgressData)evt.getNewValue();
                    ControllerImpl.this.setProgress(progress, (int)progressData.getProgress(), progressData.getMessage());
                } else if (name.equals("state") && evt.getNewValue().equals((Object)SwingWorker.StateValue.DONE)) {
                    try {
                        Object result = worker.get();
                        ControllerImpl.this.setChanged();
                        ControllerImpl.this.notifyObservers(new Object[]{4, result});
                        ControllerImpl.this.clearChanged();
                    }
                    catch (ExecutionException ex) {
                        ex.printStackTrace();
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        worker.execute();
    }

    private void setProgress(final JProgressBar progress, final int value, final String str) {
        if (progress == null) {
            return;
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                progress.setValue(value);
                if (str != null) {
                    progress.setString(str);
                }
            }
        });
    }

}

