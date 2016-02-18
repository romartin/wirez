package org.wirez.bpmn.client.factory.control;

import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.bpmn.api.factory.BPMNDefinitionFactory;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.content.ConnectionContent;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.command.impl.AddCanvasEdgeCommand;
import org.wirez.core.client.canvas.command.impl.CompositeElementCanvasCommand;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.control.toolbox.command.AddConnectionCommand;
import org.wirez.core.client.control.toolbox.command.Context;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.impl.BaseShape;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.util.WirezLogger;
import org.wirez.core.client.util.ShapeUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
public class SequenceFlowConnectionCommandCallback implements AddConnectionCommand.Callback {

    private static Logger LOGGER = Logger.getLogger("org.wirez.bpmn.client.factory.control.SequenceFlowConnectionCommandCallback");

    DefaultCanvasCommands defaultCanvasCommands;
    GraphCommandFactoryImpl graphCommandFactoryImpl;
    ClientDefinitionServices clientDefinitionServices;
    ShapeManager shapeManager;
    BPMNDefinitionFactory bpmnDefinitionFactory;

    private Element source;
    private Edge<ConnectionContent<SequenceFlow>, Node> edge;
    
    @Inject
    public SequenceFlowConnectionCommandCallback(final DefaultCanvasCommands defaultCanvasCommands,
                                                 final GraphCommandFactoryImpl graphCommandFactoryImpl,
                                                 final ClientDefinitionServices clientDefinitionServices,
                                                 final ShapeManager shapeManager,
                                                 final BPMNDefinitionFactory bpmnDefinitionFactory) {
        this.defaultCanvasCommands = defaultCanvasCommands;
        this.graphCommandFactoryImpl = graphCommandFactoryImpl;
        this.clientDefinitionServices = clientDefinitionServices;
        this.shapeManager = shapeManager;
        this.bpmnDefinitionFactory = bpmnDefinitionFactory;
    }

    @Override
    public void init(final Element element) {
        final SequenceFlow sequenceFlow = bpmnDefinitionFactory.buildSequenceFlow();
        clientDefinitionServices.buildGraphElement(UUID.uuid(), sequenceFlow, new ServiceCallback<Element>() {
            @Override
            public void onSuccess(final Element item) {
                SequenceFlowConnectionCommandCallback.this.source = element;
                SequenceFlowConnectionCommandCallback.this.edge = (Edge<ConnectionContent<SequenceFlow>, Node>) item;
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                log(Level.SEVERE, WirezLogger.getErrorMessage(error));
            }
        });
        
    }

    @Override
    public boolean isAllowed(final Context context, final Node target) {

        final CompositeElementCanvasCommand canvasCommand = defaultCanvasCommands.COMPOSITE_COMMAND(edge)
                .add ( defaultCanvasCommands.getCommandFactory().setConnectionTargetNodeCommand( (Node<? extends ViewContent<?>, Edge>) target, edge, 0 ) );

        return context.getCommandManager().allow( canvasCommand );
        
    }

    @Override
    public void accept(final Context context, final Node target) {
        log(Level.FINE, "AddConnectionCommandCallback - Connect from [" + source.getUUID() + "] to [" + target.getUUID() + "]");

        final Canvas canvas = context.getCanvasHandler().getCanvas();
        final BaseShape sourceShape = (BaseShape) canvas.getShape(source.getUUID());
        final BaseShape targetShape = (BaseShape) canvas.getShape(target.getUUID());
        final int[] magnetIndexes = ShapeUtils.getDefaultMagnetsIndex(sourceShape, targetShape);
        
        final ShapeFactory factory = shapeManager.getFactory(edge.getContent().getDefinition());

        final CompositeElementCanvasCommand connectionsCommand = defaultCanvasCommands.COMPOSITE_COMMAND(edge)
                .add( graphCommandFactoryImpl.setConnectionSourceNodeCommand( (Node<? extends ViewContent<?>, Edge>) source, edge, magnetIndexes[0] ) )
                .add( graphCommandFactoryImpl.setConnectionTargetNodeCommand( (Node<? extends ViewContent<?>, Edge>) target, edge, magnetIndexes[1] ) );


        final AddCanvasEdgeCommand addEdgeCommand = defaultCanvasCommands.ADD_EDGE( edge, factory);
        context.getCommandManager().execute( connectionsCommand, addEdgeCommand );
        
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
 