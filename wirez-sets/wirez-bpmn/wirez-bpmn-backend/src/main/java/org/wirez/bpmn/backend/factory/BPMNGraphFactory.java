package org.wirez.bpmn.backend.factory;

import org.wirez.bpmn.api.factory.BPMNAbstractGraphFactory;
import org.wirez.bpmn.api.factory.BPMNDefinitionBuilder;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.api.service.definition.DefinitionService;
import org.wirez.core.api.util.UUID;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named( BPMNAbstractGraphFactory.FACTORY_NAME )
public class BPMNGraphFactory extends BPMNAbstractGraphFactory {

    DefinitionService definitionService;
    
    protected BPMNGraphFactory() {
        super();
    }

    @Override
    protected Node buildGraphElement(String id) {
        return (Node) definitionService.buildGraphElement(UUID.uuid(), id );
    }

    @Inject
    public BPMNGraphFactory(DefinitionService definitionService,
                            BPMNDefinitionBuilder bpmnDefinitionBuilder, 
                            GraphCommandManager graphCommandManager,
                            GraphCommandFactory graphCommandFactory,
                            @Named( "empty" ) RuleManager emptyRuleManager) {
        super(bpmnDefinitionBuilder, graphCommandManager, graphCommandFactory, emptyRuleManager);
        this.definitionService = definitionService;
    }

}
