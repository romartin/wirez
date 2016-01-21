package org.wirez.core.backend.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.adapter.DefinitionAdapter;
import org.wirez.core.api.definition.DefaultDefinition;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.factory.DefaultGraphFactory;
import org.wirez.core.api.graph.factory.EdgeFactory;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.graph.factory.NodeFactory;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.api.registry.RuleRegistry;
import org.wirez.core.api.rule.Rule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.*;

// TODO: ElementFactory's injection not working.
@ApplicationScoped
public class AnnotatedDefinitionAdapter implements DefinitionAdapter<Definition> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedDefinitionAdapter.class);

    AnnotatedPropertyAdapter propertyAdapter;
    RuleRegistry<Rule> ruleRuleRegistry;
    DefaultGraphFactory<? extends Definition> graphFactory;
    NodeFactory<? extends Definition> nodeFactory;
    EdgeFactory<? extends Definition> edgeFactory;

    @Inject
    public AnnotatedDefinitionAdapter(AnnotatedPropertyAdapter propertyAdapter, 
                                      RuleRegistry<Rule> ruleRuleRegistry,
                                      DefaultGraphFactory<? extends Definition> graphFactory,
                                      NodeFactory<? extends Definition> nodeFactory,
                                      EdgeFactory<? extends Definition> edgeFactory) {
        this.propertyAdapter = propertyAdapter;
        this.ruleRuleRegistry = ruleRuleRegistry;
        this.graphFactory = graphFactory;
        this.nodeFactory = nodeFactory;
        this.edgeFactory = edgeFactory;
    }

    
    @Override
    public boolean accepts(Class pojoClass) {
        return !pojoClass.equals(DefaultDefinition.class);
    }

    @Override
    public Set<PropertySet> getPropertySets(final Definition definition) {
        Set<PropertySet> result = null;

        if ( null != definition ) {
            Method[] methods = definition.getClass().getMethods();
            if ( null != methods ) {
                result = new HashSet<>();
                for (Method method : methods) {
                    org.wirez.core.api.annotation.definition.PropertySet annotation = method.getAnnotation(org.wirez.core.api.annotation.definition.PropertySet.class);
                    if ( null != annotation) {
                        try {
                            PropertySet propertySet = (PropertySet) method.invoke(definition);
                            result.add(propertySet);
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated property sets for Definition with id " + definition.getId());
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Set<Property> getProperties(final Definition definition) {
        Set<Property> result = null;

        if ( null != definition ) {
            Method[] methods = definition.getClass().getMethods();
            if ( null != methods ) {
                result = new HashSet<>();
                for (Method method : methods) {
                    org.wirez.core.api.annotation.definition.Property annotation = method.getAnnotation(org.wirez.core.api.annotation.definition.Property.class);
                    if ( null != annotation) {
                        try {
                            Property property = (Property) method.invoke(definition);
                            result.add(property);
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated properties for Definition with id " + definition.getId());
                        }
                    }
                }
            }
        }
        
        return result;
    }

    // Rules are generated at compile time by the default annotation processor and available at runtime in the RuleRegistry
    @Override
    public Collection<Rule> getRules(Definition pojo) {
        return ruleRuleRegistry.getRules(pojo);
    }

    @Override
    public Map<Property, Object> getPropertiesValues(Definition pojo) {

        if ( null != pojo ) {

            Set<Property> properties = getProperties(pojo);

            if ( null != properties && !properties.isEmpty() ) {

                final Map<Property, Object> result = new HashMap<>(properties.size());

                for (final Property property : properties) {
                    
                    Object value = propertyAdapter.getValue(property);
                    result.put(property, value);

                }
                
                return result;
            }
            
        }
        
        return null;
    }

    @Override
    public Class<? extends Element> getGraphElementType(Definition definition) {

        Class<? extends Element> result = null;

        if ( null != definition ) {
            org.wirez.core.api.annotation.graph.Graph annotation = definition.getClass().getAnnotation(org.wirez.core.api.annotation.graph.Graph.class);
            result = annotation.type();
        }
        
        return result;
    }

    @Override
    public ElementFactory getElementFactory(Definition definition) {
       
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
}
