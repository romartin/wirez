package org.wirez.core.backend.service.definition;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.adapter.*;
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
import java.util.*;

@Service
@ApplicationScoped
public class DefinitionServiceImpl implements DefinitionService {

    @Inject
    DefinitionManager definitionManager;

    @Override
    public DefinitionSetResponse getDefinitionSet(final String id) {
        
        final DefinitionSet definitionSet = definitionManager.getDefinitionSet(id);
        final DefinitionSetAdapter adapter = definitionManager.getDefinitionSetAdapter(definitionSet);
        final DefinitionSetRuleAdapter ruleAdapter = definitionManager.getDefinitionSetRuleAdapter(definitionSet);
        final Set<Definition> definitions = adapter.getDefinitions(definitionSet);
        final Set<PropertySet> propertySets = adapter.getPropertySets(definitionSet);
        final Collection<Rule> rules = ruleAdapter.getRules(definitionSet);
        
        Definition graphDefinition = null;
        for (Definition definition : definitions) {
            Class<? extends Element> elementClass = definitionManager.getDefinitionAdapter(definition).getGraphElementType(definition);
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

                final DefinitionSetAdapter adapter = definitionManager.getDefinitionSetAdapter(definitionSet);
                final Set<Definition> definitions = adapter.getDefinitions(definitionSet);
                
                for (Definition definition : definitions) {
                    if (definition.getId().equals(id)) {
                        
                        final DefinitionAdapter definitionAdapter = definitionManager.getDefinitionAdapter(definition);
                        Class<? extends Element> elementClass = definitionAdapter.getGraphElementType(definition);
                        Map<Property, Object> properties = definitionAdapter.getPropertiesValues(definition);
                        Map<PropertySet, Set<Property>> propertySetSetMap = new HashMap<>();
                        final Set<PropertySet> propertySets = definitionAdapter.getPropertySets(definition);
                        if ( null != propertySets ) {
                            for (PropertySet propertySet : propertySets) {
                                PropertySetAdapter pSetAdapter = definitionManager.getPropertySetAdapter(propertySet);
                                Set<Property> pSetProperties = pSetAdapter.getProperties(propertySet);
                                pSetProperties = pSetProperties != null ? pSetProperties : new HashSet<Property>();
                                propertySetSetMap.put(propertySet, pSetProperties);
                            }
                        }
                        return new DefinitionResponseImpl(definition, elementClass.getName(), properties, propertySetSetMap);
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
            final DefinitionAdapter definitionAdapter = definitionManager.getDefinitionAdapter(definition);
            ElementFactory elementFactory = definitionAdapter.getElementFactory(definition);
            
            // Cache on element properties the annotated Property's on the definition.
            Set<Property> properties = definitionAdapter.getProperties(definition);

            // And properties on each definition's annotated PropertySet.
            Set<PropertySet> propertySets = definitionAdapter.getPropertySets(definition);
            if ( null != propertySets && !propertySets.isEmpty() ) {
                for (PropertySet propertySet : propertySets) {
                    PropertySetAdapter propertySetAdapter = definitionManager.getPropertySetAdapter(propertySet);
                    Set<Property> setProperties = propertySetAdapter.getProperties(propertySet);
                    if( null != setProperties ) {
                        properties.addAll(setProperties);
                    }
                }
            }
            Set<String> labels = definition.getLabels();
            Element<ViewContent<?>> element = elementFactory.build(definition, properties, labels);;
            return element;
            
        }
        
        return null;
        
    }
    
}
