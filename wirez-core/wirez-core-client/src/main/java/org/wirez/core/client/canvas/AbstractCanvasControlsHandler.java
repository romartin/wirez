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

package org.wirez.core.client.canvas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import org.wirez.core.api.definition.adapter.DefinitionSetRuleAdapter;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.traverse.tree.TreeWalkTraverseProcessor;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.Default;
import org.wirez.core.api.rule.Rule;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.listener.CanvasElementListener;
import org.wirez.core.client.control.ShapeControl;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.factory.control.HasShapeControlFactories;
import org.wirez.core.client.factory.control.ShapeControlFactory;
import org.wirez.core.client.mutation.HasGraphElementMutation;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides rule constrains and enables the different shape controls.
 */
@Dependent
public class AbstractCanvasControlsHandler<D extends Diagram, C extends AbstractCanvas> extends AbstractCanvasHandler<D, C, CanvasElementListener> {

    private static Logger LOGGER = Logger.getLogger(AbstractCanvasControlsHandler.class.getName());

    protected final Map<String, List<ShapeControl>> shapeControls = new HashMap<>();
    
    @Inject
    public AbstractCanvasControlsHandler(final ClientDefinitionManager clientDefinitionManager,
                                         final ClientFactoryServices clientFactoryServices,
                                         final @Default  RuleManager ruleManager,
                                         final GraphCommandManager graphCommandManager,
                                         final GraphCommandFactory graphCommandFactory,
                                         final GraphUtils graphUtils,
                                         final IncrementalIndexBuilder indexBuilder,
                                         final CanvasCommandFactory commandFactory,
                                         final TreeWalkTraverseProcessor treeWalkTraverseProcessor, 
                                         final ShapeManager shapeManager,
                                         final CanvasCommandManager<AbstractCanvasHandler> commandManager) {

        super( clientDefinitionManager, clientFactoryServices, ruleManager, graphCommandManager,
                graphCommandFactory, graphUtils, indexBuilder, commandFactory, treeWalkTraverseProcessor, 
                shapeManager, commandManager);

    }


    @Override
    protected void doInitialize() {

        // Load the rules that apply for the diagram.
        final String defSetId = getDiagram().getSettings().getDefinitionSetId();

        clientFactoryServices.newDomainObject( defSetId, new ServiceCallback<Object>() {
            @Override
            public void onSuccess(Object definitionSet) {

                // ***************** TODO ***********************
                // Create missing client adapters (annot. processing) - Definition, DefinitionSet, PropertySet
                
                DefinitionSetRuleAdapter adapter = clientDefinitionManager.getDefinitionSetRuleAdapter( definitionSet.getClass() );

                final Collection<Rule> rules = adapter.getRules( definitionSet );
                if (rules != null) {
                    for (final Rule rule : rules) {
                        ruleManager.addRule(rule);
                    }
                }

                afterLoadRules();
                AbstractCanvasControlsHandler.super.doInitialize();


            }

            @Override
            public void onError(ClientRuntimeError error) {
                GWT.log("Error");
            }
        });
        
    }
    
    protected void afterLoadRules() {
        
    }

    @Override
    protected void doRegister(final Shape shape, 
                              final Element element, 
                              final ShapeFactory factory) {
        super.doRegister(shape, element, factory);
        doAddShapeControls(shape, element, factory);
    }

    @Override
    protected void doDeregister(final Shape shape, 
                                final Element element) {
        super.doDeregister(shape, element);
        doRemoveShapeControls(shape);
    }

    protected void doAddShapeControls(final Shape shape,
                                      final Element element,
                                      final ShapeFactory factory) {
        
        // Shape controls.
        if (factory instanceof HasShapeControlFactories) {

            final Collection<ShapeControlFactory<?, ?>> factories = ((HasShapeControlFactories) factory).getFactories();
            for (ShapeControlFactory controlFactory : factories) {
                ShapeControl control = controlFactory.build(shape);
                addControl(shape, element, control);
            }
            
        }
        
    }
    
    protected void addControl(final Shape shape,
                              final Element element,
                              final ShapeControl control) {
        // Some controls needs to add elements on the DOM.
        if (control instanceof IsWidget) {
            final IsWidget controlWidget = (IsWidget) control;
            canvas.addControl(controlWidget);
        }

        // Enable the stateful control.
        control.enable(this, shape, element);
        
        // Controls cache.
        List<ShapeControl> controls = shapeControls.get( shape.getId() );
        if ( null == controls ) {
            controls = new LinkedList<>();
            shapeControls.put( shape.getId(), controls );
        }
        controls.add( control );
    }

    protected void doRemoveShapeControls(final Shape shape) {
        
        final String shapeId = shape.getId();
        final List<ShapeControl> controls = shapeControls.get( shapeId );
        if ( null != controls && !controls.isEmpty() ) {
            for ( final ShapeControl control : controls ) {
                
                // Some controls needs to remove elements from the DOM.
                if (control instanceof IsWidget) {
                    final IsWidget controlWidget = (IsWidget) control;
                    canvas.deleteControl(controlWidget);
                }

                // Enable the stateful control.
                control.disable(this, shape);
            }
            
            shapeControls.remove( shapeId );
        }
        
    }
    
    @Override
    protected void afterElementAdded(final Element element) {
        super.afterElementAdded(element);
        fireElementAdded(element);
    }

    @Override
    protected void afterElementDeleted(final Element element) {
        super.afterElementDeleted(element);
        fireElementDeleted(element);
    }

    @Override
    protected void afterElementUpdated(final Element element, 
                                       final HasGraphElementMutation elementMutation) {
        super.afterElementUpdated(element, elementMutation);
        fireElementUpdated(element);
    }

    protected void fireElementAdded(final Element element) {
        for (final CanvasElementListener listener : listeners) {
            listener.onElementAdded(element);
        }
    }

    protected void fireElementDeleted(final Element element) {
        for (final CanvasElementListener listener : listeners) {
            listener.onElementDeleted(element);
        }
    }

    protected void fireElementUpdated(final Element element) {
        for (final CanvasElementListener listener : listeners) {
            listener.onElementModified(element);
        }
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
