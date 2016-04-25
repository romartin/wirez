package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.definition.adapter.binding.AbstractBindableAdapter;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandExecutionContextImpl;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.definition.DefinitionSet;
import org.wirez.core.api.graph.content.definition.DefinitionSetImpl;
import org.wirez.core.api.graph.factory.BaseGraphFactory;
import org.wirez.core.api.graph.impl.GraphImpl;
import org.wirez.core.api.graph.store.GraphNodeStoreImpl;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.rule.RulesManager;
import org.wirez.core.api.util.UUID;

import javax.inject.Inject;
import java.util.Set;

public abstract class BPMNAbstractGraphFactory extends BaseGraphFactory<DefinitionSet, Graph<DefinitionSet, Node>> {

    public static final transient String FACTORY_NAME = "bpmnGraphFactory";

    DefinitionManager definitionManager;
    FactoryManager factoryManager;
    GraphUtils graphUtils;
    BPMNDefinitionFactory bpmnDefinitionBuilder;
    GraphCommandManager graphCommandManager;
    GraphCommandFactory graphCommandFactory;
    RulesManager<?, ?, ?, ?> emptyRulesManager;

    protected BPMNAbstractGraphFactory() {
    }

    public BPMNAbstractGraphFactory(final DefinitionManager definitionManager,
                                    final FactoryManager factoryManager,
                                    final BPMNDefinitionFactory bpmnDefinitionBuilder,
                                    final GraphCommandManager graphCommandManager,
                                    final GraphCommandFactory graphCommandFactory,
                                    final RulesManager<?, ?, ?, ?> emptyRulesManager) {
        this.definitionManager = definitionManager;
        this.factoryManager = factoryManager;
        this.bpmnDefinitionBuilder = bpmnDefinitionBuilder;
        this.graphCommandManager = graphCommandManager;
        this.graphCommandFactory = graphCommandFactory;
        this.emptyRulesManager = emptyRulesManager;
    }

    protected Node buildGraphElement(String id) {
        return (Node) factoryManager.newElement(UUID.uuid(), id );
    }

    @Override
    public Graph<DefinitionSet, Node> build(String uuid, String definitionSetId , Set<String> labels) {

        Graph<DefinitionSet, Node> graph =
                new GraphImpl<DefinitionSet>( uuid,
                        labels,
                        new DefinitionSetImpl( definitionSetId ),
                        new GraphNodeStoreImpl());

        // Add a BPMN diagram by default.
        final BPMNDiagram diagram = bpmnDefinitionBuilder.buildBPMNDiagram();
        final String diagramId = definitionManager.getDefinitionAdapter(diagram.getClass()).getId( diagram );
        Node diagramNode = buildGraphElement( diagramId );
        
        graphCommandManager
                .batch( graphCommandFactory.ADD_NODE( graph, diagramNode ) )
                .batch( graphCommandFactory.UPDATE_POSITION( diagramNode, 30d, 30d) )
                .executeBatch( createGraphContext() );
        
        return graph;
        
    }
    
    private GraphCommandExecutionContext createGraphContext() {
        return new GraphCommandExecutionContextImpl( 
                definitionManager, 
                factoryManager,
                emptyRulesManager,
                graphUtils );
    }

}
