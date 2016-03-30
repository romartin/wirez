package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.bpmn.api.BPMNDiagram;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.GraphCommandManager;
import org.wirez.core.api.graph.command.factory.GraphCommandFactory;
import org.wirez.core.api.graph.content.view.*;
import org.wirez.core.api.graph.factory.BaseElementFactory;
import org.wirez.core.api.graph.factory.GraphFactory;
import org.wirez.core.api.graph.impl.GraphImpl;
import org.wirez.core.api.graph.store.GraphNodeStoreImpl;
import org.wirez.core.api.rule.RuleManager;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

public abstract class BPMNAbstractGraphFactory extends BaseElementFactory<BPMNDefinition, View<BPMNDefinition>, Graph<View<BPMNDefinition>, Node>>
        implements GraphFactory<BPMNDefinition> {

    public static final String FACTORY_NAME = "bpmnGraphFactory";

    BPMNDefinitionBuilder bpmnDefinitionBuilder;
    GraphCommandManager graphCommandManager;
    GraphCommandFactory graphCommandFactory;
    RuleManager emptyRuleManager;

    protected BPMNAbstractGraphFactory() {
    }

    @Inject
    public BPMNAbstractGraphFactory(BPMNDefinitionBuilder bpmnDefinitionBuilder,
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
    public Graph<View<BPMNDefinition>, Node> build(String uuid, BPMNDefinition definition, Set<Property> properties, Set<String> labels) {

        Graph<View<BPMNDefinition>, Node> graph =
                new GraphImpl<View<BPMNDefinition>>( uuid,
                        properties,
                        labels,
                        new ViewImpl<>( definition, buildBounds()),
                        new GraphNodeStoreImpl());

        // Add a BPMN diagram by default.
        BPMNDiagram diagram = bpmnDefinitionBuilder.buildBPMNDiagram();
        Node diagramNode = buildGraphElement( diagram.getId() );
        graphCommandManager.execute( emptyRuleManager, 
                graphCommandFactory.ADD_NODE( graph, diagramNode ),
                graphCommandFactory.UPDATE_POSITION( diagramNode, 30d, 30d) );
        
        return graph;
        
    }

    protected Bounds buildBounds() {
        return new BoundsImpl(new BoundImpl(1000d, 1000d), new BoundImpl(0d, 0d));
    }
    
}
