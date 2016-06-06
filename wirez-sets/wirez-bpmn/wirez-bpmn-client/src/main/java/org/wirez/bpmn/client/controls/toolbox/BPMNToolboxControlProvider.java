package org.wirez.bpmn.client.controls.toolbox;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.bpmn.client.controls.toolbox.command.NewNodeCommand;
import org.wirez.bpmn.client.controls.toolbox.command.NewSequenceFlowCommand;
import org.wirez.bpmn.definition.BPMNDefinition;
import org.wirez.bpmn.definition.ParallelGateway;
import org.wirez.bpmn.definition.StartNoneEvent;
import org.wirez.bpmn.definition.BaseTask;
import org.wirez.core.client.canvas.controls.toolbox.ToolboxControlProvider;
import org.wirez.core.client.canvas.controls.toolbox.command.ToolboxCommand;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@Dependent
public class BPMNToolboxControlProvider implements ToolboxControlProvider<BPMNDefinition, Shape<?>> {

    NewSequenceFlowCommand newSequenceFlowCommand;
    NewNodeCommand newSeqFlowNodeCommand;
    
    @Inject
    public BPMNToolboxControlProvider(final NewSequenceFlowCommand newSequenceFlowCommand,
                                      final NewNodeCommand newSeqFlowNodeCommand) {
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

            final List<ToolboxCommand<?, Shape<?>>> result = new LinkedList<ToolboxCommand<?, Shape<?>>>() {{
                add( newSequenceFlowCommand );
                add( newSeqFlowNodeCommand );
            }};
            
            return result;
            
        }
        
        return null;
    }

    protected boolean supportsAddConnectionCommand(final BPMNDefinition definition) {
        return ( definition instanceof StartNoneEvent
                || definition instanceof BaseTask
                || definition instanceof ParallelGateway);
    }
    
}
