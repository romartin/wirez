package org.wirez.core.client.canvas.controls.builder.impl;

import org.wirez.core.client.canvas.util.CanvasLayoutUtils;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.rule.model.ModelCardinalityRuleManager;
import org.wirez.core.rule.model.ModelContainmentRuleManager;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.event.processing.CanvasProcessingCompletedEvent;
import org.wirez.core.client.canvas.event.processing.CanvasProcessingStartedEvent;
import org.wirez.core.client.service.ClientFactoryServices;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Build element on the canvas, either a node or an edge.
 */
@Dependent
@Element
public class ElementBuilderControlImpl extends AbstractElementBuilderControl {

    private static Logger LOGGER = Logger.getLogger( ElementBuilderControlImpl.class.getName() );

    @Inject
    public ElementBuilderControlImpl( final ClientDefinitionManager clientDefinitionManager,
                                      final ClientFactoryServices clientFactoryServices,
                                      final CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                      final GraphUtils graphUtils,
                                      final ModelContainmentRuleManager modelContainmentRuleManager,
                                      final ModelCardinalityRuleManager modelCardinalityRuleManager,
                                      final CanvasCommandFactory canvasCommandFactory,
                                      final GraphBoundsIndexer graphBoundsIndexer,
                                      final Event<CanvasProcessingStartedEvent> canvasProcessingStartedEvent,
                                      final Event<CanvasProcessingCompletedEvent> canvasProcessingCompletedEvent,
                                      final CanvasLayoutUtils canvasLayoutUtils ) {
        super( clientDefinitionManager, clientFactoryServices, canvasCommandManager, graphUtils,
                modelContainmentRuleManager, modelCardinalityRuleManager, canvasCommandFactory, graphBoundsIndexer,
                canvasProcessingStartedEvent, canvasProcessingCompletedEvent, canvasLayoutUtils );

    }

}
