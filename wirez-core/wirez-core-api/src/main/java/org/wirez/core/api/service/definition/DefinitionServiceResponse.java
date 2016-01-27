package org.wirez.core.api.service.definition;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.service.ServiceResponse;

import java.util.Map;
import java.util.Set;

public interface DefinitionServiceResponse extends ServiceResponse {

    Definition getDefinition();
    
    String getElementClassName();

    Map<Property, Object> getProperties();

    Map<PropertySet, Set<Property>> getPropertySets();

}
