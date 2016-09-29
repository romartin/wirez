package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser;

import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;

public class ParsingContext implements ContextualParser.Context {

    private final DefinitionManager definitionManager;
    private final GraphUtils graphUtils;
    private final Bpmn2OryxManager oryxManager;
    
    public ParsingContext(DefinitionManager definitionManager,
                          GraphUtils graphUtils, 
                          Bpmn2OryxManager oryxManager) {
        this.definitionManager = definitionManager;
        this.graphUtils = graphUtils;
        this.oryxManager = oryxManager;
    }

    @Override
    public DefinitionManager getDefinitionManager() {
        return definitionManager;
    }

    @Override
    public GraphUtils getGraphUtils() {
        return graphUtils;
    }

    @Override
    public Bpmn2OryxManager getOryxManager() {
        return oryxManager;
    }

}
