package org.wirez.core.api.adapter.shared;

import org.wirez.core.api.adapter.DefinitionAdapter;
import org.wirez.core.api.definition.DefaultDefinition;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.HasValue;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.factory.DefaultGraphFactory;
import org.wirez.core.api.graph.factory.EdgeFactory;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.graph.factory.NodeFactory;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.rule.Rule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class DefaultDefinitionAdapter implements DefinitionAdapter<DefaultDefinition> {

    DefaultGraphFactory<? extends Definition> graphFactory;
    NodeFactory<? extends Definition> nodeFactory;
    EdgeFactory<? extends Definition> edgeFactory;

    public DefaultDefinitionAdapter() {
    }

    @Inject
    public DefaultDefinitionAdapter(DefaultGraphFactory<? extends Definition> graphFactory, NodeFactory<? extends Definition> nodeFactory, EdgeFactory<? extends Definition> edgeFactory) {
        this.graphFactory = graphFactory;
        this.nodeFactory = nodeFactory;
        this.edgeFactory = edgeFactory;
    }

    @Override
    public Set<PropertySet> getPropertySets(final DefaultDefinition pojo) {
        Set<PropertySet> result = null;
        if ( null != pojo ) {
            result = pojo.getPropertySets();
        }
        
        return result;
    }

    @Override
    public Set<Property> getProperties(final DefaultDefinition pojo) {
        Set<Property> result = null;
        if ( null != pojo ) {
            result = pojo.getProperties();
        }

        return result;
    }

    @Override
    public Map<Property, Object> getPropertiesValues(final DefaultDefinition pojo) {
        Map<Property, Object> result = null;
        if ( null != pojo ) {
            final Set<Property> properties = pojo.getProperties();
            if ( null != properties && !properties.isEmpty() ) {
                result = new HashMap<>(properties.size());
                for (final Property property : properties) {
                    HasValue hasValue = (HasValue) property;
                    result.put(property, hasValue.getValue());
                }
            }
        }

        return result;
    }

    @Override
    public Class<? extends Element> getGraphElementType(final DefaultDefinition pojo) {
        // TODO
        return null;
    }

    @Override
    public ElementFactory getElementFactory(final DefaultDefinition definition) {
        if ( null != definition ) {

            Class<? extends Element> item = getGraphElementType(definition);

            if (item.equals(DefaultGraph.class)) {
                return graphFactory;
            } else if (item.equals(Node.class)) {
                return nodeFactory;
            } else if (item.equals(Node.class)) {
                return edgeFactory;
            }

        }

        return null;
    }

    @Override
    public boolean accepts(final Object pojo) {
        return pojo instanceof DefaultDefinition;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
}
