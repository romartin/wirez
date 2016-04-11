package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.util.GraphUtils;

public interface ContextualParser extends Parser {
    
    interface Context {
        
        DefinitionManager getDefinitionManager();
        
        GraphUtils getGraphUtils();
        
    }
    
    void initialize( Context context );
    
}
