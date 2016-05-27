package org.wirez.bpmn.definition.factory;

import org.wirez.bpmn.definition.BPMNDiagram;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.EmptyRulesCommandExecutionContext;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.GraphCommandManager;
import org.wirez.core.graph.command.factory.GraphCommandFactory;
import org.wirez.core.graph.content.definition.DefinitionSet;
import org.wirez.core.graph.content.definition.DefinitionSetImpl;
import org.wirez.core.graph.factory.BaseGraphFactory;
import org.wirez.core.graph.impl.GraphImpl;
import org.wirez.core.graph.store.GraphNodeStoreImpl;
import org.wirez.core.util.UUID;

import java.util.Set;

public abstract class BPMNAbstractGraphFactory extends BaseGraphFactory<DefinitionSet, Graph<DefinitionSet, Node>> {

    public static final transient String FACTORY_NAME = "bpmnGraphFactory";

    DefinitionManager definitionManager;
    FactoryManager factoryManager;
    GraphCommandManager graphCommandManager;
    GraphCommandFactory graphCommandFactory;

    protected BPMNAbstractGraphFactory() {
    }

    public BPMNAbstractGraphFactory(final DefinitionManager definitionManager,
                                    final FactoryManager factoryManager,
                                    final GraphCommandManager graphCommandManager,
                                    final GraphCommandFactory graphCommandFactory) {
        this.definitionManager = definitionManager;
        this.factoryManager = factoryManager;
        this.graphCommandManager = graphCommandManager;
        this.graphCommandFactory = graphCommandFactory;
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
        final BPMNDiagram diagram = new BPMNDiagram.BPMNDiagramBuilder().build();
        final String diagramId = definitionManager.getDefinitionAdapter(diagram.getClass()).getId( diagram );
        Node diagramNode = buildGraphElement( diagramId );
        
        graphCommandManager
                .batch( graphCommandFactory.ADD_NODE( graph, diagramNode ) )
                .batch( graphCommandFactory.UPDATE_POSITION( diagramNode, 30d, 30d) )
                .executeBatch( createGraphContext() );
        
        return graph;
        
    }
    
    private GraphCommandExecutionContext createGraphContext() {
        return new EmptyRulesCommandExecutionContext( 
                definitionManager, 
                factoryManager );
    }

}
