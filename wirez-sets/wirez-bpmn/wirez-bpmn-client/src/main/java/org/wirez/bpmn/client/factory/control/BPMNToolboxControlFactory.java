package org.wirez.bpmn.client.factory.control;

import org.wirez.bpmn.client.TaskShape;
import org.wirez.core.client.Shape;
import org.wirez.core.client.control.toolbox.ToolboxControl;
import org.wirez.core.client.control.toolbox.command.AddConnectionCommand;
import org.wirez.core.client.control.toolbox.command.NameToolboxCommand;
import org.wirez.core.client.control.toolbox.command.RemoveToolboxCommand;
import org.wirez.core.client.factory.control.ToolboxControlFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class BPMNToolboxControlFactory extends ToolboxControlFactory {

    AddConnectionCommand addConnectionCommand;
    SequenceFlowConnectionCommandCallback sequenceFlowConnectionCommandCallback;
    
    @Inject
    public BPMNToolboxControlFactory(final SequenceFlowConnectionCommandCallback sequenceFlowConnectionCommandCallback,
                                     final NameToolboxCommand nameToolboxCommand,
                                     final RemoveToolboxCommand removeToolboxCommand,
                                     final AddConnectionCommand addConnectionCommand,
                                     final ToolboxControl toolboxControl) {
        super(nameToolboxCommand, removeToolboxCommand, toolboxControl);
        this.sequenceFlowConnectionCommandCallback = sequenceFlowConnectionCommandCallback;
        this.addConnectionCommand = addConnectionCommand;
    }

    @PostConstruct
    public void init() {
        super.init();;
        addConnectionCommand.setCallback(sequenceFlowConnectionCommandCallback);
    }

    @Override
    public ToolboxControl build(final Shape shape) {
        defaults();
        if ( shape instanceof TaskShape ) {
            toolboxControl.addCommand(addConnectionCommand);
        }
        
        return toolboxControl;
    }
}
