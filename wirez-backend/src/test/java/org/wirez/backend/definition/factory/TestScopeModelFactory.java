package org.wirez.backend.definition.factory;

import org.wirez.core.backend.definition.utils.BackendBindableDefinitionUtils;
import org.wirez.core.definition.annotation.definition.Definition;
import org.wirez.core.definition.factory.BindableModelFactory;
import org.wirez.core.definition.factory.Builder;
import org.wirez.core.graph.Element;

import java.util.Set;

/**
 * Model factory for annotated modelsfor using  on test scope.
 */
public class TestScopeModelFactory extends BindableModelFactory<Object> {

    private final Object definitionSet;

    public TestScopeModelFactory(Object definitionSet) {
        this.definitionSet = definitionSet;
    }
   
    private static Set<Class<?>> getDefinitions(Object defSet ) {
        return BackendBindableDefinitionUtils.getDefinitions( defSet );
    }

    @Override
    public Set<Class<?>> getAcceptedClasses() {
        return getDefinitions( this.definitionSet );
    }

    @Override
    public Object build(Class<?> clazz) {
        
        Builder<?> builder = newDefinitionBuilder( clazz );
        
        return builder.build();
    }

    private Builder<?> newDefinitionBuilder( Class<?> definitionClass ) {

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
    
    private Class<? extends Builder<?>> getDefinitionBuilderClass( Class<?> definitionClass ) {

        if ( null != definitionClass ) {

            Definition annotation = definitionClass.getAnnotation(Definition.class);
            
            if ( null != annotation ) {
                
                return annotation.builder();
                
            }
        }

        return null;
    }
    
}
