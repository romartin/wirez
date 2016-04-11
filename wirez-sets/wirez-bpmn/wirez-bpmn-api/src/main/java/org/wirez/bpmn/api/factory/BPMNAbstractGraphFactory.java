package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.definition.DefinitionSet;
import org.wirez.core.api.graph.content.definition.DefinitionSetImpl;
import org.wirez.core.api.graph.factory.BaseElementFactory;
import org.wirez.core.api.graph.factory.BaseGraphFactory;
import org.wirez.core.api.graph.factory.GraphFactory;
import org.wirez.core.api.graph.impl.GraphImpl;
import org.wirez.core.api.graph.store.GraphNodeStoreImpl;
import org.wirez.core.api.rule.RuleManager;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

public abstract class BPMNAbstractGraphFactory extends BaseGraphFactory<DefinitionSet, Graph<DefinitionSet, Node>> {

    public static final transient String FACTORY_NAME = "bpmnGraphFactory";

    BPMNDefinitionFactory bpmnDefinitionBuilder;
    GraphCommandManager graphCommandManager;
    GraphCommandFactory graphCommandFactory;
    RuleManager emptyRuleManager;

    protected BPMNAbstractGraphFactory() {
    }

    @Inject
    public BPMNAbstractGraphFactory(BPMNDefinitionFactory bpmnDefinitionBuilder,
                                    GraphCommandManager graphCommandManager,
                                    GraphCommandFactory graphCommandFactory,
                                    @Named( "empty" ) RuleManager emptyRuleManager) {
        this.bpmnDefinitionBuilder = bpmnDefinitionBuilder;
        this.graphCommandManager = graphCommandManager;
        this.graphCommandFactory = graphCommandFactory;
        this.emptyRuleManager = emptyRuleManager;
    }
    
    protected abstract Node buildGraphElement( String id );

    @Override
    public Graph<DefinitionSet, Node> build(String uuid, String definitionSetId , Set<String> labels) {

        Graph<DefinitionSet, Node> graph =
                new GraphImpl<DefinitionSet>( uuid,
                        labels,
                        new DefinitionSetImpl( definitionSetId ),
                        new GraphNodeStoreImpl());

        // Add a BPMN diagram by default.
        BPMNDiagram diagram = bpmnDefinitionBuilder.buildBPMNDiagram();
        Node diagramNode = buildGraphElement( diagram.getClass().getSimpleName() );
        graphCommandManager.execute( emptyRuleManager, 
                graphCommandFactory.ADD_NODE( graph, diagramNode ),
                graphCommandFactory.UPDATE_POSITION( diagramNode, 30d, 30d) );
        
        return graph;
        
    }

}
