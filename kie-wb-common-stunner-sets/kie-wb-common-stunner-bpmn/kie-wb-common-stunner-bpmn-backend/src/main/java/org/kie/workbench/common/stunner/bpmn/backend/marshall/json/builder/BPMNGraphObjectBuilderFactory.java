package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.builder;

import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.kie.workbench.common.stunner.core.backend.definition.adapter.annotation.RuntimeDefinitionAdapter;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.definition.adapter.BindableMorphAdapter;
import org.kie.workbench.common.stunner.core.definition.adapter.MorphAdapter;
import org.kie.workbench.common.stunner.core.definition.morph.BindablePropertyMorphDefinition;
import org.kie.workbench.common.stunner.core.definition.morph.MorphDefinition;
import org.kie.workbench.common.stunner.core.factory.graph.EdgeFactory;
import org.kie.workbench.common.stunner.core.factory.graph.ElementFactory;
import org.kie.workbench.common.stunner.core.factory.graph.NodeFactory;

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

            MorphAdapter<Object> morphAdapter = definitionManager.adapters().registry().getMorphAdapter( defClass );

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

                Class<? extends ElementFactory> elementFactory = RuntimeDefinitionAdapter.getGraphFactory( defClass );


                if ( isNodeFactory( elementFactory ) ) {

                    return new NodeBuilderImpl( defClass );

                } else if ( isEdgeFactory( elementFactory ) ) {

                    return new EdgeBuilderImpl( defClass );

                } else {

                    throw new RuntimeException("No graph element found for definition with class [" + defClass.getName() + "]");

                }
                
            }
            
        } 

        throw new RuntimeException("No definition found for oryx stencil with id [" + oryxId + "]");

    }

    private static boolean isNodeFactory( Class<? extends ElementFactory> elementFactory ) {

        return elementFactory.isAssignableFrom( NodeFactory.class );

    }

    private static boolean isEdgeFactory( Class<? extends ElementFactory> elementFactory ) {

        return elementFactory.isAssignableFrom( EdgeFactory.class );

    }

}
