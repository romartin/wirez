package org.wirez.core.backend.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.DefaultDefinition;
import org.wirez.core.api.definition.DefaultDefinitionSet;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class AnnotatedDefinitionSetAdapter implements DefinitionSetAdapter<DefinitionSet> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedDefinitionSetAdapter.class);

    @Inject
    AnnotatedDefinitionAdapter annotatedDefinitionAdapter;

    @Override
    public boolean accepts(Object pojo) {
        return ( ! ( pojo instanceof DefaultDefinitionSet ) );
    }

    @Override
    public Set<PropertySet> getPropertySets(DefinitionSet definitionSet) {
        // Not supported right now.
        return new HashSet<>();
    }

    @Override
    public Set<Definition> getDefinitions(DefinitionSet definitionSet) {
        Set<Definition> result = null;

        if ( null != definitionSet ) {

            Field[] fields = definitionSet.getClass().getDeclaredFields();
            if ( null != fields ) {

                Set<Definition> definitions = new HashSet<>();
                for (Field field: fields) {
                    org.wirez.core.api.annotation.definitionset.Definition annotation = field.getAnnotation(org.wirez.core.api.annotation.definitionset.Definition.class);
                    if ( null != annotation) {
                        try {
                            field.setAccessible(true);
                            Definition definition  = (Definition) field.get(definitionSet);
                            definitions.add(definition);
                        } catch (Exception e) {
                            LOG.error("Error obtaining annotated definitions for DefinitionSet with id " + definitionSet.getId());
                        }
                    }
                }

                result = definitions;
            }
            
        }
        
        return result;
    }

    @Override
    public Set<Property> getAllProperties(DefinitionSet pojo) {
        final Set<Property> result = new HashSet<>();
        Set<Definition> definitions = getDefinitions(pojo);
        if ( null != definitions ) {
            for (Definition definition : definitions) {
                Set<Property> defProperties = annotatedDefinitionAdapter.getProperties(definition);
                if ( null != defProperties && !defProperties.isEmpty() ) {
                    result.addAll(defProperties);
                }
            }
        }
        
        return result;
    }

    @Override
    public int getPriority() {
        return 100;
    }
    
}
