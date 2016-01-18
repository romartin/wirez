package org.wirez.core.api.service.definition;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.graph.Element;

public interface DefinitionSetResponse {
    
    DefinitionSet getDefinitionSet();
    
    Definition getGraphElement();
    
}
