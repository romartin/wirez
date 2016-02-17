package org.wirez.core.api.graph.command;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.adapter.PropertyAdapter;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.command.impl.*;
import org.wirez.core.api.graph.content.ParentChildRelationship;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.util.ElementUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GraphCommandFactoryImpl implements GraphCommandFactory {

    DefinitionManager definitionManager;

    public GraphCommandFactoryImpl() {
    }

    @Inject
    public GraphCommandFactoryImpl(final DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    @Override
    public AddChildNodeCommand addChildNodeCommand(final Graph target,
                                                   final Node parent,
                                                   final Node candidate) {
        return new AddChildNodeCommand(this, target, parent, candidate);
    }

    @Override
    public RemoveChildNodeCommand removeChildNodeCommand(final Graph target,
                                                         final Node oldParent,
                                                         final Node candidate) {
        return new RemoveChildNodeCommand(this, target, oldParent, candidate);
    }

    @Override
    public RemoveChildNodesCommand removeChildNodesCommand(final Graph target, final Node parent) {
        return new RemoveChildNodesCommand(this, target, parent);
    }

    @Override
    public AddEdgeCommand addEdgeCommand(final Node target, final Edge edge) {
        return new AddEdgeCommand(this, target, edge);
    }

    @Override
    public AddNodeCommand addNodeCommand(final Graph target,
                                         final Node candidate) {
        return new AddNodeCommand(this, target, candidate);
    }

    @Override
    public ClearGraphCommand clearGraphCommand(final Graph target) {
        return new ClearGraphCommand(this, target);
    }

    @Override
    public SetParentCommand setParentCommand(final Node parent, final Node candidate, final Edge<ParentChildRelationship, Node> edge) {
        return new SetParentCommand(this, parent, candidate, edge);
    }

    @Override
    public RemoveParentCommand removeParentCommand(final Node parent, final Node candidate) {
        return new RemoveParentCommand(this, parent, candidate);
    }

    @Override
    public DeleteEdgeCommand deleteEdgeCommand(final Edge<? extends ViewContent, Node> edge) {
        return new DeleteEdgeCommand(this, edge);
    }

    @Override
    public DeleteNodeCommand deleteNodeCommand(final Graph target,
                                               final Node candidate) {
        return new DeleteNodeCommand(this, target, candidate);
    }

    @Override
    public SetConnectionSourceNodeCommand setConnectionSourceNodeCommand(final Node<? extends ViewContent<?>, Edge> sourceNode,
                                                                         final Edge<? extends ViewContent<?>, Node> edge,
                                                                         final int magnetIndex) {
        return new SetConnectionSourceNodeCommand(this, sourceNode, edge, magnetIndex);
    }

    @Override
    public SetConnectionTargetNodeCommand setConnectionTargetNodeCommand(final Node<? extends ViewContent<?>, Edge> targetNode,
                                                                         final Edge<? extends ViewContent<?>, Node> edge,
                                                                         final int magnetIndex) {
        return new SetConnectionTargetNodeCommand(this, targetNode, edge, magnetIndex);
    }

    @Override
    public UpdateElementPositionCommand updateElementPositionCommand(final Element element,
                                                                     final Double x,
                                                                     final Double y) {
        return new UpdateElementPositionCommand(this, element, x ,y);
    }

    @Override
    public UpdateElementPropertyValueCommand updateElementPropertyValueCommand(final Element element,
                                                                               final String propertyId,
                                                                               final Object value) {
        final Property p = ElementUtils.getProperty(element, propertyId);
        PropertyAdapter adapter = getPropertyAdapter(p);
        return adapter != null ? new UpdateElementPropertyValueCommand(this, adapter, element, propertyId, value) : null;
    }
    
    
    protected PropertyAdapter getPropertyAdapter(final Property property) {
        return definitionManager.getPropertyAdapter(property);
    }
    
}
