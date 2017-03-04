/*
 * Decompiled with CFR 0_118.
 */
package net.sourceforge.code2uml.graph.layouts;

import java.util.HashMap;
import java.util.Map;
import net.sourceforge.code2uml.graph.layouts.GraphLayout;
import net.sourceforge.code2uml.graph.layouts.GraphLayoutFactory;
import net.sourceforge.code2uml.graph.layouts.SimpleGraphLayout;
import net.sourceforge.code2uml.graph.layouts.force.ForceBasedGraphLayout;

public class GraphLayoutFactoryImpl
implements GraphLayoutFactory {
    private Map<String, GraphLayout> layouts = new HashMap<String, GraphLayout>();
    private GraphLayout defaultLayout = new SimpleGraphLayout();

    public GraphLayoutFactoryImpl() {
        this.layouts.put("rectangular", new ForceBasedGraphLayout());
    }

    @Override
    public GraphLayout getLayout(String name) {
        GraphLayout layout = this.layouts.get(name);
        if (layout == null) {
            return this.defaultLayout;
        }
        return layout;
    }
}

