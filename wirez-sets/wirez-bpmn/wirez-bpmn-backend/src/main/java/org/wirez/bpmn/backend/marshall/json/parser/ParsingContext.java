package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.util.GraphUtils;

public class ParsingContext implements ContextualParser.Context {

    private final DefinitionManager definitionManager;
    private final GraphUtils graphUtils;
    
    public ParsingContext(DefinitionManager definitionManager,
                          GraphUtils graphUtils) {
        this.definitionManager = definitionManager;
        this.graphUtils = graphUtils;
    }

    @Override
    public DefinitionManager getDefinitionManager() {
        return definitionManager;
    }

    @Override
    public GraphUtils getGraphUtils() {
        return graphUtils;
    }
    
}
