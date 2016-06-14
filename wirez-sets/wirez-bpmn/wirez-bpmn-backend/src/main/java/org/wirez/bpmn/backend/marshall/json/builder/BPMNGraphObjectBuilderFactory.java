package org.wirez.bpmn.backend.marshall.json.builder;

import org.wirez.core.backend.definition.adapter.annotation.RuntimeDefinitionAdapter;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.BindableMorphAdapter;
import org.wirez.core.definition.adapter.MorphAdapter;
import org.wirez.core.definition.morph.BindablePropertyMorphDefinition;
import org.wirez.core.definition.morph.MorphDefinition;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;

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
        this.definitionManager = definitionManager;
        this.oryxManager = oryxManager;
    }

    public BPMNGraphObjectBuilderFactory() {
    }

    public GraphObjectBuilder<?, ?> bootstrapBuilder() {
        return new BootstrapObjectBuilder( this );
    }
    
    @SuppressWarnings("unchecked")
    public GraphObjectBuilder<?, ?> builderFor( String oryxId ) {
        
        if ( oryxId == null ) {
            throw new NullPointerException();
        }
        
        Class<?> defClass = oryxManager.getMappingsManager().getDefinition( oryxId );

        if ( null != defClass ) {

            MorphAdapter<Object> morphAdapter = definitionManager.getMorphAdapter( defClass );

            BindablePropertyMorphDefinition propertyMorphDefinition = null;

            if ( morphAdapter != null ) {

                final Iterable<MorphDefinition> morphDefinitions =
                        ( (BindableMorphAdapter<Object>) morphAdapter).getMorphDefinitionsForType( defClass );

                if ( null != morphDefinitions && morphDefinitions.iterator().hasNext() ) {
                    for ( MorphDefinition morphDefinition : morphDefinitions ) {
                        if ( morphDefinition instanceof BindablePropertyMorphDefinition ) {
                            propertyMorphDefinition = (BindablePropertyMorphDefinition) morphDefinition;
                            break;
                        }
                    }
                }


            }

            if ( null != propertyMorphDefinition ) {

                // Specific handle for morphing based on class inheritance.
                return new NodePropertyMorphBuilderImpl( defClass, propertyMorphDefinition );
                
            } else {

                Class<? extends Element> element = RuntimeDefinitionAdapter.getGraphElement( defClass );

                if ( element.isAssignableFrom( Node.class ) ) {

                    return new NodeBuilderImpl( defClass );

                } else if ( element.isAssignableFrom( Edge.class ) ) {

                    return new EdgeBuilderImpl( defClass );

                } else {

                    throw new RuntimeException("No graph element found for definition with class [" + defClass.getName() + "]");

                }
                
            }
            
        } 

        throw new RuntimeException("No definition found for oryx stencil with id [" + oryxId + "]");

    }

}
