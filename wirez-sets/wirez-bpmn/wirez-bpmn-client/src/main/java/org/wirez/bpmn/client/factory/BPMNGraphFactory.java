package org.wirez.bpmn.client.factory;

import org.wirez.bpmn.api.factory.BPMNAbstractGraphFactory;
import org.wirez.bpmn.api.factory.BPMNDefinitionFactory;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.rule.Empty;
import org.wirez.core.api.rule.EmptyRuleManager;
import org.wirez.core.api.rule.RuleManager;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named( BPMNAbstractGraphFactory.FACTORY_NAME )
public class BPMNGraphFactory extends BPMNAbstractGraphFactory {

    protected BPMNGraphFactory() {
    }

    @Inject
    public BPMNGraphFactory(FactoryManager factoryManager,
                            BPMNDefinitionFactory bpmnDefinitionBuilder,
                            GraphCommandManager graphCommandManager,
                            GraphCommandFactory graphCommandFactory,
                            @Empty RuleManager emptyRuleManager) {
        super(factoryManager, bpmnDefinitionBuilder, graphCommandManager, graphCommandFactory, emptyRuleManager);
    }

}
