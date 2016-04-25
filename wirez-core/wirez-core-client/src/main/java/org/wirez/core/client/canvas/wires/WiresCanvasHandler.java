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
import org.wirez.core.api.graph.processing.index.IncrementalIndexBuilder;
import org.wirez.core.api.graph.processing.traverse.tree.TreeWalkTraverseProcessor;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.RulesManager;
import org.wirez.core.api.rule.impl.graph.Graph;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.event.*;
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
                              final @Graph RulesManager<?, ?, ?, ?> rulesManager,
                              final GraphUtils graphUtils,
                              final IncrementalIndexBuilder indexBuilder,
                              final TreeWalkTraverseProcessor treeWalkTraverseProcessor, 
                              final ShapeManager shapeManager,
                              final Event<CanvasInitializationCompletedEvent> canvasInitializationCompletedEvent,
                              final Event<CanvasElementAddedEvent> canvasElementAddedEvent,
                              final Event<CanvasElementRemovedEvent> canvasElementRemovedEvent,
                              final Event<CanvasElementUpdatedEvent> canvasElementUpdatedEvent,
                              final Event<CanvasProcessingStartedEvent> canvasProcessingStartedEvent,
                              final Event<CanvasProcessingCompletedEvent> canvasProcessingCompletedEvent) {

        super( clientDefinitionManager, clientFactoryServices, rulesManager, graphUtils, 
                indexBuilder, treeWalkTraverseProcessor, shapeManager, canvasInitializationCompletedEvent,
                canvasElementAddedEvent, canvasElementRemovedEvent, canvasElementUpdatedEvent,
                canvasProcessingStartedEvent, canvasProcessingCompletedEvent );
        
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
