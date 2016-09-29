package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser;

import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;

public interface ContextualParser extends Parser {
    
    interface Context {
        
        DefinitionManager getDefinitionManager();
        
        GraphUtils getGraphUtils();

        Bpmn2OryxManager getOryxManager();

    }
    
    void initialize( Context context );
    
}
