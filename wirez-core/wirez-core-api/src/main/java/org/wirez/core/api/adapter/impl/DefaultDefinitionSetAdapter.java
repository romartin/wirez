package org.wirez.core.api.adapter.impl;

import org.wirez.core.api.adapter.Adapter;
import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.DefaultDefinitionSet;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import java.util.Collection;
import java.util.Set;

@ApplicationScoped
public class DefaultDefinitionSetAdapter implements DefinitionSetAdapter<DefaultDefinitionSet> {

    @Override
    public boolean accepts(final Class pojoClass) {
        return pojoClass.equals(DefaultDefinitionSet.class);
    }

    @Override
    public Set<PropertySet> getPropertySets(DefaultDefinitionSet pojo) {
        Set<PropertySet> result = null;
        if ( null != pojo ) {
            result = pojo.getPropertySets();
        }

        return result;
    }

    @Override
    public Set<Definition> getDefinitions(DefaultDefinitionSet pojo) {
        Set<Definition> result = null;
        if ( null != pojo ) {
            result = pojo.getDefinitions();
        }
        
        return result;
    }

    @Override
    public Collection<Rule> getRules(DefaultDefinitionSet pojo) {
        Collection<Rule> result = null;
        if ( null != pojo ) {
            result = pojo.getRules();
        }

        return result;
    }
}
