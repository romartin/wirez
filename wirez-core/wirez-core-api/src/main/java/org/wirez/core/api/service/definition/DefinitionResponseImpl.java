package org.wirez.core.api.service.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Element;

import java.util.Set;

@Portable
public class DefinitionResponseImpl implements DefinitionResponse {

    private final Definition definition;
    private final String elementClass;
    private final Set<Property> properties;

    public DefinitionResponseImpl(@MapsTo("definition") Definition definition,
                                  @MapsTo("elementClass") String elementClass,
                                  @MapsTo("properties") Set<Property> properties) {
        this.definition = definition;
        this.elementClass = elementClass;
        this.properties = properties;
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    @Override
    public String getElementClassName() {
        return elementClass;
    }

    @Override
    public Set<Property> getProperties() {
        return properties;
    }
    
}
