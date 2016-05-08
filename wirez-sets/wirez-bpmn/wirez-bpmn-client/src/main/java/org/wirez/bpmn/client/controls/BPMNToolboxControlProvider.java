package org.wirez.bpmn.client.controls;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.bpmn.api.ParallelGateway;
import org.wirez.bpmn.api.StartNoneEvent;
import org.wirez.bpmn.api.Task;
import org.wirez.bpmn.client.controls.command.NewSeqFlowNodeCommand;
import org.wirez.bpmn.client.controls.command.NewSequenceFlowCommand;
import org.wirez.core.client.canvas.controls.toolbox.ToolboxControlProvider;
import org.wirez.core.client.canvas.controls.toolbox.command.ToolboxCommand;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@Dependent
public class BPMNToolboxControlProvider implements ToolboxControlProvider<BPMNDefinition, Shape<?>> {

    NewSequenceFlowCommand newSequenceFlowCommand;
    NewSeqFlowNodeCommand newSeqFlowNodeCommand;
    
    @Inject
    public BPMNToolboxControlProvider(final NewSequenceFlowCommand newSequenceFlowCommand,
                                      final NewSeqFlowNodeCommand newSeqFlowNodeCommand) {
        this.newSequenceFlowCommand = newSequenceFlowCommand;
        this.newSeqFlowNodeCommand = newSeqFlowNodeCommand;
    }

    @Override
    public boolean supports(final Object definition) {
        return definition instanceof BPMNDefinition;
    }

    @Override
    public List<ToolboxCommand<?, Shape<?>>> getCommands(final BPMNDefinition item) {
        
        if ( supportsAddConnectionCommand( item ) ) {
            
            return new LinkedList<ToolboxCommand<?, Shape<?>>>() {{
                add( newSequenceFlowCommand );
                add( newSeqFlowNodeCommand );
            }};
            
        }
        
        return null;
    }

    protected boolean supportsAddConnectionCommand(final BPMNDefinition definition) {
        return ( definition instanceof StartNoneEvent
                || definition instanceof Task
                || definition instanceof ParallelGateway);
    }
    
}
