package org.wirez.core.backend.service.definition;

import org.jboss.errai.bus.server.annotations.Service;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.service.definition.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
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
        final Collection<Definition> definitions = definitionManager.getDefinitions(definitionSet);
        Definition graphDefinition = null;
        for (Definition definition : definitions) {
            Class<? extends Element> elementClass = definitionManager.getGraphElementType(definition);
            Class<? extends Element> graphClass = DefaultGraph.class;
            if ( elementClass.equals( graphClass ) ) {
                graphDefinition = definition;
            }
        }
        
        return new DefinitionSetResponseImpl(definitionSet, graphDefinition, definitions);
    }

    @Override
    public DefinitionResponse getDefinition(final String id) {

        final Collection<DefinitionSet> definitionSets = definitionManager.getDefinitionSets();
        if ( null != definitionSets ) {
            for (DefinitionSet definitionSet : definitionSets) {
                final Collection<Definition> definitions = definitionManager.getDefinitions(definitionSet);
                for (Definition definition : definitions) {
                    if (definition.getId().equals(id)) {
                        Class<? extends Element> elementClass = definitionManager.getGraphElementType(definition);
                        Set<Property> properties = definitionManager.getProperties(definition);
                        return new DefinitionResponseImpl(definition, elementClass.getName(), properties);
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
            ElementFactory elementFactory = definitionManager.getFactory(definition);
            Set<Property> properties = definitionManager.getProperties(definition);
            Set<String> labels = definition.getDefinitionContent().getLabels();
            Element<ViewContent<?>> element = elementFactory.build(definition, properties, labels);;
            return element;
        }
        return null;
    }
}
