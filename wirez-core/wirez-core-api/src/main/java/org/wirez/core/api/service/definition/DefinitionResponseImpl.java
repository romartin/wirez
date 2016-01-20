package org.wirez.core.api.service.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.graph.Element;

import java.util.Map;
import java.util.Set;

@Portable
public class DefinitionResponseImpl implements DefinitionResponse {

    private final Definition definition;
    private final String elementClass;
    private final Map<Property, Object> properties;
    private final Set<PropertySet> propertySets;

    public DefinitionResponseImpl(@MapsTo("definition") Definition definition,
                                  @MapsTo("elementClass") String elementClass,
                                  @MapsTo("properties") Map<Property, Object> properties,
                                  @MapsTo("propertySets") Set<PropertySet> propertySets) {
        this.definition = definition;
        this.elementClass = elementClass;
        this.properties = properties;
        this.propertySets = propertySets;
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
    public Map<Property, Object> getProperties() {
        return properties;
    }

    @Override
    public Set<PropertySet> getPropertySets() {
        return propertySets;
    }

}
