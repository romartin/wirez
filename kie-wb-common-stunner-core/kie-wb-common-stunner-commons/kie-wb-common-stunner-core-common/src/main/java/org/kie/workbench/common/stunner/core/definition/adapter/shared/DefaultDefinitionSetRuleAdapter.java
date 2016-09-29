package org.kie.workbench.common.stunner.core.definition.adapter.shared;

import org.kie.workbench.common.stunner.core.definition.adapter.DefinitionSetRuleAdapter;
import org.kie.workbench.common.stunner.core.definition.impl.DefinitionSetImpl;
import org.kie.workbench.common.stunner.core.rule.Rule;

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
    public boolean isPojoModel() {
        return false;
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
