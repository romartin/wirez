package org.wirez.core.api;

import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.definition.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.factory.ModelFactory;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.factory.*;
import org.wirez.core.api.util.ElementUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseFactoryManager implements FactoryManager {

    protected DefinitionManager definitionManager;
    protected final List<ModelFactory> modelFactories = new ArrayList<ModelFactory>();

    private BaseFactoryManager() {
    }

    public BaseFactoryManager(DefinitionManager definitionManager) {
        this.definitionManager = definitionManager;
    }

    @Override
    public <W> W model(String id) {
        ModelFactory modelFactory = getModelFactory( id );
        return modelFactory != null ? (W) modelFactory.build(id) : null;
    }

    @Override
    public <W extends Graph> W graph( String uuid, String definitionSetId ) {

        DefinitionSet definitionSet = definitionManager.getDefinitionSet( definitionSetId );
        if ( null == definitionSet ) {
            throw new RuntimeException("No DefinitionSet found for id [" + definitionSetId + "]");
        }
        
        DefinitionSetAdapter definitionSetAdapter = definitionManager.getDefinitionSetAdapter( definitionSet.getClass() );
        Class<?> graphElementClass = definitionSetAdapter.getGraph( definitionSet );
        String factory = definitionSetAdapter.getGraphFactory( definitionSet );
        ElementFactory elementFactory = getElementFactory( definitionSet, graphElementClass, factory );
        Set<Object> properties = new HashSet<>();
        Set<String> labels = new HashSet<>();

        return (W) elementFactory.build(uuid, definitionSetId, properties, labels);
    }

    @Override
    public <W extends Element> W element(String uuid, String id ) {
        
        Object definition = this.model( id );
        
        if ( null == definition ) {
            throw new RuntimeException("No factory for Definition with id [" + id + "]");
        }
        
        DefinitionAdapter definitionAdapter = definitionManager.getDefinitionAdapter( definition.getClass() );

        Class<?> graphElementClass = definitionAdapter.getGraphElement( definition );
        String factory = definitionAdapter.getElementFactory( definition );
        ElementFactory elementFactory = getElementFactory( definition, graphElementClass, factory );

        // Cache on element properties the annotated Property's on the definition.
        Set<?> properties = ElementUtils.getAllProperties( definitionManager, definition );
        
        // Graph element's labels.
        Set<?> labels = definitionAdapter.getLabels( definition );
        
        return (W) elementFactory.build(uuid, definition, properties, labels);
    }

    protected abstract ElementFactory getElementFactory(Object definition,
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
