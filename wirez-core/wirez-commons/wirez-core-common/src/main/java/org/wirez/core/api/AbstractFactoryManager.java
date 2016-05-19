package org.wirez.core.api;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.adapter.DefinitionSetAdapter;
import org.wirez.core.definition.factory.ModelFactory;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.factory.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class AbstractFactoryManager implements FactoryManager {

    protected DefinitionManager definitionManager;
    protected final List<ModelFactory> modelFactories = new LinkedList<>();

    private AbstractFactoryManager() {
    }

    public AbstractFactoryManager(DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    @Override
    public <W> W newDomainObject(String id) {
        ModelFactory modelFactory = getModelFactory( id );
        return modelFactory != null ? (W) modelFactory.build(id) : null;
    }

    @Override
    public <W extends Graph> W newGraph(String uuid, String definitionSetId ) {

        Object definitionSet = definitionManager.getDefinitionSet( definitionSetId );
        if ( null == definitionSet ) {
            throw new RuntimeException("No DefinitionSet found for id [" + definitionSetId + "]");
        }
        
        DefinitionSetAdapter definitionSetAdapter = definitionManager.getDefinitionSetAdapter( definitionSet.getClass() );
        Class<?> graphElementClass = definitionSetAdapter.getGraph( definitionSet );
        String factory = definitionSetAdapter.getGraphFactory( definitionSet );
        GraphFactory elementFactory = getGraphFactory( definitionSet, graphElementClass, factory );
        
        // Check no factory found.
        if ( null == elementFactory ) {
            return null;
        }
        
        return (W) elementFactory.build(uuid, definitionSetId, new HashSet<>());
    }

    @Override
    public <W extends Element> W newElement(String uuid, String id ) {
        
        Object definition = this.newDomainObject( id );
        
        if ( null == definition ) {
            throw new RuntimeException("No factory for Definition with id [" + id + "]");
        }
        
        DefinitionAdapter definitionAdapter = definitionManager.getDefinitionAdapter( definition.getClass() );

        Class<?> graphElementClass = definitionAdapter.getGraphElement( definition );
        String factory = definitionAdapter.getElementFactory( definition );
        ElementFactory elementFactory = getElementFactory( definition, graphElementClass, factory );

        // Check no factory found.
        if ( null == elementFactory ) {
            return null;
        }
        
        // Graph element's labels.
        Set<?> labels = definitionAdapter.getLabels( definition );
        
        return (W) elementFactory.build(uuid, definition, labels);
    }

    protected abstract ElementFactory getElementFactory(Object definition,
                                                        Class<?> graphElementClass,
                                                        String factory);

    protected abstract GraphFactory getGraphFactory(Object definition,
                                                    Class<?> graphElementClass,
                                                    String factory);

    protected String getFactoryReference(Class<?> graphElementClass,
                                         String factory) {
        String ref = graphElementClass.getName();
        if ( factory != null && factory.trim().length() > 0 ) {
            ref = factory;
        } else if (graphElementClass.equals(Graph.class)) {
            ref = GraphFactoryImpl.FACTORY_NAME;
        } else if (graphElementClass.equals(Node.class)) {
            ref = ViewNodeFactoryImpl.FACTORY_NAME;
        } else if (graphElementClass.equals(Edge.class)) {
            ref = ConnectionEdgeFactoryImpl.FACTORY_NAME;
        }
        
        return ref;
    }
    
    protected ModelFactory getModelFactory(final String id) {
        for (final ModelFactory builder : modelFactories) {
            if ( builder.accepts( id ) ) {
                return builder;
            }
        }

        return null;
    }
    
}
