package org.wirez.core.api.service.definition;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.service.ServiceResponse;

import java.util.Collection;
import java.util.Set;

public interface DefinitionSetServiceResponse extends ServiceResponse {
    
    DefinitionSet getDefinitionSet();
    
    Definition getGraphElement();
    
    Set<Definition> getDefinitions();

    Set<PropertySet> getPropertySets();

    Collection<Rule> getRules();
    
}
