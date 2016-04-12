package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxPropertyManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.graph.util.GraphUtils;

public interface ContextualParser extends Parser {
    
    interface Context {
        
        DefinitionManager getDefinitionManager();
        
        GraphUtils getGraphUtils();

        Bpmn2OryxPropertyManager getOryxPropertyManager();

        Bpmn2OryxIdMappings getOryxIdMappings();

    }
    
    void initialize( Context context );
    
}
