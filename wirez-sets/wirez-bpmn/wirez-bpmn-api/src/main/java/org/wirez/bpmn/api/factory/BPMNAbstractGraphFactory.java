package org.wirez.bpmn.api.factory;

import java.util.Set;

import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.EmptyRulesCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandExecutionContext;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.definition.DefinitionSet;
import org.wirez.core.api.graph.content.definition.DefinitionSetImpl;
import org.wirez.core.api.graph.factory.BaseGraphFactory;
import org.wirez.core.api.graph.impl.GraphImpl;
import org.wirez.core.api.graph.store.GraphNodeStoreImpl;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.api.util.UUID;

public abstract class BPMNAbstractGraphFactory extends BaseGraphFactory<DefinitionSet, Graph<DefinitionSet, Node>> {

    public static final transient String FACTORY_NAME = "bpmnGraphFactory";

    DefinitionManager definitionManager;
    FactoryManager factoryManager;
    GraphUtils graphUtils;
    BPMNDefinitionFactory bpmnDefinitionBuilder;
    GraphCommandManager graphCommandManager;
    GraphCommandFactory graphCommandFactory;

    protected BPMNAbstractGraphFactory() {
    }

    public BPMNAbstractGraphFactory(final DefinitionManager definitionManager,
                                    final FactoryManager factoryManager,
                                    final BPMNDefinitionFactory bpmnDefinitionBuilder,
                                    final GraphCommandManager graphCommandManager,
                                    final GraphCommandFactory graphCommandFactory) {
        this.definitionManager = definitionManager;
        this.factoryManager = factoryManager;
        this.bpmnDefinitionBuilder = bpmnDefinitionBuilder;
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
        return new EmptyRulesCommandExecutionContext(
                definitionManager,
                factoryManager,
                graphUtils );
    }

}
