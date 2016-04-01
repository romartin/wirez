package org.wirez.bpmn.backend.factory;

import org.wirez.bpmn.api.factory.BPMNAbstractGraphFactory;
import org.wirez.bpmn.api.factory.BPMNDefinitionFactory;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named( BPMNAbstractGraphFactory.FACTORY_NAME )
public class BPMNGraphFactory extends BPMNAbstractGraphFactory {

    FactoryManager factoryManager;
    
    protected BPMNGraphFactory() {
        super();
    }

    @Override
    protected Node buildGraphElement(String id) {
        return (Node) factoryManager.element(UUID.uuid(), id );
    }

    @Inject
    public BPMNGraphFactory(FactoryManager factoryManager,
                            BPMNDefinitionFactory bpmnDefinitionBuilder, 
                            GraphCommandManager graphCommandManager,
                            GraphCommandFactory graphCommandFactory,
                            @Named( "empty" ) RuleManager emptyRuleManager) {
        super(bpmnDefinitionBuilder, graphCommandManager, graphCommandFactory, emptyRuleManager);
        this.factoryManager = factoryManager;
    }

}
