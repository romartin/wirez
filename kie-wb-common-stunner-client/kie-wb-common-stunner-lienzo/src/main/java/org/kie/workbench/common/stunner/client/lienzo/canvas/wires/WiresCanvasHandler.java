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

package org.kie.workbench.common.stunner.client.lienzo.canvas.wires;

import org.kie.workbench.common.stunner.core.client.ShapeManager;
import org.kie.workbench.common.stunner.core.client.api.ClientDefinitionManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.event.registration.CanvasElementAddedEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.registration.CanvasElementRemovedEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.registration.CanvasElementUpdatedEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.registration.CanvasElementsClearEvent;
import org.kie.workbench.common.stunner.core.client.command.factory.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.service.ClientFactoryServices;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.graph.processing.index.IncrementalIndexBuilder;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;
import org.kie.workbench.common.stunner.core.rule.graph.GraphRulesManager;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class WiresCanvasHandler<D extends Diagram, C extends WiresCanvas> extends AbstractCanvasHandler<D, C> {

    @Inject
    public WiresCanvasHandler(final ClientDefinitionManager clientDefinitionManager,
                              final ClientFactoryServices clientFactoryServices,
                              final GraphRulesManager rulesManager,
                              final GraphUtils graphUtils,
                              final IncrementalIndexBuilder indexBuilder,
                              final ShapeManager shapeManager,
                              final Event<CanvasElementAddedEvent> canvasElementAddedEvent,
                              final Event<CanvasElementRemovedEvent> canvasElementRemovedEvent,
                              final Event<CanvasElementUpdatedEvent> canvasElementUpdatedEvent,
                              final Event<CanvasElementsClearEvent> canvasElementsClearEvent,
                              final CanvasCommandFactory canvasCommandFactory ) {

        super( clientDefinitionManager, clientFactoryServices, rulesManager, graphUtils,
                indexBuilder, shapeManager, canvasElementAddedEvent,
                canvasElementRemovedEvent, canvasElementUpdatedEvent, canvasElementsClearEvent, canvasCommandFactory );
        
    }

}
