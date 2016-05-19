package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.graph.util.GraphUtils;

public interface ContextualParser extends Parser {
    
    interface Context {
        
        DefinitionManager getDefinitionManager();
        
        GraphUtils getGraphUtils();

        Bpmn2OryxManager getOryxManager();

    }
    
    void initialize( Context context );
    
}
