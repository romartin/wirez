package org.wirez.bpmn.client.factory;

import org.wirez.bpmn.api.factory.BPMNAbstractGraphFactory;
import org.wirez.bpmn.api.factory.BPMNDefinitionFactory;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named( BPMNAbstractGraphFactory.FACTORY_NAME )
public class BPMNGraphFactory extends BPMNAbstractGraphFactory {

    protected BPMNGraphFactory() {
    }

    @Inject
    public BPMNGraphFactory(final DefinitionManager definitionManager,
                            final FactoryManager factoryManager,
                            final BPMNDefinitionFactory bpmnDefinitionBuilder,
                            final GraphCommandManager graphCommandManager,
                            final GraphCommandFactory graphCommandFactory) {
        super(definitionManager, factoryManager, bpmnDefinitionBuilder,
                graphCommandManager, graphCommandFactory );
    }

}
