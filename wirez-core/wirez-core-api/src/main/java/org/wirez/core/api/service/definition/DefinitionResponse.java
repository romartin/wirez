package org.wirez.core.api.service.definition;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Element;

import java.util.Set;

public interface DefinitionResponse {

    Definition getDefinition();
    
    String getElementClassName();

    Set<Property> getProperties();
    
}
