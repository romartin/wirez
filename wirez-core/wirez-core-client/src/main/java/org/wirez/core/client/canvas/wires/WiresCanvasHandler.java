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

package org.wirez.core.client.canvas.wires;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.traverse.tree.TreeWalkTraverseProcessor;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.Default;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.event.CanvasElementAddedEvent;
import org.wirez.core.client.canvas.event.CanvasElementRemovedEvent;
import org.wirez.core.client.canvas.event.CanvasElementUpdatedEvent;
import org.wirez.core.client.canvas.event.CanvasInitializationCompletedEvent;
import org.wirez.core.client.service.ClientFactoryServices;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class WiresCanvasHandler<D extends Diagram, C extends WiresCanvas> extends AbstractCanvasHandler<D, C> {

    private static Logger LOGGER = Logger.getLogger(WiresCanvasHandler.class.getName());

    @Inject
    public WiresCanvasHandler(final ClientDefinitionManager clientDefinitionManager,
                              final ClientFactoryServices clientFactoryServices,
                              final @Default  RuleManager ruleManager,
                              final GraphCommandManager graphCommandManager,
                              final GraphCommandFactory graphCommandFactory,
                              final GraphUtils graphUtils,
                              final IncrementalIndexBuilder indexBuilder,
                              final CanvasCommandFactory commandFactory,
                              final TreeWalkTraverseProcessor treeWalkTraverseProcessor, 
                              final ShapeManager shapeManager,
                              final CanvasCommandManager<AbstractCanvasHandler> commandManager,
                              final Event<CanvasInitializationCompletedEvent> canvasInitializationCompletedEvent,
                              final Event<CanvasElementAddedEvent> canvasElementAddedEvent,
                              final Event<CanvasElementRemovedEvent> canvasElementRemovedEvent,
                              final Event<CanvasElementUpdatedEvent> canvasElementUpdatedEvent) {

        super( clientDefinitionManager, clientFactoryServices, ruleManager, graphCommandManager,
                graphCommandFactory, graphUtils, indexBuilder, commandFactory, treeWalkTraverseProcessor, 
                shapeManager, commandManager, canvasInitializationCompletedEvent,
                canvasElementAddedEvent, canvasElementRemovedEvent, canvasElementUpdatedEvent );
        
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
