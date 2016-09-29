package org.kie.workbench.common.stunner.backend.definition.factory;

import org.kie.workbench.common.stunner.core.backend.utils.BackendBindableDefinitionUtils;
import org.kie.workbench.common.stunner.core.definition.annotation.Definition;
import org.kie.workbench.common.stunner.core.definition.builder.Builder;
import org.kie.workbench.common.stunner.core.factory.definition.AbstractTypeDefinitionFactory;

import java.util.Set;

/**
 * Model factory for annotated modelsfor using  on test scope.
 */
public class TestScopeModelFactory extends AbstractTypeDefinitionFactory<Object> {

    private final Object definitionSet;

    public TestScopeModelFactory(Object definitionSet) {
        this.definitionSet = definitionSet;
    }
   
    private static Set<Class<? extends Object>> getDefinitions(Object defSet ) {
        return BackendBindableDefinitionUtils.getDefinitions( defSet );
    }

    @Override
    public Set<Class<? extends Object>> getAcceptedClasses() {
        return getDefinitions( this.definitionSet );
    }

    @Override
    public Object build(Class<? extends Object> clazz) {
        
        Builder<?> builder = newDefinitionBuilder( clazz );
        
        return builder.build();
    }

    private Builder<?> newDefinitionBuilder( Class<? extends Object> definitionClass ) {

        Class<? extends Builder<?>> builderClass = getDefinitionBuilderClass( definitionClass );
        
        if ( null != builderClass ) {

            try {
                return builderClass.newInstance();
                
            } catch (InstantiationException e) {
                
                e.printStackTrace();
                
            } catch (IllegalAccessException e) {
                
                e.printStackTrace();
                
            }
            
        }
        
        throw new RuntimeException( "No annotated builder found for Definition [" + definitionClass.getName() + "]" );
    }
    
    private Class<? extends Builder<?>> getDefinitionBuilderClass( Class<? extends Object> definitionClass ) {

        if ( null != definitionClass ) {

            Definition annotation = definitionClass.getAnnotation(Definition.class);
            
            if ( null != annotation ) {
                
                return annotation.builder();
                
            }
        }

        return null;
    }
    
}
