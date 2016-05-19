package org.wirez.core.client.canvas.controls.builder.impl;

import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.rule.model.ModelCardinalityRuleManager;
import org.wirez.core.rule.model.ModelContainmentRuleManager;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.controls.builder.ElementBuilderControl;
import org.wirez.core.client.canvas.controls.builder.request.ElementBuildRequest;
import org.wirez.core.client.canvas.controls.builder.request.ElementBuildRequestImpl;
import org.wirez.core.client.canvas.controls.event.BuildCanvasShapeEvent;
import org.wirez.core.client.canvas.event.processing.CanvasProcessingCompletedEvent;
import org.wirez.core.client.canvas.event.processing.CanvasProcessingStartedEvent;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.session.command.Session;
import org.wirez.core.client.shape.factory.ShapeFactory;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
@Observer
public class ObserverBuilderControl extends AbstractElementBuilderControl 
        implements ElementBuilderControl<AbstractCanvasHandler> {
    
    @Inject
    public ObserverBuilderControl(final ClientDefinitionManager clientDefinitionManager,
                                  final ClientFactoryServices clientFactoryServices,
                                  final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                  final GraphUtils graphUtils,
                                  final ModelContainmentRuleManager modelContainmentRuleManager,
                                  final ModelCardinalityRuleManager modelCardinalityRuleManager,
                                  final CanvasCommandFactory canvasCommandFactory,
                                  final GraphBoundsIndexer graphBoundsIndexer,
                                  final Event<CanvasProcessingStartedEvent> canvasProcessingStartedEvent,
                                  final Event<CanvasProcessingCompletedEvent> canvasProcessingCompletedEvent) {
        super( clientDefinitionManager, clientFactoryServices, canvasCommandManager, graphUtils, 
                modelContainmentRuleManager, modelCardinalityRuleManager, canvasCommandFactory, graphBoundsIndexer,
                canvasProcessingStartedEvent, canvasProcessingCompletedEvent );
    }

    void onBuildCanvasShape(@Observes BuildCanvasShapeEvent buildCanvasShapeEvent) {
        checkNotNull("buildCanvasShapeEvent", buildCanvasShapeEvent);

        if ( null != canvasHandler ) {
            final CanvasHandler context = buildCanvasShapeEvent.getCanvasHandler();

            if ( null != context && context.equals( context ) ) {
                final ShapeFactory factory = buildCanvasShapeEvent.getShapeFactory();
                final Object definition = buildCanvasShapeEvent.getDefinition();
                final double x = buildCanvasShapeEvent.getX();
                final double y = buildCanvasShapeEvent.getY();
                final double cx = canvasHandler.getCanvas().getAbsoluteX();
                final double cy = canvasHandler.getCanvas().getAbsoluteY();
                final ElementBuildRequest<AbstractCanvasHandler> request = 
                        new ElementBuildRequestImpl( x - cx, y - cy, definition, factory );
                ObserverBuilderControl.this.build(request);
                canvasHandler.getCanvas().draw();
            }
        }

    }
    
}
