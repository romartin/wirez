package org.wirez.bpmn.client.controls.toolbox.command;

import org.wirez.bpmn.definition.SequenceFlow;
import org.wirez.client.lienzo.canvas.controls.toolbox.command.NewConnectorToolboxCommand;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.Deselect;
import org.wirez.core.client.animation.Select;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.EdgeBuilderControl;
import org.wirez.core.client.components.drag.ConnectorDragProxyFactory;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class NewSequenceFlowCommand extends NewConnectorToolboxCommand {
    
    private static final String EDGE_ID = BindableAdapterUtils.getDefinitionId( SequenceFlow.class );

    @Inject
    public NewSequenceFlowCommand(final ClientDefinitionManager clientDefinitionManager,
                                  final ClientFactoryServices clientFactoryServices,
                                  final ShapeManager shapeManager,
                                  final GraphBoundsIndexer graphBoundsIndexer,
                                  final ConnectorDragProxyFactory<AbstractCanvasHandler> connectorDragProxyFactory,
                                  final EdgeBuilderControl<AbstractCanvasHandler> edgeBuilderControl,
                                  final @Select  ShapeAnimation selectionAnimation,
                                  final @Deselect  ShapeDeSelectionAnimation deSelectionAnimation) {
        super( clientDefinitionManager, clientFactoryServices, shapeManager, graphBoundsIndexer, 
                connectorDragProxyFactory, edgeBuilderControl,
                selectionAnimation, deSelectionAnimation );
    }

    @Override
    protected String getEdgeIdentifier() {
        return EDGE_ID;
    }
    
}
