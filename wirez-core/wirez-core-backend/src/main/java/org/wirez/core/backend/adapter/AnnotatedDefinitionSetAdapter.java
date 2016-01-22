package org.wirez.core.backend.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.DefaultDefinitionSet;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.DefinitionSet;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.registry.RuleRegistry;
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

    RuleRegistry<Rule> ruleRuleRegistry;

    @Inject
    public AnnotatedDefinitionSetAdapter(RuleRegistry<Rule> ruleRuleRegistry) {
        this.ruleRuleRegistry = ruleRuleRegistry;
    }

    @Override
    public boolean accepts(Class pojoClass) {
        return !pojoClass.equals(DefaultDefinitionSet.class);
    }

    @Override
    public Set<PropertySet> getPropertySets(DefinitionSet definitionSet) {
        // TODO
        return null;
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

    // Rules are generated at compile time by the default annotation processor and available at runtime in the RuleRegistry
    @Override
    public Collection<Rule> getRules(DefinitionSet pojo) {
        return ruleRuleRegistry.getRules(pojo);
    }
}
