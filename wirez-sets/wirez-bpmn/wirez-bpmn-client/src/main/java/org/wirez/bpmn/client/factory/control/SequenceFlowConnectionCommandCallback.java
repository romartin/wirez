package org.wirez.bpmn.client.factory.control;

import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.google.gwt.logging.client.LogConfiguration;
import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.bpmn.api.factory.BPMNDefinitionFactory;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.factory.GraphCommandFactoryImpl;
import org.wirez.core.api.graph.content.view.ViewConnector;
import org.wirez.core.api.util.UUID;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
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

    CanvasCommandFactory commandFactory;
    GraphCommandFactoryImpl graphCommandFactoryImpl;
    ClientDefinitionServices clientDefinitionServices;
    ShapeManager shapeManager;
    BPMNDefinitionFactory bpmnDefinitionFactory;

    private Element source;
    private Edge<ViewConnector<SequenceFlow>, Node> edge;
    
    @Inject
    public SequenceFlowConnectionCommandCallback(final CanvasCommandFactory commandFactory,
                                                 final GraphCommandFactoryImpl graphCommandFactoryImpl,
                                                 final ClientDefinitionServices clientDefinitionServices,
                                                 final ShapeManager shapeManager,
                                                 final BPMNDefinitionFactory bpmnDefinitionFactory) {
        this.commandFactory = commandFactory;
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
                SequenceFlowConnectionCommandCallback.this.edge = (Edge<ViewConnector<SequenceFlow>, Node>) item;
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                log(Level.SEVERE, WirezLogger.getErrorMessage(error));
            }
        });
        
    }

    @Override
    public boolean isAllowed(final Context context, final Node target) {

        final boolean allowsSourceConn = context.getCanvasHandler().allow( commandFactory.SET_SOURCE_NODE( (Node) source, edge, 0) );
        final boolean allowsTargetConn = context.getCanvasHandler().allow( commandFactory.SET_SOURCE_NODE( target, edge, 0) );
                
        final boolean isAllowed = allowsSourceConn & allowsTargetConn;
        log(Level.FINE, "Connection allowed from [" + source.getUUID() + "] to [" + target.getUUID() + "] = [" 
                + ( isAllowed ? "true" : "false" ) + "]");
        return isAllowed;
        
    }

    @Override
    public void accept(final Context context, final Node target) {

        final Canvas canvas = context.getCanvasHandler().getCanvas();
        final BaseShape sourceShape = (BaseShape) canvas.getShape(source.getUUID());
        final BaseShape targetShape = (BaseShape) canvas.getShape(target.getUUID());
        final int[] magnetIndexes = ShapeUtils.getDefaultMagnetsIndex( (WiresShape) sourceShape.getShapeView(),
                (WiresShape) targetShape.getShapeView());
        
        final ShapeFactory factory = shapeManager.getFactory(edge.getContent().getDefinition());

        final CommandResults<CanvasCommandViolation> results =
                context.getCanvasHandler().execute( commandFactory.ADD_EDGE( (Node) source, edge, factory),
                                            commandFactory.SET_SOURCE_NODE( (Node) source, edge, magnetIndexes[0]),
                                            commandFactory.SET_TARGET_NODE( target, edge, magnetIndexes[1]));

        // TODO: Check results.

        log(Level.FINE, "Connection performed from [" + source.getUUID() + "] to [" + target.getUUID() + "]");
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
 