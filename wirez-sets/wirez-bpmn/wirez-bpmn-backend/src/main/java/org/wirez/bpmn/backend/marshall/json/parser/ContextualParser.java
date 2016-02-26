package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.core.api.DefinitionManager;

public interface ContextualParser extends Parser {
    
    interface Context {
        
        DefinitionManager getDefinitionManager();
        
    }
    
    void initialize( Context context );
    
}
