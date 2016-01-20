package org.wirez.core.backend.service.definition;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.adapter.Adapter;
import org.wirez.core.api.adapter.DefinitionAdapter;
import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.service.definition.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Service
@ApplicationScoped
public class DefinitionServiceImpl implements DefinitionService {

    DefinitionManager definitionManager;

    @Inject
    public DefinitionServiceImpl(final DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    @Override
    public DefinitionSetResponse getDefinitionSet(final String id) {
        
        final DefinitionSet definitionSet = definitionManager.getDefinitionSet(id);
        final DefinitionSetAdapter adapter = definitionManager.getDefinitionSetAdapter(definitionSet.getClass());
        final Set<Definition> definitions = adapter.getDefinitions(definitionSet);
        final Set<PropertySet> propertySets = adapter.getPropertySets(definitionSet);
        final Collection<Rule> rules = adapter.getRules(definitionSet);
        
        Definition graphDefinition = null;
        for (Definition definition : definitions) {
            Class<? extends Element> elementClass = definitionManager.getDefinitionAdapter(definition.getClass()).getGraphElementType(definition);
            Class<? extends Element> graphClass = DefaultGraph.class;
            if ( elementClass.equals( graphClass ) ) {
                graphDefinition = definition;
            }
        }

        return new DefinitionSetResponseImpl(definitionSet, graphDefinition, definitions, propertySets, rules);
        
    }

    @Override
    public DefinitionResponse getDefinition(final String id) {

        final Collection<DefinitionSet> definitionSets = definitionManager.getDefinitionSets();
        if ( null != definitionSets ) {
            for (DefinitionSet definitionSet : definitionSets) {

                final DefinitionSetAdapter adapter = definitionManager.getDefinitionSetAdapter(definitionSet.getClass());
                final Set<Definition> definitions = adapter.getDefinitions(definitionSet);
                
                for (Definition definition : definitions) {
                    if (definition.getId().equals(id)) {
                        
                        final DefinitionAdapter definitionAdapter = definitionManager.getDefinitionAdapter(definition.getClass());
                        final Set<PropertySet> propertySets = definitionAdapter.getPropertySets(definition);
                        Class<? extends Element> elementClass = definitionAdapter.getGraphElementType(definition);
                        Map<Property, Object> properties = definitionAdapter.getPropertiesValues(definition);
                        return new DefinitionResponseImpl(definition, elementClass.getName(), properties, propertySets);
                    }
                }
            }
        }
        
        return null;
    }

    @Override
    public Element buildGraphElement(final String definitionId) {
        
        DefinitionResponse response = getDefinition(definitionId);
        
        if ( null != response ) {
            
            Definition definition = response.getDefinition();
            final DefinitionAdapter definitionAdapter = definitionManager.getDefinitionAdapter(definition.getClass());
            ElementFactory elementFactory = definitionAdapter.getElementFactory(definition);
            Set<Property> properties = definitionAdapter.getProperties(definition);
            Set<String> labels = definition.getLabels();
            Element<ViewContent<?>> element = elementFactory.build(definition, properties, labels);;
            return element;
            
        }
        
        return null;
        
    }
    
}
