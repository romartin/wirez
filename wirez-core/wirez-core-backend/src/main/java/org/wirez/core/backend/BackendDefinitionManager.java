/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.core.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.Content;
import org.wirez.core.api.definition.DefaultContent;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.factory.DefaultGraphFactory;
import org.wirez.core.api.graph.factory.EdgeFactory;
import org.wirez.core.api.graph.factory.ElementFactory;
import org.wirez.core.api.graph.factory.NodeFactory;
import org.wirez.core.api.graph.impl.DefaultGraph;
import org.wirez.core.backend.graph.factory.DefaultGraphFactoryImpl;
import org.wirez.core.backend.graph.factory.EdgeFactoryImpl;
import org.wirez.core.backend.graph.factory.NodeFactoryImpl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.*;

// TODO : Refactor instanceof clauses by adapter classes.
// TODO: Factories injection not working
@Dependent
public class BackendDefinitionManager implements DefinitionManager {

    private static final Logger LOG = LoggerFactory.getLogger(BackendDefinitionManager.class);

    Instance<DefinitionSet> definitionSetInstances;
    DefaultGraphFactory graphFactory;
    NodeFactory nodeFactory;
    EdgeFactory edgeFactory;
    
    private final List<DefinitionSet> definitionSets = new ArrayList<DefinitionSet>();

    @Inject
    public BackendDefinitionManager(Instance<DefinitionSet> definitionSetInstances) {
        this.definitionSetInstances = definitionSetInstances;
        this.graphFactory = new DefaultGraphFactoryImpl();
        this.nodeFactory = new NodeFactoryImpl();
        this.edgeFactory = new EdgeFactoryImpl();
    }

    @PostConstruct
    public void init() {
        initDefinitionSets();
    }

    private void initDefinitionSets() {
        for (DefinitionSet definitionSet : definitionSetInstances) {
            definitionSets.add(definitionSet);
        }
    }


    @Override
    public DefinitionSet getDefinitionSet(final String id) {
        if (null != id) {
            for (DefinitionSet definitionSet : definitionSets) {
                if (definitionSet.getId().equals(id)) {
                    return definitionSet;
                }
            }
        }
        return null;
    }

    @Override
    public Set<Property> getProperties(final Definition definition) {

        final Set<Property> properties = new HashSet<>();

        Content defContent = definition.getDefinitionContent();
        if ( defContent instanceof DefaultContent ) {
        
            final DefaultContent defaultContent = (DefaultContent) defContent;
            return defaultContent.getProperties();
            
        } else {

            Method[] methods = definition.getClass().getMethods();
            if ( null != methods ) {
                for (Method method : methods) {
                    org.wirez.core.api.annotation.definition.Property annotation = method.getAnnotation(org.wirez.core.api.annotation.definition.Property.class);
                    if ( null != annotation) {
                        try {
                            Property property = (Property) method.invoke(definition);
                            properties.add(property);
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated properties for Definition with id " + definition.getId());
                        }
                    }
                }
            }
        }
        
        return properties;
    }

    @Override
    public Collection<DefinitionSet> getDefinitionSets() {
        return definitionSets;
    }

    @Override
    public ElementFactory getFactory(final Definition definition) {

        if ( null != definition ) {
            Class<? extends Element> elementType = getGraphElementType(definition);

            if (elementType.equals(DefaultGraph.class)) {
                return graphFactory;
            } else if (elementType.equals(Node.class)) {
                return nodeFactory;
            } else if (elementType.equals(Node.class)) {
                return edgeFactory;
            }

        }

        return null;
    }

    @Override
    public Class<? extends Element> getGraphElementType(final Definition definition) {
        org.wirez.core.api.annotation.graph.Graph annotation = definition.getClass().getAnnotation(org.wirez.core.api.annotation.graph.Graph.class);
        return annotation.type();
    }
    
}

