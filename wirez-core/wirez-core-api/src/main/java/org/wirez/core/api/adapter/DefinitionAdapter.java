package org.wirez.core.api.adapter;

import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.rule.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface DefinitionAdapter<T extends Definition> extends Adapter<T> {

    Set<PropertySet> getPropertySets(T pojo);

    Set<Property> getProperties(T pojo);

    Collection<Rule> getRules(T pojo);

    Map<Property, Object> getPropertiesValues(T pojo);

    Class<? extends Element> getGraphElementType(T pojo);

    ElementFactory getElementFactory(T pojo);
    
}
