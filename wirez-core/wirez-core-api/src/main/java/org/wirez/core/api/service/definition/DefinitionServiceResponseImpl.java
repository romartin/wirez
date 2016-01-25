package org.wirez.core.api.service.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;

import java.util.Map;
import java.util.Set;

@Portable
public class DefinitionServiceResponseImpl implements DefinitionServiceResponse {

    private final Definition definition;
    private final String elementClass;
    private final Map<Property, Object> properties;
    private final Map<PropertySet, Set<Property>> propertySets;

    public DefinitionServiceResponseImpl(@MapsTo("definition") Definition definition,
                                         @MapsTo("elementClass") String elementClass,
                                         @MapsTo("properties") Map<Property, Object> properties,
                                         @MapsTo("propertySets") Map<PropertySet, Set<Property>> propertySets) {
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
    public Map<PropertySet, Set<Property>> getPropertySets() {
        return propertySets;
    }

}
