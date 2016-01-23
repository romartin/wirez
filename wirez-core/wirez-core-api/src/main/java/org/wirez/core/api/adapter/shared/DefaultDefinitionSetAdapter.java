package org.wirez.core.api.adapter.shared;

import org.wirez.core.api.adapter.DefinitionSetAdapter;
import org.wirez.core.api.definition.DefaultDefinition;
import org.wirez.core.api.definition.DefaultDefinitionSet;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.rule.Rule;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.Set;

@ApplicationScoped
public class DefaultDefinitionSetAdapter implements DefinitionSetAdapter<DefaultDefinitionSet> {

    @Override
    public boolean accepts(final Object pojo) {
        return pojo instanceof DefaultDefinitionSet;
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
    public Set<Property> getAllProperties(DefaultDefinitionSet pojo) {
        // TODO
        return null;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
}
