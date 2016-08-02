package org.wirez.core.definition.adapter;

public abstract class AbstractDefinitionSetRuleAdapter<T> implements DefinitionSetRuleAdapter<T> {

    @Override
    public boolean isPojoModel() {
        return true;
    }

    @Override
    public int getPriority() {
        return 0;
    }

}
