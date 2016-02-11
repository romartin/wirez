package org.wirez.bpmn.client.factory.control;

import org.wirez.bpmn.client.BPMNBasicShape;
import org.wirez.bpmn.client.ParallelGatewayShape;
import org.wirez.bpmn.client.StartNoneEventShape;
import org.wirez.bpmn.client.TaskShape;
import org.wirez.core.client.Shape;
import org.wirez.core.client.control.toolbox.ToolboxControl;
import org.wirez.core.client.control.toolbox.command.AddConnectionCommand;
import org.wirez.core.client.control.toolbox.command.NameToolboxCommand;
import org.wirez.core.client.control.toolbox.command.RemoveToolboxCommand;
import org.wirez.core.client.factory.control.ShapeControlFactory;
import org.wirez.core.client.factory.control.ToolboxControlFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@Dependent
public class BPMNToolboxControlFactory implements ShapeControlFactory<Shape, ToolboxControl> {

    NameToolboxCommand nameToolboxCommand;
    RemoveToolboxCommand removeToolboxCommand;
    AddConnectionCommand addConnectionCommand;
    SequenceFlowConnectionCommandCallback sequenceFlowConnectionCommandCallback;
    Instance<ToolboxControl> toolboxControlInstances;
    
    @Inject
    public BPMNToolboxControlFactory(final SequenceFlowConnectionCommandCallback sequenceFlowConnectionCommandCallback,
                                     final NameToolboxCommand nameToolboxCommand,
                                     final RemoveToolboxCommand removeToolboxCommand,
                                     final AddConnectionCommand addConnectionCommand,
                                     final Instance<ToolboxControl> toolboxControlInstances) {
        this.nameToolboxCommand = nameToolboxCommand;
        this.removeToolboxCommand = removeToolboxCommand;
        this.sequenceFlowConnectionCommandCallback = sequenceFlowConnectionCommandCallback;
        this.addConnectionCommand = addConnectionCommand;
        this.toolboxControlInstances = toolboxControlInstances;
    }

    @PostConstruct
    public void init() {
        addConnectionCommand.setCallback(sequenceFlowConnectionCommandCallback);
    }

    @Override
    public ToolboxControl build(final Shape shape) {
        final ToolboxControl toolboxControl = toolboxControlInstances.get();
        defaults(toolboxControl);
        if ( acceptAddConnectionCommand(shape) ) {
            toolboxControl.addCommand(addConnectionCommand);
        }
        
        return toolboxControl;
    }

    protected void defaults(ToolboxControl toolboxControl) {
        toolboxControl.addCommand(nameToolboxCommand);
        toolboxControl.addCommand(removeToolboxCommand);
    }
    
    protected boolean acceptAddConnectionCommand(final Shape shape) {
        return ( shape instanceof StartNoneEventShape 
                || shape instanceof TaskShape 
                || shape instanceof ParallelGatewayShape ); 
    }
}
