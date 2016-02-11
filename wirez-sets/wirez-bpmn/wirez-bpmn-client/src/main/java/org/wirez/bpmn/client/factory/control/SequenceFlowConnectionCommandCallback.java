package org.wirez.bpmn.client.factory.control;

import com.google.gwt.core.client.GWT;
import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.bpmn.api.factory.BPMNDefinitionFactory;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.commands.SharedGraphCommandFactory;
import org.wirez.core.api.graph.content.ConnectionContent;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.command.impl.AddCanvasEdgeCommand;
import org.wirez.core.client.canvas.command.impl.CompositeElementCanvasCommand;
import org.wirez.core.client.canvas.command.impl.DefaultCanvasCommands;
import org.wirez.core.client.control.toolbox.command.AddConnectionCommand;
import org.wirez.core.client.control.toolbox.command.Context;
import org.wirez.core.client.factory.ShapeFactory;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.util.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class SequenceFlowConnectionCommandCallback implements AddConnectionCommand.Callback {
    
    DefaultCanvasCommands defaultCanvasCommands;
    SharedGraphCommandFactory sharedGraphCommandFactory;
    ClientDefinitionServices clientDefinitionServices;
    ShapeManager shapeManager;
    BPMNDefinitionFactory bpmnDefinitionFactory;

    @Inject
    public SequenceFlowConnectionCommandCallback(final DefaultCanvasCommands defaultCanvasCommands,
                                                 final SharedGraphCommandFactory sharedGraphCommandFactory,
                                                 final ClientDefinitionServices clientDefinitionServices,
                                                 final ShapeManager shapeManager,
                                                 final BPMNDefinitionFactory bpmnDefinitionFactory) {
        this.defaultCanvasCommands = defaultCanvasCommands;
        this.sharedGraphCommandFactory = sharedGraphCommandFactory;
        this.clientDefinitionServices = clientDefinitionServices;
        this.shapeManager = shapeManager;
        this.bpmnDefinitionFactory = bpmnDefinitionFactory;
    }

    @Override
    public void onNodeClick(final Context context, final Element source, final Node target) {
        GWT.log("AddConnectionCommandCallback - Connect from [" + source.getUUID() + "] to [" + target.getUUID() + "]");

        final SequenceFlow sequenceFlow = bpmnDefinitionFactory.buildSequenceFlow();
        clientDefinitionServices.buildGraphElement(sequenceFlow, new ServiceCallback<Element>() {
            @Override
            public void onSuccess(final Element item) {
                final Edge<ConnectionContent<SequenceFlow>, Node> edge = (Edge<ConnectionContent<SequenceFlow>, Node>) item;
                final ShapeFactory factory = shapeManager.getFactory(edge.getContent().getDefinition());

                final CompositeElementCanvasCommand connectionsCommand = defaultCanvasCommands.COMPOSITE_COMMAND(edge)
                        .add( sharedGraphCommandFactory.setConnectionSourceNodeCommand( (Node<? extends ViewContent<?>, Edge>) source, edge, 0) )
                        .add( sharedGraphCommandFactory.setConnectionTargetNodeCommand((Node<? extends ViewContent<?>, Edge>) target, edge, 0) );


                final AddCanvasEdgeCommand addEdgeCommand = defaultCanvasCommands.ADD_EDGE( edge, factory);
                
                context.getCommandManager().execute( connectionsCommand, addEdgeCommand );
                
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                Logger.logError(error);
            }
        });
        
        
    }
    
}
 