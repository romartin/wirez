package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.backend.definition.adapter.AnnotatedDefinitionAdapter;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Node;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BPMNGraphObjectBuilderFactory {

    DefinitionManager definitionManager;
    Bpmn2OryxManager oryxManager;
    
    @Inject
    public BPMNGraphObjectBuilderFactory(DefinitionManager definitionManager,
                                         Bpmn2OryxManager oryxManager) {
        this.definitionManager = definitionManager;
        this.oryxManager = oryxManager;
    }

    public BPMNGraphObjectBuilderFactory() {
    }

    public GraphObjectBuilder<?, ?> bootstrapBuilder() {
        return new BootstrapObjectBuilder( this );
    }
    
    public GraphObjectBuilder<?, ?> builderFor(String oryxId) {
        if ( oryxId == null) throw new NullPointerException();
        
        Class<?> defClass = oryxManager.getMappingsManager().getDefinition( oryxId );
        if ( null == defClass ) {
            throw new RuntimeException("No definition found for oryx stencil with id [" + oryxId + "]");
        }

        Class<? extends Element> element = AnnotatedDefinitionAdapter.getGraphElement( defClass );
        if ( element.isAssignableFrom(Node.class) ) {
            return new NodeBuilderImpl( defClass );
        } else if ( element.isAssignableFrom(Edge.class) ) {
            return new EdgeBuilderImpl( defClass );
        }

        throw new RuntimeException("No graph element found for definition with class [" + defClass.getName() + "]");
    }

}
