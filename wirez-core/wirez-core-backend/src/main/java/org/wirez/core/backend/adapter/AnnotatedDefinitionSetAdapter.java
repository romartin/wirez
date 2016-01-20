package org.wirez.core.backend.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.DefaultDefinitionSet;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;

import javax.enterprise.context.ApplicationScoped;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AnnotatedDefinitionSetAdapter implements DefinitionSetAdapter<DefinitionSet> {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedDefinitionSetAdapter.class);

    @Override
    public boolean accepts(Class pojoClass) {
        return !pojoClass.equals(DefaultDefinitionSet.class);
    }

    @Override
    public Set<PropertySet> getPropertySets(DefinitionSet pojo) {
        // TODO
        return null;
    }

    @Override
    public Set<Definition> getDefinitions(DefinitionSet definitionSet) {
        Set<Definition> result = null;

        if ( null != definitionSet ) {

            Method[] methods = definitionSet.getClass().getMethods();
            if ( null != methods ) {

                Set<Definition> definitions = new HashSet<>();
                for (Method method : methods) {
                    org.wirez.core.api.annotation.definitionset.Definition annotation = method.getAnnotation(org.wirez.core.api.annotation.definitionset.Definition.class);
                    if ( null != annotation) {
                        try {
                            Definition definition  = (Definition) method.invoke(definitionSet);
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
    public Collection<Rule> getRules(DefinitionSet pojo) {
        // TODO
        return null;
    }
}
