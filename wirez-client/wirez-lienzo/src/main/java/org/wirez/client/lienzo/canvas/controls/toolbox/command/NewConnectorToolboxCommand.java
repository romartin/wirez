package org.wirez.client.lienzo.canvas.controls.toolbox.command;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.client.lienzo.util.SVGUtils;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.EdgeBuilderControl;
import org.wirez.core.client.components.drag.ConnectorDragProxyFactory;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;

public abstract class NewConnectorToolboxCommand extends org.wirez.core.client.canvas.controls.toolbox.command.connector.NewConnectorCommand<Shape<?>> {


    public NewConnectorToolboxCommand(final ClientDefinitionManager clientDefinitionManager,
                                      final ClientFactoryServices clientFactoryServices,
                                      final ShapeManager shapeManager,
                                      final GraphBoundsIndexer graphBoundsIndexer,
                                      final ConnectorDragProxyFactory<AbstractCanvasHandler> connectorDragProxyFactory,
                                      final EdgeBuilderControl<AbstractCanvasHandler> edgeBuilderControl,
                                      final ShapeAnimation selectionAnimation,
                                      final ShapeDeSelectionAnimation deSelectionAnimation ) {
        super(clientDefinitionManager, clientFactoryServices, shapeManager, 
                graphBoundsIndexer, connectorDragProxyFactory, edgeBuilderControl,
                selectionAnimation, deSelectionAnimation, 
                SVGUtils.createSVGIcon(SVGUtils.getConnectorIcon()) );
    }
    
}
