package org.wirez.bpmn.factory;

import org.wirez.bpmn.definition.BPMNDiagram;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.factory.graph.ElementFactory;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.command.EmptyRulesCommandExecutionContext;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.graph.command.GraphCommandManager;
import org.wirez.core.graph.command.factory.GraphCommandFactory;
import org.wirez.core.graph.content.definition.DefinitionSet;
import org.wirez.core.graph.content.definition.DefinitionSetImpl;
import org.wirez.core.graph.impl.GraphImpl;
import org.wirez.core.graph.store.GraphNodeStoreImpl;
import org.wirez.core.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BPMNGraphFactoryImpl implements BPMNGraphFactory {

    private final DefinitionManager definitionManager;
    private final GraphCommandManager graphCommandManager;
    private final GraphCommandFactory graphCommandFactory;
    private final FactoryManager factoryManager;

    protected BPMNGraphFactoryImpl() {
        this.factoryManager = null;
        this.graphCommandManager = null;
        this.graphCommandFactory = null;
        this.definitionManager = null;
    }

    @Inject
    public BPMNGraphFactoryImpl( final DefinitionManager definitionManager,
                                 final FactoryManager factoryManager,
                                 final GraphCommandManager graphCommandManager,
                                 final GraphCommandFactory graphCommandFactory ) {
        this.definitionManager = definitionManager;
        this.factoryManager = factoryManager;
        this.graphCommandManager = graphCommandManager;
        this.graphCommandFactory = graphCommandFactory;
    }

    @Override
    public Class<? extends ElementFactory> getFactoryType() {
        return BPMNGraphFactory.class;
    }

    @Override
    public Graph<DefinitionSet, Node> build( final String uuid ) {
        return build( uuid, null );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Graph<DefinitionSet, Node> build( final String uuid,
                                             final Object definitionSet ) {
        final GraphImpl graph = new GraphImpl<>( uuid, new GraphNodeStoreImpl() );
        final String id = getId( definitionSet );
        final DefinitionSet content = new DefinitionSetImpl( id );
        graph.setContent( content );

        // Add a BPMN diagram node by default.
        Node diagramNode = ( Node ) factoryManager.newElement( UUID.uuid(), BPMNDiagram.class );

        graphCommandManager
                .batch( graphCommandFactory.ADD_NODE( graph, diagramNode ) )
                .batch( graphCommandFactory.UPDATE_POSITION( diagramNode, 0d, 0d) )
                .executeBatch( createGraphContext() );

        return graph;
    }

    private GraphCommandExecutionContext createGraphContext() {
        return new EmptyRulesCommandExecutionContext(
                definitionManager,
                factoryManager );
    }

    private String getId( final Object defSet ) {
        return definitionManager.adapters().forDefinitionSet().getId( defSet );
    }

}
