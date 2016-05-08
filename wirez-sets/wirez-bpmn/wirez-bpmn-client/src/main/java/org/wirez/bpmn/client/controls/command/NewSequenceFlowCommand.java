package org.wirez.bpmn.client.controls.command;

import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.api.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.api.lookup.util.CommonLookups;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.Deselect;
import org.wirez.core.client.animation.Select;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.EdgeBuilderControl;
import org.wirez.core.client.canvas.controls.toolbox.command.connector.NewConnectorCommand;
import org.wirez.core.client.components.drag.ConnectorDragProxyFactory;
import org.wirez.core.client.service.ClientFactoryServices;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class NewSequenceFlowCommand extends org.wirez.client.lienzo.canvas.controls.toolbox.command.NewConnectorCommand {
    
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
