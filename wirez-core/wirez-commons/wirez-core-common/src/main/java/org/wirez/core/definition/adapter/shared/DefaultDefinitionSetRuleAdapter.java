package org.wirez.core.definition.adapter.shared;

import org.wirez.core.definition.adapter.DefinitionSetRuleAdapter;
import org.wirez.core.definition.impl.DefinitionSetImpl;
import org.wirez.core.rule.Rule;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collection;

@ApplicationScoped
public class DefaultDefinitionSetRuleAdapter implements DefinitionSetRuleAdapter<DefinitionSetImpl> {

    @Override
    public boolean accepts(final Class<?> pojo)
    {
        return pojo.getName().equals(DefinitionSetImpl.class.getName());
    }
    
    @Override
    public Collection<Rule> getRules(final DefinitionSetImpl pojo) {
        return null;
    }

    @Override
    public int getPriority() {
        return 1;
    }
    
}
