/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph;

import java.awt.Graphics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import net.sourceforge.code2uml.graph.ConstructionHints;
import net.sourceforge.code2uml.graph.EdgeComponent;
import net.sourceforge.code2uml.graph.EdgeType;
import net.sourceforge.code2uml.graph.Graph;
import net.sourceforge.code2uml.graph.GraphConstructor;
import net.sourceforge.code2uml.graph.GraphImpl;
import net.sourceforge.code2uml.graph.NodeComponent;
import net.sourceforge.code2uml.graph.NodeConstructorImpl;
import net.sourceforge.code2uml.graph.layouts.GraphLayout;
import net.sourceforge.code2uml.graph.layouts.GraphLayoutFactory;
import net.sourceforge.code2uml.graph.layouts.GraphLayoutFactoryImpl;
import net.sourceforge.code2uml.unitdata.FieldInfo;
import net.sourceforge.code2uml.unitdata.UnitInfo;
import net.sourceforge.code2uml.util.GenericNameConverter;
import net.sourceforge.code2uml.util.ProgressData;

public class GraphConstructorImpl
extends Observable
implements GraphConstructor {
    private GraphLayoutFactory factory = new GraphLayoutFactoryImpl();

    @Override
    public Graph construct(Collection<UnitInfo> units, Graphics g, ConstructionHints hints) {
        GraphImpl graph = new GraphImpl();
        NodeConstructorImpl constructor = new NodeConstructorImpl();
        GenericNameConverter converter = new GenericNameConverter();
        HashMap<String, NodeComponent> nodes = new HashMap<String, NodeComponent>();
        HashMap<String, UnitInfo> unitsMap = new HashMap<String, UnitInfo>();
        boolean showNonpublic = hints.isNonpublicUnitsVisible();
        int unitCount = 0;
        for (UnitInfo unit2 : units) {
            if (showNonpublic || unit2.isPublic()) {
                unitsMap.put(unit2.getName(), unit2);
                nodes.put(unit2.getName(), constructor.construct(unit2, g, hints));
                ++unitCount;
            }
            this.setChanged();
            this.notifyObservers(new ProgressData(50.0 * (double)unitCount / (double)units.size()));
            this.clearChanged();
        }
        for (UnitInfo unit2 : units) {
            if (!showNonpublic && !unit2.isPublic()) continue;
            for (String name : unit2.getSupertypes()) {
                UnitInfo target;
                if (!nodes.containsKey(name) || (!(target = (UnitInfo)unitsMap.get(name)).isClass() || !hints.isGeneralizationDrawn()) && (!target.isInterface() || !hints.isRealizationDrawn())) continue;
                EdgeComponent edge = new EdgeComponent();
                edge.setFrom((NodeComponent)nodes.get(unit2.getName()));
                edge.setTo((NodeComponent)nodes.get(name));
                if (target.isInterface()) {
                    edge.setType(EdgeType.REALIZATION);
                } else {
                    edge.setType(EdgeType.GENERALIZATION);
                }
                ((NodeComponent)nodes.get(unit2.getName())).addEdge(edge);
            }
            if (!hints.isHasADrawn()) continue;
            for (FieldInfo field : unit2.getFields()) {
                if (field.isStatic()) continue;
                for (String type : converter.getTypeNames(field.getTypeName())) {
                    if (!nodes.containsKey(type)) continue;
                    EdgeComponent edge = new EdgeComponent();
                    edge.setFrom((NodeComponent)nodes.get(unit2.getName()));
                    edge.setTo((NodeComponent)nodes.get(type));
                    edge.setType(hints.getHasAType());
                    ((NodeComponent)nodes.get(unit2.getName())).addEdge(edge);
                }
            }
        }
        graph.getNodes().addAll(nodes.values());
        GraphLayout layout = this.factory.getLayout(hints.getLayoutName());
        layout.addObserver(new Observer(){

            @Override
            public void update(Observable observable, Object arg) {
                GraphConstructorImpl.this.setChanged();
                GraphConstructorImpl.this.notifyObservers(new ProgressData(((ProgressData)arg).getProgress() / 2.0 + 50.0));
                GraphConstructorImpl.this.clearChanged();
            }
        });
        layout.layout(graph);
        return graph;
    }

}

